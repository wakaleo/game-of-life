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

import java.util.List;

/**
 * Category repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.0.1, Apr 8, 2017
 * @since 2.0.0
 */
public interface CategoryRepository extends Repository {

    /**
     * Gets a category by the specified category title.
     *
     * @param categoryTitle the specified category title
     * @return a category, {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByTitle(final String categoryTitle) throws RepositoryException;

    /**
     * Gets a category by the specified category URI.
     *
     * @param categoryURI the specified category URI
     * @return a category, {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByURI(final String categoryURI) throws RepositoryException;

    /**
     * Gets the maximum order.
     *
     * @return order number, returns {@code -1} if not found
     * @throws RepositoryException repository exception
     */
    int getMaxOrder() throws RepositoryException;

    /**
     * Gets the upper category of the category specified by the given id.
     *
     * @param id the given id
     * @return upper category, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getUpper(final String id) throws RepositoryException;

    /**
     * Gets the under category of the category specified by the given id.
     *
     * @param id the given id
     * @return under category, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getUnder(final String id) throws RepositoryException;

    /**
     * Gets a category by the specified order.
     *
     * @param order the specified order
     * @return category, returns {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByOrder(final int order) throws RepositoryException;

    /**
     * Gets most used categories (contains the most tags) with the specified number.
     *
     * @param num the specified number
     * @return a list of most used categories, returns an empty list if not found
     * @throws RepositoryException repository exception
     */
    List<JSONObject> getMostUsedCategories(final int num) throws RepositoryException;
}
