package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.ActivityStatisService;
import com.huilian.hlej.hcf.service.ActivityTemplateService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.RewardRecordVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 活动管理
 * @author yangbo
 * @date 2017年3月3日 下午2:30:02
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/activityManagement")
public class activityManagementController extends BaseController {

	 @Autowired
	  private ActivityStatisService activityStatisService;
	
	 
	 @Autowired
	  private VendingMachineService vendingMachineService;
	 
	 @Autowired
	 private ActivityTemplateService activityTemplateService;
	 
	 
	 @RequestMapping(value = {"list"})
	  public String list(ActivityManageVo activityManageVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		 if("注册类大图".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("1");
			 
		 }
		 if("注册类小图".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("2");
			 
		 }
		 if("广告类".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("3");
			 
		 }
		 if("贷款类".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("4");
			 
		 }
		 
		  Page<ActivityManageVo> page = activityStatisService.getActivityVoStatisPage(new Page<ActivityManageVo>(request, response), activityManageVo);
		  Page<ActivityManageVo> pages = activityStatisService.getActivityVoStatisPage(new Page<ActivityManageVo>(request, response, "str"), activityManageVo);
			/*List<Channel>   lst = vendingMachineService.getChannel();
			Map<String,String> map = new HashMap<String,String>();
			for(Channel c :lst){
				map.put(c.getChannelId().toString(), c.getChannelName());
			}
			List<ActivityManageVo>list =   page.getList();
			for(ActivityManageVo vo:list){
				String channelId = vo.getChannel();
				String[]arr = channelId.split(",");
				StringBuffer sb = new StringBuffer();
				for(String str:arr){
					if(map.containsKey(str)){
						if(str==arr[arr.length - 1]){
							sb.append(map.get(str));
						}else {
							sb.append(map.get(str)+",");
						}
					}
				}
				vo.setChannelName(sb.toString());
			}*/
		  
		  model.addAttribute("page", page);
		  
		  model.addAttribute("pages", pages.getList().size());
		  model.addAttribute("channelList", vendingMachineService.getChannel()); 
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  model.addAttribute("activityTypeList",vendingMachineService.getActivityType() ); 
		  model.addAttribute("communityList", vendingMachineService.getCommunity());
		  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords() );
		  activityManageVo.setStartTime(null);
		  activityManageVo.setEndTime(null);
		 
		  model.addAttribute("activityManageVo", activityManageVo);
	    return "/hcoffee/vending/statis/activityManagement";
	    
	  }
	
	 
	 @RequestMapping(value = "close")
	  @ResponseBody
	  public Map<String, String> close(@RequestBody ActivityManageVo activityManageVo) {
		  if(("0").equals(activityManageVo.getStatus())){
			  activityManageVo.setStatus("1");
		  }else{
			  activityManageVo.setStatus("0");
		  }
		  Map<String,String>  result = activityStatisService.closeAtivityManagement(activityManageVo);
		  return  result;
	  }
	  
	 
	 
	  @RequestMapping(value = "delete")
	  
	  @ResponseBody
	  public Map<String, String> delete(@RequestBody ActivityManageVo activityManageVo) throws IOException {
		  Map<String,String>  result  = new HashMap<String, String>();
		  
		 List< ActivityConterTemplateVo> sad	=  activityStatisService.getActivityManageBy(activityManageVo.getSchemeNo());
		 
		 
		 if(sad!=null){
			 for(ActivityConterTemplateVo sb : sad){
				 
				 List<VendingMachineVo> selectVendByTemplateId = activityTemplateService.selectVendByTemplateId(sb.getTemplateId());
				 if(selectVendByTemplateId!=null){
					    result.put("code", "1");
						result.put("msg", "该活动已经发布了不能删除"); 
					 return result;
				 }
				 
			 }
			 
		 }
		 
		 result = activityStatisService.deleteActivityById(activityManageVo);
		  
	  	return  result;
	  }
	  
	  
	  @RequestMapping(value = "selectImg")
	  @ResponseBody
	  public String selectImg(@RequestBody String activityType, Model model) {
	
		  
		  ActivityTypeVo cityByCityId = activityStatisService.getSelectImg(activityType);
		  
		  return   JsonMapper.toJsonString(cityByCityId);
	  }
	  
	 
	 /**
	  * 导出活动统计数据
	  *
	  * @param ActivityManageVo
	  * @param request
	  * @param response
	  * @param redirectAttributes
	  * @return
	  */
	 ///@RequiresPermissions("list")
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(ActivityManageVo activityManageVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "活动管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<ActivityManageVo> page = activityStatisService.getActivityVoStatisPage(new Page<ActivityManageVo>(request, response), activityManageVo);
	     List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
	     new HCFExportExcel("活动管理", ActivityManageVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/ActivityStatis/list?repage";
	 }
	
	 
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(ActivityManageVo activityManageVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "活动管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<ActivityManageVo> page = activityStatisService.getActivityVoStatisPage(new Page<ActivityManageVo>(request, response,"str"), activityManageVo);
			 List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
			 new HCFExportExcel("活动管理", ActivityManageVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
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
	   try {
	     String fileName = "活动统计详情" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<RewardRecordVo> page = activityStatisService.getRewardRecordListPage(new Page<RewardRecordVo>(request, response), rewardRecordVo);
	     List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
	     new HCFExportExcel("活动统计详情", RewardRecordVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/ActivityStatis/rewardRecordList?repage";
	 }
	
	 @RequestMapping(value = "load")
	  @ResponseBody
	  public String edit(@RequestBody ActivityManageVo activityManageVo) {
		 ActivityManageVo vo = activityStatisService.getActivityManagementCode(activityManageVo);
		 
		 vo.setChannelName(activityManageVo.getChannelName());
		 
		 
		 if("1".equals(vo.getActivityType())){
			 vo.setActivityType("注册类大图");
			 
		 }
		 if("2".equals(vo.getActivityType())){
			 vo.setActivityType("注册类小图");
			 
		 }
		 if("3".equals(vo.getActivityType())){
			 vo.setActivityType("广告类");
			 
		 }
		 if("4".equals(vo.getActivityType())){
			 vo.setActivityType("贷款类");
			 
		 }
		  return   JsonMapper.toJsonString(vo);
	  }
	 
	  @RequestMapping(value = "updatePrice")
	  @ResponseBody
	  public Map<String, String> updateActivt(@RequestBody ActivityManageVo activityManageVo) throws IOException {
		 Map<String,String>  result  = new HashMap<String, String>();
		 if("注册类大图".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("1");
			 
		 }
		 if("注册类小图".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("2");
			 
		 }
		 if("广告类".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("3");
			 
		 }
		 if("贷款类".equals(activityManageVo.getActivityType())){
			 activityManageVo.setActivityType("4");
			 
		 }
		 
		 ActivityManageVo  vo = activityStatisService.getChannelById(activityManageVo);
		 
		 
		 if(!vo.getSchemeNo().equals(activityManageVo.getSchemeNo())){//编码发生了更改
			 result = this.validateVO(activityManageVo.getSchemeNo(), null);
			  if(result.size()>0){
				  return result;
			  }
		 }
		 if(!vo.getSchemeName().equals(activityManageVo.getSchemeName())){//名称发生了更改
			 result = this.validateVO(null, activityManageVo.getSchemeName());
			  if(result.size()>0){
				  return result;
			  }
		 }
		 
		 activityManageVo.setChannel(activityManageVo.getChannelList());
	  	result = activityStatisService.updatePriceById(activityManageVo);
	  	return  result;
	  }
	 
	 
	 @RequestMapping(value = "save")
	  @ResponseBody
	  public Map<String, String> save(@RequestBody ActivityManageVo activityManageVo) throws IOException {
		  Map<String,String>  result =  new HashMap<String, String>();
		  
		  activityManageVo.setChannel(activityManageVo.getChannelList());
		  
		/*  result = this.validateVO(activityManageVo.getSchemeNo(), null);
		  if(result.size()>0){
			  return result;
		  }*/
		  
		  
		  if("注册类大图".equals(activityManageVo.getActivityType())){
				 activityManageVo.setActivityType("1");
				 
			 }
			 if("注册类小图".equals(activityManageVo.getActivityType())){
				 activityManageVo.setActivityType("2");
				 
			 }
			 if("广告类".equals(activityManageVo.getActivityType())){
				 activityManageVo.setActivityType("3");
				 
			 }
			 if("贷款类".equals(activityManageVo.getActivityType())){
				 activityManageVo.setActivityType("4");
				 
			 }
			 
			 ActivityManageVo  vo = activityStatisService.getActivityManageBySchName(activityManageVo.getSchemeNo());
			 
			 ActivityManageVo  voo = activityStatisService.getActivityManageBySchNameNo(activityManageVo.getSchemeName());
			 
			 if(vo!=null){
				 
				 result.put("code", "2");
				  result.put("msg", "活动编码已存在，请重新输入!");
				  return  result;
			 }
			 if(voo!=null){
				 
				 result.put("code", "2");
				 result.put("msg", "活动名称已存在，请重新输入!");
				 return  result;
			 }
			
		  
		  
		  
		
	  	result = activityStatisService.saveChannel(activityManageVo);
	  	return  result;
	  }
	 
	 /**
	   *验证渠道Id或者名称是否重复
	   */
	  private Map<String,String>  validateVO(String schemeNo,String schemeName){
			 Map<String,String>  result = new HashMap<String, String>();
			 ActivityManageVo cu = new ActivityManageVo();
			 if(schemeNo!=null && !"".equals(schemeNo)){
				 cu.setSchemeNo(schemeNo);
			  }else if(schemeName!=null && !"".equals(schemeName)){
				  cu.setSchemeName(schemeName);
			  }
			 
			 ActivityManageVo vo = activityStatisService.getChannel(cu);
			  if(vo!=null){
				  if(vo.getSchemeNo()==null){
					  result.put("code", "2");
					  result.put("msg", "活动编码已存在，请重新输入!");
					  return  result;
				
				  }else if (vo.getSchemeName()==null){
					  result.put("code", "2");
					  result.put("msg", "活动类型已存在，请重新输入!");
					  return  result;
				  }
				  
			  }
			return result; 
	  }  
}
