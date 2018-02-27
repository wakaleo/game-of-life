package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 比特机器货道信息实体类
 * 
 * @author ZhangZeBiao
 * @date 2017年12月14日 下午5:32:27
 */
public class BitMachineAisleInfoVo extends BaseDataEntity<BitMachineAisleInfoVo> implements Serializable {

	/*
	 * 对应表 machine_aisle_info
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	// 货道编号
	private String idStr;
	
	// 货道价格
	private Integer aisleGoodsSellingPrice;
	
	// 货道名称 
	private String aisleName;

	// 货道默认库存
	private Integer defaultGoodsStock;
	// 货道真实库存
	private Integer goodsStock;
	// 是否售卖
	private Integer isSaleEnabled;
	private String isSaleEnabledStr;
	// 是否货道故障  0正常1故障
	private Integer isBreak;
	private String isBreakStr;
	
	// 机器ID
	private BigInteger machineId;
	// 商品ID
	private Integer goodsSkuInfoGoodsSkuId;
	// 商品名称
	private String goodsSkuSubject;
	// 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date gmtCreated;
	// 修改时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;
	
	// 搜过经的内容
	private String searchText;
	
	
	
	
//	@ExcelField(title = "货道编号", align = 2, sort = 1)
	public String getIdStr() {
		return super.id;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	@ExcelField(title = "商品价格", align = 2, sort = 5)
	public Integer getAisleGoodsSellingPrice() {
		return aisleGoodsSellingPrice;
	}

	public void setAisleGoodsSellingPrice(Integer aisleGoodsSellingPrice) {
		this.aisleGoodsSellingPrice = aisleGoodsSellingPrice;
	}

	@ExcelField(title = "货道编号", align = 2, sort = 1)
	public String getAisleName() {
		return aisleName;
	}

	public void setAisleName(String aisleName) {
		this.aisleName = aisleName;
	}

	@ExcelField(title = "库存容量", align = 2, sort = 7)
	public Integer getDefaultGoodsStock() {
		return defaultGoodsStock;
	}

	public void setDefaultGoodsStock(Integer defaultGoodsStock) {
		this.defaultGoodsStock = defaultGoodsStock;
	}
	
	@ExcelField(title = "库存数", align = 2, sort = 6)
	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public Integer getIsSaleEnabled() {
		return isSaleEnabled;
	}

	public void setIsSaleEnabled(Integer isSaleEnabled) {
		this.isSaleEnabled = isSaleEnabled;
	}

	public String getIsSaleEnabledStr() {
		return isSaleEnabledStr;
	}

	public void setIsSaleEnabledStr(String isSaleEnabledStr) {
		this.isSaleEnabledStr = isSaleEnabledStr;
	}

	public Integer getIsBreak() {
		return isBreak;
	}

	public void setIsBreak(Integer isBreak) {
		this.isBreak = isBreak;
	}

	@ExcelField(title = "货道状态", align = 2, sort = 2)// 0正常1故障
	public String getIsBreakStr() {
		String str = "";
		if(isBreak == 0){
			str = "正常";
		}
		if(isBreak == 1){
			str = "故障";
		}
		return str;
	}

	public void setIsBreakStr(String isBreakStr) {
		this.isBreakStr = isBreakStr;
	}

	public BigInteger getMachineId() {
		return machineId;
	}

	public void setMachineId(BigInteger machineId) {
		this.machineId = machineId;
	}

	@ExcelField(title = "商品代码", align = 2, sort = 3)
	public Integer getGoodsSkuInfoGoodsSkuId() {
		return goodsSkuInfoGoodsSkuId;
	}

	public void setGoodsSkuInfoGoodsSkuId(Integer goodsSkuInfoGoodsSkuId) {
		this.goodsSkuInfoGoodsSkuId = goodsSkuInfoGoodsSkuId;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 4)
	public String getGoodsSkuSubject() {
		return goodsSkuSubject;
	}

	public void setGoodsSkuSubject(String goodsSkuSubject) {
		this.goodsSkuSubject = goodsSkuSubject;
	}

	
	
	
	
	
	
	
	
	
	
	

}
