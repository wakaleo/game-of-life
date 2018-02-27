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

import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * {@link org.b3log.solo.service.PermalinkQueryService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Jun 21, 2013
 * @since 0.6.1
 */
public final class PermalinkQueryServiceTestCase {

    /**
     * Test method for {@linkplain Permalinks#matchDefaultArticlePermalinkFormat(java.lang.String)}.
     */
    @Test
    public void matchDefaultArticlePermalinkFormat() {
        Assert.assertTrue(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1986/08/25/1234567890.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1986/0/25/1234567890.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1986/08/25/a.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1986/aa/25/1234567890.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/1986/aa/25/1234567890.html"));
        Assert.assertFalse(
                PermalinkQueryService.matchDefaultArticlePermalinkFormat(
                "/articles/1986/08/25/1234567890html"));

    }

    /**
     * Test method for {@linkplain Permalinks#matchDefaultPagePermalinkFormat(java.lang.String)}.
     */
    @Test
    public void matchDefaultPagePermalinkFormat() {
        Assert.assertTrue(PermalinkQueryService.matchDefaultPagePermalinkFormat(
                "/pages/1234567890.html"));
        Assert.assertFalse(PermalinkQueryService.matchDefaultPagePermalinkFormat(
                "/pages.html"));
        Assert.assertFalse(PermalinkQueryService.matchDefaultPagePermalinkFormat(
                "/1234567890.html"));
        Assert.assertFalse(PermalinkQueryService.matchDefaultPagePermalinkFormat(
                "/pages/a1234567890.html"));
    }
}
