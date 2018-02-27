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

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Strings;
import org.b3log.solo.model.Article;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Emotions;
import org.b3log.solo.util.Markdowns;
import org.b3log.solo.util.QueryResults;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Article console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.7, Oct 3, 2017
 * @since 0.4.0
 */
@RequestProcessor
public class ArticleConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleConsole.class);

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Markdowns.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "html": ""
     * }
     * </pre>!
     * </p>
     *
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "markdownText": ""
     *                 }
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/markdown/2html", method = HTTPRequestMethod.POST)
    public void markdown2HTML(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        final JSONObject result = new JSONObject();
        renderer.setJSONObject(result);
        result.put(Keys.STATUS_CODE, true);

        final String markdownText = request.getParameter("markdownText");
        if (Strings.isEmptyOrNull(markdownText)) {
            result.put("html", "");

            return;
        }

        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            String html = Emotions.convert(markdownText);
            html = Markdowns.toHTML(html);

            result.put("html", html);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets an article by the specified request json object.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "oId": "",
     *     "articleTitle": "",
     *     "articleAbstract": "",
     *     "articleContent": "",
     *     "articlePermalink": "",
     *     "articleHadBeenPublished": boolean,
     *     "articleTags": [{
     *         "oId": "",
     *         "tagTitle": ""
     *     }, ....],
     *     "articleSignId": "",
     *     "signs": [{
     *         "oId": "",
     *         "signHTML": ""
     *     }, ....]
     *     "sc": "GET_ARTICLE_SUCC"
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/*", method = HTTPRequestMethod.GET)
    public void getArticle(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final String articleId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/article/").length());

            final JSONObject result = articleQueryService.getArticle(articleId);
            result.put(Keys.STATUS_CODE, true);
            renderer.setJSONObject(result);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets articles(by crate date descending) by the specified request json object.
     * <p>
     * The request URI contains the pagination arguments. For example, the request URI is
     * /console/articles/status/published/1/10/20, means the current page is 1, the page size is 10, and the window size
     * is 20.
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
     *     "articles": [{
     *         "oId": "",
     *         "articleTitle": "",
     *         "articleCommentCount": int,
     *         "articleCreateTime"; long,
     *         "articleViewCount": int,
     *         "articleTags": "tag1, tag2, ....",
     *         "articlePutTop": boolean,
     *         "articleIsPublished": boolean
     *      }, ....]
     * }
     * </pre>, order by article update date and sticky(put top).
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/articles/status/*/*/*/*"/* Requests.PAGINATION_PATH_PATTERN */,
            method = HTTPRequestMethod.GET)
    public void getArticles(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            String path = request.getRequestURI().substring((Latkes.getContextPath() + "/console/articles/status/").length());
            final String status = StringUtils.substringBefore(path, "/");

            path = path.substring((status + "/").length());
            final JSONObject requestJSONObject = Requests.buildPaginationRequest(path);
            final boolean published = "published".equals(status);
            requestJSONObject.put(Article.ARTICLE_IS_PUBLISHED, published);

            final JSONArray excludes = new JSONArray();

            excludes.put(Article.ARTICLE_CONTENT);
            excludes.put(Article.ARTICLE_UPDATE_DATE);
            excludes.put(Article.ARTICLE_CREATE_DATE);
            excludes.put(Article.ARTICLE_AUTHOR_EMAIL);
            excludes.put(Article.ARTICLE_HAD_BEEN_PUBLISHED);
            excludes.put(Article.ARTICLE_IS_PUBLISHED);
            excludes.put(Article.ARTICLE_RANDOM_DOUBLE);
            requestJSONObject.put(Keys.EXCLUDES, excludes);

            final JSONObject result = articleQueryService.getArticles(requestJSONObject);

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
     * Removes an article by the specified request.
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
     * @param context   the specified http request context
     * @param request   the specified http servlet request
     * @param response  the specified http servlet response
     * @param articleId the specified article id
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/{articleId}", method = HTTPRequestMethod.DELETE)
    public void removeArticle(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response,
                              final String articleId) throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        try {
            if (!articleQueryService.canAccessArticle(articleId, request)) {
                ret.put(Keys.STATUS_CODE, false);
                ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));

                return;
            }

            articleMgmtService.removeArticle(articleId);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("removeSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("removeFailLabel"));
        }
    }

    /**
     * Cancels publish an article by the specified request.
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
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/unpublish/*", method = HTTPRequestMethod.PUT)
    public void cancelPublishArticle(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
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
            final String articleId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/article/unpublish/").length());

            if (!articleQueryService.canAccessArticle(articleId, request)) {
                ret.put(Keys.STATUS_CODE, false);
                ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));

                return;
            }

            articleMgmtService.cancelPublishArticle(articleId);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("unPulbishSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("unPulbishFailLabel"));
        }
    }

    /**
     * Cancels an top article by the specified request.
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
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/canceltop/*", method = HTTPRequestMethod.PUT)
    public void cancelTopArticle(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();

        renderer.setJSONObject(ret);

        if (!userQueryService.isAdminLoggedIn(request)) {
            ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));
            ret.put(Keys.STATUS_CODE, false);

            return;
        }

        try {
            final String articleId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/article/canceltop/").length());

            articleMgmtService.topArticle(articleId, false);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("cancelTopSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("cancelTopFailLabel"));
        }
    }

    /**
     * Puts an article to top by the specified request.
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
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/puttop/*", method = HTTPRequestMethod.PUT)
    public void putTopArticle(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();

        renderer.setJSONObject(ret);

        if (!userQueryService.isAdminLoggedIn(request)) {
            ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));
            ret.put(Keys.STATUS_CODE, false);

            return;
        }

        try {
            final String articleId = request.getRequestURI().substring((Latkes.getContextPath() + "/console/article/puttop/").length());

            articleMgmtService.topArticle(articleId, true);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("putTopSuccLabel"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = new JSONObject();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.STATUS_CODE, false);
            jsonObject.put(Keys.MSG, langPropsService.get("putTopFailLabel"));
        }
    }

    /**
     * Updates an article by the specified request json object.
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
     * @param context  the specified http request context
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "article": {
     *                 "oId": "",
     *                 "articleTitle": "",
     *                 "articleAbstract": "",
     *                 "articleContent": "",
     *                 "articleTags": "tag1,tag2,tag3",
     *                 "articlePermalink": "", // optional
     *                 "articleIsPublished": boolean,
     *                 "articleSignId": "" // optional
     *                 "articleCommentable": boolean,
     *                 "articleViewPwd": "",
     *                 "postToCommunity": boolean
     *                 }
     *                 }
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/", method = HTTPRequestMethod.PUT)
    public void updateArticle(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final JSONObject article = requestJSONObject.getJSONObject(Article.ARTICLE);
            final String articleId = article.getString(Keys.OBJECT_ID);
            renderer.setJSONObject(ret);

            if (!articleQueryService.canAccessArticle(articleId, request)) {
                ret.put(Keys.MSG, langPropsService.get("forbiddenLabel"));
                ret.put(Keys.STATUS_CODE, false);

                return;
            }

            articleMgmtService.updateArticle(requestJSONObject);

            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
            ret.put(Keys.STATUS_CODE, true);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }

    /**
     * Adds an article with the specified request.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "oId": "", // Generated article id
     *     "msg": ""
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request, for example,
     *                 {
     *                 "article": {
     *                 "articleTitle": "",
     *                 "articleAbstract": "",
     *                 "articleContent": "",
     *                 "articleTags": "tag1,tag2,tag3",
     *                 "articlePermalink": "", // optional
     *                 "articleIsPublished": boolean,
     *                 "postToCommunity": boolean,
     *                 "articleSignId": "" // optional
     *                 "articleCommentable": boolean,
     *                 "articleViewPwd": ""
     *                 }
     *                 }
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/article/", method = HTTPRequestMethod.POST)
    public void addArticle(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final JSONObject currentUser = userQueryService.getCurrentUser(request);
            requestJSONObject.getJSONObject(Article.ARTICLE).put(Article.ARTICLE_AUTHOR_EMAIL, currentUser.getString(User.USER_EMAIL));

            final String articleId = articleMgmtService.addArticle(requestJSONObject);
            ret.put(Keys.OBJECT_ID, articleId);
            ret.put(Keys.MSG, langPropsService.get("addSuccLabel"));
            ret.put(Keys.STATUS_CODE, true);

            renderer.setJSONObject(ret);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }
}
