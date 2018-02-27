package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 商机管理bean
 * 
 * @author yangweichao
 * @version 2017-8-24 14:31:27
 */
public class LeadManagementVo extends BaseDataEntity<LeadManagementVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 商机用户名 */
	private String lMName;

	/** 电话 */
	private String lMPhone;

	/** 地址 */
	private String lMAdd;

	/** 申请时间 */
	private Date applicationTime;

	/** 创建时间 */
	private Date creationTime;

	// 更新时间
	private Date updateTime;

	/** 跟进状态 */
	private Integer state;

	/** 存放集合 */
	private String idList;

	// 搜索内容
	private String searchText;

	// 备注
	private String remark;

	// -------操作历史记录字段--start--对应hcf_lead_history表----------//

	// 操作时间
	private Timestamp operationTime;
	private String operationTimeStr;

	// 操作人
	private String operationPerson;
	// 操作动作
	private String operationAction;
	// 商机关联id
	private Integer leadId;

	// 历史记录list
	private List<LeadManagementVo> list;

	// -------操作历史记录字段--end------------//

	public Timestamp getOperationTime() {
		return operationTime;
	}

	public List<LeadManagementVo> getList() {
		return list;
	}

	public void setList(List<LeadManagementVo> list) {
		this.list = list;
	}

	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationPerson() {
		return operationPerson;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	public String getOperationAction() {
		return operationAction;
	}

	public void setOperationAction(String operationAction) {
		this.operationAction = operationAction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getlMName() {
		return lMName;
	}

	public void setlMName(String lMName) {
		this.lMName = lMName;
	}

	public String getlMPhone() {
		return lMPhone;
	}

	public void setlMPhone(String lMPhone) {
		this.lMPhone = lMPhone;
	}

	public String getlMAdd() {
		return lMAdd;
	}

	public void setlMAdd(String lMAdd) {
		this.lMAdd = lMAdd;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getLeadId() {
		return leadId;
	}

	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}

	public String getOperationTimeStr() {
		return operationTimeStr;
	}

	public void setOperationTimeStr(String operationTimeStr) {
		this.operationTimeStr = operationTimeStr;
	}

}
