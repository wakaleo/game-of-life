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

import com.huilian.hlej.hcf.common.utils.DataSourceContextHolder;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.BitMachineAisleInfoService;
import com.huilian.hlej.hcf.service.BitMachineBaseInfoService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.vo.BitMachineAisleInfoVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 01比特机器信息管理
 * @author ZhangZeBiao
 * @date 2017年12月14日 下午5:32:27
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/bitMachineInfo")
public class BitMachineInfoController extends BaseController{


	private static final String LIST = "/hcoffee/vending/center/bitMachineInfoList";
	private static final String AISLE_LIST = "/hcoffee/vending/center/bitMachineAisleInfoList";

	
	@Autowired
	private BitMachineBaseInfoService bitMachineBaseInfoService;
	
	@Autowired
	private BitMachineAisleInfoService bitMachineAisleInfoService;
	
	@Autowired
	private DictionariesService dictionariesService;

	@Autowired
	FileHandler fileHandler;
	
	/**
	 * 查询所有比特机器基本信息
	 * @param bitMachineBaseInfoVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(BitMachineBaseInfoVo bitMachineBaseInfoVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		DataSourceContextHolder.setDBType("bitDataSource");  
		Page<BitMachineBaseInfoVo> page = bitMachineBaseInfoService
				.getBitMachineBaseInfoVoPage(new Page<BitMachineBaseInfoVo>(request, response), bitMachineBaseInfoVo);
//		Page<BitMachineBaseInfoVo> pages = bitMachineBaseInfoService.getBitMachineBaseInfoVoPage(
//				new Page<BitMachineBaseInfoVo>(request, response, "str"), bitMachineBaseInfoVo);
//		List<BitMachineBaseInfoVo> bitMachineList = bitMachineBaseInfoService.getBitMachineBaseInfoVoAll();
//		List<LottoEqRelationVo> vendCodeList = bitMachineBaseInfoService.getLottoEqRelationVoAll();
//		List<DictionariesVo> prizeTypeList = dictionariesService.getLottoPrizeTypeList();
		model.addAttribute("page", page);
//		model.addAttribute("pages", pages.getList().size());
//		model.addAttribute("bitMachineBaseInfoVoList", bitMachineBaseInfoService.getLottoActivityVoList(bitMachineBaseInfoVo));
//		model.addAttribute("bitMachineList", bitMachineList);
//		model.addAttribute("vendCodeList", vendCodeList);
//		model.addAttribute("prizeTypeList", prizeTypeList);
		DataSourceContextHolder.clearDBType();  
		return LIST;
	}
	
	/**
	 * 查询所有比特机器货道信息
	 * @param bitMachineAisleInfoVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/aisleInfoList")
	public String aisleInfoList(BitMachineAisleInfoVo bitMachineAisleInfoVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		DataSourceContextHolder.setDBType("bitDataSource");  
		Page<BitMachineAisleInfoVo> page = bitMachineAisleInfoService
				.getBitMachineAisleInfoVoPage(new Page<BitMachineAisleInfoVo>(request, response), bitMachineAisleInfoVo);
//		Page<BitMachineAisleInfoVo> pages = bitMachineAisleInfoService.getBitMachineAisleInfoVoPage(
//				new Page<BitMachineAisleInfoVo>(request, response, "str"), bitMachineAisleInfoVo);
		BitMachineAisleInfoVo aisleInfoVo=new BitMachineAisleInfoVo();
		aisleInfoVo.setMachineId(bitMachineAisleInfoVo.getMachineId());
		BitMachineBaseInfoVo baseInfoVo =bitMachineAisleInfoService.getBitMachineAisleCountVo(aisleInfoVo);
		model.addAttribute("page", page);
//		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("baseInfoVo", baseInfoVo);
		DataSourceContextHolder.clearDBType();  
		return AISLE_LIST;
	}

	
	

	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	/*@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody BitMachineBaseInfoVo bitMachineBaseInfoVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = bitMachineBaseInfoVo.getId();
		BitMachineBaseInfoVo editBitMachineBaseInfoVo = bitMachineBaseInfoService.getBitMachineBaseInfoVo(id);
		map.put("editBitMachineBaseInfoVo", editBitMachineBaseInfoVo);
		return map;
	}*/
	
	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	/*@RequestMapping(value = "/editPrize")
	@ResponseBody
	public Map<String, Object> editPrize(@RequestBody BitMachineBaseInfoVo bitMachineBaseInfoVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BitMachineBaseInfoVo> prizeList = bitMachineBaseInfoService.getBitMachineBaseInfoVoListByActivityNoAndVendCode(bitMachineBaseInfoVo);
		map.put("prizeList", prizeList);
		return map;
	}
	*/
	

	/**
	 * 导出基本信息所有
	 * @param goodsSaleDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportBase", method = RequestMethod.POST)
	public String exportBase(BitMachineBaseInfoVo bitMachineBaseInfoVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
	   try {
		 DataSourceContextHolder.setDBType("bitDataSource");  
	     String fileName = "比特机器基本信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<BitMachineBaseInfoVo> page = bitMachineBaseInfoService.getBitMachineBaseInfoVoPage(new Page<BitMachineBaseInfoVo>(requst,response,"str"), bitMachineBaseInfoVo);
	     DataSourceContextHolder.clearDBType(); 
		 List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
	     new HCFExportExcel("比特机器基本信息", BitMachineBaseInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/bitMachineInfo/list?repage";
	}
	
	/**
	 * 导出基本信息当前页
	 * @param goodsSaleDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportCurrentBase", method = RequestMethod.POST)
	public String exportCurrentBase(BitMachineBaseInfoVo bitMachineBaseInfoVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		try {
			DataSourceContextHolder.setDBType("bitDataSource");  
			String fileName = "比特机器基本信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<BitMachineBaseInfoVo> page = bitMachineBaseInfoService.getBitMachineBaseInfoVoPage(new Page<BitMachineBaseInfoVo>(requst,response), bitMachineBaseInfoVo);
			DataSourceContextHolder.clearDBType(); 
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("比特机器基本信息", BitMachineBaseInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/bitMachineInfo/list?repage";
	}
	
	/**
	 * 导出货道信息所有
	 * @param bitMachineAisleInfoVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportAisle", method = RequestMethod.POST)
	public String exportAisle(BitMachineAisleInfoVo bitMachineAisleInfoVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		try {
			DataSourceContextHolder.setDBType("bitDataSource");  
			String fileName = "比特机器货道信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<BitMachineAisleInfoVo> page = bitMachineAisleInfoService.getBitMachineAisleInfoVoPage(new Page<BitMachineAisleInfoVo>(requst,response,"str"), bitMachineAisleInfoVo);
			DataSourceContextHolder.clearDBType(); 
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("比特机器货道信息", BitMachineAisleInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/bitMachineInfo/aisleInfoList?repage";
	}
	
	/**
	 * 导出货道信息当前页
	 * @param bitMachineAisleInfoVo
	 * @param requst
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportCurrentAisle", method = RequestMethod.POST)
	public String exportCurrentAisle(BitMachineAisleInfoVo bitMachineAisleInfoVo,HttpServletRequest requst,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		try {
			DataSourceContextHolder.setDBType("bitDataSource");  
			String fileName = "比特机器货道信息" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<BitMachineAisleInfoVo> page = bitMachineAisleInfoService.getBitMachineAisleInfoVoPage(new Page<BitMachineAisleInfoVo>(requst,response), bitMachineAisleInfoVo);
			DataSourceContextHolder.clearDBType(); 
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("比特机器货道信息", BitMachineAisleInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/bitMachineInfo/aisleInfoList?repage";
	}
	
	
	
	

	
}
