package com.huilian.hlej.hcf.service;

import com.huilian.hlej.hcf.vo.StockInfoVo;
import com.huilian.hlej.hcf.vo.VendGoodsStockVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendStockService {

	/**
	 * 机器库存显示列表
	 * @param page
	 * @param stockInfoVo
	 * @return
	 */
	Page<StockInfoVo> getVendStockPage(Page<StockInfoVo> page, StockInfoVo stockInfoVo);

	/**
	 * 机器商品库存显示列表
	 * @param page
	 * @param stockInfoVo
	 * @return
	 */
	Page<VendGoodsStockVo> getVendGoodsStockPage(Page<VendGoodsStockVo> page, VendGoodsStockVo vendGoodsStockVo);
}
