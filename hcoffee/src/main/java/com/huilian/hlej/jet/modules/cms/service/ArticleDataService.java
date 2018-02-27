/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huilian.hlej.jet.common.service.CrudService;
import com.huilian.hlej.jet.modules.cms.dao.ArticleDataDao;
import com.huilian.hlej.jet.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author hlej
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
