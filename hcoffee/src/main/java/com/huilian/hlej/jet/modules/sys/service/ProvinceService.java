package com.huilian.hlej.jet.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.modules.sys.dao.ProvinceDao;
import com.huilian.hlej.jet.modules.sys.entity.Province;


@Service
@Transactional(readOnly = true)
public class ProvinceService {

	@Autowired
	private ProvinceDao provinceDao;

	public List<Province> getProvincesByCountryId(Long countryId) {
		return provinceDao.getProvincesByCountryId(countryId);
	}
}
