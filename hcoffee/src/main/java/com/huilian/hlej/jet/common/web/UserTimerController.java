package com.huilian.hlej.jet.common.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.jet.common.service.UserTimerService;
import com.huilian.hlej.jet.modules.sys.dao.UserDao;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "${adminPath}/userTimer")
public class UserTimerController extends BaseController{
	
	@Autowired
	private UserTimerService userTimerService;
	
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = { "validateIsFirst", "" })
	public void validateIsFirst(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String loginName =  request.getParameter("loginName");
		
		User user = new User();
		user.setLoginName(loginName);
		int isFirstLogin = userTimerService.getFirstLoginCount(user);
		
		this.renderString(response, isFirstLogin);
	
		//	return "/modules/sys/firstModifyPwd";
		
	}
	

	/**
	 * 首次登录设置密码页面
	 * TODO Add comments here.
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "toFirstModifyPwd", "" })
	public String toFirstModifyPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		
		return "/modules/sys/firstModifyPwd";
		
	}
	
	/**
	 * 验证输入的账户及旧密码是否有问题
	 * TODO Add comments here.
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "getUserByPwd", "" })
	public void getUserByPwd(HttpServletRequest request,HttpServletResponse response,Model model){
		JSONObject item =  new JSONObject();
		String loginName = request.getParameter("loginName");
		String oldPassword = request.getParameter("oldPassword");
	//	oldPassword = SystemService.entryptPassword(oldPassword);
		
		/*SystemService.validatePassword(oldPassword, password)*/
		User user = new User();
		user.setLoginName(loginName);
		user = userTimerService.getUserBy(user);
		String isEmpty="";
		String msg="";
		if(user!=null){
			
			String password = user.getPassword();
			boolean isUser= SystemService.validatePassword(oldPassword, password);
			/*user.setPassword(oldPassword);
			user = userTimerService.getUserBy(user);*/
			if(!isUser){
				isEmpty="0";
				msg  = "用户旧密码错误";
				item.put("isEmpty", isEmpty);
				item.put("msg", msg);
			}else{
				isEmpty="1";
				item.put("isEmpty", isEmpty);
			}
		}
		else{
			isEmpty="0";
			msg  = "用户不存在";
			item.put("isEmpty", isEmpty);
			item.put("msg", msg);
		}
	
		this.renderString(response, item);
	
	}
	
	/**
	 * 更新用户密码
	 * TODO Add comments here.
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "toUpdateUser", "" })
	public void toUpdateUser(HttpServletRequest request,HttpServletResponse response,Model model){
		
		String loginName = request.getParameter("loginName");
		String oldPassword = request.getParameter("oldPassword");
		oldPassword = SystemService.entryptPassword(oldPassword);
		String newPassword = request.getParameter("newPassword");
		newPassword=SystemService.entryptPassword(newPassword);
		Date now = new Date();
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(newPassword);
		user.setUpdateDate(now);
		user.setIsFirstLogin(0);
		userDao.updateSysUserPwd(user);
		System.out.println(1);
	}
	
	
	
}	
