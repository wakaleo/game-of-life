package com.huilian.hlej.hcf.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.service.GoodsTypeManagementService;
import com.huilian.hlej.hcf.vo.GoodsTypeVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 商品分类管理
 * 
 * @author LongZhangWei
 * @date 2017年8月29日 下午8:07:31
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/goodsTypeManagement")
public class GoodsTypeManagementController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/goodsTypeManagementList";
	
	@Autowired
	private GoodsTypeManagementService goodsTypeManagementService;
	
	/**
	 * 查询商品类型
	 * @param goodsTypeVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(GoodsTypeVo goodsTypeVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsTypeVo> page = goodsTypeManagementService.getGoodsTypeVoPage(new Page<GoodsTypeVo>(request, response), goodsTypeVo);
		Page<GoodsTypeVo> pages = goodsTypeManagementService.getGoodsTypeVoPage(
				new Page<GoodsTypeVo>(request, response, "str"), goodsTypeVo);
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("goodsTypeList", goodsTypeManagementService.getGoodsTypeVoList(goodsTypeVo));
		return LIST;
	}

	/**
	 * 保存商品类型
	 * @param goodsTypeVo
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody GoodsTypeVo goodsTypeVo,Model model)
			throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			result.put("code","0");
			result.put("msg", "添加成功");
			goodsTypeManagementService.saveGoodsTypeVo(goodsTypeVo);
		} catch (Exception e) {
			result.put("code","1");
			result.put("msg", "添加失败");
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 修改商品类型
	 * @param goodsTypeVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody GoodsTypeVo goodsTypeVo, Model model) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			goodsTypeManagementService.updateGoodsTypeVo(goodsTypeVo);
			result.put("code","0");
			result.put("msg", "修改成功");
		} catch (Exception e) {
			result.put("code","1");
			result.put("msg", "修改失败");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 编辑商品类型查询单个商品类型
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsTypeVo goodsTypeVo = goodsTypeManagementService.getGoodsTypeVoById(id);
		map.put("goodsTypeVo", goodsTypeVo);
		return map;
	}
	
	/**
	 * 删除商品类型
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> showMore(@RequestBody String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(goodsTypeManagementService.deleteGoodsTypeVoById(id)){
			result.put("code","0");
			result.put("msg", "删除成功");
		}else{
			result.put("code","1");
			result.put("msg", "删除失败");
		}
		return result;
	}
	
	/**
	 * 删除商品类型时，判断该商品类型有没商品
	 * 如果有该类型的商品存在，则不能删除该类型
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/isCanDelete")
	@ResponseBody
	public Map<String, Object> isCanDelete(@RequestBody String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(!goodsTypeManagementService.isCanDeleteGoodsType(id)){
			result.put("code","0");
			result.put("msg", "删除成功");
		}else{
			result.put("code","1");
			result.put("msg", "删除失败,有该类型的商品存在");
		}
		return result;
	}
}
