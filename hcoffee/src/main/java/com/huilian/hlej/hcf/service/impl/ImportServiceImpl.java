package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huilian.hlej.hcf.dao.ImportDao;
import com.huilian.hlej.hcf.service.ImportService;
import com.huilian.hlej.hcf.vo.OrderImportVo;
@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	private ImportDao importDao;
	
	@Override
	public boolean save(List<OrderImportVo> list) {
		boolean falg = true;
		try {
			importDao.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			falg = false;
		}
		return falg;
	}

	@Override
	public boolean saveVo(OrderImportVo vo) {
		boolean falg = true;
		try {
			importDao.saveVo(vo);
		} catch (Exception e) {
			e.printStackTrace();
			falg = false;
		}
		return falg;
	}

}
