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
import org.b3log.solo.model.Link;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link LinkMgmtService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Sep 11, 2012
 */
@Test(suiteName = "service")
public class LinkMgmtServiceTestCase extends AbstractTestCase {

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
     * Add Link.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addLink() throws Exception {
        final LinkMgmtService linkMgmtService = getLinkMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject link = new JSONObject();
        requestJSONObject.put(Link.LINK, link);

        link.put(Link.LINK_TITLE, "link1 title");
        link.put(Link.LINK_ADDRESS, "link1 address");
        link.put(Link.LINK_DESCRIPTION, "link1 description");

        final String linkId = linkMgmtService.addLink(requestJSONObject);
        Assert.assertNotNull(linkId);
    }

    /**
     * Remove Link.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void removeLink() throws Exception {
        final LinkMgmtService linkMgmtService = getLinkMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject link = new JSONObject();
        requestJSONObject.put(Link.LINK, link);

        link.put(Link.LINK_TITLE, "link2 title");
        link.put(Link.LINK_ADDRESS, "link2 address");
        link.put(Link.LINK_DESCRIPTION, "link2 description");

        final String linkId = linkMgmtService.addLink(requestJSONObject);
        Assert.assertNotNull(linkId);

        final LinkQueryService linkQueryService = getLinkQueryService();
        JSONObject result = linkQueryService.getLink(linkId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Link.LINK).
                getString(Link.LINK_TITLE), "link2 title");

        linkMgmtService.removeLink(linkId);

        result = linkQueryService.getLink(linkId);
        Assert.assertNull(result);
    }

    /**
     * Update Link.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void updateLink() throws Exception {
        final LinkMgmtService linkMgmtService = getLinkMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject link = new JSONObject();
        requestJSONObject.put(Link.LINK, link);

        link.put(Link.LINK_TITLE, "link3 title");
        link.put(Link.LINK_ADDRESS, "link3 address");
        link.put(Link.LINK_DESCRIPTION, "link3 description");

        final String linkId = linkMgmtService.addLink(requestJSONObject);
        Assert.assertNotNull(linkId);

        final LinkQueryService linkQueryService = getLinkQueryService();
        JSONObject result = linkQueryService.getLink(linkId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Link.LINK).
                getString(Link.LINK_TITLE), "link3 title");

        link.put(Link.LINK_TITLE, "updated link3 title");
        linkMgmtService.updateLink(requestJSONObject);

        result = linkQueryService.getLink(linkId);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getJSONObject(Link.LINK).getString(
                Link.LINK_TITLE), "updated link3 title");
    }

    /**
     * Change Order.
     * 
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "addLink")
    public void changeOrder() throws Exception {
        final LinkMgmtService linkMgmtService = getLinkMgmtService();

        final JSONObject requestJSONObject = new JSONObject();
        final JSONObject link = new JSONObject();
        requestJSONObject.put(Link.LINK, link);

        link.put(Link.LINK_TITLE, "link4 title");
        link.put(Link.LINK_ADDRESS, "link4 address");
        link.put(Link.LINK_DESCRIPTION, "link4 description");

        final String linkId = linkMgmtService.addLink(requestJSONObject);
        Assert.assertNotNull(linkId);

        final int oldOrder = link.getInt(Link.LINK_ORDER);
        linkMgmtService.changeOrder(linkId, "up");

        final JSONObject result = getLinkQueryService().getLink(linkId);
        Assert.assertNotNull(result);
        Assert.assertTrue(oldOrder > result.getJSONObject(Link.LINK).getInt(
                Link.LINK_ORDER));
    }
}
