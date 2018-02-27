package com.huilian.hlej.hcf.vo;

import java.sql.Timestamp;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 银行logo实体类
 * 
 * @author LongZhangWei
 * @date 2017年12月12日 下午2:04:45
 */
public class BankLogoVo extends BaseDataEntity<BankLogoVo> {

	// 银行名称
	private String bankName;
	// 银行编码
	private Integer bankCode;
	// logo图片Url
	private String logoUrl;
	// 创建时间
	private String createTime;
	// 创建人
	private String createBy;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}
