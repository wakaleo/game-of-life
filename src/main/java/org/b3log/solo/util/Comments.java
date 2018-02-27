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
package org.b3log.solo.util;

import org.b3log.solo.model.Article;
import org.b3log.solo.model.Page;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Comment utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.12, Jun 4, 2013
 * @since 0.3.1
 */
public final class Comments {

    /**
     * Gets comment sharp URL with the specified page and comment id.
     *
     * @param page      the specified page
     * @param commentId the specified comment id
     * @return comment sharp URL
     * @throws JSONException json exception
     */
    public static String getCommentSharpURLForPage(final JSONObject page, final String commentId) throws JSONException {
        return page.getString(Page.PAGE_PERMALINK) + "#" + commentId;
    }

    /**
     * Gets comment sharp URL with the specified article and comment id.
     *
     * @param article   the specified article
     * @param commentId the specified comment id
     * @return comment sharp URL
     * @throws JSONException json exception
     */
    public static String getCommentSharpURLForArticle(final JSONObject article, final String commentId) throws JSONException {
        final String articleLink = article.getString(Article.ARTICLE_PERMALINK);

        return articleLink + "#" + commentId;
    }

    /**
     * Private constructor.
     */
    private Comments() {
    }
}
