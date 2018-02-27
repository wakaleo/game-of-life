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
package org.b3log.solo.processor;


import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.DoNothingRenderer;
import org.b3log.solo.service.StatisticMgmtService;


/**
 * Statistics processor.
 * 
 * <p>
 * Statistics of Solo: 
 * 
 *   <ul>
 *     <li>{@link #viewCounter(org.b3log.latke.servlet.HTTPRequestContext) Blog/Article view counting}</li>
 *   </ul>
 * <p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.2.0, Oct 12, 2013
 * @since 0.4.0
 */
@RequestProcessor
public class StatProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(StatProcessor.class);

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Online visitor count refresher.
     * 
     * @param context the specified context
     */
    @RequestProcessing(value = "/console/stat/onlineVisitorRefresh", method = HTTPRequestMethod.GET)
    public void onlineVisitorCountRefresher(final HTTPRequestContext context) {
        context.setRenderer(new DoNothingRenderer());

        StatisticMgmtService.removeExpiredOnlineVisitor();
    }
}
