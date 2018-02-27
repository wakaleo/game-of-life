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
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Option query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Jul 22, 2017
 * @since 0.6.0
 */
@Service
public class OptionQueryService {

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Gets an option with the specified option id.
     *
     * @param optionId the specified option id
     * @return an option, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getOptionById(final String optionId) throws ServiceException {
        try {
            return optionRepository.get(optionId);
        } catch (final RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets options with the specified category.
     * <p>
     * All options with the specified category will be merged into one json object as the return value.
     * </p>
     *
     * @param category the specified category
     * @return all options with the specified category, for example,
     * <pre>
     * {
     *     "${optionId}": "${optionValue}",
     *     ....
     * }
     * </pre>, returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getOptions(final String category) throws ServiceException {
        final Query query = new Query().
                setFilter(new PropertyFilter(Option.OPTION_CATEGORY, FilterOperator.EQUAL, category));

        try {
            final JSONObject result = optionRepository.get(query);
            final JSONArray options = result.getJSONArray(Keys.RESULTS);
            if (0 == options.length()) {
                return null;
            }

            final JSONObject ret = new JSONObject();

            for (int i = 0; i < options.length(); i++) {
                final JSONObject option = options.getJSONObject(i);

                ret.put(option.getString(Keys.OBJECT_ID), option.opt(Option.OPTION_VALUE));
            }

            return ret;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sets the option repository with the specified option repository.
     *
     * @param optionRepository the specified option repository
     */
    public void setOptionRepository(final OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
}
