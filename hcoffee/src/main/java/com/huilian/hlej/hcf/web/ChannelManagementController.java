package com.huilian.hlej.hcf.web;

import java.io.File;
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
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.CommunityVo;
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
@RequestMapping(value = "${adminPath}/hcf/channelManagement")
public class ChannelManagementController extends BaseController {

	@Autowired
	private ChannelManagementService channelManagementService;

	@RequestMapping(value = { "list" })
	public String list(ChannelVo channelVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ChannelVo> page = channelManagementService.getChannelManagementPage(new Page<ChannelVo>(request, response),
				channelVo);
		model.addAttribute("page", page);
		model.addAttribute("channelVo", channelVo);
		channelVo.setStartTime(null);
		channelVo.setEndTime(null);
		return "/hcoffee/vending/channelManagement";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, String> save(@RequestBody ChannelVo channelVo) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		result = this.validateVO(channelVo.getChannelId(), null);
		if (result.size() > 0) {
			return result;
		}
		result = this.validateVO(null, channelVo.getChannelName());
		if (result.size() > 0) {
			return result;
		}

		channelVo.setDataSource(3);// 数据来源默认设为3

		if (channelVo.getStatusList().equals("开启")) {
			channelVo.setDataStatus(0);

		} else {
			channelVo.setDataStatus(1);
		}

		result = channelManagementService.saveChannel(channelVo);
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, String> update(@RequestBody ChannelVo channelVo) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		ChannelVo vo = channelManagementService.getChannelById(channelVo);

		if (!vo.getChannelId().equals(channelVo.getChannelId())) {// 编码发生了更改
			result = this.validateVO(channelVo.getChannelId(), null);
			if (result.size() > 0) {
				return result;
			}
		}
		if (!vo.getChannelName().equals(channelVo.getChannelName())) {// 名称发生了更改
			result = this.validateVO(null, channelVo.getChannelName());
			if (result.size() > 0) {
				return result;
			}
		}

		if (channelVo.getStatusList().equals("开启")) {
			channelVo.setDataStatus(0);

		} else {
			channelVo.setDataStatus(1);
		}

		result = channelManagementService.updateChannelById(channelVo);
		return result;
	}

	@RequestMapping(value = "updateChannelStatus")
	@ResponseBody
	public Map<String, String> updateCommunityStatus(@RequestBody ChannelVo channelVo) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		result = channelManagementService.updateChannelStatus(channelVo);
		;
		return result;
	}

	@RequestMapping(value = "load")
	@ResponseBody
	public String load(@RequestBody ChannelVo channelVo) {
		ChannelVo vo = channelManagementService.getChannelById(channelVo);

		StringBuffer sb = new StringBuffer();

		if (vo.getDataStatus() == 1) {

			sb.append("关闭").append(",");
		} else {
			sb.append("开启").append(",");
		}
		vo.setStatusList(sb.toString());

		return JsonMapper.toJsonString(vo);
	}

	/**
	 * 验证渠道Id或者名称是否重复
	 */
	private Map<String, String> validateVO(BigDecimal channelId, String channelName) {
		Map<String, String> result = new HashMap<String, String>();
		ChannelVo cu = new ChannelVo();
		if (channelId != null && !"".equals(channelId)) {
			cu.setChannelId(channelId);
		} else if (channelName != null && !"".equals(channelName)) {
			cu.setChannelName(channelName);

		}
		ChannelVo vo = channelManagementService.getChannel(cu);
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
	/// @RequiresPermissions("list")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ChannelVo channelVo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "渠道数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ChannelVo> page = channelManagementService.getChannelList(new Page(request, response), channelVo);
			List<Integer> widths = Arrays.asList(2000, 2400, 0, 0, 2400, 0);
			new HCFExportExcel("渠道数据", ChannelVo.class, widths).setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出渠道数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/channelManagement/list?repage";
	}

	@RequestMapping(value = "qbexport", method = RequestMethod.POST)
	public String exportQbFile(ChannelVo channelVo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "渠道数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ChannelVo> page = channelManagementService.getChannelList(new Page(request, response, "str"),
					channelVo);
			List<Integer> widths = Arrays.asList(2000, 2400, 0, 0, 2400, 0);
			new HCFExportExcel("渠道数据", ChannelVo.class, widths).setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出渠道数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/channelManagement/list?repage";
	}

}
