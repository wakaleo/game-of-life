package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 售货机实体
 * 
 * 
 * Modifier liujian
 */

public class VendingMachineVo extends BaseDataEntity<VendingMachineVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String vendCode;// 售货机编码
	private String vendName;
	private BigDecimal channel;// 渠道

	private String appVersion;// 售货机汇联app版本
	private String appVersionId; // 售货机汇联app版本id
	private Date upgradeTime;// 售货机汇联app最新升级时间

	private String upgradeRemark;// 最新升级内容

	private Integer status; // 机器状态0 使用中和1停止使用

	private Date createTime;

	private Date updateTime;

	private String groupId;// 售货机分组id

	private String provinceId; // 省份ID

	private String provinceName; // 省份名称

	private String cityId; // 城市ID

	private String cityName; // 城市名称

	private String areaId; // 区域ID

	private String areaName; // 区域名称

	private String communityId; // 社区ID

	private String communityName; // 社区名称

	private String location;// 售货机位置，精确到那栋楼

	private Date startTime;//
	private Date endTime;//
	private String channelName;

	private String statusName;
	private String fabuRecort;

	private String vendCodeLocation;

	private String vendCodeList;

	private String deliveryType; // 投放类型

	private String equipSupply;// 设备供应方
	private String huiLink;// linjie
	private String deliveryId;// deliveryId
	private String version;// deliveryId
	private String versionId;// deliveryId
	private String ctiryName;// 区域名称
	private String templateId;// 模板名称
	private String oldTemplateId;// 模板名称
	private String optionType;// 模板名称
	private String templateName;// 模板名称
	private String oldTemplateName;// 模板名称
	private String romkRecot;// 撤销记录
	private String aDList;// 广告id记录

	// 二维码
	// 二维码模板编码
	private String wechatModelNo;
	// 二维码模板操作 1-更新 2-清空
	private String wechatOptionType;
	// 二维码模板操作（更新、清空）时间
	private Date wechatOptionTime;
	// 原二维码模板编码
	private String oldWechatModelNo;
	// 展示的所有机器的编码
	private String[] arr_vendCode = new String[15];
	// 选中的所有机器的编码
	private String[] arr_checked_vendCode = new String[15];
	// 二维码模板名称
	private String modelName;
	// 原二维码模板名称
	private String oldModelName;

	private String langLaction;// 坐标位置

	private Integer addFlag;// 添加售货的方式 1表示正常添加;2表示快速添加

	@ExcelField(title = "坐标位置", align = 2, sort = 70)
	public String getLangLaction() {
		return langLaction;
	}

	public void setLangLaction(String langLaction) {
		this.langLaction = langLaction;
	}

	private int toltol;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title = "渠道", align = 2, sort = 10)
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@ExcelField(title = "售货机编码", align = 2, sort = 20)
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

	@ExcelField(title = "详细地址", align = 2, sort = 30)
	public String getVendCodeLocation() {
		return this.communityName + this.location;
	}

	public void setVendCodeLocation(String vendCodeLocation) {
		this.vendCodeLocation = vendCodeLocation;
	}

	@ExcelField(title = "当前版本", align = 2, sort = 40)
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	// 归属渠道 售货机编码 区域 归属商圈 点位属性 详细地址 当前版本 启用状态
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@ExcelField(title = "归属渠道", align = 2, sort = 70)
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

	@ExcelField(title = "机器状态", align = 2, sort = 70)
	public String getStatusName() {
		if (this.status.intValue() == 1) {
			return "停止使用";
		}
		return "使用中";
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

	public int getToltol() {
		return toltol;
	}

	public void setToltol(int toltol) {
		this.toltol = toltol;
	}

	@ExcelField(title = "投放类型", align = 2, sort = 70)
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@ExcelField(title = "区域", align = 2, sort = 70)
	public String getCtiryName() {

		if (!cityName.isEmpty() && !areaName.isEmpty()) {

			return cityName + areaName;
		}
		return "";
	}

	public void setCtiryName(String ctiryName) {
		this.ctiryName = ctiryName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getOldTemplateId() {
		return oldTemplateId;
	}

	public void setOldTemplateId(String oldTemplateId) {
		this.oldTemplateId = oldTemplateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getOldTemplateName() {
		return oldTemplateName;
	}

	public void setOldTemplateName(String oldTemplateName) {
		this.oldTemplateName = oldTemplateName;
	}

	public String getFabuRecort() {
		return fabuRecort;
	}

	public void setFabuRecort(String fabuRecort) {
		this.fabuRecort = fabuRecort;
	}

	public String getRomkRecot() {
		return romkRecot;
	}

	public void setRomkRecot(String romkRecot) {
		this.romkRecot = romkRecot;
	}

	public String getaDList() {
		return aDList;
	}

	public void setaDList(String aDList) {
		this.aDList = aDList;
	}

	public String getWechatModelNo() {
		return wechatModelNo;
	}

	public void setWechatModelNo(String wechatModelNo) {
		this.wechatModelNo = wechatModelNo;
	}

	public String getWechatOptionType() {
		return wechatOptionType;
	}

	public void setWechatOptionType(String wechatOptionType) {
		this.wechatOptionType = wechatOptionType;
	}

	public String getOldWechatModelNo() {
		return oldWechatModelNo;
	}

	public void setOldWechatModelNo(String oldWechatModelNo) {
		this.oldWechatModelNo = oldWechatModelNo;
	}

	public String[] getArr_vendCode() {
		return arr_vendCode;
	}

	public String[] getArr_checked_vendCode() {
		return arr_checked_vendCode;
	}

	public void setArr_checked_vendCode(String[] arr_checked_vendCode) {
		this.arr_checked_vendCode = arr_checked_vendCode;
	}

	public void setArr_vendCode(String[] arr_vendCode) {
		this.arr_vendCode = arr_vendCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getOldModelName() {
		return oldModelName;
	}

	public void setOldModelName(String oldModelName) {
		this.oldModelName = oldModelName;
	}

	public Date getWechatOptionTime() {
		return wechatOptionTime;
	}

	public void setWechatOptionTime(Date wechatOptionTime) {
		this.wechatOptionTime = wechatOptionTime;
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
