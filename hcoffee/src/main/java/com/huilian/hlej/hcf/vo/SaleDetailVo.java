package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 销售明细类
 * 
 * @author LongZhangWei
 * @date 2017年9月6日 下午3:33:32
 */
public class SaleDetailVo extends BaseDataEntity<SaleDetailVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 机器编号
	private String vendCode;
	// 部署地
	private String location;
	// 销售金额（元）
	private Double saleMoney;
	private String saleMoneyStr;

	// 销售件数
	private Integer saleCount;
	private String saleCountStr;

	// 出货日期
	private Timestamp shipTime;
	private String shipTimeStr;
	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;
	// 机器编码字符串
	private String vendCodes;
	// 订单号
	private String orderNo;
	// 搜索内容
	private String searchContent;
	// 代理商登录名
	private String loginName;
	// 代理商姓名
	private String dealerName;

	@ExcelField(title = "机器编号", align = 2, sort = 10)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "部署地", align = 2, sort = 20)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}

	@ExcelField(title = "销售金额（元）", align = 2, sort = 30)
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

	@ExcelField(title = "销售件数", align = 2, sort = 40)
	public String getSaleCountStr() {
		return String.valueOf(saleCount != null ? saleCount : "0");
	}

	public void setSaleCountStr(String saleCountStr) {
		this.saleCountStr = saleCountStr;
	}

	@ExcelField(title = "出货日期", align = 2, sort = 50)
	public Timestamp getShipTime() {
		return shipTime;
	}

	public void setShipTime(Timestamp shipTime) {
		this.shipTime = shipTime;
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

	public String getVendCodes() {
		return vendCodes;
	}

	public void setVendCodes(String vendCodes) {
		this.vendCodes = vendCodes;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getShipTimeStr() {
		return shipTimeStr;
	}

	public void setShipTimeStr(String shipTimeStr) {
		this.shipTimeStr = shipTimeStr;
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

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

}
