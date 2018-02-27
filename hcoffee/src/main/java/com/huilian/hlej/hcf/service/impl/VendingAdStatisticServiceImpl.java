package com.huilian.hlej.hcf.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.VendingAdStatisticDao;
import com.huilian.hlej.hcf.service.VendingAdStatisticService;
import com.huilian.hlej.hcf.vo.StatistTotelVo;
import com.huilian.hlej.hcf.vo.VendingAdStatisticVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional

public class VendingAdStatisticServiceImpl implements VendingAdStatisticService {
	@Autowired
	private VendingAdStatisticDao vendingAdStatisticDao;

	@Override
	public Page<VendingConterAdVo> getVendingAdStatisticPage(Page<VendingConterAdVo> page,
			VendingConterAdVo vendingConterAdVo) {
		vendingConterAdVo.setPage(page);
		page.setList(vendingAdStatisticDao.getVendingAdStatisticPage(vendingConterAdVo));
		return page;
	}

	@Override
	public List<VendingConterAdVo> getVendingConterAd() {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getVendingConterAd();
	}

	@Override
	public List<VendingAdStatisticVo> getVendByImgName(String imgName) {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getVendByImgName(imgName);
	}

	@Override
	public List<StatistTotelVo> getChanelByimgName(String imgName) {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getChanelByimgName(imgName);
	}

	@Override
	public List<VendingMachineVo> getVending() {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getVending();
	}

	@Override
	public Page<StatistTotelVo> getRewardRecordListPage(Page<StatistTotelVo> page, StatistTotelVo statistTotelVo) {
		statistTotelVo.setPage(page);
		page.setList(vendingAdStatisticDao.getRewardRecordListPage(statistTotelVo));
		return page;
	}

	@Override
	public VendingStartVo getVendmotorByVendCode(String vendCode) {
		
		return vendingAdStatisticDao.getVendmotorByVendCode(vendCode);
	}

	@Override
	public StatistTotelVo getUpdateTime() {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getUpdateTime();
	}

	@Override
	public List<StatistTotelVo> getVendByAdId(String adId) {
		// TODO Auto-generated method stub
		return vendingAdStatisticDao.getVendByAdId(adId);
	}

	
}
