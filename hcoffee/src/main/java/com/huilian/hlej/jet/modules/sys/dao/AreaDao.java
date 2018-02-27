/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.dao;

import com.huilian.hlej.jet.common.persistence.TreeDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.Area;

/**
 * 区域DAO接口
 *
 * @author huilian.hlej
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

}
