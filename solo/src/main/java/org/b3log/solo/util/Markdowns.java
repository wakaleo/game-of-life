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
package org.b3log.solo.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Latkes;
import org.b3log.latke.cache.Cache;
import org.b3log.latke.cache.CacheFactory;
import org.b3log.latke.ioc.LatkeBeanManagerImpl;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.LangPropsServiceImpl;
import org.b3log.latke.util.Callstacks;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.latke.util.Strings;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.*;

/**
 * <a href="http://en.wikipedia.org/wiki/Markdown">Markdown</a> utilities.
 * <p>
 * Uses the <a href="https://github.com/chjj/marked">marked</a> as the processor, if not found this command, try
 * built-in <a href="https://github.com/vsch/flexmark-java">flexmark</a> instead.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.3.0.7, Jan 13, 2018
 * @since 0.4.5
 */
public final class Markdowns {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Markdowns.class);

    /**
     * Language service.
     */
    private static final LangPropsService LANG_PROPS_SERVICE = LatkeBeanManagerImpl.getInstance().getReference(LangPropsServiceImpl.class);

    /**
     * Markdown cache.
     */
    private static final Cache MD_CACHE = CacheFactory.getCache("markdown");

    /**
     * Markdown to HTML timeout.
     */
    private static final int MD_TIMEOUT = 2000;

    /**
     * Built-in MD engine options.
     */

    private static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL_OPTIONALS | Extensions.ALL_WITH_OPTIONALS
    );

    /**
     * Built-in MD engine parser.
     */
    private static final com.vladsch.flexmark.parser.Parser PARSER =
            com.vladsch.flexmark.parser.Parser.builder(OPTIONS).build();

    /**
     * Built-in MD engine HTML renderer.
     */
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    /**
     * Marked engine serve path.
     */
    private static final String MARKED_ENGINE_URL = "http://localhost:8250";

    /**
     * Whether marked is available.
     */
    public static boolean MARKED_AVAILABLE;

    static {
        MD_CACHE.setMaxCount(1024 * 10 * 4);

        try {
            final URL url = new URL(MARKED_ENGINE_URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            final OutputStream outputStream = conn.getOutputStream();
            IOUtils.write("Solo 大法好", outputStream, "UTF-8");
            IOUtils.closeQuietly(outputStream);

            final InputStream inputStream = conn.getInputStream();
            final String html = IOUtils.toString(inputStream, "UTF-8");
            IOUtils.closeQuietly(inputStream);

            conn.disconnect();

            MARKED_AVAILABLE = StringUtils.contains(html, "<p>Solo 大法好</p>");

            if (MARKED_AVAILABLE) {
                LOGGER.log(Level.DEBUG, "[marked] is available, uses it for markdown processing");
            } else {
                LOGGER.log(Level.DEBUG, "[marked] is not available, uses built-in [flexmark] for markdown processing");
            }
        } catch (final Exception e) {
            LOGGER.log(Level.INFO, "[marked] is not available, uses built-in [flexmark] for markdown processing. " +
                    "Please reads FAQ section in user guide (https://hacpai.com/article/1492881378588) for more details.");
        }
    }

    /**
     * Private constructor.
     */
    private Markdowns() {
    }

    /**
     * Converts the specified markdown text to HTML.
     *
     * @param markdownText the specified markdown text
     * @return converted HTML, returns an empty string "" if the specified markdown text is "" or {@code null}, returns
     * 'markdownErrorLabel' if exception
     */
    public static String toHTML(final String markdownText) {
        if (Strings.isEmptyOrNull(markdownText)) {
            return "";
        }

        final String cachedHTML = getHTML(markdownText);
        if (null != cachedHTML) {
            return cachedHTML;
        }

        final ExecutorService pool = Executors.newSingleThreadExecutor();
        final long[] threadId = new long[1];

        final Callable<String> call = () -> {
            threadId[0] = Thread.currentThread().getId();

            String html = LANG_PROPS_SERVICE.get("contentRenderFailedLabel");

            if (MARKED_AVAILABLE) {
                html = toHtmlByMarked(markdownText);
                if (!StringUtils.startsWith(html, "<p>")) {
                    html = "<p>" + html + "</p>";
                }
            } else {
                com.vladsch.flexmark.ast.Node document = PARSER.parse(markdownText);
                html = RENDERER.render(document);
                if (!StringUtils.startsWith(html, "<p>")) {
                    html = "<p>" + html + "</p>";
                }
            }

            final Document doc = Jsoup.parse(html);
            doc.select("a").forEach(a -> {
                final String src = a.attr("href");
                if (!StringUtils.startsWithIgnoreCase(src, Latkes.getServePath())) {
                    a.attr("target", "_blank");
                }
            });
            doc.outputSettings().prettyPrint(false);

            String ret = doc.select("body").html();
            ret = StringUtils.trim(ret);

            // cache it
            putHTML(markdownText, ret);

            return ret;
        };

        Stopwatchs.start("Md to HTML");
        try {
            final Future<String> future = pool.submit(call);

            return future.get(MD_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (final TimeoutException e) {
            LOGGER.log(Level.ERROR, "Markdown timeout [md=" + markdownText + "]");
            Callstacks.printCallstack(Level.ERROR, new String[]{"org.b3log"}, null);

            final Set<Thread> threads = Thread.getAllStackTraces().keySet();
            for (final Thread thread : threads) {
                if (thread.getId() == threadId[0]) {
                    thread.stop();

                    break;
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Markdown failed [md=" + markdownText + "]", e);
        } finally {
            pool.shutdownNow();

            Stopwatchs.end();
        }

        return LANG_PROPS_SERVICE.get("contentRenderFailedLabel");
    }

    private static String toHtmlByMarked(final String markdownText) throws Exception {
        final URL url = new URL(MARKED_ENGINE_URL);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);

        final OutputStream outputStream = conn.getOutputStream();
        IOUtils.write(markdownText, outputStream, "UTF-8");
        IOUtils.closeQuietly(outputStream);

        final InputStream inputStream = conn.getInputStream();
        final String html = IOUtils.toString(inputStream, "UTF-8");
        IOUtils.closeQuietly(inputStream);

        //conn.disconnect();

        return html;
    }

    /**
     * Gets HTML for the specified markdown text.
     *
     * @param markdownText the specified markdown text
     * @return HTML
     */
    private static String getHTML(final String markdownText) {
        final String hash = DigestUtils.md5Hex(markdownText);
        final JSONObject value = MD_CACHE.get(hash);
        if (null == value) {
            return null;
        }

        return value.optString("data");
    }

    /**
     * Puts the specified HTML into cache.
     *
     * @param markdownText the specified markdown text
     * @param html         the specified HTML
     */
    private static void putHTML(final String markdownText, final String html) {
        final String hash = DigestUtils.md5Hex(markdownText);
        final JSONObject value = new JSONObject();
        value.put("data", html);
        MD_CACHE.put(hash, value);
    }
}
