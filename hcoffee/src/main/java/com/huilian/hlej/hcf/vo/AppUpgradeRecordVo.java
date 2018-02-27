package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
/**
 * 售货机升级记录
 * @author Administrator
 *
 */
public class AppUpgradeRecordVo  extends BaseDataEntity<AppUpgradeRecordVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String vendId; // 'hsh_vend_qr_code 售货机id',
	
	private String vendCode; //   '售货机编码',
	
	private BigDecimal channel; // '售货机渠道',
	private String channelName;
	private String versionCurrent; //    '售货机汇联app当前版本',
	private String versionCurrentId; //    '售货机汇联app当前版本id',
	private String version; // '本次任务要升级售货机汇联app新版本',
	private String versionId; // '本次任务要升级售货机汇联app新版本id',
	private Date upgradeTime; //       '售货机汇联app升级时间',
	
	private String remark; //   '升级内容',
	
	private Integer status; // 使用状态 升级状态1 待升级和，2已升级，3升级失败
	
	private  Date   createTime ;    
	private  Date confirmTime;
	private String downloadLink;//升级链接

	private String location;//售货机位置
	
	
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getVendId() {
		return vendId;
	}

	public void setVendId(String vendId) {
		this.vendId = vendId;
	}

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
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public Date getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(Date upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

 
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVersionCurrent() {
		return versionCurrent;
	}

	public void setVersionCurrent(String versionCurrent) {
		this.versionCurrent = versionCurrent;
	}

	public String getVersionCurrentId() {
		return versionCurrentId;
	}

	public void setVersionCurrentId(String versionCurrentId) {
		this.versionCurrentId = versionCurrentId;
	}
 

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	
}
