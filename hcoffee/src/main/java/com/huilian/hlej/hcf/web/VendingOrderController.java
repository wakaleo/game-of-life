package com.huilian.hlej.hcf.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.VendingStateService;
import com.huilian.hlej.hcf.vo.OrderVo;
import com.huilian.hlej.hcf.vo.TranQueryVo;
import com.huilian.hlej.hcf.vo.payStusVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
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
@RequestMapping(value = "${adminPath}/hcf/transactionQuery")
public class VendingOrderController extends BaseController {


	@Autowired
	private VendingStateService vendingStateService;

    @Autowired
	private VendingMachineService vendingMachineService;
	 
	 @RequestMapping(value = {"list"})
	  public String list(TranQueryVo tranQueryVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		  Page<TranQueryVo> page = vendingStateService.getTransactionQueryPage(new Page<TranQueryVo>(request, response), tranQueryVo);
		  List<Channel>   lst = vendingMachineService.getChannel();
			Map<String,String> map = new HashMap<String,String>();
			for(Channel c :lst){
				map.put(c.getChannelId().toString(), c.getChannelName());
			}
			List<TranQueryVo>list =   page.getList();
			for(TranQueryVo vo:list){
				String channelId = vo.getChannel();
				if(map.containsKey(channelId)){
					vo.setChannelName(map.get(channelId));
				}
			}	
		 
	      model.addAttribute("channelList", vendingMachineService.getChannel()); 
	      model.addAttribute("orserList", Arrays.asList(new OrderVo("1","下单未支付"),new OrderVo("2","下单已支付"),new OrderVo("3","交易完成"),new OrderVo("4","交易失败"),new OrderVo("5","交易异常"))); 
	      model.addAttribute("payStusList", Arrays.asList(new payStusVo("1","未支付"),new payStusVo("2","部分支付"),new payStusVo("3","支付完成"),new payStusVo("4","已退回"),new payStusVo("5","已申请退款"),new payStusVo("6","退款成功"),new payStusVo("7","申请退款失败"))); 
	      model.addAttribute("shipStusList", Arrays.asList(new payStusVo("1","未通知出货"),new payStusVo("2","已通知出货"),new payStusVo("3","通知出货失败"),new payStusVo("4","出货成功"),new payStusVo("5","出货失败"))); 
	      model.addAttribute("page", page);
		  model.addAttribute("tranQueryVo", tranQueryVo);
		  
		return "/hcoffee/vending/tranQuery";
	  }
	 
 
	  /**
	   * 导出数据
	   * 
	   * @param communityVo
	   * 
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   */
	  ///@RequiresPermissions("list")
	  @RequestMapping(value = "export", method = RequestMethod.POST)
	  public String exportFile(TranQueryVo tranQueryVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    try {
	      String fileName = "三方交易查询数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	      Page<TranQueryVo> page = vendingStateService.getTransactionQueryPage(new Page(request, response), tranQueryVo);
	      List<Channel>   lst = vendingMachineService.getChannel();
			Map<String,String> map = new HashMap<String,String>();
			for(Channel c :lst){
				map.put(c.getChannelId().toString(), c.getChannelName());
			}
			List<TranQueryVo>list =   page.getList();
			for(TranQueryVo vo:list){
				String channelId = vo.getChannel();
				if(map.containsKey(channelId)){
					vo.setChannelName(map.get(channelId));
				}
			}
		 
	      List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0, 0 , 0,2400);
	      new HCFExportExcel("三方交易查询数据", TranQueryVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	      return null;
	    } catch (Exception e) { 	
	      addMessage(redirectAttributes, "导出机三方交易查询数据失败！失败信息：" + e.getMessage());
	    }
	    return "redirect:" + adminPath + "/hcf/transactionQuery/list?repage";
	  }
	  @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	  public String exportQbFile(TranQueryVo tranQueryVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		  try {
			  String fileName = "三方交易查询数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			  Page<TranQueryVo> page = vendingStateService.getTransactionQueryPage(new Page(request, response, "str"), tranQueryVo);
			  List<Channel>   lst = vendingMachineService.getChannel();
				Map<String,String> map = new HashMap<String,String>();
				for(Channel c :lst){
					map.put(c.getChannelId().toString(), c.getChannelName());
				}
				List<TranQueryVo>list =   page.getList();
				for(TranQueryVo vo:list){
					String channelId = vo.getChannel();
					if(map.containsKey(channelId)){
						vo.setChannelName(map.get(channelId));
					}
				}
			 
				List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0, 0 , 0,2400 );
			  new HCFExportExcel("三方交易查询数据", TranQueryVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			 
			  return null;
		  } catch (Exception e) {
			  addMessage(redirectAttributes, "导出三方交易查询数据失败！失败信息：" + e.getMessage());
		  }
		  return "redirect:" + adminPath + "/hcf/transactionQuery/list?repage";
	  }
	  
	  @RequestMapping(value = "updateOrderStauts")
	  @ResponseBody
	  public String updateOrderStauts(@RequestBody TranQueryVo tranQueryVo, Model model) {
		  Map<String,String>  result = vendingStateService.updateOrderStauts(tranQueryVo);
		  return   JsonMapper.toJsonString(result);
	  }
	 
}
