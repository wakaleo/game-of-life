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

/**
 * Comparators utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Dec 30, 2010
 */
public final class Comparators {

    /**
     * Article create date comparator.
     */
    public static final ArticleCreateDateComparator ARTICLE_CREATE_DATE_COMPARATOR = new ArticleCreateDateComparator();

    /**
     * Article update date comparator.
     */
    public static final ArticleUpdateDateComparator ARTICLE_UPDATE_DATE_COMPARATOR = new ArticleUpdateDateComparator();

    /**
     * Tag reference count comparator.
     */
    public static final TagRefCntComparator TAG_REF_CNT_COMPARATOR = new TagRefCntComparator();

    /**
     * Private default constructor.
     */
    private Comparators() {
    }
}
