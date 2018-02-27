package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.huilian.hlej.hcf.common.utils.FileUploadUtil;
import com.huilian.hlej.hcf.service.VendingAdConterService;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.EquipSupplyVo;
import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.mapper.JsonMapper;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 广告管理控制器
 * 
 * @author xiekangjian
 * @date 2017年2月4日 上午10:11:14
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/vendingConterAdMentget")
public class vendingAdConterMentgetController extends BaseController {
	private final long fileSize = 1024 * 1024 * 50;
	private final long fileSize1 = 1024 * 1024 * 1;
	@Autowired
	private VendingAdConterService vendingAdConterService;

	@Autowired
	private VendingMachineService vendingMachineService;

	@Autowired
	FileHandler fileHandler;

	@RequestMapping(value = { "list" })
	public String list(VendingConterAdVo vendingConterAdVo, HttpServletRequest request, HttpServletResponse response,
			Model model) throws ParseException {

		Page<VendingConterAdVo> page = vendingAdConterService
				.getVendingConterAdPage(new Page<VendingConterAdVo>(request, response), vendingConterAdVo);
		/*
		 * java.util.Date beginDate; java.util.Date endDate; long day=0;
		 * 
		 * List<VendingConterAdVo> list = page.getList(); for(VendingConterAdVo
		 * as : list){ if(as.getStartTime()!=null && as.getEndTime()!=null){
		 * //Date startTime = as.getStartTime(); //Date endTime =
		 * as.getEndTime(); SimpleDateFormat df = new
		 * SimpleDateFormat("yyyy-MM-dd"); String startDate =
		 * df.format(as.getStartTime()); String endDatea =
		 * df.format(as.getEndTime()); beginDate = df.parse(startDate); endDate=
		 * df.parse(endDatea);
		 * day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000); } }
		 */
		model.addAttribute("page", page);
		// 广告播放时长改为输入框
		// model.addAttribute("playList", Arrays.asList(new PlayVo("1","5"),new
		// OrderVo("2","10"),new OrderVo("3","15"),new OrderVo("4","20")));

		model.addAttribute("vendingConterAdVo", vendingConterAdVo);
		return "/hcoffee/vending/vendingConterAdMentget";
	}

	@RequestMapping(value = "edit")
	@ResponseBody
	public String edit(@RequestBody String adId) {
		VendingConterAdVo VendingConterAdVo = vendingAdConterService.getVendingConterAdByCode(adId);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimes = df.format(VendingConterAdVo.getStartTime());
		String endTimes = df.format(VendingConterAdVo.getEndTime());
		VendingConterAdVo.setStartTimes(startTimes);
		VendingConterAdVo.setEndTimes(endTimes);
		return JsonMapper.toJsonString(VendingConterAdVo);
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, String> update(VendingConterAdVo vendingConterAdVo, Model model, MultipartFile file1,
			MultipartFile video) {
		Map<String, String> result = new HashMap<String, String>();

		try {
			if (file1 != null && file1.getOriginalFilename() != "") {
				result = validateFile1(file1);
				if (result.size() > 0) {
					return result;
				}
				vendingConterAdVo.setImgPath(uploadFile(file1, new FileInfoVo()));
			}

			if (video != null && video.getOriginalFilename() != "") {
				result = validateFile(video);
				if (result.size() > 0) {
					return result;
				}

				vendingConterAdVo.setVedioPath(uploadFile(video, new FileInfoVo()));
			}

			result = vendingAdConterService.updateVendingConterAd(vendingConterAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, String> delete(@RequestBody VendingConterAdVo VendingConterAdVo) {

		Map<String, String> result = vendingAdConterService.deleteVendingAdByCode(VendingConterAdVo);
		return result;

	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, String> save(VendingConterAdVo vendingConterAdVo, Model model, MultipartFile file1,
			MultipartFile video) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		VendingConterAdVo vendingConterAd = vendingAdConterService.getVendAdByADId(vendingConterAdVo.getAdId());
		VendingConterAdVo vendingContera = vendingAdConterService
				.getVendingConterAdByadId(vendingConterAdVo.getImgName());
		if (vendingConterAd != null) {
			if (vendingConterAd.getAdId() != null) {
				result.put("code", "1");
				result.put("msg", "该广告编码已经存在");
				return result;
			}
		}
		if (vendingContera != null) {
			if (vendingContera.getImgName() != null) {
				result.put("code", "1");
				result.put("msg", "该广告名称已经存在");
				return result;

			}
		}

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 * 
		 * vendingConterAdVo.setStartTime(df.format(vendingConterAdVo.
		 * getStartTime()));
		 */
		// String formatDate = df.format(vendingConterAdVo.getStartTime());
		/*
		 * VendingMachine vendingMachine =
		 * vendingMachineService.getVendingMachineByCode(vendingAdVo.getVendCode
		 * ()); if
		 * (vendingAdService.getVendingAdByCode(vendingAdVo.getVendCode()) !=
		 * null) { result.put("code", "2"); result.put("msg", "此机器已经添加，请选择更新!");
		 * return result; } if (vendingMachine == null) { result.put("code",
		 * "2"); result.put("msg", "此机器不存在,请确认后重新输入!"); return result; }
		 */

		vendingConterAdVo.setAdStatus("1");
		vendingConterAdVo.setGroundType("2");
		try {
			if (file1 != null && file1.getOriginalFilename() != "") {
				result = validateFile1(file1);
				if (result.size() > 0) {
					return result;
				}
				// vendingConterAdVo.setImgPath(uploadFile(file1, new
				// FileInfoVo()));
				vendingConterAdVo.setImgPath(uploadFile(file1, new FileInfoVo()));
			}

			if (video != null && video.getOriginalFilename() != "") {
				result = validateFile(video);
				if (result.size() > 0) {
					return result;
				}
				// vendingConterAdVo.setImgPath(uploadFile(video, new
				// FileInfoVo()));
				vendingConterAdVo.setVedioPath(uploadFile(video, new FileInfoVo()));
			}

			result = vendingAdConterService.addVendingConterAd(vendingConterAdVo);
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
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
					/*
					 * add("flv"); add("avi"); add("rm"); add("rmvb");
					 * add("wmv");
					 */
				}
			};
			String extName = video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf(".") + 1)
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

	@RequestMapping(value = { "shebeiList" })
	public String list(VendingMachineVo vendingMachineVo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String vendCodeList = vendingMachineVo.getVendCodeList();
		Page<VendingMachineVo> page = vendingMachineService
				.getVendingMachinePage(new Page<VendingMachineVo>(request, response), vendingMachineVo);
		Page<VendingMachineVo> pages = vendingMachineService
				.getVendingMachinePage(new Page<VendingMachineVo>(request, response, "str"), vendingMachineVo);
		model.addAttribute("page", page);
		model.addAttribute("vendCodeList", vendCodeList);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("channelList", vendingMachineService.getChannel());
		model.addAttribute("provinceNameList", vendingMachineService.getprovinceName());
		List<Map<String, String>> getprovinceName = vendingMachineService.getprovinceName();
		model.addAttribute("communityList", vendingMachineService.getCommunity());
		model.addAttribute("deliveryTypeList", vendingMachineService.getDeliveryType());
		model.addAttribute("equipSupplyList",
				Arrays.asList(new EquipSupplyVo("100", "凯欣达供应"), new EquipSupplyVo("111", "便捷神供应")));
		model.addAttribute("appVersionList", vendingMachineService.getAppVersionRecords());
		vendingMachineVo.setStartTime(null);
		vendingMachineVo.setEndTime(null);
		model.addAttribute("vendingMachineVo", vendingMachineVo);
		return "/hcoffee/vending/vendingConterAdList";
	}

	/**
	 * 发布到指定的机器
	 * @param fist
	 * @param vendCodeList
	 * @param aDList
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "vendList")
	@ResponseBody
	public String batchUpgrade(@RequestBody String fist, String vendCodeList, String aDList, Model model) {

		String decode = URLDecoder.decode(URLDecoder.decode(vendCodeList));
		String aDList1 = URLDecoder.decode(URLDecoder.decode(aDList));
		// String template =
		// URLDecoder.decode(URLDecoder.decode(templateIds),"UTF-8");
		String[] vendList = decode.split(",");
		String[] aDLists = aDList1.split(",");
		// 要把发布中修改为已发布

		VendingConterAdVo sd = new VendingConterAdVo();
		VendingReleVo vendingReleVo = new VendingReleVo();
		StatistVo statis = new StatistVo();

		/*for (String stra : vendList) {

			List<StatistVo> listConterAdByCode = vendingAdConterService.getVendListConterAdByCode(stra);
			sd.setVendCode(stra);
			for (String adList : aDLists) {

				VendingConterAdVo vendAdByADId = vendingAdConterService.getVendAdByADId(adList);
				// sd.setCreateTime(vendAdByADId.getCreateTime());
				sd.setImgName(vendAdByADId.getImgName());
				sd.setAdId(adList);
				sd.setPlayTime(vendAdByADId.getPlayTime());
				vendingAdConterService.addVendingStatistAd(sd);

			}
		}*/

		Map<String, String> reulte = null;

		for (String str : vendList) {
			// 先查询如果该机器存在便更新
			sd.setVendCode(str);
			vendingReleVo.setaDList(aDList);
			vendingReleVo.setVendCode(str);
			List<VendingReleVo> vendingRele = vendingAdConterService.getVendReleByVendCode(str);
			// 保存工作
			vendingReleVo.setAdStatus("1");
			String addAdList = vendingReleVo.getaDList();
			Map<String, String> param = new HashMap<String, String>();
			if (vendingRele != null && vendingRele.size() > 0) {
				if(addAdList.indexOf(",") != -1){//多个广告
					String[] adIdArr = addAdList.split(","); 
					for(String adId : adIdArr){
						Map<String, Object> map = vendingAdConterService.queryParamMap(adId);
						if (map != null) {
							sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
							sd.setAdId(adId);
							sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
							param.put("vendCode", str);
							param.put("adId", adId);
							boolean isExistFlag = vendingAdConterService.isExist(param);
							if(!isExistFlag){//不存在该广告
								vendingAdConterService.addVendingStatistAd(sd);
							}
						}
					}
				}else{//单个广告
					param.put("vendCode", str);
					param.put("adId", addAdList);
					boolean isExistFlag = vendingAdConterService.isExist(param);
					if(!isExistFlag){
						Map<String, Object> map = vendingAdConterService.queryParamMap(aDList);
						if (map != null) {
							sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
							sd.setAdId(aDList);
							sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
							vendingAdConterService.addVendingStatistAd(sd);
						}
					}
				}
				String beforeAdList = vendingAdConterService.queryBeforAdList(vendingReleVo.getVendCode());
				StringBuffer newADList = new StringBuffer();
				if(beforeAdList != null && !"".equals(beforeAdList)){
					newADList.append(beforeAdList);
				}
				if(aDLists != null && aDLists.length >0){
					int len = aDLists.length;
					for(int i=0;i<len;i++){
						if(isContains(beforeAdList,aDLists[i])){//如果机器上已经存在该广告
							newADList.append(",").append(aDLists[i]);
						}
					}
				}else{
					if(isContains(beforeAdList,aDList)){//如果机器上已经存在该广告
						newADList.append(",").append(aDList);
					}
				}
				String finalAdList = "";
				if(newADList.length() != 0){
					finalAdList =  newADList.toString();
				}else{
					finalAdList = aDList;
				}
				vendingReleVo.setaDList(finalAdList);
				reulte = vendingAdConterService.updateVendingReleAd(vendingReleVo);
			} else {
				// 添加
				if(addAdList.indexOf(",") != -1){//多个广告
					String[] adIdArr = addAdList.split(","); 
					for(String adId : adIdArr){
						Map<String, Object> map = vendingAdConterService.queryParamMap(adId);
						if (map != null) {
							sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
							sd.setAdId(adId);
							sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
							param.put("vendCode", str);
							param.put("adId", adId);
							boolean isExistFlag = vendingAdConterService.isExist(param);
							if(!isExistFlag){//不存在该广告
								vendingAdConterService.addVendingStatistAd(sd);
							}
						}
					}
				}else{//单个广告
					param.put("vendCode", str);
					param.put("adId", addAdList);
					boolean isExistFlag = vendingAdConterService.isExist(param);
					if(!isExistFlag){
						Map<String, Object> map = vendingAdConterService.queryParamMap(aDList);
						if (map != null) {
							sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
							sd.setAdId(aDList);
							sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
							vendingAdConterService.addVendingStatistAd(sd);
						}
					}
				}
				reulte = vendingAdConterService.saveVendingReleAd(vendingReleVo);
			}
		}

		for (String adList : aDLists) {
			sd.setAdStatus("2");
			sd.setAdId(adList);
			vendingAdConterService.updateByAdId(sd);

		}

		return JsonMapper.toJsonString(reulte);
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

	@RequestMapping(value = "/pulishAllMainch")
	@ResponseBody
	public Map<String, String> pulishAllMainch(HttpServletRequest request) throws IOException {
		String aDList = request.getParameter("aDList");
		Map<String, String> result = new HashMap<String, String>();
		try {
			// 得到所有机器编码
			String vendCodes = vendingAdConterService.queryAllVendCode();
			String[] aDLists = null;
			if (aDList != null && aDList.indexOf(",") != -1) {
				aDLists = aDList.split(",");
			}
			VendingConterAdVo sd = new VendingConterAdVo();
			VendingReleVo vendingReleVo = new VendingReleVo();
			StatistVo statis = new StatistVo();

			Map<String, String> reulte = null;

			if (!StringUtils.isEmpty(vendCodes)) {
				if (vendCodes.indexOf(",") != -1) {// 多个机器
					String[] vendArr = vendCodes.split(",");
					for (String vendCode : vendArr) {
						sd.setVendCode(vendCode);
						vendingReleVo.setVendCode(vendCode);
						if (aDLists != null && aDLists.length > 0) {
							for (String adId : aDLists) {
								Map<String, Object> map = vendingAdConterService.queryParamMap(adId);
								if (map != null) {
									sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
									sd.setAdId(adId);
									sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
									Map<String, String> param = new HashMap<String, String>();
									param.put("vendCode", vendCode);
									param.put("adId", adId);
									boolean isExistFlag = vendingAdConterService.isExist(param);
									if(!isExistFlag){//不存在该广告
										vendingAdConterService.addVendingStatistAd(sd);
									}
								}
							}
						}else{
							Map<String, String> param = new HashMap<String, String>();
							param.put("vendCode", vendCode);
							param.put("adId", aDList);
							boolean isExistFlag = vendingAdConterService.isExist(param);
							if(!isExistFlag){
								Map<String, Object> map = vendingAdConterService.queryParamMap(aDList);
								if (map != null) {
									sd.setImgName(map.containsKey("imgName") ? map.get("imgName").toString() : "");
									sd.setAdId(aDList);
									sd.setPlayTime(map.containsKey("playTime") ? map.get("playTime").toString() : "");
									vendingAdConterService.addVendingStatistAd(sd);
								}
							}
						}
						//该机器已添加的的广告
						String beforeAdList = vendingAdConterService.queryBeforAdList(vendingReleVo.getVendCode());
						StringBuffer newADList = new StringBuffer();
						if(beforeAdList != null && !"".equals(beforeAdList)){
							newADList.append(beforeAdList);
							if(aDLists != null && aDLists.length >0){
								int len = aDLists.length;
								for(int i=0;i<len;i++){
									if(isContains(beforeAdList,aDLists[i])){//如果机器上已经存在该广告
										newADList.append(",").append(aDLists[i]);
									}
								}
							}else{
								if(isContains(beforeAdList,aDList)){//如果机器上已经存在该广告
									newADList.append(",").append(aDList);
								}
							}
						}
						String finalAdList = "";
						if(newADList.length() != 0){
							finalAdList =  newADList.toString();
						}else{
							finalAdList = aDList;
						}
						vendingReleVo.setaDList(finalAdList);
						vendingReleVo.setVendCode(vendCode);
						List<VendingReleVo> vendingRele = vendingAdConterService.getVendReleByVendCode(vendCode);

						// 保存工作
						vendingReleVo.setAdStatus("1");
						if (vendingRele != null && vendingRele.size() > 0) {
							// 更新
							reulte = vendingAdConterService.updateVendingReleAd(vendingReleVo);
						} else {
							// 添加
							reulte = vendingAdConterService.saveVendingReleAd(vendingReleVo);
						}

						if (aDList.indexOf(",") != -1) {// 多条广告
							aDLists = aDList.split(",");
							for (String adList : aDLists) {
								
								sd.setAdStatus("2");
								sd.setAdId(adList);
								vendingAdConterService.updateByAdId(sd);

							}
						} else {
							// 单条广告
							sd.setAdStatus("2");
							sd.setAdId(aDList);
							vendingAdConterService.updateByAdId(sd);
						}

					}
				} else {
					// 单个机器
					sd.setVendCode(vendCodes);
					VendingConterAdVo vendingConterAdVo = vendingAdConterService.getVendAdByADId(aDList);
					if (vendingConterAdVo != null) {
						sd.setImgName(vendingConterAdVo.getImgName());
						sd.setAdId(aDList);
						sd.setPlayTime(vendingConterAdVo.getPlayTime());
						vendingAdConterService.addVendingStatistAd(sd);
					}
					
					//该机器已添加的的广告
					String beforeAdList = vendingAdConterService.queryBeforAdList(vendingReleVo.getVendCode());
					StringBuffer newADList = new StringBuffer();
					
					if(aDLists != null && aDLists.length >0){
						
						int len = aDLists.length;
						for(int i=0;i<len;i++){
							if(i < len -1){
								if(!beforeAdList.contains(aDLists[i])){//如果机器上已经存在该广告
									newADList.append(",").append(aDLists[i]);
								}
							}else{
								newADList.append(",").append(aDLists[i]);
							}
							
						}
					}else{
						if(!beforeAdList.contains(aDList)){//如果机器上已经存在该广告
							newADList.append(aDList);
						}
					}

					vendingReleVo.setaDList(newADList.toString());
					vendingReleVo.setVendCode(vendCodes);
					List<VendingReleVo> vendingRele = vendingAdConterService.getVendReleByVendCode(vendCodes);
					
					// 保存工作
					vendingReleVo.setAdStatus("1");
					if (vendingRele != null && vendingRele.size() > 0) {
						// 更新
						reulte = vendingAdConterService.updateVendingReleAd(vendingReleVo);
					} else {
						// 添加
						reulte = vendingAdConterService.saveVendingReleAd(vendingReleVo);
					}
					if (aDList.indexOf(",") != -1) {// 多条广告
						aDLists = aDList.split(",");
						for (String adList : aDLists) {

							sd.setAdStatus("2");
							sd.setAdId(adList);
							vendingAdConterService.updateByAdId(sd);

						}
					} else {
						// 单条广告
						sd.setAdStatus("2");
						sd.setAdId(aDList);
						vendingAdConterService.updateByAdId(sd);
					}

				}
				result.put("code", "0");
				result.put("msg", "发布成功");
			} else {
				// 没有机器存在
				result.put("code", "1");
				result.put("msg", "没有机器存在");
			}
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isContains(String str1,String str2){
		boolean flag = true;
		if(str1.indexOf(",") != -1){
			String[] arr = str1.split(",");
			for(String str : arr){
				if(str.equals(str2)){
					flag = false;
				    break;
				}
					
			}
		}else{
			if(str1.equals(str2))
				flag = false;
		}
		return flag;
	}
}
