/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.utils.excel.fieldtype;

import com.huilian.hlej.jet.common.utils.StringUtils;
import com.huilian.hlej.jet.modules.sys.entity.Area;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 *
 * @author hlej
 * @version 2013-03-10
 */
public class AreaType {

  /**
   * 获取对象值（导入）
   */
  public static Object getValue(String val) {
    for (Area e : UserUtils.getAreaList()) {
      if (StringUtils.trimToEmpty(val).equals(e.getName())) {
        return e;
      }
    }
    return null;
  }

  /**
   * 获取对象值（导出）
   */
  public static String setValue(Object val) {
    if (val != null && ((Area) val).getName() != null) {
      return ((Area) val).getName();
    }
    return "";
  }
}
