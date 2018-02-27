package com.huilian.hlej.hcf.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.hcf.dao.BankLogoDao;
import com.huilian.hlej.hcf.service.BankLogoService;
import com.huilian.hlej.hcf.vo.BankLogoVo;
import com.huilian.hlej.jet.common.persistence.Page;

@Service
@Transactional
public class BankLogoServiceImpl implements BankLogoService{

	@Autowired
	private BankLogoDao bankLogoDao;
	
	@Override
	public void saveBankLogoVo(BankLogoVo bankLogoVo) {
		try {
			bankLogoDao.saveBankLogoVo(bankLogoVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String queryBankName(int bankCode) {
		String bankName = null;
		try {
			bankName = bankLogoDao.queryBankName(bankCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankName;
	}

	@Override
	public Page<BankLogoVo> getBankLogoVoPage(Page<BankLogoVo> page, BankLogoVo bankLogoVo) {
		page.setList(bankLogoDao.findBankLogoVoList(bankLogoVo));
		bankLogoVo.setPage(page);
		return page;
	}

	@Override
	public BankLogoVo getBankLogoVoById(String id) {
		return bankLogoDao.queryBankLogoVo(id);
	}

	@Override
	public boolean updateBankLogoVo(BankLogoVo bankLogoVo) {
		boolean falg = true;
		try {
			bankLogoDao.updateBankLogoVo(bankLogoVo);
		} catch (Exception e) {
			e.printStackTrace();
			falg = false;
		}
		return falg;
	}

	@Override
	public boolean deleteBankLogoVoById(String id) {
		boolean falg = true;
		try {
			bankLogoDao.deleteBankLogoVoById(id);
		} catch (Exception e) {
			e.printStackTrace();
			falg = false;
		}
		return falg;
	}

}
