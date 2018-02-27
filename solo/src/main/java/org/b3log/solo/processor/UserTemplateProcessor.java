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
package org.b3log.solo.processor;


import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.servlet.renderer.freemarker.FreeMarkerRenderer;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.freemarker.Templates;
import org.b3log.solo.model.Option;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.service.StatisticMgmtService;
import org.b3log.solo.util.Skins;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * User template processor.
 *
 * <p>
 * User can add a template (for example "links.ftl") then visits the page ("links.html").
 * </p>
 *
 * <p>
 * See <a href="https://code.google.com/p/b3log-solo/issues/detail?id=409">issue 409</a> for more details.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.5, Nov 20, 2015
 * @since 0.4.5
 */
@RequestProcessor
public class UserTemplateProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleProcessor.class);

    /**
     * Filler.
     */
    @Inject
    private Filler filler;

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
     * Statistic management service.
     */
    @Inject
    private StatisticMgmtService statisticMgmtService;

    /**
     * Shows the user template page.
     *
     * @param context the specified context
     * @param request the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @throws IOException io exception
     */
    @RequestProcessing(value = "/*.html", method = HTTPRequestMethod.GET)
    public void showPage(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
        throws IOException {
        final String requestURI = request.getRequestURI();
        String templateName = StringUtils.substringAfterLast(requestURI, "/");

        templateName = StringUtils.substringBefore(templateName, ".") + ".ftl";
        LOGGER.log(Level.DEBUG, "Shows page[requestURI={0}, templateName={1}]", requestURI, templateName);

        final AbstractFreeMarkerRenderer renderer = new FreeMarkerRenderer();

        context.setRenderer(renderer);
        renderer.setTemplateName(templateName);

        final Map<String, Object> dataModel = renderer.getDataModel();

        final Template template = Templates.getTemplate((String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME), templateName);

        if (null == template) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);

                return;
            } catch (final IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }

        try {
            final Map<String, String> langs = langPropsService.getAll(Locales.getLocale(request));

            dataModel.putAll(langs);
            final JSONObject preference = preferenceQueryService.getPreference();

            filler.fillBlogHeader(request, response, dataModel, preference);
            filler.fillUserTemplate(request, template, dataModel, preference);
            filler.fillBlogFooter(request, dataModel, preference);
            Skins.fillLangs(preference.optString(Option.ID_C_LOCALE_STRING), (String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME), dataModel);

            statisticMgmtService.incBlogViewCount(request, response);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (final IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }
}
