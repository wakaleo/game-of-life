package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.BitMachineBaseInfoDao;
import com.huilian.hlej.hcf.dao.LottoActivityDao;
import com.huilian.hlej.hcf.service.BitMachineBaseInfoService;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 比特机器基本信息Service实现类
 * @author zhangzebiao
 * @date 2017年12月14日 下午5:32:27
 */
@Service
@Transactional
public class BitMachineBaseInfoServiceImpl implements BitMachineBaseInfoService {

	
	@Autowired
	private BitMachineBaseInfoDao bitMachineBaseInfoDao;
	
	


	@Override
	public Page<BitMachineBaseInfoVo> getBitMachineBaseInfoVoPage(Page<BitMachineBaseInfoVo> page, BitMachineBaseInfoVo bitMachineBaseInfoVo) {
		//List<BitMachineBaseInfoVo> list=bitMachineBaseInfoDao.findIsOutOfStock();
		bitMachineBaseInfoVo.setPage(page);
		List<BitMachineBaseInfoVo> findBitMachineBaseInfoVoList = bitMachineBaseInfoDao.findBitMachineBaseInfoVoList(bitMachineBaseInfoVo);
		page.setList(findBitMachineBaseInfoVoList);
		return page;
	}








}
