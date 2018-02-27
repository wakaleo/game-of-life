package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 咖啡活动实体类
 * 
 * @author LongZhangWei
 * @date 2018年1月19日 上午9:37:00
 */
public class CoffeeActivityInfoVo extends BaseDataEntity<CoffeeActivityInfoVo> implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	// 活动方案编号
	private String schemeNo;
	// 获取礼品的验证码,8位随机字母加数字
	private String drawCode;
	// 活动礼品状态（0，待领取，1，领取成功 2，领取失败 3，失效）
	private Integer status;
	private String statusStr;
	// 领取时间
	private Timestamp acquireDate;
	private String acquireDateStr;
	// 创建时间
	private Timestamp createDate;
	private String createDateStr;
	// 失效时间
	private Timestamp invalidDate;
	private String invaliDateStr;
	// 手机号码
	private String phoneNo;
	// 商品id
	private String goodsId;
	// 商品名称
	private String goodsName;
	// 商品价格(以分为单位)
	private Double price;
	// 备注
	private String remark;
	// 创建机器ID
	private BigInteger createMachineCode;
	// 提取机器ID
	private BigInteger getMachineCode;
	// 订单号
	private String orderNo;
	// 口味代码百位代表咖啡十位代表奶个位代表糖浓度0-5
	private String fastCode;

	// 开始时间
	private Date startTime;
	private String startTimeStr;
	// 结束时间
	private Date endTime;
	private String endTimeStr;

	@ExcelField(title = "活动编号", align = 2, sort = 1)
	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	@ExcelField(title = "取货编号", align = 2, sort = 2)
	public String getDrawCode() {
		return drawCode;
	}

	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title = "礼品状态", align = 2, sort = 3)
	public String getStatusStr() {
		if (status.intValue() == 0) {
			statusStr = "待领取";
		}
		if (status.intValue() == 1) {
			statusStr = "领取成功";
		}
		if (status.intValue() == 2) {
			statusStr = "领取失败";
		}
		if (status.intValue() == 3) {
			statusStr = "失效";
		}
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Date getAcquireDate() {
		return acquireDate;
	}

	public void setAcquireDate(Timestamp acquireDate) {
		this.acquireDate = acquireDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	@ExcelField(title = "领取时间", align = 2, sort = 4)
	public String getAcquireDateStr() {
		return acquireDateStr;
	}

	public void setAcquireDateStr(String acquireDateStr) {
		this.acquireDateStr = acquireDateStr;
	}

	@ExcelField(title = "创建时间", align = 2, sort = 5)
	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	@ExcelField(title = "有效时间", align = 2, sort = 6)
	public String getInvaliDateStr() {
		return invaliDateStr;
	}

	public void setInvaliDateStr(String invaliDateStr) {
		this.invaliDateStr = invaliDateStr;
	}

	@ExcelField(title = "手机号码", align = 2, sort = 7)
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@ExcelField(title = "商品ID", align = 2, sort = 8)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ExcelField(title = "商品名称", align = 2, sort = 9)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "商品价格", align = 2, sort = 10)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigInteger getCreateMachineCode() {
		return createMachineCode;
	}

	public void setCreateMachineCode(BigInteger createMachineCode) {
		this.createMachineCode = createMachineCode;
	}

	@ExcelField(title = "取货机器ID", align = 2, sort = 11)
	public BigInteger getGetMachineCode() {
		return getMachineCode;
	}

	public void setGetMachineCode(BigInteger getMachineCode) {
		this.getMachineCode = getMachineCode;
	}

	@ExcelField(title = "订单号", align = 2, sort = 13)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "口味代码", align = 2, sort = 14)
	public String getFastCode() {
		return fastCode;
	}

	public void setFastCode(String fastCode) {
		this.fastCode = fastCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

}
