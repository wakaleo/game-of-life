package com.huilian.hlej.hcf.vo;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 商品类
 * 
 * @author LongZhangWei
 * @date 2017年8月29日 下午3:17:09
 */
public class GoodsTypeVo extends BaseDataEntity<GoodsTypeVo> implements Serializable{

	private static final long serialVersionUID = 1L;

	// 商品类型id
	private String typeId;
	// 类型名称
	private String typeName;
	// 排序字段
	private int orderFlag;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(int orderFlag) {
		this.orderFlag = orderFlag;
	}

}
