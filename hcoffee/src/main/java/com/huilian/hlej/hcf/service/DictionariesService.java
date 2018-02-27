package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.DictionariesVo;

/**
 * 字典Service
 * @author LongZhangWei
 * @date 2017年9月12日 上午9:54:46
 */
public interface DictionariesService {

	/**
	 * 查询订单状态
	 * @return
	 */
	public List<DictionariesVo> getOrderStatusList();
	
	/**
	 * 查询订单状态(商品销售明细)
	 * @return
	 */
	public List<DictionariesVo> getGoodsSaleDetailOrderStatusList();
	
	/**
	 * 订货中心支付类型(不包括取货码类型)
	 * @return
	 */
	public List<DictionariesVo> getOrderPayWayList();
	
	/**
	 * 销售管理支付类型(包括取货码类型)
	 * @return
	 */
	public List<DictionariesVo> getSalePayWayList();
	
	/**
	 * 订单来源
	 * @return
	 */
	public List<DictionariesVo> getOrderSourceList();
	
	/**
	 * 支付状态
	 * @return
	 */
	public List<DictionariesVo> getPayStatusList();
	
	/**
	 * 支付方式
	 * @return
	 */
	public List<DictionariesVo> getPayTypeList();
	
	/**
	 * 出货状态
	 * @return
	 */
	public List<DictionariesVo> getShipStatusList();
	
	/**
	 * 代理类型
	 * @return
	 */
	public List<DictionariesVo> getDealerTypeList();
	
	/**
	 * 代理级别
	 * @return
	 */
	public List<DictionariesVo> getDealerGradeList();
	
	/**
	 * 合作状态
	 * @return
	 */
	public List<DictionariesVo> getConStatusList();
	
	/**
	 * 性别
	 * @return
	 */
	public List<DictionariesVo> getSexList();
	
	/**
	 * 跟进状态
	 * @return
	 */
	public List<DictionariesVo> getStateList();
	
	
	/**
	 * 抽奖活动状态
	 * @return
	 */
	public List<DictionariesVo> getLottoActivityStatusList();
	
	
	/**
	 * 抽奖方式
	 * @return
	 */
	public List<DictionariesVo> getLottoWayList();
	
	
	/**
	 * 抽奖抽奖活动触发条件
	 * @return
	 */
	public List<DictionariesVo> getLottoTriggerConditionList();

	/**
	 * 抽奖奖品类型
	 * @return
	 */
	public List<DictionariesVo> getLottoPrizeTypeList();

	/**
	 * 抽奖来源
	 * @return
	 */
	public List<DictionariesVo> getLottoSource();
	
	/**
	 * 银行卡名称
	 * @return
	 */
	public List<DictionariesVo> getBankName();
	
}
