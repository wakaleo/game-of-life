package com.huilian.hlej.hcf.service.impl;

 
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.PayMentgerDao;
import com.huilian.hlej.hcf.service.PayMentgerService;
import com.huilian.hlej.hcf.vo.PayVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author luozb
 *
 */
@Service
@Transactional
public class PayMentgerServiceImpl implements PayMentgerService{

	@Autowired
	private PayMentgerDao payMentgerDao;

	@Override
	public Page<PayVo> getPayMentgerPage(Page<PayVo> page, PayVo payVo) {
		payVo.setPage(page);
		page.setList(payMentgerDao.getPayMentgerPage(payVo));
		return page;
	}

	@Override
	public Map<String, String> savePayMenter(PayVo payVo) {
		Map<String, String> result = new HashMap<String, String>();
		payMentgerDao.savePayMenter(payVo);
		result.put("code", "0");
		result.put("msg", "保存成功");
		return result;
	}
	
	@Override
	public PayVo getPayById(PayVo payVo) {
		// TODO Auto-generated method stub
		return payMentgerDao.getPayById(payVo);
	}

	@Override
	public Map<String,String> updatePayById(PayVo payVo) {
		Map<String, String> result = new HashMap<String, String>();
		 payMentgerDao.updatePayById(payVo);
		result.put("code", "0");
		result.put("msg", "更新成功");
		return result;
		
	}
	
	@Override
	public Map<String, String> updateChannelStatus(PayVo payVo) {
		Map<String, String> result = new HashMap<String, String>();
		payMentgerDao.updateChannelStatus(payVo);
		result.put("code", "0");
		result.put("msg", "删除成功");
		return result;
	}

	@Override
	public PayVo validateChannelName(PayVo payVo) {
		// TODO Auto-generated method stub
		return payMentgerDao.validateChannelName(payVo);
	}
	
}
