package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.hcf.vo.LottoDetailVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 抽奖明细Service
 * @author ZhangZeBiao	
 * @date 2017年11月2日 下午7:42:25
 */

public interface LottoDetailService {

	/**
	 * 查询商品销售
	 * @param goodsSaleDetailVo
	 * @return
	 */
	//public List<GoodsSaleDetailVo> getGoodsSaleDetailVoList(GoodsSaleDetailVo goodsSaleDetailVo);
	
	/**
	 * 分页
	 * @param page
	 * @param goodsSaleDetailVo
	 * @return
	 */
	//public Page<GoodsSaleDetailVo> getGoodsSaleDetailVoPage(Page<GoodsSaleDetailVo> page,GoodsSaleDetailVo goodsSaleDetailVo);

	/**
	 * 分页
	 * @param page
	 * @param lottoDetailVo
	 * @return
	 */
	public Page<LottoDetailVo> getLottoDetailVoPage(Page<LottoDetailVo> page, LottoDetailVo lottoDetailVo);
	
}
