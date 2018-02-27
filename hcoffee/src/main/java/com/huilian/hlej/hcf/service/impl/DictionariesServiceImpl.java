package com.huilian.hlej.hcf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.DictionariesDao;
import com.huilian.hlej.hcf.service.DictionariesService;
import com.huilian.hlej.hcf.vo.DictionariesVo;

@Service
@Transactional
public class DictionariesServiceImpl implements DictionariesService {

	@Autowired
	private DictionariesDao dictionaresDao;
	
	@Override
	public List<DictionariesVo> getOrderStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getOrderStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getOrderPayWayList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getOrderPayWayList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getOrderSourceList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getOrderSourceList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getGoodsSaleDetailOrderStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getGoodsSaleDetailOrderStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getPayStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getPayStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getPayTypeList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getPayTypeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getShipStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getShipStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getDealerTypeList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getDealerTypeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getDealerGradeList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getDealerGradeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getConStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getConStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getSalePayWayList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getSalePayWayList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getSexList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getSexList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getStateList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getStateList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getLottoActivityStatusList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getLottoActivityStatusList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getLottoWayList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getLottoWayList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getLottoTriggerConditionList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getLottoTriggerConditionList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getLottoPrizeTypeList() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getLottoPrizeTypeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getLottoSource() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getLottoSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DictionariesVo> getBankName() {
		List<DictionariesVo> list = null;
		try {
			list = dictionaresDao.getBankName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
