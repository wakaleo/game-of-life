package com.huilian.hlej.hcf.service;

import java.util.List;
import java.util.Map;

import com.huilian.hlej.hcf.vo.DealerEqRelationVo;
import com.huilian.hlej.hcf.vo.OrderDetailVo;
import com.huilian.hlej.hcf.vo.SaleDetailVo;
import com.huilian.hlej.hcf.vo.SaleManagementVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 销售管理Service
 * @author LongZhangWei
 * @date 2017年9月6日 下午1:44:42
 */
public interface SaleManagementService {

	/**
	 * 查询代理商销售信息
	 * @param saleManagementVo
	 * @return
	 */
	public Page<SaleManagementVo> getSaleManagementVoPage(Page<SaleManagementVo> page, SaleManagementVo saleManagement);
	
	/**
	 * 查询销售明细
	 * @param saleDetailVo
	 * @return
	 */
	public Page<SaleDetailVo> getSaleDetailVoPage(Page<SaleDetailVo> page, SaleDetailVo saleDetailVo);
	
	/**
	 * 查询订单明细
	 * @param orderDetailVo
	 * @return
	 */
	public Page<OrderDetailVo> getOrderDatailVoPage(Page<OrderDetailVo> page, OrderDetailVo orderDetailVo);
	
	/**
	 * 根据代理商登录名查询其下的机器编码
	 * @param loginName
	 * @return
	 */
	public List<DealerEqRelationVo> getVendCodesListByLoginName(String loginName);
	
}
