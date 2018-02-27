package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 设备信息实体类
 * 
 * @author LongZhangWei
 * @date 2017年12月25日 上午9:34:27
 */
public class EquipmentInfoVo extends BaseDataEntity<EquipmentInfoVo> implements Serializable {

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
	// 联网状态
	private Integer networkState;
	private String networkStateStr;
	// 故障状态
	private Integer faultState;
	private String faultStateStr;
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

	public Integer getNetworkState() {
		return networkState;
	}

	public void setNetworkState(Integer networkState) {
		this.networkState = networkState;
	}

	@ExcelField(title = "联网状态", align = 2, sort = 4)
	public String getNetworkStateStr() {
		if (networkState.intValue() == 1) {
			networkStateStr = "离线";
		}
		if (networkState.intValue() == 2) {
			networkStateStr = "在线";
		}
		return networkStateStr;
	}

	public void setNetworkStateStr(String networkStateStr) {
		this.networkStateStr = networkStateStr;
	}

	public Integer getFaultState() {
		return faultState;
	}

	public void setFaultState(Integer faultState) {
		this.faultState = faultState;
	}

	@ExcelField(title = "故障状态", align = 2, sort = 5)
	public String getFaultStateStr() {
		if(faultState.intValue() == 1){
			faultStateStr = "正常";
		}
		if(faultState.intValue() == 2){
			faultStateStr = "故障";
		}
		return faultStateStr;
	}

	public void setFaultStateStr(String faultStateStr) {
		this.faultStateStr = faultStateStr;
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
