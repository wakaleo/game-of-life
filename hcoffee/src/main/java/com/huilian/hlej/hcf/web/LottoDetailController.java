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
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.LottoDetailService;
import com.huilian.hlej.hcf.service.LottoVendService;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.LottoDetailVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoDetailVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 抽奖活动明细Controller
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:57:09
 */

@Controller
@RequestMapping(value = "${adminPath}/hcf/lottoDetail")
public class LottoDetailController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/lottoDetailList";
	
	@Autowired
	private LottoDetailService lottoDetailService;
	
	@Autowired
	private LottoVendService lottoVendService;
	
	@Autowired
	private DictionariesService dictionariesService;
	
	@RequestMapping(value="/list")
	public String list(LottoDetailVo lottoDetailVo,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<LottoDetailVo> page = lottoDetailService
				.getLottoDetailVoPage(new Page<LottoDetailVo>(request, response), lottoDetailVo);
		Page<LottoDetailVo> pages = lottoDetailService.getLottoDetailVoPage(
				new Page<LottoDetailVo>(request, response, "str"), lottoDetailVo);
		List<LottoActivityVo> activityList = lottoVendService.getLottoActivityVoAll();
		List<DictionariesVo> prizeTypeList = dictionariesService.getLottoPrizeTypeList();
		List<DictionariesVo> lottoSourceList = dictionariesService.getLottoSource();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("activityList", activityList);
		model.addAttribute("prizeTypeList", prizeTypeList);
		model.addAttribute("lottoSourceList", lottoSourceList);
		return LIST;
	}
	
	/**
	 * 导出所有
	 * @param lottoDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportFile(LottoDetailVo lottoDetailVo,HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "抽奖活动明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<LottoDetailVo> page = lottoDetailService.getLottoDetailVoPage(
					new Page<LottoDetailVo>(request, response, "str"), lottoDetailVo);
		 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
	     new HCFExportExcel("抽奖活动明细", LottoDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/lottoDetail/list?repage";
	}
	
	/**
	 * 导出当前页
	 * @param lottoDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportCurrent", method = RequestMethod.POST)
	public String exportCurrent(LottoDetailVo lottoDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		try {
			String fileName = "抽奖活动明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<LottoDetailVo> page = lottoDetailService.getLottoDetailVoPage(new Page<LottoDetailVo>(requst,response), lottoDetailVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("抽奖活动明细", LottoDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/lottoDetail/list?repage";
	}
}
