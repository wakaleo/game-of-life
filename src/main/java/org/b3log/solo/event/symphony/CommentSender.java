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
package org.b3log.solo.event.symphony;


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
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.event.rhythm.ArticleSender;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.PreferenceQueryService;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * This listener is responsible for sending comment to B3log Symphony.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.0, Sep 6, 2017
 * @since 0.5.5
 */
public final class CommentSender extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentSender.class);

    /**
     * URL fetch service.
     */
    private final URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    /**
     * URL of adding comment to Symphony.
     */
    private static final URL ADD_COMMENT_URL;

    static {
        try {
            ADD_COMMENT_URL = new URL(SoloServletListener.B3LOG_SYMPHONY_SERVE_PATH + "/solo/comment");
        } catch (final MalformedURLException e) {
            LOGGER.log(Level.ERROR, "Creates remote service address[symphony add comment] error!");
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void action(final Event<JSONObject> event) throws EventException {
        final JSONObject data = event.getData();

        LOGGER.log(Level.DEBUG, "Processing an event[type={0}, data={1}] in listener[className={2}]",
                event.getType(), data, ArticleSender.class.getName());
        try {
            final JSONObject originalComment = data.getJSONObject(Comment.COMMENT);

            final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
            final PreferenceQueryService preferenceQueryService = beanManager.getReference(PreferenceQueryService.class);

            final JSONObject preference = preferenceQueryService.getPreference();
            if (null == preference) {
                throw new EventException("Not found preference");
            }

            if (Latkes.getServePath().contains("localhost")) {
                LOGGER.log(Level.TRACE, "Solo runs on local server, so should not send this comment[id={0}] to Symphony",
                        originalComment.getString(Keys.OBJECT_ID));
                return;
            }

            final HTTPRequest httpRequest = new HTTPRequest();

            httpRequest.setURL(ADD_COMMENT_URL);
            httpRequest.setRequestMethod(HTTPRequestMethod.POST);
            final JSONObject requestJSONObject = new JSONObject();
            final JSONObject comment = new JSONObject();

            comment.put("commentId", originalComment.optString(Keys.OBJECT_ID));
            comment.put("commentAuthorName", originalComment.getString(Comment.COMMENT_NAME));
            comment.put("commentAuthorEmail", originalComment.getString(Comment.COMMENT_EMAIL));
            comment.put(Comment.COMMENT_CONTENT, originalComment.getString(Comment.COMMENT_CONTENT));
            comment.put("articleId", originalComment.getString(Comment.COMMENT_ON_ID));

            requestJSONObject.put(Comment.COMMENT, comment);
            requestJSONObject.put("clientVersion", SoloServletListener.VERSION);
            requestJSONObject.put("clientRuntimeEnv", "LOCAL");
            requestJSONObject.put("clientName", "Solo");
            requestJSONObject.put("clientHost", Latkes.getServePath());
            requestJSONObject.put("clientAdminEmail", preference.optString(Option.ID_C_ADMIN_EMAIL));
            requestJSONObject.put("userB3Key", preference.optString(Option.ID_C_KEY_OF_SOLO));

            httpRequest.setPayload(requestJSONObject.toString().getBytes("UTF-8"));

            urlFetchService.fetchAsync(httpRequest);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Sends a comment to Symphony error: {0}", e.getMessage());
        }

        LOGGER.log(Level.DEBUG, "Sent a comment to Symphony");
    }

    /**
     * Gets the event type {@linkplain EventTypes#ADD_COMMENT_TO_ARTICLE}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.ADD_COMMENT_TO_ARTICLE;
    }
}
