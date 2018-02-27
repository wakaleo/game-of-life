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
package org.b3log.solo.model.sitemap;


import java.util.ArrayList;
import java.util.List;


/**
 * Sitemap.
 *
 * <p>
 * See <a href="http://www.sitemaps.org/protocol.php">Sitemap XML format</a> 
 * for more details.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Sep 22, 2011
 * @see URL
 * @since 0.3.1
 */
public final class Sitemap {

    /**
     * Start document.
     */
    private static final String START_DOCUMENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /**
     * Start URL set element.
     */
    private static final String START_URL_SET_ELEMENT = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";

    /**
     * End URL set element.
     */
    private static final String END_URL_SET_ELEMENT = "</urlset>";

    /**
     * URLs.
     */
    private List<URL> urls = new ArrayList<>();

    /**
     * Adds the specified url.
     * 
     * @param url the specified url
     */
    public void addURL(final URL url) {
        urls.add(url);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(START_DOCUMENT);
        stringBuilder.append(START_URL_SET_ELEMENT);

        for (final URL url : urls) {
            stringBuilder.append(url.toString());
        }

        stringBuilder.append(END_URL_SET_ELEMENT);

        return stringBuilder.toString();
    }
}
