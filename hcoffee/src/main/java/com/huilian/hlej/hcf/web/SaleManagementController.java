package com.huilian.hlej.hcf.web;

import java.net.URLDecoder;
import java.sql.Timestamp;
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
import com.huilian.hlej.hcf.service.DealerStockManagementService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.SaleManagementService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.OrderDetailVo;
import com.huilian.hlej.hcf.vo.SaleDetailVo;
import com.huilian.hlej.hcf.vo.SaleManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 销售管理Controller
 * @author LongZhangWei
 * @date 2017年9月6日 上午10:31:41
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/saleManagement")
public class SaleManagementController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/saleManagementList";//销售管理列表页面
	private static final String SALE_RECORD = "/hcoffee/vending/center/saleRecordList";//销售明细页面
	private static final String ORDER_RECORD = "/hcoffee/vending/center/saleOrderRecordList";//订单明细页面
	
	@Autowired
	private SaleManagementService saleManagementService;
	
	@Autowired
	private DictionariesService dictionarsService;
	
	@Autowired
	private DealerStockManagementService dealerStockManagementService;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value="/list")
	public String list(SaleManagementVo saleManagementVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		Page<SaleManagementVo> page = saleManagementService.getSaleManagementVoPage(new Page<SaleManagementVo>(requst,response), saleManagementVo);
		Map<String, Object> map = getStatistics(page.getList());
		if(null != map){
			model.addAttribute("saleCount", map.get("saleCount"));
			model.addAttribute("saleMoney", map.get("saleMoney"));
		}
		model.addAttribute("startTime", saleManagementVo.getStartTime()!=null?format.format(saleManagementVo.getStartTime()):"");
		model.addAttribute("endTime", saleManagementVo.getEndTime()!=null?format.format(saleManagementVo.getEndTime()):"");
		model.addAttribute("page", page);
		return LIST;
	}
	
	/**
	 * 销售明细
	 * @param saleManagementVo
	 * @param requst
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saleRecordList")
	public String saleRecordList(SaleManagementVo saleManagementVo,HttpServletRequest request,HttpServletResponse response,Model model)throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String loginName = saleManagementVo.getLoginName();
		String startTimeStr = saleManagementVo.getStartTimeStr();
		String endTimeStr = saleManagementVo.getEndTimeStr();
		List<DealerEqRelationVo> list = null;
 		if(null != loginName){
 			String vendCodes = dealerStockManagementService.getVendCodesByLoginName(loginName);
 			list = saleManagementService.getVendCodesListByLoginName(loginName);
			SaleDetailVo saleDetailVo = new SaleDetailVo();
			saleDetailVo.setLoginName(loginName);
			saleDetailVo.setStartTime(startTimeStr!=null?format.parse(startTimeStr):null);
			saleDetailVo.setEndTime(endTimeStr!=null?format.parse(endTimeStr):null);
			saleDetailVo.setDealerName(saleManagementVo.getDealerName());
			Page<SaleDetailVo> page = saleManagementService.getSaleDetailVoPage(new Page<SaleDetailVo>(request,response), saleDetailVo);
			Page<SaleDetailVo> pages = saleManagementService.getSaleDetailVoPage(new Page<SaleDetailVo>(request,response,"str"), saleDetailVo);
			List<DictionariesVo> salePayWayList = dictionarsService.getSalePayWayList();
			Map<String, Object> map = getStatistics(pages.getList());
			if(null != map){
				model.addAttribute("saleCount", map.get("saleCount"));
				model.addAttribute("saleMoney", map.get("saleMoney"));
			}
			model.addAttribute("page", page);
			model.addAttribute("loginName", loginName);
			model.addAttribute("vendCodes", vendCodes);
			model.addAttribute("salePayWayList", salePayWayList);
			model.addAttribute("startTime", startTimeStr!=null?startTimeStr:"");
			model.addAttribute("endTime", endTimeStr!=null?endTimeStr:"");
			model.addAttribute("dealerName", saleManagementVo.getDealerName()!=null?URLDecoder.decode(saleManagementVo.getDealerName(),"UTF-8"):"");
		}
		model.addAttribute("vendCodeList", list);
		return SALE_RECORD;
	}
	
	/**
	 * 获取销售数量和销售金额的总计
	 * @param list
	 * @return
	 */
	private Map<String, Object> getStatistics(List<?> list){
		DecimalFormat df = new DecimalFormat("######0.00"); 
		Integer saleCount = 0;
		Double saleMoney = 0.0;
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != list && list.size() >0){
			for(Object object : list){
				if(object instanceof SaleManagementVo){
					SaleManagementVo vo = (SaleManagementVo)object;
					saleCount += vo.getSaleCount()!=null?vo.getSaleCount():0;
					saleMoney += vo.getSaleMoney()!=null?vo.getSaleMoney():0;
				}
				if(object instanceof SaleDetailVo){
					SaleDetailVo vo = (SaleDetailVo)object;
					saleCount += vo.getSaleCount()!=null?vo.getSaleCount():0;
					saleMoney += vo.getSaleMoney()!=null?vo.getSaleMoney():0;
				}
				if(object instanceof OrderDetailVo){
					OrderDetailVo vo = (OrderDetailVo)object;
					saleCount += vo.getSaleCount()!=null?vo.getSaleCount():0;
					saleMoney += vo.getSaleMoney()!=null?vo.getSaleMoney():0;
				}
			}
		}
		map.put("saleCount", saleCount);
		map.put("saleMoney", df.format(saleMoney));
		return map;
	}
	
	
	
	/**
	 * 订单明细
	 * @param saleManagementVo
	 * @param requst
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/orderRecordList")
	public String orderRecordList(SaleDetailVo saleDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		String vendCode = saleDetailVo.getVendCode();
		String shipTimeStr = saleDetailVo.getShipTimeStr();
		if(null != vendCode && !vendCode.equals("")){
			OrderDetailVo orderDetailVo = new OrderDetailVo();
			orderDetailVo.setVendCode(vendCode);
			if(null != shipTimeStr){
				Timestamp shipTime = Timestamp.valueOf(saleDetailVo.getShipTimeStr());
				orderDetailVo.setShipTime(shipTime);
			}
			Page<OrderDetailVo> page = saleManagementService.getOrderDatailVoPage(new Page<OrderDetailVo>(requst,response), orderDetailVo);
			List<DictionariesVo> salePayWayList = dictionarsService.getSalePayWayList();
			Map<String, Object> map = getStatistics(page.getList());
			if(null != map){
				model.addAttribute("saleCount", map.get("saleCount"));
				model.addAttribute("saleMoney", map.get("saleMoney"));
			}
			model.addAttribute("page", page);
			model.addAttribute("vendCode", vendCode);
			model.addAttribute("salePayWayList", salePayWayList);
		}
		return ORDER_RECORD;
	}
	
	/**
	 * 订单明细查询
	 * @param orderDetailVo
	 * @param requst
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/orderDetaildList")
	public String orderDetaildList(OrderDetailVo orderDetailVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		Page<OrderDetailVo> page = saleManagementService.getOrderDatailVoPage(new Page<OrderDetailVo>(requst,response), orderDetailVo);
		List<DictionariesVo> salePayWayList = dictionarsService.getSalePayWayList();
		Map<String, Object> map = getStatistics(page.getList());
		if(null != map){
			model.addAttribute("saleCount", map.get("saleCount"));
			model.addAttribute("saleMoney", map.get("saleMoney"));
		}
		model.addAttribute("orderNo", orderDetailVo.getOrderNo());
		model.addAttribute("page", page);
		model.addAttribute("salePayWayList", salePayWayList);
		model.addAttribute("vendCode", orderDetailVo.getVendCode()!=null?orderDetailVo.getVendCode():"");
		model.addAttribute("payType",orderDetailVo.getPayType());
		model.addAttribute("payStatus", orderDetailVo.getPayStatus());
		model.addAttribute("orderStatus", orderDetailVo.getOrderStatus());
		model.addAttribute("shipStatus", orderDetailVo.getShipStatus());
		return ORDER_RECORD;
	}
	
	@RequestMapping(value="/detailList")
	public String detailList(SaleManagementVo saleManagementVo,HttpServletRequest requst,HttpServletResponse response,Model model){
		String loginName = saleManagementVo.getLoginName();
		String vendCodes = saleManagementVo.getVendCodes();
		String dealerName = saleManagementVo.getDealerName()!=null?saleManagementVo.getDealerName():"";
		if(null != loginName){
			String vendCode = saleManagementVo.getVendCode();
			SaleDetailVo saleDetailVo = new SaleDetailVo();
			saleDetailVo.setVendCode(vendCode);
			saleDetailVo.setStartTime(saleManagementVo.getStartTime()!=null?saleManagementVo.getStartTime():null);
			saleDetailVo.setEndTime(saleManagementVo.getEndTime()!=null?saleManagementVo.getEndTime():null);
			saleDetailVo.setLocation(saleManagementVo.getLocation());
			saleDetailVo.setVendCodes(MethodUtil.getFormatStr(vendCodes));
			saleDetailVo.setDealerName(dealerName);
			Page<SaleDetailVo> page = saleManagementService.getSaleDetailVoPage(new Page<SaleDetailVo>(requst,response), saleDetailVo);
			Page<SaleDetailVo> pages = saleManagementService.getSaleDetailVoPage(new Page<SaleDetailVo>(requst,response,"str"), saleDetailVo);
			Map<String, Object> map = getStatistics(pages.getList());
			if(null != map){
				model.addAttribute("saleCount", map.get("saleCount"));
				model.addAttribute("saleMoney", map.get("saleMoney"));
			}
			List<DealerEqRelationVo> list = saleManagementService.getVendCodesListByLoginName(loginName);
			model.addAttribute("page", page);
			model.addAttribute("searchContent", saleManagementVo.getSearchContent());
			model.addAttribute("vendCodeList", list);
			model.addAttribute("vendCodes", vendCodes);
			model.addAttribute("vendCode", vendCode!=null?vendCode:"");
			model.addAttribute("startTime", saleManagementVo.getStartTime()!=null?format.format(saleManagementVo.getStartTime()):"");
			model.addAttribute("endTime", saleManagementVo.getEndTime()!=null?format.format(saleManagementVo.getEndTime()):"");
			model.addAttribute("location", saleManagementVo.getLocation()!=null?saleManagementVo.getLocation():"");
			model.addAttribute("dealerName", dealerName);
		}
		return SALE_RECORD;
	}
	
	/**
	 * 销售管是列表导出
	 * @param saleManagementVo
	 * @param requst
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportFile(SaleManagementVo saleManagementVo,HttpServletRequest requst,HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "销售管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<SaleManagementVo> page = saleManagementService.getSaleManagementVoPage(new Page<SaleManagementVo>(requst,response), saleManagementVo);
		  List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400);
	     new HCFExportExcel("销售管理", SaleManagementVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/saleManagement/list?repage";
	}
	
	/**
	 * 导出销售明细
	 * @param saleDetailVo
	 * @param requst
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportSaleDetail", method = RequestMethod.POST)
	public String exportSaleDetail(SaleDetailVo saleDetailVo,HttpServletRequest requst,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "销售明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<SaleDetailVo> page = saleManagementService.getSaleDetailVoPage(new Page<SaleDetailVo>(requst,response), saleDetailVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400);
			new HCFExportExcel("销售明细", SaleDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/saleManagement/detailList?repage";
	}
	
	/**
	 * 导出订单明细
	 * @param saleDetailVo
	 * @param requst
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportOrderDetail", method = RequestMethod.POST)
	public String exportOrderDetail(OrderDetailVo orderDetailVo,HttpServletRequest requst,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "订单明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<OrderDetailVo> page = saleManagementService.getOrderDatailVoPage(new Page<OrderDetailVo>(requst,response), orderDetailVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400,2400);
			new HCFExportExcel("订单明细", OrderDetailVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/saleManagement/orderRecordList?repage";
	}
	
}
