package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 经销商管理实体类
 * 
 * @author LongZhangWei
 * @date 2017年8月24日 上午11:48:03
 */
public class DealerManagermentVo extends BaseDataEntity<DealerManagermentVo> implements Serializable {

	/*
	 * 经销商基本信息 数据库对应表 hcf_dealer_baseInfo
	 */
	private static final long serialVersionUID = 1L;

	// ID
	private String dealerID;

	// 姓名
	private String dealerName;
	// 登录名
	private String loginName;
	// 类型
	private Integer dealerType;
	// 代理商级别
	private Integer dealerGrade;
	// 跟进状态
	private Integer followStatus;
	// 合作状态
	private Integer conStatus;
	// 电话
	private String cellPhone;
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
	private String dealerArea;
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
	// 上级经销商姓名
	private String parentName;
	// 跟进状态
	private Integer followStutas;
	// 银行编码
	private Integer bankCode;
	// 银行名称
	private String bankName;
	/*
	 * 设备设信息 数据库以应表 hcf_eq_dealer_relation
	 */

	// 存放设备编号
	private String[] arr_no = new String[10];
	// 存放设备位置
	private String[] arr_lo = new String[10];
	// 存放关系表id
	private String[] arr_id = new String[10];

	private String password;

	// 搜过经的内容
	private String searchText;

	// ---二期需求 公司代理商添加字段 --开始---//

	// 企业名
	private String companyName;
	// 征信建议
	private Integer zxjy;
	// 统一社会信用代码
	private String xyCode;
	// 征信查询结果
	private String zxcxjgUrl;
	// 证件照片
	private String zjzpUrl;
	// 征信查询结果上传文件的文件原名
	private String zxjgFileName;
	// 证件照片上传文件原名
	private String zjzpFileName;

	// ---二期需求 公司代理商添加字段 --结束---//

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getDealerID() {
		return dealerID;
	}

	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(Integer dealerType) {
		this.dealerType = dealerType;
	}

	public Integer getDealerGrade() {
		return dealerGrade;
	}

	public void setDealerGrade(Integer dealerGrade) {
		this.dealerGrade = dealerGrade;
	}

	public Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(Integer followStatus) {
		this.followStatus = followStatus;
	}

	public Integer getConStatus() {
		return conStatus;
	}

	public void setConStatus(Integer conStatus) {
		this.conStatus = conStatus;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	public String getDealerArea() {
		return dealerArea;
	}

	public void setDealerArea(String dealerArea) {
		this.dealerArea = dealerArea;
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String[] getArr_no() {
		return arr_no;
	}

	public void setArr_no(String[] arr_no) {
		this.arr_no = arr_no;
	}

	public String[] getArr_lo() {
		return arr_lo;
	}

	public void setArr_lo(String[] arr_lo) {
		this.arr_lo = arr_lo;
	}

	public String[] getArr_id() {
		return arr_id;
	}

	public void setArr_id(String[] arr_id) {
		this.arr_id = arr_id;
	}

	public Integer getFollowStutas() {
		return followStutas;
	}

	public void setFollowStutas(Integer followStutas) {
		this.followStutas = followStutas;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getZxjy() {
		return zxjy;
	}

	public void setZxjy(Integer zxjy) {
		this.zxjy = zxjy;
	}

	public String getXyCode() {
		return xyCode;
	}

	public void setXyCode(String xyCode) {
		this.xyCode = xyCode;
	}

	public String getZxcxjgUrl() {
		return zxcxjgUrl;
	}

	public void setZxcxjgUrl(String zxcxjgUrl) {
		this.zxcxjgUrl = zxcxjgUrl;
	}

	public String getZjzpUrl() {
		return zjzpUrl;
	}

	public void setZjzpUrl(String zjzpUrl) {
		this.zjzpUrl = zjzpUrl;
	}

	public String getZxjgFileName() {
		return zxjgFileName;
	}

	public void setZxjgFileName(String zxjgFileName) {
		this.zxjgFileName = zxjgFileName;
	}

	public String getZjzpFileName() {
		return zjzpFileName;
	}

	public void setZjzpFileName(String zjzpFileName) {
		this.zjzpFileName = zjzpFileName;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
