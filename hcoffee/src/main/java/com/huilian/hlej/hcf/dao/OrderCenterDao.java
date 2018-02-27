package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.OrderBaseInfoVo;
import com.huilian.hlej.hcf.vo.OrderGoodsVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 订货中心Dao
 * @author LongZhangWei
 * @date 2017年9月1日 下午4:59:04
 */
@MyBatisDao
public interface OrderCenterDao {
	
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<OrderBaseInfoVo> getOrderBaseInfoVoList(OrderBaseInfoVo orderBaseInfoVo);
	
	/**
	 * 查询单个订单
	 * @param id
	 * @return
	 */
	public OrderBaseInfoVo getOrderBaseInfoVo(String id);
	
	/**
	 * 修改订单信息
	 * @param orderBaseInfoVo
	 */
	public void updateOrderBaseInfoVo(OrderBaseInfoVo orderBaseInfoVo);
	
	/**
	 * 查询订单商品列表
	 * @param orderNo
	 * @return
	 */
	public List<OrderGoodsVo> goodslist(String orderNo);
	
	/**
	 * 更改订单同步状态
	 * @param map
	 */
	public void updateOrderSyncStatus(Map<String, Object> map);
	
}
