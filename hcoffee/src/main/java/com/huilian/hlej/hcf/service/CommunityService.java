package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface CommunityService {

	public   Page<CommunityVo> getCommunityPage(Page<CommunityVo> page, CommunityVo communityVo);
	public   Map<String,String> saveCommunity(CommunityVo communityVo);
	public   Map<String,String> updateCommunityById(CommunityVo communityVo);
	public   CommunityVo getCommunity(CommunityVo communityVo);
	public   CommunityVo getCommunityById(CommunityVo communityVo);
	public   Map<String,String> updateCommunityStatus(CommunityVo communityVo);
	public Page<CommunityVo> getCommunityList(Page<CommunityVo> page,CommunityVo communityVo);

}
