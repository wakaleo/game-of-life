package com.huilian.hlej.hcf.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.ActivityTemplateDao;
import com.huilian.hlej.hcf.service.ActivityTemplateService;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional

public class ActivityTemplateServiceImpl implements ActivityTemplateService {
	@Autowired
	private ActivityTemplateDao anctivityTempleDao;
	

	@Override
	public Page<ActivityConterTemplateVo> getActivityTemplateList(Page<ActivityConterTemplateVo> page,
			ActivityConterTemplateVo anctivityTempleVo) {
		anctivityTempleVo.setPage(page);
		page.setList(anctivityTempleDao.getActivityTemplateList(anctivityTempleVo));
		return page;
	}

	@Override
	public ActivityManageVo getTemplateByid(String templateId) {
		return anctivityTempleDao.getTemplateByid(templateId);
	}

	@Override
	public List<ActivityManageVo> getActivityTemplateByCode(String schemeNo) {
		return anctivityTempleDao.getActivityTemplateByCode(schemeNo);
	}

	@Override
	public AnctivityTempleVo getTemplateByTemplateName(String templateName) {
		return anctivityTempleDao.getTemplateByTemplateName(templateName);
	}

	@Override
	public Map<String, String> updateVendingByCode(VendingMachineVo activityManage) {
		
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.updateVendingByCode(activityManage);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
		
	}

	@Override
	public VendingMachineVo selectByTemplateId(String provinceId) {
		return anctivityTempleDao.selectByTemplateId(provinceId);
	}

	@Override
	public AnctivityTempleVo getTemplateName(String templateId) {
		return anctivityTempleDao.getTemplateName(templateId);
	}

	@Override
	public Map<String, String> updateBySchemno(ActivityConterTemplateVo activityManageVo) {
		
		
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.updateBySchemno(activityManageVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
		
	}

	@Override
	public Map<String, String> saveTemPlate(AnctivityTempleVo anctivityTempleVo ) {

		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.saveTemPlate(anctivityTempleVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
	}

	@Override
	public List<ActivityConterTemplateVo> getActivityManageByTemplateName(String templateId) {
		return anctivityTempleDao.getActivityManageByTemplateName(templateId);
	}

	@Override
	public Map<String, String> updateActivityManageVo(ActivityManageVo activityManageVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.updateActivityManageVo(activityManageVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		 return result;
	}

	@Override
	public ActivityManageVo seleByschemeNo(String str) {
		// TODO Auto-generated method stub
		return anctivityTempleDao.seleByschemeNo(str);
	}

	@Override
	public Map<String, String> updateByTemplate(AnctivityTempleVo anctivityTempleVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.updateByTemplate(anctivityTempleVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
	}

	@Override
	public AnctivityTempleVo selectByoldTemplateId(String oldTemplateId) {
		// TODO Auto-generated method stub
		return anctivityTempleDao.selectByoldTemplateId(oldTemplateId);
	}

	@Override
	public List<ActivityManageVo> selectActiveByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		return anctivityTempleDao.selectActiveByTemplateId(templateId);
	}

	

	@Override
	public List<VendingMachineVo> selectVendByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		return anctivityTempleDao.selectVendByTemplateId(templateId);
	}

	@Override
	public Map<String, String> saveActivityConterTemplate(ActivityConterTemplateVo activityConterTemplateVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.saveActivityConterTemplate(activityConterTemplateVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
	}

	@Override
	public Map<String, String> deteTemplateByTemplateId(AnctivityTempleVo anctivityTempleVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.deteTemplateByTemplateId(anctivityTempleVo);
		 result.put("code", "0");
			result.put("msg", "成功");
		return  result;
	}

	@Override
	public AnctivityTempleVo getTemplateByName(String templateName) {
		return anctivityTempleDao.getTemplateByName(templateName);
	}

	@Override
	public Map<String, String>  updateActivityConterManage(ActivityConterTemplateVo activityConterTemplateVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.updateActivityConterManage(activityConterTemplateVo);
		 result.put("code", "0");
			result.put("msg", "成功");
		return  result;
		
	}

	@Override
	public Map<String, String> getActivityManageByTemplateNames(ActivityConterTemplateVo vendConterTemplateVo) {
		Map<String, String> result = new HashMap<String, String>();
		anctivityTempleDao.getActivityManageByTemplateNames(vendConterTemplateVo);
		  result.put("code", "0");
			result.put("msg", "成功");
		
			 return result;
	}




	
}
