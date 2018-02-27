package com.huilian.hlej.hcf.service;

import java.util.List;

import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.TranQueryVo;
import com.huilian.hlej.hcf.vo.VendCustInfoVo;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.Page;

public interface VendCustInfoService {



	Page<VendCustInfoVo> getVendCustQuery(Page<VendCustInfoVo> page, VendCustInfoVo vendCustInfo);


	List<CommunityVo> getCommunityName();

}
