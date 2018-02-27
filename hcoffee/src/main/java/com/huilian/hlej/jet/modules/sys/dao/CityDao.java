/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/tsingson">tsingson</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.dao;

import java.util.List;

import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.CityEntity;

/**
 * 区域DAO接口
 * @author hlej
 * @version 2014-05-16
 */
@MyBatisDao
public interface CityDao{
	public CityEntity getCityInfoById(String id);
	
	/**
	 * 获取国家下的所有城市
	 * @param countryId
	 * @return
	 */
	public List<CityEntity> getCityInfoByCountryId(Long countryId);
}
