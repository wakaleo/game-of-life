package com.huilian.hlej.jet.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huilian.hlej.jet.modules.sys.dao.CityDao;
import com.huilian.hlej.jet.modules.sys.entity.CityEntity;

@Service
public class CityService {
	@Autowired
	CityDao cityDao;
	
	public CityEntity getCityInfoById(String cityId){
		return cityDao.getCityInfoById(cityId);
	}
	
	public List<CityEntity> getCityInfoByCountryId(Long countryId){
        return cityDao.getCityInfoByCountryId(countryId);
    }
}
