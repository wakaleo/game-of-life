package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 商品销售明细类 hcf_vend_order_info
 * 
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:06:16
 */
public class GoodsSaleDetailVo extends BaseDataEntity<GoodsSaleDetailVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 来源：1-便捷神 2-凯欣达
	private Integer source;
	private String sourceStr;

	// 订单号，唯一标示此次交易的流水号
	private String orderNo;
	// 订单状态，1-交易成功 2-交易失败,待退款 3-交易失败，已退款
	private Integer orderStatus;
	private String oderStatusStr;

	// 订单创建时间
	private Timestamp orderCreateTime;
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
	private String amountStr;

	// 支付状态，1-未支付 2-已支付
	private Integer payStatus;
	private String payStatusStr;

	// 支付时间
	private Timestamp payTime;
	// 支付方式,1-微信支付 2-支付宝支付 3-现金 4-雅支付 5-取货码  6-抽奖 7-泛贩支付
	private Integer payType;
	private String payTypeStr;

	// 支付用户ID，根据支付方式提供微信或者支付宝ID，如果是现金支付则填【现金】
	private String payUser;
	// 出货状态，1-已出货 2-未出货
	private Integer shipStatus;
	private String shipStatusStr;

	// 出货时间
	private Timestamp shipTime;
	// 记录更新时间
	private Timestamp updateTime;
	// 查询的开始时间
	private Date startTime;
	// 查询的结束时间
	private Date endTime;

	public Integer getSource() {
		return source;
	}
	
	public void setSource(Integer source) {
		this.source = source;
	}

	@ExcelField(title = "来源", align = 2, sort = 15)
	public String getSourceStr() {
		String str = "";
		if(source.intValue() == 1)
			str = "便捷神";
		if(source.intValue() == 2)
			str = "凯欣达";
		if(source.intValue() == 3)
			str = "乐科";
		if(source.intValue() == 4)
			str = "零壹比特";
		return str;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	@ExcelField(title = "订单号", align = 2, sort = 1)
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

	@ExcelField(title = "订单状态", align = 2, sort = 2)
	public String getOderStatusStr() {
		String str = "";
		if (orderStatus.intValue() == 1)
			str = "交易成功";
		if (orderStatus.intValue() == 2)
			str = "交易失败,待退款";
		if (orderStatus.intValue() == 3)
			str = "交易失败，已退款";
		return str;
	}

	public void setOderStatusStr(String oderStatusStr) {
		this.oderStatusStr = oderStatusStr;
	}

	@ExcelField(title = "订单创建时间", align = 2, sort = 3)
	public Timestamp getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Timestamp orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	@ExcelField(title = "机器编码", align = 2, sort = 4)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "商品编号", align = 2, sort = 5)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 6)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "货道", align = 2, sort = 7)
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

	@ExcelField(title = "价格（单位：分）", align = 2, sort = 8)
	public String getAmountStr() {
		return String.valueOf(amount);
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	@ExcelField(title = "支付状态", align = 2, sort = 9)//支付状态，1-未支付 2-已支付 3-部分支付 4-支付完成 5-已退回 6-已申请退款 7-退款成功 8-申请退款失败
	public String getPayStatusStr() {
		String str = "";
		if(payStatus.intValue() == 1){
			str = "未支付";
		}
		if(payStatus.intValue() == 2){
			str = "已支付";
		}
		if(payStatus.intValue() == 3){
			str = "部分支付";
		}
		if(payStatus.intValue() == 4){
			str = "支付完成";
		}
		if(payStatus.intValue() == 5){
			str = "已退回";
		}
		if(payStatus.intValue() == 6){
			str = "已申请退款";
		}
		if(payStatus.intValue() == 7){
			str = "退款成功";
		}
		if(payStatus.intValue() == 8){
			str = "申请退款失败";
		}
		return str;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	@ExcelField(title = "支付时间", align = 2, sort = 10)
	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@ExcelField(title = "支付方式", align = 2, sort = 11)
	public String getPayTypeStr() {// 支付方式,1-微信支付 2-支付宝支付 3-现金 4-雅支付 5-取货码
		String str = "";
		if (payType.intValue() == 1)
			str = "微信支付";
		if (payType.intValue() == 2)
			str = "支付宝支付";
		if (payType.intValue() == 3)
			str = "现金";
		if (payType.intValue() == 4)
			str = "雅支付";
		if (payType.intValue() == 5)
			str = "取货码";
		if (payType.intValue() == 6)
			str = "抽奖";
		if (payType.intValue() == 7)
			str = "泛贩支付";
		return str;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
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

	@ExcelField(title = "出货状态", align = 2, sort = 12)
	public String getShipStatusStr() {
		String str = "";
		if (shipStatus.intValue() == 1)
			str = "已出货";
		if (shipStatus.intValue() == 2)
			str = "未出货";
		if (shipStatus.intValue() == 3)
			str = "未通知出货";
		if (shipStatus.intValue() == 4)
			str = "已通知出货";
		if (shipStatus.intValue() == 5)
			str = "通知出货失败";
		if (shipStatus.intValue() == 6)
			str = "出货成功";
		if (shipStatus.intValue() == 7)
			str = "出货失败";
		return str;
	}

	public void setShipStatusStr(String shipStatusStr) {
		this.shipStatusStr = shipStatusStr;
	}

	@ExcelField(title = "出货时间", align = 2, sort = 13)
	public Timestamp getShipTime() {
		return shipTime;
	}

	public void setShipTime(Timestamp shipTime) {
		this.shipTime = shipTime;
	}

	@ExcelField(title = "记录更新时间", align = 2, sort = 14)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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

}
