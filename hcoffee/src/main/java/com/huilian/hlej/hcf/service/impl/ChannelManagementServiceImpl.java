package com.huilian.hlej.hcf.service.impl;

 
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.ChannelManagementDao;
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.service.ChannelManagementService;
import com.huilian.hlej.hcf.vo.ChannelVo;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author luozb
 *
 */
@Service
@Transactional
public class ChannelManagementServiceImpl implements ChannelManagementService{

	@Autowired
	private ChannelManagementDao channelManagementDao;

	@Override
	public Page<ChannelVo> getChannelManagementPage(Page<ChannelVo> page, ChannelVo channelVo) {
		channelVo.setPage(page);
		page.setList(channelManagementDao.getChannelManagementPage(channelVo));
		return page;
	}

	@Override
	public Map<String, String> saveChannel(ChannelVo channelVo) {
		Map<String, String> result = new HashMap<String, String>();
		channelManagementDao.saveChannel(channelVo);
		result.put("code", "0");
		result.put("msg", "保存成功");
		return result;
	}
	@Override
	public Map<String, String> updateChannelById(ChannelVo channelVo) {
		Map<String, String> result = new HashMap<String, String>();
		channelManagementDao.updateChannelById(channelVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
	}

	@Override
	public Map<String, String> updateChannelStatus(ChannelVo channelVo) {
		Map<String, String> result = new HashMap<String, String>();
		channelManagementDao.updateChannelStatus(channelVo);
		result.put("code", "0");
		result.put("msg", "删除成功");
		return result;
	}
	
	

	@Override
	public ChannelVo getChannel(ChannelVo channelVo) {
		return channelManagementDao.getChannel(channelVo);
	}

	@Override
	public ChannelVo getChannelById(ChannelVo channelVo) {
		return channelManagementDao.getChannelById(channelVo);
	}
	
	@Override
	public Page<ChannelVo> getChannelList(Page<ChannelVo> page, ChannelVo channelVo) {
		channelVo.setPage(page);
		page.setList(channelManagementDao.getChannelList(channelVo));
		return page;
	}
	
	@Override
	public ChannelVo getChannelNameByChannelId(ChannelVo channelVo) {
		
		 return channelManagementDao.getChannelNameByChannelId(channelVo);
	}
}
