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
package org.b3log.solo.event;


/**
 * Event types.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.7, May 30, 2014
 * @since 0.3.1
 */
public final class EventTypes {

    /**
     * Indicates a add article event.
     */
    public static final String ADD_ARTICLE = "Add Article";

    /**
     * Indicates a update article event.
     */
    public static final String UPDATE_ARTICLE = "Update Article";

    /**
     * Indicates a remove article event.
     */
    public static final String REMOVE_ARTICLE = "Remove Article";
    
    /**
     * Indicates a before render article event.
     */
    public static final String BEFORE_RENDER_ARTICLE = "Before Render Article";

    /**
     * Indicates an add comment to article event.
     */
    public static final String ADD_COMMENT_TO_ARTICLE = "Add Comment To Article";

    /**
     * Indicates an add comment (from symphony) to article event.
     */
    public static final String ADD_COMMENT_TO_ARTICLE_FROM_SYMPHONY = "Add Comment To Article From Symphony";

    /**
     * Indicates an add comment to page event.
     */
    public static final String ADD_COMMENT_TO_PAGE = "Add Comment To Page";

    /**
     * Indicates a remove comment event.
     */
    public static final String REMOVE_COMMENT = "Remove Comment";

    /**
     * Private default constructor.
     */
    private EventTypes() {}
}
