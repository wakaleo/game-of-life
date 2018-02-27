package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.alibaba.druid.util.StringUtils;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.ChannelManagementService;
import com.huilian.hlej.hcf.service.PayMentgerService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.PayVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 渠道管理
 * 
 * @author yangbo
 * @date 2017年3月1日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/payMentger")
public class PayMentgerController extends BaseController {

	@Autowired
	private PayMentgerService payMentgerService;

	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	  private ChannelManagementService channelManagementService;
	
	

	@RequestMapping(value = { "list" })
	public String list(PayVo payVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayVo> page = payMentgerService.getPayMentgerPage(new Page<PayVo>(request, response), payVo);
		model.addAttribute("page", page);
		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("payVo", payVo);
		return "/hcoffee/vending/payManagemer";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, String> save(@RequestBody PayVo payVo) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		 result = this.validateVO(payVo.getChannelId(),null);
		  if(result.size()>0){
			  return result;
		  }
		String[] strings = payVo.getPayList().split(",");
		for (String ss : strings) {

			if (!ss.isEmpty() && ss.equals("微信")) {
				payVo.setWxQrCode(1);
			} 
			if (!ss.isEmpty() && ss.equals("第三方")) {
				payVo.setYjlQrCode(1);
			} 
			if (!ss.isEmpty() && ss.equals("支付宝")) {
				payVo.setZfbQrCode(1);
			} 
		}
		String[] split = payVo.getMxscQrCodeList().split(",");
		
		
		for(String str :split){
			if (!str.isEmpty() && str.equals("不显示")) {
				payVo.setMxscQrCode("0");
			} 
			if (!str.isEmpty() && str.equals("显示")) {
				payVo.setMxscQrCode("1");
			} 	
			
		}
			
		
		ChannelVo channelVo =new ChannelVo();
		
		channelVo.setChannelId(payVo.getChannelId());
		
		ChannelVo channelVo2 = channelManagementService.getChannelNameByChannelId(channelVo);
		
		payVo.setChannelName(channelVo2.getChannelName());
		
		
		 
		
		result = payMentgerService.savePayMenter(payVo);
		return result;
	}
	
	
	  @RequestMapping(value = "update")
	  @ResponseBody
	  public Map<String, String> update(@RequestBody PayVo payVo) throws IOException {
		 Map<String,String>  result  = new HashMap<String, String>();
		 PayVo  vo = payMentgerService.getPayById(payVo);
		 
		 ChannelVo channelVo =new ChannelVo();
			channelVo.setChannelId(payVo.getChannelId());
			
			ChannelVo channelVo2 = channelManagementService.getChannelNameByChannelId(channelVo);
			
			payVo.setChannelName(channelVo2.getChannelName());
	
			String[] strings = payVo.getPayList().split(",");
			for (String ss : strings) {

				if (!ss.isEmpty() && ss.equals("微信")) {
					payVo.setWxQrCode(1);
				} 
				if (!ss.isEmpty() && ss.equals("第三方")) {
					payVo.setYjlQrCode(1);
				} 
				if (!ss.isEmpty() && ss.equals("支付宝")) {
					payVo.setZfbQrCode(1);
				} 
			}
			
			String[] split = payVo.getMxscQrCodeList().split(",");
			
			
			for(String str :split){
				if (!str.isEmpty() && str.equals("不显示")) {
					payVo.setMxscQrCode("0");
				} 
				if (!str.isEmpty() && str.equals("显示")) {
					payVo.setMxscQrCode("1");
				} 	
				
			}
			
			String[] splita = payVo.getMxscQrCodeList().split(",");
			
			
			for(String str :splita){
				if (!str.isEmpty() && str.equals("不显示")) {
					payVo.setMxscQrCode("0");
				} 
				if (!str.isEmpty() && str.equals("显示")) {
					payVo.setMxscQrCode("1");
				} 	
				
			}
	  	result = payMentgerService.updatePayById(payVo);;
	  	return  result;
	  }
	
	
	  @RequestMapping(value = "load")
	  @ResponseBody
	  public String load(@RequestBody PayVo payVo) {
		  PayVo  vo = payMentgerService.getPayById(payVo);
		  StringBuffer sb=new StringBuffer();
		  StringBuffer sbs=new StringBuffer();
		  
		  if(vo.getWxQrCode()==1){
			  sb.append("微信").append(",");
		  } 
		  
		  if(vo.getYjlQrCode()==1){
			  sb.append("第三方").append(",");
		  }
		  
		   if(vo.getZfbQrCode()==1){
			  sb.append("支付宝");
			}
		   if("0".equals(vo.getMxscQrCode())){
			   sbs.append("不显示");
		   }
		   if("1".equals(vo.getMxscQrCode())){
			   sbs.append("显示");
		   }
		   vo.setMxscQrCodeList(sbs.toString());
		  vo.setPayList(sb.toString());
		  
		  return   JsonMapper.toJsonString(vo);
		  
		  
	  }
	  
	  
	  
	  @RequestMapping(value = "updateChannelStatus")
	  @ResponseBody
	  public Map<String, String> updateCommunityStatus(@RequestBody PayVo payVo) throws IOException {
		  Map<String,String>  result  = new HashMap<String, String>();
	  	 result = payMentgerService.updateChannelStatus(payVo);;
	  	return  result;
	  }
	  
	  
	  /**
	   *验证渠道Id或者名称是否重复
	   */
	  private Map<String,String>  validateVO(BigDecimal channelId, String channelName){
			 Map<String,String>  result = new HashMap<String, String>();
			 PayVo payVo =new PayVo();
			 if(channelId!=null && !"".equals(channelId)){
				 payVo.setChannelId(channelId);
			  }
			 PayVo vo = payMentgerService.validateChannelName(payVo);
			 if (vo != null) {
					if (StringUtils.isEmpty(vo.getChannelId().toString())) {
						result.put("code", "2");
						result.put("msg", "渠道名称已存在，请重新输入!");
						return result;

					} else {

						result.put("code", "2");
						result.put("msg", "渠道编码已存在，请重新输入!");
						return result;

					}
				}
				return result;
		 } 
	  
 
	  /**
	   * 导出渠道数据
	   *
	   * @param communityVo
	   * @param request
	   * @param response
	   * @param redirectAttributes
	   * @return
	   */
	  ///@RequiresPermissions("list")
	  @RequestMapping(value = "export", method = RequestMethod.POST)
	  public String exportFile(PayVo payVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	    try {
	      String fileName = "支付管理数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	      Page<PayVo> page =  payMentgerService.getPayMentgerPage(new Page(request, response), payVo);
	      List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
	      new HCFExportExcel("支付管理数据", PayVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	      return null;
	    } catch (Exception e) {
	      addMessage(redirectAttributes, "导出支付管理数据失败！失败信息：" + e.getMessage());
	    }
	    return "redirect:" + adminPath + "/hcf/payMentger/list?repage";
	  }
	  @RequestMapping(value = "qbexport", method = RequestMethod.POST)
	  public String exportQbFile(PayVo payVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		  try {
			  String fileName = "支付管理数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			  Page<PayVo> page = payMentgerService.getPayMentgerPage(new Page(request, response, "str"), payVo);
			  List<Integer> widths = Arrays.asList(3600,0,0,0,0,2400,2400, 0);
			  new HCFExportExcel("支付管理数据", PayVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			  return null;
		  } catch (Exception e) {
			  addMessage(redirectAttributes, "导出支付管理数据失败！失败信息：" + e.getMessage());
		  }
		  return "redirect:" + adminPath + "/hcf/payMentger/list?repage";
	  }

}
