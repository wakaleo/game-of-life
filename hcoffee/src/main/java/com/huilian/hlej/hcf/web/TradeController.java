package com.huilian.hlej.hcf.web;


import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.huilian.hlej.hcf.service.TradeService;
import com.huilian.hlej.hcf.vo.TradeInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 咖啡机
 * @author LongZhangWei
 * @date 2017年12月25日 上午9:20:35
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/trade")
public class TradeController extends BaseController{
	
	private static final String OFFLINELIST = "/hcoffee/vending/center/offlineList";//线下交易查询
	
	@Autowired
	private TradeService tradeService;
	
	/**
	 * 查询设备信息
	 * @param equipmentInfoVo
	 * @param request
	 * @param response
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/offLineList")
	public String equipmentInfoList(TradeInfoVo tradeInfoVo , HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<TradeInfoVo> page = tradeService.getTradeInfoVoPage(new Page<TradeInfoVo>(request, response),tradeInfoVo);
		model.addAttribute("page", page);
		return OFFLINELIST;
	}
	
	@RequestMapping(value = "/exportInfo", method = RequestMethod.POST)
	public String exportInfo(TradeInfoVo tradeInfoVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "线下交易查询信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<TradeInfoVo> page = tradeService.getTradeInfoVoPage(new Page<TradeInfoVo>(request, response,"str"),tradeInfoVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("线下交易查询", TradeInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (IOException e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/hcf/trade/offLineList?repage";
	}
	
}
