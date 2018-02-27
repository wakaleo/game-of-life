package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 对帐实体类
 * 
 * @author LongZhangWei
 * @date 2017年11月27日 下午4:54:18
 */
public class CheckUpAccountVo extends BaseDataEntity<CheckUpAccountVo> implements Serializable {

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
	private double incomeMoney;
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

	private int amount;

	private Integer amountStatus;

	@ExcelField(title = "日期", align = 2, sort = 1)
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

	//@ExcelField(title = "日期", align = 2, sort = 10)
	public String getCheckUpTimes() {
		return checkUpTimes;
	}

	public void setCheckUpTimes(String checkUpTimes) {
		this.checkUpTimes = checkUpTimes;
	}

	@ExcelField(title = "收入（元）", align = 2, sort = 3)
	public double getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(double incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	@ExcelField(title = "支出（元）", align = 2, sort = 4)
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

	public Integer getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(Integer amountStatus) {
		this.amountStatus = amountStatus;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
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
