package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.OrderManagementVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 订单管理dao
 * 
 * @author yangweichao
 * date 2017-8-28 15:30:55
 */
@MyBatisDao
public interface OrderManagementDao {

	List<OrderManagementVo> getOrderManagementPage(OrderManagementVo orderManagementVo);


	void updateOrderMangementById(OrderManagementVo orderManagementVo);

}
