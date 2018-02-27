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
import org.b3log.solo.model.Category;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link CategoryMgmtService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Apr 12, 2017
 * @since 2.0.0
 */
@Test(suiteName = "service")
public class CategoryMgmtServiceTestCase extends AbstractTestCase {

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
     * Add a category.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void addCategory() throws Exception {
        final CategoryMgmtService categoryMgmtService = getCategoryMgmtService();

        final JSONObject category = new JSONObject();
        category.put(Category.CATEGORY_TITLE, "category1 title");
        category.put(Category.CATEGORY_URI, "category1 uri");
        category.put(Category.CATEGORY_DESCRIPTION, "category1 description");

        final String categoryId = categoryMgmtService.addCategory(category);
        Assert.assertNotNull(categoryId);
    }

    /**
     * Remove a category.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void removeCategory() throws Exception {
        final CategoryMgmtService categoryMgmtService = getCategoryMgmtService();

        final JSONObject category = new JSONObject();
        category.put(Category.CATEGORY_TITLE, "category2 title");
        category.put(Category.CATEGORY_URI, "category2 uri");
        category.put(Category.CATEGORY_DESCRIPTION, "category2 description");

        final String categoryId = categoryMgmtService.addCategory(category);
        Assert.assertNotNull(categoryId);

        final CategoryQueryService categoryQueryService = getCategoryQueryService();
        JSONObject result = categoryQueryService.getCategory(categoryId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getString(Category.CATEGORY_TITLE), "category2 title");

        categoryMgmtService.removeCategory(categoryId);

        result = categoryQueryService.getCategory(categoryId);
        Assert.assertNull(result);
    }

    /**
     * Update a category.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void updateCategory() throws Exception {
        final CategoryMgmtService categoryMgmtService = getCategoryMgmtService();

        final JSONObject category = new JSONObject();
        category.put(Category.CATEGORY_TITLE, "category3 title");
        category.put(Category.CATEGORY_URI, "category3 uri");
        category.put(Category.CATEGORY_DESCRIPTION, "category3 description");

        final String categoryId = categoryMgmtService.addCategory(category);
        Assert.assertNotNull(categoryId);

        final CategoryQueryService categoryQueryService = getCategoryQueryService();
        JSONObject result = categoryQueryService.getCategory(categoryId);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getString(Category.CATEGORY_TITLE), "category3 title");

        category.put(Category.CATEGORY_TITLE, "updated category3 title");
        categoryMgmtService.updateCategory(categoryId, category);

        result = categoryQueryService.getCategory(categoryId);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getString(Category.CATEGORY_TITLE), "updated category3 title");
    }

    /**
     * Change Order.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "addCategory")
    public void changeOrder() throws Exception {
        final CategoryMgmtService categoryMgmtService = getCategoryMgmtService();

        final JSONObject category = new JSONObject();
        category.put(Category.CATEGORY_TITLE, "category4 title");
        category.put(Category.CATEGORY_URI, "category4 uri");
        category.put(Category.CATEGORY_DESCRIPTION, "category4 description");

        final String categoryId = categoryMgmtService.addCategory(category);
        Assert.assertNotNull(categoryId);

        final int oldOrder = category.getInt(Category.CATEGORY_ORDER);
        categoryMgmtService.changeOrder(categoryId, "up");

        final JSONObject result = getCategoryQueryService().getCategory(categoryId);
        Assert.assertNotNull(result);
        Assert.assertTrue(oldOrder > result.getInt(Category.CATEGORY_ORDER));
    }
}
