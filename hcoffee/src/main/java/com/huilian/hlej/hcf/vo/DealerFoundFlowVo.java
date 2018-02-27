package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 代理商补贴实体类
 * 
 * @author LongZhangWei
 * @date 2017年11月23日 下午5:20:30
 */
public class DealerFoundFlowVo extends BaseDataEntity<DealerFoundFlowVo> implements Serializable {

	/**
	 * 系列号
	 */
	private static final long serialVersionUID = 1L;

	// 经销商登录名
	private String loginName;
	// 经销商姓名
	private String dealerName;
	// 结算月份：以月为单位，例如：2017-11
	private String settlementMonth;
	// 金额，单位：分
	private int amount;
	// 金额类型：1-补贴金额；2-广告金额；3-销售金额；4-采购支出金额；5-提现金额
	private int amountType;
	// 金额状态：1待结算；2已结算
	private int amountStatus;
	// 结算/采购/提现日期：实际日期
	private Timestamp checkoutTime;
	// 流水号
	private String serialsNumber;

	// 当前月份销售额
	private Integer curMonthSaleMoney;

	// 上一个月销售额
	private Integer befMonthSaleMoney;

	// 机器绑定的年月
	private String monthStr;

	// 绑定的机器数
	private Integer num;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(String settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmountType() {
		return amountType;
	}

	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}

	public int getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(int amountStatus) {
		this.amountStatus = amountStatus;
	}

	public Timestamp getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Timestamp checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getSerialsNumber() {
		return serialsNumber;
	}

	public void setSerialsNumber(String serialsNumber) {
		this.serialsNumber = serialsNumber;
	}

	public Integer getCurMonthSaleMoney() {
		return curMonthSaleMoney;
	}

	public void setCurMonthSaleMoney(Integer curMonthSaleMoney) {
		this.curMonthSaleMoney = curMonthSaleMoney;
	}

	public Integer getBefMonthSaleMoney() {
		return befMonthSaleMoney;
	}

	public void setBefMonthSaleMoney(Integer befMonthSaleMoney) {
		this.befMonthSaleMoney = befMonthSaleMoney;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

}
