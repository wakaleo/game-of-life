package com.huilian.hlej.hcf.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.VendStockService;
import com.huilian.hlej.hcf.vo.StockInfoVo;
import com.huilian.hlej.hcf.vo.VendGoodsStockVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 机器库存数监测
 * @author LongZhangWei
 * @date 2017年12月11日 下午2:43:21
 */

@Controller
@RequestMapping(value = "${adminPath}/hcf/vendStock")
public class VendStockController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(VendStockController.class);
	
	private static final String LIST = "/hcoffee/vending/center/vendStockList";//机器库存显示列表页面
	private static final String VENDGDLIST = "/hcoffee/vending/center/vendGoodsStockList";//机器商品库存显示列表页面
	
	@Autowired
	private VendStockService vendStockService;
	
	@RequestMapping(value = "/list")
	public String list(StockInfoVo stockInfoVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		Page<StockInfoVo> page = vendStockService.getVendStockPage(new Page<StockInfoVo>(request, response), stockInfoVo);
		Page<StockInfoVo> pages = vendStockService.getVendStockPage(new Page<StockInfoVo>(request, response, "str"), stockInfoVo);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return LIST;
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportW(StockInfoVo stockInfoVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "机器库存数监测" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<StockInfoVo> page = vendStockService.getVendStockPage(new Page<StockInfoVo>(request, response,"str"), stockInfoVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("机器库存数监测", StockInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendStock/list?repage";
	}
	
	@RequestMapping(value = "/vendGoodsList")
	public String vendGoodsList(VendGoodsStockVo vendGoodsStockVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		Page<VendGoodsStockVo> page = vendStockService.getVendGoodsStockPage(new Page<VendGoodsStockVo>(request, response), vendGoodsStockVo);
		Page<VendGoodsStockVo> pages = vendStockService.getVendGoodsStockPage(new Page<VendGoodsStockVo>(request, response, "str"), vendGoodsStockVo);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return VENDGDLIST;
	}
	
	@RequestMapping(value = "/exportByVg", method = RequestMethod.POST)
	public String exportByVg(VendGoodsStockVo vendGoodsStockVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "机器商品库存" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<VendGoodsStockVo> page = vendStockService.getVendGoodsStockPage(new Page<VendGoodsStockVo>(request, response,"str"), vendGoodsStockVo);
			List<Integer> widths = Arrays.asList(3600,0,2400);
			new HCFExportExcel("机器商品库存", VendGoodsStockVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendStock/vendGoodsList?repage";
	}
}

