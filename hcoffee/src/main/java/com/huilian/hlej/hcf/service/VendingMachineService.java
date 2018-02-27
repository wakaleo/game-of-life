package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.entity.ActivityScheme;
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.entity.LngLat;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.AppUpgradeRecordVo;
import com.huilian.hlej.hcf.vo.CountyVo;
import com.huilian.hlej.hcf.vo.CtiyVo;
import com.huilian.hlej.hcf.vo.CustInfoVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingMachineService {

	public List<VendingMachineVo> getVendingMachineList(VendingMachineVo vendingMachineVo);

	public VendingMachine getVendingMachineByCode(String vendCode);

	public Page<VendingMachineVo> getVendingMachinePage(Page<VendingMachineVo> page, VendingMachineVo VendingMachineVo);

	public Map<String, String> saveVendingMachine(VendingMachine vendingMachine);

	public Map<String, String> updateVendingMachine(VendingMachine VendingMachine);

	public List<Channel> getChannel();

	public List<Map<String, String>> getCommunity();

	public List<AppVersionRecords> getAppVersionRecords();

	public Map<String, String> saveAppUpgradeTask(AppUpgradeTask appUpgradeTask);

	public Map<String, String> enable(VendingMachine vendingMachine);

	public List<AppUpgradeRecordVo> selectAppUpgradeRecordList(AppUpgradeRecordVo appUpgradeRecordVo);

	public Page<AppUpgradeRecordVo> getAppUpgradeRecordPage(Page<AppUpgradeRecordVo> page,
			AppUpgradeRecordVo appUpgradeRecordVo);

	public Map<String, String> batchUpgrade(AppUpgradeTask appUpgradeTask);

	public List<ActivityScheme> getActivityScheme();

	public Page<VendingMachineVo> getVendingMachine(Page<VendingMachineVo> page, VendingMachineVo vendingMachineVo);

	public Channel getChannelName();

	public List<Map<String, String>> getMachineCode();

	Page<CustInfoVo> getVendingStatisticsPage(Page<CustInfoVo> page, CustInfoVo vendingMachineVo);

	public List<Map<String, String>> getprovinceName();

	public List<Map<String, String>> getCityName();

	public List<Map<String, String>> getAreaName();

	public List<Map<String, String>> getDeliveryType();

	public List<Map<String, String>> getEquipSupply();

	public List<CtiyVo> getCityByProvinceId(String provinceId);

	public List<CountyVo> getCityByCityId(String cityId);

	public Map<String, String> saveTubiao(LngLat lngLat);

	public Page<LngLat> getTuBiaoList(Page<LngLat> page, LngLat lngLat);

	public LngLat gettuBiaoByVendCode(String vendCode);

	public Map<String, String> udateTubiao(LngLat lngLat);

	public List<ActivityTypeVo> getActivityType();

	public List<ActivityScheme> getActivityTypeMaxImg();

	public List<ActivityScheme> getActivityTypes();

	public List<ActivityScheme> getActivityTypeMinImg();

	public List<ActivityScheme> getActivityTypeDai();

	public ActivityScheme selectSchemeName(String schemeNo);

	public Map<String, String> updateVendingByCode(VendingMachineVo activityManage);

	public Page<VendingMachineVo> getVendingMachineByTemplateId(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo);

	public Page<VendingMachineVo> getVendingMachineByOptionType(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo);

	public Page<VendingMachineVo> getVendingByVendcoed(Page<VendingMachineVo> page, VendingMachineVo vendingMachineVo);

	/**
	 * 根据选中二维码模板的更新
	 * 
	 * @param vendingMachineVo
	 * @throws Exception
	 */
	public void updateVendByWechat(VendingMachineVo vendingMachineVo) throws Exception;

	/**
	 * 二维码字段机器分页
	 * 
	 * @param page
	 * @param vendingMachineVo
	 * @return
	 */
	public Page<VendingMachineVo> getVendingMachineByModelNo(Page<VendingMachineVo> page,
			VendingMachineVo vendingMachineVo);

	/**
	 * 全部更新
	 * 
	 * @param appUpgradeTask
	 * @return
	 */
	public Map<String, String> allUpdate(List<VendingMachineVo> list, AppUpgradeTask appUpgradeTask);

}
