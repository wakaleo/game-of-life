package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.UploadAdVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface UploadAdService {
	Page<UploadAdVo> getUploadAdPage(Page<UploadAdVo> page, UploadAdVo uploadAdVo);
	public   Map<String,String> saveUploadAdVo(UploadAdVo uploadAdVo);
	public   Map<String,String> updateUploadAdById(UploadAdVo uploadAdVo);
	public   UploadAdVo getUploadAd(UploadAdVo uploadAdVo);
	public   Map<String,String> closeUploadAd(UploadAdVo uploadAdVo);
	public   Page<UploadAdVo> getUploadAdList(Page<UploadAdVo> page,UploadAdVo uploadAdVo);
}
