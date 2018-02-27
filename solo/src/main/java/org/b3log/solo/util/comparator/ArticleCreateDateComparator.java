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
package org.b3log.solo.util.comparator;

import org.b3log.solo.model.Article;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.Date;

/**
 * Article comparator by create date.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Dec 30, 2010
 */
public final class ArticleCreateDateComparator implements Comparator<JSONObject> {

    /**
     * Package default constructor.
     */
    ArticleCreateDateComparator() {
    }

    @Override
    public int compare(final JSONObject article1, final JSONObject article2) {
        try {
            final Date date1 = (Date) article1.get(Article.ARTICLE_CREATE_DATE);
            final Date date2 = (Date) article2.get(Article.ARTICLE_CREATE_DATE);

            return date2.compareTo(date1);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
