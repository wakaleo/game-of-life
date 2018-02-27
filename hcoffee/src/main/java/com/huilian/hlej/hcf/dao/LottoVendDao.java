package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 抽奖活动管理Dao
 * @author ZhangZeBiao
 * @date 2017年10月29日 下午7:22:26
 */
@MyBatisDao
public interface LottoVendDao extends CrudDao<LottoActivityVo> {
	/**
	 * 保存奖品基本信息
	 * @param dealerManagermentVo
	 */
	void saveLottoVendVo(LottoVendVo lottoVendVo);
	/**
	 * 查询所有的抽奖活动信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<LottoVendVo> findLottoVendVoList(LottoVendVo lottoVendVo);
	
	
	/**
	 * 查询所有活动
	 * @return
	 */
	public List<LottoActivityVo> getLottoActivityVoAll();
	
	/**
	 * 获取奖品列表
	 * @param lottoVendVo
	 * @return
	 */
	public List<LottoVendVo> getPrizeList(LottoVendVo lottoVendVo);
	/**
	 * 更新奖品
	 * @param lottoVendVo
	 */
	void updateLottoVendVo(LottoVendVo lottoVendVo);
	/**
	 * 获取指定ID奖品
	 * @param id
	 * @return
	 */
	LottoVendVo getLottoVendVo(String id);
	/**
	 * 删除奖品
	 * @param lottoVendVo
	 */
	void deletePrize(LottoVendVo lottoVendVo);
	/**
	 * 查询奖品名是否可用
	 * @param prizeName
	 * @return
	 */
	int checkPrizeName(String prizeName);
	/**
	 * 查询指定活动机器下的奖品信息
	 * @param lottoVendVo
	 * @return
	 */
	List<LottoVendVo> getLottoVendVoListByActivityNoAndVendCode(LottoVendVo lottoVendVo);
	/**
	 * 删除指定活动指定机器的所有奖品
	 * @param activityNo
	 * @param vendCode
	 */
	void deleteListByActivityNoAndVendCode(LottoVendVo lottoVendVo);
	
}
