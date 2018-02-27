package com.huilian.hlej.hcf.entity;

 
import java.math.BigDecimal;
import java.util.Date;
 
/**
 * 售货机升级任务
 * 
 * @author liujian
 *
 */
 
public class AppUpgradeTask {
 
	private int id;
	
	private String vendId; // 'hsh_vend_qr_code 售货机id',
	
	private String vendCode; //   '售货机编码',
	
	private BigDecimal channel; // '售货机渠道',
	private String channelName;
	private String versionCurrent; //    '售货机汇联app当前版本',
	private String versionCurrentId; //    '售货机汇联app当前版本id',
	private String version; // '本次任务要升级售货机汇联app新版本',
	private String versionId; // '本次任务要升级售货机汇联app新版本id',
	private Date upgradeTime; //       '售货机汇联app升级时间',
	private Integer status;
	private String remark; //   '升级内容',
	
	
	private  Date   createTime ;    
	private  Date confirmTime;
	private String downloadLink;//升级链接

	private String batchVendCode;
	
	
	
	public String getBatchVendCode() {
		return batchVendCode;
	}

	public void setBatchVendCode(String batchVendCode) {
		this.batchVendCode = batchVendCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
