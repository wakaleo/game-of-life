package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;
/**
* 上传广告vo
* @author luozb
* @date 2017年2月24日 下午17:31:14
*
*/
public class UploadAdVo  extends BaseDataEntity<UploadAdVo> implements Serializable {


	private static final long serialVersionUID = 1000L;

	private  BigDecimal channelId ;//     渠道	
	
	private  String schemeNo  ;// 活动方案编码	
		
	private String adPath;//广告路径
	
	private  Date   createTime ;//创建时间    
	
	private  Date   updateTime ;//更新时间    
	
	private  String channelName;//渠道名称
	private String schemeName;//活动名称
	private  Date startTime  ;// 查询开始时间
	private  Date endTime  ;// 	查询结束时间
	private Integer adStatus =1; //广告状态：1、进行中，2、已结束
	private String statusName;//状态名称
	
	private String idList;
	
	public BigDecimal getChannelId() {
		return channelId;
	}
	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getAdPath() {
		return adPath;
	}
	public void setAdPath(String adPath) {
		this.adPath = adPath;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 30)
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
	
	@ExcelField(title = "渠道", align = 2, sort = 10)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@ExcelField(title = "活动类型", align = 2, sort = 20)
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
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
	public Integer getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(Integer adStatus) {
		this.adStatus = adStatus;
	}
	
	@ExcelField(title = "广告状态", align = 2, sort = 40)
	public String getStatusName() {
		if ( adStatus.intValue()==2) {
			return "已关闭";
		} 
		return "进行中";
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	
}
