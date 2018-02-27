package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.AlreadyAccountVo;
import com.huilian.hlej.hcf.vo.CheckUpAccountVo;
import com.huilian.hlej.hcf.vo.DealerTemplateVo;
import com.huilian.hlej.hcf.vo.DivideAccountVo;
import com.huilian.hlej.hcf.vo.OutAccountVo;
import com.huilian.hlej.hcf.vo.WaitingAccountVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface FinancialCenterService {

	/**
	 * 保存代理商模版参数
	 * @param divideAccountVo
	 * @return
	 */
	public Map<String, String> saveDivideAccountVo(DivideAccountVo divideAccountVo);
	
	/**
	 * 修改代理商模版参数
	 * @param dealerManagermentVo
	 * @return
	 */
	public Map<String, String> updateDivideAccountVo(DivideAccountVo divideAccountVo);
	
	/**
	 * 查询所有模版列表
	 * @param page
	 * @param divideAccountVo
	 * @return
	 */
	public Page<DivideAccountVo> getDivideAccountVoPage(Page<DivideAccountVo> page, DivideAccountVo divideAccountVo);
	
	/**
	 * 查询单个模版信息
	 * @param id
	 * @return
	 */
	DivideAccountVo getDivideAccountVo(String id);
	
	/**
	 * 查询关联该模版信息
	 * @param id
	 * @return
	 */
	List<DealerTemplateVo> getDealerTemplateVoList(String templateId);
	
	/**
	 * 查询对账单列表
	 * @param page
	 * @param checkUpAccountVo
	 * @return
	 */
	public Page<CheckUpAccountVo> getCheckUpAccountVoPage(Page<CheckUpAccountVo> page, CheckUpAccountVo checkUpAccountVo);
	
	/**
	 * 查询已结算列表
	 * @param page
	 * @param checkUpAccountVo
	 * @return
	 */
	public Page<AlreadyAccountVo> getCheckUpAccountVoPage_1(Page<AlreadyAccountVo> page, AlreadyAccountVo alreadyAccountVo);

	/**
	 * 查询待结算列表
	 * @param page
	 * @param checkUpAccountVo
	 * @return
	 */
	public Page<WaitingAccountVo> getCheckUpAccountVoPage_2(Page<WaitingAccountVo> page,
			WaitingAccountVo waitingAccountVo);

	/**
	 * 根据登录名查询其下的所有机器
	 * @param loginName
	 */
	public String getAllVendCodeByLoginName(String loginName);

	/**
	 * 根据机器编码和日期查询销售额
	 * @param map
	 */
	public double querySaleMoneyByDate(Map<String, Object> map);

	/**
	 * 获取给付系数%
	 * @param saleMoney
	 * @return
	 */
	public String getPercentage(Map<String, Object> map);

	/**
	 * 查询模版根据登录名
	 * @param loginName
	 * @return
	 */
	Map<String, Object> queryParmaIdByLoginName(String loginName);

	/**
	 * 查询总的补贴
	 * @param parm
	 * @return
	 */
	public double queryTotalSubsidy(Map<String, Object> parm);

	/**
	 * 查询发放补贴期数（机器补贴的）
	 * @param parm
	 * @return
	 */
	public int queryPeriods(Map<String, Object> parm);

	/**
	 * 根据某字时间段已结算的补贴
	 * @param parm
	 * @return
	 */
	public double queryAcaMoney(Map<String, Object> parm);

	/**
	 * 查询某月的广告数
	 * @param parm
	 * @return
	 */
	int queryAdvertisNum(Map<String, Object> parm);

	/**
	 * 已推送的机器数
	 * @param vendCodes
	 * @return
	 */
	public int aleardyPushNum(Map<String, Object> parm);

	/**
	 * 查询支出列表
	 * @param page
	 * @param checkUpAccountVo
	 * @return
	 */
	public Page<OutAccountVo> getCheckUpAccountVoPage_3(Page<OutAccountVo> page,
			OutAccountVo outAccountVo);
}
