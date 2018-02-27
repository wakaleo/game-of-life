package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 商品销售详细Dao
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:16:09
 */
@MyBatisDao
public interface GoodsSaleDetailDao {

	/**
	 * 查询商品销售
	 * @param goodsSaleDetailVo
	 * @return
	 */
	public List<GoodsSaleDetailVo> getGoodsSaleDetailVoList(GoodsSaleDetailVo goodsSaleDetailVo);
}
