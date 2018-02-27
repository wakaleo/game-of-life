package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.StockInfoVo;
import com.huilian.hlej.hcf.vo.VendGoodsStockVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VendStockDao {

	List<StockInfoVo> queryStockInfoVoPage(StockInfoVo stockInfoVo);

	List<VendGoodsStockVo> queryVendGoodsStockPage(VendGoodsStockVo vendGoodsStockVo);
}
