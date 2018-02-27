package com.huilian.hlej.hcf.vo;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 代理商库存明细类
 * 
 * @author LongZhangWei
 * @date 2017年9月5日 下午4:43:56
 */
public class DealerStockDetailVo extends BaseDataEntity<DealerStockDetailVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 产品代码
	private String goodsId;
	// 产品名称
	private String goodsName;
	// 单位
	private String specification;
	// 库存数
	private Integer count;
	// 已销售量
	private Integer saledAmount;
	//采购量
	private Integer procuAmount;
	// 采购在订量
	private Integer procurementAmount;
	// 建议采购量
	private Integer suggestProcurementAmount;
	// 报警阈值
	private Integer thresholdValue;
	// 代理商所有拥有的机器编码
	private String vendCodes;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSaledAmount() {
		return saledAmount;
	}

	public void setSaledAmount(Integer saledAmount) {
		this.saledAmount = saledAmount;
	}

	public Integer getProcurementAmount() {
		return procurementAmount;
	}

	public void setProcurementAmount(Integer procurementAmount) {
		this.procurementAmount = procurementAmount;
	}

	public Integer getSuggestProcurementAmount() {
		return suggestProcurementAmount;
	}

	public void setSuggestProcurementAmount(Integer suggestProcurementAmount) {
		this.suggestProcurementAmount = suggestProcurementAmount;
	}

	public Integer getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Integer thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public String getVendCodes() {
		return vendCodes;
	}

	public void setVendCodes(String vendCodes) {
		this.vendCodes = vendCodes;
	}

	public Integer getProcuAmount() {
		return procuAmount;
	}

	public void setProcuAmount(Integer procuAmount) {
		this.procuAmount = procuAmount;
	}

}
