package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.vo.BankLogoVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BankLogoDao {

	void saveBankLogoVo(BankLogoVo bankLogoVo);

	String queryBankName(int bankCode);

	List<BankLogoVo> findBankLogoVoList(BankLogoVo bankLogoVo);

	BankLogoVo queryBankLogoVo(String id);

	void updateBankLogoVo(BankLogoVo bankLogoVo);

	void deleteBankLogoVoById(String id);
}
