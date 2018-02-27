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

import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Category;
import org.b3log.solo.repository.CategoryRepository;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link CategoryRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Apr 12, 2017
 * @since 2.0.0
 */
@Test(suiteName = "repository")
public final class CategoryRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Tests.
     *
     * @throws Exception exception
     */
    @Test
    public void test() throws Exception {
        final CategoryRepository categoryRepository = getCategoryRepository();

        final int category1Order = 1, category2Order = 2, category3Order = 3;

        JSONObject category1 = new JSONObject();
        category1.put(Category.CATEGORY_TITLE, "category title");
        category1.put(Category.CATEGORY_DESCRIPTION, "cateogry description");
        category1.put(Category.CATEGORY_URI, "category uri");
        category1.put(Category.CATEGORY_ORDER, category1Order);
        category1.put(Category.CATEGORY_TAG_CNT, 0);

        Transaction transaction = categoryRepository.beginTransaction();
        categoryRepository.add(category1);
        transaction.commit();

        Assert.assertNull(categoryRepository.getByTitle("title"));
        Assert.assertNotNull(categoryRepository.getByTitle("category title"));

        Assert.assertNull(categoryRepository.getByOrder(0));
        Assert.assertNotNull(categoryRepository.getByOrder(category1Order));

        final JSONObject category2 = new JSONObject();
        category2.put(Category.CATEGORY_TITLE, "category title");
        category2.put(Category.CATEGORY_DESCRIPTION, "cateogry description");
        category2.put(Category.CATEGORY_URI, "category uri");
        category2.put(Category.CATEGORY_ORDER, category2Order);
        category2.put(Category.CATEGORY_TAG_CNT, 0);

        transaction = categoryRepository.beginTransaction();
        final String category2Id = categoryRepository.add(category2);
        transaction.commit();

        Assert.assertEquals(categoryRepository.getMaxOrder(), category2Order);

        JSONObject category3 = new JSONObject();
        category3.put(Category.CATEGORY_TITLE, "category title");
        category3.put(Category.CATEGORY_DESCRIPTION, "cateogry description");
        category3.put(Category.CATEGORY_URI, "category uri");
        category3.put(Category.CATEGORY_ORDER, category3Order);
        category3.put(Category.CATEGORY_TAG_CNT, 0);

        transaction = categoryRepository.beginTransaction();
        categoryRepository.add(category3);
        transaction.commit();

        final int total = 3;
        Assert.assertEquals(categoryRepository.count(), total);

        category1 = categoryRepository.getUpper(category2Id);
        Assert.assertNotNull(category1);
        Assert.assertEquals(category1.getInt(Category.CATEGORY_ORDER), category1Order);

        category3 = categoryRepository.getUnder(category2Id);
        Assert.assertNotNull(category3);
        Assert.assertEquals(category3.getInt(Category.CATEGORY_ORDER), category3Order);
    }
}
