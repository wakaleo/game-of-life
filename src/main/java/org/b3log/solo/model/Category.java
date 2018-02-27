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
package org.b3log.solo.model;

/**
 * This class defines all category model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Mar 30, 2017
 * @since 2.0.0
 */
public final class Category {

    /**
     * Category.
     */
    public static final String CATEGORY = "category";

    /**
     * Categories.
     */
    public static final String CATEGORIES = "categories";

    /**
     * Key of category title.
     */
    public static final String CATEGORY_TITLE = "categoryTitle";

    /**
     * Key of category URI.
     */
    public static final String CATEGORY_URI = "categoryURI";

    /**
     * Key of category description.
     */
    public static final String CATEGORY_DESCRIPTION = "categoryDescription";

    /**
     * Key of category order.
     */
    public static final String CATEGORY_ORDER = "categoryOrder";

    /**
     * Key of category tag count.
     */
    public static final String CATEGORY_TAG_CNT = "categoryTagCnt";

    //// Transient ////
    /**
     * Key of category tags.
     */
    public static final String CATEGORY_T_TAGS = "categoryTags";

    /**
     * Private default constructor.
     */
    private Category() {
    }
}
