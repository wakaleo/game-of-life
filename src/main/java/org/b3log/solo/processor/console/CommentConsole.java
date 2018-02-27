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
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.b3log.solo.model.Comment;
import org.b3log.solo.service.CommentMgmtService;
import org.b3log.solo.service.CommentQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.QueryResults;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Comment console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Feb 28, 2014
 * @since 0.4.0
 */
@RequestProcessor
public class CommentConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentConsole.class);

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Comment query service.
     */
    @Inject
    private CommentQueryService commentQueryService;

    /**
     * Comment management service.
     */
    @Inject
    private CommentMgmtService commentMgmtService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Removes a comment of an article by the specified request.
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
    @RequestProcessing(value = "/console/page/comment/*", method = HTTPRequestMethod.DELETE)
    public void removePageComment(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        try {
            final String commentId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/page/comment/").length());

            if (!commentQueryService.canAccessComment(commentId, request)) {
                ret.put(Keys.STATUS_CODE, false);
                ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));

                return;
            }

            commentMgmtService.removePageComment(commentId);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("removeSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            ret.put(Keys.STATUS_CODE, false);
            ret.put(Keys.MSG, langPropsService.get("removeFailLabel"));
        }
    }

    /**
     * Removes a comment of an article by the specified request.
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
    @RequestProcessing(value = "/console/article/comment/*", method = HTTPRequestMethod.DELETE)
    public void removeArticleComment(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        try {
            final String commentId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/article/comment/").length());

            if (!commentQueryService.canAccessComment(commentId, request)) {
                ret.put(Keys.STATUS_CODE, false);
                ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));

                return;
            }

            commentMgmtService.removeArticleComment(commentId);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("removeSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            ret.put(Keys.STATUS_CODE, false);
            ret.put(Keys.MSG, langPropsService.get("removeFailLabel"));
        }
    }

    /**
     * Gets comments by the specified request.
     * <p>
     * The request URI contains the pagination arguments. For example, the
     * request URI is /console/comments/1/10/20, means the current page is 1, the
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
     *     "comments": [{
     *         "oId": "",
     *         "commentTitle": "",
     *         "commentName": "",
     *         "commentEmail": "",
     *         "thumbnailUrl": "",
     *         "commentURL": "",
     *         "commentContent": "",
     *         "commentTime": long,
     *         "commentSharpURL": ""
     *      }, ....]
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/comments/*/*/*"/* Requests.PAGINATION_PATH_PATTERN */,
            method = HTTPRequestMethod.GET)
    public void getComments(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final String requestURI = request.getRequestURI();
            final String path = requestURI.substring((Latkes.getContextPath() + "/console/comments/").length());

            final JSONObject requestJSONObject = Requests.buildPaginationRequest(path);
            final JSONObject result = commentQueryService.getComments(requestJSONObject);

            result.put(Keys.STATUS_CODE, true);

            renderer.setJSONObject(result);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets comments of an article specified by the article id for administrator.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "comments": [{
     *         "oId": "",
     *         "commentName": "",
     *         "commentEmail": "",
     *         "thumbnailUrl": "",
     *         "commentURL": "",
     *         "commentContent": "",
     *         "commentTime": long,
     *         "commentSharpURL": "",
     *         "isReply": boolean
     *      }, ....]
     * }
     * </pre>
     * </p>
     *
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/comments/article/*", method = HTTPRequestMethod.GET)
    public void getArticleComments(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);

            final String requestURI = request.getRequestURI();
            final String articleId = requestURI.substring((Latkes.getContextPath() + "/console/comments/article/").length());

            final List<JSONObject> comments = commentQueryService.getComments(articleId);

            ret.put(Comment.COMMENTS, comments);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets comments of a page specified by the article id for administrator.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "comments": [{
     *         "oId": "",
     *         "commentName": "",
     *         "commentEmail": "",
     *         "thumbnailUrl": "",
     *         "commentURL": "",
     *         "commentContent": "",
     *         "commentTime": long,
     *         "commentSharpURL": "",
     *         "isReply": boolean
     *      }, ....]
     * }
     * </pre>
     * </p>
     *
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/comments/page/*", method = HTTPRequestMethod.GET)
    public void getPageComments(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);

            final String requestURI = request.getRequestURI();
            final String pageId = requestURI.substring((Latkes.getContextPath() + "/console/comments/page/").length());

            final List<JSONObject> comments = commentQueryService.getComments(pageId);

            ret.put(Comment.COMMENTS, comments);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }
}
