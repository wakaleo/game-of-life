package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import org.restlet.util.StringReadingListener;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 代理商销售类
 * 
 * @author LongZhangWei
 * @date 2017年9月6日 上午11:52:30
 */
public class SaleManagementVo extends BaseDataEntity<SaleManagementVo> implements Serializable {

	private static final long serialVersionUID = 1L;
	// 登录名
	private String loginName;
	// ID
	private Integer ID;
	private String IDStr;

	// 姓名
	private String dealerName;
	// 电话
	private String cellPhone;
	private String cellPhoneStr;

	// 销售金额(元)
	private Double saleMoney;
	private String saleMoneyStr;

	// 销售数量(件)
	private Integer saleCount;
	private String saleCountStr;

	// 机械数
	private Integer machineNum;
	private String machineNumStr;

	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;
	// 订单号
	private String orderNo;
	// 搜索的内容
	private String searchContent;

	// 所拥有的机器编码
	private String vendCodes;
	// 机器编码
	private String vendCode;
	// 机器部署地
	private String location;

	@ExcelField(title = "登录名", align = 2, sort = 10)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@ExcelField(title = "ID", align = 2, sort = 20)
	public String getIDStr() {
		return String.valueOf(ID);
	}

	public void setIDStr(String iDStr) {
		IDStr = iDStr;
	}

	@ExcelField(title = "姓名", align = 2, sort = 30)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	@ExcelField(title = "电话", align = 2, sort = 40)
	public String getCellPhoneStr() {
		return String.valueOf(cellPhone);
	}

	public void setCellPhoneStr(String cellPhoneStr) {
		this.cellPhoneStr = cellPhoneStr;
	}

	public Double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}

	@ExcelField(title = "销售金额（元）", align = 2, sort = 50)
	public String getSaleMoneyStr() {
		return String.valueOf(saleMoney != null ? saleMoney : 0.00);
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

	@ExcelField(title = "销售数量（件）", align = 2, sort = 60)
	public String getSaleCountStr() {
		return String.valueOf(saleCount != null ? saleCount : 0);
	}

	public void setSaleCountStr(String saleCountStr) {
		this.saleCountStr = saleCountStr;
	}

	public Integer getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(Integer machineNum) {
		this.machineNum = machineNum;
	}

	@ExcelField(title = "机器数", align = 2, sort = 70)
	public String getMachineNumStr() {
		return String.valueOf(machineNum);
	}

	public void setMachineNumStr(String machineNumStr) {
		this.machineNumStr = machineNumStr;
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

	public String getVendCodes() {
		return vendCodes;
	}

	public void setVendCodes(String vendCodes) {
		this.vendCodes = vendCodes;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
