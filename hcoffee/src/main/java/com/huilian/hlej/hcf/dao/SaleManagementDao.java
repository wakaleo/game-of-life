package com.huilian.hlej.hcf.dao;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.OrderDetailVo;
import com.huilian.hlej.hcf.vo.SaleDetailVo;
import com.huilian.hlej.hcf.vo.SaleManagementVo;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;

/**
 * 销售管理Dao
 * @author LongZhangWei
 * @date 2017年9月6日 上午11:42:02
 */
@MyBatisDao
public interface SaleManagementDao {

	/**
	 * 查询代理商销售信息
	 * @param saleManagementVo
	 * @return
	 */
	public List<SaleManagementVo> getSaleManagementList(SaleManagementVo saleManagementVo);
	
	/**
	 * 查询销售明细
	 * @param saleDetailVo
	 * @return
	 */
	public List<SaleDetailVo> getSaleDetailVoList(SaleDetailVo saleDetailVo);
	
	/**
	 * 查询订单明细
	 * @param orderDetailVo
	 * @return
	 */
	public List<OrderDetailVo> getOrderDatailVoList(OrderDetailVo orderDetailVo);
	
	/**
	 * 查询总销售件数和所有机器的销售总金额
	 * @param vendCodes
	 * @return
	 */
	public Map<String,Object> getAmountAndMoneySum(Map<String,String> param);
	
	/**
	 * 根据代理商登录名查询其下的机器编码
	 * @param loginName
	 * @return
	 */
	public List<DealerEqRelationVo> getVendCodesListByLoginName(String loginName);
}
