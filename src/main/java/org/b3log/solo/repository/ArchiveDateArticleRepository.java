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
 * Archive date-Article repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Nov 9, 2011
 * @since 0.3.1
 */
public interface ArchiveDateArticleRepository extends Repository {

    /**
     * Gets archive date-article relations by the specified archive date id.
     *
     * @param archiveDateId the specified archive date id
     * @param currentPageNum the specified current page number, MUST greater
     * then {@code 0}
     * @param pageSize the specified page size(count of a page contains objects),
     * MUST greater then {@code 0}
     * @return for example
     * <pre>
     * {
     *     "pagination": {
     *       "paginationPageCount": 88250
     *     },
     *     "rslts": [{
     *         "oId": "",
     *         "archiveDate_oId": "",
     *         "article_oId": ""
     *     }, ....]
     * }
     * </pre>
     * @throws RepositoryException repository exception
     */
    JSONObject getByArchiveDateId(final String archiveDateId,
        final int currentPageNum,
        final int pageSize)
        throws RepositoryException;

    /**
     * Gets an archive date-article relations by the specified article id.
     *
     * @param articleId the specified article id
     * @return for example
     * <pre>
     * {
     *     "archiveDate_oId": "",
     *     "article_oId": articleId
     * }, returns {@code null} if not found
     * </pre>
     * @throws RepositoryException repository exception
     */
    JSONObject getByArticleId(final String articleId)
        throws RepositoryException;
}
