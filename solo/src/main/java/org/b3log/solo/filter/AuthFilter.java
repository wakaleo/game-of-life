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


import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.solo.service.UserMgmtService;
import org.b3log.solo.service.UserQueryService;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Authentication filter.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.5, Sep 21, 2017
 * @since 0.3.1
 */
public final class AuthFilter implements Filter {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    /**
     * If the specified request is NOT made by an authenticated user, sends error 403.
     *
     * @param request  the specified request
     * @param response the specified response
     * @param chain    filter chain
     * @throws IOException      io exception
     * @throws ServletException servlet exception
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
        final UserMgmtService userMgmtService = beanManager.getReference(UserMgmtService.class);
        final UserQueryService userQueryService = beanManager.getReference(UserQueryService.class);

        try {
            userMgmtService.tryLogInWithCookie(httpServletRequest, httpServletResponse);

            final JSONObject currentUser = userQueryService.getCurrentUser(httpServletRequest);
            if (null == currentUser) {
                LOGGER.debug("The request has been forbidden");
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);

                return;
            }

            final String userRole = currentUser.optString(User.USER_ROLE);
            if (Role.VISITOR_ROLE.equals(userRole)) {
                LOGGER.warn("The request [Visitor] has been forbidden");
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);

                return;
            }

            chain.doFilter(request, response);
        } catch (final IOException e) {
            LOGGER.log(Level.ERROR, "Auth filter failed", e);

            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void destroy() {
    }
}
