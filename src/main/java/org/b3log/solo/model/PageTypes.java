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
 * This enumeration defines all page types language configuration keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.0.0.0, Aug 28, 2012
 * @since 0.3.1
 */
public enum PageTypes {

    /**
     * Article.
     */
    ARTICLE("articleLabel", "Article"),
    /**
     * Tag articles.
     */
    TAG_ARTICLES("tagArticlesLabel", "TagArticles"),
    /**
     * Date articles.
     */
    DATE_ARTICLES("dateArticlesLabel", "DateArticles"),
    /**
     * Index.
     */
    INDEX("indexArticleLabel", "Index"),
    /**
     * Tags.
     */
    TAGS("allTagsLabel", "Tags"),
    /**
     * Author articles.
     */
    AUTHOR_ARTICLES("authorArticlesLabel", "AuthorArticles"),
    /**
     * Page.
     */
    PAGE("customizedPageLabel", "Page"),
    /**
     * Kill browser page.
     */
    KILL_BROWSER("killBrowserPageLabel", "KillBrowser"),
    /**
     * User template.
     */
    USER_TEMPLATE("userTemplatePageLabel", "UserTemplate");
    /**
     * Language label.
     */
    private final String langLabel;
    /**
     * Type name.
     */
    private final String typeName;
    
    /**
     * Gets the language label.
     * 
     * @return language label
     */
    public String getLangeLabel() {
        return langLabel;
    }
    
    /**
     * Gets the type name.
     * 
     * @return type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Constructs a page type with the specified language label and type name.
     * 
     * @param langLabel the specified language label
     * @param typeName the specified type name
     */
    PageTypes(final String langLabel, final String typeName) {
        this.langLabel = langLabel;
        this.typeName = typeName;
    }
}
