package com.huilian.hlej.hcf.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.LeadManagementVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 商机管理dao
 * 
 * @author yangweichao
 * date 2017-8-24 11:17:32
 */
@MyBatisDao
public interface LeadMangementDao extends CrudDao<LeadManagementVo>{

	/** 查询所有数据 */
	public LeadManagementVo list(LeadManagementVo leadManagementVo);

	public List<LeadManagementVo> getCommunityPage(LeadManagementVo leadManagementVo);

	public LeadManagementVo getLeadManagementById(LeadManagementVo leadManagementVo);

	/**
	 * 更新数据
	 * @param leadManagementVo
	 */
	public void updateLeadMangementById(LeadManagementVo leadManagementVo);

	/**
	 * 导出数据
	 * @param leadManagementVo
	 * @return
	 */
	public List<LeadManagementVo> getLeadManagementList(LeadManagementVo leadManagementVo);

	/**
	 * 查询操作历史记录
	 * @return
	 */
	public List<LeadManagementVo> getOperationHistory(Integer id);
	
	/**
	 *添加操作历史记录
	 * @return
	 */
	public void addOperationHistory(LeadManagementVo leadManagementVo);
}
