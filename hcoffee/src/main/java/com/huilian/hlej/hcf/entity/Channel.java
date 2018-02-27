package com.huilian.hlej.hcf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 渠道商
 * 
 * @author Administrator
 *
 */
public class Channel implements Serializable {
	/**
	 * 汇生活渠道
	 */
	public static final BigDecimal CHANNEL_HSH = new BigDecimal(16993204);
	
	/**
	 * 汇生活售货机
	 */
	public static final BigDecimal VEND_HSH = new BigDecimal(16993202);

	/**
	 * 彩生活渠道
	 */
	public static final BigDecimal CHANNEL_CSH = new BigDecimal(196225731);

	/**
	 * 雅居乐渠道
	 */
	public static final BigDecimal CHANNEL_YJL = new BigDecimal(100000000);
	/**
	 * 百斯特游轮渠道
	 */
	public static final String CHANNEL_BST = "1602559840455";
	/**
	 * 恒腾密蜜 渠道
	 */
	public static final BigDecimal CHANNEL_HTMM = new BigDecimal("21064055694");
	/**
	 * 深圳外部用户渠道
	 */
	public static final BigDecimal CHANNEL_SZ_OUTER_USER = new BigDecimal(123456789);

	private static final long serialVersionUID = 1L;

	private BigDecimal channelId;

	private BigDecimal channelRefId;

	private String qrUrl; // 生成二维码地址

	private String channelName; // 渠道商名称

	private String mark; // 标识

	private Integer isShowOnlyThis; // 是否只显示本渠道商品

	private String remark; // 备注

	private BigInteger provinceId; // 省份ID

	private String provinceName; // 省份名称

	private String cityId; // 城市ID

	private String cityName; // 城市名称

	private String areaId; // 区域ID

	private String areaName; // 区域名称

	private String communityId; // 社区ID

	private String communityName; // 社区名称

	private Date createTime;

	private Date updateTime;

	private int dataSource;

	private int dataStatus; // 数据状态 0:正常，1:删除

	private String createBy;

	private String updateBy;

	public BigDecimal getChannelId() {
		return channelId;
	}

	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(BigDecimal channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigInteger getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(BigInteger provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getIsShowOnlyThis() {
		return isShowOnlyThis;
	}

	public void setIsShowOnlyThis(Integer isShowOnlyThis) {
		this.isShowOnlyThis = isShowOnlyThis;
	}
}