package com.huilian.hlej.hcf.service;

import com.huilian.hlej.hcf.vo.TradeInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 咖啡机Service
 * @author LongZhangWei
 * @date 2017年12月25日 上午11:30:36
 */
public interface TradeService {

	/**
	 * 设备信息查询
	 * @param page
	 * @param equipmentInfoVo
	 * @return
	 */
	Page<TradeInfoVo> getTradeInfoVoPage(Page<TradeInfoVo> page, TradeInfoVo tradeInfoVo);

}
