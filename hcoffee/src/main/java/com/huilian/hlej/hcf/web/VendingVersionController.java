package com.huilian.hlej.hcf.web;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.service.VendingVersionService;
import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 售货机版本控制器
 * 
 * @author xiekangjian
 * @date 2016年12月30日 下午3:02:30
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingVersion")
public class VendingVersionController extends BaseController {
	@Autowired
	FileHandler fileHandler;

	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	private VendingVersionService vendingVersionService;
	
	private Map<String, Object> queryRecordMap = new HashMap<String, Object>();

	@RequestMapping(value = { "list" })
	public String list(AppVersionRecordsVo appVersionRecordsVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AppVersionRecordsVo> page = vendingVersionService
				.getVendingVersionPage(new Page<AppVersionRecordsVo>(request, response), appVersionRecordsVo);
		model.addAttribute("page", page);
		model.addAttribute("appVersionRecordsVo", appVersionRecordsVo);
		appVersionRecordsVo.setStartTime(null);
		appVersionRecordsVo.setEndTime(null);
		return "/hcoffee/vending/vendingVersionList";
	}

	@RequestMapping(value = "upgrade")
	@ResponseBody
	public String upgrade(@RequestBody AppUpgradeTask appUpgradeTask, Model model) {
		Map<String, String> result = vendingMachineService.saveAppUpgradeTask(appUpgradeTask);
		return JsonMapper.toJsonString(result);
	}

	@RequestMapping(value = { "versionList" })
	public String list(VendingMachineVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<VendingMachineVo> page = vendingMachineService
				.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		Page<VendingMachineVo> pages = vendingMachineService
				.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
		queryRecordMap.put("allList", pages.getList());
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
		List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
		model.addAttribute("communityList", vendingMachineService.getCommunity());
		model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
		model.addAttribute("equipSupplyList",
				Arrays.asList(new EquipSupplyVo("100", "凯欣达供应"), new EquipSupplyVo("111", "便捷神供应")));
		model.addAttribute("appVersionList", vendingMachineService.getAppVersionRecords());
		List<AppVersionRecords> appVersionRecords = vendingMachineService.getAppVersionRecords();
		// model.addAttribute("appVersionList",vendingMachineService.getAappVersion());
		// List<Map<String, String>> aappVersion =
		// vendingMachineService.getAappVersion();
		vendingMachineVo.setStartTime(null);
		vendingMachineVo.setEndTime(null);
		model.addAttribute("vendingMachineVo", vendingMachineVo);
		return "/hcoffee/vending/versionList";
	}

	@RequestMapping(value = "upgradeRecord")
	public String upgradeList(AppUpgradeRecordVo appUpgradeRecordVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AppUpgradeRecordVo> page = vendingMachineService
				.getAppUpgradeRecordPage(new Page<AppUpgradeRecordVo>(request, response), appUpgradeRecordVo);
		model.addAttribute("upgradeRecordPage", page);
		model.addAttribute("vendCode", appUpgradeRecordVo.getVendCode());
		return "/hcoffee/vending/upgradeRecordList";
	}

	@RequestMapping(value = { "save" })
	@ResponseBody
	public Map<String, String> save(AppVersionRecordsVo appVersionRecordsVo, HttpServletRequest request, Model model,
			MultipartFile file) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String downloadLink = "";
			if (file != null && file.getOriginalFilename() != "") {
				downloadLink = "https://image.huicoffee.com.cn/" + uploadFile(file, new FileInfoVo());
			} else {
				result.put("code", "1");
				result.put("msg", "不能上传空文件");
				return result;
			}

			appVersionRecordsVo.setDownloadLink(downloadLink);
			result = vendingVersionService.saveVendingVersion(appVersionRecordsVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public String update(AppVersionRecordsVo appVersionRecordsVo, Model model, MultipartFile file) {
		String downloadLink = "";
		if (file != null && file.getOriginalFilename() != "") {
			try {
				downloadLink = "https://image.huicoffee.com.cn/" + uploadFile(file, new FileInfoVo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		appVersionRecordsVo.setDownloadLink(downloadLink);

		Map<String, String> result = vendingVersionService.updateVendingVersion(appVersionRecordsVo);
		return JsonMapper.toJsonString(result);
	}

	@RequestMapping(value = "edit")
	@ResponseBody
	public String edit(@RequestBody String version) {
		AppVersionRecordsVo appVersionRecordsVo = vendingVersionService.getVendingVersionByVersion(version);
		return JsonMapper.toJsonString(appVersionRecordsVo);
	}

	@RequestMapping(value = "batchUpgrade")
	@ResponseBody
	public String batchUpgrade(@RequestBody AppUpgradeTask appUpgradeTask, Model model) {
		Map<String, String> result = vendingMachineService.batchUpgrade(appUpgradeTask);
		return JsonMapper.toJsonString(result);
	}

	@RequestMapping(value = "deleted")
	@ResponseBody
	public String deleted(@RequestBody String version) {
		Map<String, String> result = vendingVersionService.deleteVendingVersionByVersion(version);
		return JsonMapper.toJsonString(result);
	}

	public static void main(String[] args) {
		String str = "huicoffee_1.1.2.apk";
		System.out.println(str.indexOf("_"));
		System.out.println(str.lastIndexOf("."));
		System.err.println(str.substring(str.indexOf("_") + 1, str.lastIndexOf(".")));
		System.out.println(Class.class.getClass().getResource("/").getPath());
		// 类的绝对路径
		File appFile = new File(new File(Class.class.getClass().getResource("/").getPath()).getParent() + "/upgrate/");
		System.out.println("上传文件的保存目录为 : " + appFile.getPath());
	}

	/**
	 * 导出售货机版本数据
	 *
	 * @param appVersionRecordsVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	/// @RequiresPermissions("list")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(AppVersionRecordsVo appVersionRecordsVo, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "售货机版本" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<AppVersionRecordsVo> page = vendingVersionService
					.getVendingVersionPage(new Page<AppVersionRecordsVo>(request, response), appVersionRecordsVo);
			List<Integer> widths = Arrays.asList(3600, 2400, 2400, 5200);
			new HCFExportExcel("售货机版本", AppVersionRecordsVo.class, widths).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendingVersion/list?repage";
	}

	@RequestMapping(value = "qbexport", method = RequestMethod.POST)
	public String exportQbFile(AppVersionRecordsVo appVersionRecordsVo, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "售货机版本" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<AppVersionRecordsVo> page = vendingVersionService.getVendingVersionPage(
					new Page<AppVersionRecordsVo>(request, response, "str"), appVersionRecordsVo);
			List<Integer> widths = Arrays.asList(3600, 2400, 2400, 5200);
			new HCFExportExcel("售货机版本", AppVersionRecordsVo.class, widths).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendingVersion/list?repage";
	}

	public String uploadFile(MultipartFile file, FileInfoVo fileInfo) throws Exception {
		Map<String, String> map = new HashMap<>();
		try {
			if (fileInfo == null) {
				fileInfo = new FileInfoVo();
			}
			String fileName = fileInfo.getFileName();
			if (StringUtils.isEmpty(fileName)) {
				fileName = file.getOriginalFilename();
				fileInfo.setFileName(fileName);
			}
			String dataSource = fileInfo.getDataSource();
			if (StringUtils.isEmpty(dataSource)) {
				fileInfo.setDataSource("hlej");
			}

			fileInfo = fileHandler.upload(file.getBytes(), fileInfo);
			String name = fileInfo.getFilePath();
			if (name != null) {
				map.put("code", "1");
				map.put("msg", "成功");
				map.put("path", name);
				map.put("fileName", file.getOriginalFilename());
				return name;
			} else {
				map.put("code", "-1");
				map.put("msg", "失败");
				map.put("path", "");
				map.put("fileName", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "-1");
			map.put("msg", "失败");
			map.put("path", "");
			map.put("fileName", "");
			throw new Exception(e);
		}
		return "";
	}

	/**
	 * 全部更新
	 * @param appUpgradeTask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "allUpdate")
	@ResponseBody
	public String allUpdate(@RequestBody AppUpgradeTask appUpgradeTask, Model model,HttpServletRequest request,
			HttpServletResponse response) {
		List<VendingMachineVo> list = (List<VendingMachineVo>) queryRecordMap.get("allList");
		Map<String, String> result = vendingMachineService.allUpdate(list,appUpgradeTask);
		return JsonMapper.toJsonString(result);
	}

}
