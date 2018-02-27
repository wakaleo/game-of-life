/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.persistence.proxy;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * <p>
 * 自定义Mybatis的配置，扩展.
 * </p>
 *
 * @author poplar.yfyang
 * @version 1.0 2012-05-13 上午10:06
 * @since JDK 1.5
 */
public class PageConfiguration extends Configuration {

  protected MapperRegistry mapperRegistry = new PaginationMapperRegistry(this);

  @Override
  public <T> void addMapper(Class<T> type) {
    mapperRegistry.addMapper(type);
  }

  @Override
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    return mapperRegistry.getMapper(type, sqlSession);
  }

  @Override
  public boolean hasMapper(Class<?> type) {
    return mapperRegistry.hasMapper(type);
  }
}
