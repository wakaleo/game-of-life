package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 抽奖活动明细类 hcf_lotto_prize_info
 * 
 * @author ZhangZeBiao
 * @date 2017年11月2日 下午7:06:16
 */
public class LottoDetailVo extends BaseDataEntity<LottoDetailVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 机器编码
	private String vendCode;
	// 用户
	private String userNo;
	// 奖品类型：1-货道商品 2-商城商品
	private String prizeType;
	private String prizeTypeStr;
	// 奖品名称
	private String prizeName;
	// 中奖时间
	private Date createTime;
	// 抽奖活动编码
	private String activityNo;
	// 抽奖活动名称
	private String activityName;
	// 抽奖来源：1-商城注册 2-商城充值 10-缴纳物业 11-预存物业
	private Integer lottoSource;
	private String lottoSourceStr;

	// 查询的开始时间
	private Date startTime;
	// 查询的结束时间
	private Date endTime;
	// 状态：1-领取成功  2-奖品推送免息商城失败    3-货道不存在  4-空货道  5-出货失败
	private String status;
	private String statusStr;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@ExcelField(title = "状态", align = 2, sort = 8)
	public String getStatusStr() {
		String str = "";
		try {
			if ("1".equals(status.toString().trim()) )
				str = "领取成功";
			if ("2".equals(status.toString().trim()))
				str = "奖品推送免息商城失败";
			if ("3".equals(status.toString().trim()) )
				str = "货道不存在";
			if ("4".equals(status.toString().trim()))
				str = "空货道";
			if ("5".equals(status.toString().trim()))
				str = "出货失败";
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
		return str;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	@ExcelField(title = "机器编码", align = 2, sort = 2)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "用户", align = 2, sort = 7)
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	@ExcelField(title = "奖品类型", align = 2, sort = 6)
	public String getPrizeTypeStr() {
		String str = "";
		try {
			if ("1".equals(prizeType.toString().trim()) )
				str = "货道商品";
			if ("2".equals(prizeType.toString().trim()))
				str = "商城商品";
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
		return str;
	}

	public void setPrizeTypeStr(String prizeTypeStr) {
		this.prizeTypeStr = prizeTypeStr;
	}

	@ExcelField(title = "奖品名称", align = 2, sort = 4)
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@ExcelField(title = "中奖时间", align = 2, sort = 3)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ExcelField(title = "活动编号", align = 2, sort = 1)
	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	
	@ExcelField(title = "活动名称", align = 2, sort = 1)
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getLottoSource() {
		return lottoSource;
	}

	public void setLottoSource(Integer lottoSource) {
		this.lottoSource = lottoSource;
	}

	@ExcelField(title = "抽奖来源", align = 2, sort = 5)
	public String getLottoSourceStr() {
		String str = "";
		if (lottoSource.intValue() == 1)
			str = "商城注册";
		if (lottoSource.intValue() == 2)
			str = "商城充值";
		if (lottoSource.intValue() == 10)
			str = "缴纳物业";
		if (lottoSource.intValue() == 11)
			str = "预存物业";
		return str;
	}

	public void setLottoSourceStr(String lottoSourceStr) {
		this.lottoSourceStr = lottoSourceStr;
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

}
