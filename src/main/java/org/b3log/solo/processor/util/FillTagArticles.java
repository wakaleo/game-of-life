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
package org.b3log.solo.processor.util;


import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Tag;
import org.b3log.solo.service.ArticleQueryService;
import org.b3log.solo.service.TagQueryService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Fill tag articles.
 *
 * @author <a href="mailto:385321165@qq.com">DASHU</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Apr 12, 2017
 * @since 0.6.1
 */
@Service
public class FillTagArticles implements TemplateMethodModelEx {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FillTagArticles.class);
    /**
     * Arg size.
     */
    private static final int ARG_SIZE = 3;
    /**
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;
    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    @Override
    public Object exec(final List arguments) throws TemplateModelException {
        if (arguments.size() != ARG_SIZE) {
            LOGGER.debug("FillTagArticles with wrong arguments!");

            throw new TemplateModelException("Wrong arguments!");
        }

        final String tagTitle = (String) arguments.get(0);
        final int currentPageNum = Integer.parseInt((String) arguments.get(1));
        final int pageSize = Integer.parseInt((String) arguments.get(2));

        try {
            final JSONObject result = tagQueryService.getTagByTitle(tagTitle);
            if (null == result) {
                return new ArrayList<JSONObject>();
            }

            final JSONObject tag = result.getJSONObject(Tag.TAG);
            final String tagId = tag.getString(Keys.OBJECT_ID);

            final List<JSONObject> ret = articleQueryService.getArticlesByTag(tagId, currentPageNum, pageSize);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fill tag articles failed", e);
        }

        return null;
    }
}
