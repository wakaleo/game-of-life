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
public interface CrudDao<T> extends BaseDao {

  /**
   * 获取单条数据
   *
   * @param id
   * @return
   */
  public T get(String id);

  /**
   * 获取单条数据
   *
   * @param entity
   * @return
   */
  public T get(T entity);

  /**
   * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
   *
   * @param entity
   * @return
   */
  public List<T> findList(T entity);

  /**
   * 查询所有数据列表
   *
   * @param entity
   * @return
   */
  public List<T> findAllList(T entity);

  /**
   * 查询所有数据列表
   *
   * @return
   * @see public List<T> findAllList(T entity)
   */
  @Deprecated
  public List<T> findAllList();

  /**
   * 插入数据
   *
   * @param entity
   * @return
   */
  public int insert(T entity);

  /**
   * 更新数据
   *
   * @param entity
   * @return
   */
  public int update(T entity);

  /**
   * 删除数据（一般为逻辑删除，更新del_flag字段为1）
   *
   * @param id
   * @return
   * @see public int delete(T entity)
   */
  @Deprecated
  public int delete(String id);

  /**
   * 删除数据（一般为逻辑删除，更新del_flag字段为1）
   *
   * @param entity
   * @return
   */
  public int delete(T entity);

}
