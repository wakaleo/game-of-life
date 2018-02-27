package com.huilian.hlej.hcf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.EquipmentDao;
import com.huilian.hlej.hcf.service.EquipmentService;
import com.huilian.hlej.hcf.vo.CoffeeActivityInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentMateriaVo;
import com.huilian.hlej.hcf.vo.EquipmentTradeVo;
import com.huilian.hlej.hcf.vo.EquipmentWarnVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 咖啡机Service实现类
 * @author LongZhangWei
 * @date 2017年12月25日 上午11:30:09
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentDao equipmentDao;
	
	@Override
	public Page<EquipmentInfoVo> getEquipmentInfoVoPage(Page<EquipmentInfoVo> page,
			EquipmentInfoVo equipmentInfoVo) {
		equipmentInfoVo.setPage(page);
		page.setList(equipmentDao.findEquipmentInfoVoList(equipmentInfoVo));
		return page;
	}

	@Override
	public Page<EquipmentTradeVo> getEquipmentTradeVo(Page<EquipmentTradeVo> page, EquipmentTradeVo equipmentTradeVo) {
		equipmentTradeVo.setPage(page);
		page.setList(equipmentDao.findEquipmentTradeVoList(equipmentTradeVo));
		return page;
	}

	@Override
	public Page<EquipmentWarnVo> getEquipmentWarnVoPage(Page<EquipmentWarnVo> page, EquipmentWarnVo equipmentWarnVo) {
		equipmentWarnVo.setPage(page);
		page.setList(equipmentDao.findEquipmentWarnVoList(equipmentWarnVo));
		return page;
	}

	@Override
	public Page<EquipmentMateriaVo> getEquipmentMateriaVoPage(Page<EquipmentMateriaVo> page,
			EquipmentMateriaVo equipmentMateriaVo) {
		equipmentMateriaVo.setPage(page);
		page.setList(equipmentDao.findEquipmentMateriaVoList(equipmentMateriaVo));
		return page;
	}

	@Override
	public Page<EquipmentWarnVo> getEquipmentHisWarnVoPage(Page<EquipmentWarnVo> page,
			EquipmentWarnVo equipmentWarnVo) {
		equipmentWarnVo.setPage(page);
		page.setList(equipmentDao.findHisWarnList(equipmentWarnVo));
		return page;
	}

	@Override
	public Page<CoffeeActivityInfoVo> getCoffeeActivityInfoVo(Page<CoffeeActivityInfoVo> page,
			CoffeeActivityInfoVo coffeeActivityInfoVo) {
		coffeeActivityInfoVo.setPage(page);
		page.setList(equipmentDao.findCoffeeActivityInfoList(coffeeActivityInfoVo));
		return page;
	}

}
