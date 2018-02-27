package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
import com.huilian.hlej.hcf.service.UploadAdService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.UploadAdVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 上传广告控制器
 * @author luozb
 * @date 2017年2月24日 下午17:31:14
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/uploadAd")
public class UploadAdController  extends BaseController {
	  private final long fileSize = 1024*1024*50; 
	
	 @Autowired
	  private VendingMachineService vendingMachineService;
	 
	 @Autowired
	  private UploadAdService uploadAdService;
	 
	 @Autowired
	 FileHandler fileHandler;
	
	
	 @RequestMapping(value = {"list"})
	  public String list(UploadAdVo uploadAdVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  
		 Page<UploadAdVo> page = uploadAdService.getUploadAdPage(new Page<UploadAdVo>(request, response), uploadAdVo);
		  model.addAttribute("page", page);
		  model.addAttribute("channelList", vendingMachineService.getChannel());
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  uploadAdVo.setStartTime(null);
		  uploadAdVo.setEndTime(null);
		  model.addAttribute("uploadAdVo", uploadAdVo);
	    return "/hcoffee/vending/uploadAdList";
	  }
	 
	 
	 @RequestMapping(value = "edit")
	  @ResponseBody
	  public Map<String, String> edit(UploadAdVo uploadAdVo, Model model,MultipartFile video) throws IOException {
		 Map<String,String>  result  = new HashMap<String, String>();
		 try {
			if(video!=null && !"".equals(video.getOriginalFilename())){//只有重新选择了视频文件，才需要验证上传
				 result = validateFile(video);
				 if(result.size()>0){
					 return result;
				 }
				 uploadAdVo.setAdPath(uploadFile(video,new FileInfoVo()));
				 
			 }
			result = uploadAdService.updateUploadAdById(uploadAdVo);;
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
	  	return  result;
	  }
	 
	 @RequestMapping(value = "load")
	  @ResponseBody
	  public String load(@RequestBody UploadAdVo uploadAdVo) {
		 UploadAdVo  vo = uploadAdService.getUploadAd(uploadAdVo);
		  return   JsonMapper.toJsonString(vo);
	  }
	  
	  
	  @RequestMapping(value = "close")
	  @ResponseBody
	  public Map<String, String> close(@RequestBody UploadAdVo uploadAdVo) {
		  if(uploadAdVo.getAdStatus()==1){
			  uploadAdVo.setAdStatus(2);
		  }else{
			  uploadAdVo.setAdStatus(1);
		  }
		  Map<String,String>  result = uploadAdService.closeUploadAd(uploadAdVo);
		  return  result;
	  }
	 
	 @RequestMapping(value = "save")
	  @ResponseBody
	  public Map<String, String> save(UploadAdVo uploadAdVo, Model model,MultipartFile video) throws IOException {
		  Map<String,String>  result = validateFile(video);
		  if(result.size()>0){
			  return result;
		  }
		  UploadAdVo vo = uploadAdService.getUploadAd(uploadAdVo);
		  if(vo!=null){
			  result.put("code", "2");
			  result.put("msg", "此渠道对应的活动类型已经添加广告，请选择更新!");
			  return  result;
		  }
		  	
	  	try {
			uploadAdVo.setAdPath(uploadFile(video,new FileInfoVo()));
			result = uploadAdService.saveUploadAdVo(uploadAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
	  	return  result;
	  }
	 
	 public String  uploadFile(MultipartFile file,FileInfoVo fileInfo) throws Exception{
		  Map<String,String> map=new HashMap<>();
			try{
				if(fileInfo == null){
				    fileInfo = new FileInfoVo(); 
				}
				String fileName = fileInfo.getFileName();
				if(StringUtils.isEmpty(fileName)){
				    fileName = file.getOriginalFilename();
				    fileInfo.setFileName(fileName);
				}
	            String dataSource = fileInfo.getDataSource();
	            if(StringUtils.isEmpty(dataSource)){
	                fileInfo.setDataSource("hlej");
	            }
	            
	            fileInfo = fileHandler.upload(file.getBytes(), fileInfo);
	            String name = fileInfo.getFilePath();
	            if (name != null) {
	                map.put("code", "1");
	                map.put("msg", "成功");
	                map.put("path", name);
	                map.put("fileName" ,file.getOriginalFilename());
	                return name;
	            } else {
	                map.put("code", "-1");
	                map.put("msg", "失败");
	                map.put("path", "");
	                map.put("fileName" ,"");
	            }
			}catch (Exception e){
			    e.printStackTrace();
				map.put("code" ,"-1");
				map.put("msg","失败");
				map.put("path" ,"");
				map.put("fileName" ,"");
				throw new Exception(e);
			}
			return "";
	  }
	 
	 private Map<String,String>  validateFile(MultipartFile video){
		 Map<String,String>  result = new HashMap<String, String>();
		 if(video==null || "".equals(video.getOriginalFilename())){//验证是否选择了视频文件
		  		result.put("code", "2");
				result.put("msg", "视频文件为空，请选择!");
				return  result;
		  }else if(video.getSize()>this.fileSize){//验证文件大小
			    result.put("code", "2");
				result.put("msg", "视频文件过大，请重新选择!");
				return  result;
		  }else {//验证文件格式
			  final HashSet<String> set = new HashSet<String>() {
			         {
		               add("mp4"); 
		               add("flv"); 
		               add("avi"); 
		               add("rm"); 
		               add("rmvb"); 
		               add("wmv"); 
		             }
			     };
			  
			  String extName =  video.getOriginalFilename().substring(video.getOriginalFilename().indexOf(".") + 1).toLowerCase().trim();
			  if(!set.contains(extName)){
				  result.put("code", "2");
				  result.put("msg", "文件格式不对，请重新选择!");
				  return  result;
			  }
		  }
		 return result;
	 } 
	 
	 /**
	  * 导出广告数据
	  *
	  * @param uploadAdVo
	  * @param request
	  * @param response
	  * @param redirectAttributes
	  * @return
	  */
	 ///@RequiresPermissions("list")
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(UploadAdVo uploadAdVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "上传广告" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<UploadAdVo> page = uploadAdService.getUploadAdList(new Page(request, response), uploadAdVo);
	     List<Integer> widths = Arrays.asList(0,0, 2400, 0);
	     new HCFExportExcel("上传广告", UploadAdVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/uploadAd/list?repage";
	 }
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(UploadAdVo uploadAdVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "上传广告" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<UploadAdVo> page = uploadAdService.getUploadAdList(new Page(request, response, "str"), uploadAdVo);
			 List<Integer> widths = Arrays.asList(0,0, 2400, 0);
			 new HCFExportExcel("上传广告", UploadAdVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/uploadAd/list?repage";
	 }
	 
}
