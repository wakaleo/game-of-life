package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

public class StockInfoVo extends BaseDataEntity<StockInfoVo> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 售货机编码
	private String vendCode;
	// 设备名称
	private String vendName;
	// 货道编号
	private String ailseId;
	// 商品编号
	private String goodsId;
	// 商品名称
	private String goodsName;
	// 商品价格
	private int goodsPrice;
	// 库存数
	private int stockAmount;
	// 库存变化
	private int changeAmount;
	// 货道容量
	private int ailseCapacity;
	// 设置类型
	private Integer setType;
	private String setTypeStr;
	// 设置时间
	private String setTimeStr;
	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;
	// 代理商搜索内容
	private String searchContent;

	@ExcelField(title = "售货机编码", align = 2, sort = 1)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "设备名称", align = 2, sort = 2)
	public String getVendName() {
		return vendName;
	}

	public void setVendName(String vendName) {
		this.vendName = vendName;
	}

	@ExcelField(title = "货道编号", align = 2, sort = 3)
	public String getAilseId() {
		return ailseId;
	}

	public void setAilseId(String ailseId) {
		this.ailseId = ailseId;
	}

	@ExcelField(title = "商品编号", align = 2, sort = 4)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 5)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "商品价格", align = 2, sort = 6)
	public int getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	@ExcelField(title = "库存数", align = 2, sort = 7)
	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	@ExcelField(title = "库存变化", align = 2, sort = 8)
	public int getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(int changeAmount) {
		this.changeAmount = changeAmount;
	}

	@ExcelField(title = "货道容量", align = 2, sort = 9)
	public int getAilseCapacity() {
		return ailseCapacity;
	}

	public void setAilseCapacity(int ailseCapacity) {
		this.ailseCapacity = ailseCapacity;
	}

	public Integer getSetType() {
		return setType;
	}

	public void setSetType(Integer setType) {
		this.setType = setType;
	}

	@ExcelField(title = "设置类型", align = 2, sort = 10)
	public String getSetTypeStr() {
		if (setType.intValue() == 1) {
			setTypeStr = "货道存量设置";
		}
		if (setType.intValue() == 2) {
			setTypeStr = "货道容量设置";
		}
		return setTypeStr;
	}

	public void setSetTypeStr(String setTypeStr) {
		this.setTypeStr = setTypeStr;
	}

	@ExcelField(title = "设置时间", align = 2, sort = 11)
	public String getSetTimeStr() {
		return setTimeStr;
	}

	public void setSetTimeStr(String setTimeStr) {
		this.setTimeStr = setTimeStr;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

}
