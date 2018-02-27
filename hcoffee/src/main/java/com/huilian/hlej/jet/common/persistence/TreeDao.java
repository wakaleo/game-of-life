/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.persistence;

import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author hlej
 * @version 2014-05-16
 */
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

  /**
   * 找到所有子节点
   *
   * @param entity
   * @return
   */
  public List<T> findByParentIdsLike(T entity);

  /**
   * 更新所有父节点字段
   *
   * @param entity
   * @return
   */
  public int updateParentIds(T entity);

}
