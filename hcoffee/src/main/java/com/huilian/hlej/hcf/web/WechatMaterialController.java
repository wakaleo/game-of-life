package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huilian.hlej.hcf.common.utils.FileUploadUtil;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.WechatMaterialModelService;
import com.huilian.hlej.hcf.service.WechatMaterialService;
import com.huilian.hlej.hcf.vo.WechatMaterialModelVo;
import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 二维码素材管理
 * @author ZhangZeBiao
 * @date 2017年12月6日 上午11:44:51
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/wechatMaterial")
public class WechatMaterialController {

	private static final String LIST = "/hcoffee/vending/center/wechatMaterialList";

	
	@Autowired
	private WechatMaterialService wechatMaterialService;
	
	@Autowired
	private WechatMaterialModelService wechatMaterialModelService;
	
	@Autowired
	private DictionariesService dictionariesService;

	@Autowired
	FileHandler fileHandler;
	
	/**
	 * 查询所有抽奖机器
	 * @param lottoVendVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(WechatMaterialVo wechatMaterialVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<WechatMaterialVo> page = wechatMaterialService
				.getWechatMaterialVoPage(new Page<WechatMaterialVo>(request, response), wechatMaterialVo);
		Page<WechatMaterialVo> pages = wechatMaterialService.getWechatMaterialVoPage(
				new Page<WechatMaterialVo>(request, response, "str"), wechatMaterialVo);
		List<WechatMaterialVo> materialList = wechatMaterialService.getWechatMaterialVoAll();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("materialList", materialList);
		return LIST;
	}

	/**
	 * 保存二维码信息
	 * @param lottoVendVoList
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody WechatMaterialVo wechatMaterialVo, Model model) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			wechatMaterialService.saveWechatMaterialVo(wechatMaterialVo);
			result.put("code","0");
			result.put("msg", "添加成功");
		} catch (Exception e) {
			result.put("code","1");
			result.put("msg", "添加失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 保存销商信息
	 * @param 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uploadPic")
	@ResponseBody
	public Map<String, String> uploadPic(MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		String prizeUrl="";
		if(file != null && file.getOriginalFilename() != ""){
			try {
				prizeUrl = uploadFile(file, new FileInfoVo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("prizeUrl", prizeUrl);
		return result;
	}
	
	
	/**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    
	/**
	 * 修改
	 * @param lottoVendVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody WechatMaterialVo wechatMaterialVo, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		map = wechatMaterialService.updateWechatMaterialVo(wechatMaterialVo);
		return map;
	}

	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody WechatMaterialVo wechatMaterialVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = wechatMaterialVo.getId();
		WechatMaterialVo editPOJO = wechatMaterialService.getWechatMaterialVo(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startTime = sdf.format(editPOJO.getStartTime());
		String endTime = sdf.format(editPOJO.getEndTime());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("editPOJO", editPOJO);
		return map;
	}
	
	
	
	


	
	/**
	 * 删除二维码素材
	 * @param id
	 */
	@RequestMapping(value = "/deleteVo")
	@ResponseBody
	public Map<String, String> deleteVo(@RequestBody WechatMaterialVo wechatMaterialVo) {
		Map<String, String> map = new HashMap<String, String>();
		WechatMaterialModelVo wechatMaterialModelVo = new WechatMaterialModelVo();
		wechatMaterialModelVo.setWechatNo(wechatMaterialVo.getWechatNo());
		List<WechatMaterialModelVo> list=wechatMaterialModelService.getWechatMaterialModelListByWechatNo(wechatMaterialModelVo);
		if (list!=null&&list.size()>0) {
			map.put("code", "1");
			map.put("msg", "素材已被应用，无法删除!");
		}else {
			boolean flag = wechatMaterialService.deleteWechatMaterialVo(wechatMaterialVo);
			if (flag) {
				map.put("code", "0");
				map.put("msg", "删除成功!");
			} else {
				map.put("code", "1");
				map.put("msg", "删除失败!");
			}
		}
		return map;
	}
	
	
	

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, headers = "Accept=application/json")
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
	
	/**
	 * 验证名称或编码是否存在   //可用为0，不可用为1
	 * @param wechatMaterialVo
	 * @return
	 */
	@RequestMapping(value = "/checkNameOfNo")
	@ResponseBody
	public Map<String, Object> checkNameOfNo(@RequestBody WechatMaterialVo wechatMaterialVo) {
		String m="";
		if (wechatMaterialVo.getWechatName()!=null) {
			m="二维码名称";
		}else if (wechatMaterialVo.getWechatNo()!=null) {
			m="二维码编码";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = wechatMaterialService.checkNameOfNo(wechatMaterialVo);
		if (flag) {
			map.put("code", "0");
			map.put("msg", m+"可用");
		} else {
			map.put("code", "1");
			map.put("msg", m+"不可用");
		}
		return map;
	}
}
