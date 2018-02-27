package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.service.OrderManagementService;
import com.huilian.hlej.hcf.vo.LeadManagementVo;
import com.huilian.hlej.hcf.vo.OrderManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 订单管理
 * 
 * @author yangweichao
 * @date 2017-8-28 15:17:35
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/OrderManagement")
public class OrderManagementController extends BaseController{
	
	@Autowired
	private OrderManagementService OrderManagementService;
	
	 @RequestMapping(value = {"list"})
	  public String list(OrderManagementVo OrderManagementVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<OrderManagementVo> page = OrderManagementService.getOrderManagementPage(new Page<OrderManagementVo>(request, response), OrderManagementVo);
		 long count = page.getCount();
		  System.out.println("数量："+count);
		  
		  /*  int state = OrderManagementVo.getState();
		  System.out.println("状态："+state);*/
		  model.addAttribute("page", page);
		  model.addAttribute("OrderManagementVo", OrderManagementVo);
		  
	      return "/hcoffee/vending/center/OrderManagement";
	  }
	 
	 
	  @RequestMapping(value = "update")
	  @ResponseBody
	  public Map<String, String> update(@RequestBody OrderManagementVo orderManagementVo) throws IOException {
		 
		 Map<String,String>  result  = new HashMap<String, String>();
		 
	  	result = OrderManagementService.updateOrderMangementById(orderManagementVo);
	  	return  result;
	  }
	  
	  /*
	  @RequestMapping(value = "selectAll")
	  @ResponseBody
	  public Map<String, String> selectAll(@RequestBody OrderManagementVo orderManagementVo) throws IOException {
		 
		 Map<String,String>  result  = new HashMap<String, String>();
		 
	  	result = OrderManagementService.selectAll(orderManagementVo);
	  	return  result;
	  }
	  
	 */
 
}
