package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.VendStoreVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VendStoreDao extends CrudDao<VendStoreVo> {

	List<VendStoreVo> getVendStorePage(VendStoreVo vendStoreVo);

	void deleteVendStore(String goodsID);

	VendStoreVo getVendStore(String id);

	void updateVendStore(VendStoreVo vendStoreVo);

	void saveVendStore(VendStoreVo vendStoreVo);

	VendStoreVo getStoreByVendCode(String vendCode);
	
}
