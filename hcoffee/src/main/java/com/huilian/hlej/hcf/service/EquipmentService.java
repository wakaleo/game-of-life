package com.huilian.hlej.hcf.service;

import com.huilian.hlej.hcf.vo.CoffeeActivityInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentMateriaVo;
import com.huilian.hlej.hcf.vo.EquipmentTradeVo;
import com.huilian.hlej.hcf.vo.EquipmentWarnVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 咖啡机Service
 * @author LongZhangWei
 * @date 2017年12月25日 上午11:30:36
 */
public interface EquipmentService {

	/**
	 * 设备信息查询
	 * @param page
	 * @param equipmentInfoVo
	 * @return
	 */
	Page<EquipmentInfoVo> getEquipmentInfoVoPage(Page<EquipmentInfoVo> page, EquipmentInfoVo equipmentInfoVo);

	/**
	 * 设备交易查询
	 * @param page
	 * @param equipmentTradeVo
	 * @return
	 */
	Page<EquipmentTradeVo> getEquipmentTradeVo(Page<EquipmentTradeVo> page, EquipmentTradeVo equipmentTradeVo);

	/**
	 * 设备报警查询
	 * @param page
	 * @param equipmentWarnVo
	 * @return
	 */
	Page<EquipmentWarnVo> getEquipmentWarnVoPage(Page<EquipmentWarnVo> page, EquipmentWarnVo equipmentWarnVo);

	/**
	 * 查询设备物料
	 * @param page
	 * @param equipmentMateriaVo
	 * @return
	 */
	Page<EquipmentMateriaVo> getEquipmentMateriaVoPage(Page<EquipmentMateriaVo> page,EquipmentMateriaVo equipmentMateriaVo);

	/**
	 * 查询机器故障历史信息
	 * @param page
	 * @param equipmentWarnVo
	 * @return
	 */
	Page<EquipmentWarnVo> getEquipmentHisWarnVoPage(Page<EquipmentWarnVo> page, EquipmentWarnVo equipmentWarnVo);

	/**
	 * 咖啡机活动查询
	 * @param page
	 * @param coffeeActivityInfoVo
	 * @return
	 */
	Page<CoffeeActivityInfoVo> getCoffeeActivityInfoVo(Page<CoffeeActivityInfoVo> page,
			CoffeeActivityInfoVo coffeeActivityInfoVo);

}
