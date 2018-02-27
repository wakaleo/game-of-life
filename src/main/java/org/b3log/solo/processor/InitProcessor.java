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

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Sessions;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.InitService;
import org.b3log.solo.util.QueryResults;
import org.b3log.solo.util.Thumbnails;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Solo initialization service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.0.11, May 25, 2017
 * @since 0.4.0
 */
@RequestProcessor
public class InitProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(InitProcessor.class);

    /**
     * Initialization service.
     */
    @Inject
    private InitService initService;

    /**
     * Filler.
     */
    @Inject
    private Filler filler;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Shows initialization page.
     *
     * @param context  the specified http request context
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/init", method = HTTPRequestMethod.GET)
    public void showInit(final HTTPRequestContext context, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (initService.isInited()) {
            response.sendRedirect("/");

            return;
        }

        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();

        renderer.setTemplateName("init.ftl");
        context.setRenderer(renderer);

        final Map<String, Object> dataModel = renderer.getDataModel();

        final Map<String, String> langs = langPropsService.getAll(Locales.getLocale(request));

        dataModel.putAll(langs);

        dataModel.put(Common.VERSION, SoloServletListener.VERSION);
        dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
        dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        Keys.fillRuntime(dataModel);
        filler.fillMinified(dataModel);
    }

    /**
     * Initializes Solo.
     *
     * @param context  the specified http request context
     * @param request  the specified http servlet request, for example,      <pre>
     *                 {
     *                     "userName": "",
     *                     "userEmail": "",
     *                     "userPassword": ""
     *                 }
     *                 </pre>
     * @param response the specified http servlet response
     * @throws Exception exception
     */
    @RequestProcessing(value = "/init", method = HTTPRequestMethod.POST)
    public void initSolo(final HTTPRequestContext context, final HttpServletRequest request,
                         final HttpServletResponse response) throws Exception {
        if (initService.isInited()) {
            response.sendRedirect("/");

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        final JSONObject ret = QueryResults.defaultResult();
        renderer.setJSONObject(ret);

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);

            final String userName = requestJSONObject.optString(User.USER_NAME);
            final String userEmail = requestJSONObject.optString(User.USER_EMAIL);
            final String userPassword = requestJSONObject.optString(User.USER_PASSWORD);

            if (Strings.isEmptyOrNull(userName) || Strings.isEmptyOrNull(userEmail) || Strings.isEmptyOrNull(userPassword)
                    || !Strings.isEmail(userEmail)) {
                ret.put(Keys.MSG, "Init failed, please check your input");

                return;
            }

            if (UserExt.invalidUserName(userName)) {
                ret.put(Keys.MSG, "Init failed, please check your username (length [1, 20], content {a-z, A-Z, 0-9}, do not contain 'admin' for security reason]");

                return;
            }

            final Locale locale = Locales.getLocale(request);
            requestJSONObject.put(Keys.LOCALE, locale.toString());

            initService.init(requestJSONObject);

            // If initialized, login the admin
            final JSONObject admin = new JSONObject();
            admin.put(User.USER_NAME, userName);
            admin.put(User.USER_EMAIL, userEmail);
            admin.put(User.USER_ROLE, Role.ADMIN_ROLE);
            admin.put(User.USER_PASSWORD, userPassword);
            admin.put(UserExt.USER_AVATAR, Thumbnails.getGravatarURL(userEmail, "128"));

            Sessions.login(request, response, admin);

            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            ret.put(Keys.MSG, e.getMessage());
        }
    }
}
