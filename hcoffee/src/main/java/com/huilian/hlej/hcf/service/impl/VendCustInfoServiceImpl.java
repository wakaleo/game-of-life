package com.huilian.hlej.hcf.service.impl;

 
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendCustInfo;
import com.huilian.hlej.hcf.service.VendCustInfoService;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.VendCustInfoVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author liujian
 *
 */
@Service
@Transactional
public class VendCustInfoServiceImpl implements VendCustInfoService{

	@Autowired
	private VendCustInfo vendCustInfoDao;
	



	@Override
	public Page<VendCustInfoVo> getVendCustQuery(Page<VendCustInfoVo> page, VendCustInfoVo vendCustInfo) {
		vendCustInfo.setPage(page);
		
		page.setList(vendCustInfoDao.getVendCustQuery(vendCustInfo));
		return page;
	}


	@Override
	public List<CommunityVo> getCommunityName() {
		// TODO Auto-generated method stub
		return vendCustInfoDao.getCommunityName();
	}
}



