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

import org.b3log.latke.model.User;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Page;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link PageMgmtService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Sep 11, 2012
 */
@Test(suiteName = "service")
public class PageMgmtServiceTestCase extends AbstractTestCase {

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
     * Add Page.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addPage() throws Exception {
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

    /**
     * Remove Page.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void removePage() throws Exception {
        final PageMgmtService pageMgmtService = getPageMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject page = new JSONObject();
        requestJSONObject.put(Page.PAGE, page);

        page.put(Page.PAGE_CONTENT, "page2 content");
        page.put(Page.PAGE_PERMALINK, "page2 permalink");
        page.put(Page.PAGE_TITLE, "page2 title");
        page.put(Page.PAGE_COMMENTABLE, true);
        page.put(Page.PAGE_TYPE, "page");
        page.put(Page.PAGE_OPEN_TARGET, "_self");

        final String pageId = pageMgmtService.addPage(requestJSONObject);
        Assert.assertNotNull(pageId);

        final PageQueryService pageQueryService = getPageQueryService();
        JSONObject result = pageQueryService.getPage(pageId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Page.PAGE).getString(Page.PAGE_TITLE), "page2 title");

        pageMgmtService.removePage(pageId);

        result = pageQueryService.getPage(pageId);
        Assert.assertNull(result);
    }

    /**
     * Update Page.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void updatePage() throws Exception {
        final PageMgmtService pageMgmtService = getPageMgmtService();

        JSONObject requestJSONObject = new JSONObject();
        final JSONObject page = new JSONObject();
        requestJSONObject.put(Page.PAGE, page);

        page.put(Page.PAGE_CONTENT, "page3 content");
        page.put(Page.PAGE_PERMALINK, "page3 permalink");
        page.put(Page.PAGE_TITLE, "page3 title");
        page.put(Page.PAGE_COMMENTABLE, true);
        page.put(Page.PAGE_TYPE, "page");
        page.put(Page.PAGE_OPEN_TARGET, "_self");

        final String pageId = pageMgmtService.addPage(requestJSONObject);
        Assert.assertNotNull(pageId);

        final PageQueryService pageQueryService = getPageQueryService();
        JSONObject result = pageQueryService.getPage(pageId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Page.PAGE).getString(Page.PAGE_TITLE), "page3 title");

        page.put(Page.PAGE_TITLE, "updated page3 title");
        pageMgmtService.updatePage(requestJSONObject);

        result = pageQueryService.getPage(pageId);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Page.PAGE).getString(Page.PAGE_TITLE), "updated page3 title");
    }

    /**
     * Change Order.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "addPage")
    public void changeOrder() throws Exception {
        final PageMgmtService pageMgmtService = getPageMgmtService();

        JSONObject requestJSONObject = new JSONObject();
        final JSONObject page = new JSONObject();
        requestJSONObject.put(Page.PAGE, page);

        page.put(Page.PAGE_CONTENT, "page4 content");
        page.put(Page.PAGE_PERMALINK, "page4 permalink");
        page.put(Page.PAGE_TITLE, "page4 title");
        page.put(Page.PAGE_COMMENTABLE, true);
        page.put(Page.PAGE_TYPE, "page");
        page.put(Page.PAGE_OPEN_TARGET, "_self");

        final String pageId = pageMgmtService.addPage(requestJSONObject);
        Assert.assertNotNull(pageId);

        final int oldOrder = page.getInt(Page.PAGE_ORDER);
        pageMgmtService.changeOrder(pageId, "up");

        final JSONObject result = getPageQueryService().getPage(pageId);
        Assert.assertNotNull(result);
        Assert.assertTrue(oldOrder > result.getJSONObject(Page.PAGE).getInt(Page.PAGE_ORDER));
    }
}
