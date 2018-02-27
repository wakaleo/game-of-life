package com.huilian.hlej.jet.modules.sys.entity;

/**
 * 
 * 国家
 * @author luowenyan
 * 2016年1月15日下午4:46:42
 * version 1.0
 */
public class Country {

	private Long id;
	
	private String chineseName;
	
	private String code;
	
	private String englishName;
	
	private Integer currencyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	
	
}
