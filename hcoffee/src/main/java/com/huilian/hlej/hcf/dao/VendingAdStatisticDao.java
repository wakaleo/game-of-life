package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.StatistTotelVo;
import com.huilian.hlej.hcf.vo.VendingAdStatisticVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VendingAdStatisticDao extends CrudDao<VendingConterAdVo> {

	List<VendingConterAdVo> getVendingAdStatisticPage(VendingConterAdVo vendingConterAdVo);

	List<VendingConterAdVo> getVendingConterAd();

	List<VendingAdStatisticVo> getVendByImgName(String imgName);

	List<StatistTotelVo> getChanelByimgName(String imgName);


	List<StatistTotelVo> getRewardRecordListPage(StatistTotelVo statistTotelVo);

	VendingStartVo getVendmotorByVendCode(String vendCode);

	StatistTotelVo getUpdateTime();

	List<VendingMachineVo> getVending();

	List<StatistTotelVo> getVendByAdId(String adId);

	
}
