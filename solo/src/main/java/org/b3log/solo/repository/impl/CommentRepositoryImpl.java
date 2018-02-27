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
package org.b3log.solo.repository.impl;


import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.*;
import org.b3log.latke.repository.annotation.Repository;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.cache.CommentCache;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;


/**
 * Comment repository.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.9, Jul 16, 2017
 * @since 0.3.1
 */
@Repository
public class CommentRepositoryImpl extends AbstractRepository implements CommentRepository {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentRepositoryImpl.class);

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Comment cache.
     */
    @Inject
    private CommentCache commentCache;

    /**
     * Public constructor.
     */
    public CommentRepositoryImpl() {
        super(Comment.COMMENT);
    }

    @Override
    public void remove(final String id) throws RepositoryException {
        super.remove(id);

        commentCache.removeComment(id);
    }

    @Override
    public JSONObject get(final String id) throws RepositoryException {
        JSONObject ret = commentCache.getComment(id);
        if (null != ret) {
            return ret;
        }

        ret = super.get(id);
        if (null == ret) {
            return null;
        }

        commentCache.putComment(ret);

        return ret;
    }

    @Override
    public void update(final String id, final JSONObject comment) throws RepositoryException {
        super.update(id, comment);

        comment.put(Keys.OBJECT_ID, id);
        commentCache.putComment(comment);
    }

    @Override
    public int removeComments(final String onId) throws RepositoryException {
        final List<JSONObject> comments = getComments(onId, 1, Integer.MAX_VALUE);

        for (final JSONObject comment : comments) {
            final String commentId = comment.optString(Keys.OBJECT_ID);

            remove(commentId);
        }

        LOGGER.log(Level.DEBUG, "Removed comments[onId={0}, removedCnt={1}]", onId, comments.size());

        return comments.size();
    }

    @Override
    public List<JSONObject> getComments(final String onId, final int currentPageNum, final int pageSize)
            throws RepositoryException {
        final Query query = new Query().
                addSort(Keys.OBJECT_ID, SortDirection.DESCENDING).
                setFilter(new PropertyFilter(Comment.COMMENT_ON_ID, FilterOperator.EQUAL, onId)).
                setCurrentPageNum(currentPageNum).setPageSize(pageSize).setPageCount(1);

        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        return CollectionUtils.jsonArrayToList(array);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JSONObject> getRecentComments(final int num) throws RepositoryException {
        final Query query = new Query().
                addSort(Keys.OBJECT_ID, SortDirection.DESCENDING).
                setCurrentPageNum(1).setPageSize(num).setPageCount(1);

        List<JSONObject> ret;
        final JSONObject result = get(query);
        final JSONArray array = result.optJSONArray(Keys.RESULTS);

        ret = CollectionUtils.jsonArrayToList(array);

        // Removes unpublished article related comments
        removeForUnpublishedArticles(ret);

        return ret;
    }

    /**
     * Removes comments of unpublished articles for the specified comments.
     *
     * @param comments the specified comments
     * @throws RepositoryException repository exception
     */
    private void removeForUnpublishedArticles(final List<JSONObject> comments) throws RepositoryException {
        LOGGER.debug("Removing unpublished articles' comments....");
        final Iterator<JSONObject> iterator = comments.iterator();

        while (iterator.hasNext()) {
            final JSONObject comment = iterator.next();
            final String commentOnType = comment.optString(Comment.COMMENT_ON_TYPE);

            if (Article.ARTICLE.equals(commentOnType)) {
                final String articleId = comment.optString(Comment.COMMENT_ON_ID);

                if (!articleRepository.isPublished(articleId)) {
                    iterator.remove();
                }
            }
        }

        LOGGER.debug("Removed unpublished articles' comments....");
    }

    /**
     * Sets the article repository with the specified article repository.
     *
     * @param articleRepository the specified article repository
     */
    public void setArticleRepository(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
