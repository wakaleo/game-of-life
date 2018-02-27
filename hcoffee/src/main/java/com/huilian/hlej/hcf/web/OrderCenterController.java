package com.huilian.hlej.hcf.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.OrderCenterService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.OrderBaseInfoVo;
import com.huilian.hlej.hcf.vo.OrderGoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 订货中心
 * 
 * @author LongZhangWei
 * @date 2017年9月1日 下午2:05:20
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/orderCenter")
public class OrderCenterController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/orderCenterList";
	private static final String RECORD = "/hcoffee/vending/center/orderRecordList";

	private static Logger logger = LoggerFactory.getLogger(OrderCenterController.class);
	
	@Autowired
	private OrderCenterService orderCenterService;
	
	@Autowired
	private DictionariesService dictionariesService;

	@RequestMapping(value = "/list")
	public String list(OrderBaseInfoVo orderBaseInfoVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		Page<OrderBaseInfoVo> page = orderCenterService.getOrderBaseInfoVoPage(new Page<>(request, response),orderBaseInfoVo);
		Page<OrderBaseInfoVo> pages = orderCenterService.getOrderBaseInfoVoPage(new Page<>(request, response, "str"),orderBaseInfoVo);
		List<DictionariesVo> orderStatusList = dictionariesService.getOrderStatusList();
		List<DictionariesVo> orderPayWayList = dictionariesService.getOrderPayWayList();
		List<DictionariesVo> orderSourceList = dictionariesService.getOrderSourceList();
		Integer syncStatus = orderBaseInfoVo.getSyncStatus();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("orderStatusList", orderStatusList);
		model.addAttribute("orderPayWayList", orderPayWayList);
		model.addAttribute("orderSourceList", orderSourceList);
		model.addAttribute("syncStatus", syncStatus!=null?syncStatus:"");
		return LIST;
	}

	@RequestMapping(value = "/orderRecordList")
	public String rewardRecordList(OrderBaseInfoVo orderBaseInfoVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<OrderBaseInfoVo> roderListPage = orderCenterService.getOrderBaseInfoVoPage(new Page<>(request, response),orderBaseInfoVo);
		Page<OrderBaseInfoVo> roderListPages = orderCenterService.getOrderBaseInfoVoPage(new Page<>(request, response,"str"),orderBaseInfoVo);
		
		String orderNo = orderBaseInfoVo.getOrderNo();
		if(null != orderNo && orderNo != ""){
			OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
			orderGoodsVo.setOrderBaseInfo(orderBaseInfoVo);
			Page<OrderGoodsVo> page = orderCenterService.getOrderGoodsVoPage(new Page<>(request, response),orderGoodsVo);
			Page<OrderGoodsVo> pages = orderCenterService.getOrderGoodsVoPage(new Page<>(request, response,"str"),orderGoodsVo);
			model.addAttribute("page", page);
			model.addAttribute("pages", pages.getList().size());
		}
		model.addAttribute("roderListPage", roderListPage);
		model.addAttribute("roderListPageS", roderListPages);
		return RECORD;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != id && id != ""){
			OrderBaseInfoVo baseInfoVo = orderCenterService.getOrderBaseInfoVo(id);
			map.put("code", "0");
			map.put("baseInfoVo", baseInfoVo);
		}else{
			map.put("code", "1");
		}
		return map;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(@RequestBody OrderBaseInfoVo orderBaseInfoVo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(orderCenterService.updateOrderBaseInfoVo(orderBaseInfoVo)){
			map.put("code", "0");
		}else{
			map.put("code", "1");
		}
		return map;
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String exportFile(OrderBaseInfoVo orderBaseInfoVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "订单中心" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<OrderBaseInfoVo> page = orderCenterService.getOrderBaseInfoVoPage(new Page<>(request, response),
					orderBaseInfoVo);
		  List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400,2400,2400, 2400,2400,2400,2400,2400);
	     new HCFExportExcel("订货单", OrderBaseInfoVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/orderCenter/list?repage";
	}
	
	
	@RequestMapping(value = "/manualSync")
	@ResponseBody
	public Map<String, Object> manualSync(@RequestBody String orderNo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("后台手动同步订单开始...................");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderNo", orderNo.replaceAll("\"",""));
		//String httpUrl = "https://test6hsh.hlej.com/huicoffee/jinDieInteface/syncorderDataInfo.action";
		//String httpUrl = "https://www.huicoffee.com.cn/hkf/jinDieInteface/syncorderDataInfo.action";
		String httpUrl = "https://tes01bit.funsales.com/hkf/jinDieInteface/syncorderDataInfo.action";
		//String httpUrl = "http://localhost:8888/huicoffee/jinDieInteface/syncorderDataInfo.action";
		try {
			logger.info("后台手动同步订单给金蝶的参数..:"+jsonObject.toJSONString());
			String resultStrin = MethodUtil.sendHttp(httpUrl, jsonObject.toJSONString());
			if(resultStrin != null){
				JSONObject resultJsonObject = JSON.parseObject(resultStrin);
				String code = resultJsonObject.getString("code");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderNo", orderNo);
				if("0".equals(code)){
					param.put("syncStatus", "1");
					param.put("syncRemark", "后台同步成功");
					map.put("code", "0");
					map.put("msg", "手动同步成功");
				}else if("1".equals(code)){
					param.put("syncStatus", "2");
					param.put("syncRemark", "后台同步失败");
					map.put("code", "1");
					map.put("msg", "手动同步失败");
				}
				orderCenterService.updateOrderSyncStatus(param);
			}
			map.put("code", "1");
			map.put("msg", "手动同步失败");
		} catch (Exception e) {
			logger.info("后同手动同步订单失败：" + e.toString());
			map.put("code", "1");
			map.put("msg", "手动同步失败");
			e.printStackTrace();
		}
		return map;
	}
}
