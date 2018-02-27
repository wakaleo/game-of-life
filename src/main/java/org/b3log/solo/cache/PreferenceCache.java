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
package org.b3log.solo.cache;

import org.b3log.latke.cache.Cache;
import org.b3log.latke.cache.CacheFactory;
import org.b3log.latke.ioc.inject.Named;
import org.b3log.latke.ioc.inject.Singleton;
import org.b3log.solo.model.Option;
import org.json.JSONObject;

/**
 * Preference cache.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Jul 22, 2017
 * @since 2.3.0
 */
@Named
@Singleton
public class PreferenceCache {

    /**
     * Preference cache.
     */
    private Cache cache = CacheFactory.getCache(Option.CATEGORY_C_PREFERENCE);

    /**
     * Get the preference.
     *
     * @return preference
     */
    public JSONObject getPreference() {
        return cache.get(Option.CATEGORY_C_PREFERENCE);
    }

    /**
     * Adds or updates the specified preference.
     *
     * @param preference the specified preference
     */
    public void putPreference(final JSONObject preference) {
        cache.put(Option.CATEGORY_C_PREFERENCE, preference);
    }

    /**
     * Clears the preference.
     */
    public void clear() {
        cache.removeAll();
    }
}
