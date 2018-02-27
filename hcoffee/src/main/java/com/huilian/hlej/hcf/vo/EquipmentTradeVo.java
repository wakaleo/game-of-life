package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 设备交易实体类
 * 
 * @author LongZhangWei
 * @date 2017年12月25日 上午9:38:53
 */
public class EquipmentTradeVo extends BaseDataEntity<EquipmentTradeVo> implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	// 机器编码
	private String vendCode;
	// 订单号
	private String orderNo;
	// 产品编号
	private String productCode;
	// 产品名称
	private String productName;
	// 支付类型
	private Integer payType;
	private String payTypeStr;
	// 价格（元）
	private double price;
	// 交易时间
	private Timestamp tradeTime;
	private String tradeTimeStr;
	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;

	@ExcelField(title = "机器编号", align = 2, sort = 1)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "订单号", align = 2, sort = 2)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "产品编号", align = 2, sort = 3)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@ExcelField(title = "支付类型", align = 2, sort = 5)
	public String getPayTypeStr() {
		if (payType.intValue() == 1) {
			payTypeStr = "取货码";
		}
		if (payType.intValue() == 2) {
			payTypeStr = "微信支付";
		}
		if (payType.intValue() == 3) {
			payTypeStr = "支付宝支付";
		}
		if (payType.intValue() == 4) {
			payTypeStr = "其他";
		}
		return payTypeStr;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	@ExcelField(title = "价格（元）", align = 2, sort = 6)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Timestamp getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}

	@ExcelField(title = "交易时间", align = 2, sort = 7)
	public String getTradeTimeStr() {
		return tradeTimeStr;
	}

	public void setTradeTimeStr(String tradeTimeStr) {
		this.tradeTimeStr = tradeTimeStr;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

}
