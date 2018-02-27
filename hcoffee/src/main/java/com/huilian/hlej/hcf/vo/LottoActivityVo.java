package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 抽奖活动管理实体类
 * 
 * @author ZhangZeBiao
 * @date 2017年10月24日 上午11:48:03
 */
public class LottoActivityVo extends BaseDataEntity<LottoActivityVo> implements Serializable {

	/*
	 * 抽奖活动管理信息 数据库对应表 hcf_lotto_activity
	 */
	private static final long serialVersionUID = 1L;

	// 抽奖活动ID
	private String activityNo;

	// 活动名称
	private String activityName;
	// 活动状态：1-未进行  2-进行中  3-已结束
	private Integer activityStatus;
	// 创建时间
	private Date createTime;
	// 抽奖次数
	private Integer drawNum;
	// 中奖人（次）数
	private Integer winnerNum;
	// 开始时间 
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	// 结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	// 抽奖方式:1-转盘  2-投票
	private Integer lottoWay;
	// 触发条件:1-第三方指令
	private Integer triggerCondition;
	// 活动说明
	private String activityDesc;
	// 当前时间
	private Date nowTime;
	
	
	
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDrawNum() {
		return drawNum;
	}
	public void setDrawNum(Integer drawNum) {
		this.drawNum = drawNum;
	}
	public Integer getWinnerNum() {
		return winnerNum;
	}
	public void setWinnerNum(Integer winnerNum) {
		this.winnerNum = winnerNum;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) throws ParseException {
		this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) throws ParseException {
		this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
	}
	public Integer getLottoWay() {
		return lottoWay;
	}
	public void setLottoWay(Integer lottoWay) {
		this.lottoWay = lottoWay;
	}
	public Integer getTriggerCondition() {
		return triggerCondition;
	}
	public void setTriggerCondition(Integer triggerCondition) {
		this.triggerCondition = triggerCondition;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
	
	
	

}
