package com.huilian.hlej.jet.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.Province;


@MyBatisDao
public interface ProvinceDao {

	List<Province> getProvincesByCountryId(@Param("countryId") Long countryId);

}
