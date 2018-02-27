package com.huilian.hlej.hcf.vo;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 机器商品库存实体类
 * 
 * @author LongZhangWei
 * @date 2017年12月12日 上午11:37:36
 */
public class VendGoodsStockVo extends BaseDataEntity<VendGoodsStockVo> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商品编号
	private String goodsId;
	// 商品名称
	private String goodsName;
	// 库存数
	private int stockAmount;
	// 售货机编码
	private String vendCode;
	// 代理商搜索内容
	private String searchContent;

	@ExcelField(title = "商品编号", align = 2, sort = 1)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 2)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "库存数", align = 2, sort = 3)
	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

}
