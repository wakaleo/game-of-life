package com.huilian.hlej.hcf.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 二维码模板管理vo
 * 
 * @author ZhangZeBiao
 * @date 2017年12月7日 下午5:56:44
 */
public class WechatMaterialModelVo extends BaseDataEntity<WechatMaterialModelVo> implements Serializable {

	/*
	 * 对应表 hcf_wechat_material_model
	 */
	private static final long serialVersionUID = 1L;


	// 模板名称
	private String modelName;

	// 模板编码
	private String modelNo;
	// 二维码素材编码
	private String wechatNo;
	// 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	// 备注
	private String remark;
	// 排序
	private Integer sort;
	
	
	
	
	// 搜过经的内容
	private String searchText;
	
	
	// 二维码名称
	private String wechatName;

	
	/*
	 * 数据库以应表 hcf_wechat_material
	 */

	// 模板id
	private String[] arr_id = new String[10];
	// 素材编号
	private String[] arr_no = new String[10];
	// 素材名称
//	private String[] arr_na = new String[10];
	// 创建时间
	private Date[] arr_ct = new Date[10];
	// 排序
	private Integer[] arr_st = new Integer[10];



	
	
	
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
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
	public String[] getArr_no() {
		return arr_no;
	}
	public void setArr_no(String[] arr_no) {
		this.arr_no = arr_no;
	}
	public String[] getArr_id() {
		return arr_id;
	}
	public void setArr_id(String[] arr_id) {
		this.arr_id = arr_id;
	}
	public Date[] getArr_ct() {
		return arr_ct;
	}
	public void setArr_ct(Date[] arr_ct) {
		this.arr_ct = arr_ct;
	}
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer[] getArr_st() {
		return arr_st;
	}
	public void setArr_st(Integer[] arr_st) {
		this.arr_st = arr_st;
	}
	
	

	




}
