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
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.model.Tag;
import org.b3log.solo.repository.TagRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Tag query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.3, Dec 17, 2015
 * @since 0.4.0
 */
@Service
public class TagQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TagQueryService.class);

    /**
     * Tag repository.
     */
    @Inject
    private TagRepository tagRepository;

    /**
     * Gets a tag by the specified tag title.
     *
     * @param tagTitle the specified tag title
     * @return for example,      <pre>
     * {
     *     "tag": {
     *         "oId": "",
     *         "tagTitle": "",
     *         "tagReferenceCount": int,
     *         "tagPublishedRefCount": int
     *     }
     * }
     * </pre>, returns {@code null} if not found
     *
     * @throws ServiceException service exception
     */
    public JSONObject getTagByTitle(final String tagTitle) throws ServiceException {
        try {
            final JSONObject ret = new JSONObject();

            final JSONObject tag = tagRepository.getByTitle(tagTitle);

            if (null == tag) {
                return null;
            }

            ret.put(Tag.TAG, tag);

            LOGGER.log(Level.DEBUG, "Got an tag[title={0}]", tagTitle);

            return ret;
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets an article failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets the count of tags.
     *
     * @return count of tags
     * @throws ServiceException service exception
     */
    public long getTagCount() throws ServiceException {
        try {
            return tagRepository.count();
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets tags failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets all tags.
     *
     * @return for example,      <pre>
     * [
     *     {"tagTitle": "", "tagReferenceCount": int, ....},
     *     ....
     * ]
     * </pre>, returns an empty list if not found
     *
     * @throws ServiceException service exception
     */
    public List<JSONObject> getTags() throws ServiceException {
        try {
            final Query query = new Query().setPageCount(1);

            final JSONObject result = tagRepository.get(query);
            final JSONArray tagArray = result.optJSONArray(Keys.RESULTS);

            return CollectionUtils.jsonArrayToList(tagArray);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets tags failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets top (reference count descending) tags.
     *
     * @param fetchSize the specified fetch size
     * @return for example,      <pre>
     * [
     *     {"tagTitle": "", "tagReferenceCount": int, ....},
     *     ....
     * ]
     * </pre>, returns an empty list if not found
     *
     * @throws ServiceException service exception
     */
    public List<JSONObject> getTopTags(final int fetchSize) throws ServiceException {
        try {
            final Query query = new Query().setPageCount(1).setPageSize(fetchSize).
                    addSort(Tag.TAG_PUBLISHED_REFERENCE_COUNT, SortDirection.DESCENDING);

            final JSONObject result = tagRepository.get(query);
            final JSONArray tagArray = result.optJSONArray(Keys.RESULTS);

            return CollectionUtils.jsonArrayToList(tagArray);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets top tags failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets bottom (reference count ascending) tags.
     *
     * @param fetchSize the specified fetch size
     * @return for example,      <pre>
     * [
     *     {"tagTitle": "", "tagReferenceCount": int, ....},
     *     ....
     * ]
     * </pre>, returns an empty list if not found
     *
     * @throws ServiceException service exception
     */
    public List<JSONObject> getBottomTags(final int fetchSize) throws ServiceException {
        try {
            final Query query = new Query().setPageCount(1).setPageSize(fetchSize).
                    addSort(Tag.TAG_PUBLISHED_REFERENCE_COUNT, SortDirection.ASCENDING);

            final JSONObject result = tagRepository.get(query);
            final JSONArray tagArray = result.optJSONArray(Keys.RESULTS);

            return CollectionUtils.jsonArrayToList(tagArray);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets bottom tags failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Removes tags of unpublished articles from the specified tags.
     *
     * @param tags the specified tags
     * @throws JSONException json exception
     * @throws RepositoryException repository exception
     */
    public void removeForUnpublishedArticles(final List<JSONObject> tags) throws JSONException, RepositoryException {
        final Iterator<JSONObject> iterator = tags.iterator();

        while (iterator.hasNext()) {
            final JSONObject tag = iterator.next();

            if (0 == tag.getInt(Tag.TAG_PUBLISHED_REFERENCE_COUNT)) {
                iterator.remove();
            }
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
}
