package com.huilian.hlej.hcf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.AppVersionRecordsDao;
import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.service.VendingVersionService;
import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.jet.common.persistence.Page;
/**
 * 
 * @author xiekangjian
 * @date 2016年12月30日 下午3:28:39
 *
 */
@Service
@Transactional
public class VendingVersionServiceImpl implements VendingVersionService{

	@Autowired
	private AppVersionRecordsDao appVersionRecordsDao;
	
	@Override
	public Page<AppVersionRecordsVo> getVendingVersionPage(Page<AppVersionRecordsVo> page,AppVersionRecordsVo appVersionRecordsVo) {
		appVersionRecordsVo.setPage(page);
		page.setList(appVersionRecordsDao.findList(appVersionRecordsVo));
		return page;
	}

	@Override
	public Map<String, String> saveVendingVersion(AppVersionRecordsVo appVersionRecordsVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			AppVersionRecordsVo appVersion = appVersionRecordsDao.getVendingVersionByVersion(appVersionRecordsVo.getVersion());
			if(appVersion !=null){
				result.put("code", "1");
				result.put("msg", "版本（"+appVersionRecordsVo.getVersion()+"）已经存在！");
				return result;
			}
			appVersionRecordsDao.saveVendingVersion(appVersionRecordsVo);
			result.put("code", "0");
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Transactional(readOnly = false)
	public Map<String, String> updateVendingVersion(AppVersionRecordsVo appVersionRecordsVo) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			appVersionRecordsDao.updateVendingVersion(appVersionRecordsVo);
			result.put("code", "0");
			result.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

	@Override
	public AppVersionRecordsVo getVendingVersionByVersion(String version) {
		return appVersionRecordsDao.getVendingVersionByVersion(version);
	}

	@Override
	public  Map<String,String> deleteVendingVersionByVersion(String version) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			appVersionRecordsDao.deleteVendingVersion(version);
			result.put("code", "0");
			result.put("msg", "成功");
		} catch (Exception e) {
			result.put("code", "5");
			result.put("msg", "系统内部错误");
		}
		return result;
	}

}
