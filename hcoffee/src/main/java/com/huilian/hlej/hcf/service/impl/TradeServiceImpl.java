package com.huilian.hlej.hcf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.TradeDao;
import com.huilian.hlej.hcf.service.TradeService;
import com.huilian.hlej.hcf.vo.TradeInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 咖啡机Service实现类
 * @author LongZhangWei
 * @date 2017年12月25日 上午11:30:09
 */
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeDao tradeDao;

	@Override
	public Page<TradeInfoVo> getTradeInfoVoPage(Page<TradeInfoVo> page, TradeInfoVo tradeInfoVo) {
		tradeInfoVo.setPage(page);
		page.setList(tradeDao.findTradeInfoVoList(tradeInfoVo));
		return page;
	}
	
	
}
