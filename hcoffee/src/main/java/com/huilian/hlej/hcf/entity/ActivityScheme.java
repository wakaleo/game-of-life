package com.huilian.hlej.hcf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
 

/**
 * 售货机活动方案
 * 
 * @author liujian
 * 
 */
 
public class ActivityScheme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private  String schemeNo;// 活动方案编码
	
	private  String schemeName;// 活动方案名称
	
	private  String channel; //参与活动得渠道，可多个英文逗号分隔
	
	private  String groupIds; // 售货机分组，可多个英文逗号分隔
	
	private  String goodsId; // 商品ID，可多个英文逗号分隔
	
	private  BigDecimal minPrice; //最小价格
	
	private  BigDecimal maxPrice; //最大价格
	private  String activityType; //最大价格
	
	private  Date createTime; //创建时间
	
	private  Date updateTime; //修改时间

	private  String remark; //备注
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	
}
