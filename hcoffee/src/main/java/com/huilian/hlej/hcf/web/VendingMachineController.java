package com.huilian.hlej.hcf.web;

 
import java.util.ArrayList;
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
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.entity.LngLat;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.CountyVo;
import com.huilian.hlej.hcf.vo.CtiyVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;


/**
 * 自动贩卖机
 *
 * @author liujian
 * @version 2016-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingMachine")
public class VendingMachineController extends BaseController {

  @Autowired
  private VendingMachineService vendingMachineService;

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
	  model.addAttribute("equipSupplyList", Arrays.asList(new EquipSupplyVo("100","凯欣达供应"),new EquipSupplyVo("111","便捷神供应"),new EquipSupplyVo("112","零壹供应"),new EquipSupplyVo("113","乐科供应")));
	  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords());
	  vendingMachineVo.setStartTime(null);
	  vendingMachineVo.setEndTime(null);
	  model.addAttribute("vendingMachineVo", vendingMachineVo);
    return "/hcoffee/vending/vendingMachineList";
  }
  @RequestMapping(value = {"tuBiaoList"})
  public String tuBiaoList(LngLat lngLat,HttpServletRequest request, HttpServletResponse response, Model model) {
	  Page<LngLat> pages = vendingMachineService.getTuBiaoList(new Page<LngLat>(request, response, "str"), lngLat);
	  List<LngLat> list = pages.getList();
	  String piont = null;
	  
	  StringBuffer piontList =new StringBuffer();
	  for(LngLat ss : list){
			 piont=ss.getLang() +","+ ss.getLat();
			 piontList.append(piont);
			 piontList.append(",");
	  }
	  model.addAttribute("page", pages);
	  model.addAttribute("piontList", piontList.toString());
	  return "/hcoffee/vending/aLLtubiaoList";
  }
  @RequestMapping(value = {"selectBiaoList"})
  public String selectBiaoList(LngLat lngLat,HttpServletRequest request, HttpServletResponse response, Model model) {
	 
	  return "/hcoffee/vending/selectTuBiao";
  }


  @RequestMapping(value = "upgradeRecord")
  public String upgradeList(AppUpgradeRecordVo appUpgradeRecordVo,HttpServletRequest request, HttpServletResponse response, Model model ) {
	  Page<AppUpgradeRecordVo> page = vendingMachineService.getAppUpgradeRecordPage(new Page<AppUpgradeRecordVo>(request, response), appUpgradeRecordVo);
	  model.addAttribute("upgradeRecordPage", page);
	  model.addAttribute("vendCode", appUpgradeRecordVo.getVendCode());
	  return "/hcoffee/vending/upgradeRecordList";
  }
  
  
  
  @RequestMapping(value = "updateTubiao")
  public String updateTubiao( VendingMachineVo vendingMachine, HttpServletRequest request, HttpServletResponse response, Model model) {
	  /*if("".equals(vendingMachine.getVendCode())){
		  
		  return "/hcoffee/vending/upgradeRecordList";
	  }
	  
	  Map<String,String>  result = vendingMachineService.updateVendingMachine(vendingMachine);*/
	  model.addAttribute("vendingMachine", vendingMachineService.getVendingMachineByCode(vendingMachine.getVendCode()));
	  VendingMachine vendingMachineByCode = vendingMachineService.getVendingMachineByCode(vendingMachine.getVendCode());
	  
	  return "/hcoffee/vending/baiDuDiTu";
  }
  
  @RequestMapping(value = "save")
  @ResponseBody
  public  Map<String,String>  save(@RequestBody VendingMachine vendingMachine, Model model) {
	  Map<String, String> result = vendingMachineService.saveVendingMachine(vendingMachine);
	  return   result;
  }
  @RequestMapping(value = "saveTubiao")
  @ResponseBody
  public  Map<String,String>  saveTubiao(@RequestBody LngLat lngLat, Model model) {
	  
	  LngLat biaoByVendCode = vendingMachineService.gettuBiaoByVendCode(lngLat.getVendCode());
	  Map<String, String> result = null;
	  if(biaoByVendCode==null){
		result = vendingMachineService.saveTubiao(lngLat);
	  }else{
		  result =  vendingMachineService.udateTubiao(lngLat);
	  }
	 
	  return   result;
  }
  
  @RequestMapping(value = "update")
  @ResponseBody
  public String update(@RequestBody VendingMachine vendingMachine, Model model) {
	  Map<String,String>  result = vendingMachineService.updateVendingMachine(vendingMachine);
	  return   JsonMapper.toJsonString(result);
  }
  @RequestMapping(value = "edit")
  @ResponseBody
  public String edit(@RequestBody String vendCode) {
	  VendingMachine  vendingMachine = vendingMachineService.getVendingMachineByCode(vendCode);
	  
	  if(vendingMachine.getDeliveryType()==null || " ".equals(vendingMachine.getDeliveryType())){
		  vendingMachine.setDeliveryType("请选投放类型");
	  }
	  
	  
	  if(vendingMachine.getEquipSupply()==null || " ".equals( vendingMachine.getEquipSupply())){
		  vendingMachine.setEquipSupply("请选择设备供应");
		  
	  }
	  
	  if(vendingMachine.getProvinceName()==null || " ".equals(vendingMachine.getProvinceName())){
		  vendingMachine.setProvinceName("省份");
		  
	  }
	  
	  if(vendingMachine.getAreaName()==null || " ".equals(vendingMachine.getAreaName())){
		  vendingMachine.setAreaName("区/县");
		  
	  }
	  
	  if(vendingMachine.getCityName()==null || " ".equals(vendingMachine.getCityName())){
		  vendingMachine.setCityName("市");
	  }
	
	  return   JsonMapper.toJsonString(vendingMachine);
  }
  
  @RequestMapping(value = "selectProvince")
  @ResponseBody
  public String selectProvince(@RequestBody String provinceId , Model model) {
	 
	  List<CtiyVo> cityByProvinceId = vendingMachineService.getCityByProvinceId(provinceId);
	 return   JsonMapper.toJsonString(cityByProvinceId);
  }
  
  
  @RequestMapping(value = "selectCity")
  @ResponseBody
  public String selectCity(@RequestBody String cityId, Model model) {
	  List<CountyVo> cityByCityId = vendingMachineService.getCityByCityId(cityId);
	  
	  return   JsonMapper.toJsonString(cityByCityId);
  }
  
  
  @RequestMapping(value = "enable")
  @ResponseBody
  public String enable(@RequestBody VendingMachine vendingMachine, Model model) {
	  Map<String,String>  result = vendingMachineService.enable(vendingMachine);
	  return   JsonMapper.toJsonString(result);
  }
 
  
  @RequestMapping(value = "upgrade")
  @ResponseBody
  public String upgrade(@RequestBody AppUpgradeTask appUpgradeTask, Model model) {
	  Map<String,String>  result = vendingMachineService.saveAppUpgradeTask(appUpgradeTask);
	  return   JsonMapper.toJsonString(result);
  }
  
  
  
  @RequestMapping(value = "batchUpgrade")
  @ResponseBody
  public String batchUpgrade(@RequestBody AppUpgradeTask appUpgradeTask, Model model) {
	  Map<String,String>  result = vendingMachineService.batchUpgrade(appUpgradeTask);
	  return   JsonMapper.toJsonString(result);
  }
  
  /**
	  * 导出售货机数据
	  *
	  * @param vendingMachineVo
	  * @param request
	  * @param response
	  * @param redirectAttributes
	  * @return
	  */
	 ///@RequiresPermissions("list")
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 public String exportFile(VendingMachineVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "售货机管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		  Page<VendingMachineVo> page = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		  List<VendingMachineVo> list = page.getList();
		  
		 
		  
		  List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400, 2400 );
	     new HCFExportExcel("售货机管理", VendingMachineVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/vendingMachine/list?repage";
	 }
	 
  
	 @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	 public String exportQbFile(VendingMachineVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		 try {
			 String fileName = "售货机管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			 Page<VendingMachineVo> page = vendingMachineService.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
			List<VendingMachineVo> list = page.getList();
			
			 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400, 2400 );
			 new HCFExportExcel("售货机管理", VendingMachineVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 return null;
		 } catch (Exception e) {
			 addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		 }
		 return "redirect:" + adminPath + "/hcf/vendingMachine/list?repage";
	 }
  
 /* 
  @RequiresPermissions("sys:area:edit")
  @RequestMapping(value = "save")
  public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
    if (Global.isDemoMode()) {
      addMessage(redirectAttributes, "演示模式，不允许操作！");
      return "redirect:" + adminPath + "/sys/area";
    }
    if (!beanValidator(model, area)) {
      return form(area, model);
    }
    areaService.save(area);
    addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
    return "redirect:" + adminPath + "/sys/area/";
  }

  @RequiresPermissions("sys:area:edit")
  @RequestMapping(value = "delete")
  public String delete(Area area, RedirectAttributes redirectAttributes) {
    if (Global.isDemoMode()) {
      addMessage(redirectAttributes, "演示模式，不允许操作！");
      return "redirect:" + adminPath + "/sys/area";
    }
//		if (Area.isRoot(id)){
//			addMessage(redirectAttributes, "删除区域失败, 不允许删除顶级区域或编号为空");
//		}else{
    areaService.delete(area);
    addMessage(redirectAttributes, "删除区域成功");
//		}
    return "redirect:" + adminPath + "/sys/area/";
  }

  @RequiresPermissions("user")
  @ResponseBody
  @RequestMapping(value = "treeData")
  public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
    List<Map<String, Object>> mapList = Lists.newArrayList();
    List<Area> list = areaService.findAll();
    for (int i = 0; i < list.size(); i++) {
      Area e = list.get(i);
      if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", e.getId());
        map.put("pId", e.getParentId());
        map.put("name", e.getName());
        mapList.add(map);
      }
    }
    return mapList;
  }*/
}
