package com.huilian.hlej.hcf.service;

import com.huilian.hlej.hcf.vo.BankLogoVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface BankLogoService {

	/**
	 * 保存银行卡logo信息
	 * @param bankLogoVo
	 */
	void saveBankLogoVo(BankLogoVo bankLogoVo);

	/**
	 * 查询银行名称根据银行编码
	 * @return
	 */
	String queryBankName(int bankCode);

	/**
	 * 查询logo列表
	 * @param page
	 * @param bankLogoVo
	 * @return
	 */
	Page<BankLogoVo> getBankLogoVoPage(Page<BankLogoVo> page, BankLogoVo bankLogoVo);

	/**
	 * 根据id查询银行卡logo信息
	 * @param id
	 * @return
	 */
	BankLogoVo getBankLogoVoById(String id);

	/**
	 * 修改银行卡logo信息
	 * @param bankLogoVo
	 * @return
	 */
	boolean updateBankLogoVo(BankLogoVo bankLogoVo);

	/**
	 * 根据id删除银行卡信息
	 * @param id
	 * @return
	 */
	boolean deleteBankLogoVoById(String id);

}
