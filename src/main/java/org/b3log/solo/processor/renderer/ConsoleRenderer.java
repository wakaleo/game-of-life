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
package org.b3log.solo.processor.renderer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.solo.SoloServletListener;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * <a href="http://freemarker.org">FreeMarker</a> HTTP response renderer for administrator console and initialization
 * rendering.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.3, Jul 16, 2017
 * @since 0.4.1
 */
public final class ConsoleRenderer extends AbstractFreeMarkerRenderer {

    /**
     * FreeMarker configuration.
     */
    public static final Configuration TEMPLATE_CFG;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ConsoleRenderer.class);

    static {
        TEMPLATE_CFG = new Configuration(Configuration.VERSION_2_3_0);
        TEMPLATE_CFG.setDefaultEncoding("UTF-8");

        final ServletContext servletContext = SoloServletListener.getServletContext();

        TEMPLATE_CFG.setServletContextForTemplateLoading(servletContext, "");
        TEMPLATE_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        TEMPLATE_CFG.setLogTemplateExceptions(false);
    }

    @Override
    protected Template getTemplate(final String templateDirName, final String templateName) {
        try {
            return TEMPLATE_CFG.getTemplate(templateName);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    protected void beforeRender(final HTTPRequestContext context) throws Exception {
    }

    @Override
    protected void afterRender(final HTTPRequestContext context) throws Exception {
    }
}
