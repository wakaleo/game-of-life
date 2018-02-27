package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 代理商分帐模版设置VO 表：hcf_dealer_divide_account_template
 * 
 * @author LongZhangWei
 * @date 2017年11月10日 下午4:07:21
 */
public class DivideAccountVo extends BaseDataEntity<DivideAccountVo> implements Serializable {

	/**
	 * 系序号
	 */
	private static final long serialVersionUID = 1L;

	// 模版ID
	private String templateId;
	// 模版名称
	private String templateName;
	// 结算方式
	private Integer settlementWay;
	// 项目类型
	private String probjectType;
	/*---售货机销售分成----参数设置----start---*/

	/* 月份参数值 */
	private Integer fristMonthOne_sale;
	private Integer fristMonthTwo_sale;
	private Integer secondMonthOne_sale;
	private Integer secondMonthTwo_sale;
	private Integer threeMonthOne_sale;
	private Integer threeMonthTwo_sale;
	private Integer fourthMonthOne_sale;
	private Integer fourthMonthTwo_sale;

	private double saleOne;
	private double saleSecond_1;
	private double saleSecond_2;
	private double saleThree;

	/* 销售额的值 */

	private double fristSaleValue_1;
	private double secondSaleValue_1;
	private double threeSaleValue_1;
	private double fourthSaleValue_1;

	private double fristSaleValue_2;
	private double secondSaleValue_2;
	private double threeSaleValue_2;
	private double fourthSaleValue_2;

	private double fristSaleValue_3;
	private double secondSaleValue_3;
	private double threeSaleValue_3;
	private double fourthSaleValue_3;

	/*---售货机销售分成----参数设置----end---*/

	/*---机器补贴----参数设置----start---*/
	/* 月份参数值 */
	private Integer fristMonthOne_vend;
	private Integer fristMonthTwo_vend;
	private Integer secondMonthOne_vend;
	private Integer secondMonthTwo_vend;
	private Integer threeMonthOne_vend;
	private Integer threeMonthTwo_vend;
	private Integer fourthMonthOne_vend;
	private Integer fourthMonthTwo_vend;

	/* 月分对应的补贴 */
	private double fristSaleValue_vend;
	private double secondSaleValue_vend;
	private double threeSaleValue_vend;
	private double fourthSaleValue_vend;
	/*---机器补贴----参数设置----end---*/

	// 广告分成 广告费
	private double advertMoney;

	// 操作人
	private String operator;

	// 开始时间
	private Date startTime;
	private Timestamp createTime;
	private String startTimeStr;

	// 结束时间
	private Date endTime;
	private Timestamp updateTime;
	private String endTimeStr;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(Integer settlementWay) {
		this.settlementWay = settlementWay;
	}

	public String getProbjectType() {
		return probjectType;
	}

	public void setProbjectType(String probjectType) {
		this.probjectType = probjectType;
	}

	public Integer getFristMonthOne_sale() {
		return fristMonthOne_sale;
	}

	public void setFristMonthOne_sale(Integer fristMonthOne_sale) {
		this.fristMonthOne_sale = fristMonthOne_sale;
	}

	public Integer getFristMonthTwo_sale() {
		return fristMonthTwo_sale;
	}

	public void setFristMonthTwo_sale(Integer fristMonthTwo_sale) {
		this.fristMonthTwo_sale = fristMonthTwo_sale;
	}

	public Integer getSecondMonthOne_sale() {
		return secondMonthOne_sale;
	}

	public void setSecondMonthOne_sale(Integer secondMonthOne_sale) {
		this.secondMonthOne_sale = secondMonthOne_sale;
	}

	public Integer getSecondMonthTwo_sale() {
		return secondMonthTwo_sale;
	}

	public void setSecondMonthTwo_sale(Integer secondMonthTwo_sale) {
		this.secondMonthTwo_sale = secondMonthTwo_sale;
	}

	public Integer getThreeMonthOne_sale() {
		return threeMonthOne_sale;
	}

	public void setThreeMonthOne_sale(Integer threeMonthOne_sale) {
		this.threeMonthOne_sale = threeMonthOne_sale;
	}

	public Integer getThreeMonthTwo_sale() {
		return threeMonthTwo_sale;
	}

	public void setThreeMonthTwo_sale(Integer threeMonthTwo_sale) {
		this.threeMonthTwo_sale = threeMonthTwo_sale;
	}

	public Integer getFourthMonthOne_sale() {
		return fourthMonthOne_sale;
	}

	public void setFourthMonthOne_sale(Integer fourthMonthOne_sale) {
		this.fourthMonthOne_sale = fourthMonthOne_sale;
	}

	public Integer getFourthMonthTwo_sale() {
		return fourthMonthTwo_sale;
	}

	public void setFourthMonthTwo_sale(Integer fourthMonthTwo_sale) {
		this.fourthMonthTwo_sale = fourthMonthTwo_sale;
	}

	public double getFristSaleValue_1() {
		return fristSaleValue_1;
	}

	public void setFristSaleValue_1(double fristSaleValue_1) {
		this.fristSaleValue_1 = fristSaleValue_1;
	}

	public double getSecondSaleValue_1() {
		return secondSaleValue_1;
	}

	public void setSecondSaleValue_1(double secondSaleValue_1) {
		this.secondSaleValue_1 = secondSaleValue_1;
	}

	public double getThreeSaleValue_1() {
		return threeSaleValue_1;
	}

	public void setThreeSaleValue_1(double threeSaleValue_1) {
		this.threeSaleValue_1 = threeSaleValue_1;
	}

	public double getFourthSaleValue_1() {
		return fourthSaleValue_1;
	}

	public void setFourthSaleValue_1(double fourthSaleValue_1) {
		this.fourthSaleValue_1 = fourthSaleValue_1;
	}

	public double getFristSaleValue_2() {
		return fristSaleValue_2;
	}

	public void setFristSaleValue_2(double fristSaleValue_2) {
		this.fristSaleValue_2 = fristSaleValue_2;
	}

	public double getSecondSaleValue_2() {
		return secondSaleValue_2;
	}

	public void setSecondSaleValue_2(double secondSaleValue_2) {
		this.secondSaleValue_2 = secondSaleValue_2;
	}

	public double getThreeSaleValue_2() {
		return threeSaleValue_2;
	}

	public void setThreeSaleValue_2(double threeSaleValue_2) {
		this.threeSaleValue_2 = threeSaleValue_2;
	}

	public double getFourthSaleValue_2() {
		return fourthSaleValue_2;
	}

	public void setFourthSaleValue_2(double fourthSaleValue_2) {
		this.fourthSaleValue_2 = fourthSaleValue_2;
	}

	public double getFristSaleValue_3() {
		return fristSaleValue_3;
	}

	public void setFristSaleValue_3(double fristSaleValue_3) {
		this.fristSaleValue_3 = fristSaleValue_3;
	}

	public double getSecondSaleValue_3() {
		return secondSaleValue_3;
	}

	public void setSecondSaleValue_3(double secondSaleValue_3) {
		this.secondSaleValue_3 = secondSaleValue_3;
	}

	public double getThreeSaleValue_3() {
		return threeSaleValue_3;
	}

	public void setThreeSaleValue_3(double threeSaleValue_3) {
		this.threeSaleValue_3 = threeSaleValue_3;
	}

	public double getFourthSaleValue_3() {
		return fourthSaleValue_3;
	}

	public void setFourthSaleValue_3(double fourthSaleValue_3) {
		this.fourthSaleValue_3 = fourthSaleValue_3;
	}

	public Integer getFristMonthOne_vend() {
		return fristMonthOne_vend;
	}

	public void setFristMonthOne_vend(Integer fristMonthOne_vend) {
		this.fristMonthOne_vend = fristMonthOne_vend;
	}

	public Integer getFristMonthTwo_vend() {
		return fristMonthTwo_vend;
	}

	public void setFristMonthTwo_vend(Integer fristMonthTwo_vend) {
		this.fristMonthTwo_vend = fristMonthTwo_vend;
	}

	public Integer getSecondMonthOne_vend() {
		return secondMonthOne_vend;
	}

	public void setSecondMonthOne_vend(Integer secondMonthOne_vend) {
		this.secondMonthOne_vend = secondMonthOne_vend;
	}

	public Integer getSecondMonthTwo_vend() {
		return secondMonthTwo_vend;
	}

	public void setSecondMonthTwo_vend(Integer secondMonthTwo_vend) {
		this.secondMonthTwo_vend = secondMonthTwo_vend;
	}

	public Integer getThreeMonthOne_vend() {
		return threeMonthOne_vend;
	}

	public void setThreeMonthOne_vend(Integer threeMonthOne_vend) {
		this.threeMonthOne_vend = threeMonthOne_vend;
	}

	public Integer getThreeMonthTwo_vend() {
		return threeMonthTwo_vend;
	}

	public void setThreeMonthTwo_vend(Integer threeMonthTwo_vend) {
		this.threeMonthTwo_vend = threeMonthTwo_vend;
	}

	public Integer getFourthMonthOne_vend() {
		return fourthMonthOne_vend;
	}

	public void setFourthMonthOne_vend(Integer fourthMonthOne_vend) {
		this.fourthMonthOne_vend = fourthMonthOne_vend;
	}

	public Integer getFourthMonthTwo_vend() {
		return fourthMonthTwo_vend;
	}

	public void setFourthMonthTwo_vend(Integer fourthMonthTwo_vend) {
		this.fourthMonthTwo_vend = fourthMonthTwo_vend;
	}

	public double getFristSaleValue_vend() {
		return fristSaleValue_vend;
	}

	public void setFristSaleValue_vend(double fristSaleValue_vend) {
		this.fristSaleValue_vend = fristSaleValue_vend;
	}

	public double getSecondSaleValue_vend() {
		return secondSaleValue_vend;
	}

	public void setSecondSaleValue_vend(double secondSaleValue_vend) {
		this.secondSaleValue_vend = secondSaleValue_vend;
	}

	public double getThreeSaleValue_vend() {
		return threeSaleValue_vend;
	}

	public void setThreeSaleValue_vend(double threeSaleValue_vend) {
		this.threeSaleValue_vend = threeSaleValue_vend;
	}

	public double getFourthSaleValue_vend() {
		return fourthSaleValue_vend;
	}

	public void setFourthSaleValue_vend(double fourthSaleValue_vend) {
		this.fourthSaleValue_vend = fourthSaleValue_vend;
	}

	public double getAdvertMoney() {
		return advertMoney;
	}

	public void setAdvertMoney(double advertMoney) {
		this.advertMoney = advertMoney;
	}

	public double getSaleOne() {
		return saleOne;
	}

	public void setSaleOne(double saleOne) {
		this.saleOne = saleOne;
	}

	public double getSaleSecond_1() {
		return saleSecond_1;
	}

	public void setSaleSecond_1(double saleSecond_1) {
		this.saleSecond_1 = saleSecond_1;
	}

	public double getSaleSecond_2() {
		return saleSecond_2;
	}

	public void setSaleSecond_2(double saleSecond_2) {
		this.saleSecond_2 = saleSecond_2;
	}

	public double getSaleThree() {
		return saleThree;
	}

	public void setSaleThree(double saleThree) {
		this.saleThree = saleThree;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
