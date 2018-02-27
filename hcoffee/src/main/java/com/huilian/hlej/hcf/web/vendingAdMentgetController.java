package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.hcf.common.utils.FileUploadUtil;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.service.VendingAdService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.VendingAdVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 广告管理控制器
 * 
 * @author xiekangjian
 * @date 2017年2月4日 上午10:11:14
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingAdMentget")
public class vendingAdMentgetController extends BaseController {
	private final long fileSize = 1024 * 1024 * 50;
	private final long fileSize1 = 1024 * 1024 * 1;

	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	private VendingAdService vendingAdService;

	@Autowired
	FileHandler fileHandler;

	/*@RequestMapping(value = { "list" })
	public String list(VendingAdVo vendingAdVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (user != null && !StringUtils.isBlank(user.getUserType()) && !"admin".equals(user.getLoginName())) {
			vendingAdVo.setChannel(BigDecimal.valueOf(Long.valueOf(user.getUserType())));
		}
		Page<VendingAdVo> page = vendingAdService.getVendingAdPage(new Page<VendingAdVo>(request, response),
				vendingAdVo);
		model.addAttribute("page", page);
		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("activitySchemeList", vendingMachineService.getActivityScheme());
		model.addAttribute("communityList", vendingMachineService.getCommunity());
		model.addAttribute("appVersionList", vendingMachineService.getAppVersionRecords());
		model.addAttribute("machineCodeList", vendingMachineService.getMachineCode());
		vendingAdVo.setStartTime(null);
		vendingAdVo.setEndTime(null);
		model.addAttribute("vendingAdVo", vendingAdVo);
		return "/hcoffee/vending/vendingAdMentget";
	}
*/
/*	@RequestMapping(value = "edit")
	@ResponseBody
	public String edit(@RequestBody String vendCode) {
		VendingAdVo vendingAdVo = vendingAdService.getVendingAdByCode(vendCode);
		return JsonMapper.toJsonString(vendingAdVo);
	}
*/
	@RequestMapping(value = "editUpdate")
	@ResponseBody
	public String ideUpdate(@RequestBody String vendCode) {
		VendingMachine vendingMachineVo = vendingAdService.getChanelComtityByCode(vendCode);
		return JsonMapper.toJsonString(vendingMachineVo);
	}

	/*@RequestMapping(value = "close")
	@ResponseBody
	public Map<String, String> close(@RequestBody VendingAdVo vendingAdVo) {
		if (vendingAdVo.getAdStatus() == 1) {
			vendingAdVo.setAdStatus(2);
		} else {
			vendingAdVo.setAdStatus(1);
		}
		Map<String, String> result = vendingAdService.closeVendingAdByCode(vendingAdVo);
		return result;
	}*/

	/*@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, String> update(VendingAdVo vendingAdVo, Model model, MultipartFile file1, MultipartFile file2,
			MultipartFile file3, MultipartFile video) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (file1 != null && file1.getOriginalFilename() != "") {
				result = validateFile1(file1);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath1(uploadFile(file1, new FileInfoVo()));
			}
			if (file2 != null && file2.getOriginalFilename() != "") {
				result = validateFile1(file2);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath2(uploadFile(file2, new FileInfoVo()));
			}
			if (file3 != null && file3.getOriginalFilename() != "") {

				result = validateFile1(file3);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath3(uploadFile(file3, new FileInfoVo()));
			}

			if (video != null && video.getOriginalFilename() != "") {
				 result = validateFile(video);
				  if(result.size()>0){
					  return result;
				  }

				vendingAdVo.setVedioPath(uploadFile(video, new FileInfoVo()));
			}

			result = vendingAdService.updateVendingAdById(vendingAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}*/

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, String> save(VendingAdVo vendingAdVo, Model model, MultipartFile file1, MultipartFile file2,
			MultipartFile file3, MultipartFile video) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		VendingMachine vendingMachine = vendingMachineService.getVendingMachineByCode(vendingAdVo.getVendCode());
		if (vendingAdService.getVendingAdByCode(vendingAdVo.getVendCode()) != null) {
			result.put("code", "2");
			result.put("msg", "此机器已经添加，请选择更新!");
			return result;
		}
		if (vendingMachine == null) {
			result.put("code", "2");
			result.put("msg", "此机器不存在,请确认后重新输入!");
			return result;
		}
		try {
			if (file1 != null && file1.getOriginalFilename() != "") {
				result = validateFile1(file1);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath1(uploadFile(file1, new FileInfoVo()));
			}
			if (file2 != null && file2.getOriginalFilename() != "") {

				result = validateFile1(file2);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath2(uploadFile(file2, new FileInfoVo()));
			}
			if (file3 != null && file3.getOriginalFilename() != "") {
				result = validateFile1(file3);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setImgPath3(uploadFile(file3, new FileInfoVo()));
			}
			if (video != null && video.getOriginalFilename() != "") {
				result = validateFile(video);
				if (result.size() > 0) {
					return result;
				}

				vendingAdVo.setVedioPath(uploadFile(video, new FileInfoVo()));
			}
			result = vendingAdService.addVendingAd(vendingAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

/*	@RequestMapping(value = "batchEdit")
	@ResponseBody
	public Map<String, String> batchUpgrade(VendingAdVo vendingAdVo, Model model, MultipartFile file1,
			MultipartFile file2, MultipartFile file3, MultipartFile video) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (file1 != null && file1.getOriginalFilename() != "") {
				vendingAdVo.setImgPath1(uploadFile(file1, new FileInfoVo()));
			}
			if (file2 != null && file2.getOriginalFilename() != "") {
				vendingAdVo.setImgPath2(uploadFile(file2, new FileInfoVo()));
			}
			if (file3 != null && file3.getOriginalFilename() != "") {
				vendingAdVo.setImgPath3(uploadFile(file3, new FileInfoVo()));
			}
			if (video != null && video.getOriginalFilename() != "") {
				vendingAdVo.setVedioPath(uploadFile(video, new FileInfoVo()));
			}
			result = vendingAdService.batchUpdate(vendingAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}*/

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

	private Map<String, String> validateFile(MultipartFile video) {
		Map<String, String> result = new HashMap<String, String>();
		if (video == null || "".equals(video.getOriginalFilename())) {// 验证是否选择了视频文件
			result.put("code", "2");
			result.put("msg", "视频文件为空，请选择!");
			return result;
		} else if (video.getSize() > this.fileSize) {// 验证文件大小
			result.put("code", "2");
			result.put("msg", "视频文件过大，请重新选择!");
			return result;
		} else {// 验证文件格式
			final HashSet<String> set = new HashSet<String>() {
				{
					add("mp4");
				/*	add("flv");
					add("avi");
					add("rm");
					add("rmvb");
					add("wmv");*/
				}
			};
			String extName = video.getOriginalFilename().substring(video.getOriginalFilename().indexOf(".") + 1)
					.toLowerCase().trim();
			if (!set.contains(extName)) {
				result.put("code", "2");
				result.put("msg", "文件格式不对，请重新选择!");
				return result;
			}
		}
		return result;
	}

	// 图片验证
	private Map<String, String> validateFile1(MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		if (file == null || "".equals(file.getOriginalFilename())) {// 验证是否选择了视频文件
			result.put("code", "2");
			result.put("msg", "图片文件为空，请选择!");
			return result;
		} else if (file.getSize() > this.fileSize1) {// 验证文件大小
			result.put("code", "2");
			result.put("msg", "图片文件过大，请重新选择!");
			return result;
		}
		return result;
	}

	/**
	 * 导出广告数据
	 *
	 * @param vendingAdVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
/*	/// @RequiresPermissions("list")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(VendingAdVo vendingAdVo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "广告管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<VendingAdVo> page = vendingAdService.getVendingAdPage(new Page<VendingAdVo>(request, response),
					vendingAdVo);
			List<Integer> widths = Arrays.asList(2000, 2400, 0, 0, 2400, 0);
			new HCFExportExcel("广告管理", VendingAdVo.class, widths).setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendingAd/list?repage";
	}

	/// @RequiresPermissions("list")
	@RequestMapping(value = "qbexport", method = RequestMethod.POST)
	public String exportQbFile(VendingAdVo vendingAdVo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "广告管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<VendingAdVo> page = vendingAdService.getVendingAdPage(new Page<VendingAdVo>(request, response, "str"),
					vendingAdVo);
			List<Integer> widths = Arrays.asList(2000, 2400, 0, 0, 2400, 0);
			new HCFExportExcel("广告管理", VendingAdVo.class, widths).setDataList(page.getList()).write(response, fileName)
					.dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/vendingAd/list?repage";
	}*/

	@RequestMapping(value = "uploadFile", method = RequestMethod.POST, headers = "Accept=application/json")
	// 处理上传的
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") CommonsMultipartFile file) throws IOException {
		PrintWriter out;
		boolean flag = false;
		if (file.getSize() > 0) {
			String path = "/assets/upload/files/";
			String uploadPath = request.getSession().getServletContext().getRealPath(path);
			try {
				// 文件上传的位置可以自定义
				flag = FileUploadUtil.uploadFile(file, request);
			} catch (Exception e) {
			}

		}
		out = response.getWriter();
		if (flag == true) {
			out.print("1");
		} else {
			out.print("2");
		}
	}

}
