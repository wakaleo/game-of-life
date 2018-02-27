package com.huilian.hlej.hcf.vo;

 

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

 
/**
 * 渠道VO
 * @author yangbo
 * @date 2017年3月1日 上午11:56:37
 *
 */
 
public class ChannelVo extends BaseDataEntity<ChannelVo> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal channelId;

	private String channelName; // 渠道商名称

	private String mark; // 标识

	private Integer isShowOnlyThis; // 是否只显示本渠道商品

	private String remark; // 备注
	
	private String create_by;
	
	private Date createTime;//更新时间
	private BigInteger provinceId; // 省份ID

	private String provinceName; // 省份名称

	private String cityId; // 城市ID

	private String cityName; // 城市名称

	private String areaId; // 区域ID

	private String areaName; // 区域名称
	
	private String statusName;//状态名称

	private  Date startTime  ;// 查询开始时间
	
	private  Date endTime  ;// 	查询结束时间

	private int dataSource;//数据来源:1,汇有房,2汇生活
	
	private int dataStatus;//数据状态:0:正常,1:删除'
	
	private String idList;
	private String statusList;
	
	@ExcelField(title = "渠道编码", align = 2, sort = 20)
	public BigDecimal getChannelId() {
		return channelId;
	}

	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}
	
	@ExcelField(title = "渠道名称", align = 2, sort = 20)
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@ExcelField(title = "渠道标识", align = 2, sort = 20)
	public String getMark() {
		return mark;
	}
	

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getIsShowOnlyThis() {
		return isShowOnlyThis;
	}

	public void setIsShowOnlyThis(Integer isShowOnlyThis) {
		this.isShowOnlyThis = isShowOnlyThis;
	}

	@ExcelField(title = "渠道备注", align = 2, sort = 20)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	
	@ExcelField(title = "创建时间", align = 2, sort = 30)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	@ExcelField(title = "渠道状态", align = 2, sort = 40)
	public String getStatusName() {
		if ( dataStatus==1) {
			return "关闭";
		} 
		return "开启";
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusList() {
		return statusList;
	}

	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}
	
	
	
}
