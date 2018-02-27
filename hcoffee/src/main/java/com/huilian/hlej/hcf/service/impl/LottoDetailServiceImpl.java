package com.huilian.hlej.hcf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.LottoDetailDao;
import com.huilian.hlej.hcf.service.LottoDetailService;
import com.huilian.hlej.hcf.vo.LottoDetailVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 抽奖明细Service实现类
 * @author ZhangZeBiao
 * @date 2017年11月2日 下午7:43:40
 */

@Service
@Transactional
public class LottoDetailServiceImpl implements LottoDetailService {

	@Autowired
	private LottoDetailDao lottoDetailDao;
	
	/*@Override
	public List<GoodsSaleDetailVo> getGoodsSaleDetailVoList(GoodsSaleDetailVo goodsSaleDetailVo) {
		List<GoodsSaleDetailVo> list = null;
		try {
			list = goodsSaleDetailDao.getGoodsSaleDetailVoList(goodsSaleDetailVo);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public Page<GoodsSaleDetailVo> getGoodsSaleDetailVoPage(Page<GoodsSaleDetailVo> page,
			GoodsSaleDetailVo goodsSaleDetailVo) {
		goodsSaleDetailVo.setPage(page);
		page.setList(goodsSaleDetailDao.getGoodsSaleDetailVoList(goodsSaleDetailVo));
		return page;
	}*/

	@Override
	public Page<LottoDetailVo> getLottoDetailVoPage(Page<LottoDetailVo> page, LottoDetailVo lottoDetailVo) {
		lottoDetailVo.setPage(page);
		page.setList(lottoDetailDao.getLottoDetailVoList(lottoDetailVo));
		return page;
	}

}
