/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.supcan.annotation.common.properties;

import java.lang.annotation.*;

/**
 * 硕正Background注解
 *
 * @author WangZhen
 * @version 2013-11-12
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SupBackground {

  /**
   * 背景颜色
   *
   * @return
   */
  String bgColor() default "";

}
