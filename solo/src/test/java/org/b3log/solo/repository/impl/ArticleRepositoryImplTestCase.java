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
package org.b3log.solo.repository.impl;

import java.util.Date;
import java.util.List;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Article;
import org.b3log.solo.repository.ArticleRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link ArticleRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.3, May 1, 2012
 */
@Test(suiteName = "repository")
public final class ArticleRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Adds successfully.
     * 
     * @throws Exception exception
     */
    @Test
    public void add() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        final JSONObject article = new JSONObject();

        article.put(Article.ARTICLE_TITLE, "article title1");
        article.put(Article.ARTICLE_ABSTRACT, "article abstract");
        article.put(Article.ARTICLE_TAGS_REF, "tag1, tag2");
        article.put(Article.ARTICLE_AUTHOR_EMAIL, "test@gmail.com");
        article.put(Article.ARTICLE_COMMENT_COUNT, 0);
        article.put(Article.ARTICLE_VIEW_COUNT, 0);
        article.put(Article.ARTICLE_CONTENT, "article content");
        article.put(Article.ARTICLE_PERMALINK, "article permalink1");
        article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, true);
        article.put(Article.ARTICLE_IS_PUBLISHED, true);
        article.put(Article.ARTICLE_PUT_TOP, false);
        article.put(Article.ARTICLE_CREATE_DATE, new Date());
        article.put(Article.ARTICLE_UPDATE_DATE, new Date());
        article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");
        article.put(Article.ARTICLE_EDITOR_TYPE, "");

        final Transaction transaction = articleRepository.beginTransaction();
        articleRepository.add(article);
        transaction.commit();

        final JSONArray results = articleRepository.getByAuthorEmail("test@gmail.com", 1, Integer.MAX_VALUE).getJSONArray(Keys.RESULTS);

        Assert.assertEquals(results.length(), 1);
    }

    /**
     * Get by permalink.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByPermalink() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();
        final JSONObject article = articleRepository.getByPermalink("article permalink1");

        Assert.assertNotNull(article);
        Assert.assertEquals(article.getString(Article.ARTICLE_TITLE), "article title1");

        Assert.assertNull(articleRepository.getByPermalink("not found"));
    }

    /**
     * Get by permalink.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add"})
    public void previousAndNext() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        final JSONObject article = new JSONObject();

        article.put(Article.ARTICLE_TITLE, "article title2");
        article.put(Article.ARTICLE_ABSTRACT, "article abstract");
        article.put(Article.ARTICLE_TAGS_REF, "tag1, tag2");
        article.put(Article.ARTICLE_AUTHOR_EMAIL, "test@gmail.com");
        article.put(Article.ARTICLE_COMMENT_COUNT, 1);
        article.put(Article.ARTICLE_VIEW_COUNT, 1);
        article.put(Article.ARTICLE_CONTENT, "article content");
        article.put(Article.ARTICLE_PERMALINK, "article permalink2");
        article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, true);
        article.put(Article.ARTICLE_IS_PUBLISHED, true);
        article.put(Article.ARTICLE_PUT_TOP, false);
        article.put(Article.ARTICLE_CREATE_DATE, new Date());
        article.put(Article.ARTICLE_UPDATE_DATE, new Date());
        article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");
        article.put(Article.ARTICLE_EDITOR_TYPE, "");

        final Transaction transaction = articleRepository.beginTransaction();
        articleRepository.add(article);
        transaction.commit();

        Assert.assertEquals(articleRepository.count(), 2);

        JSONObject previousArticle = articleRepository.getPreviousArticle(article.getString(Keys.OBJECT_ID));

        Assert.assertNotNull(previousArticle);
        Assert.assertEquals(previousArticle.getString(Article.ARTICLE_TITLE), "article title1");
        Assert.assertEquals(previousArticle.getString(Article.ARTICLE_PERMALINK), "article permalink1");
        Assert.assertNull(previousArticle.opt(Keys.OBJECT_ID));

        previousArticle = articleRepository.getByPermalink(previousArticle.getString(Article.ARTICLE_PERMALINK));

        final JSONObject nextArticle = articleRepository.getNextArticle(previousArticle.getString(Keys.OBJECT_ID));
        Assert.assertNotNull(previousArticle);
        Assert.assertEquals(nextArticle.getString(Article.ARTICLE_TITLE), "article title2");
    }

    /**
     * Get Most Comment Articles.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add", "previousAndNext"})
    public void getMostCommentArticles() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        final JSONObject article = new JSONObject();

        article.put(Article.ARTICLE_TITLE, "article title3");
        article.put(Article.ARTICLE_ABSTRACT, "article abstract");
        article.put(Article.ARTICLE_TAGS_REF, "tag1, tag2");
        article.put(Article.ARTICLE_AUTHOR_EMAIL, "test@gmail.com");
        article.put(Article.ARTICLE_COMMENT_COUNT, 2);
        article.put(Article.ARTICLE_VIEW_COUNT, 2);
        article.put(Article.ARTICLE_CONTENT, "article content");
        article.put(Article.ARTICLE_PERMALINK, "article permalink3");
        article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, true);
        article.put(Article.ARTICLE_IS_PUBLISHED, true);
        article.put(Article.ARTICLE_PUT_TOP, false);
        article.put(Article.ARTICLE_CREATE_DATE, new Date());
        article.put(Article.ARTICLE_UPDATE_DATE, new Date());
        article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");
        article.put(Article.ARTICLE_EDITOR_TYPE, "");

        final Transaction transaction = articleRepository.beginTransaction();
        articleRepository.add(article);
        transaction.commit();

        List<JSONObject> mostCommentArticles = articleRepository.getMostCommentArticles(2);
        Assert.assertNotNull(mostCommentArticles);
        Assert.assertEquals(mostCommentArticles.size(), 2);
        Assert.assertEquals(mostCommentArticles.get(0).getInt(Article.ARTICLE_COMMENT_COUNT), 2);
        Assert.assertEquals(mostCommentArticles.get(1).getInt(Article.ARTICLE_COMMENT_COUNT), 1);

        mostCommentArticles = articleRepository.getMostCommentArticles(1);
        Assert.assertNotNull(mostCommentArticles);
        Assert.assertEquals(mostCommentArticles.size(), 1);
        Assert.assertEquals(mostCommentArticles.get(0).getInt(Article.ARTICLE_COMMENT_COUNT), 2);
    }

    /**
     * Get Most View Count Articles 
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add",
                              "previousAndNext",
                              "getMostCommentArticles"})
    public void getMostViewCountArticles() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        final JSONObject article = new JSONObject();

        article.put(Article.ARTICLE_TITLE, "article title4");
        article.put(Article.ARTICLE_ABSTRACT, "article abstract");
        article.put(Article.ARTICLE_TAGS_REF, "tag1, tag2");
        article.put(Article.ARTICLE_AUTHOR_EMAIL, "test@gmail.com");
        article.put(Article.ARTICLE_COMMENT_COUNT, 3);
        article.put(Article.ARTICLE_VIEW_COUNT, 3);
        article.put(Article.ARTICLE_CONTENT, "article content");
        article.put(Article.ARTICLE_PERMALINK, "article permalink4");
        article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, false);
        article.put(Article.ARTICLE_IS_PUBLISHED, false); // Unpublished
        article.put(Article.ARTICLE_PUT_TOP, false);
        article.put(Article.ARTICLE_CREATE_DATE, new Date());
        article.put(Article.ARTICLE_UPDATE_DATE, new Date());
        article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");
        article.put(Article.ARTICLE_EDITOR_TYPE, "");

        final Transaction transaction = articleRepository.beginTransaction();
        articleRepository.add(article);
        transaction.commit();

        List<JSONObject> mostViewCountArticles = articleRepository.getMostViewCountArticles(2);
        Assert.assertNotNull(mostViewCountArticles);
        Assert.assertEquals(mostViewCountArticles.size(), 2);
        Assert.assertEquals(mostViewCountArticles.get(0).getInt(Article.ARTICLE_VIEW_COUNT), 2);
        Assert.assertEquals(mostViewCountArticles.get(1).getInt(Article.ARTICLE_VIEW_COUNT), 1);

        mostViewCountArticles = articleRepository.getMostViewCountArticles(1);
        Assert.assertNotNull(mostViewCountArticles);
        Assert.assertEquals(mostViewCountArticles.size(), 1);
        Assert.assertEquals(mostViewCountArticles.get(0).getInt(Article.ARTICLE_VIEW_COUNT), 2);

    }

    /**
     * Get Randomly.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add",
                              "previousAndNext",
                              "getMostCommentArticles",
                              "getMostViewCountArticles"})
    public void getRandomly() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        List<JSONObject> articles = articleRepository.getRandomly(3);
        Assert.assertNotNull(articles);
    }

    /**
     * Get Recent Articles.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add",
                              "previousAndNext",
                              "getMostCommentArticles",
                              "getMostViewCountArticles"})
    public void getRecentArticles() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        Assert.assertEquals(articleRepository.count(), 4);

        List<JSONObject> recentArticles = articleRepository.getRecentArticles(3);
        Assert.assertNotNull(recentArticles);
        Assert.assertEquals(recentArticles.size(), 3);

        Assert.assertEquals(recentArticles.get(0).getString(Article.ARTICLE_TITLE), "article title3");
        Assert.assertEquals(recentArticles.get(1).getString(Article.ARTICLE_TITLE), "article title2");
        Assert.assertEquals(recentArticles.get(2).getString(Article.ARTICLE_TITLE), "article title1");
    }

    /**
     * Is Published.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = {"add", "getMostViewCountArticles"})
    public void isPublished() throws Exception {
        final ArticleRepository articleRepository = getArticleRepository();

        final JSONArray all = articleRepository.get(new Query()).getJSONArray(Keys.RESULTS);
        Assert.assertNotNull(all);

        final JSONObject article = all.getJSONObject(0);
        Assert.assertTrue(articleRepository.isPublished(article.getString(Keys.OBJECT_ID)));

        final JSONObject notPublished = articleRepository.getByPermalink("article permalink4");
        Assert.assertNotNull(notPublished);
        Assert.assertFalse(notPublished.getBoolean(Article.ARTICLE_IS_PUBLISHED));

        Assert.assertFalse(articleRepository.isPublished("not found"));
    }
}
