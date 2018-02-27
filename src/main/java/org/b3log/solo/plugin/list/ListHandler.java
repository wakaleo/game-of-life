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
package org.b3log.solo.plugin.list;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.Article;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * List (table of contents of an article) handler.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="http://www.annpeter.cn">Ann Peter</a>
 * @version 1.0.2.0, Feb 6, 2018
 * @since 0.6.7
 */
public class ListHandler extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ListHandler.class);

    @Override
    public String getEventType() {
        return EventTypes.BEFORE_RENDER_ARTICLE;
    }

    @Override
    public void action(final Event<JSONObject> event) throws EventException {
        final JSONObject data = event.getData();
        final JSONObject article = data.optJSONObject(Article.ARTICLE);
        String content = article.optString(Article.ARTICLE_CONTENT);
        if (StringUtils.containsIgnoreCase(content, "plugins/list/style.css")) {
            LOGGER.log(Level.WARN, "ToC hit twice, please report this \"ghosty\" issue to developer team: https://github.com/b3log/solo/issues/new");

            return;
        }


        final Document doc = Jsoup.parse(content, StringUtils.EMPTY, Parser.htmlParser());
        doc.outputSettings().prettyPrint(false);

        final StringBuilder listBuilder = new StringBuilder();
        listBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + Latkes.getStaticServePath() + "/plugins/list/style.css\" />");
        final Elements hs = doc.select("h1, h2, h3, h4, h5");
        listBuilder.append("<ul class='b3-solo-list'>");
        for (int i = 0; i < hs.size(); i++) {
            final Element element = hs.get(i);
            final String tagName = element.tagName().toLowerCase();
            final String text = element.text();
            final String id = "b3_solo_" + tagName + "_" + i;

            element.before("<span id='" + id + "'></span>");

            listBuilder.append("<li class='b3-solo-list-").append(tagName).append("'><a href='#").append(id).append("'>").append(text).
                    append("</a></li>");
        }
        listBuilder.append("</ul>");

        final Element body = doc.getElementsByTag("body").get(0);
        content = listBuilder.toString() + body.html();
        article.put(Article.ARTICLE_CONTENT, content);
    }
}
