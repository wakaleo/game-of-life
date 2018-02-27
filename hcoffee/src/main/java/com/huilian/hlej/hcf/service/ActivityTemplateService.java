package com.huilian.hlej.hcf.service;



import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.AnctivityTempleVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.ActivityConterTemplateVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface ActivityTemplateService {

	Page<ActivityConterTemplateVo> getActivityTemplateList(Page<ActivityConterTemplateVo> page, ActivityConterTemplateVo anctivityTempleVo);

	ActivityManageVo getTemplateByid(String templateId);

	List<ActivityManageVo>  getActivityTemplateByCode(String schemeNo);

	AnctivityTempleVo getTemplateByTemplateName(String templateName);

	Map<String, String> updateVendingByCode(VendingMachineVo activityManage);
	

	public VendingMachineVo selectByTemplateId(String provinceId);

	AnctivityTempleVo getTemplateName(String templateId);

	Map<String, String> updateBySchemno(ActivityConterTemplateVo activityManageVo);

	Map<String, String> saveTemPlate(AnctivityTempleVo anctivityTempleVo);

	List<ActivityConterTemplateVo> getActivityManageByTemplateName(String templateId);

	Map<String, String> updateActivityManageVo(ActivityManageVo activityManageVo);

	ActivityManageVo seleByschemeNo(String string);

	Map<String, String> updateByTemplate(AnctivityTempleVo anctivityTempleVo);

	AnctivityTempleVo selectByoldTemplateId(String oldTemplateId);

	List<ActivityManageVo> selectActiveByTemplateId(String templateId);


	List<VendingMachineVo> selectVendByTemplateId(String templateId);

	Map<String, String> saveActivityConterTemplate(ActivityConterTemplateVo activityConterTemplateVo);

	Map<String, String> deteTemplateByTemplateId(AnctivityTempleVo anctivityTempleVo);

	AnctivityTempleVo getTemplateByName(String templateName);

	Map<String, String>  updateActivityConterManage(ActivityConterTemplateVo activityConterTemplateVo);

	Map<String, String> getActivityManageByTemplateNames(ActivityConterTemplateVo vendConterTemplateVo);





	
}
