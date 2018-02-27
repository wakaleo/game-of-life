package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 抽奖活动管理实体类
 * 
 * @author ZhangZeBiao
 * @date 2017年10月24日 上午11:48:03
 */
public class LottoVendVo extends BaseDataEntity<LottoVendVo> implements Serializable {

	/*
	 * 经销商基本信息 数据库对应表 hcf_dealer_baseInfo
	 */
	private static final long serialVersionUID = 1L;

	// 抽奖活动编码
	private String activityNo;

	// 活动名称
	private String activityName;
	// 机器编码
	private String vendCode;
	// 奖品名
	private String prizeName;
	// 奖品类型
	private Integer prizeType;
	// 详细位置：省市区+详细地址
	private String location;
	// 货道
	private String shelf;
	// 商城商品ID
	private String goodsID;
	// 奖品图片地址
	private String prizeUrl;
	// 奖品数量
	private Integer prizeNum;
	// 概率：中奖概率合计为1
	private double probability;
	// 排序：升序
	private Integer sort;
	//创建时间
	private Date createTime;
	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	public String getVendCode() {
		return vendCode;
	}
	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	public Integer getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getPrizeUrl() {
		return prizeUrl;
	}
	public void setPrizeUrl(String prizeUrl) {
		this.prizeUrl = prizeUrl;
	}
	public Integer getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}
	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	

}
