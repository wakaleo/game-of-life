package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsTypeVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 商品分类管理Dao
 * @author LongZhangWei
 * @date 2017年8月30日 下午1:39:16
 */
@MyBatisDao
public interface GoodsTypeManagementDao extends CrudDao<GoodsTypeVo> {

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
	public void saveGoodsTypeVo(GoodsTypeVo goodsTypeVo);
	
	/**
	 * 修改商品类型
	 * @param goodsVo
	 */
	public void updateGoodsTypeVo(GoodsTypeVo goodsTypeVo);
	
	/**
	 * 查询单个商品类型
	 * @param id
	 * @return
	 */
	public GoodsTypeVo getGoodsTypeVoById(String id);
	
	/**
	 * 删除商品类型
	 * @param id
	 */
	public void deleteGoodsTypeVoById(String id);
	
	/**
	 * 判断是否可以删除该商品类型
	 * @param id
	 * @return
	 */
	public Integer isCanDeleteGoodsType(String id);
}
