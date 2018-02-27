/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/tsingson">tsingson</a> All rights reserved.
 */
package com.huilian.hlej.jet.common.persistence;

import com.huilian.hlej.jet.common.utils.IdGen;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.utils.UserUtils;

/**
 * 无共用字段的数据Entity类
 * 
 * @author tsingson
 * @version 2015-08-18
 */
public abstract class BaseDataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	public BaseDataEntity() {
		super();
		// this.delFlag = DEL_FLAG_NORMAL;
	}

	public BaseDataEntity(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord) {
			setId(IdGen.uuid());
		}
		@SuppressWarnings("unused")
		User user = UserUtils.getUser();
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate() {
		@SuppressWarnings("unused")
		User user = UserUtils.getUser();

	}
}