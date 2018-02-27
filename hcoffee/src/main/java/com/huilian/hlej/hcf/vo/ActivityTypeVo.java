package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 活动管理Vo
 * 
 * @author xiekangjian
 * @date 2017年1月12日 下午2:27:37
 *
 */
public class ActivityTypeVo extends BaseDataEntity<ActivityTypeVo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String activityId;
	private String activityType;
	private String maxImg;
	private String minImg;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getMaxImg() {
		return maxImg;
	}
	public void setMaxImg(String maxImg) {
		this.maxImg = maxImg;
	}
	public String getMinImg() {
		return minImg;
	}
	public void setMinImg(String minImg) {
		this.minImg = minImg;
	}
	
	
	
}
