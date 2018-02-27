package com.huilian.hlej.hcf.entity;

 

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

 
/**
 * 售货机实体
 * 
 *  
 * Modifier  liujian
 */
 
public class LngLat extends BaseDataEntity<LngLat> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	
	private  String   vendCode ;//  售货机编码
	
	private  String channel   ;//     渠道
	
	
	private  String channelName;
	
	
	private  String lang  ;
	
	private  String lat;
	private  String laction;

	public String getVendCode() {
		return vendCode;
	}

	public void setVendCode(String vendCode) {
		this.vendCode = vendCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLaction() {
		return laction;
	}

	public void setLaction(String laction) {
		this.laction = laction;
	}
	
	
}
