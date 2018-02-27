package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.vo.VendingAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingAdService {

	Page<VendingReleVo> getVendingAdPage(Page<VendingReleVo> page, VendingReleVo vendingReleVo);

	List<VendingReleVo> getVendingAdByCode(String vendCode);

	Map<String, String> addVendingAd(VendingAdVo vendingAdVo);

	Map<String, String> closeVendingAdByCode(VendingReleVo vendingReleVo);

	//Map<String, String> batchUpdate(VendingAdVo vendingAdVo);
	
	//Map<String, String> updateVendingAdById(VendingAdVo vendingAdVo);

	VendingMachine getChanelComtityByCode(String vendCode);
	
}
