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

import org.b3log.latke.Keys;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Option;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link OptionQueryService} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Jul 16, 2017
 * @since 0.6.0
 */
@Test(suiteName = "service")
public class OptionQueryServiceTestCase extends AbstractTestCase {

    /**
     * Gets.
     *
     * @throws Exception exception
     */
    @Test
    public void get() throws Exception {
        // Check
        final OptionQueryService optionQueryService = getOptionQueryService();

        JSONObject options = optionQueryService.getOptions(Option.CATEGORY_C_BROADCAST);
        Assert.assertNull(options);

        // Add one
        final OptionMgmtService optionMgmtService = getOptionMgmtService();

        JSONObject option = new JSONObject();
        option.put(Keys.OBJECT_ID, Option.ID_C_BROADCAST_CHANCE_EXPIRATION_TIME);
        option.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_BROADCAST);
        option.put(Option.OPTION_VALUE, 5L);

        final String id = optionMgmtService.addOrUpdateOption(option);
        Assert.assertNotNull(id);

        // Check again

        option = optionQueryService.getOptionById(Option.ID_C_BROADCAST_CHANCE_EXPIRATION_TIME);
        Assert.assertNotNull(option);

        options = optionQueryService.getOptions(Option.CATEGORY_C_BROADCAST);
        Assert.assertNotNull(options);
        Assert.assertEquals(options.optLong(Option.ID_C_BROADCAST_CHANCE_EXPIRATION_TIME), 5L);
    }
}
