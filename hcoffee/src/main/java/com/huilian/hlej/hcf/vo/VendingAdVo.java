package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;
/**
 * 售货机广告实体
 * @author xiekangjian
 * @date 2017年1月24日 下午4:01:42
 *
 */
public class VendingAdVo  extends BaseDataEntity<VendingAdVo> implements Serializable {


	private static final long serialVersionUID = 1L;

	private  String   vendCode ;//  售货机编码
	
	private  BigDecimal channel   ;//     渠道
	
	private  String  appVersion ;//      售货机汇联app版本
	private  String  appVersionId; //售货机汇联app版本id
	private  Date upgradeTime  ;//      售货机汇联app最新升级时间
	
	private String  upgradeRemark ;//     最新升级内容
	
	private  Integer status ;          //  机器状态0 使用中和1停止使用
		
	private  Date   createTime ;    
	
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
	
	private  String location  ;//售货机位置，精确到那栋楼
	
	private  Date startTime  ;// 
	private  Date endTime  ;// 
	private  String channelName;
	
	private String batchVendCode; //批量更新的售货机编码
	
	private String vendCodeList;
	
	
	private String imgPath1;
	private String imgPath2;
	private String imgPath3;
	private String imgLink1;
	private String imgLink2;
	private String imgLink3;
	private String vedioPath;
	private Integer adType;  //广告类型：1、图片，2、视频
	private Integer adStatus =1; //广告状态：1、进行中，2、已结束
	
	private String typeName;
	
	private String statusName;
	
	
	
	
	public String getBatchVendCode() {
		return batchVendCode;
	}
	public void setBatchVendCode(String batchVendCode) {
		this.batchVendCode = batchVendCode;
	}
	
	@ExcelField(title = "售货机编码", align = 2, sort = 30)
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
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppVersionId() {
		return appVersionId;
	}
	public void setAppVersionId(String appVersionId) {
		this.appVersionId = appVersionId;
	}
	public Date getUpgradeTime() {
		return upgradeTime;
	}
	public void setUpgradeTime(Date upgradeTime) {
		this.upgradeTime = upgradeTime;
	}
	public String getUpgradeRemark() {
		return upgradeRemark;
	}
	public void setUpgradeRemark(String upgradeRemark) {
		this.upgradeRemark = upgradeRemark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 50)
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
	
	@ExcelField(title = "机器所在社区", align = 1, sort = 20)
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	
	@ExcelField(title = "渠道", align = 2, sort = 10)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getImgPath1() {
		return imgPath1;
	}
	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}
	public String getImgPath2() {
		return imgPath2;
	}
	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}
	public String getImgPath3() {
		return imgPath3;
	}
	public void setImgPath3(String imgPath3) {
		this.imgPath3 = imgPath3;
	}
	public String getVedioPath() {
		return vedioPath;
	}
	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}
	public Integer getAdType() {
		return adType;
	}
	public void setAdType(Integer adType) {
		this.adType = adType;
	}
	public Integer getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(Integer adStatus) {
		this.adStatus = adStatus;
	}
	
	@ExcelField(title = "广告类型", align = 2, sort = 40)
	public String getTypeName() {
		if(this.adType.intValue()==2){
			return "视频";
		}
		return "图片";
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@ExcelField(title = "广告状态", align = 2, sort = 60)
	public String getStatusName() {
		if(this.getAdStatus().intValue()==2){
			return "已关闭";
		}
		return "进行中";
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getVendCodeList() {
		return vendCodeList;
	}
	public void setVendCodeList(String vendCodeList) {
		this.vendCodeList = vendCodeList;
	}
	public String getImgLink1() {
		return imgLink1;
	}
	public void setImgLink1(String imgLink1) {
		this.imgLink1 = imgLink1;
	}
	public String getImgLink2() {
		return imgLink2;
	}
	public void setImgLink2(String imgLink2) {
		this.imgLink2 = imgLink2;
	}
	public String getImgLink3() {
		return imgLink3;
	}
	public void setImgLink3(String imgLink3) {
		this.imgLink3 = imgLink3;
	}
	
}
