package com.huilian.hlej.hcf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.AppUpgradeTaskDao;
import com.huilian.hlej.hcf.dao.VendingMachineDao;
import com.huilian.hlej.hcf.entity.ActivityScheme;
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.entity.LngLat;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.service.VendingMachineService;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.CountyVo;
import com.huilian.hlej.hcf.vo.CtiyVo;
import com.huilian.hlej.hcf.vo.CustInfoVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 
 * @author liujian
 *
 */
@Service
@Transactional
public class VendingMachineServiceImpl implements VendingMachineService {

	@Autowired
	private VendingMachineDao vendingMachineDao;

	@Autowired
	private AppUpgradeTaskDao appUpgradeTaskDao;
	
	@Override
	public List<VendingMachineVo> getVendingMachineList(VendingMachineVo vendingMachineVo) {
		List<VendingMachineVo> list = vendingMachineDao.findList(vendingMachineVo);
		return list;
	}

	@Override
	public VendingMachine getVendingMachineByCode(String vendCode) {
		return vendingMachineDao.getVendingMachineByCode(vendCode);
	}

	public List<Map<String, String>> getCommunity() {
		return vendingMachineDao.getCommunity();
	}

	@Override
	public List<Map<String, String>> getprovinceName() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getprovinceName();
	}

	public List<AppVersionRecords> getAppVersionRecords() {
		return vendingMachineDao.getAppVersionRecords();
	}

	@Override
	public Page<VendingMachineVo> getVendingMachinePage(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.findVendingMachineList(vendingMachineVo));
		return page;
	}

	@Override
	public Page<AppUpgradeRecordVo> getAppUpgradeRecordPage(Page<AppUpgradeRecordVo> page,
			AppUpgradeRecordVo appUpgradeRecordVo) {
		appUpgradeRecordVo.setPage(page);
		page.setList(appUpgradeTaskDao.getVendUpgradeRecordByCode(appUpgradeRecordVo));
		return page;
	}

	@Override
	public List<AppUpgradeRecordVo> selectAppUpgradeRecordList(AppUpgradeRecordVo appUpgradeRecordVo) {
		List<AppUpgradeRecordVo> list = appUpgradeTaskDao.getVendUpgradeRecordByCode(appUpgradeRecordVo);
		return list;
	}

	@Transactional(readOnly = false)
	public Map<String, String> updateVendingMachine(VendingMachine vendingMachine) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (vendingMachine.getChannel() == null) {
				result.put("code", "1");
				result.put("msg", "渠道为空");
				return result;
			}
			if (StringUtils.isBlank(vendingMachine.getAppVersionId())) {
				result.put("code", "1");
				result.put("msg", "app版本为空");
				return result;
			}
			if (StringUtils.isBlank(vendingMachine.getCommunityId())) {
				result.put("code", "1");
				result.put("msg", "社区为空");
				return result;
			}
			trimStr(vendingMachine);
			vendingMachineDao.updateVendingMachine(vendingMachine);
			result.put("code", "0");
			result.put("msg", "成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public List<Channel> getChannel() {
		return vendingMachineDao.getChannel();
	}

	@Override
	public List<ActivityScheme> getActivityScheme() {
		return vendingMachineDao.getActivityScheme();
	}

	@Transactional(readOnly = false)
	public Map<String, String> saveVendingMachine(VendingMachine vendingMachine) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (StringUtils.isBlank(vendingMachine.getVendCode())) {
				result.put("code", "1");
				result.put("msg", "机器编码为空");
				return result;
			}
			VendingMachine dbo = vendingMachineDao.getVendingMachineByCode(vendingMachine.getVendCode());
			vendingMachine.setStatus(0);
			trimStr(vendingMachine);
			Integer addFlag = vendingMachine.getAddFlag();
			if (addFlag == 1) {// 正常添加
				if (dbo != null) {
					result.put("code", "2");
					result.put("msg", "机器编码已注册");
					return result;
				}
				if (vendingMachine.getChannel() == null) {
					result.put("code", "1");
					result.put("msg", "渠道为空");
					return result;
				}
				if (StringUtils.isBlank(vendingMachine.getAppVersionId())) {
					result.put("code", "1");
					result.put("msg", "app版本为空");
					return result;
				}
				if (StringUtils.isBlank(vendingMachine.getCommunityId())) {
					result.put("code", "1");
					result.put("msg", "社区为空");
					return result;
				}
				vendingMachineDao.saveVendingMachine(vendingMachine);
			}
			if (addFlag == 2) {// 快速添加
				vendingMachineDao.fastSaveVendingMachine(vendingMachine);
			}
			result.put("code", "0");
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	private void trimStr(VendingMachine vendingMachine) {
		String location = vendingMachine.getLocation();
		String locationStr = location != null ? location.replaceAll("\\n", "") : "";
		vendingMachine.setLocation(locationStr);
	}

	@Transactional(readOnly = false)
	public Map<String, String> saveAppUpgradeTask(AppUpgradeTask appUpgradeTask) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			VendingMachine dbo = vendingMachineDao.getVendingMachineByCode(appUpgradeTask.getVendCode());
			if (dbo == null) {
				result.put("code", "1");
				result.put("msg", "售货机（" + appUpgradeTask.getVendCode() + "）不存在");
				return result;
			}
			AppVersionRecords versionObj = vendingMachineDao.getAppVersionbyId(appUpgradeTask.getVersionId());
			if (versionObj == null) {
				result.put("code", "1");
				result.put("msg", "app版本（" + appUpgradeTask.getVersionId() + "）不存在");
				return result;
			}
			// 判断该售货机是否处于待升级，不是就新增该售货机的待升级记录；是则修改该跳记录状态为作废，新增一条该售货机的待升级记录
			appUpgradeTask.setStatus(0);
			List<AppUpgradeTask> taskList = (List<AppUpgradeTask>) appUpgradeTaskDao.getAppUpgradeTask(appUpgradeTask);
			if (taskList != null && taskList.size() > 0) {
				appUpgradeTask.setStatus(4);
				appUpgradeTask.setId(taskList.get(0).getId());
				appUpgradeTaskDao.updateAppUpgradeTask(appUpgradeTask);
			}

			// 判断该售货机是否有升级中的记录，有则提示次售货机正在升级中
			appUpgradeTask.setStatus(1);
			taskList = (List<AppUpgradeTask>) appUpgradeTaskDao.getAppUpgradeTask(appUpgradeTask);
			if (taskList != null && taskList.size() > 0) {
				result.put("code", "3");
				result.put("msg", "售货机（" + appUpgradeTask.getVendCode() + "）正处于升级中,请耐心等待!");
				return result;
			}

			appUpgradeTask.setVendCode(dbo.getVendCode());
			appUpgradeTask.setVendId(dbo.getId());
			appUpgradeTask.setStatus(0);
			appUpgradeTask.setChannel(dbo.getChannel());
			appUpgradeTask.setChannelName(dbo.getChannelName());
			appUpgradeTask.setVersionCurrent(dbo.getAppVersion());
			appUpgradeTask.setVersionCurrentId(dbo.getAppVersionId());
			appUpgradeTask.setDownloadLink(versionObj.getDownloadLink());
			appUpgradeTaskDao.saveAppUpgradeTask(appUpgradeTask);
			result.put("code", "0");
			result.put("msg", "操作成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	public Map<String, String> enable(VendingMachine vendingMachine) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if (StringUtils.isBlank(vendingMachine.getVendCode())) {
				result.put("code", "1");
				result.put("msg", "售货机编码不能为空");
				return result;
			}
			vendingMachineDao.updateStatusByCode(vendingMachine.getStatus(), vendingMachine.getVendCode());
			result.put("code", "0");
			result.put("msg", "操作成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public Map<String, String> batchUpgrade(AppUpgradeTask appUpgradeTask) {
		Map<String, String> result = new HashMap<String, String>();

		if (appUpgradeTask != null) {
			String[] strs = appUpgradeTask.getBatchVendCode().split(",");
			for (String vendCode : strs) {
				appUpgradeTask.setVendCode(vendCode);
				result = saveAppUpgradeTask(appUpgradeTask);
				if (!("0").equals(result.get("code"))) {
					return result;
				}
			}
		}
		return result;
		// Map<String, String> result = new HashMap<String, String>();
		// try {
		// appUpgradeTaskDao.batchUpgrade(AppUpgradeList);
		// result.put("code", "0");
		// result.put("msg", "操作成功");
		// } catch (Exception e) {
		// result.put("code", "5");
		// result.put("msg", "系统内部错误");
		// }
		// return result;
	}

	@Override
	public Page<VendingMachineVo> getVendingMachine(Page<VendingMachineVo> page, VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.findList(vendingMachineVo));
		return page;
	}

	@Override
	public Channel getChannelName() {
		return vendingMachineDao.getChannelName();
	}

	@Override
	public List<Map<String, String>> getMachineCode() {
		return vendingMachineDao.getMachineCode();
	}

	@Override
	public Page<CustInfoVo> getVendingStatisticsPage(Page<CustInfoVo> page, CustInfoVo vendingMachineVo) {

		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.getVendingStatisticsPage(vendingMachineVo));
		return page;
	}

	@Override
	public List<Map<String, String>> getCityName() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getCityName();
	}

	@Override
	public List<Map<String, String>> getAreaName() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getAreaName();
	}

	@Override
	public List<Map<String, String>> getDeliveryType() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getDeliveryType();
	}

	@Override
	public List<Map<String, String>> getEquipSupply() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getEquipSupply();
	}

	@Override
	public List<CtiyVo> getCityByProvinceId(String provinceId) {
		// TODO Auto-generated method stub
		return vendingMachineDao.getCityByProvinceId(provinceId);
	}

	@Override
	public List<CountyVo> getCityByCityId(String cityId) {
		// TODO Auto-generated method stub
		return vendingMachineDao.getCityByCityId(cityId);
	}
	/*
	 * @Override public List<Map<String, String>> getAappVersion() { return
	 * vendingMachineDao.getAappVersion(); }
	 */

	@Override
	public Map<String, String> saveTubiao(LngLat lngLat) {
		Map<String, String> result = new HashMap<String, String>();
		vendingMachineDao.saveTubiao(lngLat);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public Page<LngLat> getTuBiaoList(Page<LngLat> page, LngLat lngLat) {
		lngLat.setPage(page);
		page.setList(vendingMachineDao.getTuBiaoList(lngLat));
		return page;
	}

	@Override
	public LngLat gettuBiaoByVendCode(String vendCode) {
		// TODO Auto-generated method stub
		return vendingMachineDao.gettuBiaoByVendCode(vendCode);
	}

	@Override
	public Map<String, String> udateTubiao(LngLat lngLat) {
		// TODO Auto-generated method stub
		Map<String, String> result = new HashMap<String, String>();
		vendingMachineDao.udateTubiao(lngLat);
		result.put("code", "0");
		result.put("msg", "成功");
		return result;
	}

	@Override
	public List<ActivityTypeVo> getActivityType() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getActivityType();
	}

	@Override
	public List<ActivityScheme> getActivityTypeMaxImg() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getActivityTypeMaxImg();
	}

	@Override
	public List<ActivityScheme> getActivityTypes() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getActivityTypes();
	}

	@Override
	public List<ActivityScheme> getActivityTypeMinImg() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getActivityTypeMinImg();
	}

	@Override
	public List<ActivityScheme> getActivityTypeDai() {
		// TODO Auto-generated method stub
		return vendingMachineDao.getActivityTypeDai();
	}

	@Override
	public ActivityScheme selectSchemeName(String schemeNo) {
		// TODO Auto-generated method stub
		return vendingMachineDao.selectSchemeName(schemeNo);
	}

	@Override
	public Map<String, String> updateVendingByCode(VendingMachineVo activityManage) {
		// TODO Auto-generated method stub
		Map<String, String> result = new HashMap<String, String>();
		try {

			vendingMachineDao.updateVendingByCode(activityManage);
			result.put("code", "0");
			result.put("msg", "成功发布");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public Page<VendingMachineVo> getVendingMachineByTemplateId(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.getVendingMachineByTemplateId(vendingMachineVo));
		return page;
	}

	@Override
	public Page<VendingMachineVo> getVendingMachineByOptionType(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.getVendingMachineByOptionType(vendingMachineVo));
		return page;
	}

	@Override
	public Page<VendingMachineVo> getVendingByVendcoed(Page<VendingMachineVo> page, VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.getVendingByVendcoed(vendingMachineVo));
		return page;
	}

	@Override
	public void updateVendByWechat(VendingMachineVo vendingMachineVo) throws Exception {
		try {
			// 模板no
			String modelNo = vendingMachineVo.getWechatModelNo();
			// 所有展示的机器
			String[] arr_vendCode = vendingMachineVo.getArr_vendCode();
			// 所有选中的机器
			String[] arr_checked_vendCode = vendingMachineVo.getArr_checked_vendCode();
			for (String vendCode : arr_vendCode) {
				VendingMachineVo backVo = vendingMachineDao.getVendByCode(vendCode);
				// 当前绑定的
				String wechatModelNo = backVo.getWechatModelNo();
				// 当前操作状态1-更新 2-清空
				String wechatOptionType = backVo.getWechatOptionType();
				// 是否有选中
				boolean checkedVend = false;
				for (String checkedVendCode : arr_checked_vendCode) {
					if (vendCode.equals(checkedVendCode)) {
						checkedVend = true;
						break;
					}
				}
				// 如果没选并且原来no为空，或者不是更新的，则不更新
				if (!checkedVend
						&& ((wechatModelNo == null || wechatModelNo.length() == 0) || !"1".equals(wechatOptionType))) {
					continue;
				}
				// 如果选了，并且是更新状态，并且当前的no和选中的no一样
				if (checkedVend && "1".equals(wechatOptionType) && modelNo.equals(wechatModelNo)) {
					continue;
				}
				if (checkedVend && (!modelNo.equals(wechatModelNo)
						|| (modelNo.equals(wechatModelNo) && !"1".equals(wechatOptionType)))) {
					Date date = new Date();
					backVo.setOldWechatModelNo(wechatModelNo);
					backVo.setWechatModelNo(modelNo);
					backVo.setWechatOptionType("1");
					backVo.setUpdateTime(date);
					backVo.setWechatOptionTime(date);
					vendingMachineDao.updateVendByWechat(backVo);
				}
				if (!checkedVend && "1".equals(wechatOptionType) && modelNo.equals(wechatModelNo)) {
					Date date = new Date();
					backVo.setWechatModelNo(modelNo);
					backVo.setOldWechatModelNo(modelNo);
					backVo.setWechatOptionType("2");
					backVo.setUpdateTime(date);
					backVo.setWechatOptionTime(date);
					vendingMachineDao.updateVendByWechat(backVo);
				}

				// 如果原没有绑定，且没选中或者有绑定，且选中的是同一个，则continue
			}

		} catch (Exception e) {
			throw new Exception("发布出错：" + e.getMessage());
		}
	}

	@Override
	public Page<VendingMachineVo> getVendingMachineByModelNo(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo) {
		vendingMachineVo.setPage(page);
		page.setList(vendingMachineDao.getVendingMachineByModelNo(vendingMachineVo));
		return page;
	}

	@Override
	public Map<String, String> allUpdate(List<VendingMachineVo> list,AppUpgradeTask appUpgradeTask) {
		Map<String, String> result = new HashMap<String, String>();
		for(VendingMachineVo vvo : list){
			appUpgradeTask.setVendCode(vvo.getVendCode());
			result = saveAppUpgradeTask(appUpgradeTask);
			if (!("0").equals(result.get("code"))) {
				return result;
			}
		}
		return result;
	}

}
