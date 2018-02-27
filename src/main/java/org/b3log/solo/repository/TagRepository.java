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
 * Tag repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Aug 12, 2010
 * @since 0.3.1
 */
public interface TagRepository extends Repository {

    /**
     * Gets tags of an article specified by the article id.
     * 
     * @param articleId the specified article id
     * @return a list of tags of the specified article, returns an empty list
     * if not found
     * @throws RepositoryException repository exception
     */
    List<JSONObject> getByArticleId(final String articleId)
        throws RepositoryException;

    /**
     * Gets a tag by the specified tag title.
     *
     * @param tagTitle the specified tag title
     * @return a tag, {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByTitle(final String tagTitle) throws RepositoryException;

    /**
     * Gets most used tags with the specified number.
     *
     * @param num the specified number
     * @return a list of most used tags, returns an empty list if not found
     * @throws RepositoryException repository exception
     */
    List<JSONObject> getMostUsedTags(final int num) throws RepositoryException;
}
