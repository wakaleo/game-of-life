package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 第三方交易查询Vo
 * 
 * @author yangbo
 * @date 2017年5月12日 下午2:27:37
 *
 */
public class TranQueryVo extends BaseDataEntity<TranQueryVo> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String vendCode;// 售货机编码 s
	private String orderNo;// 订单编号
	private String goodsId;// 商品编号
	private String orderStatus;// 订单状态：1.下单未支付 2.下单已支付 3.交易完成 4.交易失败 5.交易异常
	private String paymentStatus;// 1.未支付2.部分支付3.支付完成4.已退回5.已申请退款6.退款成功7.申请退款失败
	private String shipmentStatus;// 出货状态：1.未通知出货 2.已通知出货 3.通知出货失败，退款  4.出货成功 5.出货失败
	private Double amount;// 价格,单位分
	private String goodsName;// 商品名称
	private String channel;// 订单渠道
	private Date paymentDate;// 支付时间
	private Date createDate;// 订单创建时间
	private Date shipmentDate;// 出货时间
	private String orderName;// 订单名称
	private String shipmentName;// 支付名称
	private String paymentName;// 退货名称
	private String channelName;// 退货
	private Date startTime;// 开始查询时间开始时间
	private Date endTime;// 查询结束时间
	private String remark1;	//备注
	
	
	@ExcelField(title = "备注", align = 2, sort = 91)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@ExcelField(title = "售货机编码", align = 2, sort = 10)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "订单编号", align = 2, sort = 20)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "商品编号", align = 2, sort = 30)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}
	

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	
	@ExcelField(title = "商品名称", align = 2, sort = 40)
	public String getGoodsName() {
		return goodsName;
	}
	
	@ExcelField(title = "价格", align = 2, sort = 50)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	@ExcelField(title = "创建时间", align = 2, sort = 60)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@ExcelField(title = "订单状态", align = 2, sort = 70)
	public String getOrderName() {
		if (Integer.valueOf(orderStatus).intValue() == 1) {
			return "下单未支付";
		} else if (Integer.valueOf(orderStatus).intValue() == 2) {
			return "下单已支付 ";
		} else if (Integer.valueOf(orderStatus).intValue() == 3) {
			return "交易完成";
		} else if (Integer.valueOf(orderStatus).intValue() == 4) {
			return "交易失败";
		}
		return "交易异常";
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@ExcelField(title = "出货状态", align = 2, sort = 80)
	public String getShipmentName() {
		
		if (Integer.valueOf(shipmentStatus).intValue() == 1) {
			return "未通知出货";
		} else if (Integer.valueOf(shipmentStatus).intValue() == 2) {
			return "已通知出货 ";
		} else if (Integer.valueOf(shipmentStatus).intValue() == 3) {
			return "通知出货失败 ";
		} else if (Integer.valueOf(shipmentStatus).intValue() == 4) {
			return "出货成功";
		} 
		return "出货失败";
	}

	public void setShipmentName(String shipmentName) {
		this.shipmentName = shipmentName;
	}
	
	@ExcelField(title = "支付状态", align = 2, sort = 90)
	public String getPaymentName() {
		if (Integer.valueOf(paymentStatus).intValue() == 1) {
			return "未支付";
		} else if (Integer.valueOf(paymentStatus).intValue() == 2) {
			return "部分支付 ";
		} else if (Integer.valueOf(paymentStatus).intValue() == 3) {
			return "支付完成";
		} else if (Integer.valueOf(paymentStatus).intValue() == 4) {
			return "已退回";
		}else if (Integer.valueOf(paymentStatus).intValue() == 5) {
			return "已申请退款";
		}else if (Integer.valueOf(paymentStatus).intValue() == 6) {
			return "退款成功";
		}
		return "申请退款失败";
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	@ExcelField(title = "订单渠道", align = 2, sort = 51)
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
