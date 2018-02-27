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

import java.util.List;
import junit.framework.Assert;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.TagArticleRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

/**
 * {@link TagArticleRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Dec 30, 2011
 */
@Test(suiteName = "repository")
public class TagArticleRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Add.
     *
     * @throws Exception exception
     */
    @Test
    public void add() throws Exception {
        final TagArticleRepository tagArticleRepository = getTagArticleRepository();

        final JSONObject tagArticle = new JSONObject();

        tagArticle.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, "article1 id");
        tagArticle.put(Tag.TAG + "_" + Keys.OBJECT_ID, "tag1 id");

        final Transaction transaction = tagArticleRepository.beginTransaction();
        tagArticleRepository.add(tagArticle);
        transaction.commit();
    }

    /**
     * Get By ArticleId.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByArticleId() throws Exception {
        final TagArticleRepository tagArticleRepository
                = getTagArticleRepository();

        final List<JSONObject> tagArticle
                = tagArticleRepository.getByArticleId("article1 id");
        Assert.assertNotNull(tagArticle);

        Assert.assertEquals(0, tagArticleRepository.getByArticleId("").size());
    }

    /**
     * Get By TagId.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByTagId() throws Exception {
        final TagArticleRepository tagArticleRepository
                = getTagArticleRepository();

        final JSONArray results
                = tagArticleRepository.getByTagId("tag1 id", 1, Integer.MAX_VALUE).
                getJSONArray(Keys.RESULTS);
        Assert.assertEquals(1, results.length());
    }
}
