package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 比特机器基本信息Dao
 * @author ZhangZeBiao
 * @date 2017年12月14日 下午5:32:27
 */
@MyBatisDao
public interface BitMachineBaseInfoDao extends CrudDao<BitMachineBaseInfoVo> {
	/**
	 * 查询所有的比特机器基本信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<BitMachineBaseInfoVo> findBitMachineBaseInfoVoList(BitMachineBaseInfoVo bitMachineBaseInfoVo);

	/**
	 * 查询所有断货的机器信息
	 * @return
	 */
//	public List<BitMachineBaseInfoVo> findIsOutOfStock();
	
}
