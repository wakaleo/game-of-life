/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.vo.CommunityVo;
import com.huilian.hlej.hcf.vo.TranQueryVo;
import com.huilian.hlej.hcf.vo.VendCustInfoVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * @author YANGBO
 * @version 2017/5/14
 */
@MyBatisDao
public interface VendCustInfo extends CrudDao<VendCustInfoVo> {
	
	public List<VendCustInfoVo> getVendCustQuery(VendCustInfoVo vendCustInfo);

	public List<CommunityVo> getCommunityName();
	

}
