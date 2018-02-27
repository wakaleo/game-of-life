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


import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.TextXMLRenderer;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Page;
import org.b3log.solo.model.Tag;
import org.b3log.solo.model.sitemap.Sitemap;
import org.b3log.solo.model.sitemap.URL;
import org.b3log.solo.repository.ArchiveDateRepository;
import org.b3log.solo.repository.PageRepository;
import org.b3log.solo.repository.TagRepository;
import org.b3log.solo.repository.impl.ArticleRepositoryImpl;
import org.b3log.solo.service.PreferenceQueryService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;


/**
 * Site map (sitemap) processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.6, May 17, 2013
 * @since 0.3.1
 */
@RequestProcessor
public class SitemapProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SitemapProcessor.class);

    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepositoryImpl articleRepository;

    /**
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Archive date repository.
     */
    @Inject
    private ArchiveDateRepository archiveDateRepository;

    /**
     * Returns the sitemap.
     * 
     * @param context the specified context
     */
    @RequestProcessing(value = "/sitemap.xml", method = HTTPRequestMethod.GET)
    public void sitemap(final HTTPRequestContext context) {
        final TextXMLRenderer renderer = new TextXMLRenderer();

        context.setRenderer(renderer);

        final Sitemap sitemap = new Sitemap();

        try {
            addArticles(sitemap);
            addNavigations(sitemap);
            addTags(sitemap);
            addArchives(sitemap);

            LOGGER.log(Level.INFO, "Generating sitemap....");
            final String content = sitemap.toString();

            LOGGER.log(Level.INFO, "Generated sitemap");
            renderer.setContent(content);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Get blog article feed error", e);

            try {
                context.getResponse().sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            } catch (final IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Adds articles into the specified sitemap.
     * 
     * @param sitemap the specified sitemap
     * @throws Exception exception
     */
    private void addArticles(final Sitemap sitemap) throws Exception {
        // XXX: query all articles?
        final Query query = new Query().setCurrentPageNum(1).setFilter(new PropertyFilter(Article.ARTICLE_IS_PUBLISHED, FilterOperator.EQUAL, true)).addSort(
            Article.ARTICLE_CREATE_DATE, SortDirection.DESCENDING);

        // XXX: maybe out of memory 
        final JSONObject articleResult = articleRepository.get(query);

        final JSONArray articles = articleResult.getJSONArray(Keys.RESULTS);

        for (int i = 0; i < articles.length(); i++) {
            final JSONObject article = articles.getJSONObject(i);
            final String permalink = article.getString(Article.ARTICLE_PERMALINK);

            final URL url = new URL();

            url.setLoc(Latkes.getServePath() + permalink);

            final Date updateDate = (Date) article.get(Article.ARTICLE_UPDATE_DATE);
            final String lastMod = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(updateDate);

            url.setLastMod(lastMod);

            sitemap.addURL(url);
        }
    }

    /**
     * Adds navigations into the specified sitemap.
     * 
     * @param sitemap the specified sitemap
     * @throws Exception exception 
     */
    private void addNavigations(final Sitemap sitemap) throws Exception {
        final JSONObject result = pageRepository.get(new Query());
        final JSONArray pages = result.getJSONArray(Keys.RESULTS);

        for (int i = 0; i < pages.length(); i++) {
            final JSONObject page = pages.getJSONObject(i);
            final String permalink = page.getString(Page.PAGE_PERMALINK);

            final URL url = new URL();

            // The navigation maybe a page or a link
            // Just filters for user mistakes tolerance
            if (!permalink.contains("://")) {
                url.setLoc(Latkes.getServePath() + permalink);
            } else {
                url.setLoc(permalink);
            }

            sitemap.addURL(url);
        }
    }

    /**
     * Adds tags (tag-articles) and tags wall (/tags.html) into the specified sitemap.
     * 
     * @param sitemap the specified sitemap
     * @throws Exception exception
     */
    private void addTags(final Sitemap sitemap) throws Exception {
        final JSONObject result = tagRepository.get(new Query());
        final JSONArray tags = result.getJSONArray(Keys.RESULTS);

        for (int i = 0; i < tags.length(); i++) {
            final JSONObject tag = tags.getJSONObject(i);
            final String link = URLEncoder.encode(tag.getString(Tag.TAG_TITLE), "UTF-8");

            final URL url = new URL();

            url.setLoc(Latkes.getServePath() + "/tags/" + link);

            sitemap.addURL(url);
        }

        // Tags wall
        final URL url = new URL();

        url.setLoc(Latkes.getServePath() + "/tags.html");
        sitemap.addURL(url);
    }

    /**
     * Adds archives (archive-articles) into the specified sitemap.
     * 
     * @param sitemap the specified sitemap
     * @throws Exception exception
     */
    private void addArchives(final Sitemap sitemap) throws Exception {
        final JSONObject result = archiveDateRepository.get(new Query());
        final JSONArray archiveDates = result.getJSONArray(Keys.RESULTS);

        for (int i = 0; i < archiveDates.length(); i++) {
            final JSONObject archiveDate = archiveDates.getJSONObject(i);
            final long time = archiveDate.getLong(ArchiveDate.ARCHIVE_TIME);
            final String dateString = DateFormatUtils.format(time, "yyyy/MM");

            final URL url = new URL();

            url.setLoc(Latkes.getServePath() + "/archives/" + dateString);

            sitemap.addURL(url);
        }
    }
}
