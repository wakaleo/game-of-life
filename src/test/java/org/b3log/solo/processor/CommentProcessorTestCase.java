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

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.model.User;
import org.b3log.latke.service.ServiceException;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Page;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.PageMgmtService;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;
import static org.mockito.Mockito.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link CommentProcessorTestCase} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Nov 5, 2016
 * @since 1.7.0
 */
@Test(suiteName = "processor")
public class CommentProcessorTestCase extends AbstractTestCase {

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
     * addPageComment.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addPageComment() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestURI()).thenReturn("/add-page-comment.do");
        when(request.getMethod()).thenReturn("POST");

        final HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(CaptchaProcessor.CAPTCHA)).thenReturn("captcha123456");
        when(request.getSession(false)).thenReturn(session);

        final JSONObject requestJSON = new JSONObject();
        requestJSON.put("captcha", "captcha123456");
        requestJSON.put("oId", addPage());
        requestJSON.put("commentName", "88250");
        requestJSON.put("commentEmail", "d@hacpai.com");
        requestJSON.put("commentURL", "https://hacpai.com");
        requestJSON.put("commentContent", "测试评论");

        final BufferedReader reader = new BufferedReader(new StringReader(requestJSON.toString()));
        when(request.getReader()).thenReturn(reader);

        final MockDispatcherServlet dispatcherServlet = new MockDispatcherServlet();
        dispatcherServlet.init();

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(printWriter);

        dispatcherServlet.service(request, response);

        final String content = stringWriter.toString();
        Assert.assertTrue(StringUtils.contains(content, "\"sc\":true"));
    }

    /**
     * addArticleComment.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addArticleComment() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestURI()).thenReturn("/add-article-comment.do");
        when(request.getMethod()).thenReturn("POST");

        final HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(CaptchaProcessor.CAPTCHA)).thenReturn("captcha123456");
        when(request.getSession(false)).thenReturn(session);

        final JSONObject requestJSON = new JSONObject();
        requestJSON.put("captcha", "captcha123456");
        requestJSON.put("oId", addArticle());
        requestJSON.put("commentName", "88250");
        requestJSON.put("commentEmail", "d@hacpai.com");
        requestJSON.put("commentURL", "https://hacpai.com");
        requestJSON.put("commentContent", "测试评论");

        final BufferedReader reader = new BufferedReader(new StringReader(requestJSON.toString()));
        when(request.getReader()).thenReturn(reader);

        final MockDispatcherServlet dispatcherServlet = new MockDispatcherServlet();
        dispatcherServlet.init();

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(printWriter);

        dispatcherServlet.service(request, response);

        final String content = stringWriter.toString();
        Assert.assertTrue(StringUtils.contains(content, "\"sc\":true"));
    }

    private String addPage() throws ServiceException {
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

        return pageMgmtService.addPage(requestJSONObject);
    }

    private String addArticle() throws Exception {
        final ArticleMgmtService articleMgmtService = getArticleMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject article = new JSONObject();
        requestJSONObject.put(Article.ARTICLE, article);

        article.put(Article.ARTICLE_AUTHOR_EMAIL, "test@gmail.com");
        article.put(Article.ARTICLE_TITLE, "article1 title");
        article.put(Article.ARTICLE_ABSTRACT, "article1 abstract");
        article.put(Article.ARTICLE_CONTENT, "article1 content");
        article.put(Article.ARTICLE_TAGS_REF, "tag1, tag2, tag3");
        article.put(Article.ARTICLE_PERMALINK, "article1 permalink");
        article.put(Article.ARTICLE_IS_PUBLISHED, true);
        article.put(Common.POST_TO_COMMUNITY, true);
        article.put(Article.ARTICLE_SIGN_ID, "1");
        article.put(Article.ARTICLE_COMMENTABLE, true);
        article.put(Article.ARTICLE_VIEW_PWD, "");

        return articleMgmtService.addArticle(requestJSONObject);
    }
}
