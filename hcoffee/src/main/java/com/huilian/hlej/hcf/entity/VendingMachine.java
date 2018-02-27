package com.huilian.hlej.hcf.entity;

 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

 
/**
 * 售货机实体
 * 
 *  
 * Modifier  liujian
 */
 
public class VendingMachine extends BaseDataEntity<VendingMachine> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	private  String   vendCode ;//  售货机编码
	
	private  BigDecimal channel   ;//     渠道
	
	private  String  appVersion ;//      售货机汇联app版本
	private  String  appVersionId; //售货机汇联app版本id
	private  Date upgradeTime  ;//      售货机汇联app最新升级时间
	
	private String  upgradeRemark ;//     最新升级内容
	
	private  Integer status ;          //  机器状态0 使用中和1停止使用
		
	private  Date   createTime ;    
	private  Date   timer ;    
	
	private  Date   updateTime ;    
	
	private  String  groupId;//售货机分组id
	
	private String provinceId; // 省份ID

	private String provinceName; // 省份名称

	private String cityId; // 城市ID

	private String cityName; // 城市名称

	private String areaId; // 区域ID

	private String areaName; // 区域名称

	private String communityId; // 社区ID

	private String communityName; // 社区名称
	
	private  String location  ;//售货机具体位置
	
	private  String channelName;
	private  String deliveryId  ;
	
	private  String equipId;
	
	private  String lng  ;
	
	private  String lat;
	
	
	
	private String deliveryType; //投放类型
	
	private String equipSupply;//设备供应方
	private String huiLink;//linjie
	
	private String vendName;// 售货机名称
	
	private Integer addFlag;// 添加售货的方式 1表示正常添加;2表示快速添加
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	 
	public Date getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(Date upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	public BigDecimal getChannel() {
		return channel;
	}

	public void setChannel(BigDecimal channel) {
		this.channel = channel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	

	public String getUpgradeRemark() {
		return upgradeRemark;
	}

	public void setUpgradeRemark(String upgradeRemark) {
		this.upgradeRemark = upgradeRemark;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(String appVersionId) {
		this.appVersionId = appVersionId;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getEquipSupply() {
		return equipSupply;
	}

	public void setEquipSupply(String equipSupply) {
		this.equipSupply = equipSupply;
	}

	public String getHuiLink() {
		return huiLink;
	}

	public void setHuiLink(String huiLink) {
		this.huiLink = huiLink;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public Date getTimer() {
		return timer;
	}

	public void setTimer(Date timer) {
		this.timer = timer;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getVendName() {
		return vendName;
	}

	public void setVendName(String vendName) {
		this.vendName = vendName;
	}
	
	public Integer getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(Integer addFlag) {
		this.addFlag = addFlag;
	}
}
