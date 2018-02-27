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
package org.b3log.solo.filter;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.DispatcherServlet;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.HttpControl;
import org.b3log.latke.servlet.renderer.HTTP500Renderer;
import org.b3log.solo.service.InitService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks initialization filter.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.1.2, Sep 13, 2016
 * @since 0.3.1
 */
public final class InitCheckFilter implements Filter {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(InitCheckFilter.class);

    /**
     * Whether initialization info reported.
     */
    private static boolean initReported;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    /**
     * If Solo has not been initialized, so redirects to /init.
     *
     * @param request the specified request
     * @param response the specified response
     * @param chain filter chain
     * @throws IOException io exception
     * @throws ServletException servlet exception
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String requestURI = httpServletRequest.getRequestURI();

        LOGGER.log(Level.TRACE, "Request[URI={0}]", requestURI);

        // If requests Latke Remote APIs, skips this filter 
        if (requestURI.startsWith(Latkes.getContextPath() + "/latke/remote")) {
            chain.doFilter(request, response);

            return;
        }

        final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
        final InitService initService = beanManager.getReference(InitService.class);

        if (initService.isInited()) {
            chain.doFilter(request, response);

            return;
        }

        if ("POST".equalsIgnoreCase(httpServletRequest.getMethod()) && (Latkes.getContextPath() + "/init").equals(requestURI)) {
            // Do initailization
            chain.doFilter(request, response);

            return;
        }

        if (!initReported) {
            LOGGER.log(Level.DEBUG, "Solo has not been initialized, so redirects to /init");
            initReported = true;
        }

        final HTTPRequestContext context = new HTTPRequestContext();

        context.setRequest((HttpServletRequest) request);
        context.setResponse((HttpServletResponse) response);

        request.setAttribute(Keys.HttpRequest.REQUEST_URI, Latkes.getContextPath() + "/init");
        request.setAttribute(Keys.HttpRequest.REQUEST_METHOD, HTTPRequestMethod.GET.name());

        final HttpControl httpControl = new HttpControl(DispatcherServlet.SYS_HANDLER.iterator(), context);

        try {
            httpControl.nextHandler();
        } catch (final Exception e) {
            context.setRenderer(new HTTP500Renderer(e));
        }

        DispatcherServlet.result(context);
    }

    @Override
    public void destroy() {
    }
}
