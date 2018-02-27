package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.LottoDetailVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 抽奖详细Dao
 * @author ZhangZeBiao
 * @date 2017年11月2日 下午7:16:09
 */
@MyBatisDao
public interface LottoDetailDao {


	/**
	 * 查询中奖详细
	 * @param lottoDetailVo
	 * @return
	 */
	public List<LottoDetailVo> getLottoDetailVoList(LottoDetailVo lottoDetailVo);
}
