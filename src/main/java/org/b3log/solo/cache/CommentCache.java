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

import org.b3log.latke.Keys;
import org.b3log.latke.cache.Cache;
import org.b3log.latke.cache.CacheFactory;
import org.b3log.latke.ioc.inject.Named;
import org.b3log.latke.ioc.inject.Singleton;
import org.b3log.solo.model.Comment;
import org.b3log.solo.util.JSONs;
import org.json.JSONObject;

/**
 * Comment cache.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Jul 16, 2017
 * @since 2.3.0
 */
@Named
@Singleton
public class CommentCache {

    /**
     * Comment cache.
     */
    private Cache cache = CacheFactory.getCache(Comment.COMMENTS);

    /**
     * Gets a comment by the specified comment id.
     *
     * @param id the specified comment id
     * @return comment, returns {@code null} if not found
     */
    public JSONObject getComment(final String id) {
        final JSONObject comment = cache.get(id);
        if (null == comment) {
            return null;
        }

        return JSONs.clone(comment);
    }

    /**
     * Adds or updates the specified comment.
     *
     * @param comment the specified comment
     */
    public void putComment(final JSONObject comment) {
        cache.put(comment.optString(Keys.OBJECT_ID), JSONs.clone(comment));
    }

    /**
     * Removes a comment by the specified comment id.
     *
     * @param id the specified comment id
     */
    public void removeComment(final String id) {
        cache.remove(id);
    }
}
