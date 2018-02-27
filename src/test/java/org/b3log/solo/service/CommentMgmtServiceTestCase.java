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

import java.util.List;
import org.b3log.latke.Keys;
import org.b3log.latke.model.User;
import org.b3log.latke.util.Requests;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Page;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link CommentMgmtService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Sep 11, 2012
 */
@Test(suiteName = "service")
public class CommentMgmtServiceTestCase extends AbstractTestCase {

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
     * Add Article Comment.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addArticleComment() throws Exception {
        final ArticleQueryService articleQueryService = getArticleQueryService();

        final List<JSONObject> articles = articleQueryService.getRecentArticles(10);

        Assert.assertEquals(articles.size(), 1);

        final CommentQueryService commentQueryService = getCommentQueryService();
        JSONObject paginationRequest = Requests.buildPaginationRequest("1/10/20");
        JSONObject result = commentQueryService.getComments(paginationRequest);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONArray(Comment.COMMENTS).length(), 1);

        final CommentMgmtService commentMgmtService = getCommentMgmtService();
        final JSONObject requestJSONObject = new JSONObject();

        final String articleId = articles.get(0).getString(Keys.OBJECT_ID);
        requestJSONObject.put(Keys.OBJECT_ID, articleId);
        requestJSONObject.put(Comment.COMMENT_NAME, "comment name");
        requestJSONObject.put(Comment.COMMENT_EMAIL, "comment email");
        requestJSONObject.put(Comment.COMMENT_URL, "comment URL");
        requestJSONObject.put(Comment.COMMENT_CONTENT, "comment content");

        final JSONObject addResult = commentMgmtService.addArticleComment(requestJSONObject);
        Assert.assertNotNull(addResult);
        Assert.assertNotNull(addResult.getString(Keys.OBJECT_ID));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_DATE));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_THUMBNAIL_URL));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_SHARP_URL));

        result = commentQueryService.getComments(paginationRequest);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONArray(Comment.COMMENTS).length(), 2);
    }

    /**
     * Add Page Comment.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "addArticleComment")
    public void addPageComment() throws Exception {
        addPage();

        final PageQueryService pageQueryService = getPageQueryService();

        final JSONObject paginationRequest = Requests.buildPaginationRequest("1/10/20");
        JSONObject result = pageQueryService.getPages(paginationRequest);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONArray(Page.PAGES).length(), 1);

        final JSONArray pages = result.getJSONArray(Page.PAGES);

        final CommentQueryService commentQueryService = getCommentQueryService();
        result = commentQueryService.getComments(paginationRequest);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONArray(Comment.COMMENTS).length(),
                            2);  // 2 article comments

        final CommentMgmtService commentMgmtService = getCommentMgmtService();
        final JSONObject requestJSONObject = new JSONObject();

        final String pageId = pages.getJSONObject(0).getString(Keys.OBJECT_ID);
        requestJSONObject.put(Keys.OBJECT_ID, pageId);
        requestJSONObject.put(Comment.COMMENT_NAME, "comment name");
        requestJSONObject.put(Comment.COMMENT_EMAIL, "comment email");
        requestJSONObject.put(Comment.COMMENT_URL, "comment URL");
        requestJSONObject.put(Comment.COMMENT_CONTENT, "comment content");

        final JSONObject addResult = commentMgmtService.addPageComment(requestJSONObject);
        Assert.assertNotNull(addResult);
        Assert.assertNotNull(addResult.getString(Keys.OBJECT_ID));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_DATE));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_THUMBNAIL_URL));
        Assert.assertNotNull(addResult.getString(Comment.COMMENT_SHARP_URL));

        result = commentQueryService.getComments(paginationRequest);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONArray(Comment.COMMENTS).length(),
                            3);  // 2 article comments + 1 page comment

        final List<JSONObject> pageComments = commentQueryService.getComments(pageId);
        Assert.assertNotNull(pageComments);
        Assert.assertEquals(pageComments.size(), 1);
    }

    /**
     * Adds a page.
     * 
     * @throws Exception exception
     */
    private void addPage() throws Exception {
        final PageMgmtService pageMgmtService = getPageMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject page = new JSONObject();
        requestJSONObject.put(Page.PAGE, page);

        page.put(Page.PAGE_CONTENT, "page1 content");
        page.put(Page.PAGE_PERMALINK, "page1 permalink");
        page.put(Page.PAGE_TITLE, "page1 title");
        page.put(Page.PAGE_COMMENTABLE, true);
        page.put(Page.PAGE_TYPE, "page");
        page.put(Page.PAGE_OPEN_TARGET, "_self");

        final String pageId = pageMgmtService.addPage(requestJSONObject);

        Assert.assertNotNull(pageId);
    }
}
