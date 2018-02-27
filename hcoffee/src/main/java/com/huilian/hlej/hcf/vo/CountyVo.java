package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 城市Vo
 * 
 * @author yangbo
 * @date 2017年5月12日 下午2:27:37
 *
 */
public class CountyVo extends BaseDataEntity<CountyVo> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String countyId;//
	private String cityId;// 
	private String countyName;// 
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

}
