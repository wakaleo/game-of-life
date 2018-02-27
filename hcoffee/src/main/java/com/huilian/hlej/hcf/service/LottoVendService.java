package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 奖品管理Service
 * 
 * @author zhangzebiao
 * @date 2017年10月28日 下午7:24:47
 */
public interface LottoVendService {

	/**
	 * 保存奖品
	 * @param dealerManagermentVo 
	 * @return
	 */
	public Map<String, String> saveLottoVendVo(List<LottoVendVo> lottoVendVo);
	
	/**
	 * 抽奖活动分页
	 * @param page
	 * @param lottoVendVo
	 * @return
	 */
	public Page<LottoVendVo> getLottoVendVoPage(Page<LottoVendVo> page, LottoVendVo lottoVendVo);
	public List<LottoEqRelationVo> getLottoEqRelationVoAll();
	/**
	 * 查询所有活动信息
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
	 * 根据ID获取一条数据
	 * @param id
	 * @return
	 */
	public LottoVendVo getLottoVendVo(String id);

	/**
	 * 删除奖品
	 * @param lottoVendVo
	 * @return
	 */
	public boolean deletePrize(LottoVendVo lottoVendVo);

	/**
	 * 更新
	 * @param lottoVendVo
	 * @return
	 */
	public Map<String, String> updateLottoVendVo(LottoVendVo lottoVendVo);

	/**
	 * 检验奖品名称是否可用
	 * @param prizeName
	 * @return
	 */
	public boolean checkPrizeName(String prizeName);

	/**
	 * 根据活动ID和机器码获取对应的奖品列表
	 * @param lottoVendVo
	 * @return
	 */
	public List<LottoVendVo> getLottoVendVoListByActivityNoAndVendCode(LottoVendVo lottoVendVo);

	/**
	 * 删除机器
	 * @param lottoVendVo
	 * @return
	 */
	public boolean deleteVendCodeByActivityNoAndVendCode(LottoVendVo lottoVendVo);
	
	
	
}
