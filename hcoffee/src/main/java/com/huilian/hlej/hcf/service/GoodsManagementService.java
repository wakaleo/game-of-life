package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface GoodsManagementService {

	/**
	 * 查询商品
	 * @param goodsVo
	 * @return
	 */
	
	public List<GoodsVo> getGoodsVoList(GoodsVo goodsVo);
	
	
	/**
	 * 保存商品
	 * @param goodsVo
	 */
	public boolean saveGoodsVo(GoodsVo goodsVo);
	
	/**
	 * 修改商品
	 * @param goodsVo
	 */
	public boolean updateGoodsVo(GoodsVo goodsVo);
	
	/**
	 * 分页
	 * @param page
	 * @param goodsVo
	 * @return
	 */
	public Page<GoodsVo> getGoodsVoPage(Page<GoodsVo> page, GoodsVo goodsVo);
	
	/**
	 * 查询单个商品
	 * @param id
	 * @return
	 */
	public GoodsVo getGoodsVoById(String id);
	
	/**
	 * 修改商品的上下架状态
	 * @param goodsVo
	 * @return
	 */
	public boolean updateIsSale(GoodsVo goodsVo);
}
