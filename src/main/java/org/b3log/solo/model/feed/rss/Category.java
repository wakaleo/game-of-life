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
package org.b3log.solo.model.feed.rss;

/**
 * Category.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Sep 12, 2011
 * @since 0.3.1
 */
public final class Category {

    /**
     * Category element.
     */
    private static final String CATEGORY_ELEMENT = "<category>${term}</category>";

    /**
     * Term.
     */
    private String term;

    /**
     * Gets the term.
     *
     * @return term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the term with the specified term.
     *
     * @param term the specified term
     */
    public void setTerm(final String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return CATEGORY_ELEMENT.replace("${term}", term);
    }
}
