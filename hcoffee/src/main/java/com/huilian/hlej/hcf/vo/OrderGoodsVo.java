package com.huilian.hlej.hcf.vo;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 订单商品列表类
 * @author LongZhangWei
 * @date 2017年9月3日 上午9:22:25
 */
public class OrderGoodsVo extends BaseDataEntity<OrderGoodsVo> {

	private static final long serialVersionUID = 1L;

	private OrderBaseInfoVo orderBaseInfo;

	private GoodsVo goods;
	
	private GoodsTypeVo goodsType;

	public OrderBaseInfoVo getOrderBaseInfo() {
		return orderBaseInfo;
	}

	public void setOrderBaseInfo(OrderBaseInfoVo orderBaseInfo) {
		this.orderBaseInfo = orderBaseInfo;
	}

	public GoodsVo getGoods() {
		return goods;
	}

	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

	public GoodsTypeVo getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsTypeVo goodsType) {
		this.goodsType = goodsType;
	}

}
