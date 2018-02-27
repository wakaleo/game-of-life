package com.huilian.hlej.hcf.vo;

import java.sql.Timestamp;

public class ExcelVo {

	// 来源：1-便捷神 2-凯欣达 3-乐科 4-零壹比特
	private Integer source;
	// 订单号，唯一标示此次交易的流水号
	private String orderNo;
	// 订单状态，1-交易成功 2-交易失败,待退款 3-交易失败，已退款
	private Integer orderStatus;
	// 订单创建时间
	private Timestamp createTime;
	// 机器编码
	private String vendCode;
	// 商品编号
	private String goodsId;
	// 商品名称
	private String goodsName;
	// 货道
	private String shelf;
	// 价格（单位：分）
	private Integer amount;
	// 支付状态，1-未支付 2-已支付 3-部分支付 4-支付完成 5-已退回 6-已中请退款 7-退款成功 8-申请退款失败
	private Integer payStatus;
	// 支付用户ID，根据支付方式提供微信或者支付宝ID，如果是现金支付则填【现金】
	private String payUser;
	// 出货状态：1-已出货 2-未出货 3-未通知出货 4-已通知出货 5-通知出货失败 6-出货成功 7-出货失败
	private Integer shipStatus;
	// 出货时间
	private Timestamp shipTime;
	// 记录更新时间
	private Timestamp updateTime;
	// 交易流水
	private String runningAccount;

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

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

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public Integer getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(Integer shipStatus) {
		this.shipStatus = shipStatus;
	}

	public Timestamp getShipTime() {
		return shipTime;
	}

	public void setShipTime(Timestamp shipTime) {
		this.shipTime = shipTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getRunningAccount() {
		return runningAccount;
	}

	public void setRunningAccount(String runningAccount) {
		this.runningAccount = runningAccount;
	}

}
