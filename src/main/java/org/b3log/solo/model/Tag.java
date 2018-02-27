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
 * This class defines all tag model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.7, Dec 3, 2010
 */
public final class Tag {

    /**
     * Tag.
     */
    public static final String TAG = "tag";

    /**
     * Tags.
     */
    public static final String TAGS = "tags";

    /**
     * Key of title.
     */
    public static final String TAG_TITLE = "tagTitle";

    /**
     * Key of tag reference count.
     */
    public static final String TAG_REFERENCE_COUNT = "tagReferenceCount";

    /**
     * Key of tag reference(published article) count.
     */
    public static final String TAG_PUBLISHED_REFERENCE_COUNT = "tagPublishedRefCount";

    /**
     * Private default constructor.
     */
    private Tag() {}
}
