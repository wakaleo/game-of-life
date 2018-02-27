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
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Paginator;
import org.b3log.latke.util.Strings;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Page;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.PageRepository;
import org.b3log.solo.util.Emotions;
import org.b3log.solo.util.Markdowns;
import org.b3log.solo.util.Thumbnails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Comment query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.3.2.0, Aug 31, 2017
 * @since 0.3.5
 */
@Service
public class CommentQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentQueryService.class);

    /**
     * User service.
     */
    @Inject
    private UserQueryService userQueryService;

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
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * Can the current user access a comment specified by the given comment id?
     *
     * @param commentId the given comment id
     * @param request   the specified request
     * @return {@code true} if the current user can access the comment, {@code false} otherwise
     * @throws Exception exception
     */
    public boolean canAccessComment(final String commentId, final HttpServletRequest request) throws Exception {
        if (Strings.isEmptyOrNull(commentId)) {
            return false;
        }

        if (userQueryService.isAdminLoggedIn(request)) {
            return true;
        }

        // Here, you are not admin
        final JSONObject comment = commentRepository.get(commentId);
        if (null == comment) {
            return false;
        }

        final String onId = comment.optString(Comment.COMMENT_ON_ID);
        final String onType = comment.optString(Comment.COMMENT_ON_TYPE);

        if (Page.PAGE.equals(onType)) {
            return false; // Only admin can access page comment
        }

        final JSONObject article = articleRepository.get(onId);
        if (null == article) {
            return false;
        }

        final String currentUserEmail = userQueryService.getCurrentUser(request).getString(User.USER_EMAIL);

        return article.getString(Article.ARTICLE_AUTHOR_EMAIL).equals(currentUserEmail);
    }

    /**
     * Gets comments with the specified request json object, request and response.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "paginationCurrentPageNum": 1,
     *                          "paginationPageSize": 20,
     *                          "paginationWindowSize": 10
     * @return for example,
     * <pre>
     * {
     *     "comments": [{
     *         "oId": "",
     *         "commentTitle": "",
     *         "commentName": "",
     *         "commentEmail": "",
     *         "thumbnailUrl": "",
     *         "commentURL": "",
     *         "commentContent": "",
     *         "commentTime": long,
     *         "commentSharpURL": ""
     *      }, ....]
     *     "sc": "GET_COMMENTS_SUCC"
     * }
     * </pre>
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getComments(final JSONObject requestJSONObject) throws ServiceException {
        try {
            final JSONObject ret = new JSONObject();

            final int currentPageNum = requestJSONObject.getInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
            final int pageSize = requestJSONObject.getInt(Pagination.PAGINATION_PAGE_SIZE);
            final int windowSize = requestJSONObject.getInt(Pagination.PAGINATION_WINDOW_SIZE);

            final Query query = new Query().setCurrentPageNum(currentPageNum).setPageSize(pageSize).addSort(Comment.COMMENT_DATE,
                    SortDirection.DESCENDING);
            final JSONObject result = commentRepository.get(query);
            final JSONArray comments = result.getJSONArray(Keys.RESULTS);

            // Sets comment title and content escaping
            for (int i = 0; i < comments.length(); i++) {
                final JSONObject comment = comments.getJSONObject(i);
                String title;

                final String onType = comment.getString(Comment.COMMENT_ON_TYPE);
                final String onId = comment.getString(Comment.COMMENT_ON_ID);

                if (Article.ARTICLE.equals(onType)) {
                    final JSONObject article = articleRepository.get(onId);

                    title = article.getString(Article.ARTICLE_TITLE);
                    comment.put(Common.TYPE, Common.ARTICLE_COMMENT_TYPE);
                } else { // It's a comment of page
                    final JSONObject page = pageRepository.get(onId);

                    title = page.getString(Page.PAGE_TITLE);
                    comment.put(Common.TYPE, Common.PAGE_COMMENT_TYPE);
                }

                comment.put(Common.COMMENT_TITLE, title);

                String commentContent = comment.optString(Comment.COMMENT_CONTENT);
                commentContent = Emotions.convert(commentContent);
                commentContent = Markdowns.toHTML(commentContent);
                commentContent = Jsoup.clean(commentContent, Whitelist.relaxed());
                comment.put(Comment.COMMENT_CONTENT, commentContent);

                comment.put(Comment.COMMENT_TIME, ((Date) comment.get(Comment.COMMENT_DATE)).getTime());
                comment.remove(Comment.COMMENT_DATE);
            }

            final int pageCount = result.getJSONObject(Pagination.PAGINATION).getInt(Pagination.PAGINATION_PAGE_COUNT);
            final JSONObject pagination = new JSONObject();
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

            ret.put(Comment.COMMENTS, comments);
            ret.put(Pagination.PAGINATION, pagination);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets comments failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets comments of an article or page specified by the on id.
     *
     * @param onId the specified on id
     * @return a list of comments, returns an empty list if not found
     * @throws ServiceException service exception
     */
    public List<JSONObject> getComments(final String onId) throws ServiceException {
        try {
            final List<JSONObject> ret = new ArrayList<JSONObject>();

            final List<JSONObject> comments = commentRepository.getComments(onId, 1, Integer.MAX_VALUE);

            for (final JSONObject comment : comments) {
                comment.put(Comment.COMMENT_TIME, ((Date) comment.get(Comment.COMMENT_DATE)).getTime());
                comment.put("commentDate2", comment.get(Comment.COMMENT_DATE)); // 1.9.0 向后兼容
                comment.put(Comment.COMMENT_NAME, comment.getString(Comment.COMMENT_NAME));
                String url = comment.getString(Comment.COMMENT_URL);
                if (StringUtils.contains(url, "<")) { // legacy issue https://github.com/b3log/solo/issues/12091
                    url = "";
                }
                comment.put(Comment.COMMENT_URL, url);
                comment.put(Common.IS_REPLY, false); // Assumes this comment is not a reply

                final String email = comment.optString(Comment.COMMENT_EMAIL);

                final String thumbnailURL = comment.optString(Comment.COMMENT_THUMBNAIL_URL);
                if (Strings.isEmptyOrNull(thumbnailURL)) {
                    comment.put(Comment.COMMENT_THUMBNAIL_URL, Thumbnails.getGravatarURL(email, "128"));
                }

                if (!Strings.isEmptyOrNull(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID))) {
                    // This comment is a reply
                    comment.put(Common.IS_REPLY, true);
                }

                String commentContent = comment.optString(Comment.COMMENT_CONTENT);
                commentContent = Emotions.convert(commentContent);
                commentContent = Markdowns.toHTML(commentContent);
                commentContent = Jsoup.clean(commentContent, Whitelist.relaxed());
                comment.put(Comment.COMMENT_CONTENT, commentContent);

                ret.add(comment);
            }

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets comments failed", e);
            throw new ServiceException(e);
        }
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
     * Set the page repository with the specified page repository.
     *
     * @param pageRepository the specified page repository
     */
    public void setPageRepository(final PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    /**
     * Sets the comment repository with the specified comment repository.
     *
     * @param commentRepository the specified comment repository
     */
    public void setCommentRepository(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
