package com.huilian.hlej.jet.modules.sys.entity;

import org.apache.poi.ss.formula.functions.T;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 
 * 省份
 * @author luowenyan
 * 2016年1月15日下午4:46:42
 * version 1.0
 */
public class Province extends BaseDataEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058782031180161101L;

	private String name;
	
	private Integer value;
	
	private Integer provinceState;
	
	private Long areaId;
	
	private Long countryId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getProvinceState() {
		return provinceState;
	}

	public void setProvinceState(Integer provinceState) {
		this.provinceState = provinceState;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	
}
