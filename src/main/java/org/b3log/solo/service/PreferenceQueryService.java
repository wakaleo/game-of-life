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
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.cache.PreferenceCache;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONObject;

/**
 * Preference query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.3, Jul 22, 2017
 * @since 0.4.0
 */
@Service
public class PreferenceQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PreferenceQueryService.class);

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Optiona query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Preference cache.
     */
    @Inject
    private PreferenceCache preferenceCache;

    /**
     * Gets the reply notification template.
     *
     * @return reply notification template, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getReplyNotificationTemplate() throws ServiceException {
        try {
            final JSONObject ret = new JSONObject();
            final JSONObject preference = getPreference();

            ret.put("subject", preference.optString(Option.ID_C_REPLY_NOTI_TPL_SUBJECT));
            ret.put("body", preference.optString(Option.ID_C_REPLY_NOTI_TPL_BODY));

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Updates reply notification template failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets the user preference.
     *
     * @return user preference, returns {@code null} if not found
     * @throws ServiceException if repository exception
     */
    public JSONObject getPreference() throws ServiceException {
        try {
            final JSONObject checkInit = optionRepository.get(Option.ID_C_ADMIN_EMAIL);
            if (null == checkInit) {
                return null;
            }

            JSONObject ret = preferenceCache.getPreference();
            if (null == ret) {
                ret = optionQueryService.getOptions(Option.CATEGORY_C_PREFERENCE);
                preferenceCache.putPreference(ret);
            }

            return ret;
        } catch (final RepositoryException e) {
            return null;
        }
    }
}
