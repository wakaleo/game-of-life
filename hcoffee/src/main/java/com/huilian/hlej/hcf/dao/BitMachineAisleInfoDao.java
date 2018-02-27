package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.BitMachineAisleInfoVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.hcf.vo.BitMachineAisleInfoVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
/**
 * 比特机器基本信息Dao
 * @author ZhangZeBiao
 * @date 2017年12月14日 下午5:32:27
 */
@MyBatisDao
public interface BitMachineAisleInfoDao extends CrudDao<BitMachineAisleInfoVo> {
	/**
	 * 查询所有的比特机器货道信息
	 * @param dealerManagermentVo
	 * @return
	 */
	public List<BitMachineAisleInfoVo> findBitMachineAisleInfoVoList(BitMachineAisleInfoVo bitMachineAisleInfoVo);

	/**
	 * 获取总货道数等货道统计数据
	 * @param bitMachineAisleInfoVo
	 * @return
	 */
	public BitMachineBaseInfoVo getBitMachineAisleCountVo(BitMachineAisleInfoVo bitMachineAisleInfoVo);
	
	
}
