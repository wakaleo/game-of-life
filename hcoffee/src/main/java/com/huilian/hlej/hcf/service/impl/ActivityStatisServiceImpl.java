package com.huilian.hlej.hcf.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.ActivityStatisDao;
import com.huilian.hlej.hcf.service.ActivityStatisService;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityStatisVo;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.RewardRecordVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional

public class ActivityStatisServiceImpl implements ActivityStatisService {
	@Autowired
	private ActivityStatisDao activityStatisDao;
	
	@Override
	public Page<ActivityStatisVo> getActivityStatisPage(Page<ActivityStatisVo> page,ActivityStatisVo activityStatisVo) {
		activityStatisVo.setPage(page);
		page.setList(activityStatisDao.selectActivityStatis(activityStatisVo));
		return page;
	}


	@Override
	public Page<RewardRecordVo> getRewardRecordListPage(Page<RewardRecordVo> page,RewardRecordVo activityDetailsVo) {
		activityDetailsVo.setPage(page);
		page.setList(activityStatisDao.selectRewardRecordList(activityDetailsVo));
		
		return page;
	}

	
	
	@Override
	public Map<String, String> closeAtivityManagement(ActivityManageVo activityStatisVo) {
		Map<String, String> result = new HashMap<String, String>();
		try{
			activityStatisDao.updateAtivityManagement(activityStatisVo);
			result.put("code", "0");
			result.put("msg", "成功");
		}catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}


	@Override
	public ActivityManageVo getActivityManagementCode(ActivityManageVo activityStatisVo) {
		return activityStatisDao.getActivityManagementCode(activityStatisVo);
	}


	@Override
	public Map<String, String> Update(ActivityStatisVo activityStatisVo) {
		return activityStatisDao.getUpdateActivityManagement(activityStatisVo);
	}


	@Override
	public Map<String, String> saveChannel(ActivityManageVo activityStatisVo) {
		Map<String, String> result = new HashMap<String, String>();
		activityStatisDao.saveChannel(activityStatisVo);
		result.put("code", "0");
		result.put("msg", "保存成功");
		return result;
	}


	@Override
	public ActivityManageVo getChannel(ActivityManageVo cu) {
		return activityStatisDao.getChannel(cu);
	}


	@Override
	public ActivityManageVo getChannelById(ActivityManageVo activityStatisVo) {
		return activityStatisDao.getChannelById(activityStatisVo);
	}


	@Override
	public Map<String, String> updatePriceById(ActivityManageVo activityStatisVo) {
		Map<String, String> result = new HashMap<String, String>();
		activityStatisDao.updatePriceById(activityStatisVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
	}


	@Override
	public Page<ActivityManageVo> getActivityVoStatisPage(Page<ActivityManageVo> page,
			ActivityManageVo activityStatisVo) {
		activityStatisVo.setPage(page);
		page.setList(activityStatisDao.selectActivityVoPage(activityStatisVo));
		return page;
	}


	@Override
	public void saveReward(ActivityStatisVo activityStatisVo) {
		activityStatisDao.saveReward(activityStatisVo);
		
	}


	@Override
	public Map<String, String> deleteActivity(ActivityConterTemplateVo activityManageVo) {
		Map<String, String> result = new HashMap<String, String>();
		activityStatisDao.deleteActivity(activityManageVo);
		result.put("code", "0");
		result.put("msg", "删除成功");
		return result;
	}


	@Override
	public ActivityTypeVo getSelectImg(String activityType) {
		// TODO Auto-generated method stub
		return activityStatisDao.getSelectImg(activityType);
	}


	@Override
	public Map<String, String> deleteMachine(VendingMachineVo vendingMachineVo) {
		Map<String, String> result = new HashMap<String, String>();
		activityStatisDao.deleteMachine(vendingMachineVo);
		result.put("code", "0");
		result.put("msg", "清空模板成功");
		return result;
	}


	@Override
	public Map<String, String> deleteActivityById(ActivityManageVo activityManageVo) {
		Map<String, String> result = new HashMap<String, String>();
		activityStatisDao.deleteActivityById(activityManageVo);
		result.put("code", "0");
		result.put("msg", "删除成功");
		return result;
	}


	@Override
	public ActivityManageVo getActivityManageBySchName(String schemeNo) {
		// TODO Auto-generated method stub
		return activityStatisDao.getActivityManageBySchName(schemeNo);
	}


	@Override
	public ActivityManageVo getActivityManageBySchNameNo(String schemeName) {
		// TODO Auto-generated method stub
		return activityStatisDao.getActivityManageBySchNameNo(schemeName);
	}


	@Override
	public List< ActivityConterTemplateVo> getActivityManageBy(String schemeNo) {
		// TODO Auto-generated method stub
		return activityStatisDao.getActivityManageBy(schemeNo);
	}
}
