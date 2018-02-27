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
package org.b3log.solo.cache;

import org.b3log.latke.Keys;
import org.b3log.latke.cache.Cache;
import org.b3log.latke.cache.CacheFactory;
import org.b3log.latke.ioc.inject.Named;
import org.b3log.latke.ioc.inject.Singleton;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.solo.util.JSONs;
import org.json.JSONObject;

/**
 * User cache.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Aug 27, 2017
 * @since 2.3.0
 */
@Named
@Singleton
public class UserCache {

    /**
     * Id, User.
     */
    private Cache idCache = CacheFactory.getCache(User.USERS + "ID");

    /**
     * Email, User.
     */
    private Cache emailCache = CacheFactory.getCache(User.USERS + "Email");

    /**
     * Admin user.
     */
    private Cache adminCache = CacheFactory.getCache("adminUser");

    /**
     * Gets the admin user.
     *
     * @return admin user
     */
    public JSONObject getAdmin() {
        return adminCache.get(Role.ADMIN_ROLE);
    }

    /**
     * Adds or updates the admin user.
     *
     * @param admin the specified admin user
     */
    public void putAdmin(final JSONObject admin) {
        adminCache.put(Role.ADMIN_ROLE, admin);
    }

    /**
     * Gets a user by the specified user id.
     *
     * @param userId the specified user id
     * @return user, returns {@code null} if not found
     */
    public JSONObject getUser(final String userId) {
        final JSONObject user = idCache.get(userId);
        if (null == user) {
            return null;
        }

        return JSONs.clone(user);
    }

    /**
     * Gets a user by the specified user email.
     *
     * @param userEmail the specified user email
     * @return user, returns {@code null} if not found
     */
    public JSONObject getUserByEmail(final String userEmail) {
        final JSONObject user = emailCache.get(userEmail);
        if (null == user) {
            return null;
        }

        return JSONs.clone(user);
    }

    /**
     * Adds or updates the specified user.
     *
     * @param user the specified user
     */
    public void putUser(final JSONObject user) {
        idCache.put(user.optString(Keys.OBJECT_ID), JSONs.clone(user));
        emailCache.put(user.optString(User.USER_EMAIL), JSONs.clone(user));
    }

    /**
     * Removes a user by the specified user id.
     *
     * @param id the specified user id
     */
    public void removeUser(final String id) {
        final JSONObject user = idCache.get(id);
        if (null == user) {
            return;
        }

        idCache.remove(id);

        final String email = user.optString(User.USER_EMAIL);
        emailCache.remove(email);
    }
}
