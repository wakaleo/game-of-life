package com.huilian.hlej.jet.common.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.common.utils.EmailUtil;
import com.huilian.hlej.jet.common.utils.PassRegularUtil;
import com.huilian.hlej.jet.modules.sys.dao.UserDao;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.service.SystemService;
@Service
@Transactional(readOnly = false)
public class UserTimerService extends BaseService implements InitializingBean {


	@Autowired
	private UserDao userDao;
	
	public void timerUpdateSysPwd() throws UnsupportedEncodingException{
		User user = new User();
		List<User>  users  =  userDao.getNeedUpdateUsers(user);
		EmailUtil eu = new EmailUtil();
		for(int i=0,size=users.size();i<size;i++){
			user  =  users.get(i);
			String pwStr =  PassRegularUtil.doGenerateBack(6);
			user.setPassword(SystemService.entryptPassword(pwStr));
			Date updateDate = new Date();
			user.setUpdateDate(updateDate);
			userDao.updateSysUserPwd(user);
			try {
				eu.doSendEmail("【系统安全】系统定时修改账号密码", "尊敬的用户"+user.getLoginName()+" ,您好:<br/>&nbsp;&nbsp; 出于系统安全，您的账号密码被修改为:"+pwStr+"。", "flyingfinancial_1@163.com");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public void afterPropertiesSet() throws Exception {

		
	}
	
	public int  getFirstLoginCount(User user){
		return userDao.getFirstLoginCount(user);
	}
	
	public int updateSysUserPwd(User user){
		return userDao.updateSysUserPwd(user);
	}
	
	
	public User getUserBy(User user){
		return userDao.getUserBy(user);
	}

}
