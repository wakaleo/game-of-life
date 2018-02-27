package com.huilian.hlej.hcf.service;

import java.util.Map;

import com.huilian.hlej.hcf.vo.TranQueryVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendingStateService {

    Page<VendingStartVo> getVendingStatisticsPage(Page<VendingStartVo> page,
    		VendingStartVo vendingStartVo);

    
	Page<TranQueryVo> getTransactionQueryPage(Page<TranQueryVo> page, TranQueryVo tranQueryVo);


	Map<String, String> updateOrderStauts(TranQueryVo tranQueryVo);

}
