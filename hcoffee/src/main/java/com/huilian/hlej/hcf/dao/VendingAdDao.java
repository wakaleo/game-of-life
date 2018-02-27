package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.vo.VendingAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VendingAdDao {
	List<VendingReleVo> selectVendAdList(VendingReleVo vendingReleVo);

	List<VendingReleVo> getVendingAdByCode(String vendCode);

	void addVendingAd(VendingAdVo vendingAdVo);

	void updateVendAdByCode(VendingReleVo vendingReleVo);
	
	void updateVendingAdById(VendingAdVo vendingAdVo);

	VendingMachine getChanelComtityByCode(String vendCode);
}
