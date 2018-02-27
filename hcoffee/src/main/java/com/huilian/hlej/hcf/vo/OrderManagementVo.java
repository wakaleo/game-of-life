package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 订单管理vo
 * 
 * @author yangweichao
 * @date 2017-8-28 14:52:24
 */
public class OrderManagementVo extends BaseDataEntity<OrderManagementVo> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String id;
	
	/** 订单id */
	private String orderId;
	
	/** 订单时间 */
	private Date orderTime;
	
	/** 总金额 */
	private Double money;
	
	/** 订单状态 （0关闭订单1代发货2已发货3未收货4已收货5已完成） */
	private int orderState;
	
	/** 删除订单（1正常，2删除）*/
	private int removeOrder;
	
	/** 用户外键 */
	private String userId;
	
	/** 中间表外键  */
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public int getRemoveOrder() {
		return removeOrder;
	}

	public void setRemoveOrder(int removeOrder) {
		this.removeOrder = removeOrder;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
