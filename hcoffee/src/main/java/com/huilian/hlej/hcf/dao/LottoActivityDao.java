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
 * @date 2017年10月24日 下午7:22:26
 */
@MyBatisDao
public interface LottoActivityDao extends CrudDao<LottoActivityVo> {
	/**
	 * 查询所有的抽奖活动信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<LottoActivityVo> findLottoActivityVoList(LottoActivityVo lottoActivityVo);
	/**
	 * 通过抽奖活动ID查询单个具体的经销商
	 * @param loginName
	 * @return
	 */
	public LottoActivityVo getLottoActivityVo(String activityNo);
	/**
	 * 通过抽奖活动ID查询其关联的设备
	 * @param activityNo
	 * @return
	 */
	public List<LottoEqRelationVo> getLottoEqRelationVoList(String activityNo);
	
	/**
	 * 查询所有机械设备与投放的位置
	 * @return
	 */
	public List<LottoEqRelationVo> getLottoEqRelationVoAll();
	
	
	
	/**
	 * 根据ID找name
	 * @param activityNo
	 * @return
	 */
	public String queryActivityNameByActivityNo(String activityNo);
	
	/**
	 * 根据机器码查询详细位置：省市区+详细地址
	 * @return
	 */
	public String getLocationByVendCode(String vendCode);
	/**
	 * 活动名称是否可用
	 * @param activityName
	 * @return
	 */
	public Integer checkActivityName(String activityName);
	/**
	 * 更新
	 * @param lottoActivityVo
	 */
	public void updateLottoActivityVo(LottoActivityVo lottoActivityVo);
	/**
	 * 保存
	 * @param lottoActivityVo
	 */
	public void saveLottoActivityVo(LottoActivityVo lottoActivityVo);
	/**
	 * 活动编号是否可用
	 * @param activityNo
	 * @return
	 */
	public Integer checkActivityNo(String activityNo);
	/**
	 * 获取抽奖次数
	 * @param string
	 * @return
	 */
	public Integer getDrawNum(String activityNo);
	/**
	 * 获取中奖次数
	 * @param lactivityVo
	 * @return
	 */
	public Integer getWinnerNum(String activityNo);
	
	
}
