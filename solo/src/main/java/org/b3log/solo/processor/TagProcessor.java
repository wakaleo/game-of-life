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
package org.b3log.solo.processor;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.servlet.renderer.freemarker.FreeMarkerRenderer;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Strings;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Tag;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Skins;
import org.b3log.solo.util.comparator.Comparators;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Tag processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.1.4, Nov 20, 2015
 * @since 0.3.1
 */
@RequestProcessor
public class TagProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TagProcessor.class);

    /**
     * Filler.
     */
    @Inject
    private Filler filler;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

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
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Shows articles related with a tag with the specified context.
     *
     * @param context the specified context
     * @throws IOException io exception
     */
    @RequestProcessing(value = "/tags/**", method = HTTPRequestMethod.GET)
    public void showTagArticles(final HTTPRequestContext context) throws IOException {
        final AbstractFreeMarkerRenderer renderer = new FreeMarkerRenderer();

        context.setRenderer(renderer);

        renderer.setTemplateName("tag-articles.ftl");
        final Map<String, Object> dataModel = renderer.getDataModel();

        final HttpServletRequest request = context.getRequest();
        final HttpServletResponse response = context.getResponse();

        try {
            String requestURI = request.getRequestURI();

            if (!requestURI.endsWith("/")) {
                requestURI += "/";
            }

            String tagTitle = getTagTitle(requestURI);
            final int currentPageNum = getCurrentPageNum(requestURI, tagTitle);

            if (-1 == currentPageNum) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            LOGGER.log(Level.DEBUG, "Tag[title={0}, currentPageNum={1}]", tagTitle, currentPageNum);

            tagTitle = URLDecoder.decode(tagTitle, "UTF-8");
            final JSONObject result = tagQueryService.getTagByTitle(tagTitle);

            if (null == result) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            final JSONObject tag = result.getJSONObject(Tag.TAG);
            final String tagId = tag.getString(Keys.OBJECT_ID);

            final JSONObject preference = preferenceQueryService.getPreference();

            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), (String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME), dataModel);

            final int pageSize = preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
            final int windowSize = preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);

            final List<JSONObject> articles = articleQueryService.getArticlesByTag(tagId, currentPageNum, pageSize);

            if (articles.isEmpty()) {
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                } catch (final IOException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }

            final boolean hasMultipleUsers = userQueryService.hasMultipleUsers();

            if (hasMultipleUsers) {
                filler.setArticlesExProperties(request, articles, preference);
            } else {
                // All articles composed by the same author
                final JSONObject author = articleQueryService.getAuthor(articles.get(0));

                filler.setArticlesExProperties(request, articles, author, preference);
            }

            final int tagArticleCount = tag.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);
            final int pageCount = (int) Math.ceil((double) tagArticleCount / (double) pageSize);

            LOGGER.log(Level.TRACE, "Paginate tag-articles[currentPageNum={0}, pageSize={1}, pageCount={2}, windowSize={3}]",
                    currentPageNum, pageSize, pageCount, windowSize);
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            LOGGER.log(Level.TRACE, "tag-articles[pageNums={0}]", pageNums);

            Collections.sort(articles, Comparators.ARTICLE_CREATE_DATE_COMPARATOR);

            fillPagination(dataModel, pageCount, currentPageNum, articles, pageNums);
            dataModel.put(Common.PATH, "/tags/" + URLEncoder.encode(tagTitle, "UTF-8"));
            dataModel.put(Keys.OBJECT_ID, tagId);
            dataModel.put(Tag.TAG, tag);

            filler.fillSide(request, dataModel, preference);
            filler.fillBlogHeader(request, response, dataModel, preference);
            filler.fillBlogFooter(request, dataModel, preference);

            statisticMgmtService.incBlogViewCount(request, response);
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (final IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        } catch (final JSONException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (final IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    /**
     * Fills pagination.
     *
     * @param dataModel the specified data model
     * @param pageCount the specified page count
     * @param currentPageNum the specified current page number
     * @param articles the specified articles
     * @param pageNums the specified page numbers
     */
    private void fillPagination(final Map<String, Object> dataModel,
            final int pageCount, final int currentPageNum,
            final List<JSONObject> articles,
            final List<Integer> pageNums) {
        final String previousPageNum = Integer.toString(currentPageNum > 1 ? currentPageNum - 1 : 0);

        dataModel.put(Pagination.PAGINATION_PREVIOUS_PAGE_NUM, "0".equals(previousPageNum) ? "" : previousPageNum);
        if (pageCount == currentPageNum + 1) { // The next page is the last page
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, "");
        } else {
            dataModel.put(Pagination.PAGINATION_NEXT_PAGE_NUM, currentPageNum + 1);
        }
        dataModel.put(Article.ARTICLES, articles);
        dataModel.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, currentPageNum);
        dataModel.put(Pagination.PAGINATION_FIRST_PAGE_NUM, pageNums.get(0));
        dataModel.put(Pagination.PAGINATION_LAST_PAGE_NUM, pageNums.get(pageNums.size() - 1));
        dataModel.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
        dataModel.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);
    }

    /**
     * Gets the request page number from the specified request URI and tag title.
     *
     * @param requestURI the specified request URI
     * @param tagTitle the specified tag title
     * @return page number, returns {@code -1} if the specified request URI can not convert to an number
     */
    private static int getCurrentPageNum(final String requestURI, final String tagTitle) {
        if (Strings.isEmptyOrNull(tagTitle)) {
            return -1;
        }

        final String pageNumString = requestURI.substring((Latkes.getContextPath() + "/tags/" + tagTitle + "/").length());

        return Requests.getCurrentPageNum(pageNumString);
    }

    /**
     * Gets tag title from the specified URI.
     *
     * @param requestURI the specified request URI
     * @return tag title
     */
    private static String getTagTitle(final String requestURI) {
        final String path = requestURI.substring((Latkes.getContextPath() + "/tags/").length());

        if (path.contains("/")) {
            return path.substring(0, path.indexOf("/"));
        } else {
            return path.substring(0);
        }
    }
}
