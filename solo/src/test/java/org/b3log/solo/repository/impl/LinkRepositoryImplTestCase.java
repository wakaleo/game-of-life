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
import org.b3log.solo.model.Link;
import org.b3log.solo.repository.LinkRepository;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link LinkRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Dec 29, 2011
 */
@Test(suiteName = "repository")
public final class LinkRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Tests.
     * 
     * @throws Exception exception
     */
    @Test
    public void test() throws Exception {
        final LinkRepository linkRepository = getLinkRepository();

        final int link1Order = 1, link2Order = 2, link3Order = 3;

        JSONObject link1 = new JSONObject();

        link1.put(Link.LINK_TITLE, "link title");
        link1.put(Link.LINK_DESCRIPTION, "link description");
        link1.put(Link.LINK_ADDRESS, "link address");
        link1.put(Link.LINK_ORDER, link1Order);

        Transaction transaction = linkRepository.beginTransaction();
        linkRepository.add(link1);
        transaction.commit();

        Assert.assertNull(linkRepository.getByAddress("test"));
        Assert.assertNotNull(linkRepository.getByAddress("link address"));

        Assert.assertNull(linkRepository.getByOrder(0));
        Assert.assertNotNull(linkRepository.getByOrder(link1Order));

        final JSONObject link2 = new JSONObject();

        link2.put(Link.LINK_TITLE, "link title");
        link2.put(Link.LINK_DESCRIPTION, "link description");
        link2.put(Link.LINK_ADDRESS, "link address");
        link2.put(Link.LINK_ORDER, link2Order);

        transaction = linkRepository.beginTransaction();
        final String link2Id = linkRepository.add(link2);
        transaction.commit();

        Assert.assertEquals(linkRepository.getMaxOrder(), link2Order);


        JSONObject link3 = new JSONObject();

        link3.put(Link.LINK_TITLE, "link title");
        link3.put(Link.LINK_DESCRIPTION, "link description");
        link3.put(Link.LINK_ADDRESS, "link address");
        link3.put(Link.LINK_ORDER, link3Order);
        transaction = linkRepository.beginTransaction();

        linkRepository.add(link3);

        transaction.commit();

        final int total = 3;
        Assert.assertEquals(linkRepository.count(), total);

        link1 = linkRepository.getUpper(link2Id);
        Assert.assertNotNull(link1);
        Assert.assertEquals(link1.getInt(Link.LINK_ORDER), link1Order);

        link3 = linkRepository.getUnder(link2Id);
        Assert.assertNotNull(link3);
        Assert.assertEquals(link3.getInt(Link.LINK_ORDER), link3Order);
    }
}
