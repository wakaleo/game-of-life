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
package org.b3log.solo.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.mail.MailService;
import org.b3log.latke.mail.MailServiceFactory;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.urlfetch.HTTPRequest;
import org.b3log.latke.urlfetch.HTTPResponse;
import org.b3log.latke.urlfetch.URLFetchService;
import org.b3log.latke.urlfetch.URLFetchServiceFactory;
import org.b3log.latke.util.Ids;
import org.b3log.latke.util.Strings;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.*;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.PageRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.util.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * Comment management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.3.3.0, Aug 31, 2017
 * @since 0.3.5
 */
@Service
public class CommentMgmtService {

    /**
     * Comment mail HTML body.
     */
    public static final String COMMENT_MAIL_HTML_BODY = "<p>{articleOrPage} [<a href=\"" + "{articleOrPageURL}\">" + "{title}</a>]"
            + " received a new comment:</p>" + "{commenter}: <span><a href=\"{commentSharpURL}\">" + "{commentContent}</a></span>";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentMgmtService.class);

    /**
     * Default user thumbnail.
     */
    private static final String DEFAULT_USER_THUMBNAIL = "default-user-thumbnail.png";

    /**
     * Minimum length of comment name.
     */
    private static final int MIN_COMMENT_NAME_LENGTH = 2;

    /**
     * Maximum length of comment name.
     */
    private static final int MAX_COMMENT_NAME_LENGTH = 20;

    /**
     * Minimum length of comment content.
     */
    private static final int MIN_COMMENT_CONTENT_LENGTH = 2;

    /**
     * Maximum length of comment content.
     */
    private static final int MAX_COMMENT_CONTENT_LENGTH = 500;

    /**
     * Event manager.
     */
    @Inject
    private static EventManager eventManager;

    /**
     * URL fetch service.
     */
    private static URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Mail service.
     */
    private MailService mailService = MailServiceFactory.getMailService();

    /**
     * Sends a notification mail to administrator for notifying the specified article or page received the specified
     * comment and original comment.
     *
     * @param articleOrPage   the specified article or page
     * @param comment         the specified comment
     * @param originalComment original comment, if not exists, set it as {@code null}
     * @param preference      the specified preference
     * @throws IOException   io exception
     * @throws JSONException json exception
     */
    public void sendNotificationMail(final JSONObject articleOrPage,
                                     final JSONObject comment,
                                     final JSONObject originalComment,
                                     final JSONObject preference) throws IOException, JSONException {
        if (!Mails.isConfigured()) {
            return;
        }

        final String commentEmail = comment.getString(Comment.COMMENT_EMAIL);
        final String commentId = comment.getString(Keys.OBJECT_ID);
        final String commentContent = comment.getString(Comment.COMMENT_CONTENT);

        final String adminEmail = preference.getString(Option.ID_C_ADMIN_EMAIL);

        if (adminEmail.equalsIgnoreCase(commentEmail)) {
            LOGGER.log(Level.DEBUG, "Do not send comment notification mail to admin itself[{0}]", adminEmail);

            return;
        }

        if (Latkes.getServePath().contains("localhost")) {
            LOGGER.log(Level.INFO, "Solo runs on local server, so should not send mail");

            return;
        }

        if (null != originalComment && comment.has(Comment.COMMENT_ORIGINAL_COMMENT_ID)) {
            final String originalEmail = originalComment.getString(Comment.COMMENT_EMAIL);
            if (originalEmail.equalsIgnoreCase(adminEmail)) {
                LOGGER.log(Level.DEBUG, "Do not send comment notification mail to admin while the specified comment[{0}] is an reply",
                        commentId);
                return;
            }
        }

        final String blogTitle = preference.getString(Option.ID_C_BLOG_TITLE);
        boolean isArticle = true;
        String title = articleOrPage.optString(Article.ARTICLE_TITLE);
        if (Strings.isEmptyOrNull(title)) {
            title = articleOrPage.getString(Page.PAGE_TITLE);
            isArticle = false;
        }

        final String commentSharpURL = comment.getString(Comment.COMMENT_SHARP_URL);
        final MailService.Message message = new MailService.Message();
        message.setFrom(adminEmail);
        message.addRecipient(adminEmail);
        String mailSubject;
        String articleOrPageURL;
        String mailBody;

        if (isArticle) {
            mailSubject = blogTitle + ": New comment on article [" + title + "]";
            articleOrPageURL = Latkes.getServePath() + articleOrPage.getString(Article.ARTICLE_PERMALINK);
            mailBody = COMMENT_MAIL_HTML_BODY.replace("{articleOrPage}", "Article");
        } else {
            mailSubject = blogTitle + ": New comment on page [" + title + "]";
            articleOrPageURL = Latkes.getServePath() + articleOrPage.getString(Page.PAGE_PERMALINK);
            mailBody = COMMENT_MAIL_HTML_BODY.replace("{articleOrPage}", "Page");
        }

        message.setSubject(mailSubject);
        final String commentName = comment.getString(Comment.COMMENT_NAME);
        final String commentURL = comment.getString(Comment.COMMENT_URL);
        String commenter;

        if (!"http://".equals(commentURL)) {
            commenter = "<a target=\"_blank\" " + "href=\"" + commentURL + "\">" + commentName + "</a>";
        } else {
            commenter = commentName;
        }

        mailBody = mailBody.replace("{articleOrPageURL}", articleOrPageURL).replace("{title}", title).replace("{commentContent}", commentContent).replace("{commentSharpURL}", Latkes.getServePath() + commentSharpURL).replace(
                "{commenter}", commenter);
        message.setHtmlBody(mailBody);

        LOGGER.log(Level.DEBUG, "Sending a mail[mailSubject={0}, mailBody=[{1}] to admin[email={2}]",
                mailSubject, mailBody, adminEmail);

        mailService.send(message);
    }

    /**
     * Checks the specified comment adding request.
     * <p>
     * XSS process (name, content) in this method.
     * </p>
     *
     * @param requestJSONObject the specified comment adding request, for example,
     *                          {
     *                          "type": "", // "article"/"page"
     *                          "oId": "",
     *                          "commentName": "",
     *                          "commentEmail": "",
     *                          "commentURL": "",
     *                          "commentContent": "",
     *                          }
     * @return check result, for example,      <pre>
     * {
     *     "sc": boolean,
     *     "msg": "" // Exists if "sc" equals to false
     * }
     * </pre>
     */
    public JSONObject checkAddCommentRequest(final JSONObject requestJSONObject) {
        final JSONObject ret = new JSONObject();

        try {
            ret.put(Keys.STATUS_CODE, false);
            final JSONObject preference = preferenceQueryService.getPreference();

            if (null == preference || !preference.optBoolean(Option.ID_C_COMMENTABLE)) {
                ret.put(Keys.MSG, langPropsService.get("notAllowCommentLabel"));

                return ret;
            }

            final String id = requestJSONObject.optString(Keys.OBJECT_ID);
            final String type = requestJSONObject.optString(Common.TYPE);

            if (Article.ARTICLE.equals(type)) {
                final JSONObject article = articleRepository.get(id);

                if (null == article || !article.optBoolean(Article.ARTICLE_COMMENTABLE)) {
                    ret.put(Keys.MSG, langPropsService.get("notAllowCommentLabel"));

                    return ret;
                }
            } else {
                final JSONObject page = pageRepository.get(id);

                if (null == page || !page.optBoolean(Page.PAGE_COMMENTABLE)) {
                    ret.put(Keys.MSG, langPropsService.get("notAllowCommentLabel"));

                    return ret;
                }
            }

            String commentName = requestJSONObject.getString(Comment.COMMENT_NAME);

            if (MAX_COMMENT_NAME_LENGTH < commentName.length() || MIN_COMMENT_NAME_LENGTH > commentName.length()) {
                LOGGER.log(Level.WARN, "Comment name is too long[{0}]", commentName);
                ret.put(Keys.MSG, langPropsService.get("nameTooLongLabel"));

                return ret;
            }

            final String commentEmail = requestJSONObject.getString(Comment.COMMENT_EMAIL).trim().toLowerCase();

            if (!Strings.isEmail(commentEmail)) {
                LOGGER.log(Level.WARN, "Comment email is invalid[{0}]", commentEmail);
                ret.put(Keys.MSG, langPropsService.get("mailInvalidLabel"));

                return ret;
            }

            final String commentURL = requestJSONObject.optString(Comment.COMMENT_URL);

            if (!Strings.isURL(commentURL) || StringUtils.contains(commentURL, "<")) {
                LOGGER.log(Level.WARN, "Comment URL is invalid[{0}]", commentURL);
                ret.put(Keys.MSG, langPropsService.get("urlInvalidLabel"));

                return ret;
            }

            String commentContent = requestJSONObject.optString(Comment.COMMENT_CONTENT);

            if (MAX_COMMENT_CONTENT_LENGTH < commentContent.length() || MIN_COMMENT_CONTENT_LENGTH > commentContent.length()) {
                LOGGER.log(Level.WARN, "Comment conent length is invalid[{0}]", commentContent.length());
                ret.put(Keys.MSG, langPropsService.get("commentContentCannotEmptyLabel"));

                return ret;
            }

            ret.put(Keys.STATUS_CODE, true);

            // name XSS process
            commentName = Jsoup.clean(commentName, Whitelist.none());
            requestJSONObject.put(Comment.COMMENT_NAME, commentName);

            commentContent = Emotions.toAliases(commentContent);
            requestJSONObject.put(Comment.COMMENT_CONTENT, commentContent);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.WARN, "Checks add comment request[" + requestJSONObject.toString() + "] failed", e);

            ret.put(Keys.STATUS_CODE, false);
            ret.put(Keys.MSG, langPropsService.get("addFailLabel"));

            return ret;
        }
    }

    /**
     * Adds page comment with the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "oId": "", // page id
     *                          "commentName": "",
     *                          "commentEmail": "",
     *                          "commentURL": "", // optional
     *                          "commentContent": "",
     *                          "commentOriginalCommentId": "" // optional
     *                          }
     * @return add result, for example,      <pre>
     * {
     *     "oId": "", // generated comment id
     *     "commentDate": "", // format: yyyy-MM-dd HH:mm:ss
     *     "commentOriginalCommentName": "" // optional, corresponding to argument "commentOriginalCommentId"
     *     "commentThumbnailURL": "",
     *     "commentSharpURL": "",
     *     "commentContent": "", // processed XSS HTML
     *     "commentName": "", // processed XSS
     *     "commentURL": "", // optional
     *     "isReply": boolean,
     *     "page": {},
     *     "commentOriginalCommentId": "" // optional
     *     "commentable": boolean,
     *     "permalink": "" // page.pagePermalink
     * }
     * </pre>
     * @throws ServiceException service exception
     */
    public JSONObject addPageComment(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();
        ret.put(Common.IS_REPLY, false);

        final Transaction transaction = commentRepository.beginTransaction();

        try {
            final String pageId = requestJSONObject.getString(Keys.OBJECT_ID);
            final JSONObject page = pageRepository.get(pageId);
            ret.put(Page.PAGE, page);
            final String commentName = requestJSONObject.getString(Comment.COMMENT_NAME);
            final String commentEmail = requestJSONObject.getString(Comment.COMMENT_EMAIL).trim().toLowerCase();
            final String commentURL = requestJSONObject.optString(Comment.COMMENT_URL);
            final String commentContent = requestJSONObject.getString(Comment.COMMENT_CONTENT);

            final String originalCommentId = requestJSONObject.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID);
            ret.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, originalCommentId);
            // Step 1: Add comment
            final JSONObject comment = new JSONObject();

            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");

            JSONObject originalComment = null;

            comment.put(Comment.COMMENT_NAME, commentName);
            comment.put(Comment.COMMENT_EMAIL, commentEmail);
            comment.put(Comment.COMMENT_URL, commentURL);
            comment.put(Comment.COMMENT_CONTENT, commentContent);
            final JSONObject preference = preferenceQueryService.getPreference();
            final Date date = new Date();

            comment.put(Comment.COMMENT_DATE, date);
            ret.put(Comment.COMMENT_DATE, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
            ret.put("commentDate2", date);

            ret.put(Common.COMMENTABLE, preference.getBoolean(Option.ID_C_COMMENTABLE) && page.getBoolean(Page.PAGE_COMMENTABLE));
            ret.put(Common.PERMALINK, page.getString(Page.PAGE_PERMALINK));

            if (!Strings.isEmptyOrNull(originalCommentId)) {
                originalComment = commentRepository.get(originalCommentId);
                if (null != originalComment) {
                    comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, originalCommentId);
                    final String originalCommentName = originalComment.getString(Comment.COMMENT_NAME);

                    comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, originalCommentName);
                    ret.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, originalCommentName);

                    ret.put(Common.IS_REPLY, true);
                } else {
                    LOGGER.log(Level.WARN, "Not found orginal comment[id={0}] of reply[name={1}, content={2}]", originalCommentId,
                            commentName, commentContent);
                }
            }
            setCommentThumbnailURL(comment);
            ret.put(Comment.COMMENT_THUMBNAIL_URL, comment.getString(Comment.COMMENT_THUMBNAIL_URL));
            // Sets comment on page....
            comment.put(Comment.COMMENT_ON_ID, pageId);
            comment.put(Comment.COMMENT_ON_TYPE, Page.PAGE);
            final String commentId = Ids.genTimeMillisId();

            ret.put(Keys.OBJECT_ID, commentId);
            // Save comment sharp URL
            final String commentSharpURL = Comments.getCommentSharpURLForPage(page, commentId);

            ret.put(Comment.COMMENT_NAME, commentName);
            ret.put(Comment.COMMENT_CONTENT, commentContent);
            ret.put(Comment.COMMENT_URL, commentURL);

            ret.put(Comment.COMMENT_SHARP_URL, commentSharpURL);
            comment.put(Comment.COMMENT_SHARP_URL, commentSharpURL);
            comment.put(Keys.OBJECT_ID, commentId);
            commentRepository.add(comment);
            // Step 2: Update page comment count
            incPageCommentCount(pageId);
            // Step 3: Update blog statistic comment count
            statisticMgmtService.incBlogCommentCount();
            statisticMgmtService.incPublishedBlogCommentCount();
            // Step 4: Send an email to admin
            try {
                sendNotificationMail(page, comment, originalComment, preference);
            } catch (final Exception e) {
                LOGGER.log(Level.WARN, "Send mail failed", e);
            }
            // Step 5: Fire add comment event
            final JSONObject eventData = new JSONObject();

            eventData.put(Comment.COMMENT, comment);
            eventData.put(Page.PAGE, page);
            eventManager.fireEventSynchronously(new Event<JSONObject>(EventTypes.ADD_COMMENT_TO_PAGE, eventData));

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e);
        }

        return ret;
    }

    /**
     * Adds an article comment with the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "oId": "", // article id
     *                          "commentName": "",
     *                          "commentEmail": "",
     *                          "commentURL": "", // optional
     *                          "commentContent": "",
     *                          "commentOriginalCommentId": "" // optional
     *                          }
     * @return add result, for example,      <pre>
     * {
     *     "oId": "", // generated comment id
     *     "commentDate": "", // format: yyyy-MM-dd HH:mm:ss
     *     "commentOriginalCommentName": "" // optional, corresponding to argument "commentOriginalCommentId"
     *     "commentThumbnailURL": "",
     *     "commentSharpURL": "",
     *     "commentContent": "", // processed XSS HTML
     *     "commentName": "", // processed XSS
     *     "commentURL": "", // optional
     *     "isReply": boolean,
     *     "article": {},
     *     "commentOriginalCommentId": "", // optional
     *     "commentable": boolean,
     *     "permalink": "" // article.articlePermalink
     * }
     * </pre>
     * @throws ServiceException service exception
     */
    public JSONObject addArticleComment(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();
        ret.put(Common.IS_REPLY, false);

        final Transaction transaction = commentRepository.beginTransaction();

        try {
            final String articleId = requestJSONObject.getString(Keys.OBJECT_ID);
            final JSONObject article = articleRepository.get(articleId);
            ret.put(Article.ARTICLE, article);
            final String commentName = requestJSONObject.getString(Comment.COMMENT_NAME);
            final String commentEmail = requestJSONObject.getString(Comment.COMMENT_EMAIL).trim().toLowerCase();
            final String commentURL = requestJSONObject.optString(Comment.COMMENT_URL);
            final String commentContent = requestJSONObject.getString(Comment.COMMENT_CONTENT);

            final String originalCommentId = requestJSONObject.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID);
            ret.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, originalCommentId);
            // Step 1: Add comment
            final JSONObject comment = new JSONObject();

            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");

            JSONObject originalComment = null;

            comment.put(Comment.COMMENT_NAME, commentName);
            comment.put(Comment.COMMENT_EMAIL, commentEmail);
            comment.put(Comment.COMMENT_URL, commentURL);
            comment.put(Comment.COMMENT_CONTENT, commentContent);
            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, requestJSONObject.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID));
            comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, requestJSONObject.optString(Comment.COMMENT_ORIGINAL_COMMENT_NAME));
            final JSONObject preference = preferenceQueryService.getPreference();
            final Date date = new Date();

            comment.put(Comment.COMMENT_DATE, date);
            ret.put(Comment.COMMENT_DATE, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
            ret.put("commentDate2", date);

            ret.put(Common.COMMENTABLE, preference.getBoolean(Option.ID_C_COMMENTABLE) && article.getBoolean(Article.ARTICLE_COMMENTABLE));
            ret.put(Common.PERMALINK, article.getString(Article.ARTICLE_PERMALINK));

            ret.put(Comment.COMMENT_NAME, commentName);
            String cmtContent = Emotions.convert(commentContent);
            cmtContent = Markdowns.toHTML(cmtContent);
            cmtContent = Jsoup.clean(cmtContent, Whitelist.relaxed());
            ret.put(Comment.COMMENT_CONTENT, cmtContent);
            ret.put(Comment.COMMENT_URL, commentURL);

            if (!Strings.isEmptyOrNull(originalCommentId)) {
                originalComment = commentRepository.get(originalCommentId);
                if (null != originalComment) {
                    comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, originalCommentId);
                    final String originalCommentName = originalComment.getString(Comment.COMMENT_NAME);

                    comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, originalCommentName);
                    ret.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, originalCommentName);

                    ret.put(Common.IS_REPLY, true);
                } else {
                    LOGGER.log(Level.WARN, "Not found orginal comment[id={0}] of reply[name={1}, content={2}]",
                            originalCommentId, commentName, commentContent);
                }
            }
            setCommentThumbnailURL(comment);
            ret.put(Comment.COMMENT_THUMBNAIL_URL, comment.getString(Comment.COMMENT_THUMBNAIL_URL));
            // Sets comment on article....
            comment.put(Comment.COMMENT_ON_ID, articleId);
            comment.put(Comment.COMMENT_ON_TYPE, Article.ARTICLE);
            final String commentId = Ids.genTimeMillisId();

            comment.put(Keys.OBJECT_ID, commentId);
            ret.put(Keys.OBJECT_ID, commentId);
            final String commentSharpURL = Comments.getCommentSharpURLForArticle(article, commentId);

            comment.put(Comment.COMMENT_SHARP_URL, commentSharpURL);
            ret.put(Comment.COMMENT_SHARP_URL, commentSharpURL);

            commentRepository.add(comment);
            // Step 2: Update article comment count
            articleMgmtService.incArticleCommentCount(articleId);
            // Step 3: Update blog statistic comment count
            statisticMgmtService.incBlogCommentCount();
            statisticMgmtService.incPublishedBlogCommentCount();
            // Step 4: Send an email to admin
            try {
                sendNotificationMail(article, comment, originalComment, preference);
            } catch (final Exception e) {
                LOGGER.log(Level.WARN, "Send mail failed", e);
            }
            // Step 5: Fire add comment event
            final JSONObject eventData = new JSONObject();

            eventData.put(Comment.COMMENT, comment);
            eventData.put(Article.ARTICLE, article);
            eventManager.fireEventSynchronously(new Event<JSONObject>(EventTypes.ADD_COMMENT_TO_ARTICLE, eventData));

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e);
        }

        return ret;
    }

    /**
     * Removes a comment of a page with the specified comment id.
     *
     * @param commentId the given comment id
     * @throws ServiceException service exception
     */
    public void removePageComment(final String commentId) throws ServiceException {
        final Transaction transaction = commentRepository.beginTransaction();

        try {
            final JSONObject comment = commentRepository.get(commentId);
            final String pageId = comment.getString(Comment.COMMENT_ON_ID);

            // Step 1: Remove comment
            commentRepository.remove(commentId);
            // Step 2: Update page comment count
            decPageCommentCount(pageId);
            // Step 3: Update blog statistic comment count
            statisticMgmtService.decBlogCommentCount();
            statisticMgmtService.decPublishedBlogCommentCount();

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a comment of a page failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Removes a comment of an article with the specified comment id.
     *
     * @param commentId the given comment id
     * @throws ServiceException service exception
     */
    public void removeArticleComment(final String commentId)
            throws ServiceException {
        final Transaction transaction = commentRepository.beginTransaction();

        try {
            final JSONObject comment = commentRepository.get(commentId);
            final String articleId = comment.getString(Comment.COMMENT_ON_ID);

            // Step 1: Remove comment
            commentRepository.remove(commentId);
            // Step 2: Update article comment count
            decArticleCommentCount(articleId);
            // Step 3: Update blog statistic comment count
            statisticMgmtService.decBlogCommentCount();
            statisticMgmtService.decPublishedBlogCommentCount();

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a comment of an article failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Page comment count +1 for an page specified by the given page id.
     *
     * @param pageId the given page id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    public void incPageCommentCount(final String pageId)
            throws JSONException, RepositoryException {
        final JSONObject page = pageRepository.get(pageId);
        final JSONObject newPage = new JSONObject(page, JSONObject.getNames(page));
        final int commentCnt = page.getInt(Page.PAGE_COMMENT_COUNT);

        newPage.put(Page.PAGE_COMMENT_COUNT, commentCnt + 1);
        pageRepository.update(pageId, newPage);
    }

    /**
     * Article comment count -1 for an article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void decArticleCommentCount(final String articleId)
            throws JSONException, RepositoryException {
        final JSONObject article = articleRepository.get(articleId);
        final JSONObject newArticle = new JSONObject(article, JSONObject.getNames(article));
        final int commentCnt = article.getInt(Article.ARTICLE_COMMENT_COUNT);

        newArticle.put(Article.ARTICLE_COMMENT_COUNT, commentCnt - 1);

        articleRepository.update(articleId, newArticle);
    }

    /**
     * Page comment count -1 for an page specified by the given page id.
     *
     * @param pageId the given page id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void decPageCommentCount(final String pageId)
            throws JSONException, RepositoryException {
        final JSONObject page = pageRepository.get(pageId);
        final JSONObject newPage = new JSONObject(page, JSONObject.getNames(page));
        final int commentCnt = page.getInt(Page.PAGE_COMMENT_COUNT);

        newPage.put(Page.PAGE_COMMENT_COUNT, commentCnt - 1);
        pageRepository.update(pageId, newPage);
    }

    /**
     * Sets commenter thumbnail URL for the specified comment.
     * <p>
     * Try to set thumbnail URL using:
     * <ol>
     * <li>User avatar</li>
     * <li>Gravatar service</li>
     * <ol>
     * </p>
     *
     * @param comment the specified comment
     * @throws Exception exception
     */
    public void setCommentThumbnailURL(final JSONObject comment) throws Exception {
        final String commentEmail = comment.getString(Comment.COMMENT_EMAIL);

        // 1. user avatar
        final JSONObject user = userRepository.getByEmail(commentEmail);
        if (null != user) {
            final String avatar = user.optString(UserExt.USER_AVATAR);
            if (!Strings.isEmptyOrNull(avatar)) {
                comment.put(Comment.COMMENT_THUMBNAIL_URL, avatar);

                return;
            }
        }

        // 2. Gravatar
        String thumbnailURL = Thumbnails.getGravatarURL(commentEmail.toLowerCase(), "128");
        final URL gravatarURL = new URL(thumbnailURL);

        int statusCode = HttpServletResponse.SC_OK;

        try {
            final HTTPRequest request = new HTTPRequest();

            request.setURL(gravatarURL);
            final HTTPResponse response = urlFetchService.fetch(request);

            statusCode = response.getResponseCode();
        } catch (final IOException e) {
            LOGGER.log(Level.DEBUG, "Can not fetch thumbnail from Gravatar [commentEmail={0}]", commentEmail);
        } finally {
            if (HttpServletResponse.SC_OK != statusCode) {
                thumbnailURL = Latkes.getStaticServePath() + "/images/" + DEFAULT_USER_THUMBNAIL;
            }
        }

        comment.put(Comment.COMMENT_THUMBNAIL_URL, thumbnailURL);
    }

    /**
     * Sets the article repository with the specified article repository.
     *
     * @param articleRepository the specified article repository
     */
    public void setArticleRepository(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Sets the article management service with the specified article management service.
     *
     * @param articleMgmtService the specified article management service
     */
    public void setArticleMgmtService(final ArticleMgmtService articleMgmtService) {
        this.articleMgmtService = articleMgmtService;
    }

    /**
     * Set the page repository with the specified page repository.
     *
     * @param pageRepository the specified page repository
     */
    public void setPageRepository(final PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    /**
     * Sets the preference query service with the specified preference query service.
     *
     * @param preferenceQueryService the specified preference query service
     */
    public void setPreferenceQueryService(final PreferenceQueryService preferenceQueryService) {
        this.preferenceQueryService = preferenceQueryService;
    }

    /**
     * Sets the statistic management service with the specified statistic management service.
     *
     * @param statisticMgmtService the specified statistic management service
     */
    public void setStatisticMgmtService(final StatisticMgmtService statisticMgmtService) {
        this.statisticMgmtService = statisticMgmtService;
    }

    /**
     * Sets the comment repository with the specified comment repository.
     *
     * @param commentRepository the specified comment repository
     */
    public void setCommentRepository(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Sets the language service with the specified language service.
     *
     * @param langPropsService the specified language service
     */
    public void setLangPropsService(final LangPropsService langPropsService) {
        this.langPropsService = langPropsService;
    }
}
