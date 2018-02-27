package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.DealerManagermentVo;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 比特机器基本信息Service
 * 
 * @author zhangzebiao
 * @date 2017年12月14日 下午5:32:27
 */
public interface BitMachineBaseInfoService {

	
	/**
	 * 比特机器基本信息分页
	 * @param page
	 * @param bitMachineBaseInfoVo
	 * @return
	 */
	public Page<BitMachineBaseInfoVo> getBitMachineBaseInfoVoPage(Page<BitMachineBaseInfoVo> page, BitMachineBaseInfoVo bitMachineBaseInfoVo);
	
	
}
