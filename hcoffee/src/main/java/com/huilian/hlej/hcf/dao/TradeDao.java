package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.TradeInfoVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface TradeDao {

	/**
	 * 线下交易查询
	 * @param equipmentInfoVo
	 * @return
	 */
	List<TradeInfoVo> findTradeInfoVoList(TradeInfoVo tradeInfoVo);
}
