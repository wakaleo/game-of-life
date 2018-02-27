package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 二维码素材实体类
 * @author ZhangZeBiao
 * @date 2017年12月6日 下午2:02:47
 *
 */
public class WechatMaterialVo extends BaseDataEntity<WechatMaterialVo> implements Serializable {

	/*
	 * 对应表 hcf_wechat_material
	 */
	private static final long serialVersionUID = 1L;

	// 二维码名称
	private String wechatName;

	// 二维码编码
	private String wechatNo;
	// 开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	// 结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	// 顶部图片
	private String topUrl;
	// 底部图片
	private String bottomUrl;
	// 二维码logo图片
	private String logoUrl;
	// 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	
	// 搜过经的内容
	private String searchText;
	
	
	
	
	
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) throws ParseException {
		this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) throws ParseException {
		this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
	}
	public String getTopUrl() {
		return topUrl;
	}
	public void setTopUrl(String topUrl) {
		this.topUrl = topUrl;
	}
	public String getBottomUrl() {
		return bottomUrl;
	}
	public void setBottomUrl(String bottomUrl) {
		this.bottomUrl = bottomUrl;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	
}
