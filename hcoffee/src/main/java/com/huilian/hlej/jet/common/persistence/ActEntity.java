/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huilian.hlej.jet.modules.act.entity.Act;

import java.io.Serializable;

/**
 * Activiti Entity类
 *
 * @author hlej
 * @version 2013-05-28
 */
public abstract class ActEntity<T> extends DataEntity<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Act act;    // 流程任务对象

  public ActEntity() {
    super();
  }

  public ActEntity(String id) {
    super(id);
  }

  @JsonIgnore
  public Act getAct() {
    if (act == null) {
      act = new Act();
    }
    return act;
  }

  public void setAct(Act act) {
    this.act = act;
  }

  /**
   * 获取流程实例ID
   *
   * @return
   */
  public String getProcInsId() {
    return this.getAct().getProcInsId();
  }

  /**
   * 设置流程实例ID
   *
   * @param procInsId
   */
  public void setProcInsId(String procInsId) {
    this.getAct().setProcInsId(procInsId);
  }
}
