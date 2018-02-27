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
public class StatistTotelVo  extends BaseDataEntity<StatistTotelVo> implements Serializable {


	private static final long serialVersionUID = 1L;

	private  String   id ;//  售货机编码
	private  String   vendCode ;
	private  String  adId ;
	private  String playmins  ;
	private  String imgName  ;
	private  String playLong  ;
	private  String CommunityName  ;
	private  String channelName  ;
	private Date addTime;
	private Date updateTime;
	private  String  vendTotel;
	private  String  vendingTotel;
	private  Date startTime  ;// 
	private  Date endTime  ;// 
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
	
	@ExcelField(title = "跟新时间", align = 2, sort = 30)
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/*@ExcelField(title = "最后一次跟新时间", align = 2, sort = 30)*/
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title = "下发终端总数", align = 2, sort = 30)
	public String getVendTotel() {
		return vendTotel;
	}
	public void setVendTotel(String vendTotel) {
		this.vendTotel = vendTotel;
	}
	
	@ExcelField(title = "下发终端在线总数", align = 2, sort = 30)
	public String getVendingTotel() {
		return vendingTotel;
	}
	public void setVendingTotel(String vendingTotel) {
		this.vendingTotel = vendingTotel;
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
	
	@ExcelField(title = "在线终端分布", align = 2, sort = 30)
	public String getCommunityName() {
		return CommunityName;
	}
	public void setCommunityName(String communityName) {
		CommunityName = communityName;
	}
	@ExcelField(title = "渠道", align = 2, sort = 30)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelField(title = "广告名称", align = 2, sort = 30)
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	
}
