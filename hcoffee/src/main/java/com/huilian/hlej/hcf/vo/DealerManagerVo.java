package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 经销商管理实体类
 * 
 * @author LongZhangWei
 * @date 2017年8月24日 上午11:48:03
 */
public class DealerManagerVo extends BaseDataEntity<DealerManagerVo> implements Serializable{

	private static final long serialVersionUID = 1L;
	// 姓名
	private String dealerName;
	// 类型
	private int dealerType;
	// 代理商级别
	private int dealerGrade;
	// 跟进状态
	private int flowStatus;
	// 合作状态
	private int conStatus;
	// 电话
	private int cellPhone;
	// 地址
	private String address;
	// 详细地址
	private String detailAddress;
	// 最后登录时间
	private Date lastLoginTime;
	// 创建时间
	private Date createTime;
	// 身份证号
	private String idCard;
	// 银行卡号
	private String bankCarNumber;
	// 银行卡信息
	private String bankCarInfo;
	// 地区
	private String area;
	// 生日
	private Date birthday;
	// 性别
	private String gender;
	// 昵称
	private String nickname;
	// 邮箱
	private String mailbox;
	// 上级经销商ID
	private int parentId;

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public int getDealerType() {
		return dealerType;
	}

	public void setDealerType(int dealerType) {
		this.dealerType = dealerType;
	}

	public int getDealerGrade() {
		return dealerGrade;
	}

	public void setDealerGrade(int dealerGrade) {
		this.dealerGrade = dealerGrade;
	}

	public int getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	public int getConStatus() {
		return conStatus;
	}

	public void setConStatus(int conStatus) {
		this.conStatus = conStatus;
	}

	public int getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(int cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankCarNumber() {
		return bankCarNumber;
	}

	public void setBankCarNumber(String bankCarNumber) {
		this.bankCarNumber = bankCarNumber;
	}

	public String getBankCarInfo() {
		return bankCarInfo;
	}

	public void setBankCarInfo(String bankCarInfo) {
		this.bankCarInfo = bankCarInfo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
