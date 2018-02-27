package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 售货机版本实体
 * @author xiekangjian
 * @date 2016年12月30日 下午3:03:42
 *
 */
public class VendStoreVo extends BaseDataEntity<VendStoreVo> implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String id;//本次app升级内容
	private String vendCode;//app版本
	private String shelf;//上一版本号
	private String goodsName;//hsh_appversion ,id.上一版本号id
	private String storeGoodsLink;//hsh_appversion ,id.上一版本号id
	private String goodsID;//本次app升级内容
	private double amount;//默认使用状态0 使用中和1作废
	private String storeGoodsId;//默认使用状态0 使用中和1作废
	private Date createTime;//创建时间
	private Date startTime;//创建时间
	private Date endTime;//创建时间
	private Date lastUpdateTime;//创建时间
	private Date validTimeStart;//创建时间
	private Date validTimeEnd;//创建时间

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ExcelField(title = "售货机编码", align = 2, sort = 30)
	public String getVendCode() {
		return vendCode;
	}
	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	@ExcelField(title = "货道", align = 2, sort = 30)
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	@ExcelField(title = "商品名称", align = 2, sort = 30)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@ExcelField(title = "商品链接", align = 2, sort = 30)
	public String getStoreGoodsLink() {
		return storeGoodsLink;
	}
	public void setStoreGoodsLink(String storeGoodsLink) {
		this.storeGoodsLink = storeGoodsLink;
	}

	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
 	@ExcelField(title = "免息编号", align = 2, sort = 30)
	public String getStoreGoodsId() {
		return storeGoodsId;
	}
	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}
	@ExcelField(title = "创建时间", align = 2, sort = 30)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getValidTimeStart() {
		return validTimeStart;
	}
	public void setValidTimeStart(Date validTimeStart) {
		this.validTimeStart = validTimeStart;
	}
	public Date getValidTimeEnd() {
		return validTimeEnd;
	}
	public void setValidTimeEnd(Date validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
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
	@ExcelField(title = "商品价格", align = 2, sort = 30)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	
}
