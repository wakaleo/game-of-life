package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.GoodsVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 商品管理Dao
 * @author LongZhangWei
 * @date 2017年8月30日 下午1:39:16
 */
@MyBatisDao
public interface GoodsManagementDao extends CrudDao<GoodsVo> {

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
	public void saveGoodsVo(GoodsVo goodsVo);
	
	/**
	 * 修改商品
	 * @param goodsVo
	 */
	public void updateGoodsVo(GoodsVo goodsVo);
	
	/**
	 * 查询单个商品
	 * @param id
	 * @return
	 */
	public GoodsVo getGoodsVoById(String id);
	
	/**
	 * 商品上下架状态切换
	 */
	public void updateIsSale(GoodsVo goodsVo);
}
