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
public class EquipSupplyVo extends BaseDataEntity<EquipSupplyVo> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String equipId;//
	private String equipSupply;// 
	public EquipSupplyVo(String equipId, String equipSupply) {
		super();
		this.equipId = equipId;
		this.equipSupply = equipSupply;
	}
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipSupply() {
		return equipSupply;
	}
	public void setEquipSupply(String equipSupply) {
		this.equipSupply = equipSupply;
	}
	
	
	
	

}
