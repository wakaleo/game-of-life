package com.huilian.hlej.hcf.service;



import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityStatisVo;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.RewardRecordVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface ActivityStatisService {
	public Page<ActivityStatisVo> getActivityStatisPage(Page<ActivityStatisVo> page,ActivityStatisVo activityStatisVo);

	public Page<RewardRecordVo> getRewardRecordListPage(Page<RewardRecordVo> page,RewardRecordVo activityDetailsVo);

	public Map<String, String> closeAtivityManagement(ActivityManageVo activityStatisVo);

	public ActivityManageVo getActivityManagementCode(ActivityManageVo activityStatisVo);

	public Map<String, String> Update(ActivityStatisVo activityStatisVo);

	public Map<String, String> saveChannel(ActivityManageVo activityStatisVo);

	public ActivityManageVo getChannel(ActivityManageVo cu);
	

	public ActivityManageVo getChannelById(ActivityManageVo activityStatisVo);

	public Map<String, String> updatePriceById(ActivityManageVo activityStatisVo);

	public Page<ActivityManageVo> getActivityVoStatisPage(Page<ActivityManageVo> page,
			ActivityManageVo activityStatisVo);

	public void saveReward(ActivityStatisVo activityStatisVo);

	public Map<String, String> deleteActivity(ActivityConterTemplateVo activityManageVo);

	ActivityTypeVo getSelectImg(String activityType);


	public Map<String, String> deleteMachine(VendingMachineVo vendingMachineVo);

	public Map<String, String> deleteActivityById(ActivityManageVo activityManageVo);

	public ActivityManageVo getActivityManageBySchName(String schemeNo);

	public ActivityManageVo getActivityManageBySchNameNo(String schemeName);

	public List< ActivityConterTemplateVo> getActivityManageBy(String schemeNo);
}
