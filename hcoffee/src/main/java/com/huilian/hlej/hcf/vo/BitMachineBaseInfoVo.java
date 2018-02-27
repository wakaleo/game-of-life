package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 抽奖活动管理实体类
 * 
 * @author ZhangZeBiao
 * @date 2017年12月14日 下午5:32:27
 */
public class BitMachineBaseInfoVo extends BaseDataEntity<BitMachineBaseInfoVo> implements Serializable {

	/*
	 * 经销商基本信息 数据库对应表 hcf_dealer_baseInfo
	 */
	private static final long serialVersionUID = 1L;

	
	
	// 安卓ID
	private String androidId;
	
	// 机器编号
	private String machineCode;

	// 机器名称
	private String machineName;
	// 机器备注
	private String machineMemo;
	// 在线或离线时间
	private Date offlineOrOnlineTime;
	// 是否在线  1在线0离线
	private Integer isOnline;
	private String isOnlineStr;
	// 组织根ID
	private Integer organizationRootId;
	// 组织ID
	private String organizationId;
	// 使用的支付方式
	private String usedPayType;
	// 机器是否故障  1故障 0正常
	private Integer isAisleBreak;
	// 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date gmtCreated;
	// 修改时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;
	
	// 是否断货  0断货 1正常
	private Integer isOutOfStock;
	private String isOutOfStockStr;
	
	// 搜过经的内容
	private String searchText;

	//故障货道数
	private Integer GZHDS;
	//缺货货道数
	private Integer QHHDS;
	//缺货商品数
	private Integer QHSPS;
	//总库存容量
	private Integer ZKCRL;
	
	//断货货道数
	private Integer DHHDS;
	//缺货率
	private Double QHL;
	//断货率
	private Double DHL;
	//总货道数
	private Integer ZHDS;
	
	
	
	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	@ExcelField(title = "机器编号", align = 2, sort = 1)//1在线0离线
	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	@ExcelField(title = "机器名称", align = 2, sort = 3)//1在线0离线
	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineMemo() {
		return machineMemo;
	}

	public void setMachineMemo(String machineMemo) {
		this.machineMemo = machineMemo;
	}

	@ExcelField(title = "最后心跳时间", align = 2, sort = 6)//1在线0离线
	public Date getOfflineOrOnlineTime() {
		return offlineOrOnlineTime;
	}

	public void setOfflineOrOnlineTime(Date offlineOrOnlineTime) {
		this.offlineOrOnlineTime = offlineOrOnlineTime;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	@ExcelField(title = "网络状态", align = 2, sort = 5)//1在线0离线
	public String getIsOnlineStr() {
		String str = "";
		if(isOnline == 0){
			str = "离线";
		}
		if(isOnline == 1){
			str = "在线";
		}
		return str;
	}

	public void setIsOnlineStr(String isOnlineStr) {
		this.isOnlineStr = isOnlineStr;
	}

	public Integer getOrganizationRootId() {
		return organizationRootId;
	}

	public void setOrganizationRootId(Integer organizationRootId) {
		this.organizationRootId = organizationRootId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getUsedPayType() {
		return usedPayType;
	}

	public void setUsedPayType(String usedPayType) {
		this.usedPayType = usedPayType;
	}

	public Integer getIsAisleBreak() {
		return isAisleBreak;
	}

	public void setIsAisleBreak(Integer isAisleBreak) {
		this.isAisleBreak = isAisleBreak;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsOutOfStock() {
		return isOutOfStock;
	}

	public void setIsOutOfStock(Integer isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	@ExcelField(title = "是否故障", align = 2, sort = 4)//1断货 0正常
	public String getIsOutOfStockStr() {
		String str = "";
		if(isOutOfStock == 0){
			str = "否";
		}
		if(isOutOfStock == 1){
			str = "是";
		}
		return str;
	}

	public void setIsOutOfStockStr(String isOutOfStockStr) {
		this.isOutOfStockStr = isOutOfStockStr;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Integer getGZHDS() {
		return GZHDS;
	}

	public void setGZHDS(Integer gZHDS) {
		GZHDS = gZHDS;
	}

	public Integer getQHHDS() {
		return QHHDS;
	}

	public void setQHHDS(Integer qHHDS) {
		QHHDS = qHHDS;
	}

	public Integer getDHHDS() {
		return DHHDS;
	}

	public void setDHHDS(Integer dHHDS) {
		DHHDS = dHHDS;
	}

	

	public Double getQHL() {
		return QHL;
	}

	public void setQHL(Double qHL) {
		QHL = qHL;
	}

	public Double getDHL() {
		return DHL;
	}

	public void setDHL(Double dHL) {
		DHL = dHL;
	}

	public Integer getZHDS() {
		return ZHDS;
	}

	public void setZHDS(Integer zHDS) {
		ZHDS = zHDS;
	}

	public Integer getQHSPS() {
		return QHSPS;
	}

	public void setQHSPS(Integer qHSPS) {
		QHSPS = qHSPS;
	}

	public Integer getZKCRL() {
		return ZKCRL;
	}

	public void setZKCRL(Integer zKCRL) {
		ZKCRL = zKCRL;
	}
	
	
	@ExcelField(title = "机器激活码", align = 2, sort = 2)//1在线0离线
	public String getId() {
		return super.getId();
	}
	

}
