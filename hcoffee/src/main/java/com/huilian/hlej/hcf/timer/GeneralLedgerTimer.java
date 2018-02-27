package com.huilian.hlej.hcf.timer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huilian.hlej.hcf.dao.FinancialCenterDao;
import com.huilian.hlej.hcf.util.MethodUtil;
import com.huilian.hlej.hcf.vo.DealerFoundFlowVo;



/**
 * 分帐定时任务
 * @author LongZhangWei
 * @date 2017年11月23日 上午10:11:28
 */
@Component
public class GeneralLedgerTimer{

	//时间格式
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	//获取系统当前时间  年-月-日
	private String systemCurrentTime = simpleDateFormat.format(new Date());
	
	@Autowired
	private FinancialCenterDao financialCenterDao;

	private String pattern = "yy-MM";
	
	private DecimalFormat df = new DecimalFormat("#.00");
	
	private static Logger logger = LoggerFactory.getLogger(GeneralLedgerTimer.class);
	
	/**
	 *已结算的 
	 */
	@Scheduled(cron="0 0 0 5 * ?")//每月5号执行一次
	public void alreadyCloseAccount(){
		logger.info("已结算定时任务启动了...........................................");
		//查询每个代理商下所拥有的机器码
		List<Map<String, Object>> loginName_vend_maps = financialCenterDao.loginNameAndVendsMap();
		for(Map<String, Object> map : loginName_vend_maps){
			//登录名
			String loginName = map.get("loginName").toString();
			//所拥有的机器码
			String vendCodes = map.get("vendCodes").toString();
			vendCodes = vendCodes != null ? MethodUtil.getFormatStr(vendCodes) : "";
			/*
			 * 如果代理商没有绑定模版不计算补贴
			 */
			Map<String, Object> useTime_tempId_map = financialCenterDao.getUseTimeByLoginName(loginName);
			if(null != useTime_tempId_map && useTime_tempId_map.size() >0){
				//代理商模版应用时间
				String useTimeStr = null != useTime_tempId_map ? useTime_tempId_map.get("useTimeStr").toString() : "";
				//模版id
				String templateId = null != useTime_tempId_map ? useTime_tempId_map.get("templateId").toString() : "";
				//结算方式
				int settlementWay = financialCenterDao.querySettlementWay(templateId);
				if(settlementWay == 2){
					//计算出该代理商所有机器上一个月的销售额
					Map<String, String> map2 = new HashMap<String,String>();
					map2.put("vendCodes", vendCodes);
					Integer beforeMonthSaleMoney = financialCenterDao.beforeMonthSaleMoney(map2);
					/*
					 * 判断   saleMoney_sale 属于销售额区间的那一个区间
					 */
					
					Map<String, Object> map_s = new HashMap<String,Object>();
					map_s.put("templateId", templateId);
					map_s.put("saleMoney", beforeMonthSaleMoney);
					
					//是否属于 销售额<=
					int result_one = financialCenterDao.isSaleOne(map_s);
					boolean isSaleOne = result_one == 1 ? true : false;
					
					//是否属于 < 销售额  ≤
					int result_two = financialCenterDao.isSaleTwo(map_s);
					boolean isSaleTwo = result_two == 1 ? true : false;
					
					//是否属于 < 销售额  ≤
					int result_tree = financialCenterDao.isSaleThree(map_s);
					boolean isSaleThree = result_tree == 1 ? true : false;
					
					//获取系统当前时间与代理商模版应时间相差的月数
					int monthNum = MethodUtil.countMonths(useTimeStr,systemCurrentTime,pattern);
					monthNum = monthNum != 0 ? monthNum : 1;
					/*
					 * 每一全代理商的  售货机销售分成 机器补贴   广告分成
					 */
					//----售货机销售分成--------------------------------start------//
					//售货机销售分成
					double vendingSaleMoney = 0;
					
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
					//计算该代理商的销售分成
					vendingSaleMoney = beforeMonthSaleMoney * monthValue * 0.01;
					String vSaleMoneyStr = vendingSaleMoney != 0.0 ? df.format(vendingSaleMoney) : null;
					
					int vSaleMoney = vSaleMoneyStr != null ? (int)Double.parseDouble(vSaleMoneyStr) : 0;
					
					/*
					 *每月的5号结算上一个月的补贴
					 *把计算出来的  售货机销售分成  机器补贴  广告分成  保存到数据表 
					 */
					
					SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
					String currentStr = sFormat.format(new Date());
					StringBuffer str_1 = new StringBuffer("JS");
					str_1.append(currentStr);
					
					//生成流水号
					int num_1 = financialCenterDao.countBySerialsNumber(str_1.toString());
					String serialsNumber_1 = createSerialsNumber(num_1, str_1);
					
					String beforeMonthStr = MethodUtil.beforeMonths();
					
					
					
					DealerFoundFlowVo dff_s = new DealerFoundFlowVo();
					dff_s.setLoginName(loginName);
					dff_s.setAmount(vSaleMoney);
					dff_s.setAmountType(3);
					dff_s.setAmountStatus(2);
					dff_s.setSettlementMonth(beforeMonthStr);
					dff_s.setSerialsNumber(serialsNumber_1);
					
					/*
					 *每个月5号结算前，需把该代理商以前待结算的记录全部改为已结算状态 
					 */
					Map<String, Object> statusMap_1 = new HashMap<String,Object>();
					statusMap_1.put("loginName", loginName);
					statusMap_1.put("amountType", 3);
					statusMap_1.put("amountStatus", 2);
					//修改之前待结算的状态更改为已结算
					financialCenterDao.updateAmountStatus(statusMap_1);
					//保存售货机销售分成
					int results_1 = financialCenterDao.isExistFoundByLoginName(statusMap_1);
					if(results_1 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_s);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_s);
					//----售货机销售分成--------------------------------end------//
					
					//----机器补贴--------------------------------start------//
					int machineSubsidy = 0;
					Map<String, String> parm = new HashMap<String,String>();
					parm.put("vendCodes", vendCodes);
					//获取该代理商在各个月绑定的机器及  年-月 
					List<DealerFoundFlowVo> monthVendList = financialCenterDao.monthVendNumList(parm);
					if(null != monthVendList && monthVendList.size() >0){
						for(DealerFoundFlowVo monthVend : monthVendList){
							//得到绑定机器年月
							String yearMonthStr = null != monthVend.getMonthStr() ? monthVend.getMonthStr() : "";
							//得到年月绑定的机器数
							int vendingNum = monthVend.getNum() != null ? monthVend.getNum() : 0;
							//获取机器绑定时间与当前系统时间相差的月数
							int monthNum_v = MethodUtil.countMonths(yearMonthStr, systemCurrentTime, pattern);
							int mNum = monthNum_v != 0 ? monthNum_v : 1;
							/*
							 * 判断  mNum 机器补贴月份区间  哪一个区间 isFristMonth_vend
							 */
							Map<String, Object> parmas = new HashMap<String,Object>();
							parmas.put("templateId", templateId);
							parmas.put("mNum", mNum);
							
							//是否属于第一个月区间
							int resultV_1 = financialCenterDao.isFristMonth_vend(parmas);
							boolean isFristMonth = resultV_1 == 1 ? true : false;
							
							//是否属于第二个月区间
							int resultV_2 = financialCenterDao.isFristMonth_vend(parmas);
							boolean isSecondMonth = resultV_2 == 1 ? true : false;
							//是否属于第一个月区间
							int resultV_3 = financialCenterDao.isFristMonth_vend(parmas);
							boolean isThreeMonth = resultV_3 == 1 ? true : false;
							//是否属于第一个月区间
							int resultV_4 = financialCenterDao.isFristMonth_vend(parmas);
							boolean isFourthMonth = resultV_4 == 1 ? true : false;
							
							int monthValue_v = 0;
							
							if(isFristMonth){//属于机器补贴第一个月区间
								//获取第一个月区间值
								monthValue_v = financialCenterDao.getFristMonthVendValue(templateId);
							}else if(isSecondMonth){//属于机器补贴第二个月区间
								//获取第二个月区间值
								monthValue_v = financialCenterDao.getSecondMonthVendValue(templateId);
							}else if(isThreeMonth){//属于机器补贴第三个月区间
								//获取第三个月区间值
								monthValue_v = financialCenterDao.getThreeMonthVendValue(templateId);
							}else if(isFourthMonth){//属于机器补贴第四个月区间
								//获取第四个月区间值
								monthValue_v = financialCenterDao.getFourthMonthVendValue(templateId);
							}
							machineSubsidy += monthValue_v * vendingNum;
						}
					}
					
					//生成流水号
					StringBuffer str_2 = new StringBuffer("JS");
					str_2.append(currentStr);
					int num_2 = financialCenterDao.countBySerialsNumber(str_2.toString());
					String serialsNumber_2 = createSerialsNumber(num_2, str_2);
					
					DealerFoundFlowVo dff_v = new DealerFoundFlowVo();
					dff_v.setLoginName(loginName);
					dff_v.setAmount(machineSubsidy*100);//把元转成分
					dff_v.setAmountType(1);
					dff_v.setAmountStatus(2);
					dff_v.setSettlementMonth(beforeMonthStr);
					dff_v.setSerialsNumber(serialsNumber_2);
					
					Map<String, Object> statusMap_2 = new HashMap<String,Object>();
					statusMap_2.put("loginName", loginName);
					statusMap_2.put("amountType", 1);
					statusMap_2.put("amountStatus", 2);
					financialCenterDao.updateAmountStatus(statusMap_2);
					int results_2 = financialCenterDao.isExistFoundByLoginName(statusMap_2);
					if(results_2 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_v);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_v);
					//----机器补贴--------------------------------end------//
					
					//----广告分成-------------------------------start---//
					int advertisementMoney = 0;
					
					//查询模版的广告费
					int adMoney = financialCenterDao.getAdMoney(templateId);
					//获取上一个月该代理商所有机器的广告条数
					int adNum = getSumAdNum(vendCodes,"before");
					adNum = adNum != 0 ? adNum : 1;
					//该代理商的广告补贴  需求未明确暂时为0
					//advertisementMoney = adNum * adMoney;
					
					
					//广告分成
					
					//生成流水号
					StringBuffer str_3 = new StringBuffer("JS");
					str_3.append(currentStr);
					int num_3 = financialCenterDao.countBySerialsNumber(str_3.toString());
					String serialsNumber_3 = createSerialsNumber(num_3, str_3);
					
					DealerFoundFlowVo dff_a = new DealerFoundFlowVo();
					dff_a.setLoginName(loginName);
					dff_a.setAmount(advertisementMoney*100);//把元转成分
					dff_a.setAmountType(2);
					dff_a.setAmountStatus(2);
					dff_a.setSettlementMonth(beforeMonthStr);
					dff_a.setSerialsNumber(serialsNumber_3);
					
					Map<String, Object> statusMap_3 = new HashMap<String,Object>();
					statusMap_3.put("loginName", loginName);
					statusMap_3.put("amountType", 2);
					statusMap_3.put("amountStatus", 2);
					financialCenterDao.updateAmountStatus(statusMap_3);
					
					int results_3 = financialCenterDao.isExistFoundByLoginName(statusMap_3);
					if(results_3 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_a);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_a);
					//----广告分成-------------------------------end---//
				}
			}
		}
	}
	
	/**
	 * 
	 * 
	 * 待结算的
	 */
	@Scheduled(cron="0 0 23 * * ?")//每天23点执行一次
	public void waitingCloseAccount(){
		logger.info("待结算定时任务启动了...........................................");
		//查询每个代理商下所拥有的机器码
		List<Map<String, Object>> loginName_vend_maps = financialCenterDao.loginNameAndVendsMap();
		for(Map<String, Object> map : loginName_vend_maps){
			//登录名
			String loginName = map.get("loginName").toString();
			//所拥有的机器码
			String vendCodes = map.get("vendCodes").toString();
			vendCodes = vendCodes != null ? MethodUtil.getFormatStr(vendCodes) : "";
			/*
			 * 查询模版应用时间与模版id
			 */
			Map<String, Object> useTime_tempId_map = financialCenterDao.getUseTimeByLoginName(loginName);
			
			if(null != useTime_tempId_map && useTime_tempId_map.size() >0){
				//代理商模版应用时间
				String useTimeStr = null != useTime_tempId_map ? useTime_tempId_map.get("useTimeStr").toString() : "";
				//模版id
				String templateId = null != useTime_tempId_map ? useTime_tempId_map.get("templateId").toString() : "";
				//结算方式
				int settlementWay = financialCenterDao.querySettlementWay(templateId);
				/*
				 * 只结算方式是人工结算时才进行结算
				 */
				if(settlementWay == 2){
					//计算出该代理商所有机器当前月的销售额
					Map<String, String> maps = new HashMap<String,String>();
					maps.put("vendCodes", vendCodes);
					Integer currentMonthSaleMoney = financialCenterDao.currentMonthSaleMoney(maps);
					/*
					 * 判断   saleMoney_sale 属于销售额区间的那一个区间
					 */
					
					Map<String, Object> map_s = new HashMap<String,Object>();
					map_s.put("templateId", templateId);
					map_s.put("saleMoney", currentMonthSaleMoney);
					
					//是否属于 销售额<=
					int result_one = financialCenterDao.isSaleOne(map_s);
					boolean isSaleOne = result_one == 1 ? true : false;
					
					//是否属于 < 销售额  ≤
					int result_two = financialCenterDao.isSaleTwo(map_s);
					boolean isSaleTwo = result_two == 1 ? true : false;
					
					//是否属于 <
					int result_tree = financialCenterDao.isSaleThree(map_s);
					boolean isSaleThree = result_tree == 1 ? true : false;
					
					//获取系统当前时间与代理商模版应时间相差的月数
					int monthNum = MethodUtil.countMonths(useTimeStr,systemCurrentTime,pattern);
					monthNum = monthNum != 0 ? monthNum : 1;
					/*
					 * 每一全代理商的  售货机销售分成 机器补贴   广告分成
					 */
					//---售货机销售分成------------------------------------start----//
					double vendingSaleMoney = 0;
					
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
					//计算该代理商的销售分成
					vendingSaleMoney = currentMonthSaleMoney * monthValue * 0.01;
					String vSaleMoneyStr = df.format(vendingSaleMoney);
					int vsaleMoney = vSaleMoneyStr != null ? (int)Double.parseDouble(vSaleMoneyStr) : 0;
					String currentMonthStr = simpleDateFormat.format(new Date());
					//售货机销售分成
					DealerFoundFlowVo dff_s = new DealerFoundFlowVo();
					dff_s.setLoginName(loginName);
					dff_s.setAmount(vsaleMoney);
					dff_s.setAmountType(3);
					dff_s.setAmountStatus(1);
					dff_s.setSettlementMonth(currentMonthStr);
					
					/*
					 *保存前判断是否已存该代理商该类型的记录
					 *a.如果存在，就修改
					 *b.如是存在，就添加
					 */
					Map<String, Object> parm_1 = new HashMap<String,Object>();
					parm_1.put("loginName", loginName);
					parm_1.put("amountType", 3);
					parm_1.put("settlementMonth", currentMonthStr);
					parm_1.put("amountStatus", 1);
					/*
					 * 判断当前的待结算记录是否存在
					 * 1.如果已存待结算金额记录就更新金额
					 * 2.没有就添加
					 */
					int results_1 = financialCenterDao.isExistFoundByLoginName(parm_1);
					if(results_1 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_s);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_s);
					
					//---售货机销售分成------------------------------------end----//
					
					
					//---机器补贴------------------------------------start----//
					int machineSubsidy = 0;
					Map<String, String> parm = new HashMap<String,String>();
					parm.put("vendCodes", vendCodes);
					//获取该代理商在各个月绑定的机器及  年-月 
					List<DealerFoundFlowVo> monthVendList = financialCenterDao.monthVendNumList(parm);
					if(null != monthVendList && monthVendList.size() >0){
						for(DealerFoundFlowVo monthVend : monthVendList){
							//得到绑定机器年月
							String yearMonthStr = monthVend.getMonthStr() != null ? String.valueOf(monthVend.getMonthStr()) : "";
							//得到年月绑定的机器数
							int vendingNum = monthVend.getNum() != null ? monthVend.getNum() : 0;
							//获取机器绑定时间与当前系统时间相差的月数
							int monthNum_v = 0;
							if(null != yearMonthStr && !yearMonthStr.equals("")){
								monthNum_v = MethodUtil.countMonths(yearMonthStr, systemCurrentTime, pattern);
							}
							int mNum = monthNum_v != 0 ? monthNum_v : 1;
							/*
							 * 判断  mNum 机器补贴月份区间  哪一个区间 isFristMonth_vend
							 */
							Map<String, Object> parmas = new HashMap<String,Object>();
							parmas.put("templateId", templateId);
							parmas.put("mNum", mNum);
							
							//是否属于第一个月区间
							int resultV_1 = financialCenterDao.isFristMonth_vend(parmas);
							boolean isFristMonth = resultV_1 == 1 ? true : false;
							
							//是否属于第二个月区间
							int resultV_2 = financialCenterDao.isSecondMonth_vend(parms);
							boolean isSecondMonth = resultV_2 == 1 ? true : false;
							//是否属于第一个月区间
							int resultV_3 = financialCenterDao.isThreeMonth_vend(parms);
							boolean isThreeMonth = resultV_3 == 1 ? true : false;
							//是否属于第一个月区间
							int resultV_4 = financialCenterDao.isFourthMonth_vend(parms);
							boolean isFourthMonth = resultV_4 == 1 ? true : false;
							
							int monthValue_v = 0;
							
							if(isFristMonth){//属于机器补贴第一个月区间
								//获取第一个月区间值
								monthValue_v = financialCenterDao.getFristMonthVendValue(templateId);
							}else if(isSecondMonth){//属于机器补贴第二个月区间
								//获取第二个月区间值
								monthValue_v = financialCenterDao.getSecondMonthVendValue(templateId);
							}else if(isThreeMonth){//属于机器补贴第三个月区间
								//获取第三个月区间值
								monthValue_v = financialCenterDao.getThreeMonthVendValue(templateId);
							}else if(isFourthMonth){//属于机器补贴第四个月区间
								//获取第四个月区间值
								monthValue_v = financialCenterDao.getFourthMonthVendValue(templateId);
							}
							machineSubsidy += monthValue_v * vendingNum;
						}
					}
					
					//机器补贴
					DealerFoundFlowVo dff_v = new DealerFoundFlowVo();
					dff_v.setLoginName(loginName);
					dff_v.setAmount(machineSubsidy*100);//把元转成分
					dff_v.setAmountType(1);
					dff_v.setAmountStatus(1);
					dff_v.setSettlementMonth(currentMonthStr);
					
					Map<String, Object> parm_2 = new HashMap<String,Object>();
					parm_2.put("loginName", loginName);
					parm_2.put("amountType", 1);
					parm_2.put("settlementMonth", currentMonthStr);
					parm_2.put("amountStatus", 1);
					
					int results_2 = financialCenterDao.isExistFoundByLoginName(parm_2);
					if(results_2 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_v);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_v);
					//---机器补贴------------------------------------end----//
					
					//---广告分成------------------------------------start----//
					int advertisementMoney = 0;
					
					//查询模版的广告费
					int adMoney = financialCenterDao.getAdMoney(templateId);
					//获取当前月该代理商所有机器的广告条数
					int adNum = getSumAdNum(vendCodes,"current");
					//该代理商的广告补贴  广告补贴需求示明确，暂时为0
					//advertisementMoney = adNum * adMoney;
					
					//广告分成
					DealerFoundFlowVo dff_a = new DealerFoundFlowVo();
					dff_a.setLoginName(loginName);
					dff_a.setAmount(advertisementMoney*100);//数据库里存的是分,这里需把元转成分
					dff_a.setAmountType(2);
					dff_a.setAmountStatus(1);
					dff_a.setSettlementMonth(currentMonthStr);
					
					Map<String, Object> parm_3 = new HashMap<String,Object>();
					parm_3.put("loginName", loginName);
					parm_3.put("amountType", 2);
					parm_3.put("settlementMonth", currentMonthStr);
					parm_3.put("amountStatus", 1);
					
					int results_3 = financialCenterDao.isExistFoundByLoginName(parm_3);
					if(results_3 > 0)
						financialCenterDao.updateDealerFoundFlowVo(dff_a);
					else
						financialCenterDao.saveDealerFoundFlowVo(dff_a);
					//---广告分成------------------------------------end----//
				}
			}
		}
	}

	/**
	 * 查询机器下上一个月份的广告条数
	 * @param vendCodes
	 * @return
	 */
	
	private int getSumAdNum(String vendCodes,String str) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("vendCodes", vendCodes);
		List<Map<String, Object>> adMapList = null;
		if("before".equals(str)){
			adMapList = financialCenterDao.beforeAdListMap(map);
		}
		if("current".equals(str)){
			adMapList = financialCenterDao.currentAdListMap(map);
		}
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
	
	private static String createSerialsNumber(int num,StringBuffer str){
		int lastNum = 6;
		int len = String.valueOf(num).length();
		int count = getNumber(String.valueOf(num));
		if(len > lastNum)
			lastNum++;
		if(len == count)
			len ++;
		for(int i=0;i<lastNum-len;i++){
			str.append("0");
		}
		str.append(++num);
		return str.toString();
	}
	
	private static int getNumber(String str){
		byte[] bytes = str.getBytes();
		int count = 0;
		for(byte b : bytes){
			if(b == 57)
				count++;
		}
		return count;
	}
}
