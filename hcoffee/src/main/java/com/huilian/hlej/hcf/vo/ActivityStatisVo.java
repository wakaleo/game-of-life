package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 活动统计Vo
 * @author xiekangjian
 * @date 2017年1月12日 下午2:27:37
 *
 */
public class ActivityStatisVo extends BaseDataEntity<ActivityStatisVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String schemeNo;//活动编码
	private String usrChannel;//渠道编码
	private String schemeName;//活动名称
	private String channelName;//渠道名称
	private String status;//活动状态，0：进行中，1：已结束
	private String rewardCnt;//每个活动的不同渠道送出去的礼物数量
	private Date createTime;//活动创建时间
	private String channel;//渠道号
	
	private  Date startTime  ;// 开始查询时间开始时间
	private  Date endTime  ;// 查询结束时间
	
	private String statusName;
	
	private String schemeNoList;
	
	private String channelList;
	
	
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
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getUsrChannel() {
		return usrChannel;
	}
	public void setUsrChannel(String usrChannel) {
		this.usrChannel = usrChannel;
	}
	
	@ExcelField(title = "活动名称", align = 2, sort = 10)
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	@ExcelField(title = "渠道", align = 2, sort = 20)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@ExcelField(title = "活动状态", align = 2, sort = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title = "礼品数量", align = 2, sort = 30)
	public String getRewardCnt() {
		return rewardCnt;
	}
	public void setRewardCnt(String rewardCnt) {
		this.rewardCnt = rewardCnt;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 40)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title = "活动状态", align = 2, sort = 50)
	public String getStatusName() {
		if("1".equals(this.status)){
			return "已结束";
		}
		return "进行中";
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSchemeNoList() {
		return schemeNoList;
	}
	public void setSchemeNoList(String schemeNoList) {
		this.schemeNoList = schemeNoList;
	}
	public String getChannelList() {
		return channelList;
	}
	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
