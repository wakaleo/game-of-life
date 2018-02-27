package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendingAdDao;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.service.VendingAdService;
import com.huilian.hlej.hcf.vo.VendingAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class VendingAdServiceImpl implements VendingAdService {
	@Autowired
	private VendingAdDao vendingAdVoDao;
	
	
	@Override
	public Page<VendingReleVo> getVendingAdPage(Page<VendingReleVo> page, VendingReleVo vendingReleVo) {
		vendingReleVo.setPage(page);
		page.setList(vendingAdVoDao.selectVendAdList(vendingReleVo));
		return page;
	}


    
	@Transactional(readOnly = false)
	public Map<String, String> addVendingAd(VendingAdVo vendingAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			vendingAdVoDao.addVendingAd(vendingAdVo);
			result.put("code", "0");
			result.put("msg", "保存成功");
		}catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
		
	}

	@Override
	public Map<String, String> closeVendingAdByCode(VendingReleVo vendingReleVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			vendingAdVoDao.updateVendAdByCode(vendingReleVo);
			result.put("code", "0");
			result.put("msg", "成功");
		}catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	/*@Override
	@Transactional(readOnly = false)
	public Map<String, String> batchUpdate(VendingAdVo vendingAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			if(vendingAdVo!=null ){
			    String[] strs = vendingAdVo.getBatchVendCode().split(",");
			    for (String vendCode : strs) {
			    	vendingAdVo.setVendCode(vendCode);
			    	vendingAdVoDao.updateVendAdByCode(vendingAdVo);
			    }
			}
			result.put("code", "0");
			result.put("msg", "操作成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public Map<String, String> updateVendingAdById(VendingAdVo vendingAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			
			vendingAdVoDao.updateVendingAdById(vendingAdVo);
			result.put("code", "0");
			result.put("msg", "操作成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}*/

	@Override
	public VendingMachine getChanelComtityByCode(String vendCode) {
		return vendingAdVoDao.getChanelComtityByCode(vendCode);
	}



	@Override
	public List<VendingReleVo> getVendingAdByCode(String vendCode) {
	
			 List<VendingReleVo> vendingAdByCode = vendingAdVoDao.getVendingAdByCode(vendCode);
			return vendingAdByCode;
			 
		}
}
