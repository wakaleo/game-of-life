package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.CoffeeActivityInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentInfoVo;
import com.huilian.hlej.hcf.vo.EquipmentMateriaVo;
import com.huilian.hlej.hcf.vo.EquipmentTradeVo;
import com.huilian.hlej.hcf.vo.EquipmentWarnVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface EquipmentDao {

	/**
	 * 设备信息查询
	 * @param equipmentInfoVo
	 * @return
	 */
	List<EquipmentInfoVo> findEquipmentInfoVoList(EquipmentInfoVo equipmentInfoVo);

	/**
	 * 设备交易查询
	 * @param equipmentTradeVo
	 * @return
	 */
	List<EquipmentTradeVo> findEquipmentTradeVoList(EquipmentTradeVo equipmentTradeVo);

	/**
	 * 设备报警查询
	 * @param equipmentWarnVo
	 * @return
	 */
	List<EquipmentWarnVo> findEquipmentWarnVoList(EquipmentWarnVo equipmentWarnVo);

	/**
	 * 查询设备物料
	 * @param equipmentMateriaVo
	 * @return
	 */
	List<EquipmentMateriaVo> findEquipmentMateriaVoList(EquipmentMateriaVo equipmentMateriaVo);
	
	/**
	 * 查询机器的故障历史信息
	 * @param equipmentWarnVo
	 * @return
	 */
	List<EquipmentWarnVo> findHisWarnList(EquipmentWarnVo equipmentWarnVo);

	/**
	 * 咖啡机活动查询
	 * @param coffeeActivityInfoVo
	 * @return
	 */
	List<CoffeeActivityInfoVo> findCoffeeActivityInfoList(CoffeeActivityInfoVo coffeeActivityInfoVo);

}
