package com.huilian.hlej.jet.modules.cashbox.service;

import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.modules.cashbox.entity.CashBoxPayOrderModel;

public interface CashBoxPayOrderService {

	public CashBoxPayOrderModel get(String id);
	
	/**
	 * 查询盒子支付订单
	 * 
	 * @return
	 */
	public Page<CashBoxPayOrderModel> getOrderList(Page<CashBoxPayOrderModel> page, CashBoxPayOrderModel obj);

}
