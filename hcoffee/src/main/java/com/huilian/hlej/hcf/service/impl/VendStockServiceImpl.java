package com.huilian.hlej.hcf.service.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendStockDao;
import com.huilian.hlej.hcf.service.VendStockService;
import com.huilian.hlej.hcf.vo.StockInfoVo;
import com.huilian.hlej.hcf.vo.VendGoodsStockVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class VendStockServiceImpl implements VendStockService {

	@Autowired
	private VendStockDao vendStockDao;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Page<StockInfoVo> getVendStockPage(Page<StockInfoVo> page, StockInfoVo stockInfoVo) {
		stockInfoVo.setPage(page);
		String startTimeStr = stockInfoVo.getStartTime() != null ? format.format(stockInfoVo.getStartTime()) : "";
		String endTimeStr = stockInfoVo.getEndTime() != null ? format.format(stockInfoVo.getEndTime()) : "";
		stockInfoVo.setStartTimeStr(startTimeStr);
		stockInfoVo.setEndTimeStr(endTimeStr);
		page.setList(vendStockDao.queryStockInfoVoPage(stockInfoVo));
		return page;
	}

	@Override
	public Page<VendGoodsStockVo> getVendGoodsStockPage(Page<VendGoodsStockVo> page, VendGoodsStockVo vendGoodsStockVo) {
		vendGoodsStockVo.setPage(page);
		page.setList(vendStockDao.queryVendGoodsStockPage(vendGoodsStockVo));
		return page;
	}

}
