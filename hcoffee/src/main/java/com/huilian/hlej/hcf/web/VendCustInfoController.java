package com.huilian.hlej.hcf.web;

import java.io.UnsupportedEncodingException;
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
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.service.VendCustInfoService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.VendingStateService;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.VendCustInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 用户查询
 * 
 * @author yangbo
 * @date 2017年3月1日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendCustQuery")
public class VendCustInfoController extends BaseController {

	@Autowired
	private VendingStateService vendingStateService;
	
	@Autowired
	private VendCustInfoService vendCustInfoService;

	@Autowired
	private VendingMachineService vendingMachineService;

	@RequestMapping(value = { "list" })
	public String list(VendCustInfoVo vendCustInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) throws UnsupportedEncodingException {
		Page<VendCustInfoVo> page = vendCustInfoService.getVendCustQuery(new Page<VendCustInfoVo>(request, response),
				vendCustInfo);
		List<Channel> lst = vendingMachineService.getChannel();
		 List<CommunityVo> communityName = vendCustInfoService.getCommunityName();
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> maps = new HashMap<String, String>();
		List<VendCustInfoVo> list = page.getList();
		for (CommunityVo ss : communityName) {
			maps.put(ss.getCommunityId(),ss.getCommunityName());
		}

		for (Channel c : lst) {
			map.put(c.getChannelId().toString(), c.getChannelName());
		}
		
		for (VendCustInfoVo vo : list) {
			String communityId = vo.getCommunityId();
			String channelId = vo.getChannelId();
			if (map.containsKey(channelId)) {
				vo.setChannelName(map.get(channelId));
			} else if(maps.containsKey(communityId)){
				vo.setCommunityName(maps.get(communityId));
				
			}
			
		}

		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("communityList", vendCustInfoService.getCommunityName());
		model.addAttribute("page", page);
		model.addAttribute("vendCustInfo", vendCustInfo);
		vendCustInfo.setStartTime(null);
		vendCustInfo.setEndTime(null);
		
		return "/hcoffee/vending/custInfo";
	}


	/**
	 * 导出数据
	 *
	 * @param communityVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	/// @RequiresPermissions("list")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(VendCustInfoVo vendCustInfo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户查询数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<VendCustInfoVo> page = vendCustInfoService.getVendCustQuery(new Page(request, response), vendCustInfo);
			List<Channel> lst = vendingMachineService.getChannel();
			 List<CommunityVo> communityName = vendCustInfoService.getCommunityName();
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> maps = new HashMap<String, String>();
			List<VendCustInfoVo> list = page.getList();
			for (CommunityVo ss : communityName) {
				maps.put(ss.getCommunityId(),ss.getCommunityName());
			}

			for (Channel c : lst) {
				map.put(c.getChannelId().toString(), c.getChannelName());
			}
			
			for (VendCustInfoVo vo : list) {
				String communityId = vo.getCommunityId();
				String channelId = vo.getChannelId();
				if (map.containsKey(channelId)) {
					vo.setChannelName(map.get(channelId));
				} else if(maps.containsKey(communityId)){
					vo.setCommunityName(maps.get(communityId));
					
				}
				
			}
			List<Integer> widths = Arrays.asList(3600, 0, 0, 0, 0, 2400, 2400, 0, 0, 0);
			new HCFExportExcel("用户查询数据", VendCustInfoVo.class, widths).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出机用户查询数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendCustQuery/list?repage";
	}

	@RequestMapping(value = "qbexport", method = RequestMethod.POST)
	public String exportQbFile(VendCustInfoVo vendCustInfo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户查询数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<VendCustInfoVo> page = vendCustInfoService.getVendCustQuery(new Page(request, response, "str"),
					vendCustInfo);
			List<Channel> lst = vendingMachineService.getChannel();
			 List<CommunityVo> communityName = vendCustInfoService.getCommunityName();
			
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> maps = new HashMap<String, String>();
			List<VendCustInfoVo> list = page.getList();
			for (CommunityVo ss : communityName) {
				maps.put(ss.getCommunityId(),ss.getCommunityName());
			}

			for (Channel c : lst) {
				map.put(c.getChannelId().toString(), c.getChannelName());
			}
			
			for (VendCustInfoVo vo : list) {
				String communityId = vo.getCommunityId();
				String channelId = vo.getChannelId();
				if (map.containsKey(channelId)) {
					vo.setChannelName(map.get(channelId));
				} else if(maps.containsKey(communityId)){
					vo.setCommunityName(maps.get(communityId));
					
				}
				
			}
			List<Integer> widths = Arrays.asList(3600, 0, 0, 0, 0, 2400, 2400, 0, 0, 0);
			new HCFExportExcel("用户查询数据", VendCustInfoVo.class, widths).setDataList(page.getList())
					.write(response, fileName).dispose();

			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户查询数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendCustQuery/list?repage";
	}
}
