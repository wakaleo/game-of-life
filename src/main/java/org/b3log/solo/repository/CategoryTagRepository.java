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
 * Category-Tag relation repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.1, Mar 31, 2017
 * @since 2.0.0
 */
public interface CategoryTagRepository extends Repository {

    /**
     * Gets category-tag relations by the specified category id.
     *
     * @param categoryId     the specified category id
     * @param currentPageNum the specified current page number, MUST greater then {@code 0}
     * @param pageSize       the specified page size(count of a page contains objects), MUST greater then {@code 0}
     * @return for example      <pre>
     * {
     *     "pagination": {
     *       "paginationPageCount": 88250
     *     },
     *     "rslts": [{
     *         "oId": "",
     *         "category_oId": categoryId,
     *         "tag_oId": ""
     *     }, ....]
     * }
     * </pre>
     * @throws RepositoryException repository exception
     */
    JSONObject getByCategoryId(final String categoryId, final int currentPageNum, final int pageSize)
            throws RepositoryException;

    /**
     * Gets category-tag relations by the specified tag id.
     *
     * @param tagId          the specified tag id
     * @param currentPageNum the specified current page number, MUST greater then {@code 0}
     * @param pageSize       the specified page size(count of a page contains objects), MUST greater then {@code 0}
     * @return for example      <pre>
     * {
     *     "pagination": {
     *       "paginationPageCount": 88250
     *     },
     *     "rslts": [{
     *         "oId": "",
     *         "category_oId": "",
     *         "tag_oId": tagId
     *     }, ....]
     * }
     * </pre>
     * @throws RepositoryException repository exception
     */
    JSONObject getByTagId(final String tagId, final int currentPageNum, final int pageSize)
            throws RepositoryException;

    /**
     * Removes category-tag relations by the specified category id.
     *
     * @param categoryId the specified category id
     * @throws RepositoryException repository exception
     */
    void removeByCategoryId(final String categoryId) throws RepositoryException;

    /**
     * Removes category-tag relations by the specified tag id.
     *
     * @param tagId the specified tag id
     * @throws RepositoryException repository exception
     */
    void removeByTagId(final String tagId) throws RepositoryException;
}
