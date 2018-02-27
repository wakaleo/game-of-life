/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.common.service.TreeService;
import com.huilian.hlej.jet.modules.sys.dao.AreaDao;
import com.huilian.hlej.jet.modules.sys.entity.Area;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 区域Service
 *
 * @author huilian.hlej
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

  public List<Area> findAll() {
    return UserUtils.getAreaList();
  }

  @Transactional(readOnly = false)
  public void save(Area area) {
    super.save(area);
    UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
  }

  @Transactional(readOnly = false)
  public void delete(Area area) {
    super.delete(area);
    UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
  }

}
