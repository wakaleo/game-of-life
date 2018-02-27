package com.huilian.hlej.hcf.vo;

public class OrderImportVo {

	// 来源：1-便捷神 2-凯欣达 3-乐科 4-零壹比特
	private Integer source;
	// 订单号，唯一标示此次交易的流水号
	private String orderNo;
	// 订单状态，1-交易成功 2-交易失败,待退款 3-交易失败，已退款
	private Integer orderStatus;
	// 订单创建时间
	private String orderCreateTime;
	// 机器编码
	private String vendCode;
	// 商品编号
	private String goodsId;
	// 商品名称
	private String goodsName;
	// 货道
	private String shelf;
	// 价格（单位：分）
	private float amount;
	// 支付方式,1-微信支付 2-支付宝支付 3-现金 4-雅支付 5-取货码
	private Integer payType;
	// 支付状态，1-未支付 2-已支付 3-部分支付 4-支付完成 5-已退回 6-已中请退款 7-退款成功 8-申请退款失败
	private Integer payStatus;
	// 支付用户ID，根据支付方式提供微信或者支付宝ID，如果是现金支付则填【现金】
	private String payUser;
	// 出货状态：1-已出货 2-未出货 3-未通知出货 4-已通知出货 5-通知出货失败 6-出货成功 7-出货失败
	private Integer shipStatus;
	// 出货时间
	private String shipTime;
	// 记录更新时间
	private String updateTime;
	// 交易流水
	private String runningAccount;
	// 支付时间
	private String payTime;
	// 类目
	private String type;

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

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
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

	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRunningAccount() {
		return runningAccount;
	}

	public void setRunningAccount(String runningAccount) {
		this.runningAccount = runningAccount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OrderImportVo [source=" + source + ", orderNo=" + orderNo + ", orderStatus=" + orderStatus
				+ ", orderCreateTime=" + orderCreateTime + ", vendCode=" + vendCode + ", goodsId=" + goodsId
				+ ", goodsName=" + goodsName + ", shelf=" + shelf + ", amount=" + amount + ", payType=" + payType
				+ ", payStatus=" + payStatus + ", payUser=" + payUser + ", shipStatus=" + shipStatus + ", shipTime="
				+ shipTime + ", updateTime=" + updateTime + ", runningAccount=" + runningAccount + "]";
	}

}
