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
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.LeadMangementService;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.LeadManagementVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 商机管理
 * 
 * @author yangweichao
 * @version 1.0 2017-8-24 11:00:04
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/LeadManagement")
public class LeadManagementController extends BaseController {
	
	@Autowired
	private LeadMangementService LeadMangementService;
	
	@Autowired
	private DictionariesService dictionariesService;
	
	 @RequestMapping(value = {"list"})
	  public String list(LeadManagementVo LeadManagementVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<LeadManagementVo> page = LeadMangementService.getCommunityPage(new Page<LeadManagementVo>(request, response), LeadManagementVo);
		  List<DictionariesVo> stateList = dictionariesService.getStateList();
		  model.addAttribute("page", page);
		  model.addAttribute("LeadManagementVo", LeadManagementVo);
		  model.addAttribute("stateList", stateList);
	    return "/hcoffee/vending/center/LeadM";
	  }
	 
	 
	  @RequestMapping(value = "load")
	  @ResponseBody
	  public String load(@RequestBody LeadManagementVo leadManagementVo,Model model) {
		  LeadManagementVo  vo = LeadMangementService.getLeadManagementById(leadManagementVo);
		  List<LeadManagementVo>  list = LeadMangementService.getOperationHistory(leadManagementVo.getId()!=null?Integer.parseInt(leadManagementVo.getId()):0);
		  vo.setList(list);
		  return JsonMapper.toJsonString(vo);
	  }
	  
	 
	  @RequestMapping(value = "update")
	  @ResponseBody
	  public Map<String, String> update(@RequestBody LeadManagementVo leadManagementVo,Model model) throws IOException {
		Map<String,String>  result  = new HashMap<String, String>();
	  	result = LeadMangementService.updateLeadMangementById(leadManagementVo);
	  	return  result;
	  }
	  
	  
	  
	  /**
	   * 导出商机管理数据
	   *
	   * @param communityVo
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   */
	  ///@RequiresPermissions("list")
	  @RequestMapping(value = "export", method = RequestMethod.POST)
	  public String exportFile(LeadManagementVo leadManagementVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    try {
	      String fileName = "商机管理数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	      Page<LeadManagementVo> page = LeadMangementService.getLeadManagementList(new Page(request, response), leadManagementVo);
	      List<Integer> widths = Arrays.asList(3600,1, 2400, 1);
	      new HCFExportExcel("商机管理数据", LeadManagementVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	      return null;
	    } catch (Exception e) {
	      addMessage(redirectAttributes, "导出商机数据失败！失败信息：" + e.getMessage());
	    }
	    
	    return "redirect:" + adminPath + "/hcf/LeadManagement/list?repage";
	  }
	  
	  
	  /*
	  *//**
	   *验证编码或者名称是否重复
	   *//*
	  private Map<String,String>  validateVO(String communityId,String communityName){
			 Map<String,String>  result = new HashMap<String, String>();
			 CommunityVo cu = new CommunityVo();
			 if(communityId!=null && !"".equals(communityId)){
				 cu.setCommunityId(communityId);
			 }else{
				 cu.setCommunityName(communityName);
			 }
			  CommunityVo vo = communityService.getCommunity(cu);
			  if(vo!=null){
				  if(StringUtils.isEmpty(vo.getCommunityId())){
					  result.put("code", "2");
					  result.put("msg", "名称已存在，请重新输入!");
					  return  result;
				  }else{
					  result.put("code", "2");
					  result.put("msg", "编码已存在，请重新输入!");
					  return  result;
				  }
				  
			  }
			 return result;
		 } 
	  */
	  
	  
	 
}
