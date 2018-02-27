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

/**
 * RSS 2.0 channel.
 * <p>
 * See <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0 at Harvard Law</a> for more details.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.3, Oct 21, 2017
 * @see Item
 * @see Category
 * @since 0.3.1
 */
public final class Channel {

    /**
     * Time zone id.
     */
    public static final String TIME_ZONE_ID = "Asia/Shanghai";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FeedProcessor.class);

    /**
     * Start.
     */
    private static final String START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\" "
            + "xmlns:atom=\"http://www.w3.org/2005/Atom\"><channel>";

    /**
     * End.
     */
    private static final String END = "</channel></rss>";

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
     * Atom link variable.
     */
    private static final String ATOM_LINK_VARIABLE = "${atomLink}";

    /**
     * End link element.
     */
    private static final String END_LINK_ELEMENT = "</link>";

    /**
     * Atom link element.
     */
    private static final String ATOM_LINK_ELEMENT = "<atom:link href=\"" + ATOM_LINK_VARIABLE
            + "\" rel=\"self\" type=\"application/rss+xml\" />";

    /**
     * Start description element.
     */
    private static final String START_DESCRIPTION_ELEMENT = "<description>";

    /**
     * End description element.
     */
    private static final String END_DESCRIPTION_ELEMENT = "</description>";

    /**
     * Start generator element.
     */
    private static final String START_GENERATOR_ELEMENT = "<generator>";

    /**
     * End generator element.
     */
    private static final String END_GENERATOR_ELEMENT = "</generator>";

    /**
     * Start language element.
     */
    private static final String START_LANGUAGE_ELEMENT = "<language>";

    /**
     * End language element.
     */
    private static final String END_LANGUAGE_ELEMENT = "</language>";

    /**
     * Start last build date element.
     */
    private static final String START_LAST_BUILD_DATE_ELEMENT = "<lastBuildDate>";

    /**
     * End last build date element.
     */
    private static final String END_LAST_BUILD_DATE_ELEMENT = "</lastBuildDate>";

    /**
     * Title.
     */
    private String title;

    /**
     * Link.
     */
    private String link;

    /**
     * Atom link.
     */
    private String atomLink;

    /**
     * Description.
     */
    private String description;

    /**
     * Generator.
     */
    private String generator;

    /**
     * Last build date.
     */
    private Date lastBuildDate;

    /**
     * Language.
     */
    private String language;

    /**
     * Items.
     */
    private List<Item> items = new ArrayList<>();

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
     * Gets the atom link.
     *
     * @return atom link
     */
    public String getAtomLink() {
        return atomLink;
    }

    /**
     * Sets the atom link with the specified atom link.
     *
     * @param atomLink the specified atom link
     */
    public void setAtomLink(final String atomLink) {
        this.atomLink = atomLink;
    }

    /**
     * Gets the last build date.
     *
     * @return last build date
     */
    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    /**
     * Sets the last build date with the specified last build date.
     *
     * @param lastBuildDate the specified last build date
     */
    public void setLastBuildDate(final Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    /**
     * Gets generator.
     *
     * @return generator
     */
    public String getGenerator() {
        return generator;
    }

    /**
     * Sets the generator with the specified generator.
     *
     * @param generator the specified generator
     */
    public void setGenerator(final String generator) {
        this.generator = generator;
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
     * Adds the specified item.
     *
     * @param item the specified item
     */
    public void addItem(final Item item) {
        items.add(item);
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
     * Gets the language.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language with the specified language.
     *
     * @param language the specified language
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(START);

        stringBuilder.append(START_TITLE_ELEMENT);
        stringBuilder.append(title);
        stringBuilder.append(END_TITLE_ELEMENT);

        stringBuilder.append(START_LINK_ELEMENT);
        stringBuilder.append(link);
        stringBuilder.append(END_LINK_ELEMENT);

        stringBuilder.append(ATOM_LINK_ELEMENT.replace(ATOM_LINK_VARIABLE, atomLink));

        stringBuilder.append(START_DESCRIPTION_ELEMENT);
        stringBuilder.append(description);
        stringBuilder.append(END_DESCRIPTION_ELEMENT);

        stringBuilder.append(START_GENERATOR_ELEMENT);
        stringBuilder.append(generator);
        stringBuilder.append(END_GENERATOR_ELEMENT);

        stringBuilder.append(START_LAST_BUILD_DATE_ELEMENT);
        stringBuilder.append(DateFormatUtils.SMTP_DATETIME_FORMAT.format(lastBuildDate));
        stringBuilder.append(END_LAST_BUILD_DATE_ELEMENT);

        stringBuilder.append(START_LANGUAGE_ELEMENT);
        stringBuilder.append(language);
        stringBuilder.append(END_LANGUAGE_ELEMENT);

        for (final Item item : items) {
            stringBuilder.append(item.toString());
        }

        stringBuilder.append(END);

        return format(stringBuilder.toString());
    }
}
