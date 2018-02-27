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

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.latke.util.Ids;
import org.b3log.latke.util.Strings;
import org.b3log.solo.event.EventTypes;
import org.b3log.solo.model.*;
import org.b3log.solo.repository.*;
import org.b3log.solo.util.Comments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.b3log.solo.model.Article.*;

/**
 * Article management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.2.10, Oct 3, 2017
 * @since 0.3.5
 */
@Service
public class ArticleMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleMgmtService.class);

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

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
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Archive date repository.
     */
    @Inject
    private ArchiveDateRepository archiveDateRepository;

    /**
     * Archive date-Article repository.
     */
    @Inject
    private ArchiveDateArticleRepository archiveDateArticleRepository;

    /**
     * Tag-Article repository.
     */
    @Inject
    private TagArticleRepository tagArticleRepository;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Permalink query service.
     */
    @Inject
    private PermalinkQueryService permalinkQueryService;

    /**
     * Event manager.
     */
    @Inject
    private EventManager eventManager;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Statistic query service.
     */
    @Inject
    private StatisticQueryService statisticQueryService;

    /**
     * Tag management service.
     */
    @Inject
    private TagMgmtService tagMgmtService;

    /**
     * Determines whether the specified tag title exists in the specified tags.
     *
     * @param tagTitle the specified tag title
     * @param tags     the specified tags
     * @return {@code true} if it exists, {@code false} otherwise
     * @throws JSONException json exception
     */
    private static boolean tagExists(final String tagTitle, final List<JSONObject> tags) throws JSONException {
        for (final JSONObject tag : tags) {
            if (tag.getString(Tag.TAG_TITLE).equals(tagTitle)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Article comment count +1 for an article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    public void incArticleCommentCount(final String articleId) throws JSONException, RepositoryException {
        final JSONObject article = articleRepository.get(articleId);
        final JSONObject newArticle = new JSONObject(article, JSONObject.getNames(article));
        final int commentCnt = article.getInt(Article.ARTICLE_COMMENT_COUNT);

        newArticle.put(Article.ARTICLE_COMMENT_COUNT, commentCnt + 1);

        articleRepository.update(articleId, newArticle);
    }

    /**
     * Cancels publish an article by the specified article id.
     *
     * @param articleId the specified article id
     * @throws ServiceException service exception
     */
    public void cancelPublishArticle(final String articleId) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = articleRepository.get(articleId);

            article.put(ARTICLE_IS_PUBLISHED, false);
            tagMgmtService.decTagPublishedRefCount(articleId);
            decArchiveDatePublishedRefCount(articleId);

            articleRepository.update(articleId, article);
            statisticMgmtService.decPublishedBlogArticleCount();
            final int blogCmtCnt = statisticQueryService.getPublishedBlogCommentCount();
            final int articleCmtCnt = article.getInt(ARTICLE_COMMENT_COUNT);

            statisticMgmtService.setPublishedBlogCommentCount(blogCmtCnt - articleCmtCnt);

            final JSONObject author = userRepository.getByEmail(article.optString(Article.ARTICLE_AUTHOR_EMAIL));

            author.put(UserExt.USER_PUBLISHED_ARTICLE_COUNT, author.optInt(UserExt.USER_PUBLISHED_ARTICLE_COUNT) - 1);
            userRepository.update(author.optString(Keys.OBJECT_ID), author);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Cancels publish article failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Puts an article specified by the given article id to top or cancel top.
     *
     * @param articleId the given article id
     * @param top       the specified flag, {@code true} to top, {@code false} to
     *                  cancel top
     * @throws ServiceException service exception
     */
    public void topArticle(final String articleId, final boolean top) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject topArticle = articleRepository.get(articleId);

            topArticle.put(ARTICLE_PUT_TOP, top);

            articleRepository.update(articleId, topArticle);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Can't put the article[oId{0}] to top", articleId);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates an article by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "article": {
     *                          "oId": "",
     *                          "articleTitle": "",
     *                          "articleAbstract": "",
     *                          "articleContent": "",
     *                          "articleTags": "tag1,tag2,tag3",
     *                          "articlePermalink": "", // optional
     *                          "articleIsPublished": boolean,
     *                          "articleSignId": "", // optional
     *                          "articleCommentable": boolean,
     *                          "articleViewPwd": "",
     *                          "articleEditorType": "" // optional, preference specified if not exists this key
     *                          }
     *                          }
     * @throws ServiceException service exception
     */
    public void updateArticle(final JSONObject requestJSONObject) throws ServiceException {
        final JSONObject ret = new JSONObject();

        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = requestJSONObject.getJSONObject(ARTICLE);
            final String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            article.put(Article.ARTICLE_TAGS_REF, tagsString.replaceAll("，", ",").replaceAll("、", ","));

            final String articleId = article.getString(Keys.OBJECT_ID);
            // Set permalink
            final JSONObject oldArticle = articleRepository.get(articleId);
            final String permalink = getPermalinkForUpdateArticle(oldArticle, article, (Date) oldArticle.get(ARTICLE_CREATE_DATE));
            article.put(ARTICLE_PERMALINK, permalink);

            processTagsForArticleUpdate(oldArticle, article);

            if (!oldArticle.getString(Article.ARTICLE_PERMALINK).equals(permalink)) { // The permalink has been updated
                // Updates related comments' links
                processCommentsForArticleUpdate(article);
            }

            // Fill auto properties
            fillAutoProperties(oldArticle, article);
            // Set date
            article.put(ARTICLE_UPDATE_DATE, oldArticle.get(ARTICLE_UPDATE_DATE));
            final JSONObject preference = preferenceQueryService.getPreference();
            final Date date = new Date();

            // The article to update has no sign
            if (!article.has(Article.ARTICLE_SIGN_ID)) {
                article.put(Article.ARTICLE_SIGN_ID, "0");
            }

            if (article.getBoolean(ARTICLE_IS_PUBLISHED)) { // Publish it
                if (articleQueryService.hadBeenPublished(oldArticle)) {
                    // Edit update date only for published article
                    article.put(ARTICLE_UPDATE_DATE, date);
                } else { // This article is a draft and this is the first time to publish it
                    article.put(ARTICLE_CREATE_DATE, date);
                    article.put(ARTICLE_UPDATE_DATE, date);
                    article.put(ARTICLE_HAD_BEEN_PUBLISHED, true);
                }
            } else { // Save as draft
                if (articleQueryService.hadBeenPublished(oldArticle)) {
                    // Save update date only for published article
                    article.put(ARTICLE_UPDATE_DATE, date);
                } else {
                    // Reset create/update date to indicate this is an new draft
                    article.put(ARTICLE_CREATE_DATE, date);
                    article.put(ARTICLE_UPDATE_DATE, date);
                }
            }

            // Set editor type
            if (!article.has(Article.ARTICLE_EDITOR_TYPE)) {
                article.put(Article.ARTICLE_EDITOR_TYPE, preference.optString(Option.ID_C_EDITOR_TYPE));
            }

            final boolean publishNewArticle = !oldArticle.getBoolean(ARTICLE_IS_PUBLISHED) && article.getBoolean(ARTICLE_IS_PUBLISHED);

            // Set statistic
            if (publishNewArticle) {
                // This article is updated from unpublished to published
                statisticMgmtService.incPublishedBlogArticleCount();
                final int blogCmtCnt = statisticQueryService.getPublishedBlogCommentCount();
                final int articleCmtCnt = article.getInt(ARTICLE_COMMENT_COUNT);

                statisticMgmtService.setPublishedBlogCommentCount(blogCmtCnt + articleCmtCnt);

                final JSONObject author = userRepository.getByEmail(article.optString(Article.ARTICLE_AUTHOR_EMAIL));

                author.put(UserExt.USER_PUBLISHED_ARTICLE_COUNT, author.optInt(UserExt.USER_PUBLISHED_ARTICLE_COUNT) + 1);
                userRepository.update(author.optString(Keys.OBJECT_ID), author);
            }

            if (publishNewArticle) {
                incArchiveDatePublishedRefCount(articleId);
            }

            // Update
            final boolean postToCommunity = article.optBoolean(Common.POST_TO_COMMUNITY, true);
            article.remove(Common.POST_TO_COMMUNITY); // Do not persist this property
            articleRepository.update(articleId, article);
            article.put(Common.POST_TO_COMMUNITY, postToCommunity); // Restores the property

            if (publishNewArticle) {
                // Fire add article event
                final JSONObject eventData = new JSONObject();

                eventData.put(ARTICLE, article);
                eventData.put(Keys.RESULTS, ret);
                try {
                    eventManager.fireEventSynchronously(new Event<>(EventTypes.ADD_ARTICLE, eventData));
                } catch (final EventException e) {
                    LOGGER.log(Level.ERROR, e.getMessage(), e);
                }
            } else {
                // Fire update article event
                final JSONObject eventData = new JSONObject();

                eventData.put(ARTICLE, article);
                eventData.put(Keys.RESULTS, ret);
                try {
                    eventManager.fireEventSynchronously(new Event<>(EventTypes.UPDATE_ARTICLE, eventData));
                } catch (final EventException e) {
                    LOGGER.log(Level.ERROR, e.getMessage(), e);
                }
            }

            transaction.commit();
        } catch (final ServiceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates an article failed", e);

            throw e;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates an article failed", e);

            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Adds an article from the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          {
     *                          "article": {
     *                          "articleAuthorEmail": "",
     *                          "articleTitle": "",
     *                          "articleAbstract": "",
     *                          "articleContent": "",
     *                          "articleTags": "tag1,tag2,tag3",
     *                          "articleIsPublished": boolean,
     *                          "articlePermalink": "", // optional
     *                          "postToCommunity": boolean, // optional, default is true
     *                          "articleSignId": "" // optional, default is "0",
     *                          "articleCommentable": boolean,
     *                          "articleViewPwd": "",
     *                          "articleEditorType": "", // optional, preference specified if not exists this key
     *                          "oId": "" // optional, generate it if not exists this key
     *                          }
     *                          }
     * @return generated article id
     * @throws ServiceException service exception
     */
    public String addArticle(final JSONObject requestJSONObject) throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final JSONObject article = requestJSONObject.getJSONObject(Article.ARTICLE);
            final String ret = addArticleInternal(article);
            transaction.commit();

            return ret;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Adds the specified article for internal invocation purposes.
     *
     * @param article the specified article
     * @return generated article id
     * @throws ServiceException service exception
     */
    public String addArticleInternal(final JSONObject article) throws ServiceException {
        String ret = article.optString(Keys.OBJECT_ID);

        if (Strings.isEmptyOrNull(ret)) {
            ret = Ids.genTimeMillisId();
            article.put(Keys.OBJECT_ID, ret);
        }

        try {
            // Step 1: Add tags
            String tagsString = article.optString(Article.ARTICLE_TAGS_REF);
            tagsString = tagsString.replaceAll("，", ",").replaceAll("、", ",");
            article.put(Article.ARTICLE_TAGS_REF, tagsString);
            final String[] tagTitles = tagsString.split(",");
            final JSONArray tags = tag(tagTitles, article);
            // Step 2; Set comment/view count to 0
            article.put(Article.ARTICLE_COMMENT_COUNT, 0);
            article.put(Article.ARTICLE_VIEW_COUNT, 0);
            // Step 3: Set create/updat date
            final JSONObject preference = preferenceQueryService.getPreference();
            final Date date = new Date();

            if (!article.has(Article.ARTICLE_CREATE_DATE)) {
                article.put(Article.ARTICLE_CREATE_DATE, date);
            }
            article.put(Article.ARTICLE_UPDATE_DATE, article.opt(Article.ARTICLE_CREATE_DATE));
            // Step 4: Set put top to false
            article.put(Article.ARTICLE_PUT_TOP, false);
            // Step 5: Add tag-article relations
            addTagArticleRelation(tags, article);
            // Step 6: Inc blog article count statictis
            statisticMgmtService.incBlogArticleCount();
            if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                statisticMgmtService.incPublishedBlogArticleCount();
            }
            // Step 7: Add archive date-article relations
            archiveDate(article);
            // Step 8: Set permalink
            final String permalink = getPermalinkForAddArticle(article);
            article.put(Article.ARTICLE_PERMALINK, permalink);
            // Step 9: Add article sign id
            final String signId = article.optString(Article.ARTICLE_SIGN_ID, "1");
            article.put(Article.ARTICLE_SIGN_ID, signId);
            // Step 10: Set had been published status
            article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, false);
            if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                // Publish it directly
                article.put(Article.ARTICLE_HAD_BEEN_PUBLISHED, true);
            }
            // Step 11: Set random double
            article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
            // Step 12: Set post to community
            final boolean postToCommunity = article.optBoolean(Common.POST_TO_COMMUNITY, true);

            article.remove(Common.POST_TO_COMMUNITY); // Do not persist this property
            // Setp 13: Update user article statistic
            final JSONObject author = userRepository.getByEmail(article.optString(Article.ARTICLE_AUTHOR_EMAIL));
            final int userArticleCnt = author.optInt(UserExt.USER_ARTICLE_COUNT);

            author.put(UserExt.USER_ARTICLE_COUNT, userArticleCnt + 1);
            if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                author.put(UserExt.USER_PUBLISHED_ARTICLE_COUNT, author.optInt(UserExt.USER_PUBLISHED_ARTICLE_COUNT) + 1);
            }
            userRepository.update(author.optString(Keys.OBJECT_ID), author);
            // Step 14: Set editor type
            if (!article.has(Article.ARTICLE_EDITOR_TYPE)) {
                article.put(Article.ARTICLE_EDITOR_TYPE, preference.optString(Option.ID_C_EDITOR_TYPE));
            }
            // Step 15: Add article
            articleRepository.add(article);

            article.put(Common.POST_TO_COMMUNITY, postToCommunity); // Restores the property

            if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                // Fire add article event
                final JSONObject eventData = new JSONObject();

                eventData.put(Article.ARTICLE, article);
                eventManager.fireEventSynchronously(new Event<>(EventTypes.ADD_ARTICLE, eventData));
            }

            article.remove(Common.POST_TO_COMMUNITY);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Adds an article failed", e);

            throw new ServiceException(e);
        } catch (final EventException e) {
            LOGGER.log(Level.WARN, "Adds an article event process failed", e);
        }

        return ret;
    }

    /**
     * Removes the article specified by the given id.
     *
     * @param articleId the given id
     * @throws ServiceException service exception
     */
    public void removeArticle(final String articleId) throws ServiceException {
        LOGGER.log(Level.DEBUG, "Removing an article[id={0}]", articleId);

        final Transaction transaction = articleRepository.beginTransaction();

        try {
            decTagRefCount(articleId);
            unArchiveDate(articleId);
            removeTagArticleRelations(articleId);
            removeArticleComments(articleId);

            final JSONObject article = articleRepository.get(articleId);

            articleRepository.remove(articleId);

            statisticMgmtService.decBlogArticleCount();
            if (article.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                statisticMgmtService.decPublishedBlogArticleCount();
            }

            final JSONObject author = userRepository.getByEmail(article.optString(Article.ARTICLE_AUTHOR_EMAIL));

            author.put(UserExt.USER_PUBLISHED_ARTICLE_COUNT, author.optInt(UserExt.USER_PUBLISHED_ARTICLE_COUNT) - 1);
            author.put(UserExt.USER_ARTICLE_COUNT, author.optInt(UserExt.USER_ARTICLE_COUNT) - 1);
            userRepository.update(author.optString(Keys.OBJECT_ID), author);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes an article[id=" + articleId + "] failed", e);
            throw new ServiceException(e);
        }

        LOGGER.log(Level.DEBUG, "Removed an article[id={0}]", articleId);
    }

    /**
     * Updates the random values of articles fetched with the specified update
     * count.
     *
     * @param updateCnt the specified update count
     * @throws ServiceException service exception
     */
    public void updateArticlesRandomValue(final int updateCnt)
            throws ServiceException {
        final Transaction transaction = articleRepository.beginTransaction();

        try {
            final List<JSONObject> randomArticles = articleRepository.getRandomly(updateCnt);

            for (final JSONObject article : randomArticles) {
                article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());

                articleRepository.update(article.getString(Keys.OBJECT_ID), article);
            }

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.WARN, "Updates article random value failed");

            throw new ServiceException(e);
        }
    }

    /**
     * Increments the view count of the article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws ServiceException service exception
     */
    public void incViewCount(final String articleId) throws ServiceException {
        JSONObject article;

        try {
            article = articleRepository.get(articleId);

            if (null == article) {
                return;
            }
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets article [id=" + articleId + "] failed", e);

            return;
        }

        final Transaction transaction = articleRepository.beginTransaction();

        try {
            article.put(Article.ARTICLE_VIEW_COUNT, article.getInt(Article.ARTICLE_VIEW_COUNT) + 1);
            articleRepository.update(articleId, article);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.WARN, "Updates article view count failed");

            throw new ServiceException(e);
        }
    }

    /**
     * Decrements reference count of every tag of an article specified by the
     * given article id.
     *
     * @param articleId the given article id
     * @throws ServiceException service exception
     */
    private void decTagRefCount(final String articleId) throws ServiceException {
        try {
            final List<JSONObject> tags = tagRepository.getByArticleId(articleId);
            final JSONObject article = articleRepository.get(articleId);

            for (final JSONObject tag : tags) {
                final String tagId = tag.getString(Keys.OBJECT_ID);
                final int refCnt = tag.getInt(Tag.TAG_REFERENCE_COUNT);

                tag.put(Tag.TAG_REFERENCE_COUNT, refCnt - 1);
                final int publishedRefCnt = tag.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);

                if (article.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                    tag.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt - 1);
                } else {
                    tag.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt);
                }
                tagRepository.update(tagId, tag);
                LOGGER.log(Level.TRACE, "Deced tag[title={0}, refCnt={1}, publishedRefCnt={2}] of article[id={3}]",
                        tag.getString(Tag.TAG_TITLE), tag.getInt(Tag.TAG_REFERENCE_COUNT), tag.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT),
                        articleId);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Decs tag references count of article[id" + articleId + "] failed", e);
            throw new ServiceException(e);
        }

        LOGGER.log(Level.DEBUG, "Deced all tag reference count of article[id={0}]", articleId);
    }

    /**
     * Un-archive an article specified by the given specified article id.
     *
     * @param articleId the given article id
     * @throws ServiceException service exception
     */
    private void unArchiveDate(final String articleId) throws ServiceException {
        try {
            final JSONObject archiveDateArticleRelation = archiveDateArticleRepository.getByArticleId(articleId);
            final String archiveDateId = archiveDateArticleRelation.getString(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID);
            final JSONObject archiveDate = archiveDateRepository.get(archiveDateId);
            int archiveDateArticleCnt = archiveDate.getInt(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT);

            --archiveDateArticleCnt;
            int archiveDatePublishedArticleCnt = archiveDate.getInt(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT);
            final JSONObject article = articleRepository.get(articleId);

            if (article.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                --archiveDatePublishedArticleCnt;
            }

            if (0 == archiveDateArticleCnt) {
                archiveDateRepository.remove(archiveDateId);
            } else {
                final JSONObject newArchiveDate = new JSONObject(archiveDate,
                        CollectionUtils.jsonArrayToArray(archiveDate.names(), String[].class));

                newArchiveDate.put(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT, archiveDateArticleCnt);
                newArchiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT, archiveDatePublishedArticleCnt);
                archiveDateRepository.update(archiveDateId, newArchiveDate);
            }

            archiveDateArticleRepository.remove(archiveDateArticleRelation.getString(Keys.OBJECT_ID));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Unarchive date for article[id=" + articleId + "] failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Processes comments for article update.
     *
     * @param article the specified article to update
     * @throws Exception exception
     */
    private void processCommentsForArticleUpdate(final JSONObject article) throws Exception {
        final String articleId = article.getString(Keys.OBJECT_ID);

        final List<JSONObject> comments = commentRepository.getComments(articleId, 1, Integer.MAX_VALUE);

        for (final JSONObject comment : comments) {
            final String commentId = comment.getString(Keys.OBJECT_ID);
            final String sharpURL = Comments.getCommentSharpURLForArticle(article, commentId);

            comment.put(Comment.COMMENT_SHARP_URL, sharpURL);

            if (Strings.isEmptyOrNull(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID))) {
                comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
            }
            if (Strings.isEmptyOrNull(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_NAME))) {
                comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");
            }

            commentRepository.update(commentId, comment);
        }
    }

    /**
     * Processes tags for article update.
     * <p>
     * <ul>
     * <li>Un-tags old article, decrements tag reference count</li>
     * <li>Removes old article-tag relations</li>
     * <li>Saves new article-tag relations with tag reference count</li>
     * </ul>
     *
     * @param oldArticle the specified old article
     * @param newArticle the specified new article
     * @throws Exception exception
     */
    private void processTagsForArticleUpdate(final JSONObject oldArticle, final JSONObject newArticle) throws Exception {
        final String oldArticleId = oldArticle.getString(Keys.OBJECT_ID);
        final List<JSONObject> oldTags = tagRepository.getByArticleId(oldArticleId);
        final String tagsString = newArticle.getString(Article.ARTICLE_TAGS_REF);
        String[] tagStrings = tagsString.split(",");
        final List<JSONObject> newTags = new ArrayList<>();

        for (int i = 0; i < tagStrings.length; i++) {
            final String tagTitle = tagStrings[i].trim();
            JSONObject newTag = tagRepository.getByTitle(tagTitle);

            if (null == newTag) {
                newTag = new JSONObject();
                newTag.put(Tag.TAG_TITLE, tagTitle);
            }
            newTags.add(newTag);
        }

        final List<JSONObject> tagsDropped = new ArrayList<>();
        final List<JSONObject> tagsNeedToAdd = new ArrayList<>();
        final List<JSONObject> tagsUnchanged = new ArrayList<>();

        for (final JSONObject newTag : newTags) {
            final String newTagTitle = newTag.getString(Tag.TAG_TITLE);

            if (!tagExists(newTagTitle, oldTags)) {
                LOGGER.log(Level.DEBUG, "Tag need to add[title={0}]", newTagTitle);
                tagsNeedToAdd.add(newTag);
            } else {
                tagsUnchanged.add(newTag);
            }
        }
        for (final JSONObject oldTag : oldTags) {
            final String oldTagTitle = oldTag.getString(Tag.TAG_TITLE);

            if (!tagExists(oldTagTitle, newTags)) {
                LOGGER.log(Level.DEBUG, "Tag dropped[title={0}]", oldTag);
                tagsDropped.add(oldTag);
            } else {
                tagsUnchanged.remove(oldTag);
            }
        }

        LOGGER.log(Level.DEBUG, "Tags unchanged[{0}]", tagsUnchanged);
        for (final JSONObject tagUnchanged : tagsUnchanged) {
            final String tagId = tagUnchanged.optString(Keys.OBJECT_ID);

            if (null == tagId) {
                continue; // Unchanged tag always exist id
            }
            final int publishedRefCnt = tagUnchanged.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);

            if (oldArticle.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                if (!newArticle.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                    tagUnchanged.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt - 1);
                    tagRepository.update(tagId, tagUnchanged);
                }
            } else {
                if (newArticle.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                    tagUnchanged.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt + 1);
                    tagRepository.update(tagId, tagUnchanged);
                }
            }
        }

        for (final JSONObject tagDropped : tagsDropped) {
            final String tagId = tagDropped.getString(Keys.OBJECT_ID);
            final int refCnt = tagDropped.getInt(Tag.TAG_REFERENCE_COUNT);

            tagDropped.put(Tag.TAG_REFERENCE_COUNT, refCnt - 1);
            final int publishedRefCnt = tagDropped.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);

            if (oldArticle.getBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                tagDropped.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt - 1);
            }

            tagRepository.update(tagId, tagDropped);
        }

        final String[] tagIdsDropped = new String[tagsDropped.size()];

        for (int i = 0; i < tagIdsDropped.length; i++) {
            final JSONObject tag = tagsDropped.get(i);
            final String id = tag.getString(Keys.OBJECT_ID);

            tagIdsDropped[i] = id;
        }

        removeTagArticleRelations(oldArticleId, 0 == tagIdsDropped.length ? new String[]{"l0y0l"} : tagIdsDropped);

        tagStrings = new String[tagsNeedToAdd.size()];
        for (int i = 0; i < tagStrings.length; i++) {
            final JSONObject tag = tagsNeedToAdd.get(i);
            final String tagTitle = tag.getString(Tag.TAG_TITLE);

            tagStrings[i] = tagTitle;
        }
        final JSONArray tags = tag(tagStrings, newArticle);

        addTagArticleRelation(tags, newArticle);
    }

    /**
     * Removes tag-article relations by the specified article id and tag ids of the relations to be removed.
     * <p>
     * Removes all relations if not specified the tag ids.
     * </p>
     *
     * @param articleId the specified article id
     * @param tagIds    the specified tag ids of the relations to be removed
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void removeTagArticleRelations(final String articleId, final String... tagIds)
            throws JSONException, RepositoryException {
        final List<String> tagIdList = Arrays.asList(tagIds);
        final List<JSONObject> tagArticleRelations = tagArticleRepository.getByArticleId(articleId);

        for (int i = 0; i < tagArticleRelations.size(); i++) {
            final JSONObject tagArticleRelation = tagArticleRelations.get(i);
            String relationId;

            if (tagIdList.isEmpty()) { // Removes all if un-specified
                relationId = tagArticleRelation.getString(Keys.OBJECT_ID);
                tagArticleRepository.remove(relationId);
            } else {
                if (tagIdList.contains(tagArticleRelation.getString(Tag.TAG + "_" + Keys.OBJECT_ID))) {
                    relationId = tagArticleRelation.getString(Keys.OBJECT_ID);
                    tagArticleRepository.remove(relationId);
                }
            }
        }
    }

    /**
     * Adds relation of the specified tags and article.
     *
     * @param tags    the specified tags
     * @param article the specified article
     * @throws RepositoryException repository exception
     */
    private void addTagArticleRelation(final JSONArray tags, final JSONObject article) throws RepositoryException {
        for (int i = 0; i < tags.length(); i++) {
            final JSONObject tag = tags.optJSONObject(i);
            final JSONObject tagArticleRelation = new JSONObject();

            tagArticleRelation.put(Tag.TAG + "_" + Keys.OBJECT_ID, tag.optString(Keys.OBJECT_ID));
            tagArticleRelation.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));

            tagArticleRepository.add(tagArticleRelation);
        }
    }

    /**
     * Tags the specified article with the specified tag titles.
     *
     * @param tagTitles the specified tag titles
     * @param article   the specified article
     * @return an array of tags
     * @throws RepositoryException repository exception
     */
    private JSONArray tag(final String[] tagTitles, final JSONObject article) throws RepositoryException {
        final JSONArray ret = new JSONArray();

        for (int i = 0; i < tagTitles.length; i++) {
            final String tagTitle = tagTitles[i].trim();
            JSONObject tag = tagRepository.getByTitle(tagTitle);
            String tagId;

            if (null == tag) {
                LOGGER.log(Level.TRACE, "Found a new tag[title={0}] in article[title={1}]",
                        tagTitle, article.optString(Article.ARTICLE_TITLE));
                tag = new JSONObject();
                tag.put(Tag.TAG_TITLE, tagTitle);
                tag.put(Tag.TAG_REFERENCE_COUNT, 1);
                if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) { // Publish article directly
                    tag.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, 1);
                } else { // Save as draft
                    tag.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, 0);
                }

                tagId = tagRepository.add(tag);
                tag.put(Keys.OBJECT_ID, tagId);
            } else {
                tagId = tag.optString(Keys.OBJECT_ID);
                LOGGER.log(Level.TRACE, "Found a existing tag[title={0}, id={1}] in article[title={2}]",
                        tag.optString(Tag.TAG_TITLE), tag.optString(Keys.OBJECT_ID), article.optString(Article.ARTICLE_TITLE));
                final JSONObject tagTmp = new JSONObject();

                tagTmp.put(Keys.OBJECT_ID, tagId);
                tagTmp.put(Tag.TAG_TITLE, tagTitle);
                final int refCnt = tag.optInt(Tag.TAG_REFERENCE_COUNT);
                final int publishedRefCnt = tag.optInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);

                tagTmp.put(Tag.TAG_REFERENCE_COUNT, refCnt + 1);
                if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
                    tagTmp.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt + 1);
                } else {
                    tagTmp.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt);
                }
                tagRepository.update(tagId, tagTmp);
            }

            ret.put(tag);
        }

        return ret;
    }

    /**
     * Removes article comments by the specified article id.
     * <p>
     * Removes related comments, sets article/blog comment statistic count.
     * </p>
     *
     * @param articleId the specified article id
     * @throws Exception exception
     */
    private void removeArticleComments(final String articleId) throws Exception {
        final int removedCnt = commentRepository.removeComments(articleId);
        int blogCommentCount = statisticQueryService.getBlogCommentCount();

        blogCommentCount -= removedCnt;
        statisticMgmtService.setBlogCommentCount(blogCommentCount);

        final JSONObject article = articleRepository.get(articleId);

        if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
            int publishedBlogCommentCount = statisticQueryService.getPublishedBlogCommentCount();

            publishedBlogCommentCount -= removedCnt;
            statisticMgmtService.setPublishedBlogCommentCount(publishedBlogCommentCount);
        }
    }

    /**
     * Archive the create date with the specified article.
     *
     * @param article the specified article, for example,
     *                {
     *                ....,
     *                "oId": "",
     *                "articleCreateDate": java.util.Date,
     *                ....
     *                }
     * @throws RepositoryException repository exception
     */
    private void archiveDate(final JSONObject article) throws RepositoryException {
        final Date createDate = (Date) article.opt(Article.ARTICLE_CREATE_DATE);
        final String createDateString = DateFormatUtils.format(createDate, "yyyy/MM");
        JSONObject archiveDate = archiveDateRepository.getByArchiveDate(createDateString);

        if (null == archiveDate) {
            archiveDate = new JSONObject();
            try {
                archiveDate.put(ArchiveDate.ARCHIVE_TIME, DateUtils.parseDate(createDateString, new String[]{"yyyy/MM"}).getTime());
                archiveDate.put(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT, 0);
                archiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT, 0);

                archiveDateRepository.add(archiveDate);
            } catch (final ParseException e) {
                LOGGER.log(Level.ERROR, e.getMessage(), e);
                throw new RepositoryException(e);
            }
        }

        final JSONObject newArchiveDate = new JSONObject(archiveDate, CollectionUtils.jsonArrayToArray(archiveDate.names(), String[].class));

        newArchiveDate.put(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT, archiveDate.optInt(ArchiveDate.ARCHIVE_DATE_ARTICLE_COUNT) + 1);
        if (article.optBoolean(Article.ARTICLE_IS_PUBLISHED)) {
            newArchiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT,
                    archiveDate.optInt(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT) + 1);
        }
        archiveDateRepository.update(archiveDate.optString(Keys.OBJECT_ID), newArchiveDate);

        final JSONObject archiveDateArticleRelation = new JSONObject();

        archiveDateArticleRelation.put(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID, archiveDate.optString(Keys.OBJECT_ID));
        archiveDateArticleRelation.put(Article.ARTICLE + "_" + Keys.OBJECT_ID, article.optString(Keys.OBJECT_ID));

        archiveDateArticleRepository.add(archiveDateArticleRelation);
    }

    /**
     * Fills 'auto' properties for the specified article and old article.
     * <p>
     * Some properties of an article are not been changed while article
     * updating, these properties are called 'auto' properties.
     * </p>
     * <p>
     * The property(named {@value org.b3log.solo.model.Article#ARTICLE_RANDOM_DOUBLE}) of the specified
     * article will be regenerated.
     * </p>
     *
     * @param oldArticle the specified old article
     * @param article    the specified article
     * @throws JSONException json exception
     */
    private void fillAutoProperties(final JSONObject oldArticle, final JSONObject article) throws JSONException {
        final Date createDate = (Date) oldArticle.get(ARTICLE_CREATE_DATE);

        article.put(ARTICLE_CREATE_DATE, createDate);
        article.put(ARTICLE_COMMENT_COUNT, oldArticle.getInt(ARTICLE_COMMENT_COUNT));
        article.put(ARTICLE_VIEW_COUNT, oldArticle.getInt(ARTICLE_VIEW_COUNT));
        article.put(ARTICLE_PUT_TOP, oldArticle.getBoolean(ARTICLE_PUT_TOP));
        article.put(ARTICLE_HAD_BEEN_PUBLISHED, oldArticle.getBoolean(ARTICLE_HAD_BEEN_PUBLISHED));
        article.put(ARTICLE_AUTHOR_EMAIL, oldArticle.getString(ARTICLE_AUTHOR_EMAIL));
        article.put(ARTICLE_RANDOM_DOUBLE, Math.random());
    }

    /**
     * Gets article permalink for adding article with the specified article.
     *
     * @param article the specified article
     * @return permalink
     * @throws ServiceException if invalid permalink occurs
     */
    private String getPermalinkForAddArticle(final JSONObject article) throws ServiceException {
        final Date date = (Date) article.opt(Article.ARTICLE_CREATE_DATE);

        String ret = article.optString(Article.ARTICLE_PERMALINK);

        if (Strings.isEmptyOrNull(ret)) {
            ret = "/articles/" + DateFormatUtils.format(date, "yyyy/MM/dd") + "/" + article.optString(Keys.OBJECT_ID) + ".html";
        }

        if (!ret.startsWith("/")) {
            ret = "/" + ret;
        }

        if (PermalinkQueryService.invalidArticlePermalinkFormat(ret)) {
            throw new ServiceException(langPropsService.get("invalidPermalinkFormatLabel"));
        }

        if (permalinkQueryService.exist(ret)) {
            throw new ServiceException(langPropsService.get("duplicatedPermalinkLabel"));
        }

        return ret.replaceAll(" ", "-");
    }

    /**
     * Gets article permalink for updating article with the specified old article, article, create date.
     *
     * @param oldArticle the specified old article
     * @param article    the specified article
     * @param createDate the specified create date
     * @return permalink
     * @throws ServiceException if invalid permalink occurs
     * @throws JSONException    json exception
     */
    private String getPermalinkForUpdateArticle(final JSONObject oldArticle, final JSONObject article, final Date createDate)
            throws ServiceException, JSONException {
        final String articleId = article.getString(Keys.OBJECT_ID);
        String ret = article.optString(ARTICLE_PERMALINK).trim();
        final String oldPermalink = oldArticle.getString(ARTICLE_PERMALINK);

        if (!oldPermalink.equals(ret)) {
            if (Strings.isEmptyOrNull(ret)) {
                ret = "/articles/" + DateFormatUtils.format(createDate, "yyyy/MM/dd") + "/" + articleId + ".html";
            }

            if (!ret.startsWith("/")) {
                ret = "/" + ret;
            }

            if (PermalinkQueryService.invalidArticlePermalinkFormat(ret)) {
                throw new ServiceException(langPropsService.get("invalidPermalinkFormatLabel"));
            }

            if (!oldPermalink.equals(ret) && permalinkQueryService.exist(ret)) {
                throw new ServiceException(langPropsService.get("duplicatedPermalinkLabel"));
            }
        }

        return ret.replaceAll(" ", "-");
    }

    /**
     * Decrements reference count of archive date of an published article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void decArchiveDatePublishedRefCount(final String articleId)
            throws JSONException, RepositoryException {
        final JSONObject archiveDateArticleRelation = archiveDateArticleRepository.getByArticleId(articleId);
        final String archiveDateId = archiveDateArticleRelation.getString(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID);
        final JSONObject archiveDate = archiveDateRepository.get(archiveDateId);

        archiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT,
                archiveDate.getInt(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT) - 1);
        archiveDateRepository.update(archiveDateId, archiveDate);
    }

    /**
     * Increments reference count of archive date of an published article specified by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    private void incArchiveDatePublishedRefCount(final String articleId)
            throws JSONException, RepositoryException {
        final JSONObject archiveDateArticleRelation = archiveDateArticleRepository.getByArticleId(articleId);
        final String archiveDateId = archiveDateArticleRelation.getString(ArchiveDate.ARCHIVE_DATE + "_" + Keys.OBJECT_ID);
        final JSONObject archiveDate = archiveDateRepository.get(archiveDateId);

        archiveDate.put(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT,
                archiveDate.getInt(ArchiveDate.ARCHIVE_DATE_PUBLISHED_ARTICLE_COUNT) + 1);
        archiveDateRepository.update(archiveDateId, archiveDate);
    }

    /**
     * Sets archive date article repository with the specified archive date article repository.
     *
     * @param archiveDateArticleRepository the specified archive date article repository
     */
    public void setArchiveDateArticleRepository(final ArchiveDateArticleRepository archiveDateArticleRepository) {
        this.archiveDateArticleRepository = archiveDateArticleRepository;
    }

    /**
     * Sets archive date repository with the specified archive date repository.
     *
     * @param archiveDateRepository the specified archive date repository
     */
    public void setArchiveDateRepository(final ArchiveDateRepository archiveDateRepository) {
        this.archiveDateRepository = archiveDateRepository;
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
     * Sets the article query service with the specified article query service.
     *
     * @param articleQueryService the specified article query service
     */
    public void setArticleQueryService(final ArticleQueryService articleQueryService) {
        this.articleQueryService = articleQueryService;
    }

    /**
     * Sets the permalink query service with the specified permalink query service.
     *
     * @param permalinkQueryService the specified permalink query service
     */
    public void setPermalinkQueryService(final PermalinkQueryService permalinkQueryService) {
        this.permalinkQueryService = permalinkQueryService;
    }

    /**
     * Sets the user repository with the specified user repository.
     *
     * @param userRepository the specified user repository
     */
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
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
     * Sets the statistic query service with the specified statistic query service.
     *
     * @param statisticQueryService the specified statistic query service
     */
    public void setStatisticQueryService(final StatisticQueryService statisticQueryService) {
        this.statisticQueryService = statisticQueryService;
    }

    /**
     * Sets the tag repository with the specified tag repository.
     *
     * @param tagRepository the specified tag repository
     */
    public void setTagRepository(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Sets the tag article repository with the specified tag article repository.
     *
     * @param tagArticleRepository the specified tag article repository
     */
    public void setTagArticleRepository(final TagArticleRepository tagArticleRepository) {
        this.tagArticleRepository = tagArticleRepository;
    }

    /**
     * Sets tag management service with the specified tag management service.
     *
     * @param tagMgmtService the specified tag management service
     */
    public void setTagMgmtService(final TagMgmtService tagMgmtService) {
        this.tagMgmtService = tagMgmtService;
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
