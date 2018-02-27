package com.huilian.hlej.hcf.vo;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 线下交易信息实体类 hcf_offline_vend_order
 * 
 * @author LongZhangWei
 * @date 2018年1月22日 下午3:13:34
 */
public class TradeInfoVo extends BaseDataEntity<TradeInfoVo> implements Serializable {

	// 序列号
	private static final long serialVersionUID = 1L;
	// 订单号
	private String orderNo;
	// 手机号码
	private String phoneNo;
	// 用户名
	private String userName;
	// 用户地址
	private String location;
	// 商品名
	private String goodsName;
	// 商品价格
	private Double amount;
	// 机器编码
	private String vendCode;
	// 商品所在货道
	private String shelf;

	@ExcelField(title = "订单号", align = 1, sort = 1)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title = "手机号", align = 1, sort = 2)
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@ExcelField(title = "用户名", align = 1, sort = 3)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "用户地址", align = 1, sort = 4)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@ExcelField(title = "商品名", align = 1, sort = 5)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@ExcelField(title = "商品价格", align = 1, sort = 6)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@ExcelField(title = "机器编码", align = 1, sort = 7)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	@ExcelField(title = "货道", align = 1, sort = 8)
	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

}
