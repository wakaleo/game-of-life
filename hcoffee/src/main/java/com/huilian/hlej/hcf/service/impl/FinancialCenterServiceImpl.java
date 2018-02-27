package com.huilian.hlej.hcf.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.FinancialCenterDao;
import com.huilian.hlej.hcf.service.FinancialCenterService;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.AlreadyAccountVo;
import com.huilian.hlej.hcf.vo.CheckUpAccountVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.OutAccountVo;
import com.huilian.hlej.hcf.vo.WaitingAccountVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

@Service
@Transactional
public class FinancialCenterServiceImpl implements FinancialCenterService {

	
	//时间格式
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	//获取系统当前时间  年-月-日
	private String systemCurrentTime = simpleDateFormat.format(new Date());
	
	private String pattern = "yy-MM";
	
	@Autowired
	private FinancialCenterDao financialCenterDao;
	
	@Override
	public Map<String, String> saveDivideAccountVo(DivideAccountVo divideAccountVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			financialCenterDao.saveDivideAccountVo(divideAccountVo);
			result.put("code", "0");
			result.put("msg", "添加成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, String> updateDivideAccountVo(DivideAccountVo divideAccountVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			divideAccountVo.setOperator(UserUtils.getUser().getName());
			financialCenterDao.updateDivideAccountVo(divideAccountVo);
			result.put("code", "0");
			result.put("msg", "修改成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Page<DivideAccountVo> getDivideAccountVoPage(Page<DivideAccountVo> page, DivideAccountVo divideAccountVo) {
		divideAccountVo.setPage(page);
		page.setList(financialCenterDao.getDivideAccountQueryPage(divideAccountVo));
		return page;
	}

	@Override
	public DivideAccountVo getDivideAccountVo(String id) {
		return financialCenterDao.getDivideAccountVo(id);
	}

	@Override
	public List<DealerTemplateVo> getDealerTemplateVoList(String templateId) {
		List<DealerTemplateVo> list = null;
		try {
			list = financialCenterDao.getDealerTemplateVoList(templateId);
			
			List<Map<String,Object>> maps = financialCenterDao.loginNameAndVendsMap();
			for(Map<String, Object> m : maps){
				System.out.println("-----"+m.get("loginName"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Page<CheckUpAccountVo> getCheckUpAccountVoPage(Page<CheckUpAccountVo> page,
			CheckUpAccountVo checkUpAccountVo) {
		checkUpAccountVo.setPage(page);
		page.setList(financialCenterDao.getCheckUpAccountList(checkUpAccountVo));
		return page;
	}

	@Override
	public Page<AlreadyAccountVo> getCheckUpAccountVoPage_1(Page<AlreadyAccountVo> page,
			AlreadyAccountVo alreadyAccountVo) {
		alreadyAccountVo.setAmountStatus(2);
		alreadyAccountVo.setPage(page);
		page.setList(financialCenterDao.amountListA(alreadyAccountVo));
		return page;
	}

	@Override
	public Page<WaitingAccountVo> getCheckUpAccountVoPage_2(Page<WaitingAccountVo> page,
			WaitingAccountVo waitingAccountVo) {
		waitingAccountVo.setAmountStatus(1);
		waitingAccountVo.setPage(page);
		page.setList(financialCenterDao.amountListW(waitingAccountVo));
		return page;
	}

	@Override
	public String getAllVendCodeByLoginName(String loginName) {
		String vendCodes = financialCenterDao.getAllVendCodeByLoginName(loginName);
		return null != vendCodes ? vendCodes : "";
	}

	@Override
	public double querySaleMoneyByDate(Map<String, Object> map) {
		double saleMoney = 0;
		try {
			saleMoney = financialCenterDao.querySaleMoneyByDate(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saleMoney;
	}

	@Override
	public String getPercentage(Map<String, Object> map) {
		
		/*
		 * 判断   saleMoney_sale 属于销售额区间的那一个区间
		 */
		
		Map<String, Object> map_s = new HashMap<String,Object>();
		String templateId = map.get("templateId") != null ? map.get("templateId").toString() : "";
		String monthSaleMoney = map.get("saleMoney") != null ? map.get("saleMoney").toString() : "0";
		
		String useTimeStr = map.get("useTimeStr") != null ? map.get("useTimeStr").toString() : "";
		//获取系统当前时间与代理商模版应时间相差的月数
		int monthNum = MethodUtil.countMonths(useTimeStr ,systemCurrentTime,pattern);
		monthNum = monthNum != 0 ? monthNum : 1;
		
		
		map_s.put("templateId", templateId );
		map_s.put("saleMoney", monthSaleMoney);
		
		//是否属于 销售额<=
		int result_one = financialCenterDao.isSaleOne(map_s);
		boolean isSaleOne = result_one == 1 ? true : false;
		
		//是否属于 < 销售额  ≤
		int result_two = financialCenterDao.isSaleTwo(map_s);
		boolean isSaleTwo = result_two == 1 ? true : false;
		
		//是否属于 < 销售额  ≤
		int result_tree = financialCenterDao.isSaleThree(map_s);
		boolean isSaleThree = result_tree == 1 ? true : false;
		
		/*
		 * 判断 monthNum 处于售货机销售分成月份区间的那一个区间
		 */
		//判断 monthNum 是否属于销售分成的第一个月区间
		Map<String, Object> parms = new HashMap<String,Object>();
		parms.put("monthNum", monthNum);
		parms.put("templateId", templateId);
		
		//是否属于第一个月区间
		int result_1 = financialCenterDao.isFristMonthSale(parms);
		boolean isFristMonth_sale = result_1 == 1 ? true : false;
		
		//是否属于第二月区间
		int result_2 = financialCenterDao.isSecondMonthSale(parms);
		boolean isSecond_sale = result_2 == 1 ? true : false;
		
		//是否属于第三月区间
		int result_3 = financialCenterDao.isThreeMonthSale(parms);
		boolean isThree_sale = result_3 == 1 ? true : false;
		
		//是否属于第四月区间
		int result_4 = financialCenterDao.isFourthMonthSale(parms);
		boolean isFourth_sale = result_4 == 1 ? true : false;
		
		//月份区间参数值
		int monthValue = 0;
		
		if(isSaleOne){//是否属于第一个销售区间
			
			if(isFristMonth_sale){//属于第一个月份区间
				//取出第一月份区间的参数值
				monthValue = financialCenterDao.getFristMonthSaleValue_1(templateId);
			}else if(isSecond_sale){//属于第二个月份区间
				//取出第二月份区间的参数值
				monthValue = financialCenterDao.getSecondMonthSaleValue_1(templateId);
			}else if(isThree_sale){//属于第三个月份区间
				//取出第三月份区间的参数值
				monthValue = financialCenterDao.getThreeMonthSaleValue_1(templateId);
			}else if(isFourth_sale){//属于第四个月份区间
				//取出第四月份区间的参数值
				monthValue = financialCenterDao.getFourthMonthSaleValue_1(templateId);
			}
			
		}else if(isSaleTwo){////是否属于第二个销售区间
			if(isFristMonth_sale){//属于第一个月份区间
				//取出第一月份区间的参数值
				monthValue = financialCenterDao.getFristMonthSaleValue_2(templateId);
			}else if(isSecond_sale){//属于第二个月份区间
				//取出第二月份区间的参数值
				monthValue = financialCenterDao.getSecondMonthSaleValue_2(templateId);
			}else if(isThree_sale){//属于第三个月份区间
				//取出第三月份区间的参数值
				monthValue = financialCenterDao.getThreeMonthSaleValue_2(templateId);
			}else if(isFourth_sale){//属于第四个月份区间
				//取出第四月份区间的参数值
				monthValue = financialCenterDao.getFourthMonthSaleValue_2(templateId);
			}
		}else if(isSaleThree){
			if(isFristMonth_sale){//属于第一个月份区间
				//取出第一月份区间的参数值
				monthValue = financialCenterDao.getFristMonthSaleValue_3(templateId);
			}else if(isSecond_sale){//属于第二个月份区间
				//取出第二月份区间的参数值
				monthValue = financialCenterDao.getSecondMonthSaleValue_3(templateId);
			}else if(isThree_sale){//属于第三个月份区间
				//取出第三月份区间的参数值
				monthValue = financialCenterDao.getThreeMonthSaleValue_3(templateId);
			}else if(isFourth_sale){//属于第四个月份区间
				//取出第四月份区间的参数值
				monthValue = financialCenterDao.getFourthMonthSaleValue_3(templateId);
			}
		}
		StringBuffer str = new StringBuffer();
		return str.append(monthValue).append("%")+"";
	}

	@Override
	public Map<String, Object> queryParmaIdByLoginName(String loginName) {
		Map<String, Object> param = null;
		try {
			param = financialCenterDao.queryParmaIdByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}

	@Override
	public double queryTotalSubsidy(Map<String, Object> parm) {
		double totalSubSiby = 0;
		try {
			totalSubSiby = financialCenterDao.queryTotalSubsidy(parm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalSubSiby;
	}

	@Override
	public int queryPeriods(Map<String, Object> parm) {
		int periods = 0;
		try {
			periods = financialCenterDao.queryPeriods(parm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return periods;
	}

	@Override
	public double queryAcaMoney(Map<String, Object> parm) {
		double acaMoney = 0;
		try {
			acaMoney = financialCenterDao.queryAcaMoney(parm);
		} catch (Exception e) {
			
		}
		return acaMoney;
	}

	@Override
	public int queryAdvertisNum(Map<String, Object> parm) {
		List<Map<String, Object>> adMapList = financialCenterDao.queryAdvertisNum(parm);
		int adNum = 0;
		if(null != adMapList && adMapList.size() >0){
			for(Map<String, Object> m : adMapList){
				//取出广告id字符串
				String adIdString = m.get("aDList").toString();
				if(null != adIdString){
					if(adIdString.indexOf(",") != -1){//有多条广告
						adNum += adIdString.split(",").length;
					}else{//只有一条广告
						adNum ++;
					}
				}
			}
		}
		return adNum;
	}

	@Override
	public int aleardyPushNum(Map<String, Object> parm) {
		int pushNum = 0;
		try {
			pushNum = financialCenterDao.aleardyPushNum(parm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pushNum;
	}

	@Override
	public Page<OutAccountVo> getCheckUpAccountVoPage_3(Page<OutAccountVo> page,
			OutAccountVo outAccountVo) {
		outAccountVo.setPage(page);
		page.setList(financialCenterDao.outAmountList(outAccountVo));
		return page;
	}

}
