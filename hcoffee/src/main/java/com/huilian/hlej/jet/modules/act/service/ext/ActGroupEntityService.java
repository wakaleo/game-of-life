/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/tsingson">tsingson</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.act.service.ext;

import com.google.common.collect.Lists;
import com.huilian.hlej.jet.common.utils.SpringContextHolder;
import com.huilian.hlej.jet.modules.act.utils.ActUtils;
import com.huilian.hlej.jet.modules.sys.entity.Role;
import com.huilian.hlej.jet.modules.sys.entity.User;
import com.huilian.hlej.jet.modules.sys.service.SystemService;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Activiti Group Entity Service
 * @author hlej
 * @version 2013-12-05
 */
@Service
public class ActGroupEntityService extends GroupEntityManager {

	private SystemService systemService;

	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	public Group createNewGroup(String groupId) {
		return new GroupEntity(groupId);
	}

	public void insertGroup(Group group) {
		getDbSqlSession().insert((PersistentObject) group);
//		throw new RuntimeException("not implement method.");
	}

	public void updateGroup(GroupEntity updatedGroup) {
		CommandContext commandContext = Context.getCommandContext();
		DbSqlSession dbSqlSession = commandContext.getDbSqlSession();
		dbSqlSession.update(updatedGroup);
//		throw new RuntimeException("not implement method.");
	}

	public void deleteGroup(String groupId) {
		GroupEntity group = getDbSqlSession().selectById(GroupEntity.class, groupId);
		getDbSqlSession().delete("deleteMembershipsByGroupId", groupId);
		getDbSqlSession().delete(group);
//		throw new RuntimeException("not implement method.");
	}

	public GroupQuery createNewGroupQuery() {
	 return 	super.createNewGroupQuery();
/*		return new GroupQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
	throw new RuntimeException("not implement method.");*/
	}

	@SuppressWarnings("unchecked")
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		return getDbSqlSession().selectList("selectGroupByQueryCriteria", query, page);
//		throw new RuntimeException("not implement method.");
	}

	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		return (Long) getDbSqlSession().selectOne("selectGroupCountByQueryCriteria", query);
//		throw new RuntimeException("not implement method.");
	}

	public List<Group> findGroupsByUser(String userId) {
//		return getDbSqlSession().selectList("selectGroupsByUserId", userId);
		List<Group> list = Lists.newArrayList();
		User user = getSystemService().getUserByLoginName(userId);
		if (user != null && user.getRoleList() != null){
			for (Role role : user.getRoleList()){
				list.add(ActUtils.toActivitiGroup(role));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		return getDbSqlSession().selectListWithRawParameter("selectGroupByNativeQuery", parameterMap, firstResult, maxResults);
//		throw new RuntimeException("not implement method.");
	}

	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		return (Long) getDbSqlSession().selectOne("selectGroupCountByNativeQuery", parameterMap);
//		throw new RuntimeException("not implement method.");
	}

}
