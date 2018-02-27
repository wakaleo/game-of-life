package com.huilian.hlej.hcf.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface ChannelManagementService {

	Page<ChannelVo> getChannelManagementPage(Page<ChannelVo> page, ChannelVo channelVo);
	public   Map<String,String> saveChannel(ChannelVo channelVo);
	public   Map<String,String> updateChannelById(ChannelVo channelVo);
	public   ChannelVo getChannel(ChannelVo channelVo);
	public   ChannelVo getChannelById(ChannelVo channelVo);
	public   Map<String,String> updateChannelStatus(ChannelVo channelVo);
	public  Page<ChannelVo> getChannelList(Page<ChannelVo> page,ChannelVo channelVo);
	public   ChannelVo getChannelNameByChannelId(ChannelVo channelVo);
	


}
