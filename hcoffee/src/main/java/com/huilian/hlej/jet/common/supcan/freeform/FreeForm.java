/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.supcan.freeform;

import com.huilian.hlej.jet.common.supcan.common.Common;
import com.huilian.hlej.jet.common.supcan.common.properties.Properties;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 硕正FreeForm
 *
 * @author WangZhen
 * @version 2013-11-04
 */
@XStreamAlias("FreeForm")
public class FreeForm extends Common {

  public FreeForm() {
    super();
  }

  public FreeForm(Properties properties) {
    this();
    this.properties = properties;
  }

}
