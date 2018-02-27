package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.huilian.hlej.hcf.service.DealerManagermentService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.LottoActivityService;
import com.huilian.hlej.hcf.service.LottoVendService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
//import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.hcf.vo.LottoVendVoList;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 抽奖活动管理
 * 
 * @author ZhangZeBiao
 * @date 2017年10月24日 上午10:44:48
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/lottoVend")
public class LottoVendController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/lottoVendList";

	
	@Autowired
	private LottoVendService lottoVendService;
	
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
	public String list(LottoVendVo lottoVendVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<LottoVendVo> page = lottoVendService
				.getLottoVendVoPage(new Page<LottoVendVo>(request, response), lottoVendVo);
		Page<LottoVendVo> pages = lottoVendService.getLottoVendVoPage(
				new Page<LottoVendVo>(request, response, "str"), lottoVendVo);
		List<LottoActivityVo> activityList = lottoVendService.getLottoActivityVoAll();
		List<LottoEqRelationVo> vendCodeList = lottoVendService.getLottoEqRelationVoAll();
		List<DictionariesVo> prizeTypeList = dictionariesService.getLottoPrizeTypeList();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
//		model.addAttribute("lottoVendVoList", lottoVendService.getLottoActivityVoList(lottoVendVo));
		model.addAttribute("activityList", activityList);
		model.addAttribute("vendCodeList", vendCodeList);
		model.addAttribute("prizeTypeList", prizeTypeList);
		return LIST;
	}

	/**
	 * 保存奖品信息
	 * @param lottoVendVoList
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody List<LottoVendVo> lottoVendVoList, Model model) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			/*if (lottoVendVoList==null||lottoVendVoList.getLottoVendVoList()==null||lottoVendVoList.getLottoVendVoList().size()==0) {
				result.put("code","1");
				result.put("msg", "添加失败，无数据");
				return result;
			}*/
			lottoVendService.saveLottoVendVo(lottoVendVoList);
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
		String prizeUrl=null;
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
	 * 获取奖品列表
	 * @param getPrizeList
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getPrizeList")
	@ResponseBody
	public Map<String, Object> getPrizeList(@RequestBody LottoVendVo lottoVendVo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LottoVendVo> prizeList = lottoVendService.getPrizeList(lottoVendVo);
		Double totalProbability=0d;
		if (prizeList!=null&&prizeList.size()>0) {
			for (LottoVendVo lv : prizeList) {
				totalProbability = add(totalProbability, lv.getProbability());
			}
		}
		map.put("totalProbability", totalProbability);
		map.put("prizeList", prizeList);
		return map;
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
	public Map<String, String> update(@RequestBody LottoVendVo lottoVendVo, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		map = lottoVendService.updateLottoVendVo(lottoVendVo);
		return map;
	}

	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody LottoVendVo lottoVendVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = lottoVendVo.getId();
		LottoVendVo editLottoVendVo = lottoVendService.getLottoVendVo(id);
		map.put("editLottoVendVo", editLottoVendVo);
		return map;
	}
	
	/**
	 * 编辑页面跳转
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/editPrize")
	@ResponseBody
	public Map<String, Object> editPrize(@RequestBody LottoVendVo lottoVendVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<LottoVendVo> prizeList = lottoVendService.getLottoVendVoListByActivityNoAndVendCode(lottoVendVo);
		map.put("prizeList", prizeList);
		return map;
	}
	
	/**
	 * 删除对应的机器
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/deleteVendCode")
	@ResponseBody
	public Map<String, String> deleteVendCode(@RequestBody LottoVendVo lottoVendVo) {
		Map<String, String> map = new HashMap<String, String>();
		boolean flag=lottoVendService.deleteVendCodeByActivityNoAndVendCode(lottoVendVo);
		if (flag) {
			map.put("code", "0");
			map.put("msg", "删除成功");
//			model.addAttribute("code", "0");
//			model.addAttribute("msg", "删除成功");
		}else{
			map.put("code", "1");
			map.put("msg", "删除失败");
//			model.addAttribute("code", "1");
//			model.addAttribute("msg", "删除失败");
		}
		return map;
	}
	


	
	/**
	 * 删除奖品
	 * @param id
	 */
	@RequestMapping(value = "/deletePrize")
	@ResponseBody
	public void deletePrize(@RequestBody LottoVendVo lottoVendVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = lottoVendService.deletePrize(lottoVendVo);
		if (flag) {
			map.put("code", "0");
			map.put("msg", "删除成功!");
		} else {
			map.put("code", "1");
			map.put("msg", "删除失败!");
		}
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
	 * 验证奖品名称是否存在
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/checkPrizeName")
	@ResponseBody
	public Map<String, Object> checkPrizeName(@RequestBody String prizeName) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = lottoVendService.checkPrizeName(prizeName);
		if (!flag) {
			map.put("errorCode", "0");
			map.put("msg", "奖品名不可用");
		} else {
			map.put("errorCode", "1");
			map.put("msg", "奖品名可用");
		}
		return map;
	}
}
