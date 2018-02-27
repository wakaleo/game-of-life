package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 商品销售明细Service
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:42:25
 */

public interface GoodsSaleDetailService {

	/**
	 * 查询商品销售
	 * @param goodsSaleDetailVo
	 * @return
	 */
	public List<GoodsSaleDetailVo> getGoodsSaleDetailVoList(GoodsSaleDetailVo goodsSaleDetailVo);
	
	/**
	 * 分页
	 * @param page
	 * @param goodsSaleDetailVo
	 * @return
	 */
	public Page<GoodsSaleDetailVo> getGoodsSaleDetailVoPage(Page<GoodsSaleDetailVo> page,GoodsSaleDetailVo goodsSaleDetailVo);
	
}
