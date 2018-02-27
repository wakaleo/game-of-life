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
 * Link repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.3, Nov 2, 2011
 * @since 0.3.1
 */
public interface LinkRepository extends Repository {

    /**
     * Gets a link by the specified address.
     *
     * @param address the specified address
     * @return link, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByAddress(final String address) throws RepositoryException;

    /**
     * Gets the maximum order.
     * 
     * @return order number, returns {@code -1} if not found
     * @throws RepositoryException repository exception
     */
    int getMaxOrder() throws RepositoryException;

    /**
     * Gets the upper link of the link specified by the given id.
     * 
     * @param id the given id
     * @return upper link, returns {@code null} if not found
     * @throws RepositoryException repository exception 
     */
    JSONObject getUpper(final String id) throws RepositoryException;

    /**
     * Gets the under link of the link specified by the given id.
     * 
     * @param id the given id
     * @return under link, returns {@code null} if not found
     * @throws RepositoryException repository exception 
     */
    JSONObject getUnder(final String id) throws RepositoryException;

    /**
     * Gets a link by the specified order.
     *
     * @param order the specified order
     * @return link, returns {@code null} if not found
     * @throws RepositoryException repository exception 
     */
    JSONObject getByOrder(final int order) throws RepositoryException;
}
