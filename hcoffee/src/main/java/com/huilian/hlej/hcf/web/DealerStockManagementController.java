package com.huilian.hlej.hcf.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huilian.hlej.hcf.service.DealerStockManagementService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.vo.DealerStockDetailVo;
import com.huilian.hlej.hcf.vo.DealerStockVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 代理商库存管理Controller
 * @author LongZhangWei
 * @date 2017年9月4日 上午10:49:15
 */

@Controller
@RequestMapping(value = "${adminPath}/hcf/stockManagement")
public class DealerStockManagementController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/dealerStockManagementList";
	private static final String DETAI = "/hcoffee/vending/center/dealerStockDetailList";
	
	@Autowired
	private DealerStockManagementService dealerStockManagemetService;
	
	@Autowired
	private DictionariesService dictionarService;
	
	@RequestMapping(value="/list")
	public String list(DealerStockVo stockVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		Page<DealerStockVo> page = dealerStockManagemetService.getDealerStockVoPage(new Page<DealerStockVo>(requst,response), stockVo);
		List<DictionariesVo> dealerTypeList = dictionarService.getDealerTypeList();
		List<DictionariesVo> dealerGradeList = dictionarService.getDealerGradeList();
		List<DictionariesVo> conStatusList = dictionarService.getConStatusList();
		model.addAttribute("page", page);
		model.addAttribute("dealerTypeList", dealerTypeList);
		model.addAttribute("dealerGradeList", dealerGradeList);
		model.addAttribute("conStatusList", conStatusList);
		return LIST;
	}
	
	@RequestMapping(value="/stockDetailList")
	public String stockDetailList(DealerStockVo stockVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		String loginName = stockVo.getLoginName();
		if(null != loginName){
			String vendCodes = dealerStockManagemetService.getVendCodesByLoginName(loginName);
			DealerStockDetailVo dealerStockDetailVo = new DealerStockDetailVo();
			if(null != vendCodes){
				dealerStockDetailVo.setVendCodes(vendCodes);
				Page<DealerStockDetailVo> page = dealerStockManagemetService.getDealerStockDetailVoPage(new Page<DealerStockDetailVo>(requst,response), dealerStockDetailVo);
				model.addAttribute("page", page);
				model.addAttribute("loginName", loginName);
			}
		}
		return DETAI;
	}
	
	
}
