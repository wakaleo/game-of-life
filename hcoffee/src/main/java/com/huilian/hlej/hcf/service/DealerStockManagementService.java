package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.DealerStockDetailVo;
import com.huilian.hlej.hcf.vo.DealerStockVo;
import com.huilian.hlej.jet.common.persistence.Page;

/**
 * 代理商库存管理Service
 * @author LongZhangWei
 * @date 2017年9月4日 下午2:37:14
 */
public interface DealerStockManagementService {

	/**
	 * 获取所有代理商库存
	 * @return
	 */
	public List<DealerStockVo> getDealerStockVoList(DealerStockVo dealerStockVo);
	
	/**
	 * 分页
	 * @param page
	 * @param dealerStockVo
	 * @return
	 */
	public Page<DealerStockVo> getDealerStockVoPage(Page<DealerStockVo> page, DealerStockVo dealerStockVo);
	
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
	 * 代理商库存明细分页
	 */
	public Page<DealerStockDetailVo> getDealerStockDetailVoPage(Page<DealerStockDetailVo> page, DealerStockDetailVo dealerStockDetailVo);
}
