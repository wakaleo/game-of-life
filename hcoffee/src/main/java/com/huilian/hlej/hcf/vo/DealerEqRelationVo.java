package com.huilian.hlej.hcf.vo;

import java.sql.Timestamp;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 经销商与设备关系实体类 hcf_eq_dealer_relation
 * 
 * @author LongZhangWei
 * @date 2017年8月25日 上午11:23:22
 */
public class DealerEqRelationVo extends BaseDataEntity<DealerEqRelationVo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	// 经销商登录名
	private String loginName;

	// 设备编号
	private String vendCode;

	// 设备投放位置
	private String location;

	// 机器绑定时间
	private Timestamp bangdingTime;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getBangdingTime() {
		return bangdingTime;
	}

	public void setBangdingTime(Timestamp bangdingTime) {
		this.bangdingTime = bangdingTime;
	}

}
