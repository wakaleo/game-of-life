package com.huilian.hlej.hcf.vo;

 

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

 
/**
 * 社区Vo
 * @author luozb
 * @date 2017年2月27日 上午11:56:37
 *
 */
 
public class CommunityVo extends BaseDataEntity<CommunityVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	private String communityId; // 社区ID
	
	private String communityName; // 社区名称
	
	private  Date   createTime ; //创建时间   
	
	private String createBy;//创建人
	
	private  Date   updateTime ;//更新时间    
	
	private String updateBy;//更新人
	
	private int dataSource;//数据来源:1,汇有房,2汇生活
	
	private int dataStatus;//数据状态:0:正常,1:删除'
	
	private String statusName;//状态名称
	
	private  Date startTime  ;// 查询开始时间
	
	private  Date endTime  ;// 	查询结束时间
	
	private String idList;

	@ExcelField(title = "社区编码", align = 2, sort = 20)
	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
	@ExcelField(title = "社区名称", align = 1, sort = 10 )
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
	@ExcelField(title = "创建时间", align = 2, sort = 30)
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
	
	@ExcelField(title = "社区状态", align = 2, sort = 40)
	public String getStatusName() {
		if ( dataStatus==1) {
			return "删除";
		} 
		return "正常";
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
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

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
}
