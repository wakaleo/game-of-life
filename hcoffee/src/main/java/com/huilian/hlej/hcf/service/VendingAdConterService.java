package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingAdConterService {

	Page<com.huilian.hlej.hcf.vo.VendingConterAdVo> getVendingConterAdPage(Page<VendingConterAdVo> page,
			VendingConterAdVo vendingConterAdVo);

           VendingConterAdVo getVendingConterAdByCode(String vendCode);

		Map<String, String> updateVendingConterAd(VendingConterAdVo vendingConterAdVo);

		Map<String, String> deleteVendingAdByCode(VendingConterAdVo vendingConterAdVo);

		Map<String, String> addVendingConterAd(VendingConterAdVo vendingConterAdVo);

		Map<String, String> updateByAdId(VendingConterAdVo vendingConterAdVo);

		Map<String, String> saveVendingReleAd(VendingReleVo vendingReleVo);

		VendingConterAdVo getVendAdByADId(String adId);

		List<VendingReleVo> getVendReleByVendCode(String vendCode);

		Map<String, String> updateVendingRele(VendingReleVo vendingReleVo);

		VendingConterAdVo getVendingConterAdByadId(String imgName);

		void addVendingStatistAd(VendingConterAdVo sd);

		List<StatistVo> getVendListConterAdByCode(String stra);

		void deleteVendingAdByVendCode(StatistVo statis);

		void deletevendingReleByCode(VendingReleVo vendingReleVo);

		Map<String, String>  updateSortByvendCode(StatistVo statistVo);

		String queryAllVendCode();

		Map<String, Object> queryParamMap(String adId);

		Map<String, String> updateVendingReleAd(VendingReleVo vendingReleVo);

		String queryBeforAdList(String vendCode);

		boolean isExist(Map<String, String> param);


}
