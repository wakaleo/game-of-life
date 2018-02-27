package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 第三方交易查询Vo
 * 
 * @author yangbo
 * @date 2017年5月12日 下午2:27:37
 *
 */
public class VendCustInfoVo extends BaseDataEntity<VendCustInfoVo> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private BigDecimal vendCustInfoId;
	private String custId; //汇理财
	private String hlejId; //汇联易家
	private String openId;// 微信的openId
	public String phoneNo; // 手机号码
	private String vendCode;// 售货机编码
	private String province; // 省
	private String city; // 城市
	private  String areaId  ;    //区域
    private  String communityId  ;// 社区
    private Date startTime;// 开始查询时间开始时间
	private Date endTime;// 查询结束时间
	private Date registDate; // 注册时间
	private String channelId; // 渠道
	private String channelName;// 渠道名称
	private String communityName; // 社区名称
	private  Date   createTime ;    
	
	public BigDecimal getVendCustInfoId() {
		return vendCustInfoId;
	}
	public void setVendCustInfoId(BigDecimal vendCustInfoId) {
		this.vendCustInfoId = vendCustInfoId;
	}
	@ExcelField(title = "汇理财编号", align = 2, sort = 30)
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	@ExcelField(title = "汇联易家编号", align = 2, sort = 30)
	public String getHlejId() {
		return hlejId;
	}
	public void setHlejId(String hlejId) {
		this.hlejId = hlejId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@ExcelField(title = "电话号码", align = 2, sort = 30)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@ExcelField(title = "机器编号", align = 2, sort = 30)
	public String getVendCode() {
		return vendCode;
	}
	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	@ExcelField(title = "注册时间", align = 2, sort = 30)
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	

	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	@ExcelField(title = "渠道名称", align = 2, sort = 30)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelField(title = "社区名称", align = 2, sort = 30)
	public String getCommunityName() {
		return communityName;
	}
	
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
