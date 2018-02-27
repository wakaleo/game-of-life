package com.huilian.hlej.jet.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.modules.sys.dao.CountryDao;
import com.huilian.hlej.jet.modules.sys.entity.Country;

@Service
@Transactional(readOnly = true)
public class CountryService {

	@Autowired
	private CountryDao countryDao;

	public List<Country> getCountryList() {
		return countryDao.getCountryList();
	}
}
