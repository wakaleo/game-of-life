package com.huilian.hlej.hcf.web;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.huilian.hlej.hcf.service.DealerManagermentService;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.service.LottoActivityService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
//import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.DictionariesVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 抽奖活动管理
 * 
 * @author ZhangZeBiao
 * @date 2017年10月24日 上午10:44:48
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/lottoActivity")
public class LottoActivityController extends BaseController {

	private static final String LIST = "/hcoffee/vending/center/lottoActivityList";

	
	@Autowired
	private LottoActivityService lottoActivityService;
	
	@Autowired
	private DictionariesService dictionariesService;

	/**
	 * 查询所有抽奖活动
	 * @param lottoActivityVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(LottoActivityVo lottoActivityVo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<LottoActivityVo> page = lottoActivityService
				.getLottoActivityVoPage(new Page<LottoActivityVo>(request, response), lottoActivityVo);
		Page<LottoActivityVo> pages = lottoActivityService.getLottoActivityVoPage(
				new Page<LottoActivityVo>(request, response, "str"), lottoActivityVo);
//		List<LottoEqRelationVo> list = lottoActivityService.getLottoEqRelationVoAll();
		List<DictionariesVo> lottoActivityStatusList = dictionariesService.getLottoActivityStatusList();
		List<DictionariesVo> lottoWayList = dictionariesService.getLottoWayList();
		List<DictionariesVo> lottoTriggerConditionList = dictionariesService.getLottoTriggerConditionList();
//		List<DictionariesVo> sexList = dictionariesService.getSexList();
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("activityList", lottoActivityService.getLottoActivityVoList(lottoActivityVo));
//		model.addAttribute("vendCodeList", list);
		model.addAttribute("lottoActivityStatusList", lottoActivityStatusList);
		model.addAttribute("lottoWayList", lottoWayList);
		model.addAttribute("lottoTriggerConditionList", lottoTriggerConditionList);
//		model.addAttribute("sexList", sexList);
		return LIST;
	}



	/**
	 * 编辑页面跳转
	 * @param activityNo
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String activityNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		LottoActivityVo lottoActivityVo = lottoActivityService.getLottoActivityVo(activityNo);
//		List<LottoEqRelationVo> list = lottoActivityService.getLottoEqRelationVoList(activityNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startTime = sdf.format(lottoActivityVo.getStartTime());
		String endTime = sdf.format(lottoActivityVo.getEndTime());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("lottoActivityVo", lottoActivityVo);
//		map.put("vends", list);
		return map;
	}
	
	/**
	 * 查看更多
	 * @param activityNo
	 * @return
	 */
	@RequestMapping(value = "/showMore")
	@ResponseBody
	public Map<String, Object> showMore(@RequestBody String activityNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		LottoActivityVo lottoActivityVo = lottoActivityService.getLottoActivityVo(activityNo);
//		List<LottoEqRelationVo> list = lottoActivityService.getLottoEqRelationVoList(activityNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startTime = sdf.format(lottoActivityVo.getStartTime());
		String endTime = sdf.format(lottoActivityVo.getEndTime());
		String createTime = sdf.format(lottoActivityVo.getCreateTime());
		map.put("createTime", createTime);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("lottoActivityVo", lottoActivityVo);
//		map.put("vends", list);
		return map;
	}


	/**
	 * 验证活动名称是否存在
	 * @param activityName
	 * @return
	 */
	@RequestMapping(value = "/checkActivityName")
	@ResponseBody
	public Map<String, Object> checkActivityName(@RequestBody String activityName) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = lottoActivityService.checkActivityName(activityName);
		if (flag) {
			map.put("errorCode", "0");
			map.put("msg", "活动名不可用");
		} else {
			map.put("errorCode", "1");
			map.put("msg", "活动名可用");
		}
		return map;
	}
	
	/**
	 * 验证活动名称是否存在
	 * @param activityNo
	 * @return
	 */
	@RequestMapping(value = "/checkActivityNo")
	@ResponseBody
	public Map<String, Object> checkActivityNo(@RequestBody String activityNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = lottoActivityService.checkActivityNo(activityNo);
		if (flag) {
			map.put("errorCode", "0");
			map.put("msg", "活动编号不可用");
		} else {
			map.put("errorCode", "1");
			map.put("msg", "活动编号可用");
		}
		return map;
	}
	
	
	/**
	 * 保存活动信息
	 * @param lottoActivityVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody LottoActivityVo lottoActivityVo, Model model) {
		return lottoActivityService.saveLottoActivityVo(lottoActivityVo);
	}
	/**
	 * 更新活动信息
	 * @param lottoActivityVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody LottoActivityVo lottoActivityVo, Model model) {
		return lottoActivityService.updateLottoActivityVo(lottoActivityVo);
	}
}
