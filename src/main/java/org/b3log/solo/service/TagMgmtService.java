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


import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.CategoryTagRepository;
import org.b3log.solo.repository.TagRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Tag management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Mar 31, 2017
 * @since 0.4.0
 */
@Service
public class TagMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TagMgmtService.class);

    /**
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Category-tag repository.
     */
    @Inject
    private CategoryTagRepository categoryTagRepository;

    /**
     * Decrements reference count of every tag of an published article specified
     * by the given article id.
     *
     * @param articleId the given article id
     * @throws JSONException       json exception
     * @throws RepositoryException repository exception
     */
    public void decTagPublishedRefCount(final String articleId) throws JSONException, RepositoryException {
        final List<JSONObject> tags = tagRepository.getByArticleId(articleId);

        for (final JSONObject tag : tags) {
            final String tagId = tag.getString(Keys.OBJECT_ID);
            final int refCnt = tag.getInt(Tag.TAG_REFERENCE_COUNT);

            tag.put(Tag.TAG_REFERENCE_COUNT, refCnt);
            final int publishedRefCnt = tag.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT);

            tag.put(Tag.TAG_PUBLISHED_REFERENCE_COUNT, publishedRefCnt - 1);
            tagRepository.update(tagId, tag);
        }
    }

    /**
     * Removes all unused tags.
     *
     * @throws ServiceException if get tags failed, or remove failed
     */
    public void removeUnusedTags() throws ServiceException {
        final Transaction transaction = tagRepository.beginTransaction();

        try {
            final List<JSONObject> tags = tagQueryService.getTags();

            for (int i = 0; i < tags.size(); i++) {
                final JSONObject tag = tags.get(i);
                final int tagRefCnt = tag.getInt(Tag.TAG_REFERENCE_COUNT);

                if (0 == tagRefCnt) {
                    final String tagId = tag.getString(Keys.OBJECT_ID);

                    categoryTagRepository.removeByTagId(tagId);
                    tagRepository.remove(tagId);
                }
            }

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes unused tags failed", e);

            throw new ServiceException(e);
        }
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
     * Sets the tag query service with the specified tag query service.
     *
     * @param tagQueryService the specified tag query service
     */
    public void setTagQueryService(final TagQueryService tagQueryService) {
        this.tagQueryService = tagQueryService;
    }
}
