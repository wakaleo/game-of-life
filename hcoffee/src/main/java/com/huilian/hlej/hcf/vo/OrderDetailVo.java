package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 订单明细类
 * 
 * @author LongZhangWei
 * @date 2017年9月6日 下午10:42:02
 */
public class OrderDetailVo extends BaseDataEntity<OrderDetailVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 交易编码
	private String runningAccount;
	// 订单号
	private String orderNo;
	// 支付类型
	private Integer payType;
	private String payTypeStr;

	// 支付状态
	private Integer payStatus;

	// 订单状态
	private Integer orderStatus;

	// 销售金额（元）
	private Double saleMoney;
	private String saleMoneyStr;

	// 销售件数
	private Integer saleCount;
	private String saleCountStr;

	// 交易时间
	private Timestamp payTime;
	// 上传时间
	private Timestamp updateTime;
	// 出货结果
	private Integer shipStatus;
	private String shipStatusStr;

	// 出货日期
	private Timestamp shipTime;
	private String shipTimeStr;

	// 机器编码
	private String vendCode;
	private String vendCodes;
	// 部署地
	private String location;
	// 货道编码
	private String shelf;
	// 商品名称
	private String goodsName;
	// 代理商
	private String dealerName;
	// 查询开 始时间
	private Date startTime;
	// 查询结束时间
	private Date endTime;

	@ExcelField(title = "交易编码", align = 2, sort = 1)
	public String getRunningAccount() {
		return runningAccount;
	}

	public void setRunningAccount(String runningAccount) {
		this.runningAccount = runningAccount;
	}

	@ExcelField(title = "订单号", align = 2, sort = 2)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@ExcelField(title = "支付类型", align = 2, sort = 3)
	public String getPayTypeStr() {
		String str = "";
		if (payType.intValue() == 1) {
			str = "微信";
		}
		if (payType.intValue() == 2) {
			str = "支付宝";
		}
		if (payType.intValue() == 3) {
			str = "取货码";
		}
		if (payType.intValue() == 4) {
			str = "银联刷卡";
		}
		if (payType.intValue() == 5) {
			str = "银联扫码";
		}
		return str;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	public Double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}

	@ExcelField(title = "销售金额（元）", align = 2, sort = 4)
	public String getSaleMoneyStr() {
		return String.valueOf(saleMoney != null ? saleMoney : "0.00");
	}

	public void setSaleMoneyStr(String saleMoneyStr) {
		this.saleMoneyStr = saleMoneyStr;
	}

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	@ExcelField(title = "销售件数", align = 2, sort = 5)
	public String getSaleCountStr() {
		return String.valueOf(saleCount != null ? saleCount : "0");
	}

	public void setSaleCountStr(String saleCountStr) {
		this.saleCountStr = saleCountStr;
	}

	@ExcelField(title = "交易时间", align = 2, sort = 6)
	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@ExcelField(title = "上传时间", align = 2, sort = 7)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(Integer shipStatus) {
		this.shipStatus = shipStatus;
	}

	@ExcelField(title = "出货结果", align = 2, sort = 9)
	public String getShipStatusStr() {
		String str = "";
		if (shipStatus.intValue() == 1) {
			str = "已出货";
		}
		if (shipStatus.intValue() == 2) {
			str = "未出货";
		}
		if (shipStatus.intValue() == 3) {
			str = "未通知出货";
		}
		if (shipStatus.intValue() == 4) {
			str = "已通知出货";
		}
		if (shipStatus.intValue() == 5) {
			str = "通知出货失败 ";
		}
		if (shipStatus.intValue() == 6) {
			str = "出货成功 ";
		}
		if (shipStatus.intValue() == 7) {
			str = "出货失败";
		}
		return str;
	}

	public void setShipStatusStr(String shipStatusStr) {
		this.shipStatusStr = shipStatusStr;
	}

	@ExcelField(title = "机器编码", align = 2, sort = 10)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "部署地", align = 2, sort = 11)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@ExcelField(title = "货道编码", align = 2, sort = 12)
	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 13)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "代理商", align = 2, sort = 70)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
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

	public Timestamp getShipTime() {
		return shipTime;
	}

	public void setShipTime(Timestamp shipTime) {
		this.shipTime = shipTime;
	}

	public String getShipTimeStr() {
		return shipTimeStr;
	}

	public void setShipTimeStr(String shipTimeStr) {
		this.shipTimeStr = shipTimeStr;
	}

	public String getVendCodes() {
		return vendCodes;
	}

	public void setVendCodes(String vendCodes) {
		this.vendCodes = vendCodes;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
