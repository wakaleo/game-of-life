
package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.entity.ActivityScheme;
import com.huilian.hlej.hcf.service.ActivityStatisService;
import com.huilian.hlej.hcf.service.ActivityTemplateService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 活动管理
 * @author yangbo
 * @date 2017年3月3日 下午2:30:02
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/activityTemplatet")
public class ActivityTemplateController extends BaseController {

	 @Autowired
	  private ActivityTemplateService activityTemplateService;
	 @Autowired
	  private VendingMachineService vendingMachineService;
	 @Autowired
	  private ActivityStatisService activityStatisService;
	
	 @RequestMapping(value = {"list"})
	  public String list(ActivityConterTemplateVo anctivityTempleVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<ActivityConterTemplateVo> page = activityTemplateService.getActivityTemplateList(new Page<ActivityConterTemplateVo>(request, response), anctivityTempleVo);
			List<List<ActivityConterTemplateVo>>result = new ArrayList<List<ActivityConterTemplateVo>>();
		  Map<String,List<ActivityConterTemplateVo>> map = new HashMap<String,List<ActivityConterTemplateVo>>();
			List<ActivityConterTemplateVo>list =   page.getList();
			
			for(ActivityConterTemplateVo vo:list){
				List<ActivityConterTemplateVo> temp = map.get(vo.getTemplateId());
				
			   if(temp==null){
				   temp = new ArrayList();
			   }
			   temp.add(vo);
			   map.put(vo.getTemplateId(), temp);
				
		  }
			for(String str :map.keySet()){
				result.add(map.get(str));
			}
			
			
			List<ActivityConterTemplateVo> sies=new ArrayList<ActivityConterTemplateVo>();
			
			
			for(List<ActivityConterTemplateVo> ss :result){
			   for(ActivityConterTemplateVo ssa : ss){
				   sies.add(ssa);
				   
			   }
				
			}
	    	int sizes = sies.size();
		  model.addAttribute("page", page);
		  model.addAttribute("sizes", sizes);
		  model.addAttribute("result", result);
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  model.addAttribute("activityTypeList",vendingMachineService.getActivityType() ); 
		  model.addAttribute("anctivityTempleVo", anctivityTempleVo);
		  
		  model.addAttribute("activityTypeMaxImg", vendingMachineService.getActivityTypeMaxImg());
		  model.addAttribute("activityTypeMinImg", vendingMachineService.getActivityTypeMinImg());
		  model.addAttribute("activityTypes", vendingMachineService.getActivityTypes());
		  model.addAttribute("activityTypeDai", vendingMachineService.getActivityTypeDai());
		  model.addAttribute("anctivityTempleVo", anctivityTempleVo);
	    return "/hcoffee/vending/statis/anctivityTempleMangent";
	  }
	
	 
	  @RequestMapping(value = "delete")
	  @ResponseBody
	  public Map<String, String> delete(@RequestBody ActivityConterTemplateVo activityConterTemplateVo) throws IOException {
			/*ActivityManageVo activity = new ActivityManageVo();
			ActivityConterTemplateVo activityManage =new ActivityConterTemplateVo();
		  
		  activityManage.setTemplateId(activityManageVo.getTemplateId());*/
		  Map<String,String>  result  = new HashMap<String, String>();
		  AnctivityTempleVo sds=new AnctivityTempleVo();
		  //Map<String, String>	 result = activityStatisService.deleteActivity(activityConterTemplateVo);
		  
		  List<VendingMachineVo> lists = activityTemplateService.selectVendByTemplateId(activityConterTemplateVo.getTemplateId());
		  
		if(!lists.isEmpty()){
			   result.put("code", "1");
				result.put("msg", "该模板正在使用当中......不能删除....."); 
			 return result;
			
		}else{
			 sds.setTemplateId(activityConterTemplateVo.getTemplateId());
			 result = activityTemplateService.deteTemplateByTemplateId(sds);
		}
		  
	  	
	  	/*List<VendingMachineVo> vending=activityTemplateService.selectVendByTemplateId(activityConterTemplateVo.getTemplateId());
	  	
	  	VendingMachineVo vend=new VendingMachineVo();
	  	if(!vending.isEmpty()){
	  		
	  		 for(VendingMachineVo sss : vending ){
		  		   
		  		 String vendCode = sss.getVendCode();
		  		vend.setVendCode(vendCode);
		  		vend.setOldTemplateId(null);
		  		vend.setTemplateId(null);
		  		vend.setOptionType(null);
		  		vend.setFabuRecort(null);
		  		activityTemplateService.updateVendingByCode(vend);
		  		   
		  	   }
	  	}*/
	  	
	  	
	  	return  result;
	  }
	  
	  
	  @RequestMapping(value = "deletes")
	  @ResponseBody
	  public Map<String, String> deletes(@RequestBody String fist,String id, String templateId){
		  Map<String,String>  result  = new HashMap<String, String>();
		  VendingMachineVo   vendingMachineVo =new VendingMachineVo();
		  ActivityManageVo activityManageVo=new ActivityManageVo();
		  
		  if((!templateId.isEmpty())){
			  vendingMachineVo.setOptionType("2");
			  vendingMachineVo.setOldTemplateId(templateId);
			  vendingMachineVo.setTemplateId(templateId);
			  vendingMachineVo.setId(id);
		  }
		  result = activityStatisService.deleteMachine(vendingMachineVo);
		  return  result;
	  }
	  
	  

	/*	 @RequestMapping(value = {"addList"})
		  public String addList(ActivityManageVo anctivityTempleVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			 Page<ActivityManageVo> page = activityTemplateService.getActivityTemplateList(new Page<ActivityManageVo>(request, response), anctivityTempleVo);

			 String sd="哈哈";
			  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
			  model.addAttribute("sd", sd);
			  model.addAttribute("activityTypeList",vendingMachineService.getActivityType() ); 
			  model.addAttribute("anctivityTempleVo", anctivityTempleVo);
		    return "/hcoffee/vending/statis/anctivityTemple";
		  }*/
		 
		/* @RequestMapping(value = {"addLists"})
		  public String addListw(ActivityManageVo anctivityTempleVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			 Page<ActivityManageVo> page = activityTemplateService.getActivityTemplateList(new Page<ActivityManageVo>(request, response), anctivityTempleVo);
			  model.addAttribute("activityTypeMaxImg", vendingMachineService.getActivityTypeMaxImg());
			  model.addAttribute("activityTypeMinImg", vendingMachineService.getActivityTypeMinImg());
			  model.addAttribute("activityTypes", vendingMachineService.getActivityTypes());
			  model.addAttribute("activityTypeDai", vendingMachineService.getActivityTypeDai());
			  model.addAttribute("anctivityTempleVo", anctivityTempleVo);
		    return "/hcoffee/vending/statis/anctivityTemple";
		  }
		
		 @RequestMapping(value = {"addListf"})
		 public String addLists(ActivityManageVo anctivityTempleVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			 Page<ActivityManageVo> page = activityTemplateService.getActivityTemplateList(new Page<ActivityManageVo>(request, response), anctivityTempleVo);
			 
			 model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
			 model.addAttribute("activityTypeList",vendingMachineService.getActivityType() ); 
			 model.addAttribute("anctivityTempleVo", anctivityTempleVo);
			 
			 
			 return "/hcoffee/vending/statis/addActivit";
		 }*/
		 
		 
		/* @RequestMapping(value = "selectSchemeName")
		
		  public String selectSchemeName(@ RequestBody ActivityManageVo anctivityTempleVo, Model model) {
			 ActivityScheme schemeNames = vendingMachineService.selectSchemeName(anctivityTempleVo.getSchemeNo());
			 return JsonMapper.toJsonString(schemeNames);
		  }*/
		 
		  @RequestMapping(value = "selectSchemeName")
		  @ResponseBody
		  public String selectCity(@RequestBody ActivityManageVo anctivityTempleVo, Model model) {
			  ActivityScheme cityByCityId = vendingMachineService.selectSchemeName(anctivityTempleVo.getSchemeNo());
			  
			  return   JsonMapper.toJsonString(cityByCityId);
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
				ss.setId(anctivityTempleVo.getId());
				
			 }
			 
 			  return   JsonMapper.toJsonString(activityManages);
		  }
		  
		  //发布
		  @RequestMapping(value = {"fabuList"})
		  public String fabuList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			 String[] split = vendingMachineVo.getTemplateId().split(",");
			  for(String str : split){
				  
				  vendingMachineVo.setTemplateId(str);
			  }
			  
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			  model.addAttribute("page", page); 
			  model.addAttribute("templateId", vendingMachineVo.getTemplateId()); 
			  model.addAttribute("pages", pages.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
		    return "/hcoffee/vending/statis/machineFaBuList";
		  }
		  
		  
		@RequestMapping(value = "vendList")
		  @ResponseBody
		  public String batchUpgrade(@RequestBody String fist,String vendCodeList, String templateId, Model model) {
			//  Map<String,String>  result = vendingMachineService.batchUpgrade(vendingMachineVo.getVendCodeList());
			  //  String vendCodeLists = URLEncoder.encode(vendCodeList, "UTF-8");
			  //  String templateIds = URLEncoder.encode(templateId, "UTF-8");
			    
			String decode = URLDecoder.decode(URLDecoder.decode(vendCodeList));
			//  String template = URLDecoder.decode(URLDecoder.decode(templateIds),"UTF-8");
             String[] vendList = decode.split(",");
			
			Map<String, String> reulte = null;
			
			VendingMachineVo selectByTemplateId = null;
			
			 for(String str : vendList ){
				 selectByTemplateId=activityTemplateService.selectByTemplateId(str);
				
				 if(selectByTemplateId!=null){
					 
					 if(selectByTemplateId.getTemplateId()!=null && !templateId.equals(selectByTemplateId.getTemplateId())){
						 selectByTemplateId.setVendCode(str);
						 selectByTemplateId.setOldTemplateId(selectByTemplateId.getTemplateId());
						 selectByTemplateId.setTemplateId(templateId);
						 selectByTemplateId.setOptionType("1");
						 selectByTemplateId.setFabuRecort("1");
						 reulte = vendingMachineService.updateVendingByCode(selectByTemplateId);
						 
					 }else{
						 selectByTemplateId.setFabuRecort("1");
						 selectByTemplateId.setOptionType("1");
						 selectByTemplateId.setTemplateId(templateId);
						 selectByTemplateId.setVendCode(str);
						 reulte=vendingMachineService.updateVendingByCode(selectByTemplateId);
					 }
					 
				 }
				 
			 }
		 
			  return   JsonMapper.toJsonString(reulte);
		  }
		  
		 
		  
	  //已经发布的设备明细
		  @RequestMapping(value = {"alwrsList"})
		  public String alwrsList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			  
			  vendingMachineVo.setOptionType("1");
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			
			  List<VendingMachineVo> list = page.getList();
			  String templateId=null;
			 
			  for(VendingMachineVo sa : list){
				  
				 templateId = sa.getTemplateId();
			  }
			  model.addAttribute("templateId", templateId);
			  model.addAttribute("page", page); 
			  model.addAttribute("pages", pages.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
		    return "/hcoffee/vending/statis/releaseList";
		  }
		  
		  //已经发布的设备明细
		  @RequestMapping(value = {"alwrsLists"})
		  public String alwrsLists(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			  if(vendingMachineVo.getTemplateId().isEmpty()){
				 
					 return "/hcoffee/vending/statis/eorr";
				  
			  }
			  
			  
			  vendingMachineVo.setFabuRecort("1");
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			 

			  List<VendingMachineVo> list = page.getList();
			  String templateId=null;
			 
			  for(VendingMachineVo sa : list){
				  
				  templateId = sa.getTemplateId();
			  }
			  
			  model.addAttribute("templateId", templateId); 
			  model.addAttribute("page", page); 
			  model.addAttribute("pages", pages.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
			  return "/hcoffee/vending/statis/releaseLists";
		  }
		  //撤销设备明细
		  @RequestMapping(value = {"revomList"})
		  public String revomList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			  
			  if(vendingMachineVo.getOptionType().isEmpty() || vendingMachineVo.getTemplateId().isEmpty()){
				  return "/hcoffee/vending/statis/eorr";
				  
			  }
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			  model.addAttribute("page", page); 
			  model.addAttribute("pages", pages.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
			  return "/hcoffee/vending/statis/revomList";
		  }
		  
		  //发布记录的设备
		  @RequestMapping(value = {"recortList"})
		  public String recortList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			  vendingMachineVo.setFabuRecort("1");
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  
			  
			  
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			 
			  List<VendingMachineVo> list = page.getList();
			  Date updateTime = null;
			  String templateId=null;
			 
			  for(VendingMachineVo sa : list){
				  
				 updateTime = sa.getUpdateTime();
				 templateId = sa.getTemplateId();
			  }
			  
			  
			  model.addAttribute("updateTime", updateTime); 
			  model.addAttribute("templateId", templateId); 
			  model.addAttribute("pages", pages.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
			  return "/hcoffee/vending/statis/recortList";
		  }
		  //撤销记录
		  @RequestMapping(value = {"revokeList"})
		  public String revokeList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
			  vendingMachineVo.setOptionType("2");
			  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByOptionType(new Page<VendingMachineVo>(request, response), vendingMachineVo);
			  
			  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByTemplateId(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			  
			  List<VendingMachineVo> list = page.getList();
			  Date updateTime = null;
			  String templateId=null;
			  String optionType=null;
			 
			  for(VendingMachineVo sa : list){
				  
				 updateTime = sa.getUpdateTime();
				 templateId = sa.getTemplateId();
				 optionType = sa.getOptionType();
				 
				 
			  }
			  
			  
			  model.addAttribute("updateTime", updateTime); 
			  model.addAttribute("templateId", templateId); 
			  model.addAttribute("optionType", optionType); 
			  model.addAttribute("pages", page.getList().size());
			  model.addAttribute("channelList", vendingMachineService.getChannel());
			  model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
			  List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
			  model.addAttribute("communityList", vendingMachineService.getCommunity());
			  model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
			  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应")));
			  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
			  vendingMachineVo.setStartTime(null);
			  vendingMachineVo.setEndTime(null);
			  model.addAttribute("vendingMachineVo", vendingMachineVo);
			  return "/hcoffee/vending/statis/revokeList";
		  }
		  
		  
		 @RequestMapping(value = "save")
		  @ResponseBody
		  public  Map<String,String>  save(@RequestBody ActivityConterTemplateVo activityConterTemplateVo, Model model) {
			  
			  String[] strings = activityConterTemplateVo.getChannelList().split(",");
			  Map<String,String> retest=new HashMap<String, String>();
			  
			
			  AnctivityTempleVo anctivityTempleVo =new AnctivityTempleVo();
			  for(String str : strings){
				  ActivityManageVo seleByschemeNo = activityTemplateService.seleByschemeNo(str);
				  activityConterTemplateVo.setSchemeName(seleByschemeNo.getSchemeName());
				  
				  activityConterTemplateVo.setSchemeNo(str);
				
				  activityTemplateService.saveActivityConterTemplate(activityConterTemplateVo);
				  
			  }
			  
			 /* 
			    StringBuffer sb=new StringBuffer();
			   if(sb.toString()!=null){
				  retest.put("code", "1");
				  retest.put("msg", "该活动"+sb.toString()+"已经添加了其他模板");
				  return  retest;
			  }*/
			  
			  AnctivityTempleVo anctivityTemple = activityTemplateService.getTemplateName(activityConterTemplateVo.getTemplateId());
			  
			  AnctivityTempleVo  anctivityTemples =activityTemplateService.getTemplateByName(activityConterTemplateVo.getTemplateName());
			  
			  
			  if(anctivityTemple!=null){
				  retest.put("code", "2");
				  retest.put("msg", "该活动模板编码已存在");
				  return  retest;
			  }
			  
			  if(anctivityTemples!=null){
				  retest.put("code", "2");
				  retest.put("msg", "该活动模板名称已存在");
				  return  retest;
			  }
			  
			  anctivityTempleVo.setTemplateName(activityConterTemplateVo.getTemplateName());
			  anctivityTempleVo.setTemplateId(activityConterTemplateVo.getTemplateId());
			  anctivityTempleVo.setRemark(activityConterTemplateVo.getRemark());
			  retest= activityTemplateService.saveTemPlate(anctivityTempleVo);
			  
			  return  retest;
		  }
		  
		  
		  
	  @RequestMapping(value = "update")
		  @ResponseBody
		  public String update(@RequestBody ActivityConterTemplateVo activityManageVo, Model model) {
		  
		  
		  String id =activityManageVo.getId();
		  String TemplateId=activityManageVo.getTemplateId();
		   
		  String[] strings = activityManageVo.getChannelList().split(",");
		  
		  Map<String, String> retest = null;
		  activityManageVo.setTemplateId(activityManageVo.getOldTemplateId());
		  activityStatisService.deleteActivity(activityManageVo);
		  
		//  AnctivityTempleVo templateName = activityTemplateService.getTemplateByTemplateName(activityManageVo.getTemplateId());
		  
		  
		  for(String str : strings){
			  ActivityScheme selectSchemeName = vendingMachineService.selectSchemeName(str);
			  activityManageVo.setSchemeName(selectSchemeName.getSchemeName());
			  activityManageVo.setTemplateName(activityManageVo.getTemplateName());
			  activityManageVo.setSchemeNo(str);
			  activityManageVo.setTemplateId(TemplateId);
			  retest = activityTemplateService.saveActivityConterTemplate(activityManageVo);
			  
		  }
		   
		  
		//   AnctivityTempleVo dsd= activityTemplateService.getTemplateName(activityManageVo.getTemplateId());
		   
		   
		   
		   AnctivityTempleVo anctivityTempleVo=new AnctivityTempleVo();
		   anctivityTempleVo.setId(id);
		   anctivityTempleVo.setRemark(activityManageVo.getRemark());
		   anctivityTempleVo.setTemplateName(activityManageVo.getTemplateName());
		   
		   anctivityTempleVo.setTemplateId(activityManageVo.getTemplateId());
		   activityTemplateService.updateByTemplate(anctivityTempleVo);
		   
		   return   JsonMapper.toJsonString(retest);
		   
/*		   
		   List<String> olderSchemeNo=new ArrayList<>();
		   List ageSchemeNo = Arrays.asList(strings);
		   
		   for(ActivityManageVo sd : activityManages){
			   olderSchemeNo.add(sd.getSchemeNo());
			   }
		    Map<String,String>  result= compare(olderSchemeNo,ageSchemeNo,activityManageVo.getTemplateId());
*/			  
		  }
	   
	   
/*	   
	   public  <String extends Comparable<String>> Map<String, String> compare(List<String> olderSchemeNo, List<String> ageSchemeNo,String templateId) {
		   Map<java.lang.String, java.lang.String> result=null;
		   for(int i=0;i<olderSchemeNo.size();i++){
				ActivityManageVo seleByschemeNo = activityTemplateService.seleByschemeNo((java.lang.String) ageSchemeNo.get(i));
		     if(!olderSchemeNo.get(i).equals(ageSchemeNo.get(i))) {
			
				seleByschemeNo.setTemplateId((java.lang.String) templateId);
				 result = activityTemplateService.updateBySchemno(seleByschemeNo);
			}
		       return (Map<String, String>) result;
		   }
		   return null;
		 }*/
		  
}
