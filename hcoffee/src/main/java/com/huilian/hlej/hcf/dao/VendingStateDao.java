/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.hcf.dao;

import java.util.List;

import com.huilian.hlej.hcf.entity.AppUpgradeTask;
import com.huilian.hlej.hcf.vo.VendingMachineVo;
import com.huilian.hlej.hcf.vo.VendingStartVo;
import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
 

/**
 * 售货机升级任务dao
 * @author liujian
 * @version 2016/12/14
 */
@MyBatisDao
public interface VendingStateDao extends CrudDao<AppUpgradeTask> {
	public List<VendingStartVo> getVendingStatisticsPage(VendingStartVo vendingStartVo);

}
