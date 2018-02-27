package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingVersionService {

	Page<AppVersionRecordsVo> getVendingVersionPage(Page<AppVersionRecordsVo> page, AppVersionRecordsVo appVersionRecordsVo);

	Map<String, String> saveVendingVersion(AppVersionRecordsVo appVersionRecordsVo);

	Map<String, String> updateVendingVersion(AppVersionRecordsVo appVersionRecordsVo);

	AppVersionRecordsVo getVendingVersionByVersion(String version);

	Map<String, String> deleteVendingVersionByVersion(String version);

}
