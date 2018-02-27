package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.OrderManagementDao;
import com.huilian.hlej.hcf.service.OrderManagementService;
import com.huilian.hlej.hcf.vo.OrderManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 订单管理的实现类
 * 
 * @author yangweichao
 * @date 2017-8-28 15:27:57
 */
@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService{
	
	@Autowired
	private OrderManagementDao orderManagementDao;

	@Override
	public Page<OrderManagementVo> getOrderManagementPage(Page<OrderManagementVo> page,
			OrderManagementVo orderManagementVo) {
		orderManagementVo.setPage(page);
		page.setList(orderManagementDao.getOrderManagementPage(orderManagementVo));
		return page;
	}


	@Override
	public Map<String, String> updateOrderMangementById(OrderManagementVo orderManagementVo) {
		Map<String, String> result = new HashMap<String, String>();
		orderManagementDao.updateOrderMangementById(orderManagementVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
	}


	/*@Override
	public Map<String, String> selectAll(Page<OrderManagementVo> page, OrderManagementVo orderManagementVo) {
		orderManagementVo.setPage(page);
		page.setList(orderManagementDao.getOrderManagementPage(orderManagementVo));
		return page;
	}
*/
}
