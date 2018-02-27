package com.huilian.hlej.hcf.entity;

 
import java.io.Serializable;
import java.util.Date;
 

/**
 * 售货机app版本记录
 * 
 * @author liujian
 * 
 */
 
public class AppVersionRecords implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private Integer id;
	
	private String version    ;//  app版本
	
	private String  versionOld  ;// 较上一版本本次app升级内容
	
   private  String versionOldId;// 较上一版本本次app升级内容
	
	private int   status  ;//    使用状态0 使用中和1停用
	
	private Date  createTime ; 
	
	private String  downloadLink;  //下载链接 
	
	private String description;
	private String appVersionId;
	private String appVersion;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionOld() {
		return versionOld;
	}

	public void setVersionOld(String versionOld) {
		this.versionOld = versionOld;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	/*public String getVersionOldId() {
		return versionOldId;
	}

	public void setVersionOldId(String versionOldId) {
		this.versionOldId = versionOldId;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersionOldId() {
		return versionOldId;
	}

	public void setVersionOldId(String versionOldId) {
		this.versionOldId = versionOldId;
	}

	public String getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(String appVersionId) {
		this.appVersionId = appVersionId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	
	
}
