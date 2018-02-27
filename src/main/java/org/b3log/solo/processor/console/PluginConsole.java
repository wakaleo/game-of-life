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
package org.b3log.solo.processor.console;


import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Plugin;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.Before;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.b3log.solo.processor.console.common.ProcessAuthAdvice;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.service.PluginMgmtService;
import org.b3log.solo.service.PluginQueryService;
import org.b3log.solo.util.QueryResults;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Plugin console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="mailto:wmainlove@gmail.com">Love Yao</a>
 * @version 1.1.0.0, Jan 17, 2013
 * @since 0.4.0
 */
@RequestProcessor
@Before(adviceClass = ProcessAuthAdvice.class)
public class PluginConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PluginConsole.class);

    /**
     * Plugin query service.
     */
    @Inject
    private PluginQueryService pluginQueryService;

    /**
     * Plugin management service.
     */
    @Inject
    private PluginMgmtService pluginMgmtService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Sets a plugin's status with the specified plugin id, status.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/plugin/status/", method = HTTPRequestMethod.PUT)
    public void setPluginStatus(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);

        final String pluginId = requestJSONObject.getString(Keys.OBJECT_ID);
        final String status = requestJSONObject.getString(Plugin.PLUGIN_STATUS);

        final JSONObject result = pluginMgmtService.setPluginStatus(pluginId, status);

        renderer.setJSONObject(result);
    }

    /**
     * Gets plugins by the specified request.
     * <p>
     * The request URI contains the pagination arguments. For example, the
     * request URI is /console/plugins/1/10/20, means the current page is 1, the
     * page size is 10, and the window size is 20.
     * </p>
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
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
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     * @see Requests#PAGINATION_PATH_PATTERN
     */
    @RequestProcessing(value = "/console/plugins/*/*/*"/* Requests.PAGINATION_PATH_PATTERN */,
            method = HTTPRequestMethod.GET)
    public void getPlugins(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final String requestURI = request.getRequestURI();
            final String path = requestURI.substring((Latkes.getContextPath() + "/console/plugins/").length());

            final JSONObject requestJSONObject = Requests.buildPaginationRequest(path);

            final JSONObject result = pluginQueryService.getPlugins(requestJSONObject);

            renderer.setJSONObject(result);

            result.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * get the info of the specified pluginoId,just fot the plugin-setting.
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @param renderer the specified {@link ConsoleRenderer}
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/plugin/toSetting", method = HTTPRequestMethod.POST)
    public void toSetting(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context,
                          final ConsoleRenderer renderer) throws Exception {

        context.setRenderer(renderer);

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final String pluginId = requestJSONObject.getString(Keys.OBJECT_ID);

            final String setting = pluginQueryService.getPluginSetting(pluginId);

            renderer.setTemplateName("admin-plugin-setting.ftl");
            final Map<String, Object> dataModel = renderer.getDataModel();

            Keys.fillRuntime(dataModel);

            dataModel.put(Plugin.PLUGIN_SETTING, setting);
            dataModel.put(Keys.OBJECT_ID, pluginId);

        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            final JSONRenderer jsonRenderer = new JSONRenderer();

            jsonRenderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }

    }

    /**
     * update the setting of the plugin.
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @param renderer the specified {@link ConsoleRenderer}
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/plugin/updateSetting", method = HTTPRequestMethod.POST)
    public void updateSetting(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context,
                              final JSONRenderer renderer) throws Exception {

        context.setRenderer(renderer);

        final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
        final String pluginoId = requestJSONObject.getString(Keys.OBJECT_ID);
        final String settings = requestJSONObject.getString(Plugin.PLUGIN_SETTING);

        final JSONObject ret = pluginMgmtService.updatePluginSetting(pluginoId, settings);

        renderer.setJSONObject(ret);

    }
}
