package com.huilian.hlej.hcf.entity;



import java.io.Serializable;
import java.util.Date;
 
/**
 * 售货机分组
 * 
 * @author liujian
 * 
 */
 
public class VendGroup implements Serializable{

	private static final long serialVersionUID = 1L;

 
	private Integer id;
	
	private  String   groupName ;// 名称
	
	private  String   remark ;// 分组描述
	
	private  int  maxAmount ;// 分组最大机器数量
	
	private  Date     createTime ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	
	
}
