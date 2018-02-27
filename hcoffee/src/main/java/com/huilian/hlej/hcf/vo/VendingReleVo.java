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
public class VendingReleVo  extends BaseDataEntity<VendingReleVo> implements Serializable {


	private static final long serialVersionUID = 1L;

	private  String   vendCode ;
	
	
	private  String  aDList ;
	private  String  adStatus; 
	private  String  channel; 
	private  String  channelName; 
	private  Date createTime  ;
	private  Date updateTime  ;
	private  Date startTime  ;// 
	private  Date endTime  ;// 
	private  String num  ;//  
	private  Integer sort  ;// 排序字段
	private String communityId; // 社区ID

	private String communityName; // 社区名称
	private String imgPath;
	private String vedioPath; 
	private String imgName; 
	private String adType; 
	private String playTime; 
	
	public String getVendCode() {
		return vendCode;
	}
	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	public String getaDList() {
		return aDList;
	}
	public void setaDList(String aDList) {
		this.aDList = aDList;
	}
	public String getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getVedioPath() {
		return vedioPath;
	}
	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	
	
}
