package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.PayVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface PayMentgerService {

	Page<PayVo> getPayMentgerPage(Page<PayVo> page, PayVo payVo);
	public Map<String, String> savePayMenter(PayVo payVo);
	public   Map<String,String> updatePayById(PayVo payVo);
	public   Map<String,String> updateChannelStatus(PayVo payVo);
	/*public   Map<String,String> saveChannel(ChannelVo channelVo);
	
	public   ChannelVo getChannel(ChannelVo channelVo);
	public   ChannelVo getChannelById(ChannelVo channelVo);
	
	public  Page<ChannelVo> getChannelList(Page<ChannelVo> page,ChannelVo channelVo);*/
	PayVo getPayById(PayVo payVo);
	PayVo validateChannelName(PayVo payVo);


}
