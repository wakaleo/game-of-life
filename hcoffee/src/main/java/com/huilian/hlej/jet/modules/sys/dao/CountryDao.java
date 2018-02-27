package com.huilian.hlej.jet.modules.sys.dao;

import java.util.List;

import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.Country;

@MyBatisDao
public interface CountryDao {

	List<Country> getCountryList();

}
