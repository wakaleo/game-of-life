package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.GoodsSaleDetailDao;
import com.huilian.hlej.hcf.service.GoodsSaleDetailService;
import com.huilian.hlej.hcf.vo.GoodsSaleDetailVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 商品销售明细Service实现类
 * @author LongZhangWei
 * @date 2017年9月4日 下午7:43:40
 */

@Service
@Transactional
public class GoodsSaleDetailServiceImpl implements GoodsSaleDetailService {

	@Autowired
	private GoodsSaleDetailDao goodsSaleDetailDao;
	
	@Override
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
	}

}
