package com.huilian.hlej.hcf.web;

 
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.entity.ActivityScheme;
import com.huilian.hlej.hcf.service.ActivityStatisService;
import com.huilian.hlej.hcf.service.ActivityTemplateService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;


/**
 * 自动贩卖机
 *
 * @author yangbo
 * @version 2017-7-14
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/activitySheBeiMagement")
public class activitySheBeiMagementContrller extends BaseController {

  @Autowired
  private VendingMachineService vendingMachineService;
  
 @Autowired
  private ActivityTemplateService activityTemplateService;
 
 @Autowired
 private ActivityStatisService activityStatisService;


  @RequestMapping(value = {"list"})
  public String list(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
	  Page<VendingMachineVo> page = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
	  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
	  model.addAttribute("page", page);
	  model.addAttribute("pages", pages.getList().size());
	  model.addAttribute("channelList", vendingMachineService.getChannel());
	  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
	  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
	  model.addAttribute("communityList", vendingMachineService.getCommunity());
	  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
	  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
	  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
	 
	  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
	  model.addAttribute("activityTypeList",vendingMachineService.getActivityType() ); 
	  
	  model.addAttribute("activityTypeMaxImg", vendingMachineService.getActivityTypeMaxImg());
	  model.addAttribute("activityTypeMinImg", vendingMachineService.getActivityTypeMinImg());
	  model.addAttribute("activityTypes", vendingMachineService.getActivityTypes());
	  model.addAttribute("activityTypeDai", vendingMachineService.getActivityTypeDai());
	  
	  
	  
	  vendingMachineVo.setStartTime(null);
	  vendingMachineVo.setEndTime(null);
	  model.addAttribute("vendingMachineVo", vendingMachineVo);
    return "/hcoffee/vending/statis/sheBeiMagement";
  }
  
  @RequestMapping(value = {"recorkeList"})
  public String recorkeList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
	  Page<VendingMachineVo> page = vendingMachineService.getVendingByVendcoed(new Page<VendingMachineVo>(request, response), vendingMachineVo);
	
	  List<VendingMachineVo> list = page.getList();
	  
	  for(VendingMachineVo sd :list){
		  
		  if(sd.getOldTemplateId()!=null){
			  
			  AnctivityTempleVo oldTemplateName = activityTemplateService.getTemplateName(sd.getOldTemplateId());
			  
			  if(oldTemplateName!=null){
				  sd.setOldTemplateName(oldTemplateName.getTemplateName());
				  
			  }
		  }
			 
		  if(sd.getTemplateId()!=null && sd.getOldTemplateId()!=null){
			  if(sd.getTemplateId().equals(sd.getOldTemplateId())){
				  sd.setTemplateName(null);
				  
			  }else{
				  
                 AnctivityTempleVo templateName = activityTemplateService.getTemplateName(sd.getTemplateId());
	              
	              if(templateName!=null){
	            	  
	            	  sd.setTemplateName(templateName.getTemplateName());
	              }
				  
			    }
			  }else{
				  
				  AnctivityTempleVo templateName = activityTemplateService.getTemplateName(sd.getTemplateId());
	              
	              if(templateName!=null){
	            	  
	            	  sd.setTemplateName(templateName.getTemplateName());
	              }
		  }
	  }
	  
	  model.addAttribute("page", page);
	 
	  model.addAttribute("vendingMachineVo", vendingMachineVo);
	  return "/hcoffee/vending/statis/recorkeList";
  }
  
  
  @RequestMapping(value = "save")
  @ResponseBody
  public  Map<String,String>  save(@RequestBody ActivityConterTemplateVo activityManageVo, Model model) {
	  
	  String[] strings = activityManageVo.getChannelList().split(",");
	  
	  activityStatisService.deleteActivity(activityManageVo);
	  
	  AnctivityTempleVo templateName = activityTemplateService.getTemplateByTemplateName(activityManageVo.getTemplateId());
	  
	  Map<String,String> retest=null;
	  
	  for(String str : strings){
		  ActivityScheme selectSchemeName = vendingMachineService.selectSchemeName(str);
		  activityManageVo.setSchemeName(selectSchemeName.getSchemeName());
		  activityManageVo.setTemplateName(templateName.getTemplateName());
		  activityManageVo.setSchemeNo(str);
		  
		  retest = activityTemplateService.saveActivityConterTemplate(activityManageVo);
		  
	  }
	
	  return  retest;
  }
  
  
  @RequestMapping(value = "load")
  @ResponseBody
  public String edit(@RequestBody ActivityManageVo activityManage) {
	//List<ActivityManageVo>  activityManageVo = activityTemplateService.getActivityTemplateByCode(activityManage.getTemplateId());
	
	 AnctivityTempleVo	 anctivityTempleVo =activityTemplateService.getTemplateByTemplateName(activityManage.getTemplateId());
	
	 List<ActivityConterTemplateVo> activityManages =activityTemplateService.getActivityManageByTemplateName(activityManage.getTemplateId());

	 for(ActivityConterTemplateVo ss : activityManages){
		 
		ss.setTemplateName(anctivityTempleVo.getTemplateName());
		ss.setRemark(anctivityTempleVo.getRemark());
		ss.setOldTemplateId(anctivityTempleVo.getTemplateId());
		
	 }
	 
	  return   JsonMapper.toJsonString(activityManages);
  }
  
  
  
}
