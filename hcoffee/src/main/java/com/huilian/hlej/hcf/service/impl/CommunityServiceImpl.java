package com.huilian.hlej.hcf.service.impl;

 
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.CommunityDao;
import com.huilian.hlej.hcf.service.CommunityService;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author luozb
 *
 */
@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{

	@Autowired
	private CommunityDao communityDao;

	@Override
	public Page<CommunityVo> getCommunityPage(Page<CommunityVo> page, CommunityVo communityVo) {
		communityVo.setPage(page);
		page.setList(communityDao.getCommunityPage(communityVo));
		return page;
	}

	@Override
	public Map<String, String> saveCommunity(CommunityVo communityVo) {
		Map<String, String> result = new HashMap<String, String>();
		communityDao.saveCommunity(communityVo);
		result.put("code", "0");
		result.put("msg", "保存成功");
		return result;
	}

	@Override
	public Map<String, String> updateCommunityById(CommunityVo communityVo) {
		Map<String, String> result = new HashMap<String, String>();
		communityDao.updateCommunityById(communityVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
	}

	@Override
	public Map<String, String> updateCommunityStatus(CommunityVo communityVo) {
		Map<String, String> result = new HashMap<String, String>();
		communityDao.updateCommunityStatus(communityVo);
		result.put("code", "0");
		result.put("msg", "删除成功");
		return result;
	}
	
	

	@Override
	public CommunityVo getCommunity(CommunityVo communityVo) {
		return communityDao.getCommunity(communityVo);
	}

	@Override
	public CommunityVo getCommunityById(CommunityVo communityVo) {
		return communityDao.getCommunityById(communityVo);
	}

	@Override
	public Page<CommunityVo> getCommunityList(Page<CommunityVo> page, CommunityVo communityVo) {
		communityVo.setPage(page);
		page.setList(communityDao.getCommunityList(communityVo));
		return page;
	}
	
}
