/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.supcan.common.properties;

import com.huilian.hlej.jet.common.supcan.annotation.common.properties.SupBackground;
import com.huilian.hlej.jet.common.utils.ObjectUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 硕正TreeList Properties Background
 *
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("Background")
public class Background {

  /**
   * 背景颜色
   */
  @XStreamAsAttribute
  private String bgColor = "#FDFDFD";

  public Background() {

  }

  public Background(SupBackground supBackground) {
    this();
    ObjectUtils.annotationToObject(supBackground, this);
  }

  public Background(String bgColor) {
    this();
    this.bgColor = bgColor;
  }

  public String getBgColor() {
    return bgColor;
  }

  public void setBgColor(String bgColor) {
    this.bgColor = bgColor;
  }
}
