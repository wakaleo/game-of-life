package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ActivityTemplateDao extends CrudDao<ActivityConterTemplateVo> {
	

	List<ActivityConterTemplateVo> getActivityTemplateList(ActivityConterTemplateVo anctivityTempleVo);

	ActivityManageVo getTemplateByid(String templateId);

	List<ActivityManageVo>  getActivityTemplateByCode(String schemeNo);

	AnctivityTempleVo getTemplateByTemplateName(String templateName);

	VendingMachineVo selectByTemplateId(String provinceId);

	AnctivityTempleVo getTemplateName(String templateId);

	void updateBySchemno(ActivityConterTemplateVo activityManageVo);

	void saveTemPlate(AnctivityTempleVo anctivityTempleVo);

	List<ActivityConterTemplateVo> getActivityManageByTemplateName(String templateId);

	void updateActivityManageVo(ActivityManageVo activityManageVo);

	ActivityManageVo seleByschemeNo(String str);

	void updateByTemplate(AnctivityTempleVo anctivityTempleVo);

	AnctivityTempleVo selectByoldTemplateId(String oldTemplateId);

	List<ActivityManageVo> selectActiveByTemplateId(String templateId);


	List<VendingMachineVo> selectVendByTemplateId(String templateId);

	void updateVendingByCode(VendingMachineVo activityManage);

	void saveActivityConterTemplate(ActivityConterTemplateVo activityConterTemplateVo);

	void deteTemplateByTemplateId(AnctivityTempleVo anctivityTempleVo);

	AnctivityTempleVo getTemplateByName(String templateName);

	void updateActivityConterManage(ActivityConterTemplateVo activityConterTemplateVo);

	void getActivityManageByTemplateNames(ActivityConterTemplateVo vendConterTemplateVo);

	

	
}
