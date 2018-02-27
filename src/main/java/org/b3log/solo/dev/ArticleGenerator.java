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
package org.b3log.solo.dev;

import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.solo.model.Article;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * Generates some dummy articles for development testing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.5, Jul 9, 2017
 * @since 0.4.0
 */
@RequestProcessor
public class ArticleGenerator {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleGenerator.class);

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Generates some dummy articles with the specified context.
     *
     * @param context  the specified context
     * @param request  the specified request
     * @param response the specified response
     * @throws IOException io exception
     */
    @RequestProcessing(value = "/dev/articles/gen/*", method = HTTPRequestMethod.GET)
    public void genArticles(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        if (Latkes.RuntimeMode.DEVELOPMENT != Latkes.getRuntimeMode()) {
            LOGGER.log(Level.WARN, "Article generation just for development mode, " + "current runtime mode is [{0}]",
                    Latkes.getRuntimeMode());
            response.sendRedirect(Latkes.getServePath());

            return;
        }

        Stopwatchs.start("Gen Articles");

        final String requestURI = request.getRequestURI();
        final int num = Integer.valueOf(requestURI.substring((Latkes.getContextPath() + "/dev/articles/gen/").length()));

        try {
            final JSONObject admin = userQueryService.getAdmin();
            final String authorEmail = admin.optString(User.USER_EMAIL);

            for (int i = 0; i < num; i++) {
                final JSONObject article = new JSONObject();
                article.put(Article.ARTICLE_TITLE, "article title" + i);
                article.put(Article.ARTICLE_ABSTRACT, "article" + i + " abstract");
                final int deviationTag = 3;

                article.put(Article.ARTICLE_TAGS_REF, "taga,tagb,tag" + i % deviationTag);
                article.put(Article.ARTICLE_AUTHOR_EMAIL, authorEmail);
                article.put(Article.ARTICLE_COMMENT_COUNT, 0);
                article.put(Article.ARTICLE_VIEW_COUNT, 0);
                article.put(Article.ARTICLE_CONTENT, "article content");
                article.put(Article.ARTICLE_PERMALINK, "article" + i + " permalink");
                article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, true);
                article.put(Article.ARTICLE_IS_PUBLISHED, true);
                article.put(Article.ARTICLE_PUT_TOP, false);

                final int deviationBase = 5;
                final int deviationDay = -(Integer.valueOf(String.valueOf(i).substring(0, 1)) % deviationBase);

                final Date date = DateUtils.addMonths(new Date(), deviationDay);
                article.put(Article.ARTICLE_CREATE_DATE, date);
                article.put(Article.ARTICLE_UPDATE_DATE, date);
                article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
                article.put(Article.ARTICLE_COMMENTABLE, true);
                article.put(Article.ARTICLE_VIEW_PWD, "");
                article.put(Article.ARTICLE_SIGN_ID, "1");

                articleMgmtService.addArticle(new JSONObject().put(Article.ARTICLE, article));
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }

        Stopwatchs.end();

        response.sendRedirect(Latkes.getServePath());
    }
}
