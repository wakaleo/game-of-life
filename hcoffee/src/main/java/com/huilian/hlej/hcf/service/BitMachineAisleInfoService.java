package com.huilian.hlej.hcf.service;

import com.huilian.hlej.hcf.vo.BitMachineAisleInfoVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 比特机器货道信息Service
 * 
 * @author zhangzebiao
 * @date 2017年12月14日 下午5:32:27
 */
public interface BitMachineAisleInfoService {

	
	/**
	 * 比特机器货道信息分页
	 * @param page
	 * @param bitMachineAisleInfoVo
	 * @return
	 */
	public Page<BitMachineAisleInfoVo> getBitMachineAisleInfoVoPage(Page<BitMachineAisleInfoVo> page, BitMachineAisleInfoVo bitMachineAisleInfoVo);

	/**
	 * 获取总货道数等货道统计数据
	 * @param bitMachineAisleInfoVo
	 * @return
	 */
	public BitMachineBaseInfoVo getBitMachineAisleCountVo(BitMachineAisleInfoVo bitMachineAisleInfoVo);
	
	
	
}
