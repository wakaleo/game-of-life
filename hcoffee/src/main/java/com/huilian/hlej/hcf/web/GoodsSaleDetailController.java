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
import com.huilian.hlej.hcf.service.GoodsSaleDetailService;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 商品销售明细Controller
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:57:09
 */

@Controller
@RequestMapping(value = "${adminPath}/hcf/goodsSaleDetail")
public class GoodsSaleDetailController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/goodsSaleDetailList";
	
	@Autowired
	private GoodsSaleDetailService goodsSaleDetailService;
	
	@Autowired
	private DictionariesService dictionariesService;
	
	@RequestMapping(value="/list")
	public String list(GoodsSaleDetailVo goodsSaleDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		Page<GoodsSaleDetailVo> page = goodsSaleDetailService.getGoodsSaleDetailVoPage(new Page<GoodsSaleDetailVo>(requst,response), goodsSaleDetailVo);
		Page<GoodsSaleDetailVo> pages = goodsSaleDetailService.getGoodsSaleDetailVoPage(new Page<GoodsSaleDetailVo>(requst,response,"str"), goodsSaleDetailVo);
		List<DictionariesVo> goodsSaleorderStatusList = dictionariesService.getGoodsSaleDetailOrderStatusList();
		List<DictionariesVo> payStatusList = dictionariesService.getPayStatusList();
		List<DictionariesVo> payTypeList = dictionariesService.getPayTypeList();
		List<DictionariesVo> shipStatusList = dictionariesService.getShipStatusList();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("orderStatusList", goodsSaleorderStatusList);
		model.addAttribute("payStatusList", payStatusList);
		model.addAttribute("payTypeList", payTypeList);
		model.addAttribute("shipStatusList", shipStatusList);
		return LIST;
	}
	
	/**
	 * 导出所有
	 * @param goodsSaleDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportFile(GoodsSaleDetailVo goodsSaleDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "商品销售明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<GoodsSaleDetailVo> page = goodsSaleDetailService.getGoodsSaleDetailVoPage(new Page<GoodsSaleDetailVo>(requst,response,"str"), goodsSaleDetailVo);
		 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
	     new HCFExportExcel("商品销售明细", GoodsSaleDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/goodsSaleDetail/list?repage";
	}
	
	/**
	 * 导出当前页
	 * @param goodsSaleDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportCurrent", method = RequestMethod.POST)
	public String exportCurrent(GoodsSaleDetailVo goodsSaleDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商品销售明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<GoodsSaleDetailVo> page = goodsSaleDetailService.getGoodsSaleDetailVoPage(new Page<GoodsSaleDetailVo>(requst,response), goodsSaleDetailVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("商品销售明细", GoodsSaleDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/goodsSaleDetail/list?repage";
	}
}
