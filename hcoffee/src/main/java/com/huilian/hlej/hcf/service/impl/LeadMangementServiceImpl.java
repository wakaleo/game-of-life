package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.LeadMangementDao;
import com.huilian.hlej.hcf.service.LeadMangementService;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.LeadManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 业务实现类
 * 
 * @author yangweichao
 * date: 2017-8-24 11:09:21
 */
@Service
@Transactional
public class LeadMangementServiceImpl implements LeadMangementService{
	
	@Autowired
	private LeadMangementDao leadMangementDao;

	@Override
	public LeadManagementVo list(LeadManagementVo leadManagementVo) {
		// TODO Auto-generated method stub
		
		return leadMangementDao.list(leadManagementVo);
	}


	public Page<LeadManagementVo> getCommunityPage(Page<LeadManagementVo> page, LeadManagementVo leadManagementVo) {
		leadManagementVo.setPage(page);
		page.setList(leadMangementDao.getCommunityPage(leadManagementVo));
		return page;
	}


	@Override
	public LeadManagementVo getLeadManagementById(LeadManagementVo leadManagementVo) {
		return leadMangementDao.getLeadManagementById(leadManagementVo);
	}


	@Override
	public Map<String, String> updateLeadMangementById(LeadManagementVo leadManagementVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			leadMangementDao.updateLeadMangementById(leadManagementVo);
			Integer state = leadManagementVo.getState();
			String remark = leadManagementVo.getRemark();
			StringBuffer operationAction = new StringBuffer();
			String stateStr = "";
			if(state != 0){
				stateStr = state == 1?"已跟进":"待跟进";
				operationAction.append("修改状态为: " + stateStr);
			}
			if(null != remark){
				operationAction.append(";添加备注: " + remark);
			}
			leadManagementVo.setOperationAction(operationAction.toString());
			leadManagementVo.setOperationPerson(UserUtils.getUser().getName());
			leadManagementVo.setLeadId(Integer.parseInt(leadManagementVo.getId()));
			leadMangementDao.addOperationHistory(leadManagementVo);
			result.put("code", "0");
			result.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "1");
			result.put("msg", "更新失败");
		}
		return result;
	}

/*
	@Override
	public Page<CommunityVo> getCommunityList(Page<CommunityVo> page, CommunityVo communityVo) {
		communityVo.setPage(page);
		page.setList(communityDao.getCommunityList(communityVo));
		return page;
	}
	
	*/
	/**
	 * 导出数据
	 */
	@Override
	public Page<LeadManagementVo> getLeadManagementList(Page<LeadManagementVo> page, LeadManagementVo leadManagementVo) {
		leadManagementVo.setPage(page);
		page.setList(leadMangementDao.getLeadManagementList(leadManagementVo));
		return page;
	
	}


	@Override
	public List<LeadManagementVo> getOperationHistory(Integer id) {
		List<LeadManagementVo> list = null;
		try {
			list = leadMangementDao.getOperationHistory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	

}
