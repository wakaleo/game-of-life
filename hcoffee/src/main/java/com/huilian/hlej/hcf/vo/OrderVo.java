package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 第三方交易查询Vo
 * 
 * @author yangbo
 * @date 2017年5月12日 下午2:27:37
 *
 */
public class OrderVo extends BaseDataEntity<OrderVo> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String id;// 售货机编码 s
	private String value;// 订单编号
	public OrderVo(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
