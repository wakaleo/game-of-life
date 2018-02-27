package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.entity.ActivityScheme;
import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.entity.Channel;
import com.huilian.hlej.hcf.entity.LngLat;
import com.huilian.hlej.hcf.entity.VendingMachine;
import com.huilian.hlej.hcf.vo.ActivityManageVo;
import com.huilian.hlej.hcf.vo.ActivityTypeVo;
import com.huilian.hlej.hcf.vo.CountyVo;
import com.huilian.hlej.hcf.vo.CtiyVo;
import com.huilian.hlej.hcf.vo.CustInfoVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 售货机dao
 * @author liujian
 * @version 2016/12/14
 */
@MyBatisDao
public interface VendingMachineDao extends CrudDao<VendingMachineVo> {

	public VendingMachine getVendingMachineByCode(String vendCode);
	
	void saveVendingMachine(VendingMachine vendingMachine);
	
	int updateVendingMachine(VendingMachine vendingMachine);
	
	public List<Channel> getChannel();
	public List<Map<String,String>> getCommunity();
	
	public List<AppVersionRecords> getAppVersionRecords();
	
	public AppVersionRecords getAppVersionbyId(String id);
	
	public int updateStatusByCode(int status,String vendCode);

	public List<ActivityScheme> getActivityScheme();

	public Channel getChannelName();

	public List<Map<String,String>>  getMachineCode();

	public List<CustInfoVo> getVendingStatisticsPage(CustInfoVo vendingMachineVo);

	public List<Map<String, String>> getprovinceName();

	public List<Map<String, String>> getCityName();

	public List<Map<String, String>> getAreaName();

	public List<Map<String, String>> getDeliveryType();

	public List<Map<String, String>> getEquipSupply();

	public List<CtiyVo> getCityByProvinceId(String provinceId);
	
	public List<CountyVo> getCityByCityId(String cityId);


	public List<VendingMachineVo> findVendingMachineList(VendingMachineVo vendingMachineVo);

	public void saveTubiao(LngLat lngLat);

	public List<LngLat> getTuBiaoList(LngLat lngLat);


	public LngLat gettuBiaoByVendCode(String vendCode);

	int udateTubiao(LngLat lngLat);

	public List<ActivityTypeVo> getActivityType();

	public List<ActivityScheme> getActivityTypeMaxImg();

	public List<ActivityScheme> getActivityTypes();

	public List<ActivityScheme> getActivityTypeMinImg();

	public List<ActivityScheme> getActivityTypeDai();

	public  ActivityScheme selectSchemeName(String schemeNo);

	public void updateVendingByCode(VendingMachineVo activityManage);

	public List<VendingMachineVo> getVendingMachineByTemplateId(VendingMachineVo vendingMachineVo);

	public List<VendingMachineVo> getVendingMachineByOptionType(VendingMachineVo vendingMachineVo);

	public List<VendingMachineVo> getVendingByVendcoed(VendingMachineVo vendingMachineVo);

	/**
	 * 根据code获取VendingMachineVo
	 * @param vendCode
	 * @return
	 */
	public VendingMachineVo getVendByCode(String vendCode);

	/**
	 * 更新（发布）二维码相关字段
	 * @param backVo
	 */
	public void updateVendByWechat(VendingMachineVo backVo);

	/**
	 * 获取VendingMachineVo  用于可否删除二维码模板
	 * @param machineVo
	 * @return
	 */
	public List<VendingMachineVo> getVendByWechatModelNo(VendingMachineVo machineVo);

	/**
	 * 根据二维码相关字段获取
	 * @param vendingMachineVo
	 * @return
	 */
	public List<VendingMachineVo> getVendingMachineByModelNo(VendingMachineVo vendingMachineVo);

	/**
	 * 快速添加
	 * @param vendingMachine
	 */
	public void fastSaveVendingMachine(VendingMachine vendingMachine);
	
}
