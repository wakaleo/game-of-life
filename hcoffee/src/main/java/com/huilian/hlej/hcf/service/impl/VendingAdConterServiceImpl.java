package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.hpl.sparta.xpath.TrueExpr;
import com.huilian.hlej.hcf.dao.VendingAdConterDao;
import com.huilian.hlej.hcf.service.VendingAdConterService;
import com.huilian.hlej.hcf.vo.StatistVo;
import com.huilian.hlej.hcf.vo.VendingConterAdVo;
import com.huilian.hlej.hcf.vo.VendingReleVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional

public class VendingAdConterServiceImpl implements VendingAdConterService {
	@Autowired
	private VendingAdConterDao vendingAdConterDao;

	@Override
	public Page<VendingConterAdVo> getVendingConterAdPage(Page<VendingConterAdVo> page,
			VendingConterAdVo vendingConterAdVo) {
		// vendingConterAdVo.setVendCode(vendingConterAdVo.getVendCode()!=null ?
		// MethodUtil.getFormatStr(vendingConterAdVo.getVendCode()) : null);
		page.setPageSize(50);
		vendingConterAdVo.setPage(page);
		page.setList(vendingAdConterDao.selectVendAdList(vendingConterAdVo));
		return page;
	}

	@Override
	public VendingConterAdVo getVendingConterAdByCode(String adId) {
		// TODO Auto-generated method stub
		return vendingAdConterDao.getVendingConterAdByCode(adId);
	}

	@Override
	public Map<String, String> updateVendingConterAd(VendingConterAdVo vendingConterAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.updateVendingConterAd(vendingConterAdVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public Map<String, String> deleteVendingAdByCode(VendingConterAdVo vendingConterAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.deleteVendingAdByCode(vendingConterAdVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public Map<String, String> addVendingConterAd(VendingConterAdVo vendingConterAdVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.addVendingConterAd(vendingConterAdVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public Map<String, String> updateByAdId(VendingConterAdVo vendingConterAdVo) {

		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.updateByAdId(vendingConterAdVo);
		result.put("code", "0");
		result.put("msg", "成功");

		return result;
	}

	@Override
	public Map<String, String> saveVendingReleAd(VendingReleVo vendingReleVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.saveVendingReleAd(vendingReleVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public VendingConterAdVo getVendAdByADId(String adId) {
		// TODO Auto-generated method stub
		return vendingAdConterDao.getVendAdByADId(adId);
	}

	@Override
	public List<VendingReleVo> getVendReleByVendCode(String vendCode) {
		// TODO Auto-generated method stub
		return vendingAdConterDao.getVendReleByVendCode(vendCode);
	}

	@Override
	public Map<String, String> updateVendingRele(VendingReleVo vendingReleVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.updateVendingRele(vendingReleVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public VendingConterAdVo getVendingConterAdByadId(String imgName) {
		// TODO Auto-generated method stub
		return vendingAdConterDao.getVendingConterAdByadId(imgName);
	}

	@Override
	public void addVendingStatistAd(VendingConterAdVo sd) {
		// TODO Auto-generated method stub
		vendingAdConterDao.addVendingStatistAd(sd);
	}

	@Override
	public void deleteVendingAdByVendCode(StatistVo stra) {
		// TODO Auto-generated method stub
		vendingAdConterDao.deleteVendingAdByVendCode(stra);
	}

	@Override
	public List<StatistVo> getVendListConterAdByCode(String stra) {

		return vendingAdConterDao.getVendListConterAdByCode(stra);
	}

	@Override
	public void deletevendingReleByCode(VendingReleVo vendingReleVo) {
		// TODO Auto-generated method stub
		vendingAdConterDao.deletevendingReleByCode(vendingReleVo);
	}

	@Override
	public Map<String, String> updateSortByvendCode(StatistVo statistVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.updateSortByvendCode(statistVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public String queryAllVendCode() {
		StringBuffer vendCodeStr = new StringBuffer();
		try {
			List<VendingConterAdVo> vendCodeList = vendingAdConterDao.queryAllVendCodeList();
			for (int i = 0; i < vendCodeList.size(); i++) {
				VendingConterAdVo vAdVo = vendCodeList.get(i);
				System.out.println(vAdVo.getVendCode());
				if (i < vendCodeList.size() - 1) {
					vendCodeStr.append(vAdVo.getVendCode()).append(",");
				} else {
					vendCodeStr.append(vAdVo.getVendCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendCodeStr.toString();
	}

	@Override
	public Map<String, Object> queryParamMap(String adId) {
		Map<String, Object> resultMap = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adId", adId);
			resultMap = vendingAdConterDao.queryParamMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, String> updateVendingReleAd(VendingReleVo vendingReleVo) {
		Map<String, String> result = new HashMap<String, String>();
		vendingAdConterDao.updateVendingReleAd(vendingReleVo);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public String queryBeforAdList(String vendCode) {
		return vendingAdConterDao.queryBeforAdList(vendCode);
	}

	@Override
	public boolean isExist(Map<String, String> param) {
		boolean flag = true;
		try {
			int result = vendingAdConterDao.isExist(param);
			flag = result > 0 ? true : false;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
