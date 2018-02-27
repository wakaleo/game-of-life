package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huilian.hlej.hcf.common.utils.FileUploadUtil;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.WechatMaterialModelService;
import com.huilian.hlej.hcf.service.WechatMaterialService;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 二维码模板管理
 * 
 * @author ZhangZeBiao
 * @date 2017年12月7日 下午5:56:44
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/wechatMaterialModel")
public class WechatMaterialModelController {

	private static final String LIST = "/hcoffee/vending/center/wechatMaterialModelList";
	private static final String FBLIST = "/hcoffee/vending/center/machineWechatFaBuList";
	private static final String SBLIST = "/hcoffee/vending/center/wechatMachinesMagement";
	private static final String RELEASELIST = "/hcoffee/vending/center/wechatReleaseLists";
	private static final String SBRECORDLIST = "/hcoffee/vending/center/wechatRecordList";
	// private static final String DEALERFILEUPLOAD =
	// "/hcoffee/vending/center/dealerFileUpload";
	// private static final String DEALERIMAGEUPLOAD =
	// "/hcoffee/vending/center/dealerImageUpload";
	// private static final String DIVIDEACCOUNTLIST =
	// "/hcoffee/vending/center/divideAccountList";
	// private static final String SHOWDIVIDEACCOUNTLIST =
	// "/hcoffee/vending/center/showDivideAccountList";

	@Autowired
	private WechatMaterialModelService wechatMaterialModelService;
	@Autowired
	private WechatMaterialService wechatMaterialService;
	
	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	private DictionariesService dictionariesService;

	@Autowired
	FileHandler fileHandler;

	/**
	 * 查询所有模板
	 * 
	 * @param wechatMaterialModelVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(WechatMaterialModelVo wechatMaterialModelVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<WechatMaterialModelVo> page = wechatMaterialModelService
				.getWechatMaterialModelVoPage(new Page<WechatMaterialModelVo>(request, response), wechatMaterialModelVo);
		Page<WechatMaterialModelVo> pages = wechatMaterialModelService
				.getWechatMaterialModelVoPage(new Page<WechatMaterialModelVo>(request, response, "str"), wechatMaterialModelVo);
		List<WechatMaterialModelVo> modelList = wechatMaterialModelService.getWechatMaterialModelVoAll();
		List<WechatMaterialVo> wechatMaterialList = wechatMaterialService.getWechatMaterialVoAll();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("modelList", modelList);
		model.addAttribute("wechatMaterialList", wechatMaterialList);
		return LIST;
	}

	/**
	 * 保存二维码信息
	 * 
	 * @param wechatMaterialModelVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody WechatMaterialModelVo wechatMaterialModelVo, Model model) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			wechatMaterialModelService.saveWechatMaterialModelVo(wechatMaterialModelVo);
			result.put("code", "0");
			result.put("msg", "保存成功");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "保存失败");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存销商信息
	 * 
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uploadPic")
	@ResponseBody
	public Map<String, String> uploadPic(MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		String prizeUrl = "";
		if (file != null && file.getOriginalFilename() != "") {
			try {
				prizeUrl = uploadFile(file, new FileInfoVo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("prizeUrl", prizeUrl);
		return result;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 修改
	 * 
	 * @param lottoVendVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody WechatMaterialModelVo wechatMaterialModelVo, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		map = wechatMaterialModelService.updateWechatMaterialModelVo(wechatMaterialModelVo);
		return map;
	}

	/**
	 * 编辑页面跳转
	 * 
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody WechatMaterialModelVo wechatMaterialModelVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String modelNo = wechatMaterialModelVo.getModelNo();
		List<WechatMaterialModelVo> editPOJOList = wechatMaterialModelService.getWechatMaterialModelVoListByModelNo(wechatMaterialModelVo);
		int editSize = editPOJOList.size();
		map.put("editPOJOList", editPOJOList);
		map.put("editSize", editSize);
		return map;
	}

	/**
	 * 删除二维码模板
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/deleteVo")
	@ResponseBody
	public Map<String, String> deleteVo(@RequestBody WechatMaterialModelVo wechatMaterialModelVo) {
		
		Map<String, String> map = wechatMaterialModelService.deleteWechatMaterialModelVo(wechatMaterialModelVo);

		return map;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, headers = "Accept=application/json")
	// 处理上传的
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") CommonsMultipartFile file) throws IOException {
		PrintWriter out;
		boolean flag = false;
		if (file.getSize() > 0) {
			String path = "/assets/upload/files/";
			String uploadPath = request.getSession().getServletContext().getRealPath(path);
			try {
				// 文件上传的位置可以自定义
				flag = FileUploadUtil.uploadFile(file, request);
			} catch (Exception e) {
			}

		}
		out = response.getWriter();
		if (flag == true) {
			out.print("1");
		} else {
			out.print("2");
		}
	}

	public String uploadFile(MultipartFile file, FileInfoVo fileInfo) throws Exception {
		if (file.getSize() != 0) {
			Map<String, String> map = new HashMap<>();
			try {
				if (fileInfo == null) {
					fileInfo = new FileInfoVo();
				}
				String fileName = fileInfo.getFileName();
				if (StringUtils.isEmpty(fileName)) {
					fileName = file.getOriginalFilename();
					fileInfo.setFileName(fileName);
				}
				String dataSource = fileInfo.getDataSource();
				if (StringUtils.isEmpty(dataSource)) {
					fileInfo.setDataSource("hlej");
				}

				fileInfo = fileHandler.upload(file.getBytes(), fileInfo);
				String name = fileInfo.getFilePath();

				if (name != null) {
					map.put("code", "1");
					map.put("msg", "成功");
					map.put("path", name);
					map.put("fileName", file.getOriginalFilename());
					return name;
				} else {
					map.put("code", "-1");
					map.put("msg", "失败");
					map.put("path", "");
					map.put("fileName", "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "-1");
				map.put("msg", "失败");
				map.put("path", "");
				map.put("fileName", "");
				throw new Exception(e);
			}
		}
		return "";
	}

	/**
	 * 验证名称或编码是否存在 //可用为0，不可用为1
	 * 
	 * @param wechatMaterialModelVo
	 * @return
	 */
	@RequestMapping(value = "/checkNameOfNo")
	@ResponseBody
	public Map<String, Object> checkNameOfNo(@RequestBody WechatMaterialModelVo wechatMaterialModelVo) {
		String m = "";
		if (wechatMaterialModelVo.getModelName() != null) {
			m = "模板名称";
		} else if (wechatMaterialModelVo.getModelNo() != null) {
			m = "模板编码";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = wechatMaterialModelService.checkNameOfNo(wechatMaterialModelVo);
		if (flag) {
			map.put("code", "0");
			map.put("msg", m + "可用");
		} else {
			map.put("code", "1");
			map.put("msg", m + "不可用");
		}
		return map;
	}
	
	
	/**
	 * 编辑页面跳转
	 * 
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/showMore")
	@ResponseBody
	public Map<String, Object> showMore(@RequestBody WechatMaterialModelVo wechatMaterialModelVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String modelNo = wechatMaterialModelVo.getModelNo();
		List<WechatMaterialModelVo> morePOJOList = wechatMaterialModelService.getWechatMaterialModelVoListByModelNo(wechatMaterialModelVo);
		int moreSize = morePOJOList.size();
		map.put("morePOJOList", morePOJOList);
		map.put("moreSize", moreSize);
		return map;
	}
	
	/**
	 * 发布
	 * 
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/fabuList")
	public String fabuList(VendingMachineVo vendingMachineVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String wechatModelNo = vendingMachineVo.getWechatModelNo();
		Page<VendingMachineVo> page = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		Page<VendingMachineVo> pages = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
		model.addAttribute("page", page); 
		model.addAttribute("wechatModelNo", wechatModelNo); 
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
		List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
		model.addAttribute("communityList", vendingMachineService.getCommunity());
		model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
		model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
		model.addAttribute("vendingMachineVo", vendingMachineVo);
		return FBLIST;
	}
	
	/**
	 * 发布和取消发布
	 * @param id
	 */
	@RequestMapping(value = "/vendList")
	@ResponseBody
	public Map<String, String> vendList(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String wechatModelNo = request.getParameter("wechatModelNo");
			String arr_vendCode = request.getParameter("arr_vendCode");
			String arr_checked_vendCode = request.getParameter("arr_checked_vendCode");
			
			VendingMachineVo vendingMachineVo=new VendingMachineVo();
			String[] split = wechatModelNo.split(",");
			vendingMachineVo.setWechatModelNo(split[0]);
			if (arr_vendCode!=null&&arr_vendCode.length()>0) {
				vendingMachineVo.setArr_vendCode(arr_vendCode.split(","));
			}
			if (arr_checked_vendCode!=null&&arr_checked_vendCode.length()>0) {
				vendingMachineVo.setArr_checked_vendCode(arr_checked_vendCode.split(","));
			}
			vendingMachineService.updateVendByWechat(vendingMachineVo);
			map.put("code", "0");
			map.put("msg", "发布成功!");
		} catch (Exception e) {
			map.put("code", "1");
			map.put("msg", "发布失败!");
		}
		
		return map;
	}
	
	
	/**
	 * 机器模板管理界面
	 * @param vendingMachineVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"sbList"})
	  public String sbList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
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
	    return SBLIST;
	  }
	
	/**
	 * 设备模板记录页面
	 * @param vendingMachineVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"sbRecordList"})
	  public String sbRecordList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<VendingMachineVo> page = vendingMachineService.getVendingByVendcoed(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		  List<VendingMachineVo> list = page.getList();
		  for(VendingMachineVo sd :list){
			  //模板名称
			  if(sd.getWechatModelNo()!=null&&sd.getWechatModelNo().length()>0){
				  WechatMaterialModelVo wechatMaterialModelVo=new WechatMaterialModelVo();
				  wechatMaterialModelVo.setModelNo(sd.getWechatModelNo());
				  List<WechatMaterialModelVo> listVo = wechatMaterialModelService.getWechatMaterialModelVoListByModelNo(wechatMaterialModelVo);
				  
				  if(listVo!=null){
					  sd.setModelName(listVo.get(0).getModelName());
				  }
			  }
			  //旧模板名称
			  if(sd.getOldWechatModelNo()!=null&&sd.getOldWechatModelNo().length()>0){
				  WechatMaterialModelVo wechatMaterialModelVo=new WechatMaterialModelVo();
				  wechatMaterialModelVo.setModelNo(sd.getOldWechatModelNo());
				  List<WechatMaterialModelVo> listVo = wechatMaterialModelService.getWechatMaterialModelVoListByModelNo(wechatMaterialModelVo);
				  
				  if(listVo!=null){
					  sd.setOldModelName(listVo.get(0).getModelName());
				  }
			  }
		  }
		  
		  model.addAttribute("page", page);
		 
		  model.addAttribute("vendingMachineVo", vendingMachineVo);
		  return SBRECORDLIST;
	  }
	
	/**
	 * 已经发布的设备明细
	 * @param vendingMachineVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	  @RequestMapping(value = {"recortList"})
	  public String recortList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  if(vendingMachineVo.getWechatModelNo().isEmpty()){
			 
				 return "/hcoffee/vending/statis/eorr";
			  
		  }
		  
		  
		  vendingMachineVo.setWechatOptionType("1");
		  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByModelNo(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByModelNo(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
		 

		  List<VendingMachineVo> list = page.getList();
		  String wechatModelNo=null;
		 
		  if (!list.isEmpty()) {
				 wechatModelNo=list.get(0).getWechatModelNo();
		 }
		  
		  model.addAttribute("actionStr", "recortList"); 
		  model.addAttribute("wechatModelNo", wechatModelNo); 
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
		  return RELEASELIST;
	  }
	  /**
	   * 撤销记录
	   * @param vendingMachineVo
	   * @param request
	   * @param response
	   * @param model
	   * @return
	   */
	  @RequestMapping(value = {"revokeList"})
	  public String revokeList(VendingMachineVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  if(vendingMachineVo.getOldWechatModelNo().isEmpty()){
				 
				 return "/hcoffee/vending/statis/eorr";
			  
		  }
		  
		  
		  vendingMachineVo.setWechatOptionType("2");
		  Page<VendingMachineVo> page = vendingMachineService.getVendingMachineByModelNo(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		  Page<VendingMachineVo> pages = vendingMachineService.getVendingMachineByModelNo(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
		 

		  List<VendingMachineVo> list = page.getList();
		  String wechatModelNo=null;
		 if (!list.isEmpty()) {
			 wechatModelNo=list.get(0).getWechatModelNo();
		 }
		  
		  model.addAttribute("actionStr", "revokeList"); 
		  model.addAttribute("wechatModelNo", wechatModelNo); 
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
		  return RELEASELIST;
	  }
}
