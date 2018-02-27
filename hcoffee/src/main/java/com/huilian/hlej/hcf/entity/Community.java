package com.huilian.hlej.hcf.entity;

 

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

 
/**
 * 社区实体
 * 
 *  
 * Modifier  luozb
 */
 
public class Community extends BaseDataEntity<Community> implements Serializable {
	private static final long serialVersionUID = 1L;
 
	
	private String communityId; // 社区ID
	
	private String communityName; // 社区名称
	
	private  Date   createTime ; //创建时间   
	
	private String createBy;//创建人
	
	private  Date   updateTime ;//更新时间    
	
	private String updateBy;//更新人
	
	private int dataSource;//数据来源:1,汇有房,2汇生活
	
	private int dataStatus;//数据状态:0:正常,1:删除

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}
	
}
