package com.huilian.hlej.hcf.web;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.VendStoreService;
import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.VendStoreVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 售货机版本控制器
 * @author xiekangjian
 * @date 2016年12月30日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendStore")
public class vendStoreController extends BaseController{
	
	 
	 @Autowired
	  private VendStoreService VendStoreService;
	
	 @RequestMapping(value = {"list"})
	  public String list(VendStoreVo vendStoreVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<VendStoreVo> page = VendStoreService.getVendStorePage(new Page<VendStoreVo>(request, response), vendStoreVo);
		  model.addAttribute("page", page);
		  model.addAttribute("vendStoreVo", vendStoreVo);
		  vendStoreVo.setStartTime(null);
		  vendStoreVo.setEndTime(null);
	    return "/hcoffee/vending/vendStoreList";
	  }
	 
	  @RequestMapping(value = "save")
	  @ResponseBody
	 public  Map<String, String>  save(@RequestBody VendStoreVo vendStoreVo ,Model model) throws IOException {  
		  Map<String, String> result = new HashMap<String, String>();
//		  VendStoreVo vendStore =VendStoreService.getVendStore(vendStoreVo.getVendCode());
//		  if(vendStore!=null){
//				if(vendStore.getVendCode()!=null){
//					 result.put("code", "1");
//						result.put("msg", "该广告编码已经存在"); 
//					 return result;
//				}
//			}
//			if(vendStore!=null){
//				if(vendStore.getGoodsName()!=null){
//					  result.put("code", "1");
//						result.put("msg", "该商品名称已经存在"); 
//						 return result;
//						
//					}
//			}
		  
		  
		 try {
			
			result = VendStoreService.saveVendStore(vendStoreVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
	  	return  result;
	 }
	 
	 
	  @RequestMapping(value = "update")
	  @ResponseBody
	  public String update( @RequestBody VendStoreVo VendStoreVo, Model model) {
		
			
		  Map<String,String>  result = VendStoreService.updateVendStore(VendStoreVo);
		  return   JsonMapper.toJsonString(result);
	  }
	 
	 
	  @RequestMapping(value = "edit")
	  @ResponseBody
	  public String edit(@RequestBody String id) {
		  VendStoreVo  vendStoreVo = VendStoreService.getVendStore(id);
		  return   JsonMapper.toJsonString(vendStoreVo);
	  }
	  

	  
	  @RequestMapping(value = "deleted")
	  @ResponseBody
	  public String deleted(@RequestBody String id) {
		  Map<String,String>  result = VendStoreService.deleteVendStore(id);
		  return   JsonMapper.toJsonString(result);
	  }
	  
	  

	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(VendStoreVo vendStoreVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "售货机版本" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<VendStoreVo> page = VendStoreService.getVendStorePage(new Page<VendStoreVo>(request, response), vendStoreVo);
	     List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400,2400,0, 0);
	     new HCFExportExcel("售货机版本", VendStoreVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/vendStore/list?repage";
	 }
	 
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(VendStoreVo vendStoreVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "售货机版本" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<VendStoreVo> page = VendStoreService.getVendStorePage(new Page<VendStoreVo>(request, response, "str"), vendStoreVo);
			 List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400,2400,0, 0);
			 new HCFExportExcel("售货机版本", VendStoreVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/vendStore/list?repage";
	 }
	 
	
}
