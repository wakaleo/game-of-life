package com.huilian.hlej.hcf.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huilian.hlej.base.utils.StringUtil;
import com.huilian.hlej.hcf.common.utils.HCFExportExcel;
import com.huilian.hlej.hcf.service.FinancialCenterService;
import com.huilian.hlej.hcf.vo.AlreadyAccountVo;
import com.huilian.hlej.hcf.vo.CheckUpAccountVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.OutAccountVo;
import com.huilian.hlej.hcf.vo.WaitingAccountVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.utils.DateUtils;
import com.huilian.hlej.jet.common.web.BaseController;

/**
 * 财务中心模块Controller
 * @author LongZhangWei
 * @date 2017年11月10日 下午3:26:28
 */
@Controller
@RequestMapping(value = "${adminPath}/hcf/financialCenter")
public class FinancialCenterController extends BaseController{

	private static final String LIST = "/hcoffee/vending/center/divideAccountSettingList";//分帐模版列表页面
	private static final String ADD = "/hcoffee/vending/center/divideAccountSettinAdd";//添加分帐模版页面
	
	private static final String CHECKUPACCOUNTLIST = "/hcoffee/vending/center/checkUpAccountlist";//分帐列表页面
	
	private static final String ALREADYCLOSEACCOUNTLIST = "/hcoffee/vending/center/alreadyCloseAccountlist";//已结算记录列表页面
	private static final String OUTACCOUNTLIST = "/hcoffee/vending/center/outAccountlist";//查支出记录列表页面
	private static final String WAITINGCLOSEACCOUNTLIST = "/hcoffee/vending/center/waitingCloseAccountlist";//待结算记录列表页面
	
	@Autowired
	private FinancialCenterService financialCenterService;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value = "/list")
	public String list(DivideAccountVo divideAccountVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		
		divideAccountVo.setStartTimeStr(divideAccountVo.getStartTime()!=null?format.format(divideAccountVo.getStartTime()):null);
		divideAccountVo.setEndTimeStr(divideAccountVo.getEndTime()!=null?format.format(divideAccountVo.getEndTime()):null);
		
		Page<DivideAccountVo> page = financialCenterService.getDivideAccountVoPage(new Page<DivideAccountVo>(request, response), divideAccountVo);
		Page<DivideAccountVo> pages = financialCenterService.getDivideAccountVoPage(new Page<DivideAccountVo>(request, response,"str"), divideAccountVo);
		model.addAttribute("startTime", divideAccountVo.getStartTime()!=null?format.format(divideAccountVo.getStartTime()):"");
		model.addAttribute("endTime", divideAccountVo.getEndTime()!=null?format.format(divideAccountVo.getEndTime()):"");
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return LIST;
	}
	
	@RequestMapping(value = "/add")
	public String add(DivideAccountVo divideAccountVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		
		return ADD;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, String> save(@RequestBody DivideAccountVo divideAccountVo) {
		Map<String, String> map = new HashMap<String, String>();
		map = financialCenterService.saveDivideAccountVo(divideAccountVo);
		return map;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, String> update(@RequestBody DivideAccountVo divideAccountVo, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		map = financialCenterService.updateDivideAccountVo(divideAccountVo);
		return map;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		DivideAccountVo divideAccountVo = financialCenterService.getDivideAccountVo(id);
		map.put("diVo", divideAccountVo);
		return map;
	}
	
	@RequestMapping(value = "/show")
	@ResponseBody
	public Map<String, Object> show(@RequestBody String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		DivideAccountVo divideAccountVo = financialCenterService.getDivideAccountVo(id);
		List<DealerTemplateVo> list = financialCenterService.getDealerTemplateVoList(id);
		map.put("diVo", divideAccountVo);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 分帐
	 * @param divideAccountVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkUpAccountlist")
	public String checkUpAccountlist(CheckUpAccountVo checkUpAccountVo, HttpServletRequest request, HttpServletResponse response,Model model) {
		setStartTimeStrAndEndTimeStr(checkUpAccountVo);
		Page<CheckUpAccountVo> page = financialCenterService.getCheckUpAccountVoPage(new Page<CheckUpAccountVo>(request, response), checkUpAccountVo);
		Page<CheckUpAccountVo> pages = financialCenterService.getCheckUpAccountVoPage(new Page<CheckUpAccountVo>(request, response,"str"), checkUpAccountVo);
		
		Map<String, Object> map = getStatistics(pages.getList());
		if(null != map){
			model.addAttribute("sumInComeMoney", map.get("sumInComeMoney"));
			model.addAttribute("sumOutMoney", map.get("sumOutMoney"));
		}
		
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("startTime", checkUpAccountVo.getStartTime()!=null?format.format(checkUpAccountVo.getStartTime()):"");
		model.addAttribute("endTime", checkUpAccountVo.getEndTime()!=null?format.format(checkUpAccountVo.getEndTime()):"");
		return CHECKUPACCOUNTLIST;
	}
	
	private Map<String, Object> getStatistics(List<?> list){
		DecimalFormat df = new DecimalFormat("######0.00"); 
		double sumInComeMoney = 0.0;
		double sumOutMoney = 0.0;
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != list && list.size() >0){
			for(Object object : list){
				if(object instanceof CheckUpAccountVo){//对帐列表
					CheckUpAccountVo vo = (CheckUpAccountVo)object;
					sumInComeMoney += vo.getIncomeMoney();
					sumOutMoney += vo.getExpenditureMoney();
				}
				if(object instanceof AlreadyAccountVo){//已结算
					AlreadyAccountVo vo = (AlreadyAccountVo)object;
					sumInComeMoney += vo.getIncomeMoney();
				}
				if(object instanceof OutAccountVo){//支出
					OutAccountVo vo = (OutAccountVo)object;
					sumOutMoney += vo.getExpenditureMoney();
				}
				if(object instanceof WaitingAccountVo){//待结算
					WaitingAccountVo vo = (WaitingAccountVo)object;
					sumInComeMoney += vo.getIncomeMoney();
				}
			}
		}
		map.put("sumInComeMoney", df.format(sumInComeMoney));
		map.put("sumOutMoney", df.format(sumOutMoney));
		return map;
	}
	
	/**
	 * 查询已结算列表页面
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/alreadyCloseAccountlist")
	public String alreadyCloseAccountlist(AlreadyAccountVo alreadyAccountVo, HttpServletRequest request, HttpServletResponse response,Model model,String loginName) {
		String startTimeStr = alreadyAccountVo.getStartTime()!=null?format.format(alreadyAccountVo.getStartTime()):"";
		String endTimeStr = alreadyAccountVo.getEndTime()!=null?format.format(alreadyAccountVo.getEndTime()):"";
		alreadyAccountVo.setStartTimeStr(startTimeStr);
		alreadyAccountVo.setEndTimeStr(endTimeStr);
		alreadyAccountVo.setLoginName(loginName);
		Page<AlreadyAccountVo> page = financialCenterService.getCheckUpAccountVoPage_1(new Page<AlreadyAccountVo>(request, response), alreadyAccountVo);
		Page<AlreadyAccountVo> pages = financialCenterService.getCheckUpAccountVoPage_1(new Page<AlreadyAccountVo>(request, response,"str"), alreadyAccountVo);
		Map<String, Object> map = getStatistics(page.getList());
		if(null != map){
			model.addAttribute("sumInComeMoney", map.get("sumInComeMoney"));
		}
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		model.addAttribute("loginName", alreadyAccountVo.getLoginName() != null ? alreadyAccountVo.getLoginName() : "");
		model.addAttribute("startTime", alreadyAccountVo.getStartTime()!=null?format.format(alreadyAccountVo.getStartTime()):"");
		model.addAttribute("endTime", alreadyAccountVo.getEndTime()!=null?format.format(alreadyAccountVo.getEndTime()):"");
		return ALREADYCLOSEACCOUNTLIST;
	}

	/**
	 * 设置果询的开始与结束时间
	 * @param checkUpAccountVo
	 */
	private void setStartTimeStrAndEndTimeStr(CheckUpAccountVo checkUpAccountVo) {
		String startTimeStr = checkUpAccountVo.getStartTime()!=null?format.format(checkUpAccountVo.getStartTime()):"";
		String endTimeStr = checkUpAccountVo.getEndTime()!=null?format.format(checkUpAccountVo.getEndTime()):"";
		checkUpAccountVo.setStartTimeStr(startTimeStr);
		checkUpAccountVo.setEndTimeStr(endTimeStr);
	}
	
	/**
	 * 查看支出列表页面
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/outAccountlist")
	public String outAccountlist(OutAccountVo outAccountVo, HttpServletRequest request, HttpServletResponse response,Model model,String loginName) {
		Page<OutAccountVo> page = financialCenterService.getCheckUpAccountVoPage_3(new Page<OutAccountVo>(request, response), outAccountVo);
		Page<OutAccountVo> pages = financialCenterService.getCheckUpAccountVoPage_3(new Page<OutAccountVo>(request, response,"str"), outAccountVo);
		model.addAttribute("startTime", outAccountVo.getStartTime()!=null?format.format(outAccountVo.getStartTime()):"");
		model.addAttribute("endTime", outAccountVo.getEndTime()!=null?format.format(outAccountVo.getEndTime()):"");
		Map<String, Object> map = getStatistics(page.getList());
		if(null != map){
			model.addAttribute("sumOutMoney", map.get("sumOutMoney"));
		}
		model.addAttribute("loginName",outAccountVo.getLoginName() != null ? outAccountVo.getLoginName() : "");
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return OUTACCOUNTLIST;
	}

	/**
	 * 查待结算出列表页面
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/waitingCloseAccountlist")
	public String waitingCloseAccountlist(WaitingAccountVo waitingAccountVo, HttpServletRequest request, HttpServletResponse response,Model model,String loginName) {
		String startTimeStr = waitingAccountVo.getStartTime()!=null?format.format(waitingAccountVo.getStartTime()):"";
		String endTimeStr = waitingAccountVo.getEndTime()!=null?format.format(waitingAccountVo.getEndTime()):"";
		waitingAccountVo.setStartTimeStr(startTimeStr);
		waitingAccountVo.setEndTimeStr(endTimeStr);
		Page<WaitingAccountVo> page = financialCenterService.getCheckUpAccountVoPage_2(new Page<WaitingAccountVo>(request, response), waitingAccountVo);
		Page<WaitingAccountVo> pages = financialCenterService.getCheckUpAccountVoPage_2(new Page<WaitingAccountVo>(request, response,"str"), waitingAccountVo);
		Map<String, Object> map = getStatistics(page.getList());
		if(null != map){
			model.addAttribute("sumInComeMoney", map.get("sumInComeMoney"));
		}
		model.addAttribute("startTime", waitingAccountVo.getStartTime()!=null?format.format(waitingAccountVo.getStartTime()):"");
		model.addAttribute("endTime", waitingAccountVo.getEndTime()!=null?format.format(waitingAccountVo.getEndTime()):"");
		model.addAttribute("loginName",waitingAccountVo.getLoginName() != null ? waitingAccountVo.getLoginName() : "");
		model.addAttribute("page", page);
		model.addAttribute("pages", pages.getList().size());
		return WAITINGCLOSEACCOUNTLIST;
	}
	
	/**
	 * 导出对帐单
	 * @param checkUpAccountVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportC", method = RequestMethod.POST)
	public String exportC(CheckUpAccountVo checkUpAccountVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try {
	     String fileName = "对账单" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
	     Page<CheckUpAccountVo> page = financialCenterService.getCheckUpAccountVoPage(new Page<CheckUpAccountVo>(request, response,"str"), checkUpAccountVo);
		  List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400);
	     new HCFExportExcel("对账单", CheckUpAccountVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
	     return null;
	   } catch (Exception e) {
	     addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
	   }
	   return "redirect:" + adminPath + "/hcf/financialCenter/checkUpAccountlist?repage";
	}
	
	@RequestMapping(value = "/exportA", method = RequestMethod.POST)
	public String exportA(AlreadyAccountVo alreadyAccountVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "已结算" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<AlreadyAccountVo> page = financialCenterService.getCheckUpAccountVoPage_1(new Page<AlreadyAccountVo>(request, response,"str"), alreadyAccountVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400);
			new HCFExportExcel("已结算", AlreadyAccountVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/financialCenter/alreadyCloseAccountlist?repage";
	}
	
	/**
	 * 售货机销售分成记录的详情
	 * @param checkUpAccountVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/detail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> detail(@RequestBody CheckUpAccountVo checkUpAccountVo, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		//本月售货机销售总额
		double saleMoney = 0; 
		//本月分成比例
		String percentage = "";
		/*
		 * 1.得到代理商的所有机器
		 * 2.查询这些机器在本月销售额
		 * 3.与模版进行询得到本月分成比例
		 */
		String loginName = checkUpAccountVo.getLoginName();
		String checkUpTimes = checkUpAccountVo.getCheckUpTimes();
		String vendCodes = financialCenterService.getAllVendCodeByLoginName(loginName);
		if(!"".equals(vendCodes)){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("checkUpTimes", checkUpTimes != null?checkUpTimes:"");
			map.put("vendCodes", vendCodes != null ? vendCodes : "");
			saleMoney = financialCenterService.querySaleMoneyByDate(map);
			
			//模版信息
			Map<String, Object> param = financialCenterService.queryParmaIdByLoginName(loginName);
			String templateId = param != null ? param.get("templateId").toString() : "";
			String useTimeStr = param != null ? param.get("useTimeStr").toString() : "";
			/*
			 * 判断销售额属于哪个区间
			 */
			Map<String, Object> maps = new HashMap<String,Object>();
			maps.put("templateId", templateId);
			maps.put("saleMoney", saleMoney);
			maps.put("useTimeStr", useTimeStr);
			percentage = financialCenterService.getPercentage(maps);
		}
		resultMap.put("saleMoney", saleMoney+"");
		resultMap.put("percentage", percentage);
		return resultMap;
	}
	
	/**
	 * 机器补贴详细信息
	 * @param checkUpAccountVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/detailVending",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> detailVending(@RequestBody CheckUpAccountVo checkUpAccountVo, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Object> parm = new HashMap<String, Object>();
		
		String loginName = checkUpAccountVo.getLoginName();
		String checkUpTimes = checkUpAccountVo.getCheckUpTimes();
		Integer amountStatus = checkUpAccountVo.getAmountStatus();
		
		parm.put("loginName", loginName);
		parm.put("checkUpTimes", checkUpTimes);
		parm.put("checkUpTimes", checkUpTimes);
		parm.put("amountStatus", amountStatus);
		
		double totalSubsidy = financialCenterService.queryTotalSubsidy(parm);//总补贴（元）
		int periods = financialCenterService.queryPeriods(parm);
		double acaMoney = financialCenterService.queryAcaMoney(parm);
		double wcaMoney = 0;
		if(totalSubsidy > acaMoney)
			wcaMoney = totalSubsidy - acaMoney;
		resultMap.put("totalSubsidy", totalSubsidy+"");
		resultMap.put("periods", periods+"");
		resultMap.put("acaMoney", acaMoney+"");
		resultMap.put("wcaMoney", wcaMoney+"");
		return resultMap;
	}
	
	/**
	 * 广告补贴详细信息
	 * @param checkUpAccountVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/detailAdvertisement",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> detailAdvertisement(@RequestBody CheckUpAccountVo checkUpAccountVo, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String loginName = checkUpAccountVo.getLoginName();
		String checkUpTimes = checkUpAccountVo.getCheckUpTimes();
		
		String[] yM = checkUpTimes.split("-");
		String yearMonthStr = yM[0] + "-" + yM[1];
		
		String vendCodes = financialCenterService.getAllVendCodeByLoginName(loginName);
		
		//总广告支数
		int advertisNum = 0;
		//已推送机器数（台）
		int pushNum = 0;
		if(!StringUtil.isEmpty(vendCodes)){
			Map<String, Object> parms = new HashMap<String,Object>();
			parms.put("vendCodes", vendCodes);
			parms.put("yearMonthStr", yearMonthStr);
			advertisNum = financialCenterService.queryAdvertisNum(parms);
			pushNum = financialCenterService.aleardyPushNum(parms);
		}
		resultMap.put("advertisNum", advertisNum+"");
		resultMap.put("pushNum", pushNum+"");
		return resultMap;
	}
	
	/**
	 * 待结算导出
	 * @param checkUpAccountVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exportW", method = RequestMethod.POST)
	public String exportW(WaitingAccountVo waitingAccountVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "待结算记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<WaitingAccountVo> page = financialCenterService.getCheckUpAccountVoPage_2(new Page<WaitingAccountVo>(request, response,"str"), waitingAccountVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400);
			new HCFExportExcel("待结算记录", WaitingAccountVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/financialCenter/waitingCloseAccountlist?repage";
	}
	
	@RequestMapping(value = "/exportOut", method = RequestMethod.POST)
	public String exportOut(OutAccountVo outAccountVo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "查询支出记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<OutAccountVo> page = financialCenterService.getCheckUpAccountVoPage_3(new Page<OutAccountVo>(request, response,"str"), outAccountVo);
			List<Integer> widths = Arrays.asList(3600,0,2400,2400,2400);
			new HCFExportExcel("查询支出记录", OutAccountVo.class,widths).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/hcf/financialCenter/outAccountlist?repage";
	}

}
