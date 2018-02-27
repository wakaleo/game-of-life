package com.huilian.hlej.hcf.vo;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 代理商库存类
 * 
 * @author LongZhangWei
 * @date 2017年9月4日 上午10:45:07
 */
public class DealerStockVo extends BaseDataEntity<DealerStockVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 登录名
	private String loginName;
	// 姓名
	private String dealerName;
	// 代理类型
	private Integer dealerType;
	// 代理级别
	private Integer dealerGrade;
	// 合作状态
	private Integer conStatus;
	// 电话
	private String cellPhone;
	// 地址
	private String detailAddress;
	// 库存数
	private Integer stockAmount;
	// 搜索内容
	private String searchText;
	// 机器数
	private Integer machineNum;
	// 代理商所有的机器编码
	private String vendCodes;
	// 销售总量
	private Integer saleCount;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(Integer dealerType) {
		this.dealerType = dealerType;
	}

	public Integer getDealerGrade() {
		return dealerGrade;
	}

	public void setDealerGrade(Integer dealerGrade) {
		this.dealerGrade = dealerGrade;
	}

	public Integer getConStatus() {
		return conStatus;
	}

	public void setConStatus(Integer conStatus) {
		this.conStatus = conStatus;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getVendCodes() {
		return vendCodes;
	}

	public void setVendCodes(String vendCodes) {
		this.vendCodes = vendCodes;
	}

	public Integer getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(Integer machineNum) {
		this.machineNum = machineNum;
	}

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

}
