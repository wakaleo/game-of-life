package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 售货机版本实体
 * @author xiekangjian
 * @date 2016年12月30日 下午3:03:42
 *
 */
public class AppVersionRecordsVo extends BaseDataEntity<AppVersionRecordsVo> implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String version;//app版本
	private String versionOld;//上一版本号
	private String versionOldId;//hsh_appversion ,id.上一版本号id
	private String description;//本次app升级内容
	private Integer status=0;//默认使用状态0 使用中和1作废
	private Date createTime;//创建时间
	private String downloadLink;//下载连接
	
	private  Date startTime  ;// 开始查询时间
	private  Date endTime  ;//  结束查询时间
	private  String filePath;
	
	private String idList;
	public Date getStartTime() {
		return startTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	@ExcelField(title = "版本名称", align = 2, sort = 10)
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
	public String getVersionOldId() {
		return versionOldId;
	}
	public void setVersionOldId(String versionOldId) {
		this.versionOldId = versionOldId;
	}
	
	@ExcelField(title = "版本内容", align = 1, sort = 20)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 30)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title = "下载地址", align = 1, sort = 40)
	public String getDownloadLink() {
		return downloadLink;
	}
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	public String getIdList() {
		return idList;
	}
	public void setIdList(String idList) {
		this.idList = idList;
	}
	
}
