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

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Item.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.2, May 10, 2013
 * @since 0.3.1
 */
public final class Item {

    /**
     * Start title element.
     */
    private static final String START_TITLE_ELEMENT = "<title>";

    /**
     * End title element.
     */
    private static final String END_TITLE_ELEMENT = "</title>";

    /**
     * Start link element.
     */
    private static final String START_LINK_ELEMENT = "<link>";

    /**
     * End link element.
     */
    private static final String END_LINK_ELEMENT = "</link>";

    /**
     * Start description element.
     */
    private static final String START_DESCRIPTION_ELEMENT = "<description>";

    /**
     * End summary element.
     */
    private static final String END_DESCRIPTION_ELEMENT = "</description>";

    /**
     * Start author element.
     */
    private static final String START_AUTHOR_ELEMENT = "<author>";

    /**
     * End author element.
     */
    private static final String END_AUTHOR_ELEMENT = "</author>";

    /**
     * Categories.
     */
    private Set<Category> categories = new HashSet<>();

    /**
     * Start guid element.
     */
    private static final String START_GUID_ELEMENT = "<guid>";

    /**
     * End guid element.
     */
    private static final String END_GUID_ELEMENT = "</guid>";

    /**
     * Start pubDate element.
     */
    private static final String START_PUB_DATE_ELEMENT = "<pubDate>";

    /**
     * End pubDate element.
     */
    private static final String END_PUB_DATE_ELEMENT = "</pubDate>";

    /**
     * Guid.
     */
    private String guid;

    /**
     * Publish date.
     */
    private Date pubDate;

    /**
     * Title.
     */
    private String title;

    /**
     * Description.
     */
    private String description;

    /**
     * Link.
     */
    private String link;

    /**
     * Author.
     */
    private String author;

    /**
     * Gets the GUID.
     *
     * @return GUID
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the GUID with the specified GUID.
     *
     * @param guid the specified GUID
     */
    public void setGUID(final String guid) {
        this.guid = guid;
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
     * Gets publish date.
     *
     * @return publish date
     */
    public Date getPubDate() {
        return pubDate;
    }

    /**
     * Sets the publish date with the specified publish date.
     *
     * @param pubDate the specified publish date
     */
    public void setPubDate(final Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Gets the description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description with the specified description.
     *
     * @param description the specified description
     */
    public void setDescription(final String description) {
        this.description = description;
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

        stringBuilder.append("<item>").append(START_TITLE_ELEMENT);
        stringBuilder.append(title);
        stringBuilder.append(END_TITLE_ELEMENT);

        stringBuilder.append(START_LINK_ELEMENT);
        stringBuilder.append(link);
        stringBuilder.append(END_LINK_ELEMENT);

        stringBuilder.append(START_DESCRIPTION_ELEMENT);
        stringBuilder.append(description);
        stringBuilder.append(END_DESCRIPTION_ELEMENT);

        stringBuilder.append(START_AUTHOR_ELEMENT);
        stringBuilder.append(author);
        stringBuilder.append(END_AUTHOR_ELEMENT);

        stringBuilder.append(START_GUID_ELEMENT);
        stringBuilder.append(guid);
        stringBuilder.append(END_GUID_ELEMENT);

        for (final Category category : categories) {
            stringBuilder.append(category.toString());
        }

        stringBuilder.append(START_PUB_DATE_ELEMENT);
        stringBuilder.append(DateFormatUtils.format(pubDate, "EEE, dd MMM yyyy HH:mm:ss Z", Locale.US));
        stringBuilder.append(END_PUB_DATE_ELEMENT).append("</item>");

        return stringBuilder.toString();
    }
}
