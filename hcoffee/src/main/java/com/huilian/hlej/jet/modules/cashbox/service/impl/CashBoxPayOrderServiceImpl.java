package com.huilian.hlej.jet.modules.cashbox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.service.BaseService;
import com.huilian.hlej.jet.modules.cashbox.dao.CashBoxPayOrderDao;
import com.huilian.hlej.jet.modules.cashbox.entity.CashBoxPayOrderModel;
import com.huilian.hlej.jet.modules.cashbox.service.CashBoxPayOrderService;

@Service(value = "cashBoxPayOrderService")
public class CashBoxPayOrderServiceImpl extends BaseService implements CashBoxPayOrderService {

	@Autowired
	private CashBoxPayOrderDao cashBoxPayOrderDao;

	@Override
	public Page<CashBoxPayOrderModel> getOrderList(Page<CashBoxPayOrderModel> page, CashBoxPayOrderModel obj) {
		obj.setPage(page);
		page.setList(cashBoxPayOrderDao.findByCondition(obj));
		return page;
	}

	@Override
	public CashBoxPayOrderModel get(String id) {
		return cashBoxPayOrderDao.get(id);
	}

}
