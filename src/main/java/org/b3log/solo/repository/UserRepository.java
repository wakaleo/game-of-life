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
package org.b3log.solo.repository;


import org.b3log.latke.repository.Repository;
import org.b3log.latke.repository.RepositoryException;
import org.json.JSONObject;


/**
 * User repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.4, Nov 8, 2011
 * @since 0.3.1
 */
public interface UserRepository extends Repository {

    /**
     * Gets a user by the specified email.
     *
     * @param email the specified email
     * @return user, returns {@code null} if not found
     * @throws RepositoryException repository exception 
     */
    JSONObject getByEmail(final String email) throws RepositoryException;

    /**
     * Determine whether the specified email is administrator's.
     *
     * @param email the specified email
     * @return {@code true} if it is administrator's email, {@code false}
     * otherwise
     * @throws RepositoryException repository exception
     */
    boolean isAdminEmail(final String email) throws RepositoryException;

    /**
     * Gets the administrator user.
     *
     * @return administrator user, returns {@code null} if not found or error
     * @throws RepositoryException repository exception
     */
    JSONObject getAdmin() throws RepositoryException;
}
