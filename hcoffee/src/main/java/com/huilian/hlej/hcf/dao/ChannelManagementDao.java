package com.huilian.hlej.hcf.dao;

import java.math.BigDecimal;
import java.util.List;

import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 渠道dao
 * @author yangbo
 * @version 2017/03/1
 */
@MyBatisDao
public interface ChannelManagementDao extends CrudDao<ChannelVo> {
	public   List<ChannelVo> getChannelManagementPage(ChannelVo channelVo);
	public   int saveChannel(ChannelVo channelVo);
	public   void updateChannelById(ChannelVo channelVo);
	public   void updateChannelStatus(ChannelVo channelVo);
	public   ChannelVo getChannel(ChannelVo channelVo);
	public   ChannelVo getChannelById(ChannelVo channelVo);
	public   void deleteChannel(ChannelVo channelVo);
	public List<ChannelVo> getChannelList(ChannelVo communityVo);
	public ChannelVo getChannelNameByChannelId(ChannelVo channelVo);

}
