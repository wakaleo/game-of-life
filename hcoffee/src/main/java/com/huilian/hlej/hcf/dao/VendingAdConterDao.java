package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface VendingAdConterDao extends CrudDao<VendingConterAdVo> {

	List<VendingConterAdVo> selectVendAdList(VendingConterAdVo vendingConterAdVo);

	VendingConterAdVo getVendingConterAdByCode(String adId);

	void updateVendingConterAd(VendingConterAdVo vendingConterAdVo);

	void deleteVendingAdByCode(VendingConterAdVo vendingConterAdVo);

	void addVendingConterAd(VendingConterAdVo vendingConterAdVo);

	void updateByAdId(VendingConterAdVo vendingConterAdVo);

	void saveVendingReleAd(VendingReleVo vendingReleVo);

	VendingConterAdVo getVendAdByADId(String adId);

	List<VendingReleVo> getVendReleByVendCode(String vendCode);

	void updateVendingRele(VendingReleVo vendingReleVo);

	VendingConterAdVo getVendingConterAdByadId(String imgName);

	void addVendingStatistAd(VendingConterAdVo sd);


	void deleteVendingAdByVendCode(StatistVo stra);

	List<StatistVo> getVendListConterAdByCode(String stra);

	void deletevendingReleByCode(VendingReleVo vendingReleVo);

	void updateSortByvendCode(StatistVo statistVo);

	List<VendingConterAdVo> queryAllVendCodeList();

	Map<String, Object> queryParamMap(Map<String, Object> param);

	void updateVendingReleAd(VendingReleVo vendingReleVo);

	String queryBeforAdList(String vendCode);

	int isExist(Map<String, String> param);
	
}
