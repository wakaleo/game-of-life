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
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Page;
import org.b3log.solo.service.PageMgmtService;
import org.b3log.solo.service.PageQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.QueryResults;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Plugin console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.3, Sep 21, 2017
 * @since 0.4.0
 */
@RequestProcessor
public class PageConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PageConsole.class);

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Page query service.
     */
    @Inject
    private PageQueryService pageQueryService;

    /**
     * Page management service.
     */
    @Inject
    private PageMgmtService pageMgmtService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Updates a page by the specified request.
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
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "page": {
     *                 "oId": "",
     *                 "pageTitle": "",
     *                 "pageContent": "",
     *                 "pageOrder": int,
     *                 "pageCommentCount": int,
     *                 "pagePermalink": "",
     *                 "pageCommentable": boolean,
     *                 "pageType": "",
     *                 "pageOpenTarget": "",
     *                 "pageIcon": ""
     *                 }
     *                 }, see {@link org.b3log.solo.model.Page} for more details
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/page/", method = HTTPRequestMethod.PUT)
    public void updatePage(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject ret = new JSONObject();

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);

            pageMgmtService.updatePage(requestJSONObject);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));

            renderer.setJSONObject(ret);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }

    /**
     * Removes a page by the specified request.
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
    @RequestProcessing(value = "/console/page/*", method = HTTPRequestMethod.DELETE)
    public void removePage(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();
        renderer.setJSONObject(jsonObject);

        try {
            final String pageId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/page/").length());

            pageMgmtService.removePage(pageId);

            jsonObject.put(Keys.STATUS_CODE, true);
            jsonObject.put(Keys.MSG, langPropsService.get("removeSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("removeFailLabel"));

        }
    }

    /**
     * Adds a page with the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "oId": "", // Generated page id
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param context  the specified http request context
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "page": {
     *                 "pageTitle": "",
     *                 "pageContent": "",
     *                 "pagePermalink": "" // optional,
     *                 "pageCommentable": boolean,
     *                 "pageType": "",
     *                 "pageOpenTarget": "",
     *                 "pageIcon": ""
     *                 }
     *                 }, see {@link org.b3log.solo.model.Page} for more details
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/page/", method = HTTPRequestMethod.POST)
    public void addPage(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject ret = new JSONObject();

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);

            final String pageId = pageMgmtService.addPage(requestJSONObject);

            ret.put(Keys.OBJECT_ID, pageId);
            ret.put(Keys.MSG, langPropsService.get("addSuccLabel"));
            ret.put(Keys.STATUS_CODE, true);

            renderer.setJSONObject(ret);
        } catch (final ServiceException e) { // May be permalink check exception
            LOGGER.log(Level.WARN, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }

    /**
     * Changes a page order by the specified page id and direction.
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
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "oId": "",
     *                 "direction": "" // "up"/"down"
     *                 }
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/page/order/", method = HTTPRequestMethod.PUT)
    public void changeOrder(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject ret = new JSONObject();

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final String linkId = requestJSONObject.getString(Keys.OBJECT_ID);
            final String direction = requestJSONObject.getString(Common.DIRECTION);

            pageMgmtService.changeOrder(linkId, direction);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));

            renderer.setJSONObject(ret);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("updateFailLabel"));
        }
    }

    /**
     * Gets a page by the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean
     *     "page": {
     *         "oId": "",
     *         "pageTitle": "",
     *         "pageContent": ""
     *         "pageOrder": int,
     *         "pagePermalink": "",
     *         "pageCommentCount": int,
     *         "pageIcon": ""
     *     }
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/page/*", method = HTTPRequestMethod.GET)
    public void getPage(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final String requestURI = request.getRequestURI();
            final String pageId = requestURI.substring((Latkes.getContextPath() + "/console/page/").length());

            final JSONObject result = pageQueryService.getPage(pageId);
            if (null == result) {
                renderer.setJSONObject(QueryResults.defaultResult());

                return;
            }

            renderer.setJSONObject(result);
            result.put(Keys.STATUS_CODE, true);
            result.put(Keys.MSG, langPropsService.get("getSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets pages by the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "pagination": {
     *         "paginationPageCount": 100,
     *         "paginationPageNums": [1, 2, 3, 4, 5]
     *     },
     *     "pages": [{
     *         "oId": "",
     *         "pageTitle": "",
     *         "pageCommentCount": int,
     *         "pageOrder": int,
     *         "pagePermalink": "",
     *         .{@link PageMgmtService...}
     *      }, ....]
     *     "sc": "GET_PAGES_SUCC"
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
    @RequestProcessing(value = "/console/pages/*/*/*"/* Requests.PAGINATION_PATH_PATTERN */,
            method = HTTPRequestMethod.GET)
    public void getPages(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final String requestURI = request.getRequestURI();
            final String path = requestURI.substring((Latkes.getContextPath() + "/console/pages/").length());

            final JSONObject requestJSONObject = Requests.buildPaginationRequest(path);

            final JSONObject result = pageQueryService.getPages(requestJSONObject);
            final JSONArray pages = result.optJSONArray(Page.PAGES);

            // Site-internal URLs process
            for (int i = 0; i < pages.length(); i++) {
                final JSONObject page = pages.getJSONObject(i);

                if ("page".equals(page.optString(Page.PAGE_TYPE))) {
                    final String permalink = page.optString(Page.PAGE_PERMALINK);

                    page.put(Page.PAGE_PERMALINK, Latkes.getServePath() + permalink);
                }
            }

            result.put(Keys.STATUS_CODE, true);

            renderer.setJSONObject(result);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }
}
