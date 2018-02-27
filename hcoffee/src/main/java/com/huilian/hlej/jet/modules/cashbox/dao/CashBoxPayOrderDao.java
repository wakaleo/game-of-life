package com.huilian.hlej.jet.modules.cashbox.dao;

import java.util.List;

import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.cashbox.entity.CashBoxPayOrderModel;

@MyBatisDao
public interface CashBoxPayOrderDao extends CrudDao<CashBoxPayOrderModel> {

	int insert(CashBoxPayOrderModel obj);

	CashBoxPayOrderModel findById(Object id);

	List<CashBoxPayOrderModel> findByCondition(CashBoxPayOrderModel condition);

	void updateStatus(CashBoxPayOrderModel condition);

	List<CashBoxPayOrderModel> findByMapPaging(CashBoxPayOrderModel condition);

	int findByMapPagingCount(CashBoxPayOrderModel condition);

	CashBoxPayOrderModel findByKey(CashBoxPayOrderModel condition);
}