package com.huilian.hlej.jet.modules.cashbox.entity;

import java.sql.Date;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.supcan.annotation.treelist.cols.SupCol;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;
import com.huilian.hlej.jet.modules.sys.entity.User;

@SuppressWarnings("serial")
public class CashBoxPayOrderModel extends BaseDataEntity<CashBoxPayOrderModel> {
	private String id;
	private String transactionID; //交易流水号
	private String orderCode;// 订单号
	private String payCode;// 支付流水号
	private Integer payType;//支付方式
	private String payTypeName;//支付方式
	private String userId;// 用户ID
	private String userName;
	private String phone;// 手机号
	private String identityCode;// 身份证号码
	private Integer platformType;// 平台类型(1、汇理财；2、汇有房；3、汇生活)
	private String platformName;// 平台
	private String productId;// 产品ID
	private String productName;// 产品名称
	private Double orderMoney;// 支付金额
	private Date orderDateTime;// 支付日期
	private Integer orderForm;// 支付方式
	private String orderAccounts;// 支付账号
	private Integer orderStatus;// 支付状态
	private String remark;// 备注
	private String sellManager;// 销售经理
	private String equipmentCode;// 设备编号
	private String createUser;// 创建人
	private Date createTime;// 创建时间
	private String brokerPhone;// 经纪人电话
	private String startDate;
	private String endDate;
	private String sellName;
	private User seller;// 销售员信息

	@SupCol(isUnique = "true", isHide = "true")
	@ExcelField(title = "ID", type = 1, align = 2, sort = 1)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "交易流水号", align = 2, sort = 10)
	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	@ExcelField(title = "订单号", align = 2, sort = 20)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@ExcelField(title = "支付流水号", align = 2, sort = 30)
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@ExcelField(title = "支付方式", align = 2, sort = 35)
	public String getPayTypeName() {
		if (null == payType) {
			payTypeName = "-";
		} else if (payType == 1) {
			payTypeName = "刷卡支付";
		} else if (payType == 2) {
			payTypeName = "微信被扫支付";
		} else if (payType == 3) {
			payTypeName = "微信扫码支付";
		} else if (payType == 4) {
			payTypeName = "支付宝被扫支付";
		} else if (payType == 5) {
			payTypeName = "支付宝扫码支付";
		} else {
			payTypeName = "-";
		}
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	@ExcelField(title = "用户ID", align = 2, sort = 40)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ExcelField(title = "用户名称", type = 1, align = 1, sort = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "手机号码", align = 1, sort = 60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ExcelField(title = "身份证号码", align = 1, sort = 70)
	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	@ExcelField(title = "平台类型", align = 2, sort = 80)
	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	@ExcelField(title = "平台名称", align = 2, sort = 90)
	public String getPlatformName() {
		if (null == platformType) {
			platformName = "-";
		} else if (platformType == 1) {
			platformName = "汇理财";
		} else if (platformType == 2) {
			platformName = "汇生活";
		} else if (platformType == 3) {
			platformName = "汇有房";
		} else {
			platformName = "-";
		}
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	@ExcelField(title = "产品ID", align = 2, sort = 100)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@ExcelField(title = "产品名称", align = 2, sort = 110)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@ExcelField(title = "支付金额", align = 2, sort = 120)
	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	@ExcelField(title = "支付日期", align = 1, sort = 130)
	public Date getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(Date orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	@ExcelField(title = "支付类型", align = 2, sort = 140)
	public Integer getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(Integer orderForm) {
		this.orderForm = orderForm;
	}

	@ExcelField(title = "交易账户", align = 1, sort = 150)
	public String getOrderAccounts() {
		return orderAccounts;
	}

	public void setOrderAccounts(String orderAccounts) {
		this.orderAccounts = orderAccounts;
	}

	@ExcelField(title = "交易状态", align = 2, sort = 160)
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@ExcelField(title = "备注", align = 1, sort = 170)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSellName(String sellName) {
		this.sellName = sellName;
	}

	@ExcelField(title = "销售员", align = 1, sort = 180)
	public String getSellName() {
		if (null == seller)
			sellName = "-";
		else
			sellName = seller.getName();
		return sellName;
	}

	public String getSellManager() {
		return sellManager;
	}

	public void setSellManager(String sellManager) {
		this.sellManager = sellManager;
	}

	@ExcelField(title = "设备编号", align = 1, sort = 190)
	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	@ExcelField(title = "创建用户", sort = 200)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@ExcelField(title = "创建日期", align = 1, sort = 210)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@ExcelField(title = "经纪人", align = 1, sort = 220)
	public String getBrokerPhone() {
		return brokerPhone;
	}

	public void setBrokerPhone(String brokerPhone) {
		this.brokerPhone = brokerPhone;
	}

	@Override
	public String toString() {
		return "CashBoxPayOrderModel [id=" + id + ", transactionID=" + transactionID + ", orderCode=" + orderCode + ", payCode=" + payCode + ", payType=" + payType + ", userId=" + userId + ", userName=" + userName + ", phone=" + phone
				+ ", identityCode=" + identityCode + ", platformType=" + platformType + ", platformName=" + platformName + ", productId=" + productId + ", productName=" + productName + ", orderMoney=" + orderMoney + ", orderDateTime=" + orderDateTime
				+ ", orderForm=" + orderForm + ", orderAccounts=" + orderAccounts + ", orderStatus=" + orderStatus + ", remark=" + remark + ", sellManager=" + sellManager + ", equipmentCode=" + equipmentCode + ", createUser=" + createUser
				+ ", createTime=" + createTime + ",brokerPhone=" + brokerPhone + "]";
	}
}