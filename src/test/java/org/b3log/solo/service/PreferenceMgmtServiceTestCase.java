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
import org.b3log.solo.model.Option;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link PreferenceMgmtService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.3, Nov 23, 2015
 */
@Test(suiteName = "service")
public class PreferenceMgmtServiceTestCase extends AbstractTestCase {

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
     * Update Preference.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void updatePreference() throws Exception {
        final PreferenceMgmtService preferenceMgmtService = getPreferenceMgmtService();
        final PreferenceQueryService preferenceQueryService = getPreferenceQueryService();
        JSONObject preference = preferenceQueryService.getPreference();

        Assert.assertEquals(preference.getString(Option.ID_C_BLOG_TITLE),
                Option.DefaultPreference.DEFAULT_BLOG_TITLE);

        preference.put(Option.ID_C_BLOG_TITLE, "updated blog title");
        preferenceMgmtService.updatePreference(preference);

        preference = preferenceQueryService.getPreference();
        Assert.assertEquals(preference.getString(Option.ID_C_BLOG_TITLE), "updated blog title");
    }

    /**
     * Update Reply Notification Template.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void updateReplyNotificationTemplate() throws Exception {
        final PreferenceMgmtService preferenceMgmtService = getPreferenceMgmtService();
        final PreferenceQueryService preferenceQueryService = getPreferenceQueryService();
        JSONObject replyNotificationTemplate = preferenceQueryService.getReplyNotificationTemplate();

        Assert.assertEquals(replyNotificationTemplate.toString(), Option.DefaultPreference.DEFAULT_REPLY_NOTIFICATION_TEMPLATE);

        replyNotificationTemplate.put("subject", "updated subject");
        preferenceMgmtService.updateReplyNotificationTemplate(replyNotificationTemplate);

        replyNotificationTemplate = preferenceQueryService.getReplyNotificationTemplate();
        Assert.assertEquals(replyNotificationTemplate.getString("subject"), "updated subject");
    }
}
