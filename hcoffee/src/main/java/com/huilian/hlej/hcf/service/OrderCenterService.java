package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.OrderBaseInfoVo;
import com.huilian.hlej.hcf.vo.OrderGoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 订货中心Service
 * @author LongZhangWei
 * @date 2017年9月1日 下午5:25:10
 */
public interface OrderCenterService {

	/**
	 * 查询所有订单
	 * @param orderBaseInfoVo
	 * @return
	 */
	public List<OrderBaseInfoVo> getList(OrderBaseInfoVo orderBaseInfoVo);
	
	/**
	 * 分页
	 * @param page
	 * @param orderBaseInfoVo
	 * @return
	 */
	public Page<OrderBaseInfoVo> getOrderBaseInfoVoPage(Page<OrderBaseInfoVo> page, OrderBaseInfoVo orderBaseInfoVo);
	
	public Page<OrderGoodsVo> getOrderGoodsVoPage(Page<OrderGoodsVo> page, OrderGoodsVo orderGoodsVo);
	
	/**
	 * 查询单个订单
	 * @param id
	 * @return
	 */
	public OrderBaseInfoVo getOrderBaseInfoVo(String id);
	
	/**
	 * 修改单个订单信息
	 * @param orderBaseInfoVo
	 * @return
	 */
	public boolean updateOrderBaseInfoVo(OrderBaseInfoVo orderBaseInfoVo);
	
	/**
	 * 查询订单商品列表
	 * @param orderNo
	 * @return
	 */
	public List<OrderGoodsVo> goodslist(String orderNo);
	
	/**
	 * 更新订单的同步状态
	 * @param map
	 */
	public void updateOrderSyncStatus(Map<String,Object> map);
}
