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
 * Comment repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.4, Oct 26, 2011
 * @since 0.3.1
 */
public interface CommentRepository extends Repository {

    /**
     * Gets post comments recently with the specified fetch.
     *
     * @param fetchSize the specified fetch size
     * @return a list of comments recently, returns an empty list if not found
     * @throws RepositoryException repository exception 
     */
    List<JSONObject> getRecentComments(final int fetchSize)
        throws RepositoryException;

    /**
     * Gets comments with the specified on id, current page number and 
     * page size.
     * 
     * @param onId the specified on id
     * @param currentPageNum the specified current page number
     * @param pageSize the specified page size
     * @return a list of comments, returns an empty list if not found
     * @throws RepositoryException repository exception 
     */
    List<JSONObject> getComments(final String onId,
        final int currentPageNum,
        final int pageSize) throws RepositoryException;

    /**
     * Removes comments with the specified on id.
     * 
     * @param onId the specified on id
     * @return removed count
     * @throws RepositoryException repository exception 
     */
    int removeComments(final String onId) throws RepositoryException;
}
