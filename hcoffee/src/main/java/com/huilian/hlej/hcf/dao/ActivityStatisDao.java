package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityStatisVo;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.RewardRecordVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ActivityStatisDao extends CrudDao<ActivityStatisVo> {
	List<ActivityStatisVo> selectActivityStatis(ActivityStatisVo ActivityStatisVo);

	List<RewardRecordVo> getActivityDetailsList(String schemeNo, String usrChannel);

	List<RewardRecordVo> selectRewardRecordList(RewardRecordVo activityDetailsVo);

	void updateAtivityManagement(ActivityManageVo activityStatisVo);

	ActivityManageVo getActivityManagementCode(ActivityManageVo activityStatisVo);

	Map<String, String> getUpdateActivityManagement(ActivityStatisVo activityStatisVo);

	void saveChannel(ActivityManageVo activityStatisVo);
	

	ActivityManageVo getChannel(ActivityManageVo cu);

	ActivityManageVo getChannelById(ActivityManageVo activityStatisVo);

	void updatePriceById(ActivityManageVo activityStatisVo);

	List<ActivityManageVo> selectActivityVoPage(ActivityManageVo activityStatisVo);

	void saveReward(ActivityStatisVo activityStatisVo);

	public void deleteActivity(ActivityConterTemplateVo activityManageVo);

	ActivityTypeVo getSelectImg(String activityType);


	public void deleteMachine(VendingMachineVo vendingMachineVo);

	void deleteActivityById(ActivityManageVo activityManageVo);

	ActivityManageVo getActivityManageBySchName(String schemeNo);

	ActivityManageVo getActivityManageBySchNameNo(String schemeName);

	List< ActivityConterTemplateVo> getActivityManageBy(String schemeNo);
}
