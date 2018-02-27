package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendStoreDao;
import com.huilian.hlej.hcf.service.VendStoreService;
import com.huilian.hlej.hcf.vo.VendStoreVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author xiekangjian
 * @date 2016年12月30日 下午3:28:39
 *
 */
@Service
@Transactional
public class VendStoreServiceImpl implements VendStoreService{

	@Autowired
	private VendStoreDao vendStoreDao;

	@Override
	public Page<VendStoreVo> getVendStorePage(Page<VendStoreVo> page, VendStoreVo vendStoreVo) {
		vendStoreVo.setPage(page);
		page.setList(vendStoreDao.getVendStorePage(vendStoreVo));
		return page;
	}

	@Override
	public Map<String, String> deleteVendStore(String goodsID) {
		Map<String, String> result = new HashMap<String, String>();
		vendStoreDao.deleteVendStore(goodsID);
		 result.put("code", "0");
			result.put("msg", "删除成功");
			 return result;
	}

	@Override
	public VendStoreVo getVendStore(String id) {
		// TODO Auto-generated method stub
		return vendStoreDao.getVendStore(id);
	}

	@Override
	public Map<String, String> updateVendStore(VendStoreVo vendStoreVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendStoreDao.updateVendStore(vendStoreVo);
		 result.put("code", "0");
			result.put("msg", "更新成功");
			 return result;
	}

	@Override
	public Map<String, String> saveVendStore(VendStoreVo vendStoreVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendStoreDao.saveVendStore(vendStoreVo);
		 result.put("code", "0");
			result.put("msg", "保存成功");
			 return result;
	}

	@Override
	public VendStoreVo getStoreByVendCode(String vendCode) {
		// TODO Auto-generated method stub
		return vendStoreDao.getStoreByVendCode(vendCode);
	}
	
	

}
