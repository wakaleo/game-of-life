package com.huilian.hlej.jet.common.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.sql.DataSource;

/**
 * Spring 单元测试基类
 *
 * @author hlej
 * @version 2013-05-15
 */
@ActiveProfiles("production")
@ContextConfiguration(locations = {"/spring-context.xml"})
public class SpringTransactionalContextTests extends AbstractTransactionalJUnit4SpringContextTests {

  protected DataSource dataSource;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    super.setDataSource(dataSource);
    this.dataSource = dataSource;
  }

}
