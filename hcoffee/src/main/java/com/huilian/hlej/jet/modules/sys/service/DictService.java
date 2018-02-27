/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.common.service.CrudService;
import com.huilian.hlej.jet.common.utils.CacheUtils;
import com.huilian.hlej.jet.modules.sys.dao.DictDao;
import com.huilian.hlej.jet.modules.sys.entity.Dict;
import com.huilian.hlej.jet.modules.sys.utils.DictUtils;

/**
 * 字典Service
 *
 * @author huilian.hlej
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

  /**
   * 查询字段类型列表
   *
   * @return
   */
  public List<String> findTypeList() {
    return dao.findTypeList(new Dict());
  }

  @Transactional(readOnly = false)
  public void save(Dict dict) {
    super.save(dict);
    CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
  }

  @Transactional(readOnly = false)
  public void delete(Dict dict) {
    super.delete(dict);
    CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
  }

}
