package com.huilian.hlej.hcf.web;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.EquipmentService;
import com.huilian.hlej.hcf.vo.CoffeeActivityInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentMateriaVo;
import com.huilian.hlej.hcf.vo.EquipmentTradeVo;
import com.huilian.hlej.hcf.vo.EquipmentWarnVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 咖啡机
 * @author LongZhangWei
 * @date 2017年12月25日 上午9:20:35
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/equipment")
public class EquipmentController extends BaseController{
	
	private static final String EQUIPMENTINFOLIST = "/hcoffee/vending/center/equipmentInfoList";//设备信息查询页面
	private static final String EQUIPMENTTRADELIST = "/hcoffee/vending/center/equipmentTradeList";//设备交易查询页面
	private static final String EQUIPMENTWARNLIST = "/hcoffee/vending/center/equipmentWarnList";//设备报警查询页面
	private static final String EQUIPMENTMATERIALLIST = "/hcoffee/vending/center/equipmentMaterialList";//设备物料查询页面
	private static final String EQUIPMENTHISWARNLIST = "/hcoffee/vending/center/equipmentHisWarnList";//设备故障历史信息查询页面
	private static final String COFFEEACTIVITYLIST = "/hcoffee/vending/center/coffeeActivityList";//咖啡机活动查询
	
	@Autowired
	private EquipmentService equipmentService;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private Map<String, Object> tempDateMap = new HashMap<String, Object>();
	
	/**
	 * 查询设备信息
	 * @param equipmentInfoVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentInfoList")
	public String equipmentInfoList(EquipmentInfoVo equipmentInfoVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<EquipmentInfoVo> page = equipmentService.getEquipmentInfoVoPage(new Page<EquipmentInfoVo>(request, response),equipmentInfoVo);
		model.addAttribute("page", page);
		return EQUIPMENTINFOLIST;
	}
	
	@RequestMapping(value = "/exportInfo", method = RequestMethod.POST)
	public String exportInfo(EquipmentInfoVo equipmentInfoVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询设备信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<EquipmentInfoVo> page = equipmentService.getEquipmentInfoVoPage(new Page<EquipmentInfoVo>(request, response,"str"),equipmentInfoVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400);
			new HCFExportExcel("待结算记录", EquipmentInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/equipment/equipmentInfoList?repage";
	}
	
	/**
	 * 查询设备贸易
	 * @param equipmentTradeVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentTradeList")
	public String equipmentTradeList(EquipmentTradeVo equipmentTradeVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		equipmentTradeVo.setStartTimeStr(equipmentTradeVo.getStartTime()!=null?format.format(equipmentTradeVo.getStartTime()):null);
		equipmentTradeVo.setEndTimeStr(equipmentTradeVo.getEndTime()!=null?format.format(equipmentTradeVo.getEndTime()):null);
		Page<EquipmentTradeVo> page = equipmentService.getEquipmentTradeVo(new Page<EquipmentTradeVo>(request,response),equipmentTradeVo);
		Page<EquipmentTradeVo> pages = equipmentService.getEquipmentTradeVo(new Page<EquipmentTradeVo>(request,response,"str"),equipmentTradeVo);
		Map<String, Object> map = getStatistics(pages.getList());
		if(null != map){
			model.addAttribute("sumPrice", map.get("sumPrice"));
		}
		model.addAttribute("page", page);
		model.addAttribute("startTime", equipmentTradeVo.getStartTime()!=null?format.format(equipmentTradeVo.getStartTime()):"");
		model.addAttribute("endTime", equipmentTradeVo.getEndTime()!=null?format.format(equipmentTradeVo.getEndTime()):"");
		return EQUIPMENTTRADELIST;
	}
	
	@RequestMapping(value = "/exportTrade", method = RequestMethod.POST)
	public String exportTrade(EquipmentTradeVo equipmentTradeVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询设备贸易" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<EquipmentTradeVo> page = equipmentService.getEquipmentTradeVo(new Page<EquipmentTradeVo>(request,response,"str"),equipmentTradeVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400);
			new HCFExportExcel("查询设备贸易", EquipmentTradeVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/equipment/equipmentTradeList?repage";
	}
	
	/**
	 * 查询设备警告
	 * @param equipmentWarnVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentWarnList")
	public String equipmentWarnList(EquipmentWarnVo equipmentWarnVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<EquipmentWarnVo> page = equipmentService.getEquipmentWarnVoPage(new Page<EquipmentWarnVo>(request,response),equipmentWarnVo);
		model.addAttribute("page", page);
		return EQUIPMENTWARNLIST;
	}
	
	@RequestMapping(value = "/exportWarn", method = RequestMethod.POST)
	public String exportWarn(EquipmentWarnVo equipmentWarnVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询设备警告" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<EquipmentWarnVo> page = equipmentService.getEquipmentWarnVoPage(new Page<EquipmentWarnVo>(request,response,"str"),equipmentWarnVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400);
			new HCFExportExcel("查询设备警告", EquipmentWarnVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/equipment/equipmentWarnList?repage";
	}
	
	/**
	 * 查询设备物料
	 * @param equipmentMateriaVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentMaterialList")
	public String equipmentMaterialList(EquipmentMateriaVo equipmentMateriaVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<EquipmentMateriaVo> page = equipmentService.getEquipmentMateriaVoPage(new Page<EquipmentMateriaVo>(request,response),equipmentMateriaVo);
		model.addAttribute("page", page);
		return EQUIPMENTMATERIALLIST;
	}
	
	@RequestMapping(value = "/exportMateria", method = RequestMethod.POST)
	public String exportMateria(EquipmentMateriaVo equipmentMateriaVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询设备物料" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<EquipmentMateriaVo> page = equipmentService.getEquipmentMateriaVoPage(new Page<EquipmentMateriaVo>(request,response,"str"),equipmentMateriaVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400);
			new HCFExportExcel("查询设备物料", EquipmentMateriaVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/equipment/equipmentMaterialList?repage";
	}
	
	private Map<String, Object> getStatistics(List<?> list){
		DecimalFormat df = new DecimalFormat("######0.00");
		double sumPrice = 0.0;
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != list && list.size() >0){
			for(Object object : list){
				if(object instanceof EquipmentTradeVo){//对帐列表
					EquipmentTradeVo vo = (EquipmentTradeVo)object;
					sumPrice += vo.getPrice();
				}
			}
		}
		map.put("sumPrice", df.format(sumPrice));
		return map;
	}
	
	/**
	 * 查询机器的故障历史信息
	 * @param equipmentWarnVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentHisWarnList")
	public String equipmentHisWarnList(EquipmentWarnVo equipmentWarnVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<EquipmentWarnVo> page = equipmentService.getEquipmentHisWarnVoPage(new Page<EquipmentWarnVo>(request,response),equipmentWarnVo);
		model.addAttribute("page", page);
		model.addAttribute("vendCode", equipmentWarnVo.getVendCode());
		return EQUIPMENTHISWARNLIST;
	}
	
	/**
	 * 咖啡机活动查询
	 * @param equipmentInfoVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/coffeeActivityList")
	public String coffeeActivityList(CoffeeActivityInfoVo coffeeActivityInfoVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		coffeeActivityInfoVo.setStartTimeStr(coffeeActivityInfoVo.getStartTime()!=null?format.format(coffeeActivityInfoVo.getStartTime()):null);
		coffeeActivityInfoVo.setEndTimeStr(coffeeActivityInfoVo.getEndTime()!=null?format.format(coffeeActivityInfoVo.getEndTime()):null);
		Page<CoffeeActivityInfoVo> page = equipmentService.getCoffeeActivityInfoVo(new Page<CoffeeActivityInfoVo>(request, response),coffeeActivityInfoVo);
		model.addAttribute("page", page);
		model.addAttribute("startTime", coffeeActivityInfoVo.getStartTime()!=null?format.format(coffeeActivityInfoVo.getStartTime()):"");
		model.addAttribute("endTime", coffeeActivityInfoVo.getEndTime()!=null?format.format(coffeeActivityInfoVo.getEndTime()):"");
		return COFFEEACTIVITYLIST;
	}
	
	/**
	 * 导出咖啡机活动信息
	 * @param equipmentWarnVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportCoffeeActivityInfo", method = RequestMethod.POST)
	public String exportCoffeeActivityInfo(CoffeeActivityInfoVo coffeeActivityInfoVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询咖啡机活动信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<CoffeeActivityInfoVo> page = equipmentService.getCoffeeActivityInfoVo(new Page<CoffeeActivityInfoVo>(request, response,"str"),coffeeActivityInfoVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("查询咖啡机活动信息", CoffeeActivityInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/equipment/coffeeActivityList?repage";
	}
}
