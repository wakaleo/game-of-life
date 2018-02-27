package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.huilian.hlej.hcf.service.ChannelManagementService;
import com.huilian.hlej.hcf.service.PayMentgerService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.PayVo;
import com.huilian.hlej.hcf.vo.CustInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 用户统计
 * 
 * @author yangbo
 * @date 2017年3月1日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/statistics")
public class StatisticsController extends BaseController {


	@Autowired
	private VendingMachineService vendingMachineService;


	 @RequestMapping(value = {"list"})
	  public String list(CustInfoVo vendingMachineVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<CustInfoVo> page = vendingMachineService.getVendingStatisticsPage(new Page<CustInfoVo>(request, response), vendingMachineVo);
		  model.addAttribute("page", page);
		  vendingMachineVo.setStartTime(null);
		  vendingMachineVo.setEndTime(null);
		  model.addAttribute("vendingMachineVo", vendingMachineVo);
		  
		  
	    return "/hcoffee/vending/statistics";
	  }
	 

	  /**
	   * 用户数据
	   *
	   * @param communityVo
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   */
	  ///@RequiresPermissions("list")
	  @RequestMapping(value = "export", method = RequestMethod.POST)
	  public String exportFile(CustInfoVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    try {
	      String fileName = "用户统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	      Page<CustInfoVo> page =  vendingMachineService.getVendingStatisticsPage(new Page(request, response), vendingMachineVo);
	      List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
	      new HCFExportExcel("用户统计数据", CustInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	      return null;
	    } catch (Exception e) {
	      addMessage(redirectAttributes, "导出用户统计数据失败！失败信息：" + e.getMessage());
	    }
	    return "redirect:" + adminPath + "/hcf/statistics/list?repage";
	  }
	  @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	  public String exportQbFile(CustInfoVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		  try {
			  String fileName = "用户统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			  Page<CustInfoVo> page = vendingMachineService.getVendingStatisticsPage(new Page(request, response, "str"), vendingMachineVo);
			  List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
			  new HCFExportExcel("用户统计数据", CustInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			  return null;
		  } catch (Exception e) {
			  addMessage(redirectAttributes, "导出用户统计数据失败！失败信息：" + e.getMessage());
		  }
		  return "redirect:" + adminPath + "/hcf/statistics/list?repage";
	  }
}
