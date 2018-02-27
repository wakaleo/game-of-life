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
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.solo.model.Category;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.CategoryTagRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Category-Tag relation repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Mar 31, 2017
 * @since 2.0.0
 */
@Repository
public class CategoryTagRepositoryImpl extends AbstractRepository implements CategoryTagRepository {

    /**
     * Public constructor.
     */
    public CategoryTagRepositoryImpl() {
        super(Category.CATEGORY + "_" + Tag.TAG);
    }

    @Override
    public JSONObject getByCategoryId(final String categoryId, final int currentPageNum, final int pageSize)
            throws RepositoryException {
        final Query query = new Query().
                setFilter(new PropertyFilter(Category.CATEGORY + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, categoryId)).
                setCurrentPageNum(currentPageNum).setPageSize(pageSize).setPageCount(1);

        return get(query);
    }

    @Override
    public JSONObject getByTagId(final String tagId, final int currentPageNum, final int pageSize)
            throws RepositoryException {
        final Query query = new Query().
                setFilter(new PropertyFilter(Tag.TAG + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, tagId)).
                setCurrentPageNum(currentPageNum).setPageSize(pageSize).setPageCount(1);

        return get(query);
    }

    @Override
    public void removeByCategoryId(final String categoryId) throws RepositoryException {
        final Query query = new Query().
                setFilter(new PropertyFilter(Category.CATEGORY + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, categoryId));
        final JSONArray relations = get(query).optJSONArray(Keys.RESULTS);
        for (int i = 0; i < relations.length(); i++) {
            final JSONObject rel = relations.optJSONObject(i);
            remove(rel.optString(Keys.OBJECT_ID));
        }
    }

    @Override
    public void removeByTagId(final String tagId) throws RepositoryException {
        final Query query = new Query().
                setFilter(new PropertyFilter(Tag.TAG + "_" + Keys.OBJECT_ID, FilterOperator.EQUAL, tagId));
        final JSONArray relations = get(query).optJSONArray(Keys.RESULTS);
        for (int i = 0; i < relations.length(); i++) {
            final JSONObject rel = relations.optJSONObject(i);
            remove(rel.optString(Keys.OBJECT_ID));
        }
    }
}
