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
import org.b3log.latke.mail.MailService;
import org.b3log.latke.mail.MailServiceFactory;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.user.UserService;
import org.b3log.latke.user.UserServiceFactory;
import org.b3log.latke.util.MD5;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Sessions;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Mails;
import org.b3log.solo.util.Randoms;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

/**
 * Login/logout processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="mailto:dongxu.wang@acm.org">Dongxu Wang</a>
 * @author <a href="https://github.com/nanolikeyou">nanolikeyou</a>
 * @version 1.1.1.10, Sep 21, 2017
 * @since 0.3.1
 */
@RequestProcessor
public class LoginProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(LoginProcessor.class);

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * User service.
     */
    private UserService userService = UserServiceFactory.getUserService();

    /**
     * Mail service.
     */
    private MailService mailService = MailServiceFactory.getMailService();

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

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
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Shows login page.
     *
     * @param context the specified context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/login", method = HTTPRequestMethod.GET)
    public void showLogin(final HTTPRequestContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        String destinationURL = request.getParameter(Common.GOTO);
        if (Strings.isEmptyOrNull(destinationURL)) {
            destinationURL = Latkes.getServePath() + Common.ADMIN_INDEX_URI;
        } else if (!isInternalLinks(destinationURL)) {
            destinationURL = "/";
        }

        final HttpServletResponse response = context.getResponse();

        userMgmtService.tryLogInWithCookie(request, response);

        if (null != userService.getCurrentUser(request)) { // User has already logged in
            response.sendRedirect(destinationURL);

            return;
        }

        renderPage(context, "login.ftl", destinationURL, request);
    }

    /**
     * Logins.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "isLoggedIn": boolean,
     *     "msg": "" // optional, exists if isLoggedIn equals to false
     * }
     * </pre>
     * </p>
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/login", method = HTTPRequestMethod.POST)
    public void login(final HTTPRequestContext context) {
        final HttpServletRequest request = context.getRequest();

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();
        renderer.setJSONObject(jsonObject);

        try {
            jsonObject.put(Common.IS_LOGGED_IN, false);
            final String loginFailLabel = langPropsService.get("loginFailLabel");

            jsonObject.put(Keys.MSG, loginFailLabel);

            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, context.getResponse());
            final String userEmail = requestJSONObject.getString(User.USER_EMAIL);
            final String userPwd = requestJSONObject.getString(User.USER_PASSWORD);

            if (Strings.isEmptyOrNull(userEmail) || Strings.isEmptyOrNull(userPwd)) {
                return;
            }

            final JSONObject user = userQueryService.getUserByEmail(userEmail);
            if (null == user) {
                LOGGER.log(Level.WARN, "Not found user[email={0}]", userEmail);
                return;
            }

            if (MD5.hash(userPwd).equals(user.getString(User.USER_PASSWORD))) {
                Sessions.login(request, context.getResponse(), user);

                LOGGER.log(Level.INFO, "Logged in[email={0}]", userEmail);

                jsonObject.put(Common.IS_LOGGED_IN, true);

                if (Role.VISITOR_ROLE.equals(user.optString(User.USER_ROLE))) {
                    jsonObject.put("to", Latkes.getServePath());
                } else {
                    jsonObject.put("to", Latkes.getServePath() + Common.ADMIN_INDEX_URI);
                }

                jsonObject.remove(Keys.MSG);

                return;
            }

            LOGGER.log(Level.WARN, "Wrong password[{0}]", userPwd);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Logout.
     *
     * @param context the specified context
     * @throws IOException io exception
     */
    @RequestProcessing(value = "/logout", method = HTTPRequestMethod.GET)
    public void logout(final HTTPRequestContext context) throws IOException {
        final HttpServletRequest httpServletRequest = context.getRequest();

        Sessions.logout(httpServletRequest, context.getResponse());

        String destinationURL = httpServletRequest.getParameter(Common.GOTO);

        if (Strings.isEmptyOrNull(destinationURL) || !isInternalLinks(destinationURL)) {
            destinationURL = "/";
        }

        context.getResponse().sendRedirect(destinationURL);
    }

    /**
     * Shows forgotten password page.
     *
     * @param context the specified context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/forgot", method = HTTPRequestMethod.GET)
    public void showForgot(final HTTPRequestContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        String destinationURL = request.getParameter(Common.GOTO);

        if (Strings.isEmptyOrNull(destinationURL)) {
            destinationURL = Latkes.getServePath() + Common.ADMIN_INDEX_URI;
        } else if (!isInternalLinks(destinationURL)) {
            destinationURL = "/";
        }

        renderPage(context, "reset-pwd.ftl", destinationURL, request);
    }

    /**
     * Resets forgotten password.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "isLoggedIn": boolean,
     *     "msg": "" // optional, exists if isLoggedIn equals to false
     * }
     * </pre>
     * </p>
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/forgot", method = HTTPRequestMethod.POST)
    public void forgot(final HTTPRequestContext context) {
        final HttpServletRequest request = context.getRequest();

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();
        renderer.setJSONObject(jsonObject);

        try {
            jsonObject.put("succeed", false);
            jsonObject.put(Keys.MSG, langPropsService.get("resetPwdSuccessMsg"));

            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, context.getResponse());
            final String userEmail = requestJSONObject.getString(User.USER_EMAIL);

            if (Strings.isEmptyOrNull(userEmail)) {
                LOGGER.log(Level.WARN, "Why user's email is empty");
                return;
            }

            LOGGER.log(Level.INFO, "Login[email={0}]", userEmail);

            final JSONObject user = userQueryService.getUserByEmail(userEmail);
            if (null == user) {
                LOGGER.log(Level.WARN, "Not found user[email={0}]", userEmail);
                jsonObject.put(Keys.MSG, langPropsService.get("userEmailNotFoundMsg"));

                return;
            }

            sendResetUrl(userEmail, jsonObject);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Resets forgotten password.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "isLoggedIn": boolean,
     *     "msg": "" // optional, exists if isLoggedIn equals to false
     * }
     * </pre>
     * </p>
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/reset", method = HTTPRequestMethod.POST)
    public void reset(final HTTPRequestContext context) {
        final HttpServletRequest request = context.getRequest();
        final JSONRenderer renderer = new JSONRenderer();

        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();

        renderer.setJSONObject(jsonObject);

        try {
            final JSONObject requestJSONObject;

            requestJSONObject = Requests.parseRequestJSONObject(request, context.getResponse());
            final String token = requestJSONObject.getString("token");
            final String newPwd = requestJSONObject.getString("newPwd");
            final JSONObject passwordResetOption = optionQueryService.getOptionById(token);

            if (null == passwordResetOption) {
                LOGGER.log(Level.WARN, "Not found user by that token:[{0}]", token);
                jsonObject.put("succeed", true);
                jsonObject.put("to", Latkes.getServePath() + "/login?from=reset");
                jsonObject.put(Keys.MSG, langPropsService.get("resetPwdFailedMsg"));
                return;
            }
            final String userEmail = passwordResetOption.getString(Option.OPTION_VALUE);
            final JSONObject user = userQueryService.getUserByEmail(userEmail);

            user.put(User.USER_PASSWORD, newPwd);
            userMgmtService.updateUser(user);
            // TODO delete expired token
            LOGGER.log(Level.DEBUG, "[{0}]'s password updated successfully.", userEmail);

            jsonObject.put("succeed", true);
            jsonObject.put("to", Latkes.getServePath() + "/login?from=reset");
            jsonObject.put(Keys.MSG, langPropsService.get("resetPwdSuccessMsg"));
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Whether user is going to update an expired password out of 24 hours.
     *
     * @return whether the password has been expired TODO implement it
     */
    private boolean isPwdExpired() {
        return false;
    }

    /**
     * Sends the password resetting URL with a random token.
     *
     * @param userEmail  the given email
     * @param jsonObject return code and message object
     * @throws JSONException       the JSONException
     * @throws ServiceException    the ServiceException
     * @throws IOException         the IOException
     * @throws RepositoryException the RepositoryException
     */
    private void sendResetUrl(final String userEmail, final JSONObject jsonObject) throws JSONException,
            ServiceException, IOException, RepositoryException {
        final JSONObject preference = preferenceQueryService.getPreference();
        final String token = new Randoms().nextStringWithMD5();
        final String adminEmail = preference.getString(Option.ID_C_ADMIN_EMAIL);
        final String mailSubject = langPropsService.get("resetPwdMailSubject");
        final String mailBody = langPropsService.get("resetPwdMailBody") + " " + Latkes.getServePath()
                + "/forgot?token=" + token;
        final MailService.Message message = new MailService.Message();

        final JSONObject option = new JSONObject();

        option.put(Keys.OBJECT_ID, token);
        option.put(Option.OPTION_CATEGORY, "passwordReset");
        option.put(Option.OPTION_VALUE, userEmail);
        final Transaction transaction = optionRepository.beginTransaction();
        optionRepository.add(option);
        transaction.commit();

        message.setFrom(adminEmail);
        message.addRecipient(userEmail);
        message.setSubject(mailSubject);
        message.setHtmlBody(mailBody);

        if (Mails.isConfigured()) {
            mailService.send(message);
        } else {
            LOGGER.log(Level.INFO, "Do not send mail caused by not configure mail.properties");
        }

        jsonObject.put("succeed", true);
        jsonObject.put("to", Latkes.getServePath() + "/login?from=forgot");
        jsonObject.put(Keys.MSG, langPropsService.get("resetPwdSuccessSend"));

        LOGGER.log(Level.DEBUG, "Sent a mail[mailSubject={0}, mailBody=[{1}] to [{2}]", mailSubject, mailBody, userEmail);
    }

    /**
     * Render a page template with the destination URL.
     *
     * @param context        the context
     * @param pageTemplate   the page template
     * @param destinationURL the destination URL
     * @param request        for reset password page
     * @throws JSONException    the JSONException
     * @throws ServiceException the ServiceException
     */
    private void renderPage(final HTTPRequestContext context, final String pageTemplate, final String destinationURL,
                            final HttpServletRequest request) throws JSONException, ServiceException {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();

        renderer.setTemplateName(pageTemplate);
        context.setRenderer(renderer);

        final Map<String, Object> dataModel = renderer.getDataModel();
        final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
        final JSONObject preference = preferenceQueryService.getPreference();

        dataModel.putAll(langs);
        dataModel.put(Common.GOTO, destinationURL);
        dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        dataModel.put(Common.VERSION, SoloServletListener.VERSION);
        dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
        dataModel.put(Option.ID_C_BLOG_TITLE, preference.getString(Option.ID_C_BLOG_TITLE));

        final String token = request.getParameter("token");
        final JSONObject tokenObj = optionQueryService.getOptionById(token);

        if (tokenObj == null) {
            dataModel.put("inputType", "email");
        } else {
            // TODO verify the expired time in the tokenObj
            dataModel.put("inputType", "password");
            dataModel.put("tokenHidden", token);
        }

        final String from = request.getParameter("from");

        if ("forgot".equals(from)) {
            dataModel.put("resetMsg", langPropsService.get("resetPwdSuccessSend"));
        } else if ("reset".equals(from)) {
            dataModel.put("resetMsg", langPropsService.get("resetPwdSuccessMsg"));
        } else {
            dataModel.put("resetMsg", "");
        }

        Keys.fillRuntime(dataModel);
        filler.fillMinified(dataModel);
    }

    /**
     * Preventing unvalidated redirects and forwards. See more at:
     * <a href="https://www.owasp.org/index.php/Unvalidated_Redirects_and_Forwards_Cheat_Sheet">https://www.owasp.org/index.php/
     * Unvalidated_Redirects_and_Forwards_Cheat_Sheet</a>.
     *
     * @return whether the destinationURL is an internal link
     */
    private boolean isInternalLinks(String destinationURL) {
        return destinationURL.startsWith(Latkes.getServePath());
    }
}
