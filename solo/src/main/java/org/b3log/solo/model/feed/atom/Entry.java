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
package org.b3log.solo.model.feed.atom;


import org.apache.commons.lang.time.DateFormatUtils;

import java.util.*;


/**
 * Entry.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Sep 12, 2011
 * @since 0.3.1
 */
public final class Entry {

    /**
     * Link variable.
     */
    private static final String LINK_VARIABLE = "${link}";

    /**
     * Start title element.
     */
    private static final String START_TITLE_ELEMENT = "<title type=\"text\">";

    /**
     * End title element.
     */
    private static final String END_TITLE_ELEMENT = "</title>";

    /**
     * Start author element.
     */
    private static final String START_AUTHOR_ELEMENT = "<author>";

    /**
     * End author element.
     */
    private static final String END_AUTHOR_ELEMENT = "</author>";

    /**
     * Start name element.
     */
    private static final String START_NAME_ELEMENT = "<name>";

    /**
     * End name element.
     */
    private static final String END_NAME_ELEMENT = "</name>";

    /**
     * Start URI element.
     */
    private static final String START_URI_ELEMENT = "<uri>";

    /**
     * End URI element.
     */
    private static final String END_URI_ELEMENT = "</uri>";

    /**
     * Start entry element.
     */
    private static final String START_ENTRY_ELEMENT = "<entry>";

    /**
     * End entry element.
     */
    private static final String END_ENTRY_ELEMENT = "</entry>";

    /**
     * Start id element.
     */
    private static final String START_ID_ELEMENT = "<id>";

    /**
     * End id element.
     */
    private static final String END_ID_ELEMENT = "</id>";

    /**
     * Start summary element.
     */
    private static final String START_SUMMARY_ELEMENT = "<summary type=\"html\">";

    /**
     * End summary element.
     */
    private static final String END_SUMMARY_ELEMENT = "</summary>";

    /**
     * Link element.
     */
    private static final String LINK_ELEMENT = "<link href=\"" + LINK_VARIABLE + "\" />";

    /**
     * Start updated element.
     */
    private static final String START_UPDATED_ELEMENT = "<updated>";

    /**
     * End updated element.
     */
    private static final String END_UPDATED_ELEMENT = "</updated>";

    /**
     * Id.
     */
    private String id;

    /**
     * Update date.
     */
    private Date updated;

    /**
     * Title.
     */
    private String title;

    /**
     * Summary.
     */
    private String summary;

    /**
     * Link.
     */
    private String link;

    /**
     * Author.
     */
    private String author;

    /**
     * URI.
     */
    private String uri;

    /**
     * Categories.
     */
    private Set<Category> categories = new HashSet<Category>();

    /**
     * Gets the URI.
     *
     * @return URI
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the URI with the specified URI.
     *
     * @param uri the specified URI
     */
    public void setURI(final String uri) {
        this.uri = uri;
    }

    /**
     * Gets the author.
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author with the specified author.
     *
     * @param author the specified author
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Gets the id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id with the specified id.
     *
     * @param id the specified id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the link.
     *
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link with the specified link.
     *
     * @param link the specified link
     */
    public void setLink(final String link) {
        this.link = link;
    }

    /**
     * Gets the title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title with the specified title.
     *
     * @param title the specified title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets update date.
     *
     * @return update date
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * Sets the update date with the specified update date.
     *
     * @param updated the specified update date
     */
    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    /**
     * Gets the summary.
     *
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary with the specified summary.
     *
     * @param summary the specified summary
     */
    public void setSummary(final String summary) {
        this.summary = summary;
    }

    /**
     * Gets the categories.
     * 
     * @return categories
     */
    public Set<Category> getCatetories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Adds the specified category.
     *
     * @param category the specified category
     */
    public void addCatetory(final Category category) {
        categories.add(category);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(START_ENTRY_ELEMENT).append(START_TITLE_ELEMENT);
        stringBuilder.append(title);
        stringBuilder.append(END_TITLE_ELEMENT);

        stringBuilder.append(START_AUTHOR_ELEMENT);
        stringBuilder.append(START_NAME_ELEMENT);
        stringBuilder.append(author);
        stringBuilder.append(END_NAME_ELEMENT);
        stringBuilder.append(START_URI_ELEMENT);
        stringBuilder.append(uri);
        stringBuilder.append(END_URI_ELEMENT);
        stringBuilder.append(END_AUTHOR_ELEMENT);

        for (final Category category : categories) {
            stringBuilder.append(category.toString());
        }

        stringBuilder.append(LINK_ELEMENT.replace(LINK_VARIABLE, link));

        stringBuilder.append(START_ID_ELEMENT);
        stringBuilder.append(id);
        stringBuilder.append(END_ID_ELEMENT);

        stringBuilder.append(START_UPDATED_ELEMENT);
        stringBuilder.append(DateFormatUtils.format(// using ISO-8601 instead of RFC-3339
            updated, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern(), TimeZone.getTimeZone(Feed.TIME_ZONE_ID)));
        stringBuilder.append(END_UPDATED_ELEMENT);

        stringBuilder.append(START_SUMMARY_ELEMENT);
        stringBuilder.append(summary);
        stringBuilder.append(END_SUMMARY_ELEMENT);

        stringBuilder.append(END_ENTRY_ELEMENT);

        return stringBuilder.toString();
    }
}
