package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 设备物料实体类
 * 
 * @author LongZhangWei
 * @date 2017年12月25日 上午9:43:48
 */
public class EquipmentMateriaVo extends BaseDataEntity<EquipmentMateriaVo> implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// 机器编号
	private String vendCode;
	// 位置简述
	private String location;
	// 详细地址
	private String address;
	// 物料名称
	private String materiaName;
	// 余量（g）
	private Integer surplusNum;
	private String surplusNumStr;
	// 更新时间
	private Timestamp updateTime;
	private String updateTimeStr;

	@ExcelField(title = "机器编号", align = 2, sort = 1)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "位置简述", align = 2, sort = 2)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@ExcelField(title = "详细地址", align = 2, sort = 3)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "物料名称", align = 2, sort = 4)
	public String getMateriaName() {
		return materiaName;
	}

	public void setMateriaName(String materiaName) {
		this.materiaName = materiaName;
	}

	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}

	@ExcelField(title = "余量（g）", align = 2, sort = 5)
	public String getSurplusNumStr() {
		return String.valueOf(surplusNum);
	}

	public void setSurplusNumStr(String surplusNumStr) {
		this.surplusNumStr = surplusNumStr;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@ExcelField(title = "更新时间", align = 2, sort = 6)
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

}
