package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.BitMachineAisleInfoDao;
import com.huilian.hlej.hcf.service.BitMachineAisleInfoService;
import com.huilian.hlej.hcf.util.DoubleUtils;
import com.huilian.hlej.hcf.vo.BitMachineAisleInfoVo;
import com.huilian.hlej.hcf.vo.BitMachineBaseInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 比特机器货道信息Service实现类
 * @author zhangzebiao
 * @date 2017年12月14日 下午5:32:27
 */
@Service
@Transactional
public class BitMachineAisleInfoServiceImpl implements BitMachineAisleInfoService {

	
	@Autowired
	private BitMachineAisleInfoDao bitMachineAisleInfoDao;
	
	


	@Override
	public Page<BitMachineAisleInfoVo> getBitMachineAisleInfoVoPage(Page<BitMachineAisleInfoVo> page, BitMachineAisleInfoVo bitMachineAisleInfoVo) {
		bitMachineAisleInfoVo.setPage(page);
		List<BitMachineAisleInfoVo> findBitMachineAisleInfoVoList = bitMachineAisleInfoDao.findBitMachineAisleInfoVoList(bitMachineAisleInfoVo);
		page.setList(findBitMachineAisleInfoVoList);
		return page;
	}




	@Override
	public BitMachineBaseInfoVo getBitMachineAisleCountVo(BitMachineAisleInfoVo bitMachineAisleInfoVo) {
		BitMachineBaseInfoVo bitMachineAisleCountVo = bitMachineAisleInfoDao.getBitMachineAisleCountVo(bitMachineAisleInfoVo);
		//缺货率=缺货商品数/该机器总库存容量*100%
		Integer zkcrl = bitMachineAisleCountVo.getZKCRL();
		Integer qhsps = bitMachineAisleCountVo.getQHSPS();
		bitMachineAisleCountVo.setQHL(null==zkcrl||zkcrl==0?0.0:DoubleUtils.div(qhsps*100, zkcrl, 2));
		//断货率=断货货道数/该机器总货道数*100%
		Integer zhds = bitMachineAisleCountVo.getZHDS();
		Integer dhhds = bitMachineAisleCountVo.getDHHDS();
		bitMachineAisleCountVo.setDHL(zhds==zkcrl||zhds==0?0.0:DoubleUtils.div(dhhds*100, zhds, 2));
		return bitMachineAisleCountVo;
		
		
	}








}
