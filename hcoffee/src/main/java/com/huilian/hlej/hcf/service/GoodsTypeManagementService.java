package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsTypeVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface GoodsTypeManagementService {

	/**
	 * 查询商品类型
	 * @param goodsVo
	 * @return
	 */
	
	public List<GoodsTypeVo> getGoodsTypeVoList(GoodsTypeVo goodsTypeVo);
	
	
	/**
	 * 保存商品类型
	 * @param goodsVo
	 */
	public boolean saveGoodsTypeVo(GoodsTypeVo goodsTypeVo);
	
	/**
	 * 修改商品类型
	 * @param goodsVo
	 */
	public boolean updateGoodsTypeVo(GoodsTypeVo goodsTypeVo);
	
	/**
	 * 分页
	 * @param page
	 * @param goodsVo
	 * @return
	 */
	public Page<GoodsTypeVo> getGoodsTypeVoPage(Page<GoodsTypeVo> page, GoodsTypeVo goodsTypeVo);
	
	/**
	 * 查询单个商品类型
	 * @param id
	 * @return
	 */
	public GoodsTypeVo getGoodsTypeVoById(String id);
	
	/**
	 *  删除商品类型
	 * @param id
	 * @return
	 */
	public boolean deleteGoodsTypeVoById(String id);
	
	/**
	 * 判断是否可以删除该商品类型
	 * @param id
	 * @return
	 */
	public boolean isCanDeleteGoodsType(String id);
}
