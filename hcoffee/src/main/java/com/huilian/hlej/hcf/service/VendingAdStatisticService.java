package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.StatistTotelVo;
import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingAdStatisticVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingAdStatisticService {

	Page<VendingConterAdVo> getVendingAdStatisticPage(Page<VendingConterAdVo> page,
			VendingConterAdVo vendingConterAdVo);

	List<VendingConterAdVo> getVendingConterAd();

	List<VendingAdStatisticVo> getVendByImgName(String imgName);

	List<StatistTotelVo> getChanelByimgName(String adId);

	List<VendingMachineVo> getVending();


	VendingStartVo getVendmotorByVendCode(String vendCode);

	StatistTotelVo getUpdateTime();

	Page<StatistTotelVo> getRewardRecordListPage(Page<StatistTotelVo> page, StatistTotelVo statistTotelVo);

	List<StatistTotelVo> getVendByAdId(String adId);

	
}
