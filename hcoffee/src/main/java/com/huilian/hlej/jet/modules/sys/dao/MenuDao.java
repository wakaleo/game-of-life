/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.dao;

import java.util.List;

import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 *
 * @author huilian.hlej
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

  public List<Menu> findByParentIdsLike(Menu menu);

  public List<Menu> findByUserId(Menu menu);

  public int updateParentIds(Menu menu);

  public int updateSort(Menu menu);

}
