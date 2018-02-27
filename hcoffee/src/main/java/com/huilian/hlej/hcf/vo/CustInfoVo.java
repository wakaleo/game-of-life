package com.huilian.hlej.hcf.vo;

 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

 
/**
 * 用户统计
 * 
 *  
 * Modifier  
 */
 
public class CustInfoVo extends BaseDataEntity<CustInfoVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	private  String   vendCode ;//  售货机编码
	
	private  BigDecimal channel   ;//     渠道
	
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
	

	
	private  Date startTime  ;// 
	private  Date endTime  ;// 
	
	
	private String vendCodeList;
	
	private Integer toltol;
	
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@ExcelField(title = "创建时间", align = 2, sort = 70)
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
	@ExcelField(title = "社区名称", align = 2, sort = 70)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}


	public String getVendCodeList() {
		return vendCodeList;
	}

	public void setVendCodeList(String vendCodeList) {
		this.vendCodeList = vendCodeList;
	}
	@ExcelField(title = "记录数", align = 0, sort = 0)
	public Integer getToltol() {
		return toltol;
	}

	public void setToltol(Integer toltol) {
		this.toltol = toltol;
	}
	

	
	
}
