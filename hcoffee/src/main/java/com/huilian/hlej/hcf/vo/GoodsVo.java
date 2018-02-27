package com.huilian.hlej.hcf.vo;

import java.io.Serializable;

import com.huilian.hlej.jet.common.persistence.BaseDataEntity;

/**
 * 商品类
 * 
 * @author LongZhangWei
 * @date 2017年8月29日 下午3:17:09
 */
public class GoodsVo extends BaseDataEntity<GoodsVo> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	// 商品名称
	private String goodsName;
	//商品编码
	private String goodsId;
	// 商品类型名称
	private String typeName;
	// 商品进价,单位分
	private float priceInto;
	// 商品售价,单位分
	private float priceOut;
	// 图片地址
	private String pictureUrl;
	// 商品规格
	private String specification;
	// 商品类型外键id
	private Integer typeId;
	// 商品数量
	private int count;
	// 商品品牌
	private String goodsBrand;
	// 包箱数
	private String packagesNumber;
	// 商品添加时间
	private String createTime;
	// 跟新时间
	private String updateTime;
	// 是否上架
	private int isSale;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public float getPriceInto() {
		return priceInto;
	}

	public void setPriceInto(float priceInto) {
		this.priceInto = priceInto;
	}

	public float getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(float priceOut) {
		this.priceOut = priceOut;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getGoodsBrand() {
		return goodsBrand;
	}

	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}

	public String getPackagesNumber() {
		return packagesNumber;
	}

	public void setPackagesNumber(String packagesNumber) {
		this.packagesNumber = packagesNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getIsSale() {
		return isSale;
	}

	public void setIsSale(int isSale) {
		this.isSale = isSale;
	}

}
