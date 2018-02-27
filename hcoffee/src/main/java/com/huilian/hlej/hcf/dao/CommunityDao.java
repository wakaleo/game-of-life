package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 社区dao
 * @author luozb
 * @version 2017/02/27
 */
@MyBatisDao
public interface CommunityDao extends CrudDao<CommunityVo> {
	public   List<CommunityVo> getCommunityPage(CommunityVo communityVo);
	public   int saveCommunity(CommunityVo communityVo);
	public   void updateCommunityById(CommunityVo communityVo);
	public   void updateCommunityStatus(CommunityVo communityVo);
	public   CommunityVo getCommunity(CommunityVo communityVo);
	public   CommunityVo getCommunityById(CommunityVo communityVo);
	public   void deleteCommunity(CommunityVo communityVo);
	public List<CommunityVo> getCommunityList(CommunityVo communityVo);
}
