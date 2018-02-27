package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 经销商管理Service
 * 
 * @author LongZhangWei
 * @date 2017年8月24日 下午7:24:47
 */
public interface LottoActivityService {

	/**
	 * 抽奖活动分页
	 * @param page
	 * @param lottoActivityVo
	 * @return
	 */
	public Page<LottoActivityVo> getLottoActivityVoPage(Page<LottoActivityVo> page, LottoActivityVo lottoActivityVo);
	/**
	 * 查询所有的经销商基本信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<LottoActivityVo> getLottoActivityVoList(LottoActivityVo lottoActivityVo);
	/**
	 * 通过活动编码查询单个
	 * @param loginName
	 * @return
	 */
	public LottoActivityVo getLottoActivityVo(String activityNo);
	/**
	 * 通过抽奖活动ID查询其关联的设备
	 * @param dealerId
	 * @return
	 */
	public List<LottoEqRelationVo> getLottoEqRelationVoList(String activityNo);
	/**
	 * 查询所有机械设备与投放的位置
	 * @return
	 */
	public List<LottoEqRelationVo> getLottoEqRelationVoAll();
	
	/**
	 * 活动名称是否可用
	 * @param activityName
	 * @return
	 */
	public boolean checkActivityName(String activityName);
	/**
	 * 保存活动
	 * @param lottoActivityVo
	 * @return 
	 */
	public Map<String, String> saveLottoActivityVo(LottoActivityVo lottoActivityVo);
	/**
	 * 更新
	 * @param lottoActivityVo
	 * @return
	 */
	public Map<String, String> updateLottoActivityVo(LottoActivityVo lottoActivityVo);
	/**
	 * 活动编号是否可用
	 * @param activityNo
	 * @return
	 */
	public boolean checkActivityNo(String activityNo);
	
	
}
