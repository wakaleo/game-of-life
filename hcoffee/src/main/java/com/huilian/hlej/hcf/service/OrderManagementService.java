package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.OrderManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 订单管理业务层
 * 
 * @author yangweichao
 * date: 2017-8-28 15:26:49
 */
public interface OrderManagementService {

	Page<OrderManagementVo> getOrderManagementPage(Page<OrderManagementVo> page,
			OrderManagementVo orderManagementVo);


	Map<String, String> updateOrderMangementById(OrderManagementVo orderManagementVo);

	/**
	 * 查询所有
	 * @param orderManagementVo
	 * @return
	 *//*
	Map<String, String> selectAll(OrderManagementVo orderManagementVo);

*/
	//Map<String, String> updateOrderMangementById(OrderManagementVo orderManagementVo);

}
