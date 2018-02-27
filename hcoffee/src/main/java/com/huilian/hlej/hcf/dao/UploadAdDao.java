package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.UploadAdVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 上传广告dao
 * @author luozb
 * @version 2017/02/24
 */
@MyBatisDao
public interface UploadAdDao extends CrudDao<UploadAdVo> {

	List<UploadAdVo> selectUploadAdList(UploadAdVo uploadAdVo);
	
	public int saveUploadAd(UploadAdVo uploadAdVo);
	
	public void updateUploadAdById(UploadAdVo uploadAdVo);
	
	public UploadAdVo getUploadAd(UploadAdVo uploadAdVo);
	
	void updateUploadAd(UploadAdVo uploadAdVo);
	
	public List<UploadAdVo> getUploadAdList(UploadAdVo uploadAdVo);
	
}
