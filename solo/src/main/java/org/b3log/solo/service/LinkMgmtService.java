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
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Link;
import org.b3log.solo.repository.LinkRepository;
import org.json.JSONObject;


/**
 * Link management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Nov 2, 2011
 * @since 0.4.0
 */
@Service
public class LinkMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LinkMgmtService.class);

    /**
     * Link repository.
     */
    @Inject
    private LinkRepository linkRepository;

    /**
     * Removes a link specified by the given link id.
     *
     * @param linkId the given link id
     * @throws ServiceException service exception
     */
    public void removeLink(final String linkId)
        throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            linkRepository.remove(linkId);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Removes a link[id=" + linkId + "] failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates a link by the specified request json object.
     *
     * @param requestJSONObject the specified request json object, for example,
     * <pre>
     * {
     *     "link": {
     *         "oId": "",
     *         "linkTitle": "",
     *         "linkAddress": ""
     *     }
     * }, see {@link Link} for more details
     * </pre>
     * @throws ServiceException service exception
     */
    public void updateLink(final JSONObject requestJSONObject)
        throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject link = requestJSONObject.getJSONObject(Link.LINK);
            final String linkId = link.getString(Keys.OBJECT_ID);
            final JSONObject oldLink = linkRepository.get(linkId);

            link.put(Link.LINK_ORDER, oldLink.getInt(Link.LINK_ORDER));

            linkRepository.update(linkId, link);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, e.getMessage(), e);

            throw new ServiceException(e);
        }
    }

    /**
     * Changes the order of a link specified by the given link id with the 
     * specified direction.
     *
     * @param linkId the given link id
     * @param direction the specified direction, "up"/"down"
     * @throws ServiceException service exception
     */
    public void changeOrder(final String linkId, final String direction)
        throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject srcLink = linkRepository.get(linkId);
            final int srcLinkOrder = srcLink.getInt(Link.LINK_ORDER);

            JSONObject targetLink = null;

            if ("up".equals(direction)) {
                targetLink = linkRepository.getUpper(linkId);
            } else { // Down
                targetLink = linkRepository.getUnder(linkId);
            }

            if (null == targetLink) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                LOGGER.log(Level.WARN, "Cant not find the target link of source link[order={0}]", srcLinkOrder);
                return;
            }

            // Swaps
            srcLink.put(Link.LINK_ORDER, targetLink.getInt(Link.LINK_ORDER));
            targetLink.put(Link.LINK_ORDER, srcLinkOrder);

            linkRepository.update(srcLink.getString(Keys.OBJECT_ID), srcLink);
            linkRepository.update(targetLink.getString(Keys.OBJECT_ID), targetLink);

            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Changes link's order failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Adds a link with the specified request json object.
     * 
     * @param requestJSONObject the specified request json object, for example,
     * <pre>
     * {
     *     "link": {
     *         "linkTitle": "",
     *         "linkAddress": "",
     *         "linkDescription": "" // optional
     *     }
     * }, see {@link Link} for more details
     * </pre>
     * @return generated link id
     * @throws ServiceException service exception
     */
    public String addLink(final JSONObject requestJSONObject)
        throws ServiceException {
        final Transaction transaction = linkRepository.beginTransaction();

        try {
            final JSONObject link = requestJSONObject.getJSONObject(Link.LINK);
            final int maxOrder = linkRepository.getMaxOrder();

            link.put(Link.LINK_ORDER, maxOrder + 1);
            final String ret = linkRepository.add(link);

            transaction.commit();

            return ret;
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Adds a link failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Sets the link repository with the specified link repository.
     * 
     * @param linkRepository the specified link repository
     */
    public void setLinkRepository(final LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }
}
