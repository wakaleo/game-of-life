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
package org.b3log.solo.processor.console;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.latke.util.Requests;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Sign;
import org.b3log.solo.model.Skin;
import org.b3log.solo.service.*;
import org.b3log.solo.util.QueryResults;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Preference console request processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.0.10, May 2, 2017
 * @since 0.4.0
 */
@RequestProcessor
public class PreferenceConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PreferenceConsole.class);

    /**
     * Preference URI prefix.
     */
    private static final String PREFERENCE_URI_PREFIX = "/console/preference/";

    /**
     * Preference query service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Preference management service.
     */
    @Inject
    private PreferenceMgmtService preferenceMgmtService;

    /**
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Gets reply template.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "replyNotificationTemplate": {
     *         "subject": "",
     *         "body": ""
     *     }
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/reply/notification/template", method = HTTPRequestMethod.GET)
    public void getReplyNotificationTemplate(final HttpServletRequest request,
                                             final HttpServletResponse response,
                                             final HTTPRequestContext context) throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject replyNotificationTemplate = preferenceQueryService.getReplyNotificationTemplate();

            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);
            ret.put("replyNotificationTemplate", replyNotificationTemplate);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Updates reply template.
     *
     * @param request  the specified http servlet request, for example,
     *                 "replyNotificationTemplate": {
     *                 "subject": "",
     *                 "body": ""
     *                 }
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/reply/notification/template", method = HTTPRequestMethod.PUT)
    public void updateReplyNotificationTemplate(final HttpServletRequest request,
                                                final HttpServletResponse response,
                                                final HTTPRequestContext context) throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final JSONObject replyNotificationTemplate = requestJSONObject.getJSONObject("replyNotificationTemplate");
            preferenceMgmtService.updateReplyNotificationTemplate(replyNotificationTemplate);

            final JSONObject ret = new JSONObject();
            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
            renderer.setJSONObject(ret);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("updateFailLabel"));
        }
    }

    /**
     * Gets signs.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "signs": [{
     *         "oId": "",
     *         "signHTML": ""
     *      }, ...]
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/signs/", method = HTTPRequestMethod.GET)
    public void getSigns(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isLoggedIn(request, response)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject preference = preferenceQueryService.getPreference();
            final JSONArray signs = new JSONArray();
            final JSONArray allSigns
                    = // includes the empty sign(id=0)
                    new JSONArray(preference.getString(Option.ID_C_SIGNS));

            for (int i = 1; i < allSigns.length(); i++) { // excludes the empty sign
                signs.put(allSigns.getJSONObject(i));
            }

            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);
            ret.put(Sign.SIGNS, signs);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Gets preference.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "preference": {
     *         "mostViewArticleDisplayCount": int,
     *         "recentCommentDisplayCount": int,
     *         "mostUsedTagDisplayCount": int,
     *         "articleListDisplayCount": int,
     *         "articleListPaginationWindowSize": int,
     *         "mostCommentArticleDisplayCount": int,
     *         "externalRelevantArticlesDisplayCount": int,
     *         "relevantArticlesDisplayCount": int,
     *         "randomArticlesDisplayCount": int,
     *         "blogTitle": "",
     *         "blogSubtitle": "",
     *         "localeString": "",
     *         "timeZoneId": "",
     *         "skinName": "",
     *         "skinDirName": "",
     *         "skins": "[{
     *             "skinName": "",
     *             "skinDirName": ""
     *         }, ....]",
     *         "noticeBoard": "",
     *         "footerContent": "",
     *         "htmlHead": "",
     *         "adminEmail": "",
     *         "metaKeywords": "",
     *         "metaDescription": "",
     *         "enableArticleUpdateHint": boolean,
     *         "signs": "[{
     *             "oId": "",
     *             "signHTML": ""
     *         }, ...]",
     *         "allowVisitDraftViaPermalink": boolean,
     *         "allowRegister": boolean,
     *         "version": "",
     *         "articleListStyle": "", // Optional values: "titleOnly"/"titleAndContent"/"titleAndAbstract"
     *         "commentable": boolean,
     *         "feedOutputMode: "" // Optional values: "abstract"/"full"
     *         "feedOutputCnt": int
     *     }
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = PREFERENCE_URI_PREFIX, method = HTTPRequestMethod.GET)
    public void getPreference(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject preference = preferenceQueryService.getPreference();
            if (null == preference) {
                renderer.setJSONObject(QueryResults.defaultResult());

                return;
            }

            String footerContent = "";
            final JSONObject opt = optionQueryService.getOptionById(Option.ID_C_FOOTER_CONTENT);
            if (null != opt) {
                footerContent = opt.optString(Option.OPTION_VALUE);
            }
            preference.put(Option.ID_C_FOOTER_CONTENT, footerContent);

            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);
            ret.put(Option.CATEGORY_C_PREFERENCE, preference);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Updates the preference by the specified request.
     *
     * @param request  the specified http servlet request, for example,
     *                 "preference": {
     *                 "mostViewArticleDisplayCount": int,
     *                 "recentCommentDisplayCount": int,
     *                 "mostUsedTagDisplayCount": int,
     *                 "articleListDisplayCount": int,
     *                 "articleListPaginationWindowSize": int,
     *                 "mostCommentArticleDisplayCount": int,
     *                 "externalRelevantArticlesDisplayCount": int,
     *                 "relevantArticlesDisplayCount": int,
     *                 "randomArticlesDisplayCount": int,
     *                 "blogTitle": "",
     *                 "blogSubtitle": "",
     *                 "skinDirName": "",
     *                 "localeString": "",
     *                 "timeZoneId": "",
     *                 "noticeBoard": "",
     *                 "footerContent": "",
     *                 "htmlHead": "",
     *                 "metaKeywords": "",
     *                 "metaDescription": "",
     *                 "enableArticleUpdateHint": boolean,
     *                 "signs": [{
     *                 "oId": "",
     *                 "signHTML": ""
     *                 }, ...],
     *                 "allowVisitDraftViaPermalink": boolean,
     *                 "allowRegister": boolean,
     *                 "articleListStyle": "",
     *                 "editorType": "",
     *                 "commentable": boolean,
     *                 "feedOutputMode: "",
     *                 "feedOutputCnt": int
     *                 }
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = PREFERENCE_URI_PREFIX, method = HTTPRequestMethod.PUT)
    public void updatePreference(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);
            final JSONObject preference = requestJSONObject.getJSONObject(Option.CATEGORY_C_PREFERENCE);
            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);
            if (isInvalid(preference, ret)) {
                return;
            }

            preferenceMgmtService.updatePreference(preference);

            final Cookie cookie = new Cookie(Skin.SKIN, preference.getString(Skin.SKIN_DIR_NAME));
            cookie.setMaxAge(60 * 60); // 1 hour
            cookie.setPath("/");
            response.addCookie(cookie);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }

    /**
     * Gets Qiniu preference.
     * <p>
     * Renders the response with a json object, for example,
     * <pre>
     * {
     *     "sc": boolean,
     *     "qiniuAccessKey": "",
     *     "qiniuSecretKey": "",
     *     "qiniuDomain": "",
     *     "qiniuBucket": ""
     * }
     * </pre>
     * </p>
     *
     * @param request  the specified http servlet request
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = PREFERENCE_URI_PREFIX + "qiniu", method = HTTPRequestMethod.GET)
    public void getQiniuPreference(final HttpServletRequest request, final HttpServletResponse response,
                                   final HTTPRequestContext context) throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject qiniu = optionQueryService.getOptions(Option.CATEGORY_C_QINIU);
            if (null == qiniu) {
                renderer.setJSONObject(QueryResults.defaultResult());

                return;
            }

            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);
            ret.put(Option.CATEGORY_C_QINIU, qiniu);
            ret.put(Keys.STATUS_CODE, true);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();
            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, langPropsService.get("getFailLabel"));
        }
    }

    /**
     * Updates the Qiniu preference by the specified request.
     *
     * @param request  the specified http servlet request, for example,
     *                 "qiniuAccessKey": "",
     *                 "qiniuSecretKey": "",
     *                 "qiniuDomain": "",
     *                 "qiniuBucket": ""
     * @param response the specified http servlet response
     * @param context  the specified http request context
     * @throws Exception exception
     */
    @RequestProcessing(value = PREFERENCE_URI_PREFIX + "qiniu", method = HTTPRequestMethod.PUT)
    public void updateQiniu(final HttpServletRequest request, final HttpServletResponse response,
                            final HTTPRequestContext context) throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final JSONRenderer renderer = new JSONRenderer();
        context.setRenderer(renderer);

        try {
            final JSONObject requestJSONObject = Requests.parseRequestJSONObject(request, response);

            final String accessKey = requestJSONObject.optString(Option.ID_C_QINIU_ACCESS_KEY).trim();
            final String secretKey = requestJSONObject.optString(Option.ID_C_QINIU_SECRET_KEY).trim();
            String domain = requestJSONObject.optString(Option.ID_C_QINIU_DOMAIN).trim();
            final String bucket = requestJSONObject.optString(Option.ID_C_QINIU_BUCKET).trim();

            final JSONObject ret = new JSONObject();
            renderer.setJSONObject(ret);

            if (StringUtils.isNotBlank(domain) && !StringUtils.endsWith(domain, "/")) {
                domain += "/";
            }

            final JSONObject accessKeyOpt = new JSONObject();
            accessKeyOpt.put(Keys.OBJECT_ID, Option.ID_C_QINIU_ACCESS_KEY);
            accessKeyOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_QINIU);
            accessKeyOpt.put(Option.OPTION_VALUE, accessKey);
            final JSONObject secretKeyOpt = new JSONObject();
            secretKeyOpt.put(Keys.OBJECT_ID, Option.ID_C_QINIU_SECRET_KEY);
            secretKeyOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_QINIU);
            secretKeyOpt.put(Option.OPTION_VALUE, secretKey);
            final JSONObject domainOpt = new JSONObject();
            domainOpt.put(Keys.OBJECT_ID, Option.ID_C_QINIU_DOMAIN);
            domainOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_QINIU);
            domainOpt.put(Option.OPTION_VALUE, domain);
            final JSONObject bucketOpt = new JSONObject();
            bucketOpt.put(Keys.OBJECT_ID, Option.ID_C_QINIU_BUCKET);
            bucketOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_QINIU);
            bucketOpt.put(Option.OPTION_VALUE, bucket);

            optionMgmtService.addOrUpdateOption(accessKeyOpt);
            optionMgmtService.addOrUpdateOption(secretKeyOpt);
            optionMgmtService.addOrUpdateOption(domainOpt);
            optionMgmtService.addOrUpdateOption(bucketOpt);

            ret.put(Keys.STATUS_CODE, true);
            ret.put(Keys.MSG, langPropsService.get("updateSuccLabel"));
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            final JSONObject jsonObject = QueryResults.defaultResult();

            renderer.setJSONObject(jsonObject);
            jsonObject.put(Keys.MSG, e.getMessage());
        }
    }

    /**
     * Checks whether the specified preference is invalid and sets the specified response object.
     *
     * @param preference     the specified preference
     * @param responseObject the specified response object
     * @return {@code true} if the specified preference is invalid, returns {@code false} otherwise
     */
    private boolean isInvalid(final JSONObject preference, final JSONObject responseObject) {
        responseObject.put(Keys.STATUS_CODE, false);

        final StringBuilder errMsgBuilder = new StringBuilder('[' + langPropsService.get("paramSettingsLabel"));
        errMsgBuilder.append(" - ");

        String input = preference.optString(Option.ID_C_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("externalRelevantArticlesDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("relevantArticlesDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("randomArticlesDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("indexMostCommentArticleDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("indexMostViewArticleDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_RECENT_COMMENT_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("indexRecentCommentDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_MOST_USED_TAG_DISPLAY_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("indexTagDisplayCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("pageSizeLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("windowSizeLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        input = preference.optString(Option.ID_C_FEED_OUTPUT_CNT);
        if (!isNonNegativeInteger(input)) {
            errMsgBuilder.append(langPropsService.get("feedOutputCntLabel")).append("]  ").append(
                    langPropsService.get("nonNegativeIntegerOnlyLabel"));
            responseObject.put(Keys.MSG, errMsgBuilder.toString());
            return true;
        }

        return false;
    }

    /**
     * Checks whether the specified input is a non-negative integer.
     *
     * @param input the specified input
     * @return {@code true} if it is, returns {@code false} otherwise
     */
    private boolean isNonNegativeInteger(final String input) {
        try {
            return 0 <= Integer.valueOf(input);
        } catch (final Exception e) {
            return false;
        }
    }
}
