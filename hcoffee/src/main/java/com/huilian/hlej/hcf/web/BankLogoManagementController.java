package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huilian.hlej.hcf.service.BankLogoService;
import com.huilian.hlej.hcf.service.DictionariesService; 
import com.huilian.hlej.hcf.vo.BankLogoVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 银行Logo管理
 * @author LongZhangWei
 * @date 2017年12月12日 下午2:13:03
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/bankLogo")
public class BankLogoManagementController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(BankLogoManagementController.class);
	
	private static final String BANKLOGOLIST = "/hcoffee/vending/center/bankLogoList";//银行LOGO显示列表页面
	
	@Autowired
	private DictionariesService dictionariesService;
	
	@Autowired
	private BankLogoService bankLogoService;
	
	@Autowired
	FileHandler fileHandler;
	
	
	@RequestMapping(value = "/list")
	public String list(BankLogoVo bankLogoVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		Page<BankLogoVo> page = bankLogoService.getBankLogoVoPage(new Page<BankLogoVo>(request, response), bankLogoVo);
		Page<BankLogoVo> pages = bankLogoService.getBankLogoVoPage(new Page<BankLogoVo>(request, response,"str"), bankLogoVo);
		//获取银行卡名称字典列表
		List<DictionariesVo> bankNameList = dictionariesService.getBankName();
		model.addAttribute("bankNameList", bankNameList);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return BANKLOGOLIST;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(BankLogoVo bankLogoVo, Model model, MultipartFile file)
			throws IOException {
		logger.info("保存银行卡logo信息......................");
		Map<String, String> result = new HashMap<String, String>();
		if(file != null && file.getOriginalFilename() != ""){
			try {
				bankLogoVo.setLogoUrl(uploadFile(file, new FileInfoVo()));
				String bankName = bankLogoService.queryBankName(bankLogoVo.getBankCode());
				bankLogoVo.setBankName(bankName);
				String createBy = UserUtils.getUser().getName();
				bankLogoVo.setCreateBy(createBy);
				bankLogoService.saveBankLogoVo(bankLogoVo);
				result.put("code","0");
				result.put("msg", "添加成功");
			} catch (Exception e) {
				result.put("code","1");
				result.put("msg", "添加失败");
				e.printStackTrace();
			}
		}else{
			result.put("code","1");
			result.put("msg", "添加失败! 请上传银行卡logo图片");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(BankLogoVo bankLogoVo, Model model, MultipartFile file) {
		logger.info("修改银行卡logo信息......................");
		Map<String, String> result = new HashMap<String, String>();
		try {
			bankLogoVo.setLogoUrl(uploadFile(file, new FileInfoVo()));
			String bankName = bankLogoService.queryBankName(bankLogoVo.getBankCode());
			bankLogoVo.setBankName(bankName);
			String createBy = UserUtils.getUser().getName();
			bankLogoVo.setCreateBy(createBy);
			if(bankLogoService.updateBankLogoVo(bankLogoVo)){
				result.put("code","0");
				result.put("msg", "修改成功");
			}else{
				result.put("code","1");
				result.put("msg", "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code","1");
			result.put("msg", "修改失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		BankLogoVo bankLogoVo = bankLogoService.getBankLogoVoById(id);
		map.put("bankLogoVo", bankLogoVo);
		return map;
	}
	
	/**
	 * 删除商品类型
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(bankLogoService.deleteBankLogoVoById(id)){
			result.put("code","0");
			result.put("msg", "删除成功");
		}else{
			result.put("code","1");
			result.put("msg", "删除失败");
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
}
