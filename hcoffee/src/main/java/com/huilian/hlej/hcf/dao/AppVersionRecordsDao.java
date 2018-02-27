/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.entity.AppVersionRecords;
import com.huilian.hlej.hcf.vo.AppVersionRecordsVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 售货机版本记录dao
 * @author xiekangjian
 * @date 2017年1月3日 上午9:51:47
 *
 */
@MyBatisDao
public interface AppVersionRecordsDao extends CrudDao<AppVersionRecords> {

	List<AppVersionRecordsVo> findList(AppVersionRecordsVo appVersionRecordsVo);


	void saveVendingVersion(AppVersionRecordsVo appVersionRecordsVo);


	AppVersionRecordsVo getVendingVersionByVersion(String version);


	int updateVendingVersion(AppVersionRecordsVo appVersionRecordsVo);


	void deleteVendingVersion(String version);

}
