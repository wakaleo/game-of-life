/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.huilian.hlej.base.utils.RedisUtil;
import com.huilian.hlej.jet.common.config.Global;
import com.huilian.hlej.jet.common.security.shiro.session.SessionDAO;
import com.huilian.hlej.jet.common.servlet.ValidateCodeServlet;
import com.huilian.hlej.jet.common.utils.CacheUtils;
import com.huilian.hlej.jet.common.utils.CookieUtils;
import com.huilian.hlej.jet.common.utils.IdGen;
import com.huilian.hlej.jet.common.utils.StringUtils;
import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.dao.UserDao;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.security.FormAuthenticationFilter;
import com.huilian.hlej.jet.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 *
 * @author huilian.hlej
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

  @Autowired
  private SessionDAO sessionDAO;
  
  @Autowired
  private UserDao userDao;

  private String loginName;
  
  private String password;
  
  /**
   * 是否是验证码登录
   *
   * @param useruame 用户名
   * @param isFail   计数加1
   * @param clean    计数清零
   * @return
   */
  @SuppressWarnings("unchecked")
  public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
    Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
    if (loginFailMap == null) {
      loginFailMap = Maps.newHashMap();
      CacheUtils.put("loginFailMap", loginFailMap);
    }
    Integer loginFailNum = loginFailMap.get(useruame);
    if (loginFailNum == null) {
      loginFailNum = 0;
    }
    if (isFail) {
      loginFailNum++;
      loginFailMap.put(useruame, loginFailNum);
    }
    if (clean) {
      loginFailMap.remove(useruame);
    }
    return loginFailNum >= 3;
  }

  /**
   * 管理登录
   */
  @RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
  public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
    Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}

    if (logger.isDebugEnabled()) {
      logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
    }

    // 如果已登录，再次访问主页，则退出原账号。
    if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
      CookieUtils.setCookie(response, "LOGINED", "false");
    }
    
  
    
    // 如果已经登录，则跳转到管理首页
    if (principal != null && !principal.isMobileLogin()) {
    	
    	  //判断是否第一次登录  by chentao 
    	User user = new User();
    	user.setLoginName(principal.getLoginName());
    	user = userDao.getUserBy(user);
    	
        int isFirstLogin= user.getIsFirstLogin();

        if(isFirstLogin==1){
        	  return "redirect:" + Global.getAdminPath() + "/userTimer/toFirstModifyPwd";
        }else{
        
        
        	return "redirect:" + adminPath;
        }
    }
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jet/src/main/webapp/WEB-INF/lib/jet.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
    return "modules/sys/sysLogin";
  }

  /**
   * 登录失败，真正登录的POST请求由Filter完成
   */
  @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
  public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
    Principal principal = UserUtils.getPrincipal();
    loginName = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
    password = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);

    // 如果已经登录，则跳转到管理首页
    if (principal != null) {
    /*  return "redirect:" + adminPath;*/
    	  //判断是否第一次登录  by chentao 
    	User user = new User();
    	user.setLoginName(principal.getLoginName());
    	user = userDao.getUserBy(user);
    	
        int isFirstLogin= user.getIsFirstLogin();
        if(isFirstLogin==1){
        	  return "redirect:" + Global.getAdminPath() + "/userTimer/toFirstModifyPwd";
        }else{
        
        
        	return "redirect:" + adminPath;
        }
    }

    String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
    boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
    boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
    String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

    if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
      message = "用户或密码错误, 请重试.";
    }

    model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
    model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
    model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
    model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
    model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

    if (logger.isDebugEnabled()) {
      logger.debug("login fail, active session size: {}, message: {}, exception: {}",
          sessionDAO.getActiveSessions(false).size(), message, exception);
    }

    // 非授权异常，登录失败，验证码加1。
    if (!UnauthorizedException.class.getName().equals(exception)) {
      model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
    }

    // 验证失败清空验证码
    request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

    // 如果是手机登录，则返回JSON字符串
    if (mobile) {
      return renderString(response, model);
    }

    return "modules/sys/sysLogin";
  }

  /**
   * 登录成功，进入管理首页
   */
  @RequiresPermissions("user")
  @RequestMapping(value = "${adminPath}")
  public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
    Principal principal = UserUtils.getPrincipal();

    // 登录成功后，验证码计算器清零
    isValidateCodeLogin(principal.getLoginName(), false, true);

    if (logger.isDebugEnabled()) {
      logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
    }

    // 如果已登录，再次访问主页，则退出原账号。
    if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
      String logined = CookieUtils.getCookie(request, "LOGINED");
      if (StringUtils.isBlank(logined) || "false".equals(logined)) {
        CookieUtils.setCookie(response, "LOGINED", "true");
      } else if (StringUtils.equals(logined, "true")) {
        UserUtils.getSubject().logout();
        return "redirect:" + adminPath + "/login";
      }
    }

    // 如果是手机登录，则返回JSON字符串
    if (principal.isMobileLogin()) {
      if (request.getParameter("login") != null) {
        return renderString(response, principal);
      }
      if (request.getParameter("index") != null) {
        return "modules/sys/sysIndex";
      }
      return "redirect:" + adminPath + "/login";
    }

//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = FileUtils.readFileToByteArray(
//					FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
    
    //add by chentao begin
	    if (principal != null && !principal.isMobileLogin()) {
	    	
	  	  //判断是否第一次登录
	    	 //判断是否第一次登录  by chentao 
	    	User user = new User();
	    	user.setLoginName(principal.getLoginName());
	    	user = userDao.getUserBy(user);
	    	
	        int isFirstLogin= user.getIsFirstLogin();
	
	      if(isFirstLogin==1){
	      	  return "redirect:" + Global.getAdminPath() + "/userTimer/toFirstModifyPwd";
	      	  
	      }
	   }
	    //方案一：用session
	    Session session = UserUtils.getSession();
	    String username = (String) session.getAttribute("username");
	    String password = (String) session.getAttribute(username);
	    //方案二：用缓存
//	    String username = RedisUtil.getString("username");
//	    String password = RedisUtil.getString(username);
	    //方案三：用系统缓存
//	    String username = (String) CacheUtils.get("username");
//	    String password = (String) CacheUtils.get(username);
	    
	    model.addAttribute("loginName", username);
	    model.addAttribute("password", password);
    //end
    return "modules/sys/sysIndex";
  }

  /**
   * 获取主题方案
   */
  @RequestMapping(value = "/theme/{theme}")
  public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
    if (StringUtils.isNotBlank(theme)) {
      CookieUtils.setCookie(response, "theme", theme);
    } else {
      theme = CookieUtils.getCookie(request, "theme");
    }
    return "redirect:" + request.getParameter("url");
  }
}
