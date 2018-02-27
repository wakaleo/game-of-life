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
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.processor.FeedProcessor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Atom Feed.
 * <p>
 * See <a href="http://tools.ietf.org/html/rfc4287">RFC 4278</a> for more details.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.2, Oct 21, 2017
 * @see Entry
 * @see Category
 * @since 0.3.1
 */
public final class Feed {
    /**
     * Time zone id.
     */
    public static final String TIME_ZONE_ID = "Asia/Shanghai";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FeedProcessor.class);

    /**
     * Link variable.
     */
    private static final String LINK_VARIABLE = "${link}";

    /**
     * Start document.
     */
    private static final String START_DOCUMENT = "<?xml version=\"1.0\"?>";

    /**
     * Start feed element.
     */
    private static final String START_FEED_ELEMENT = "<feed xmlns=\"http://www.w3.org/2005/Atom\">";

    /**
     * End feed element.
     */
    private static final String END_FEED_ELEMENT = "</feed>";

    /**
     * Start id element.
     */
    private static final String START_ID_ELEMENT = "<id>";

    /**
     * End if element.
     */
    private static final String END_ID_ELEMENT = "</id>";

    /**
     * Start title element.
     */
    private static final String START_TITLE_ELEMENT = "<title type=\"text\">";

    /**
     * End title element.
     */
    private static final String END_TITLE_ELEMENT = "</title>";

    /**
     * Start subtitle element.
     */
    private static final String START_SUBTITLE_ELEMENT = "<subtitle type=\"text\"> ";

    /**
     * End subtitle element.
     */
    private static final String END_SUBTITLE_ELEMENT = "</subtitle>";

    /**
     * Start updated element.
     */
    private static final String START_UPDATED_ELEMENT = "<updated>";

    /**
     * End updated element.
     */
    private static final String END_UPDATED_ELEMENT = "</updated>";

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
     * Link element.
     */
    private static final String LINK_ELEMENT = "<link href=\"" + LINK_VARIABLE + "\" rel=\"self\" " + "type=\"application/atom+xml\" />";

    /**
     * Id.
     */
    private String id;

    /**
     * Title.
     */
    private String title;

    /**
     * Subtitle.
     */
    private String subtitle;

    /**
     * Update date.
     */
    private Date updated;

    /**
     * Author.
     */
    private String author;

    /**
     * Link.
     */
    private String link;

    /**
     * Entries.
     */
    private List<Entry> entries = new ArrayList<>();

    /**
     * Returns pretty print of the specified xml string.
     *
     * @param xml the specified xml string
     * @return the pretty print of the specified xml string
     * @throws Exception exception
     */
    private static String format(final String xml) {
        try {
            final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document doc = db.parse(new InputSource(new StringReader(xml)));
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            final StreamResult result = new StreamResult(new StringWriter());
            final DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);

            return result.getWriter().toString();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "format pretty XML failed", e);

            return xml;
        }
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
     * Gets the subtitle.
     *
     * @return subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets the subtitle with the specified subtitle.
     *
     * @param subtitle the specified subtitle
     */
    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
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
     * Adds the specified entry.
     *
     * @param entry the specified entry
     */
    public void addEntry(final Entry entry) {
        entries.add(entry);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(START_DOCUMENT);
        stringBuilder.append(START_FEED_ELEMENT);

        stringBuilder.append(START_ID_ELEMENT);
        stringBuilder.append(id);
        stringBuilder.append(END_ID_ELEMENT);

        stringBuilder.append(START_TITLE_ELEMENT);
        stringBuilder.append(title);
        stringBuilder.append(END_TITLE_ELEMENT);

        stringBuilder.append(START_SUBTITLE_ELEMENT);
        stringBuilder.append(subtitle);
        stringBuilder.append(END_SUBTITLE_ELEMENT);

        stringBuilder.append(START_UPDATED_ELEMENT);
        stringBuilder.append(DateFormatUtils.format(// using ISO-8601 instead of RFC-3339
                updated, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern(), TimeZone.getTimeZone(TIME_ZONE_ID)));
        stringBuilder.append(END_UPDATED_ELEMENT);

        stringBuilder.append(START_AUTHOR_ELEMENT);
        stringBuilder.append(START_NAME_ELEMENT);
        stringBuilder.append(author);
        stringBuilder.append(END_NAME_ELEMENT);
        stringBuilder.append(END_AUTHOR_ELEMENT);

        stringBuilder.append(LINK_ELEMENT.replace(LINK_VARIABLE, link));

        for (final Entry entry : entries) {
            stringBuilder.append(entry.toString());
        }

        stringBuilder.append(END_FEED_ELEMENT);

        return format(stringBuilder.toString());
    }
}
