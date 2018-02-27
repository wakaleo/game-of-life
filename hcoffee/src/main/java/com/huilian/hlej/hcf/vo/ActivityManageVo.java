package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 活动管理Vo
 * 
 * @author xiekangjian
 * @date 2017年1月12日 下午2:27:37
 *
 */
public class ActivityManageVo extends BaseDataEntity<ActivityManageVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String schemeNo;// 活动编码
	private String usrChannel;// 渠道编码
	private String schemeName;// 活动名称
	private String status;// 活动状态，0：进行中，1：已结束
	private Date createTime;// 活动创建时间
	private String channel;// 渠道号
	private String channelName;// 渠道号

	private Date startTime;// 开始查询时间开始时间
	private Date endTime;// 查询结束时间

	private BigDecimal minPrice ; // 最小价格
	private BigDecimal maxPrice ; // 活动礼物最大价格

	private String schemeNoList;

	private String channelList;
	private String url;
	private String activityType;
	private String maxImg;
	private String minImg;
	private String activityId;
	private String templateId;
	private String oldTemplateId;
	private String templateName;
	private String remark;
	private String vendCode;
	private String OptionType;
	private String fabuRecort; //记录发布记录的
	List<String> list;

	
	
	
	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
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

	@ExcelField(title = "活动编码", align = 2, sort = 10)
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

	@ExcelField(title = "活动方案名称", align = 2, sort = 10)
	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		if ("1".equals(this.status)) {
			return "已结束";
		}
		return "进行中";
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

	@ExcelField(title = "渠道编码", align = 2, sort = 10)
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

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getMaxImg() {
		return maxImg;
	}

	public void setMaxImg(String maxImg) {
		this.maxImg = maxImg;
	}

	public String getMinImg() {
		return minImg;
	}

	public void setMinImg(String minImg) {
		this.minImg = minImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getRemark() {
		return remark;
	}

	public String getFabuRecort() {
		return fabuRecort;
	}

	public void setFabuRecort(String fabuRecort) {
		this.fabuRecort = fabuRecort;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOptionType() {
		return OptionType;
	}

	public void setOptionType(String optionType) {
		OptionType = optionType;
	}

	public String getOldTemplateId() {
		return oldTemplateId;
	}

	public void setOldTemplateId(String oldTemplateId) {
		this.oldTemplateId = oldTemplateId;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	

}
