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

import com.alibaba.druid.util.StringUtils;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.CommunityService;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;


/**
 * 社区
 *
 * @author luozb
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/community")
public class CommunityController extends BaseController {

  @Autowired
  private CommunityService communityService;

  @RequestMapping(value = {"list"})
  public String list(CommunityVo communityVo,HttpServletRequest request, HttpServletResponse response, Model model) {
	  Page<CommunityVo> page = communityService.getCommunityPage(new Page<CommunityVo>(request, response), communityVo);
	  model.addAttribute("page", page);
	  model.addAttribute("communityVo", communityVo);
    return "/hcoffee/vending/communityList";
  }

  @RequestMapping(value = "save")
  @ResponseBody
  public Map<String, String> save(@RequestBody CommunityVo communityVo) throws IOException {
	  Map<String,String>  result =  new HashMap<String, String>();
	  result = this.validateVO(communityVo.getCommunityId(), null);
	  if(result.size()>0){
		  return result;
	  }
	  result = this.validateVO(null, communityVo.getCommunityName());
	  if(result.size()>0){
		  return result;
	  }
	  communityVo.setDataSource(3);//数据来源默认设为3
	  communityVo.setDataStatus(0);//状态默认设为正常  
  	result = communityService.saveCommunity(communityVo);;
  	return  result;
  }
  
  @RequestMapping(value = "update")
  @ResponseBody
  public Map<String, String> update(@RequestBody CommunityVo communityVo) throws IOException {
	 Map<String,String>  result  = new HashMap<String, String>();
	 CommunityVo  vo = communityService.getCommunityById(communityVo);
	 if(!vo.getCommunityId().equals(communityVo.getCommunityId())){//编码发生了更改
		 result = this.validateVO(communityVo.getCommunityId(), null);
		  if(result.size()>0){
			  return result;
		  }
	 }
	 if(!vo.getCommunityName().equals(communityVo.getCommunityName())){//名称发生了更改
		 result = this.validateVO(null, communityVo.getCommunityName());
		  if(result.size()>0){
			  return result;
		  }
	 }
	 
  	result = communityService.updateCommunityById(communityVo);;
  	return  result;
  }
  
  @RequestMapping(value = "updateCommunityStatus")
  @ResponseBody
  public Map<String, String> updateCommunityStatus(@RequestBody CommunityVo communityVo) throws IOException {
	  if(communityVo.getDataStatus()==0){
		  communityVo.setDataStatus(1);
	  }else{
		  communityVo.setDataStatus(0);
	  }
	  Map<String,String>  result  = new HashMap<String, String>();
  	 result = communityService.updateCommunityStatus(communityVo);;
  	return  result;
  }
  
  @RequestMapping(value = "load")
  @ResponseBody
  public String load(@RequestBody CommunityVo communityVo) {
	  CommunityVo  vo = communityService.getCommunityById(communityVo);
	  return   JsonMapper.toJsonString(vo);
  }
  
  /**
   *验证编码或者名称是否重复
   */
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
  
  /**
  * 导出社区数据
  *
  * @param communityVo
  * @param request
  * @param response
  * @param redirectAttributes
  * @return
  */
 ///@RequiresPermissions("list")
 @RequestMapping(value = "export", method = RequestMethod.POST)
 public String exportFile(CommunityVo communityVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
   try {
     String fileName = "社区数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
     Page<CommunityVo> page = communityService.getCommunityList(new Page(request, response), communityVo);
     List<Integer> widths = Arrays.asList(3600,1, 2400, 1);
     new HCFExportExcel("社区数据", CommunityVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
     return null;
   } catch (Exception e) {
     addMessage(redirectAttributes, "导出社区数据失败！失败信息：" + e.getMessage());
   }
   return "redirect:" + adminPath + "/hcf/community/list?repage";
 }
 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
 public String exportQbFile(CommunityVo communityVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	 try {
		 String fileName = "社区数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		 Page<CommunityVo> page = communityService.getCommunityList(new Page(request, response, "str"), communityVo);
		 List<Integer> widths = Arrays.asList(3600,1, 2400, 1);
		 new HCFExportExcel("社区数据", CommunityVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		 return null;
	 } catch (Exception e) {
		 addMessage(redirectAttributes, "导出社区数据失败！失败信息：" + e.getMessage());
	 }
	 return "redirect:" + adminPath + "/hcf/community/list?repage";
 }

  
}
