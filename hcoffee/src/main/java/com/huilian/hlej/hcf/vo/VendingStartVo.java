package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

/**
 * 活动统计Vo
 * 
 * @author yangbo 
 * @date 2017年1月12日 下午2:27:37
 *
 */
public class VendingStartVo extends BaseDataEntity<VendingStartVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String vendCode;// 售货机编码
	private String remark;// 售货机编码
	private String status;// '售货机运营状态（0，正常，1，不正常）',
	private Date heartTime;
	private String statusName;//状态名称
	@ExcelField(title = "售货机编码", align = 2, sort = 10)
	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}
	@ExcelField(title = "最后一次心跳时间", align = 2, sort = 20)
	public Date getHeartTime() {
		return heartTime;
	}

	public void setHeartTime(Date heartTime) {
		this.heartTime = heartTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@ExcelField(title = "售货机运营情况", align = 2, sort = 30)
	public String getStatusName() {
		if ( Integer.valueOf(status).intValue()==1) {
			return "不正常";
		} 
		return "正常";
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
	@ExcelField(title = "备注", align = 2, sort = 40)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	

}
