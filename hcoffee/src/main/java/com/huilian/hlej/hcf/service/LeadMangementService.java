package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.LeadManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 商机管理业务层
 * 
 * @author yangweichao
 * date: 2017-8-24 11:07:30
 */

public interface LeadMangementService {

	/**
	 * 查询表中所有数据
	 * 
	 * @param leadManagementVo
	 * @return
	 */
	public LeadManagementVo list(LeadManagementVo leadManagementVo);

	public Page<LeadManagementVo> getCommunityPage(Page<LeadManagementVo> page, LeadManagementVo leadManagementVo);

	public LeadManagementVo getLeadManagementById(LeadManagementVo leadManagementVo);

	public Map<String, String> updateLeadMangementById(LeadManagementVo leadManagementVo);

	public Page<LeadManagementVo> getLeadManagementList(Page<LeadManagementVo> page, LeadManagementVo leadManagementVo);
	
	/**
	 * 查询操作历史记录
	 * @return
	 */
	public List<LeadManagementVo> getOperationHistory(Integer id);

}
