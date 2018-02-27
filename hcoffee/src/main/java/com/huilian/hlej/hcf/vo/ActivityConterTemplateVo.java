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
/**
 * @author LL
 *
 */
public class ActivityConterTemplateVo extends BaseDataEntity<ActivityConterTemplateVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String templateId;
	private String schemeName;
	private String schemeNo;
	private String templateName;
	private Date createTime;
	private String channelList;
	private String remark;
	private String OldTemplateId;
	private String vendCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getChannelList() {
		return channelList;
	}
	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String getOldTemplateId() {
		return OldTemplateId;
	}
	public void setOldTemplateId(String oldTemplateId) {
		OldTemplateId = oldTemplateId;
	}
	public String getVendCode() {
		return vendCode;
	}
	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	
}
