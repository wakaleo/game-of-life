package com.huilian.hlej.hcf.vo;

 

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;
import com.huilian.hlej.jet.common.utils.excel.annotation.ExcelField;

 
/**
 * 
 * @author yangbo
 * @date 2017年3月1日 上午11:56:37
 *
 */
 
public class PayVo extends BaseDataEntity<PayVo> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal channelId;

	private String channelName; // 渠道商名称

	private Integer wxQrCode=0; // 微信支付 (1. 支持    0. 不支持)
	private Integer yjlQrCode=0; // 第三方支付 (1. 支持    0. 不支持) 
	private Integer zfbQrCode=0; // 支付宝支付 (1. 支持    0. 不支持) 
	private String mxscQrCode="0"; // 免息商城二维码，0：不显示，1显示
	private String payList;
	private String mxscQrCodeList;
	private String wxStatusName;//微信状态名称
	private String yjltatusName;//第三方支付状态名称
	private String zfbStatusName;//支付宝支付状态名称
	public String getPayList() {
		return payList;
	}
	public void setPayList(String payList) {
		this.payList = payList;
	}
	@ExcelField(title = "渠道编码", align = 2, sort = 10)
	public BigDecimal getChannelId() {
		return channelId;
	}
	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}
	@ExcelField(title = "渠道名称", align = 2, sort = 10)
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getWxQrCode() {
		return wxQrCode;
	}
	public void setWxQrCode(Integer wxQrCode) {
		this.wxQrCode = wxQrCode;
	}
	
	@ExcelField(title = "微信支付", align = 2, sort = 40)
	public String getWxStatusName() {
		if ( wxQrCode.intValue()==0) {
			return "不支持";
		} 
		return "支持";
	}
	public void setWxStatusName(String wxStatusName) {
		this.wxStatusName = wxStatusName;
	}
	
	
	public Integer getYjlQrCode() {
		return yjlQrCode;
	}
	public void setYjlQrCode(Integer yjlQrCode) {
		this.yjlQrCode = yjlQrCode;
	}
	@ExcelField(title = "第三方支付", align = 2, sort = 40)
	public String getYjltatusName() {
		if ( yjlQrCode.intValue()==0) {
			return "不支持";
		} 
		return "支持";
	}
	public void setYjltatusName(String yjltatusName) {
		this.yjltatusName = yjltatusName;
	}
	
	
	public Integer getZfbQrCode() {
		return zfbQrCode;
	}
	public void setZfbQrCode(Integer zfbQrCode) {
		this.zfbQrCode = zfbQrCode;
	}
	
	@ExcelField(title = "支付宝支付", align = 2, sort = 40)
	public String getZfbStatusName() {
		if ( zfbQrCode.intValue()==0) {
			return "不支持";
		} 
		return "支持";
	}
	public void setZfbStatusName(String zfbStatusName) {
		this.zfbStatusName = zfbStatusName;
	}
	public String getMxscQrCode() {
		return mxscQrCode;
	}
	public void setMxscQrCode(String mxscQrCode) {
		this.mxscQrCode = mxscQrCode;
	}
	public String getMxscQrCodeList() {
		return mxscQrCodeList;
	}
	public void setMxscQrCodeList(String mxscQrCodeList) {
		this.mxscQrCodeList = mxscQrCodeList;
	}


}
