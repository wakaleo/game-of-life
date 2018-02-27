package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
import com.huilian.hlej.hcf.service.GoodsManagementService;
import com.huilian.hlej.hcf.service.GoodsTypeManagementService;
import com.huilian.hlej.hcf.vo.GoodsTypeVo;
import com.huilian.hlej.hcf.vo.GoodsVo;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 商品管理
 * 
 * @author LongZhangWei
 * @date 2017年8月29日 下午8:07:31
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/goodsManagement")
public class GoodsManagementController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/goodsManagementList";
	
	@Autowired
	private GoodsManagementService goodsManagementService;
	
	@Autowired
	private GoodsTypeManagementService goodsTypeManagementService;
	
	@Autowired
	FileHandler fileHandler;

	@RequestMapping(value = "/list")
	public String list(GoodsVo goodsVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsVo> page = goodsManagementService.getGoodsVoPage(new Page<GoodsVo>(request, response), goodsVo);
		Page<GoodsVo> pages = goodsManagementService.getGoodsVoPage(
				new Page<GoodsVo>(request, response, "str"), goodsVo);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("goodsTypeList", goodsTypeManagementService.getGoodsTypeVoList(new GoodsTypeVo()));
		model.addAttribute("goodsList", goodsManagementService.getGoodsVoList(goodsVo));
		return LIST;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(GoodsVo goodsVo, Model model, MultipartFile file)
			throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		if(file != null && file.getOriginalFilename() != ""){
			try {
				result.put("code","0");
				result.put("msg", "添加成功");
				goodsVo.setPictureUrl(uploadFile(file, new FileInfoVo()));
				goodsManagementService.saveGoodsVo(goodsVo);
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

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(GoodsVo goodsVo, Model model, MultipartFile file) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			goodsVo.setPictureUrl(uploadFile(file, new FileInfoVo()));
			if(goodsManagementService.updateGoodsVo(goodsVo)){
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
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsVo goodsVo = goodsManagementService.getGoodsVoById(id);
		map.put("goodsVo", goodsVo);
		return map;
	}
	
	/**
	 * 商品上下架
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/isSale")
	@ResponseBody
	public Map<String, Object> isSale(@RequestBody GoodsVo goodsVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(goodsManagementService.updateIsSale(goodsVo)){
			map.put("code", "0");
			map.put("msg", "修改成功");
		}else{
			map.put("code", "1");
			map.put("msg", "修改失败");
		}
		return map;
	}
}
