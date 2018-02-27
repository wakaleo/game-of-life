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
package org.b3log.solo.event.rhythm;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.urlfetch.HTTPRequest;
import org.b3log.latke.urlfetch.URLFetchService;
import org.b3log.latke.urlfetch.URLFetchServiceFactory;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.PreferenceQueryService;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * This listener is responsible for updating article to B3log Rhythm.
 * <p>
 * The B3log Rhythm article update interface: http://rhythm.b3log.org/article (PUT).
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.1, Oct 24, 2017
 * @since 0.6.0
 */
public final class ArticleUpdater extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleUpdater.class);

    /**
     * URL of updating article to Rhythm.
     */
    private static final URL UPDATE_ARTICLE_URL;

    static {
        try {
            UPDATE_ARTICLE_URL = new URL(SoloServletListener.B3LOG_RHYTHM_SERVE_PATH + "/article");
        } catch (final MalformedURLException e) {
            LOGGER.log(Level.ERROR, "Creates remote service address[rhythm update article] error!");
            throw new IllegalStateException(e);
        }
    }

    /**
     * URL fetch service.
     */
    private final URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    @Override
    public void action(final Event<JSONObject> event) throws EventException {
        final JSONObject data = event.getData();

        LOGGER.log(Level.DEBUG, "Processing an event[type={0}, data={1}] in listener[className={2}]",
                event.getType(), data, ArticleUpdater.class.getName());
        try {
            final JSONObject originalArticle = data.getJSONObject(Article.ARTICLE);
            if (!originalArticle.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                LOGGER.log(Level.DEBUG, "Ignores post article[title={0}] to Rhythm", originalArticle.getString(Article.ARTICLE_TITLE));

                return;
            }

            final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
            final PreferenceQueryService preferenceQueryService = beanManager.getReference(PreferenceQueryService.class);

            final JSONObject preference = preferenceQueryService.getPreference();
            if (null == preference) {
                throw new EventException("Not found preference");
            }

            if (!Strings.isEmptyOrNull(originalArticle.optString(Article.ARTICLE_VIEW_PWD))) {
                return;
            }

            if (Latkes.getServePath().contains("localhost")) {
                LOGGER.log(Level.TRACE, "Solo runs on local server, so should not send this article[id={0}, title={1}] to Rhythm",
                        originalArticle.getString(Keys.OBJECT_ID), originalArticle.getString(Article.ARTICLE_TITLE));
                return;
            }

            final HTTPRequest httpRequest = new HTTPRequest();

            httpRequest.setURL(UPDATE_ARTICLE_URL);
            httpRequest.setRequestMethod(HTTPRequestMethod.PUT);
            final JSONObject requestJSONObject = new JSONObject();
            final JSONObject article = new JSONObject();

            article.put(Keys.OBJECT_ID, originalArticle.getString(Keys.OBJECT_ID));
            article.put(Article.ARTICLE_TITLE, originalArticle.getString(Article.ARTICLE_TITLE));
            article.put(Article.ARTICLE_PERMALINK, originalArticle.getString(Article.ARTICLE_PERMALINK));
            article.put(Article.ARTICLE_TAGS_REF, originalArticle.getString(Article.ARTICLE_TAGS_REF));
            article.put(Article.ARTICLE_AUTHOR_EMAIL, originalArticle.getString(Article.ARTICLE_AUTHOR_EMAIL));
            article.put(Article.ARTICLE_CONTENT, originalArticle.getString(Article.ARTICLE_CONTENT));
            article.put(Article.ARTICLE_CREATE_DATE, ((Date) originalArticle.get(Article.ARTICLE_CREATE_DATE)).getTime());
            article.put(Common.POST_TO_COMMUNITY, originalArticle.getBoolean(Common.POST_TO_COMMUNITY));

            // Removes this property avoid to persist
            originalArticle.remove(Common.POST_TO_COMMUNITY);

            requestJSONObject.put(Article.ARTICLE, article);
            requestJSONObject.put(Common.BLOG_VERSION, SoloServletListener.VERSION);
            requestJSONObject.put(Common.BLOG, "Solo");
            requestJSONObject.put(Option.ID_C_BLOG_TITLE, preference.getString(Option.ID_C_BLOG_TITLE));
            requestJSONObject.put("blogHost", Latkes.getServePath());
            requestJSONObject.put("userB3Key", preference.optString(Option.ID_C_KEY_OF_SOLO));
            requestJSONObject.put("clientAdminEmail", preference.optString(Option.ID_C_ADMIN_EMAIL));
            requestJSONObject.put("clientRuntimeEnv", "LOCAL");

            httpRequest.setPayload(requestJSONObject.toString().getBytes("UTF-8"));

            urlFetchService.fetchAsync(httpRequest);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Sends an article to Rhythm error: {0}", e.getMessage());
        }

        LOGGER.log(Level.DEBUG, "Sent an article to Rhythm");
    }

    /**
     * Gets the event type {@linkplain EventTypes#UPDATE_ARTICLE}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.UPDATE_ARTICLE;
    }
}
