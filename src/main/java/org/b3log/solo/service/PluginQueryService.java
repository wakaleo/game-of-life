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
package org.b3log.solo.service;


import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.Plugin;
import org.b3log.latke.plugin.AbstractPlugin;
import org.b3log.latke.plugin.PluginManager;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Paginator;
import org.b3log.solo.repository.PluginRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Plugin query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Oct 27, 2011
 * @since 0.4.0
 */
@Service
public class PluginQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PluginQueryService.class);

    /**
     * Plugin repository.
     */
    @Inject
    private PluginRepository pluginRepository;
    
    /**
     * Plugin manager.
     */
    @Inject
    private PluginManager pluginManager;

    /**
     * Gets plugins by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     * <pre>
     * {
     *     "paginationCurrentPageNum": 1,
     *     "paginationPageSize": 20,
     *     "paginationWindowSize": 10,
     * }, see {@link Pagination} for more details
     * </pre>
     * @return for example,
     * <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "plugins": [{
     *         "name": "",
     *         "version": "",
     *         "author": "",
     *         "status": "", // Enumeration name of {@link org.b3log.latke.plugin.PluginStatus}
     *      }, ....]
     * }
     * </pre>
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getPlugins(final JSONObject requestJSONObject)
        throws ServiceException {
        final JSONObject ret = new JSONObject();

        try {
            final int currentPageNum = requestJSONObject.getInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
            final int pageSize = requestJSONObject.getInt(Pagination.PAGINATION_PAGE_SIZE);
            final int windowSize = requestJSONObject.getInt(Pagination.PAGINATION_WINDOW_SIZE);

            final List<JSONObject> pluginJSONObjects = new ArrayList<JSONObject>();
            final List<AbstractPlugin> plugins = pluginManager.getPlugins();

            for (final AbstractPlugin plugin : plugins) {
                final JSONObject jsonObject = plugin.toJSONObject();

                pluginJSONObjects.add(jsonObject);
            }

            final int pageCount = (int) Math.ceil((double) pluginJSONObjects.size() / (double) pageSize);
            final JSONObject pagination = new JSONObject();

            ret.put(Pagination.PAGINATION, pagination);
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

            final int start = pageSize * (currentPageNum - 1);
            int end = start + pageSize;

            end = end > pluginJSONObjects.size() ? pluginJSONObjects.size() : end;
            ret.put(Plugin.PLUGINS, pluginJSONObjects.subList(start, end));

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets plugins failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * get the setting(json formatter) of the plugin by the specified pluginoId.
     * 
     * @param pluginId the specified pluginId
     * @return the {@link AbstractPlugin}
     * @throws ServiceException service exception
     * @throws JSONException json exception
     */
    public String getPluginSetting(final String pluginId) throws ServiceException, JSONException {

        JSONObject ret = null;

        try {
            ret = pluginRepository.get(pluginId);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "get plugin[" + pluginId + "] fail");
            throw new ServiceException("get plugin[" + pluginId + "] fail");

        }

        if (ret == null) {
            LOGGER.log(Level.ERROR, "can not find plugin[" + pluginId + "]");
            throw new ServiceException("can not find plugin[" + pluginId + "]");
        }

        return ret.optString(Plugin.PLUGIN_SETTING);
    }

    /**
     * Sets the plugin repository with the specified plugin repository.
     * 
     * @param pluginRepository the specified plugin repository
     */
    public void setPluginRepository(final PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }
}
