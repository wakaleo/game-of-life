package com.huilian.hlej.hcf.vo;

import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 订单基本信息类 hcf_dealer_order
 * 
 * @author LongZhangWei
 * @date 2017年8月29日 下午3:43:32
 */
public class OrderBaseInfoVo extends BaseDataEntity<OrderBaseInfoVo> {

	private static final long serialVersionUID = 1L;
	// id
	private String id;
	// 订单号
	private String orderNo;
	// 订单时间
	private Date createTime;
	// 订单总金额
	private double money;
	private String moneyName;

	// 用户id
	private Integer userId;
	// 商品id
	private String goodsId;
	// 商品数量
	private Integer goodsCount;
	private String goodsCountName;
	// 交易流水
	private String runningAccount;

	// 支付类型(0:微信;1:支付宝;2取货码;3:银联刷卡;4银联扫码)
	private Integer payWay;
	private String payWayName;

	// 订单来源(0:微信公从号;1PC)
	private Integer orderSource;
	private String orderSourceName;

	// 订单状态(0:待付款;1:已付款;2:待接单;3已接单;4:待发货;5:已发货;6:待收货;7:已收货;8:取消订单;9:申请退款;10:退款已受理;11:退款已拒绝;12:退款中;13:已退款;14:已关闭)
	private Integer orderStatus;
	private String orderStatusName;
	// 收货人
	private String consignee;
	// 收货人地址
	private String address;
	// 收货人电话
	private String cellPhone;
	private String cellPhoneStr;

	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 买家留言
	private String buyerMessage;
	// 物流公司(0:顺丰;1:申通;2中通;3:韵达;4天天快递)
	private Integer logisticsCompany;
	// 订单备注
	private String orderRemark;
	// 代理商姓名
	private String dealerName;

	private String orderList;
	private Integer syncStatus;
	private String syncRemark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "订货单号", align = 2, sort = 20)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "交易时间", align = 2, sort = 70)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@ExcelField(title = "订单金额（元）", align = 2, sort = 50)
	public String getMoneyName() {
		return String.valueOf(money);
	}

	public void setMoneyName(String moneyName) {
		this.moneyName = moneyName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ExcelField(title = "交易流水", align = 2, sort = 10)
	public String getRunningAccount() {
		return runningAccount;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	@ExcelField(title = "订货件数", align = 2, sort = 60)
	public String getGoodsCountName() {
		return String.valueOf(goodsCount);
	}

	public void setGoodsCountName(String goodsCountName) {
		this.goodsCountName = goodsCountName;
	}

	public void setRunningAccount(String runningAccount) {
		this.runningAccount = runningAccount;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	@ExcelField(title = "支付类型", align = 2, sort = 30)
	public String getPayWayName() {
		String str = "";
		if (null != this.payWay) {
			if (this.payWay.intValue() == 1) {
				str = "微信";
			}
			if (this.payWay.intValue() == 2) {
				str = "支付宝";
			}
			if (this.payWay.intValue() == 3) {
				str = "现金";
			}
			if (this.payWay.intValue() == 4) {
				str = "雅支付";
			}
			if (this.payWay.intValue() == 5) {
				str = "取货码";
			}
			if (this.payWay.intValue() == 6) {
				str = "银联刷卡";
			}
			if (this.payWay.intValue() == 7) {
				str = "银联扫码";
			}
			if (this.payWay.intValue() == 8) {
				str = "通联支付";
			}
		}
		return str;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@ExcelField(title = "订单状态", align = 2, sort = 70)
	public String getOrderStatusName() {
		String orderStatusStr = "";
		if (null != this.orderStatus) {
			if (this.orderStatus.intValue() == 1) {
				orderStatusStr = "待付款";
			}
			if (this.orderStatus.intValue() == 2) {
				orderStatusStr = "已付款";
			}
			if (this.orderStatus.intValue() == 3) {
				orderStatusStr = "待接单";
			}
			if (this.orderStatus.intValue() == 4) {
				orderStatusStr = "已接单";
			}
			if (this.orderStatus.intValue() == 5) {
				orderStatusStr = "待发货";
			}
			if (this.orderStatus.intValue() == 6) {
				orderStatusStr = "已发货";
			}
			if (this.orderStatus.intValue() == 7) {
				orderStatusStr = "待收货";
			}
			if (this.orderStatus.intValue() == 8) {
				orderStatusStr = "已收货";
			}
			if (this.orderStatus.intValue() == 9) {
				orderStatusStr = "取消订单";
			}
			if (this.orderStatus.intValue() == 10) {
				orderStatusStr = "申请退款";
			}
			if (this.orderStatus.intValue() == 11) {
				orderStatusStr = "退款已受理";
			}
			if (this.orderStatus.intValue() == 12) {
				orderStatusStr = "退款已拒绝";
			}
			if (this.orderStatus.intValue() == 13) {
				orderStatusStr = "退款中";
			}
			if (this.orderStatus.intValue() == 14) {
				orderStatusStr = "已退款";
			}
			if (this.orderStatus.intValue() == 15) {
				orderStatusStr = "已关闭";
			}
		}
		return orderStatusStr;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	@ExcelField(title = "订单来源", align = 2, sort = 40)
	public String getOrderSourceName() {
		String orderStr = "";
		if (null != this.orderSource) {
			if (this.orderSource.intValue() == 1) {
				orderStr = "微信公从号";
			}
			if (this.orderSource.intValue() == 2) {
				orderStr = "PC";
			}
		}
		return orderStr;
	}

	public void setOrderSourceName(String orderSourceName) {
		this.orderSourceName = orderSourceName;
	}

	@ExcelField(title = "收货人", align = 2, sort = 70)
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@ExcelField(title = "收货地址", align = 2, sort = 70)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	@ExcelField(title = "电话", align = 2, sort = 70)
	public String getCellPhoneStr() {
		return String.valueOf(cellPhone);
	}

	public void setCellPhoneStr(String cellPhoneStr) {
		this.cellPhoneStr = cellPhoneStr;
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

	@ExcelField(title = "代理商", align = 2, sort = 70)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public int getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(int logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncRemark() {
		return syncRemark;
	}

	public void setSyncRemark(String syncRemark) {
		this.syncRemark = syncRemark;
	}

}
