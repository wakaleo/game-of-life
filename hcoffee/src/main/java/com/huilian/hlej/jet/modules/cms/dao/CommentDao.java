/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/hlej">hlej</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.cms.dao;

import com.huilian.hlej.jet.common.persistence.CrudDao;
import com.huilian.hlej.jet.common.persistence.annotation.MyBatisDao;
import com.huilian.hlej.jet.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 * @author hlej
 * @version 2013-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}
