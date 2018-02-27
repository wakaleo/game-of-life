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

import junit.framework.Assert;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.ArchiveDate;
import org.b3log.solo.repository.ArchiveDateRepository;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

/**
 * {@link ArchiveDateRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Jul 20, 2017
 */
@Test(suiteName = "repository")
public class ArchiveDateRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Adds successfully.
     *
     * @throws Exception exception
     */
    @Test
    public void add() throws Exception {
        final ArchiveDateRepository archiveDateRepository = getArchiveDateRepository();

        final JSONObject archiveDate = new JSONObject();

        archiveDate.put(ArchiveDate.ARCHIVE_TIME, DateUtils.parseDate("2011/12", new String[]{"yyyy/MM"}).getTime());
        archiveDate.put(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT, 1);
        archiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT, 1);

        final Transaction transaction = archiveDateRepository.beginTransaction();
        archiveDateRepository.add(archiveDate);
        transaction.commit();

        final List<JSONObject> archiveDates = archiveDateRepository.getArchiveDates();
        Assert.assertNotNull(archiveDates);
        Assert.assertEquals(1, archiveDates.size());
    }

    /**
     * Get By ArchiveDate.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "add")
    public void getByArchiveDate() throws Exception {
        final ArchiveDateRepository archiveDateRepository = getArchiveDateRepository();

        final JSONObject archiveDate = archiveDateRepository.getByArchiveDate("2011/12");
        Assert.assertNotNull(archiveDate);
        Assert.assertEquals(archiveDate.optInt(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT), 1);
        //System.out.println(archiveDate.toString(SoloServletListener.JSON_PRINT_INDENT_FACTOR));
    }
}
