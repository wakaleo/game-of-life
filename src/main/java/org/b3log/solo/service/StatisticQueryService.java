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

import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.cache.StatisticCache;
import org.b3log.solo.model.Option;
import org.json.JSONObject;

/**
 * Statistic query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.0.0.0, Sep 6, 2017
 * @since 0.5.0
 */
@Service
public class StatisticQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(StatisticQueryService.class);

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Statistic cache.
     */
    @Inject
    private StatisticCache statisticCache;

    /**
     * Gets the online visitor count.
     *
     * @return online visitor count
     */
    public static int getOnlineVisitorCount() {
        return StatisticMgmtService.ONLINE_VISITORS.size();
    }

    /**
     * Get blog comment count.
     *
     * @return blog comment count
     * @throws ServiceException service exception
     */
    public int getBlogCommentCount() throws ServiceException {
        final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_STATISTIC_BLOG_COMMENT_COUNT);
        if (null == opt) {
            throw new ServiceException("Not found statistic");
        }

        return opt.optInt(Option.OPTION_VALUE);
    }

    /**
     * Get blog comment(published article) count.
     *
     * @return blog comment count
     * @throws ServiceException service exception
     */
    public int getPublishedBlogCommentCount() throws ServiceException {
        final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT);
        if (null == opt) {
            throw new ServiceException("Not found statistic");
        }

        return opt.optInt(Option.OPTION_VALUE);
    }

    /**
     * Gets blog statistic published article count.
     *
     * @return published blog article count
     * @throws ServiceException service exception
     */
    public int getPublishedBlogArticleCount() throws ServiceException {
        final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_STATISTIC_PUBLISHED_ARTICLE_COUNT);
        if (null == opt) {
            throw new ServiceException("Not found statistic");
        }

        return opt.optInt(Option.OPTION_VALUE);
    }

    /**
     * Gets blog statistic article count.
     *
     * @return blog article count
     * @throws ServiceException service exception
     */
    public int getBlogArticleCount() throws ServiceException {
        final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_STATISTIC_BLOG_ARTICLE_COUNT);
        if (null == opt) {
            throw new ServiceException("Not found statistic");
        }

        return opt.optInt(Option.OPTION_VALUE);
    }

    /**
     * Gets the statistic.
     *
     * @return statistic, returns {@code null} if not found
     * @throws ServiceException if repository exception
     */
    public JSONObject getStatistic() throws ServiceException {
        JSONObject ret = statisticCache.getStatistic();
        if (null == ret) {
            ret = optionQueryService.getOptions(Option.CATEGORY_C_STATISTIC);
            statisticCache.putStatistic(ret);
        }

        return ret;
    }

    /**
     * Sets the option query service with the specified option query service.
     *
     * @param optionQueryService the specified option query service
     */
    public void setOptionQueryService(final OptionQueryService optionQueryService) {
        this.optionQueryService = optionQueryService;
    }
}
