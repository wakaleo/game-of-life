/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.dao;

import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.sys.entity.Role;

/**
 * 角色DAO接口
 *
 * @author huilian.hlej
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

  public Role getByName(Role role);

  public Role getByEnname(Role role);

  /**
   * 维护角色与菜单权限关系
   *
   * @param role
   * @return
   */
  public int deleteRoleMenu(Role role);

  public int insertRoleMenu(Role role);

  /**
   * 维护角色与公司部门关系
   *
   * @param role
   * @return
   */
  public int deleteRoleOffice(Role role);

  public int insertRoleOffice(Role role);

}
