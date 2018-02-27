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

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Strings;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Search processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 1.0.1.0, Oct 11, 2017
 * @since 2.4.0
 */
@RequestProcessor
public class SearchProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SearchProcessor.class);

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
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Filler.
     */
    @Inject
    private Filler filler;

    /**
     * Searches articles.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/search", method = HTTPRequestMethod.GET)
    public void search(final HTTPRequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();
        context.setRenderer(renderer);
        renderer.setTemplateName("search.ftl");

        final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
        final Map<String, Object> dataModel = renderer.getDataModel();
        dataModel.putAll(langs);

        final HttpServletRequest request = context.getRequest();

        String page = request.getParameter("p");
        if (!Strings.isNumeric(page)) {
            page = "1";
        }
        final int pageNum = Integer.valueOf(page);
        String keyword = request.getParameter(Common.KEYWORD);
        if (StringUtils.isBlank(keyword)) {
            keyword = "";
        }
        keyword = Jsoup.clean(keyword, Whitelist.none());

        dataModel.put(Common.KEYWORD, keyword);
        final JSONObject result = articleQueryService.searchKeyword(keyword, pageNum, 15);
        final List<JSONObject> articles = (List<JSONObject>) result.opt(Article.ARTICLES);

        try {
            final JSONObject preference = preferenceQueryService.getPreference();

            filler.fillBlogHeader(request, context.getResponse(), dataModel, preference);
            filler.fillBlogFooter(request, dataModel, preference);
            filler.fillSide(request, dataModel, preference);

            final boolean hasMultipleUsers = userQueryService.hasMultipleUsers();
            if (hasMultipleUsers) {
                filler.setArticlesExProperties(request, articles, preference);
            } else if (!articles.isEmpty()) {
                final JSONObject author = articleQueryService.getAuthor(articles.get(0));

                filler.setArticlesExProperties(request, articles, author, preference);
            }

            dataModel.put(Article.ARTICLES, articles);
            final JSONObject pagination = result.optJSONObject(Pagination.PAGINATION);
            pagination.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, pageNum);
            dataModel.put(Pagination.PAGINATION, pagination);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Search articles failed");

            dataModel.put(Article.ARTICLES, Collections.emptyList());
        }
    }
}
