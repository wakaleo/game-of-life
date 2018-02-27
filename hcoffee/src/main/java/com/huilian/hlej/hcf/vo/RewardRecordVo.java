package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 礼物记录
 * @author xiekangjian
 * @date 2017年1月16日 下午3:29:17
 *
 */
public class RewardRecordVo extends BaseDataEntity<RewardRecordVo> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsId; //商品编号
	private String goodsName;//商品名称
	private String vendcode;//机器编码
	private String communityName;//机器所在社区
	private Date createDate;//创建时间
	private Date acquireDate;//取货时间
	private Integer status; //活动品礼状态0，待领取，1，领取成功 2，领取失败 3，失效
	private String drawCode;//取货码
	private String phoneNo;//取货码
	private String schemeNo;//活动编码
	private String usrChannel;//渠道编码
	private String anctiviType;//渠道编码
	private String channelName;//渠道编码
	private BigDecimal channel;//渠道编码
	private BigDecimal price;  
	private String getVenderId;//出货机器编码
	private  Date startTime  ;// 开始查询时间开始时间
	private  Date endTime  ;// 查询结束时间
	
	private String statusName;
	
	private String idList;
	
	
	@ExcelField(title = "取货机器编码", align = 2, sort = 100)
	public String getGetVenderId() {
		return getVenderId;
	}
	public void setGetVenderId(String getVenderId) {
		this.getVenderId = getVenderId;
	}
	@ExcelField(title = "手机号码", align = 2, sort = 30)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@ExcelField(title = "取货码", align = 2, sort = 40)
	public String getDrawCode() {
		return drawCode;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 90)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title = "礼品编码", align = 2, sort = 50)
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@ExcelField(title = "礼品名称", align = 2, sort = 60)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title = "礼品金额", align = 2, sort = 61)
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title = "机器编码", align = 2, sort = 20)
	public String getVendcode() {
		return vendcode;
	}
	public void setVendcode(String vendcode) {
		this.vendcode = vendcode;
	}
	
	@ExcelField(title = "机器所在社区", align = 2, sort = 11)
	public String getCommunityName() {
		return communityName;
	}
	
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
	@ExcelField(title = "出货时间", align = 2, sort = 70)
	public Date getAcquireDate() {
		return acquireDate;
	}
	public void setAcquireDate(Date acquireDate) {
		this.acquireDate = acquireDate;
	}
	
	@ExcelField(title = "取货码状态", align = 2, sort = 80)
	public String getStatusName() {
		if(this.status.intValue()==0){
			return "待领取";
		}else if(this.status.intValue()==1){
			return "领取成功";
		}else if(this.status.intValue()==2){
			return "领取失败";
		}else if(this.status.intValue()==3){
			return "取货码失效";
		}
		
		return "";
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
	
	@ExcelField(title = "活动类型", align = 2, sort = 12)
	public String getAnctiviType() {
		return anctiviType;
	}
	public void setAnctiviType(String anctiviType) {
		this.anctiviType = anctiviType;
	}
	
	@ExcelField(title = "渠道", align = 2, sort = 13)
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public BigDecimal getChannel() {
		return channel;
	}
	public void setChannel(BigDecimal channel) {
		this.channel = channel;
	}
	
	
	
}
