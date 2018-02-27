package com.huilian.hlej.hcf.vo;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 经销商与设备关系实体类 hcf_eq_dealer_relation
 * 
 * @author LongZhangWei
 * @date 2017年8月25日 上午11:23:22
 */
public class LottoEqRelationVo extends BaseDataEntity<LottoEqRelationVo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	// 抽奖活动ID
	private String activityNo;

	// 设备编号
	private String vendCode;

	// 设备投放位置
	private String location;


	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
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

}
