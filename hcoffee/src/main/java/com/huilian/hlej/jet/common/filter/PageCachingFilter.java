/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.filter;

import com.huilian.hlej.jet.common.utils.CacheUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面高速缓存过滤器
 *
 * @author hlej
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

  @Override
  protected CacheManager getCacheManager() {
    return CacheUtils.getCacheManager();
  }

}
