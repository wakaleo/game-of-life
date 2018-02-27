/*
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.solo.repository.impl;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.cache.UserCache;
import org.b3log.solo.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.9, Aug 27, 2017
 * @since 0.3.1
 */
@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    /**
     * User cache.
     */
    @Inject
    private UserCache userCache;

    /**
     * Public constructor.
     */
    public UserRepositoryImpl() {
        super(User.USER);
    }

    @Override
    public void remove(final String id) throws RepositoryException {
        super.remove(id);

        userCache.removeUser(id);
    }

    @Override
    public JSONObject get(final String id) throws RepositoryException {
        JSONObject ret = userCache.getUser(id);
        if (null != ret) {
            return ret;
        }

        ret = super.get(id);
        if (null == ret) {
            return null;
        }

        userCache.putUser(ret);

        return ret;
    }

    @Override
    public void update(final String id, final JSONObject user) throws RepositoryException {
        super.update(id, user);

        user.put(Keys.OBJECT_ID, id);
        userCache.putUser(user);

        if (Role.ADMIN_ROLE.equals(user.optString(User.USER_ROLE))) {
            userCache.putAdmin(user);
        }
    }

    @Override
    public JSONObject getByEmail(final String email) throws RepositoryException {
        JSONObject ret = userCache.getUserByEmail(email);
        if (null != ret) {
            return ret;
        }

        final Query query = new Query().setPageCount(1).
                setFilter(new PropertyFilter(User.USER_EMAIL, FilterOperator.EQUAL, email.toLowerCase().trim()));

        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        ret = array.optJSONObject(0);
        userCache.putUser(ret);

        return ret;
    }

    @Override
    public JSONObject getAdmin() throws RepositoryException {
        JSONObject ret = userCache.getAdmin();
        if (null != ret) {
            return ret;
        }

        final Query query = new Query().setFilter(new PropertyFilter(User.USER_ROLE, FilterOperator.EQUAL, Role.ADMIN_ROLE)).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);
        if (0 == array.length()) {
            return null;
        }

        ret = array.optJSONObject(0);
        userCache.putAdmin(ret);

        return ret;
    }

    @Override
    public boolean isAdminEmail(final String email) throws RepositoryException {
        final JSONObject user = getByEmail(email);
        if (null == user) {
            return false;
        }

        return Role.ADMIN_ROLE.equals(user.optString(User.USER_ROLE));
    }
}
