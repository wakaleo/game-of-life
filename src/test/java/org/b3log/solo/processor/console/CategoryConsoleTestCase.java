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
import org.b3log.latke.model.User;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Category;
import org.b3log.solo.processor.MockDispatcherServlet;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@link CategoryConsole} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Apr 22, 2017
 * @since 2.1.0
 */
@Test(suiteName = "processor")
public class CategoryConsoleTestCase extends AbstractTestCase {

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
     * addCategory.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addCategory() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestURI()).thenReturn("/console/category/");
        when(request.getMethod()).thenReturn("POST");

        final JSONObject adminUser = getUserQueryService().getAdmin();
        final HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute(User.USER)).thenReturn(adminUser);
        when(request.getSession(false)).thenReturn(httpSession);

        final JSONObject requestJSON = new JSONObject();
        requestJSON.put(Category.CATEGORY_T_TAGS, "Solo");
        requestJSON.put(Category.CATEGORY_TITLE, "分类1");
        requestJSON.put(Category.CATEGORY_URI, "cate1");


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
        Assert.assertTrue(StringUtils.contains(content, "sc\":true"));
    }

    /**
     * updateCategory.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "addCategory")
    public void updateCategory() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestURI()).thenReturn("/console/category/");
        when(request.getMethod()).thenReturn("PUT");

        final JSONObject adminUser = getUserQueryService().getAdmin();
        final HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute(User.USER)).thenReturn(adminUser);
        when(request.getSession(false)).thenReturn(httpSession);

        JSONObject category = getCategoryQueryService().getByTitle("分类1");

        final JSONObject requestJSON = new JSONObject();
        requestJSON.put(Category.CATEGORY_T_TAGS, "Solo");
        requestJSON.put(Keys.OBJECT_ID, category.optString(Keys.OBJECT_ID));
        requestJSON.put(Category.CATEGORY_TITLE, "新的分类1");

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
        Assert.assertTrue(StringUtils.contains(content, "sc\":true"));

        category = getCategoryQueryService().getByTitle("分类1");
        Assert.assertNull(category);

        category = getCategoryQueryService().getByTitle("新的分类1");
        Assert.assertNotNull(category);
        Assert.assertEquals(category.optInt(Category.CATEGORY_TAG_CNT), 1); // https://github.com/b3log/solo/issues/12274
    }
}
