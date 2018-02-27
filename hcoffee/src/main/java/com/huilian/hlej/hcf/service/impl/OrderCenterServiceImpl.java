package com.huilian.hlej.hcf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.OrderCenterDao;
import com.huilian.hlej.hcf.service.OrderCenterService;
import com.huilian.hlej.hcf.vo.OrderBaseInfoVo;
import com.huilian.hlej.hcf.vo.OrderGoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 订货中心Service实现类
 * @author LongZhangWei
 * @date 2017年9月1日 下午5:27:33
 */
@Service
@Transactional
public class OrderCenterServiceImpl implements OrderCenterService {

	@Autowired
	private OrderCenterDao orderCenterDao;
	
	@Override
	public List<OrderBaseInfoVo> getList(OrderBaseInfoVo orderBaseInfoVo) {
		List<OrderBaseInfoVo> list = null;
		try {
			orderCenterDao.getOrderBaseInfoVoList(orderBaseInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Page<OrderBaseInfoVo> getOrderBaseInfoVoPage(Page<OrderBaseInfoVo> page, OrderBaseInfoVo orderBaseInfoVo) {
		orderBaseInfoVo.setPage(page);
		page.setList(orderCenterDao.getOrderBaseInfoVoList(orderBaseInfoVo));
		return page;
	}

	@Override
	public OrderBaseInfoVo getOrderBaseInfoVo(String id) {
		OrderBaseInfoVo baseInfoVo = null;
		try {
			baseInfoVo = orderCenterDao.getOrderBaseInfoVo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseInfoVo;
	}

	@Override
	public boolean updateOrderBaseInfoVo(OrderBaseInfoVo orderBaseInfoVo) {
		boolean flag = false;
		try {
			orderCenterDao.updateOrderBaseInfoVo(orderBaseInfoVo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<OrderGoodsVo> goodslist(String orderNo) {
		List<OrderGoodsVo> list = null;
		try {
			list = orderCenterDao.goodslist(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return list;
	}

	@Override
	public Page<OrderGoodsVo> getOrderGoodsVoPage(Page<OrderGoodsVo> page, OrderGoodsVo orderGoodsVo) {
		orderGoodsVo.setPage(page);
		List<OrderGoodsVo> list = orderCenterDao.goodslist(orderGoodsVo.getOrderBaseInfo().getOrderNo());
		page.setList(list);
		page.setCount(list.size());
		return page;
	}

	@Override
	public void updateOrderSyncStatus(Map<String, Object> map) {
		try {
			orderCenterDao.updateOrderSyncStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
