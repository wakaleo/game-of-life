package com.huilian.hlej.hcf.service.impl;

 
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.UploadAdDao;
import com.huilian.hlej.hcf.service.UploadAdService;
import com.huilian.hlej.hcf.vo.UploadAdVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author luozb
 *
 */
@Service
@Transactional
public class UploadAdServiceImpl implements UploadAdService{

	@Autowired
	private UploadAdDao uploadAdDao;

	@Override
	public Map<String, String> saveUploadAdVo(UploadAdVo uploadAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		uploadAdDao.saveUploadAd(uploadAdVo);
		result.put("code", "0");
		result.put("msg", "保存成功");
		return result;
	}
	
	@Override
	public Map<String, String> updateUploadAdById(UploadAdVo uploadAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		uploadAdDao.updateUploadAdById(uploadAdVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
	}

	@Override
	public Page<UploadAdVo> getUploadAdPage(Page<UploadAdVo> page, UploadAdVo uploadAdVo) {
		uploadAdVo.setPage(page);
		page.setList(uploadAdDao.selectUploadAdList(uploadAdVo));
		return page;
	}

	@Override
	public UploadAdVo getUploadAd(UploadAdVo uploadAdVo) {
		return uploadAdDao.getUploadAd(uploadAdVo);
	}

	@Override
	public Map<String,String> closeUploadAd(UploadAdVo uploadAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			uploadAdDao.updateUploadAd(uploadAdVo);
			result.put("code", "0");
			result.put("msg", "成功");
		}catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public Page<UploadAdVo> getUploadAdList(Page<UploadAdVo> page, UploadAdVo uploadAdVo) {
		uploadAdVo.setPage(page);
		page.setList(uploadAdDao.getUploadAdList(uploadAdVo));
		return page;
	}
	
	
}
