package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 支出记录体类
 * 
 * @author LongZhangWei
 * @date 2017年11月27日 下午4:54:18
 */
public class OutAccountVo extends BaseDataEntity<OutAccountVo> implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// 时间段字段 如2017.10.1-2017.10.30
	private String timeDuan;
	// 结算时间(每月5号,年月日时分秒)
	private String checkUpTime;
	// 结算时间(年月日)
	private String checkUpTimes;
	// 代理商
	private String loginName;
	// 收入（元）
	private String incomeMoney;
	// 支出（元）
	private double expenditureMoney;
	// 项目
	private String itemStr;
	// 状态
	private String statuStr;
	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;
	// 代理商的名称
	private String dealerName;
	// 项目
	private Integer amountType;
	private String amountTypeStr;

	private double amount;

	private Integer amountStatus;
	private String amountStatusStr;

	public String getTimeDuan() {
		return timeDuan;
	}

	public void setTimeDuan(String timeDuan) {
		this.timeDuan = timeDuan;
	}

	public String getCheckUpTime() {
		return checkUpTime;
	}

	public void setCheckUpTime(String checkUpTime) {
		this.checkUpTime = checkUpTime;
	}

	@ExcelField(title = "日期", align = 2, sort = 1)
	public String getCheckUpTimes() {
		return checkUpTimes;
	}

	public void setCheckUpTimes(String checkUpTimes) {
		this.checkUpTimes = checkUpTimes;
	}

	public String getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(String incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public double getExpenditureMoney() {
		return expenditureMoney;
	}

	public void setExpenditureMoney(double expenditureMoney) {
		this.expenditureMoney = expenditureMoney;
	}

	public String getItemStr() {
		return itemStr;
	}

	public void setItemStr(String itemStr) {
		this.itemStr = itemStr;
	}

	public String getStatuStr() {
		return statuStr;
	}

	public void setStatuStr(String statuStr) {
		this.statuStr = statuStr;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	@ExcelField(title = "代理商", align = 2, sort = 2)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getAmountType() {
		return amountType;
	}

	public void setAmountType(Integer amountType) {
		this.amountType = amountType;
	}

	@ExcelField(title = "项目", align = 2, sort = 4)
	public String getAmountTypeStr() {
		String str = "";
		if(amountType.intValue() == 4){
			str = "订货";
		}
		if(amountType.intValue() == 5){
			str = "提现";
		}
		return str;
	}

	public void setAmountTypeStr(String amountTypeStr) {
		this.amountTypeStr = amountTypeStr;
	}

	public Integer getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(Integer amountStatus) {
		this.amountStatus = amountStatus;
	}

	@ExcelField(title = "状态", align = 2, sort = 5)
	public String getAmountStatusStr() {
		String str = "";
		if(amountStatus.intValue() == 3 || amountStatus.intValue() == 5){
			str = "失败";
		}
		if(amountStatus.intValue() == 4 || amountStatus.intValue() == 6){
			str = "成功";
		}
		return str;
	}

	public void setAmountStatusStr(String amountStatusStr) {
		this.amountStatusStr = amountStatusStr;
	}

	@ExcelField(title = "支出（元）", align = 2, sort = 3)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
