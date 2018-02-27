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

import org.b3log.latke.Keys;
import org.b3log.latke.model.User;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Tag;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * {@link ArticleQueryService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Sep 12, 2017
 */
@Test(suiteName = "service")
public class ArticleQueryServiceTestCase extends AbstractTestCase {

    /**
     * Init.
     *
     * @throws Exception exception
     */
    @Test
    public void init() throws Exception {
        final InitService initService = getInitService();

        final JSONObject requestJSONObject = new JSONObject();
        requestJSONObject.put(User.USER_EMAIL, "test@gmail.com");
        requestJSONObject.put(User.USER_NAME, "Admin");
        requestJSONObject.put(User.USER_PASSWORD, "pass");

        initService.init(requestJSONObject);

        final UserQueryService userQueryService = getUserQueryService();
        Assert.assertNotNull(userQueryService.getUserByEmail("test@gmail.com"));
    }

    /**
     * Search articles.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void searchKeyword() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();

        JSONObject result = articleQueryService.searchKeyword("欢迎", 1, 20);
        Assert.assertNotNull(result);
        List<JSONObject> articles = (List<JSONObject>) result.opt(Article.ARTICLES);
        Assert.assertEquals(articles.size(), 1);

        result = articleQueryService.searchKeyword("不存在的", 1, 20);
        Assert.assertNotNull(result);
        articles = (List<JSONObject>) result.opt(Article.ARTICLES);
        Assert.assertEquals(articles.size(), 0);
    }

    /**
     * Get Recent Articles.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getRecentArticles() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();
        final List<JSONObject> articles = articleQueryService.getRecentArticles(10);

        Assert.assertEquals(articles.size(), 1);
    }

    /**
     * Get Article.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "getRecentArticles")
    public void getArticle() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();
        final List<JSONObject> articles = articleQueryService.getRecentArticles(10);

        Assert.assertEquals(articles.size(), 1);

        final String articleId = articles.get(0).getString(Keys.OBJECT_ID);
        final JSONObject article = articleQueryService.getArticle(articleId);

        Assert.assertNotNull(article);
        Assert.assertEquals(article.optString(Article.ARTICLE_VIEW_COUNT), "");
    }

    /**
     * Get Article By Id.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "getRecentArticles")
    public void getArticleById() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();
        final List<JSONObject> articles = articleQueryService.getRecentArticles(10);

        Assert.assertEquals(articles.size(), 1);

        final String articleId = articles.get(0).getString(Keys.OBJECT_ID);
        final JSONObject article = articleQueryService.getArticleById(articleId);

        Assert.assertNotNull(article);
        Assert.assertNotNull(article.getString(Article.ARTICLE_VIEW_COUNT), "");
    }

    /**
     * Get Article Content.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "getRecentArticles")
    public void getArticleContent() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();

        final List<JSONObject> articles = articleQueryService.getRecentArticles(10);

        Assert.assertEquals(articles.size(), 1);

        final String articleId = articles.get(0).getString(Keys.OBJECT_ID);

        Assert.assertNotNull(articleQueryService.getArticleContent(null, articleId));
    }

    /**
     * Get Articles By Tag.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getArticlesByTag() throws Exception {
        final TagQueryService tagQueryService = getTagQueryService();

        JSONObject result = tagQueryService.getTagByTitle("Solo");
        Assert.assertNotNull(result);

        final JSONObject tag = result.getJSONObject(Tag.TAG);
        Assert.assertNotNull(tag);

        final String tagId = tag.getString(Keys.OBJECT_ID);

        final ArticleQueryService articleQueryService = getArticleQueryService();
        final List<JSONObject> articles = articleQueryService.getArticlesByTag(tagId, 1, Integer.MAX_VALUE);
        Assert.assertNotNull(articles);
        Assert.assertEquals(articles.size(), 1);
    }

    /**
     * Get Archives By Archive Date.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getArticlesByArchiveDate() throws Exception {
        final ArchiveDateQueryService archiveDateQueryService = getArchiveDateQueryService();

        final List<JSONObject> archiveDates = archiveDateQueryService.getArchiveDates();

        Assert.assertNotNull(archiveDates);
        Assert.assertEquals(archiveDates.size(), 1);

        final JSONObject archiveDate = archiveDates.get(0);

        final ArticleQueryService articleQueryService = getArticleQueryService();
        List<JSONObject> articles =
                articleQueryService.getArticlesByArchiveDate(archiveDate.getString(Keys.OBJECT_ID), 1, Integer.MAX_VALUE);
        Assert.assertNotNull(articles);
        Assert.assertEquals(articles.size(), 1);

        articles = articleQueryService.getArticlesByArchiveDate("not found", 1, Integer.MAX_VALUE);
        Assert.assertNotNull(articles);
        Assert.assertTrue(articles.isEmpty());
    }
}
