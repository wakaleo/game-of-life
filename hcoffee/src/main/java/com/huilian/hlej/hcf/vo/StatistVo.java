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
public class StatistVo  extends BaseDataEntity<StatistVo> implements Serializable {


	private static final long serialVersionUID = 1L;

	private  String   id ;//  售货机编码
	private  String   vendCode ;//  售货机编码
	private  String  channelName;
	private  String  imgNames;
	private  String  vendTotel;
	private  String  vendingTotel;
	private String communityName; // 社区名称
	private  Date createTime  ;
	private  Date updateTime  ;
	private  Date startTime  ;// 
	private  Date endTime  ;// 
	private  Date time  ;// 
	
	private String adId;
	private String adType;
	private  String imgName  ;//
	
	private String playmins;
	private  String playLong  ;//
	private String aDList;
	private String num;//编码顺序
	private String sort;//顺序
	private String playTime;//顺序
	private String lon;//播放时长求和
	private String min;//播放次数求和


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "售货机编码", align = 2, sort = 30)
	public String getVendCode() {
		return vendCode;
	}


	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}


	public String getAdId() {
		return adId;
	}


	public void setAdId(String adId) {
		this.adId = adId;
	}


	public String getImgName() {
		return imgName;
	}


	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@ExcelField(title = "渠道", align = 2, sort = 30)
	public String getChannelName() {
		return channelName;
	}


	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@ExcelField(title = "在线终端分布", align = 2, sort = 30)
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


	public String getImgNames() {
		return imgNames;
	}


	public void setImgNames(String imgNames) {
		this.imgNames = imgNames;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}

	@ExcelField(title = "播放次数", align = 2, sort = 30)
	public String getPlaymins() {
		return playmins;
	}


	public void setPlaymins(String playmins) {
		this.playmins = playmins;
	}

	@ExcelField(title = "播放时长", align = 2, sort = 30)
	public String getPlayLong() {
		return playLong;
	}


	public void setPlayLong(String playLong) {
		this.playLong = playLong;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getVendTotel() {
		return vendTotel;
	}


	public void setVendTotel(String vendTotel) {
		this.vendTotel = vendTotel;
	}


	public String getVendingTotel() {
		return vendingTotel;
	}


	public void setVendingTotel(String vendingTotel) {
		this.vendingTotel = vendingTotel;
	}


	public String getaDList() {
		return aDList;
	}


	public void setaDList(String aDList) {
		this.aDList = aDList;
	}


	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getPlayTime() {
		return playTime;
	}


	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}


	public String getAdType() {
		return adType;
	}


	public void setAdType(String adType) {
		this.adType = adType;
	}


	public String getLon() {
		return lon;
	}


	public void setLon(String lon) {
		this.lon = lon;
	}


	public String getMin() {
		return min;
	}


	public void setMin(String min) {
		this.min = min;
	}

	
}
