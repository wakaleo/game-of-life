package com.huilian.hlej.hcf.vo;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 代理商模版VO hcf_dealer_template
 * 
 * @author LongZhangWei
 * @date 2017年11月16日 下午3:39:37
 */
public class DealerTemplateVo {

	private Integer id;
	// 登录名
	private String loginName;
	// 模版id
	private Integer templateId;
	// 模版名
	private String templateName;
	// 应用时间
	private Timestamp useTime;
	private String useTimeStr;
	// 姓名
	private String dealerName;
	// 代理类型
	private Integer dealerType;
	// 操作人
	private String operator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Timestamp getUseTime() {
		return useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getDealerType() {
		return dealerType;
	}

	public void setDealerType(Integer dealerType) {
		this.dealerType = dealerType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getUseTimeStr() {
		return useTimeStr;
	}

	public void setUseTimeStr(String useTimeStr) {
		this.useTimeStr = useTimeStr;
	}
}
