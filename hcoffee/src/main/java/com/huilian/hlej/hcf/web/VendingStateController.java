package com.huilian.hlej.hcf.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.VendingStateService;
import com.huilian.hlej.hcf.vo.StatusVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 售货机监控管理
 * 
 * @author yangbo
 * @date 2017年3月1日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingState")
public class VendingStateController extends BaseController {


	@Autowired
	private VendingStateService vendingStateService;


	  @Autowired
	  private VendingMachineService vendingMachineService;


	 @RequestMapping(value = {"list"})
	  public String list(VendingStartVo vendingStartVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<VendingStartVo> page = vendingStateService.getVendingStatisticsPage(new Page<VendingStartVo>(request, response), vendingStartVo);
		  model.addAttribute("page", page);
		  model.addAttribute("statusList", Arrays.asList(new StatusVo("0","正常"),new StatusVo("1","不正常")));
		  model.addAttribute("vendingMachineVo", vendingStartVo);
		  
		  
	    return "/hcoffee/vending/vendingStart";
	  }
	 

	  /**
	   * 导出渠道数据
	   *
	   * @param communityVo
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   */
	  ///@RequiresPermissions("list")
	  @RequestMapping(value = "export", method = RequestMethod.POST)
	  public String exportFile(VendingStartVo vendingStartVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    try {
	      String fileName = "机器运营统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	      Page<VendingStartVo> page =  vendingStateService.getVendingStatisticsPage(new Page(request, response), vendingStartVo);
	      List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400,2400, 0);
	      new HCFExportExcel("机器运营统计数据", VendingStartVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	      return null;
	    } catch (Exception e) {
	      addMessage(redirectAttributes, "导出机器运营统计数据失败！失败信息：" + e.getMessage());
	    }
	    return "redirect:" + adminPath + "/hcf/statistics/list?repage";
	  }
	  @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	  public String exportQbFile(VendingStartVo vendingStartVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		  try {
			  String fileName = "机器运营统计数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			  Page<VendingStartVo> page = vendingStateService.getVendingStatisticsPage(new Page(request, response, "str"), vendingStartVo);
			  List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
			  new HCFExportExcel("机器运营统计数据", VendingStartVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 
			  return null;
		  } catch (Exception e) {
			  addMessage(redirectAttributes, "导出机器运营统计数据失败！失败信息：" + e.getMessage());
		  }
		  return "redirect:" + adminPath + "/hcf/statistics/list?repage";
	  }
}
