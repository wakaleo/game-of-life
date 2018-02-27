package com.huilian.hlej.hcf.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONArray;
import com.huilian.hlej.hcf.service.BankLogoService;
import com.huilian.hlej.hcf.service.DealerManagermentService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 经销商管理
 * 
 * @author LongZhangWei
 * @date 2017年8月24日 上午10:44:48
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/dealerManagerment")
public class DealerManagementController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/dealerManagementList";
	private static final String DEALERFILEUPLOAD = "/hcoffee/vending/center/dealerFileUpload";
	private static final String DEALERIMAGEUPLOAD = "/hcoffee/vending/center/dealerImageUpload";
	private static final String DIVIDEACCOUNTLIST = "/hcoffee/vending/center/divideAccountList";
	private static final String SHOWDIVIDEACCOUNTLIST = "/hcoffee/vending/center/showDivideAccountList";
			
	//存放password
	private Map<String, String> passwordMap = new HashMap<String,String>();

	@Autowired
	private DealerManagermentService dealerManagermentService;
	
	@Autowired
	private DictionariesService dictionariesService;
	
	@Autowired
	FileHandler fileHandler;
	
	@Autowired
	private BankLogoService bankLogoService;

	/**
	 * 查询所有销售商
	 * @param dealerManagermentVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(DealerManagermentVo dealerManagermentVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<DealerManagermentVo> page = dealerManagermentService
				.getDealerManagermentVoPage(new Page<DealerManagermentVo>(request, response), dealerManagermentVo);
		Page<DealerManagermentVo> pages = dealerManagermentService.getDealerManagermentVoPage(
				new Page<DealerManagermentVo>(request, response, "str"), dealerManagermentVo);
		List<DealerEqRelationVo> list = dealerManagermentService.getDealerEqRelationVoAll();
		List<DictionariesVo> dealerTypeList = dictionariesService.getDealerTypeList();
		List<DictionariesVo> dealerGradeList = dictionariesService.getDealerGradeList();
		List<DictionariesVo> conStatusList = dictionariesService.getConStatusList();
		List<DictionariesVo> sexList = dictionariesService.getSexList();
		//获取银行卡名称字典列表
		List<DictionariesVo> bankNameList = dictionariesService.getBankName();
		//上级代理列表
		List<DealerManagermentVo> superiorAgentList = dealerManagermentService.superiorAgentList();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("dealerList", dealerManagermentService.getDealerManagermentVoList(dealerManagermentVo));
		model.addAttribute("vendCodeList", list);
		model.addAttribute("dealerTypeList", dealerTypeList);
		model.addAttribute("dealerGradeList", dealerGradeList);
		model.addAttribute("conStatusList", conStatusList);
		model.addAttribute("sexList", sexList);
		model.addAttribute("superiorAgentList", superiorAgentList);
		model.addAttribute("bankNameList", bankNameList);
		return LIST;
	}

	/**
	 * 保存销商信息
	 * @param dealerManagermentVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody DealerManagermentVo dealerManagermentVo) {
		Map<String, String> map = new HashMap<String, String>();
		Integer bankCode = dealerManagermentVo.getBankCode();
		if(bankCode != null){
			String bankName = bankLogoService.queryBankName(bankCode);
			dealerManagermentVo.setBankCarInfo(bankName);
		}
		map = dealerManagermentService.saveDealerManagermentVo(dealerManagermentVo);
		return map;
	}

	/**
	 * 修改销售商信息
	 * @param dealerManagermentVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody DealerManagermentVo dealerManagermentVo, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		if(passwordMap != null && passwordMap.size() >0){
			if(dealerManagermentVo.getPassword().equals(passwordMap.get("pwd"))){
				dealerManagermentVo.setPassword(null);
			}
		}
		Integer bankCode = dealerManagermentVo.getBankCode();
		if(bankCode != null){
			String bankName = bankLogoService.queryBankName(bankCode);
			dealerManagermentVo.setBankCarInfo(bankName);
		}
		map = dealerManagermentService.updateDealerManagermentVo(dealerManagermentVo);
		return map;
	}

	/**
	 * 查询设备机械编码
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findEvdCode")
	@ResponseBody
	public Map<String, Object> findEvdCode(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DealerEqRelationVo> list = dealerManagermentService.getDealerEqRelationVoAll();
		map.put("vendCodes", list);
		return map;
	}

	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		DealerManagermentVo dealerManagermentVo = dealerManagermentService.getDealerManagermentVo(loginName);
		Integer bankCode = dealerManagermentVo.getBankCode();
		if(bankCode != null){
			String bankName = bankLogoService.queryBankName(bankCode);
			dealerManagermentVo.setBankName(bankName);
		}
		passwordMap.put("pwd", dealerManagermentVo.getPassword());
		List<DealerEqRelationVo> list = dealerManagermentService.getDealerEqRelationVoList(loginName);
		/*获取分帐设置列表信息*/
		List<DealerTemplateVo> DealerTemplateVoList = dealerManagermentService.getDealerTemplateList(loginName);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = sdf1.format(dealerManagermentVo.getBirthday());
		map.put("birthday", birthday);
		map.put("dealerVo", dealerManagermentVo);
		map.put("vends", list);
		map.put("dvList", DealerTemplateVoList);
		return map;
	}
	
	/**
	 * 查看更多
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/showMore")
	@ResponseBody
	public Map<String, Object> showMore(@RequestBody String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		DealerManagermentVo dealerManagermentVo = dealerManagermentService.getDealerManagermentVo(loginName);
		List<DealerEqRelationVo> list = dealerManagermentService.getDealerEqRelationVoList(loginName);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String birthday = sdf1.format(dealerManagermentVo.getBirthday());
		String createTime = sdf2.format(dealerManagermentVo.getCreateTime());
		String lastLoginTime = "";
		if(null != dealerManagermentVo.getLastLoginTime())
			lastLoginTime = sdf2.format(dealerManagermentVo.getLastLoginTime());
		map.put("birthday", birthday);
		map.put("createTime", createTime);
		map.put("lastLoginTime", lastLoginTime);
		map.put("dealerVo", dealerManagermentVo);
		map.put("vends", list);
		return map;
	}

	/**
	 * 验证登录是否存在
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/checkLoginName")
	@ResponseBody
	public Map<String, Object> checkLoginName(@RequestBody String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = dealerManagermentService.checkLoginName(loginName);
		if (!flag) {
			map.put("errorCode", "0");
			map.put("msg", "登录名不可用");
		} else {
			map.put("errorCode", "1");
			map.put("msg", "登录名可用");
		}
		return map;
	}

	/**
	 * 查询所设备及其投放位置
	 * @return
	 */
	@RequestMapping(value = "/initSelect")
	@ResponseBody
	public Map<String, Object> initSelect() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DealerEqRelationVo> list = dealerManagermentService.getDealerEqRelationVoAll();
		map.put("vends", list);
		return map;
	}
	
	/**
	 * 解除经售商与设备关系
	 * @param id
	 */
	@RequestMapping(value = "/deleteVendCode")
	@ResponseBody
	public void deleteVendCode(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = dealerManagermentService.deleteDealerEqRelationVo(id);
		if (flag) {
			map.put("code", "0");
			map.put("msg", "解出关联成功!");
		} else {
			map.put("code", "1");
			map.put("msg", "解出关联失败!");
		}
	}
	
	/**
	 * 验证机械编码是否已关联
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/isRepeat")
	@ResponseBody
	public Map<String, Object> isRepeat(@RequestBody DealerEqRelationVo dealerEqRelationVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String vendCode = dealerEqRelationVo.getVendCode();
		boolean flag = dealerManagermentService.isRelevance(dealerEqRelationVo);
		if (flag) {
			map.put("errorCode", "0");
			map.put("msg", "机械编码：<font color='red'><strong>" + vendCode + "</strong></font>&nbsp;已被关联,请选择其他机械编码");
		} else {
			map.put("errorCode", "1");
			map.put("msg", "关联成功");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/superiorAgent")
	@ResponseBody
	public Map<String, Object> superiorAgent(@RequestBody String dealerGrade) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> dealerGradeMapList = dealerManagermentService.superiorAgentListByGrade(dealerGrade);
		JSONArray jsonArray = new JSONArray();
		for(Map<String, Object> map : dealerGradeMapList){
			jsonArray.add(map);
		}
		resultMap.put("dealers", jsonArray);
		return resultMap;
	}
	
	@RequestMapping(value="/dealerFileUpload")
	public String dealerFileUpload(String type,String loginName,Model model){
		model.addAttribute("loginName", loginName);
		if("1".equals(type))
			return DEALERIMAGEUPLOAD;
		else
			return DEALERFILEUPLOAD;
	}
	
	@RequestMapping(value = "/fileUpload")
	@ResponseBody
	public Map<String, String> fileUpload(String type,String loginName, MultipartFile file)
			throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		if(file != null && file.getOriginalFilename() != ""){
			try {
				String fileName = file.getOriginalFilename();
				String filePath = uploadFile(file, new FileInfoVo());
				Map<String, Object> parms = new HashMap<String, Object>();
				parms.put("fileName", fileName);
				parms.put("loginName", loginName);
				parms.put("filePath", filePath);
				parms.put("type", type);
				dealerManagermentService.saveFileOrImage(parms);
				result.put("code","0");
				result.put("msg", "添加成功");
			} catch (Exception e) {
				result.put("code","1");
				result.put("msg", "添加失败");
				e.printStackTrace();
			}
		}else{
			result.put("code","1");
			result.put("msg", "添加失败! 请上传商品图片");
		}
		
		return result;
	}
	
	public String uploadFile(MultipartFile file, FileInfoVo fileInfo) throws Exception {
		if(file.getSize() != 0){
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
		}
		return "";
	}
	
	@RequestMapping(value = "/deleteFile" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFile(HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		String fileUrl = request.getParameter("fileUrl");
		String loginName = request.getParameter("loginName");
		Map<String, Object> result = new HashMap<String, Object>();
		if(!"".equals(fileUrl) && null != fileUrl){
			try {
				if(dealerManagermentService.deleteFilePath(fileUrl)){
					
					/*
					 * 删除成功后，需把临表时的数据，重新刷新页面
					 */
					Map<String, String> tempUrlMap = dealerManagermentService.getTempFileUrl(loginName);
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("cxUrl", tempUrlMap.get("ZXCXJGURL")!=null?tempUrlMap.get("ZXCXJGURL"):"");
					dataMap.put("zPUrl", tempUrlMap.get("ZJZPURL")!=null?tempUrlMap.get("ZJZPURL"):"");
					dataMap.put("zjzpFileName", tempUrlMap.get("ZJZPFILENAME")!=null?tempUrlMap.get("ZJZPFILENAME"):"");
					dataMap.put("zxjgFileName", tempUrlMap.get("ZXJGFILENAME")!=null?tempUrlMap.get("ZXJGFILENAME"):"");
					result.put("data", dataMap);
					result.put("code","0");
					result.put("msg", "删除成功");
				}else{
					result.put("code","1");
					result.put("msg", "删除失败");
				}
			} catch (Exception e) {
				result.put("code","1");
				result.put("msg", "删除失败");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/refreshFileList")
	@ResponseBody
	public Map<String, Object> refreshFileList(@RequestBody String loginName)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> tempUrlMap = dealerManagermentService.getTempFileUrl(loginName);
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("cxUrl", tempUrlMap.get("ZXCXJGURL")!=null?tempUrlMap.get("ZXCXJGURL"):"");
			dataMap.put("zPUrl", tempUrlMap.get("ZJZPURL")!=null?tempUrlMap.get("ZJZPURL"):"");
			dataMap.put("zjzpFileName", tempUrlMap.get("ZJZPFILENAME")!=null?tempUrlMap.get("ZJZPFILENAME"):"");
			dataMap.put("zxjgFileName", tempUrlMap.get("ZXJGFILENAME")!=null?tempUrlMap.get("ZXJGFILENAME"):"");
			result.put("data", dataMap);
			result.put("code","0");
		} catch (Exception e) {
			result.put("code","1");
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/download")
    public void download(HttpServletResponse response,String filePath,String fileName) throws IOException {
		   response.setCharacterEncoding("utf-8");
           response.setContentType("multipart/form-data");
           response.setHeader("Content-Disposition", "attachment;fileName="
               +  java.net.URLEncoder.encode(fileName, "utf-8"));
           OutputStream os = null;
           InputStream inputStream = null;
           try {
               os = response.getOutputStream();
               byte[] b = fileHandler.downloadFile(filePath);
               os.write(b, 0, b.length);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally{
        	   // 这里主要关闭。
        	   if(os != null)
                os.close();
        	   if(inputStream != null)
               inputStream.close();
           }
     }
	
	@RequestMapping(value="/diviSet")
	public String diviSet(DivideAccountVo divideAccountVo, HttpServletRequest request,
			HttpServletResponse response, Model model,String loginName,String dealerName,String dealerType){
		Page<DivideAccountVo> page = dealerManagermentService
				.getDivideAccountVoPage(new Page<DivideAccountVo>(request, response), divideAccountVo);
		Page<DivideAccountVo> pages = dealerManagermentService
				.getDivideAccountVoPage(new Page<DivideAccountVo>(request, response,"str"), divideAccountVo);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("loginName", loginName);
		model.addAttribute("dealerName", dealerName);
		model.addAttribute("dealerType", dealerType);
		return DIVIDEACCOUNTLIST;
	}
	
	@RequestMapping(value="/saveTemplate")
	@ResponseBody
	public Map<String, Object> saveTemplate(HttpServletRequest request,Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		String dealerType = request.getParameter("dealerType");
		String dealerName = request.getParameter("dealerName");
		String templateId = request.getParameter("templateId");
		String operator = UserUtils.getUser().getName();
		DealerTemplateVo dealerTemplateVo = new DealerTemplateVo();
		dealerTemplateVo.setDealerName(dealerName);
		dealerTemplateVo.setLoginName(loginName);
		dealerTemplateVo.setDealerType(Integer.parseInt(dealerType));
		dealerTemplateVo.setOperator(operator);
		dealerTemplateVo.setTemplateId(Integer.parseInt(templateId));
		if(dealerManagermentService.saveBandDingInfo(dealerTemplateVo)){
			result.put("code", "0");
			result.put("msg", "绑定成功");
		}else{
			result.put("code", "1");
			result.put("msg", "绑定失败");
		}
		return result;
	}
	
	@RequestMapping(value="/refreshTemplateList")
	@ResponseBody
	public Map<String, Object> refreshTemplateList(HttpServletRequest request,Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		String loginName = request.getParameter("loginName");
		List<DealerTemplateVo> DealerTemplateVoList = dealerManagermentService.getDealerTemplateList(loginName);
		result.put("tempList", DealerTemplateVoList);
		return result;
	}
	
	@RequestMapping(value="/showTemplate")
	public String showTemplate(String templateId,Model model){
		model.addAttribute("templateId", templateId);
		return SHOWDIVIDEACCOUNTLIST;
	}
}
