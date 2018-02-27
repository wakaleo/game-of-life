/**
 * Copyright &copy; 2014-2015 <a href="https://github.com/tsingson">tsingson</a> All rights reserved.
 */
package com.huilian.hlej.jet.modules.act.service.ext;

import com.google.common.collect.Lists;
import com.huilian.hlej.jet.common.utils.SpringContextHolder;
import com.huilian.hlej.jet.modules.act.utils.ActUtils;
import com.huilian.hlej.jet.modules.sys.entity.Role;
import com.huilian.hlej.jet.modules.sys.service.SystemService;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activiti User Entity Service
 * @author hlej
 * @version 2013-11-03
 */
@Service
public class ActUserEntityService extends UserEntityManager {

	private SystemService systemService;

	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	public User createNewUser(String userId) {
		return new UserEntity(userId);
	}

	public void insertUser(User user) {
		getDbSqlSession().insert((PersistentObject) user);
//		throw new RuntimeException("not implement method.");
	}

	public void updateUser(UserEntity updatedUser) {
		CommandContext commandContext = Context.getCommandContext();
		DbSqlSession dbSqlSession = commandContext.getDbSqlSession();
		dbSqlSession.update(updatedUser);
//		throw new RuntimeException("not implement method.");
	}

	public UserEntity findUserById(String userId) {
		return (UserEntity) getDbSqlSession().selectOne("selectUserById", userId);
//		return ActUtils.toActivitiUser(getSystemService().getUserByLoginName(userId));
	}

	public void deleteUser(String userId) {
//		UserEntity user = findUserById(userId);
//		if (user != null) {
//			List<IdentityInfoEntity> identityInfos = getDbSqlSession().selectList("selectIdentityInfoByUserId", userId);
//			for (IdentityInfoEntity identityInfo : identityInfos) {
//				getIdentityInfoManager().deleteIdentityInfo(identityInfo);
//			}
//			getDbSqlSession().delete("deleteMembershipsByUserId", userId);
//			user.delete();
//		}
		User user = findUserById(userId);
		if (user != null) {
			getSystemService().deleteUser(new com.huilian.hlej.jet.modules.sys.entity.User(user.getId()));
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		return getDbSqlSession().selectList("selectUserByQueryCriteria", query, page);
//		throw new RuntimeException("not implement method.");
	}

	public long findUserCountByQueryCriteria(UserQueryImpl query) {
		return (Long) getDbSqlSession().selectOne("selectUserCountByQueryCriteria", query);
//		throw new RuntimeException("not implement method.");
	}

	public List<Group> findGroupsByUser(String userId) {
//		return getDbSqlSession().selectList("selectGroupsByUserId", userId);
		List<Group> list = Lists.newArrayList();
		for (Role role : getSystemService().findRole(new Role(new com.huilian.hlej.jet.modules.sys.entity.User(null, userId)))){
			list.add(ActUtils.toActivitiGroup(role));
		}
		return list;
	}

	public UserQuery createNewUserQuery() {
		return super.createNewUserQuery();
		//return new UserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
//		throw new RuntimeException("not implement method.");
	}

	public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", userId);
		parameters.put("key", key);
		return (IdentityInfoEntity) getDbSqlSession().selectOne("selectIdentityInfoByUserIdAndKey", parameters);
//		throw new RuntimeException("not implement method.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", userId);
		parameters.put("type", type);
		return (List) getDbSqlSession().getSqlSession().selectList("selectIdentityInfoKeysByUserIdAndType", parameters);
//		throw new RuntimeException("not implement method.");
	}

	public Boolean checkPassword(String userId, String password) {
		User user = findUserById(userId);
		if ((user != null) && (password != null) && (password.equals(user.getPassword()))) {
			return true;
		}
		return false;
//		throw new RuntimeException("not implement method.");
	}

	@SuppressWarnings("unchecked")
	public List<User> findPotentialStarterUsers(String proceDefId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("procDefId", proceDefId);
		return (List<User>) getDbSqlSession().selectOne("selectUserByQueryCriteria", parameters);
//		throw new RuntimeException("not implement method.");

	}

	public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
//		return getDbSqlSession().selectListWithRawParameter("selectUserByNativeQuery", parameterMap, firstResult, maxResults);
		throw new RuntimeException("not implement method.");
	}

	public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
//		return (Long) getDbSqlSession().selectOne("selectUserCountByNativeQuery", parameterMap);
		throw new RuntimeException("not implement method.");
	}

}
