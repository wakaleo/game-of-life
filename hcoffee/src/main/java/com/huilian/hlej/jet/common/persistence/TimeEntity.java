/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.persistence;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 
 * @ClassName: TimeEntity 
 * @Description:  数据Entity类
 * @author: caoyd
 * @date: 2016年11月21日 上午9:35:59 
 * @param <T>
 */
public abstract class TimeEntity<T> extends BaseEntity<T> {

  private static final long serialVersionUID = 1L;

  protected String datasource;  // 备注
  protected User createBy;  // 创建者
  protected Date createTime;  // V2 创建日期
  protected User updateBy;  // 更新者
  protected Date updateTime;  // V2 更新日期
  protected String dataStatus;  // V2 删除标记（0：正常；1：删除；2：
  

  public TimeEntity() {
    super();
    this.dataStatus = "0";
  }

  public TimeEntity(String id) {
    super(id);
  }

  /**
   * 插入之前执行方法，需要手动调用
   */
  @Override
  public void preInsert() {
    // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
    if (!this.isNewRecord) {
      //setId(IdGen.uuid());
    }
    User user = UserUtils.getUser();
    if (StringUtils.isNotBlank(user.getId())) {
      this.updateBy = user;
      this.createBy = user;
    }
    this.updateTime = new Date();
    this.createTime = this.updateTime;
  }

  /**
   * 更新之前执行方法，需要手动调用
   */
  @Override
  public void preUpdate() {
    User user = UserUtils.getUser();
    if (StringUtils.isNotBlank(user.getId())) {
      this.updateBy = user;
    }
    this.updateTime = new Date();
  }

  @Length(min = 1, max = 1)
  public String getDatasource() {
    return datasource;
  }

  public void setDatasource(String datasource) {
    this.datasource = datasource;
  }

  @JsonIgnore
  public User getCreateBy() {
    return createBy;
  }

  public void setCreateBy(User createBy) {
    this.createBy = createBy;
  }

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @JsonIgnore
  public User getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(User updateBy) {
    this.updateBy = updateBy;
  }

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @JsonIgnore
  public String getDataStatus() {
    return dataStatus;
  }

  public void setDataStatus(String dataStatus) {
    this.dataStatus = dataStatus;
  }

}
