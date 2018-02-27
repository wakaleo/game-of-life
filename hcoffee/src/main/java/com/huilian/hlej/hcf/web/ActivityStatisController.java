package com.huilian.hlej.hcf.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.ActivityStatisService;
import com.huilian.hlej.hcf.service.ChannelManagementService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityStatisVo;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.RewardRecordVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 活动统计控制器
 * @author xiekangjian
 * @date 2017年1月12日 下午2:30:02
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/ActivityStatis")
public class ActivityStatisController extends BaseController {

	 @Autowired
	  private ActivityStatisService activityStatisService;
	
	 
	 @Autowired
	  private VendingMachineService vendingMachineService;

	 

		@Autowired
		private ChannelManagementService channelManagementService;

	 
	 @RequestMapping(value = {"list"})
	  public String list(ActivityStatisVo activityStatisVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<ActivityStatisVo> page = activityStatisService.getActivityStatisPage(new Page<ActivityStatisVo>(request, response), activityStatisVo);
		  model.addAttribute("page", page);
		  model.addAttribute("channelList", vendingMachineService.getChannel());
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  model.addAttribute("communityList", vendingMachineService.getCommunity());
		  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords() );
		  activityStatisVo.setStartTime(null);
		  activityStatisVo.setEndTime(null);
		  model.addAttribute("activityStatisVo", activityStatisVo);
	    return "/hcoffee/vending/statis/activityStatisList";
	  }
	
	 
	 @RequestMapping(value = "rewardRecordList")
	  public  String rewardRecordList(RewardRecordVo rewardRecordVo,HttpServletRequest request, HttpServletResponse response, Model model ) {
		  Page<RewardRecordVo> page = activityStatisService.getRewardRecordListPage(new Page<RewardRecordVo>(request, response), rewardRecordVo);
		 
		 
		 // channelManagementService.getChannelNameByChannelId(rewardRecordVo.getUsrChannel());
		 
		  
		  model.addAttribute("page", page);
		  //model.addAttribute("activityType", vo.getSchemeName());
		//  model.addAttribute("channelName", vo.getChannelName());
		  model.addAttribute("usrChannel", rewardRecordVo.getUsrChannel());
		  model.addAttribute("schemeNo", rewardRecordVo.getSchemeNo());
		  model.addAttribute("channelList", vendingMachineService.getChannel());
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  rewardRecordVo.setStartTime(null);
		  rewardRecordVo.setEndTime(null);
		  model.addAttribute("rewardRecordVo", rewardRecordVo);
		  
		  return "/hcoffee/vending/statis/rewardRecordList";
	  }
	 
	 /**
	  * 导出活动统计数据
	  *
	  * @param activityStatisVo
	  * @param request
	  * @param response
	  * @param redirectAttributes
	  * @return
	  */
	 ///@RequiresPermissions("list")
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(ActivityStatisVo activityStatisVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "活动统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<ActivityStatisVo> page = activityStatisService.getActivityStatisPage(new Page<ActivityStatisVo>(request, response), activityStatisVo);
	     List<Integer> widths = Arrays.asList(2000,2400, 0, 0,2400,0);
	     new HCFExportExcel("活动统计", ActivityStatisVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/ActivityStatis/list?repage";
	 }
	 
	 
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(ActivityStatisVo activityStatisVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "活动统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<ActivityStatisVo> page = activityStatisService.getActivityStatisPage(new Page<ActivityStatisVo>(request, response, "str"), activityStatisVo);
			 List<Integer> widths = Arrays.asList(2000,2400, 0, 0,2400,0);
			 new HCFExportExcel("活动统计", ActivityStatisVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/ActivityStatis/list?repage";
	 }
	 
	 /**
	  * 导出活动统计详情数据
	  *
	  * @param rewardRecordVo
	  * @param request
	  * @param response
	  * @param redirectAttributes
	  * @return
	  */
	 ///@RequiresPermissions("list")
	 @RequestMapping(value = "exportRewardRecord", method = RequestMethod.POST)
	 public String exportFile1(RewardRecordVo rewardRecordVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 ActivityManageVo vo=null;
		 ChannelVo channe =null;
		 ChannelVo sad=new ChannelVo();
		 if(!rewardRecordVo.getSchemeNo().isEmpty()&&!rewardRecordVo.getUsrChannel().isEmpty()){
			 vo = activityStatisService.getActivityManageBySchName(rewardRecordVo.getSchemeNo());
			
			 
			 sad.setChannelId(new BigDecimal(rewardRecordVo.getUsrChannel()));
			 channe = channelManagementService.getChannelNameByChannelId(sad);
			 
				 
			 }
		 
		 
		 try {
	     String fileName = "活动统计详情" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<RewardRecordVo> page = activityStatisService.getRewardRecordListPage(new Page<RewardRecordVo>(request, response), rewardRecordVo);
	 
	     List<RewardRecordVo> list = page.getList();
	     
	     if(!rewardRecordVo.getSchemeNo().isEmpty()&&!rewardRecordVo.getUsrChannel().isEmpty()){
	    	 
			 for(RewardRecordVo vv :list ){
				 
		    	 vv.setAnctiviType(vo.getSchemeName());
		    	 vv.setChannelName(channe.getChannelName());
		    	 
		     }
	     }else{
	    	 for(RewardRecordVo vv :list ){
				 vo = activityStatisService.getActivityManageBySchName(vv.getSchemeNo());
				 sad.setChannelId(vv.getChannel());
				 channe = channelManagementService.getChannelNameByChannelId(sad);
				 
		    	 vv.setAnctiviType(vo.getSchemeName());
		    	 vv.setChannelName(channe.getChannelName());
		    	 
		     }
	    	 
	     } 
		 
	    
	     
	     List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400,2400,2400,2400,0, 0, 0);
	     new HCFExportExcel("活动统计详情", RewardRecordVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/ActivityStatis/rewardRecordList?repage";
	 }
	
	 @RequestMapping(value = "qbexportRewardRecord", method = RequestMethod.POST)
	 public String exportQbFile1(RewardRecordVo rewardRecordVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		 ChannelVo sad=new ChannelVo();
		 ActivityManageVo vo=null;
		 ChannelVo channe =null;
		 if(!rewardRecordVo.getSchemeNo().isEmpty()&&!rewardRecordVo.getUsrChannel().isEmpty()){
			 vo = activityStatisService.getActivityManageBySchName(rewardRecordVo.getSchemeNo());
			
			 
			 sad.setChannelId(new BigDecimal(rewardRecordVo.getUsrChannel()));
			 channe = channelManagementService.getChannelNameByChannelId(sad);
			 }
		 
		 try {
			 String fileName = "活动统计详情" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<RewardRecordVo> page = activityStatisService.getRewardRecordListPage(new Page<RewardRecordVo>(request, response, "str"), rewardRecordVo);
			
			    List<RewardRecordVo> list = page.getList();
			     
			     
			    if(!rewardRecordVo.getSchemeNo().isEmpty()&&!rewardRecordVo.getUsrChannel().isEmpty()){
			    	 
					 for(RewardRecordVo vv :list ){
						 
				    	 vv.setAnctiviType(vo.getSchemeName());
				    	 vv.setChannelName(channe.getChannelName());
				    	 
				     }
			     }else{
			    	 for(RewardRecordVo vv :list ){
						 vo = activityStatisService.getActivityManageBySchName(vv.getSchemeNo());
						 sad.setChannelId(vv.getChannel());
						 channe = channelManagementService.getChannelNameByChannelId(sad);
						 
				    	 vv.setAnctiviType(vo.getSchemeName());
				    	 vv.setChannelName(channe.getChannelName());
				    	 
				     }
			     } 
			 
			 List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400,2400,2400,2400,0, 0, 0);
			 new HCFExportExcel("活动统计详情", RewardRecordVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/ActivityStatis/rewardRecordList?repage";
	 }
	 
}
