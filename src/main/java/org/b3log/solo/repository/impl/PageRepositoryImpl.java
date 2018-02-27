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
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.cache.PageCache;
import org.b3log.solo.model.Page;
import org.b3log.solo.repository.PageRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Page repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.9, Jul 18, 2017
 * @since 0.3.1
 */
@Repository
public class PageRepositoryImpl extends AbstractRepository implements PageRepository {

    /**
     * Page cache.
     */
    @Inject
    private PageCache pageCache;

    /**
     * Public constructor.
     */
    public PageRepositoryImpl() {
        super(Page.PAGE);
    }

    @Override
    public void remove(final String id) throws RepositoryException {
        super.remove(id);

        pageCache.removePage(id);
    }

    @Override
    public JSONObject get(final String id) throws RepositoryException {
        JSONObject ret = pageCache.getPage(id);
        if (null != ret) {
            return ret;
        }

        ret = super.get(id);
        if (null == ret) {
            return null;
        }

        pageCache.putPage(ret);

        return ret;
    }

    @Override
    public void update(final String id, final JSONObject page) throws RepositoryException {
        super.update(id, page);

        page.put(Keys.OBJECT_ID, id);
        pageCache.putPage(page);
    }

    @Override
    public JSONObject getByPermalink(final String permalink) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_PERMALINK, FilterOperator.EQUAL, permalink)).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    @Override
    public int getMaxOrder() throws RepositoryException {
        final Query query = new Query().addSort(Page.PAGE_ORDER, SortDirection.DESCENDING).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        if (0 == array.length()) {
            return -1;
        }

        return array.optJSONObject(0).optInt(Page.PAGE_ORDER);
    }

    @Override
    public JSONObject getUpper(final String id) throws RepositoryException {
        final JSONObject page = get(id);

        if (null == page) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.LESS_THAN, page.optInt(Page.PAGE_ORDER))).addSort(Page.PAGE_ORDER, SortDirection.DESCENDING).setCurrentPageNum(1).setPageSize(1).setPageCount(
                1);

        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    @Override
    public JSONObject getUnder(final String id) throws RepositoryException {
        final JSONObject page = get(id);

        if (null == page) {
            return null;
        }

        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.GREATER_THAN, page.optInt(Page.PAGE_ORDER))).addSort(Page.PAGE_ORDER, SortDirection.ASCENDING).setCurrentPageNum(1).setPageSize(1).setPageCount(
                1);

        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        if (1 != array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    @Override
    public JSONObject getByOrder(final int order) throws RepositoryException {
        final Query query = new Query().setFilter(new PropertyFilter(Page.PAGE_ORDER, FilterOperator.EQUAL, order)).setPageCount(1);
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        if (0 == array.length()) {
            return null;
        }

        return array.optJSONObject(0);
    }

    @Override
    public List<JSONObject> getPages() throws RepositoryException {
        final Query query = new Query().addSort(Page.PAGE_ORDER, SortDirection.ASCENDING).setPageCount(1);
        final JSONObject result = get(query);

        return CollectionUtils.jsonArrayToList(result.optJSONArray(Keys.RESULTS));
    }
}
