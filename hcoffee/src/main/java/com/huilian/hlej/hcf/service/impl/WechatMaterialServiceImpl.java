package com.huilian.hlej.hcf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.LottoActivityDao;
import com.huilian.hlej.hcf.dao.WechatMaterialDao;
import com.huilian.hlej.hcf.service.WechatMaterialService;
import com.huilian.hlej.hcf.vo.LottoActivityVo;
import com.huilian.hlej.hcf.vo.LottoEqRelationVo;
import com.huilian.hlej.hcf.vo.LottoVendVo;
import com.huilian.hlej.hcf.vo.WechatMaterialVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 
 * @author ZhangZeBiao
 * @date 2017年12月6日 下午1:53:09
 *
 */
@Service
@Transactional
public class WechatMaterialServiceImpl implements WechatMaterialService {

	
	@Autowired
	private WechatMaterialDao wechatMaterialDao;
	
	
//	@Override
//	public Map<String, String> saveLottoVendVo(List<LottoVendVo> lottoVendVoList) {
//		Map<String, String> result = new HashMap<String, String>();
//		try {
//			if (lottoVendVoList==null||lottoVendVoList.size()==0) {
//				return null;
//			}
//			String ai = lottoVendVoList.get(0).getActivityNo();
//			String vc = lottoVendVoList.get(0).getVendCode();
//			if (ai==null||vc==null||ai.length()==0||vc.length()==0) {
//				return null;
//			}
//			lottoVendDao.deleteListByActivityNoAndVendCode(lottoVendVoList.get(0));
//			for (LottoVendVo lottoVendVo : lottoVendVoList) {
//				String activityNo = lottoVendVo.getActivityNo();
//				String vendCode = lottoVendVo.getVendCode();
//				
//				String activityName = lottoActivityDao.queryActivityNameByActivityNo(activityNo);
//				String location = lottoActivityDao.getLocationByVendCode(vendCode);
//				lottoVendVo.setLocation(location);
//				lottoVendVo.setActivityName(activityName);
//				lottoVendDao.saveLottoVendVo(lottoVendVo);
//			}
//			result.put("code", "0");
//			result.put("msg", "添加成功!");
//		} catch (Exception e) {
//			result.put("code", "1");
//			result.put("msg", "系统内部错误");
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//
//	@Override
//	public Page<LottoVendVo> getLottoVendVoPage(Page<LottoVendVo> page, LottoVendVo lottoVendVo) {
//		lottoVendVo.setPage(page);
//		List<LottoVendVo> findLottoVendVoList = lottoVendDao.findLottoVendVoList(lottoVendVo);
//		for (LottoVendVo lv : findLottoVendVoList) {
//			String prizeUrl = lv.getPrizeUrl();
//			lv.setPrizeUrl(StringUtils.isEmpty(prizeUrl)?"":prizeUrl.replace(" ", ""));
//		}
//		page.setList(findLottoVendVoList);
//		return page;
//	}
//
//
//	@Override
//	public List<LottoActivityVo> getLottoActivityVoAll() {
//		List<LottoActivityVo> list = null;
//		try {
//			list = lottoVendDao.getLottoActivityVoAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//	
//
//	@Override
//	public List<LottoEqRelationVo> getLottoEqRelationVoAll() {
//		List<LottoEqRelationVo> list = null;
//		try {
//			list = lottoActivityDao.getLottoEqRelationVoAll();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//	@Override
//	public List<LottoVendVo> getPrizeList(LottoVendVo lottoVendVo) {
//		List<LottoVendVo> list = null;
//		try {
//			list = lottoVendDao.getPrizeList(lottoVendVo);
//			for (LottoVendVo lv : list) {
//				String prizeUrl = lv.getPrizeUrl();
//				lv.setPrizeUrl(StringUtils.isEmpty(prizeUrl)?"":prizeUrl.replace(" ", ""));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//	@Override
//	public LottoVendVo getLottoVendVo(String id) {
//		
//		return lottoVendDao.getLottoVendVo(id);
//	}
//
//	@Override
//	public boolean deletePrize(LottoVendVo lottoVendVo) {
//		boolean flag = true;
//		try {
//			lottoVendDao.deletePrize(lottoVendVo);
//		} catch (Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//	@Override
//	public Map<String, String> updateLottoVendVo(LottoVendVo lottoVendVo) {
//		Map<String, String> result = new HashMap<String, String>();
//		try {
//			String activityNo = lottoVendVo.getActivityNo();
//			String vendCode = lottoVendVo.getVendCode();
//			
//			String activityName = lottoActivityDao.queryActivityNameByActivityNo(activityNo);
//			String location = lottoActivityDao.getLocationByVendCode(vendCode);
//			lottoVendVo.setLocation(location);
//			lottoVendVo.setActivityName(activityName);
//			lottoVendDao.updateLottoVendVo(lottoVendVo);
//			result.put("code", "0");
//			result.put("msg", "更新成功!");
//		} catch (Exception e) {
//			result.put("code", "1");
//			result.put("msg", "系统内部错误");
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	@Override
//	public boolean checkPrizeName(String prizeName) {
//		boolean flag = true;
//		try {
//			int rseult = lottoVendDao.checkPrizeName(prizeName);
//			if(rseult > 0)
//				flag = false;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//
//	@Override
//	public List<LottoVendVo> getLottoVendVoListByActivityNoAndVendCode(LottoVendVo lottoVendVo) {
//		List<LottoVendVo> list = null;
//		try {
//			list = lottoVendDao.getLottoVendVoListByActivityNoAndVendCode(lottoVendVo);
//			for (LottoVendVo lv : list) {
//				String prizeUrl = lv.getPrizeUrl();
//				lv.setPrizeUrl(StringUtils.isEmpty(prizeUrl)?"":prizeUrl.replace(" ", ""));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//
//	@Override
//	public boolean deleteVendCodeByActivityNoAndVendCode(LottoVendVo lottoVendVo) {
//		try {
//			lottoVendDao.deleteListByActivityNoAndVendCode(lottoVendVo);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String, String> saveWechatMaterialVo(WechatMaterialVo wechatMaterialVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			wechatMaterialVo.setCreateTime(new Date());
			wechatMaterialDao.saveWechatMaterialVo(wechatMaterialVo);
			
			result.put("code", "0");
			result.put("msg", "添加成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public Page<WechatMaterialVo> getWechatMaterialVoPage(Page<WechatMaterialVo> page,
			WechatMaterialVo wechatMaterialVo) {
		wechatMaterialVo.setPage(page);
		List<WechatMaterialVo> findWechatMaterialVoList = wechatMaterialDao.findWechatMaterialVoList(wechatMaterialVo);
		page.setList(findWechatMaterialVoList);
		return page;
	}


	@Override
	public WechatMaterialVo getWechatMaterialVo(String id) {
		return wechatMaterialDao.getWechatMaterialVo(id);
	}


	@Override
	public boolean deleteWechatMaterialVo(WechatMaterialVo wechatMaterialVo) {
		boolean flag = true;
		try {
			wechatMaterialDao.deleteWechatMaterialVo(wechatMaterialVo);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public Map<String, String> updateWechatMaterialVo(WechatMaterialVo wechatMaterialVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
//			wechatMaterialVo.setCreateTime(new Date());
			wechatMaterialDao.updateWechatMaterialVo(wechatMaterialVo);
			result.put("code", "0");
			result.put("msg", "更新成功!");
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "系统内部错误");
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public List<WechatMaterialVo> getWechatMaterialVoAll() {
		List<WechatMaterialVo> list = null;
		try {
			WechatMaterialVo wechatMaterialVo=new WechatMaterialVo();
			list = wechatMaterialDao.findWechatMaterialVoList(wechatMaterialVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public boolean checkNameOfNo(WechatMaterialVo wechatMaterialVo) {
		boolean flag = true;
		try {
			int rseult = wechatMaterialDao.checkNameOfNo(wechatMaterialVo);
			if(rseult > 0)
				flag = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
