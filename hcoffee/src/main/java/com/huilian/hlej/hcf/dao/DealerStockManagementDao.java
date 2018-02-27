package com.huilian.hlej.hcf.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.huilian.hlej.hcf.vo.DealerStockDetailVo;
import com.huilian.hlej.hcf.vo.DealerStockVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 代理商库存管理DAO
 * @author LongZhangWei
 * @date 2017年9月4日 上午11:40:15
 */
@MyBatisDao
public interface DealerStockManagementDao extends CrudDao<T>{

	/**
	 * 获取所有代理商库存
	 * @return
	 */
	public List<DealerStockVo> getDealerStockVoList(DealerStockVo dealerStockVo);
	
	/**
	 * 根据代理商的登录名查询其下的所有机器编码
	 * @param loginName
	 * @return
	 */
	public String getVendCodesByLoginName(String loginName);
	
	/**
	 * 获取代理库存明细
	 * @param dealerStockDetailVo
	 * @return
	 */
	public List<DealerStockDetailVo> getDealerStockDetailVoList(DealerStockDetailVo dealerStockDetailVo);
	
	/**
	 * 获取代理商及其机器数和其所有的机器编码
	 * @return
	 */
	public List<DealerStockVo> loginNameMachineNumVendCodsList();
	
	/**
	 * 获取所有代商的登录名及其库存数
	 * @return
	 */
	public List<DealerStockVo> loginNameStockCountList();
	
	/**
	 * 根据机器编号查询销售总量
	 * @return
	 */
	public Integer getSaleCountByVendCodes(String vendCodes);
}
