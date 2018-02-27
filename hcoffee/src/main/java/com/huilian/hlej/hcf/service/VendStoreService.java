package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.hcf.vo.VendStoreVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendStoreService {

	Page<VendStoreVo> getVendStorePage(Page<VendStoreVo> page, VendStoreVo vendStoreVo);

	Map<String, String> deleteVendStore(String goodsID);

	VendStoreVo getVendStore(String id);

	Map<String, String> updateVendStore(VendStoreVo vendStoreVo);

	Map<String, String> saveVendStore(VendStoreVo vendStoreVo);

	VendStoreVo getStoreByVendCode(String vendCode);

	

}
