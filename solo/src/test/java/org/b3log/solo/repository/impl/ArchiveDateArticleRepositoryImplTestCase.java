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

import org.b3log.latke.Keys;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.model.Article;
import org.b3log.solo.repository.ArchiveDateArticleRepository;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link ArchiveDateArticleRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Dec 31, 2011
 */
@Test(suiteName = "repository")
public class ArchiveDateArticleRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Adds successfully.
     *
     * @throws Exception exception
     */
    @Test
    public void add() throws Exception {
        final ArchiveDateArticleRepository archiveDateArticleRepository = getArchiveDateArticleRepository();

        final JSONObject archiveDateArticle = new JSONObject();

        archiveDateArticle.put(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID, "archiveDateId");
        archiveDateArticle.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, "articleId");

        final Transaction transaction = archiveDateArticleRepository.beginTransaction();
        archiveDateArticleRepository.add(archiveDateArticle);
        transaction.commit();

        final JSONObject found = archiveDateArticleRepository.getByArticleId("articleId");
        Assert.assertNotNull(found);

        final JSONObject notFound = archiveDateArticleRepository.getByArticleId("not found");
        Assert.assertNull(notFound);
    }

    /**
     * Get By ArchiveDate Id.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByArchiveDateId() throws Exception {
        final ArchiveDateArticleRepository archiveDateArticleRepository = getArchiveDateArticleRepository();

        final JSONObject found = archiveDateArticleRepository.getByArchiveDateId("archiveDateId", 1, Integer.MAX_VALUE);
        Assert.assertNotNull(found);

        final JSONObject notFound = archiveDateArticleRepository.getByArchiveDateId("not found", 1, Integer.MAX_VALUE);
        Assert.assertNotNull(notFound);
    }

    /**
     * Get By Archive Id.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByArticleId() throws Exception {
        final ArchiveDateArticleRepository archiveDateArticleRepository = getArchiveDateArticleRepository();

        Assert.assertNotNull(archiveDateArticleRepository.getByArticleId("articleId"));
        Assert.assertNull(archiveDateArticleRepository.getByArticleId("not found"));
    }
}
