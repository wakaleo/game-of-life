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
 * Archive date repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Jul 2, 2011
 * @since 0.3.1
 */
public interface ArchiveDateRepository extends Repository {

    /**
     * Gets an archive date by the specified archive date string.
     *
     * @param archiveDate the specified archive date stirng (yyyy/MM)
     * @return an archive date, {@code null} if not found
     * @throws RepositoryException repository exception
     */
    JSONObject getByArchiveDate(final String archiveDate) throws RepositoryException;

    /**
     * Gets archive dates.
     *
     * @return a list of archive date, returns an empty list if
     * not found
     * @throws RepositoryException repository exception
     */
    List<JSONObject> getArchiveDates() throws RepositoryException;
}
