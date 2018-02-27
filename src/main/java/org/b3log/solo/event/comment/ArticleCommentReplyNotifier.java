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
package org.b3log.solo.event.comment;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.mail.MailService;
import org.b3log.latke.mail.MailService.Message;
import org.b3log.latke.mail.MailServiceFactory;
import org.b3log.latke.util.Strings;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.impl.CommentRepositoryImpl;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.util.Mails;
import org.json.JSONObject;

/**
 * This listener is responsible for processing article comment reply.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="http://www.wanglay.com">Lei Wang</a>
 * @version 1.2.2.8, Jul 20, 2017
 * @since 0.3.1
 */
public final class ArticleCommentReplyNotifier extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleCommentReplyNotifier.class);

    /**
     * Mail service.
     */
    private MailService mailService = MailServiceFactory.getMailService();

    @Override
    public void action(final Event<JSONObject> event) throws EventException {
        final JSONObject eventData = event.getData();
        final JSONObject comment = eventData.optJSONObject(Comment.COMMENT);
        final JSONObject article = eventData.optJSONObject(Article.ARTICLE);

        LOGGER.log(Level.DEBUG,
                "Processing an event[type={0}, data={1}] in listener[className={2}]",
                event.getType(), eventData, ArticleCommentReplyNotifier.class.getName());
        final String originalCommentId = comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID);

        if (Strings.isEmptyOrNull(originalCommentId)) {
            LOGGER.log(Level.DEBUG, "This comment[id={0}] is not a reply", comment.optString(Keys.OBJECT_ID));

            return;
        }

        if (Latkes.getServePath().contains("localhost")) {
            LOGGER.log(Level.INFO, "Solo runs on local server, so should not send mail");

            return;
        }

        if (!Mails.isConfigured()) {
            return;
        }

        final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
        final PreferenceQueryService preferenceQueryService = beanManager.getReference(PreferenceQueryService.class);

        final CommentRepository commentRepository = beanManager.getReference(CommentRepositoryImpl.class);

        try {
            final String commentEmail = comment.getString(Comment.COMMENT_EMAIL);
            final JSONObject originalComment = commentRepository.get(originalCommentId);

            final String originalCommentEmail = originalComment.getString(Comment.COMMENT_EMAIL);
            if (originalCommentEmail.equalsIgnoreCase(commentEmail)) {
                return;
            }

            if (!Strings.isEmail(originalCommentEmail)) {
                return;
            }

            final JSONObject preference = preferenceQueryService.getPreference();
            if (null == preference) {
                throw new EventException("Not found preference");
            }

            final String blogTitle = preference.getString(Option.ID_C_BLOG_TITLE);
            final String adminEmail = preference.getString(Option.ID_C_ADMIN_EMAIL);

            final String commentContent = comment.getString(Comment.COMMENT_CONTENT);
            final String commentSharpURL = comment.getString(Comment.COMMENT_SHARP_URL);
            final Message message = new Message();

            message.setFrom(adminEmail);
            message.addRecipient(originalCommentEmail);
            final JSONObject replyNotificationTemplate = preferenceQueryService.getReplyNotificationTemplate();

            final String articleTitle = article.getString(Article.ARTICLE_TITLE);
            final String articleLink = Latkes.getServePath() + article.getString(Article.ARTICLE_PERMALINK);
            final String commentName = comment.getString(Comment.COMMENT_NAME);
            final String commentURL = comment.getString(Comment.COMMENT_URL);
            String commenter;

            if (!"http://".equals(commentURL)) {
                commenter = "<a target=\"_blank\" " + "href=\"" + commentURL + "\">" + commentName + "</a>";
            } else {
                commenter = commentName;
            }
            final String mailSubject = replyNotificationTemplate.getString(
                    "subject").replace("${postLink}", articleLink)
                    .replace("${postTitle}", articleTitle)
                    .replace("${replier}", commenter)
                    .replace("${blogTitle}", blogTitle)
                    .replace("${replyURL}",
                            Latkes.getServePath() + commentSharpURL)
                    .replace("${replyContent}", commentContent);

            message.setSubject(mailSubject);
            final String mailBody = replyNotificationTemplate
                    .getString("body")
                    .replace("${postLink}", articleLink)
                    .replace("${postTitle}", articleTitle)
                    .replace("${replier}", commenter)
                    .replace("${blogTitle}", blogTitle)
                    .replace("${replyURL}",
                            Latkes.getServePath() + commentSharpURL)
                    .replace("${replyContent}", commentContent);

            message.setHtmlBody(mailBody);
            LOGGER.log(Level.DEBUG, "Sending a mail[mailSubject={0}, mailBody=[{1}] to [{2}]",
                    mailSubject, mailBody, originalCommentEmail);

            mailService.send(message);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            throw new EventException("Reply notifier error!");
        }
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
