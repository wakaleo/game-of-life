package com.huilian.hlej.jet.common.timer;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huilian.hlej.jet.common.service.UserTimerService;
import com.huilian.hlej.jet.common.utils.PassRegularUtil;
import com.huilian.hlej.jet.modules.sys.dao.UserDao;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.service.SystemService;
@Component
//@Controller
public  class CycleUpdateUserPwd     {

	
	@Autowired
	private UserTimerService userTimerService;
	
	@Autowired
	private UserDao userDao;


	public void doItB() throws UnsupportedEncodingException{
		System.out.println("===============bbbbbbbegin===============!");
		startWorkFlowB();
		System.out.println("===============bbbbbbend=================!");
	
	}
	
	public void doItC(){
		startWorkFlowC();
	}

	
	public void startWorkFlowB() throws UnsupportedEncodingException{
		userTimerService.timerUpdateSysPwd();
		/*User user = new User();
		List<User>  users  =  userDao.getNeedUpdateUsers(user);
		EmailUtil eu = new EmailUtil();
		for(int i=0,size=users.size();i<size;i++){
			user  =  users.get(i);
			String pwStr =  PassRegularUtil.doGenerateBack(6);
			user.setPassword(SystemService.entryptPassword(pwStr));
			Date updateDate = new Date();
			user.setUpdateDate(updateDate);
		//	userDao.updateSysUserPwd(user);
			try {
				eu.doSendEmail("【系统安全】系统定时修改账号密码", "尊敬的用户"+user.getLoginName()+" ,您好:<br/>&nbsp;&nbsp; 出于系统安全，您的账号密码被修改为:"+pwStr+"。", "494100425@qq.com");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}*/
		
		/*user  =  users.get(0);
		String pwStr =  PassRegularUtil.doGenerateBack(6);
		user.setPassword(SystemService.entryptPassword(pwStr));
		Date updateDate = new Date();
		user.setUpdateDate(updateDate);
		//userDao.updateSysUserPwd(user);
		try {
			eu.doSendEmail("【系统安全】系统定时修改账号密码", "尊敬的用户"+user.getLoginName()+" ,您好:<br/>&nbsp;&nbsp; 出于系统安全，您的账号密码被修改为:"+pwStr+"。", "flyingfinancial_1@qq.com");
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
	}
	
	
	public void startWorkFlowC(){
		User user = new User();
		List<User>  users  =  userDao.getMoreNeedUpdateUsers(user);
		for(int i=0,size=users.size();i<size;i++){
			user  =  users.get(i);
			String pwStr =  PassRegularUtil.doGenerateBack(6);
			user.setPassword(SystemService.entryptPassword(pwStr));
			userDao.updateSysUserPwd(user);
		}
	}

	
	@Scheduled(cron="0/5 * * * * ? ") //间隔5秒执行  
	//@Scheduled(cron="0 0/10 * * * ? ") //间隔10分执行  
    public void taskCycle(){  
     /*   try {
			this.doItB();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
    } 
	
	
	
	//@Scheduled(cron="0/5 * * * * ? ") //间隔5秒执行  
	//@Scheduled(cron="0 0/10 * * * ? ") //间隔10分执行  
    public void taskCycleC(){  
    } 
	
}
