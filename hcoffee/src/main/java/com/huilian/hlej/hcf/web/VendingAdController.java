package com.huilian.hlej.hcf.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.service.ChannelManagementService;
import com.huilian.hlej.hcf.service.VendingAdConterService;
import com.huilian.hlej.hcf.service.VendingAdService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;
/**
 * 广告管理控制器
 * @author yangbo
 * @date 2017年2月4日 上午10:11:14
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingAd")
public class VendingAdController  extends BaseController {
	
	 @Autowired
	  private VendingMachineService vendingMachineService;
	
	 @Autowired
	  private VendingAdService vendingAdService;
	 
	 
	 @Autowired
	private VendingAdConterService vendingAdConterService;
	 
	 @Autowired
	private ChannelManagementService channelManagementService;
	
	 @RequestMapping(value = {"list"})
	  public String list(VendingReleVo vendingReleVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		  Page<VendingReleVo> page = vendingAdService.getVendingAdPage(new Page<VendingReleVo>(request, response), vendingReleVo);
		  List<VendingReleVo> list = page.getList();
		  for(VendingReleVo vending : list){
			  //channelManagementService.getChannelNameByChannelId(vending)
			  String[] split = vending.getaDList().split(",");
			  
			  int lengthz = split.length;
			  
			  vending.setNum(String.valueOf(lengthz));
			  
			  VendingConterAdVo sa=new VendingConterAdVo();
			  sa.setVendCode(vending.getVendCode());
			  for(String strs : split){
				  sa.setAdId(strs);
				  vendingAdConterService.updateByAdId(sa);
			  }
		  }
		  model.addAttribute("page", page);
		  model.addAttribute("channelList", vendingMachineService.getChannel());
		  model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		  model.addAttribute("communityList", vendingMachineService.getCommunity());
		  model.addAttribute("appVersionList",vendingMachineService.getAppVersionRecords() );
		  vendingReleVo.setStartTime(null);
		  vendingReleVo.setEndTime(null);
		 
		  model.addAttribute("vendingReleVo", vendingReleVo);
	    return "/hcoffee/vending/vendingAdList";
	  }
	 

		@RequestMapping(value = "close")
		@ResponseBody
		public Map<String, String> close(@RequestBody VendingReleVo vendingReleVo) {
			if ("1".equals(vendingReleVo.getAdStatus())) {
				vendingReleVo.setAdStatus("2");
			} else {
				vendingReleVo.setAdStatus("1");
			}
			Map<String, String> result = vendingAdService.closeVendingAdByCode(vendingReleVo);
			return result;
		}

		@RequestMapping(value = "edit")
		@ResponseBody
		public String edit(@RequestBody String vendCode) {
			
			Integer i=1;
			List<VendingReleVo> vendingReleVo = vendingAdService.getVendingAdByCode(vendCode);
			
		for(VendingReleVo  ss : vendingReleVo){
				String[] aDIds = ss.getaDList().split(",");
				
				for(String adId : aDIds){
					
					VendingConterAdVo vendingConterAdVo	= vendingAdConterService.getVendAdByADId(adId);
					if(vendingConterAdVo!=null){
						ss.setImgPath(vendingConterAdVo.getImgPath());
						ss.setVedioPath(vendingConterAdVo.getVedioPath());
						ss.setAdType(vendingConterAdVo.getAdType());
						ss.setImgName(vendingConterAdVo.getImgName());
						ss.setSort(i);
						i++;
					}
				}
		}
			
			return JsonMapper.toJsonString(vendingReleVo);
		}
		
		
		
		//@RequestMapping(value = "vendAdList")
		@ResponseBody
		
		public String list(@RequestBody VendingConterAdVo vendingConterAdVo) {
			
			String aDList = vendingConterAdVo.getaDList();
			String[] strings = aDList.split(",");
			
			List<VendingConterAdVo>aa=new ArrayList<VendingConterAdVo>();
			for(String adId : strings){
				VendingConterAdVo vo = vendingAdConterService.getVendingConterAdByCode(adId);
				aa.add(vo);
			}	
		    
			return JsonMapper.toJsonString(aa);
		}
		
	@RequestMapping(value = "saveSort")
	@ResponseBody
	public String saveSort(@RequestBody StatistVo statistVo) {
			Map<String, String> result = new HashMap<String, String>();
			String aDList = statistVo.getaDList();
			/*String num = statistVo.getNum();
			String[] nums = num.split(",");*/
			String[] strings = aDList.split(",");
			for(int i =0;i<strings.length; i=i+3){
				statistVo.setVendCode(strings[i]);
				statistVo.setAdId(strings[i+1]);
				statistVo.setSort(strings[i+2]);
				result = vendingAdConterService.updateSortByvendCode(statistVo);
			}
			
			
			return JsonMapper.toJsonString(result);
		}
	
		
		

		@RequestMapping(value = { "vendAdList" })
		public String list(VendingConterAdVo vendingConterAdVo, HttpServletRequest request, HttpServletResponse response, Model model) {
			/*String getaDList = vendingConterAdVo.getaDList();
			Page<VendingConterAdVo> pages = null;
			String[] split = getaDList.split(",");
			for(String str : split ){
				vendingConterAdVo.setAdId(str);
	}			
			Page<VendingConterAdVo> page = vendingAdConterService.getVendingConterAdPage(new Page<VendingConterAdVo>(request, response),
						vendingConterAdVo);
			
			model.addAttribute("page", page);
			
			model.addAttribute("vendingConterAdVo", vendingConterAdVo);
			return "/hcoffee/vending/vendingAdReleList";*/
			
			String aDList = vendingConterAdVo.getaDList();
			String vendCode = vendingConterAdVo.getVendCode();
			String[] strings = aDList.split(",");
			//List<VendingConterAdVo>aa=new ArrayList<VendingConterAdVo>();
			
			List<StatistVo> aa = vendingAdConterService.getVendListConterAdByCode(vendCode);
			for(StatistVo Strs : aa){
				
				VendingConterAdVo vo = vendingAdConterService.getVendingConterAdByCode(Strs.getAdId());
				
				if(vo!=null){
					Strs.setAdType(vo.getAdType());
				}
			}
			
		/*	String sort = null;
			for(StatistVo strs : StatistVos){
			 sort = strs.getSort();
			}
			
			for(String adId : strings){
				VendingConterAdVo vo = vendingAdConterService.getVendingConterAdByCode(adId);
				vo.setNum(sort);
				aa.add(vo);
			}	*/
			 model.addAttribute("aa", aa);
			 model.addAttribute("aDList", aDList);
			 model.addAttribute("vendCode", vendCode);
			
			return "/hcoffee/vending/vendingAdReleList";
		}
}
