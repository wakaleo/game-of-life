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

import com.qiniu.util.Auth;
import jodd.io.ZipUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Plugin;
import org.b3log.latke.model.User;
import org.b3log.latke.plugin.ViewLoadEventData;
import org.b3log.latke.repository.jdbc.util.Connections;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.freemarker.AbstractFreeMarkerRenderer;
import org.b3log.latke.util.Execs;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Skin;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.processor.renderer.ConsoleRenderer;
import org.b3log.solo.processor.util.Filler;
import org.b3log.solo.service.ExportService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.service.PreferenceQueryService;
import org.b3log.solo.service.UserQueryService;
import org.b3log.solo.util.Thumbnails;
import org.json.JSONObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Admin console render processing.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.7.0.1, Nov 20, 2017
 * @since 0.4.1
 */
@RequestProcessor
public class AdminConsole {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AdminConsole.class);

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

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
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Export service.
     */
    @Inject
    private ExportService exportService;

    /**
     * Filler.
     */
    @Inject
    private Filler filler;

    /**
     * Event manager.
     */
    @Inject
    private EventManager eventManager;

    private static String sanitizeFilename(String unsanitized) {
        return unsanitized
                .replaceAll("[\\?\\\\/:|<>\\*]", " ") // filter out ? \ / : | < > *
                .replaceAll("\\s+", "_");              // white space as underscores
    }

    /**
     * Shows administrator index with the specified context.
     *
     * @param request the specified request
     * @param context the specified context
     */
    @RequestProcessing(value = "/admin-index.do", method = HTTPRequestMethod.GET)
    public void showAdminIndex(final HttpServletRequest request, final HTTPRequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();

        context.setRenderer(renderer);
        final String templateName = "admin-index.ftl";

        renderer.setTemplateName(templateName);

        final Map<String, String> langs = langPropsService.getAll(Latkes.getLocale());
        final Map<String, Object> dataModel = renderer.getDataModel();

        dataModel.putAll(langs);

        final JSONObject currentUser = userQueryService.getCurrentUser(request);
        final String userName = currentUser.optString(User.USER_NAME);
        dataModel.put(User.USER_NAME, userName);

        final String roleName = currentUser.optString(User.USER_ROLE);
        dataModel.put(User.USER_ROLE, roleName);

        final String email = currentUser.optString(User.USER_EMAIL);

        final String userAvatar = currentUser.optString(UserExt.USER_AVATAR);
        if (!Strings.isEmptyOrNull(userAvatar)) {
            dataModel.put(Common.GRAVATAR, userAvatar);
        } else {
            final String gravatar = Thumbnails.getGravatarURL(email, "128");
            dataModel.put(Common.GRAVATAR, gravatar);
        }

        try {
            final JSONObject qiniu = optionQueryService.getOptions(Option.CATEGORY_C_QINIU);

            dataModel.put(Option.ID_C_QINIU_DOMAIN, "");
            dataModel.put("qiniuUploadToken", "");

            if (null != qiniu && StringUtils.isNotBlank(qiniu.optString(Option.ID_C_QINIU_ACCESS_KEY))
                    && StringUtils.isNotBlank(qiniu.optString(Option.ID_C_QINIU_SECRET_KEY))
                    && StringUtils.isNotBlank(qiniu.optString(Option.ID_C_QINIU_BUCKET))
                    && StringUtils.isNotBlank(qiniu.optString(Option.ID_C_QINIU_DOMAIN))) {
                try {
                    final Auth auth = Auth.create(qiniu.optString(Option.ID_C_QINIU_ACCESS_KEY),
                            qiniu.optString(Option.ID_C_QINIU_SECRET_KEY));

                    final String uploadToken = auth.uploadToken(qiniu.optString(Option.ID_C_QINIU_BUCKET),
                            null, 3600 * 6, null);
                    dataModel.put("qiniuUploadToken", uploadToken);
                    dataModel.put(Option.ID_C_QINIU_DOMAIN, qiniu.optString(Option.ID_C_QINIU_DOMAIN));
                } catch (final Exception e) {
                    LOGGER.log(Level.ERROR, "Qiniu settings error", e);
                }
            }

            final JSONObject preference = preferenceQueryService.getPreference();

            dataModel.put(Option.ID_C_LOCALE_STRING, preference.getString(Option.ID_C_LOCALE_STRING));
            dataModel.put(Option.ID_C_BLOG_TITLE, preference.getString(Option.ID_C_BLOG_TITLE));
            dataModel.put(Option.ID_C_BLOG_SUBTITLE, preference.getString(Option.ID_C_BLOG_SUBTITLE));
            dataModel.put(Common.VERSION, SoloServletListener.VERSION);
            dataModel.put(Common.STATIC_RESOURCE_VERSION, Latkes.getStaticResourceVersion());
            dataModel.put(Common.YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            dataModel.put(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT, preference.getInt(Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT));
            dataModel.put(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE, preference.getInt(Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE));
            dataModel.put(Option.ID_C_LOCALE_STRING, preference.getString(Option.ID_C_LOCALE_STRING));
            dataModel.put(Option.ID_C_EDITOR_TYPE, preference.getString(Option.ID_C_EDITOR_TYPE));
            dataModel.put(Skin.SKIN_DIR_NAME, preference.getString(Skin.SKIN_DIR_NAME));

            Keys.fillRuntime(dataModel);
            filler.fillMinified(dataModel);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Admin index render failed", e);
        }

        fireFreeMarkerActionEvent(templateName, dataModel);
    }

    /**
     * Shows administrator functions with the specified context.
     *
     * @param request the specified request
     * @param context the specified context
     */
    @RequestProcessing(value = {"/admin-article.do",
            "/admin-article-list.do",
            "/admin-comment-list.do",
            "/admin-link-list.do",
            "/admin-page-list.do",
            "/admin-others.do",
            "/admin-draft-list.do",
            "/admin-user-list.do",
            "/admin-category-list.do",
            "/admin-plugin-list.do",
            "/admin-main.do",
            "/admin-about.do"},
            method = HTTPRequestMethod.GET)
    public void showAdminFunctions(final HttpServletRequest request, final HTTPRequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();
        context.setRenderer(renderer);

        final String requestURI = request.getRequestURI();
        final String templateName = StringUtils.substringBetween(requestURI, Latkes.getContextPath() + '/', ".") + ".ftl";

        LOGGER.log(Level.TRACE, "Admin function[templateName={0}]", templateName);
        renderer.setTemplateName(templateName);

        final Locale locale = Latkes.getLocale();
        final Map<String, String> langs = langPropsService.getAll(locale);
        final Map<String, Object> dataModel = renderer.getDataModel();

        dataModel.put("supportExport", Latkes.RuntimeDatabase.MYSQL == Latkes.getRuntimeDatabase()
                || Latkes.RuntimeDatabase.H2 == Latkes.getRuntimeDatabase());
        dataModel.putAll(langs);
        Keys.fillRuntime(dataModel);
        dataModel.put(Option.ID_C_LOCALE_STRING, locale.toString());

        fireFreeMarkerActionEvent(templateName, dataModel);
    }

    /**
     * Shows administrator preference function with the specified context.
     *
     * @param request the specified request
     * @param context the specified context
     */
    @RequestProcessing(value = "/admin-preference.do", method = HTTPRequestMethod.GET)
    public void showAdminPreferenceFunction(final HttpServletRequest request, final HTTPRequestContext context) {
        final AbstractFreeMarkerRenderer renderer = new ConsoleRenderer();

        context.setRenderer(renderer);

        final String templateName = "admin-preference.ftl";

        renderer.setTemplateName(templateName);

        final Locale locale = Latkes.getLocale();
        final Map<String, String> langs = langPropsService.getAll(locale);
        final Map<String, Object> dataModel = renderer.getDataModel();

        dataModel.putAll(langs);
        dataModel.put(Option.ID_C_LOCALE_STRING, locale.toString());

        JSONObject preference = null;

        try {
            preference = preferenceQueryService.getPreference();
        } catch (final ServiceException e) {
            LOGGER.log(Level.ERROR, "Loads preference failed", e);
        }

        final StringBuilder timeZoneIdOptions = new StringBuilder();
        final String[] availableIDs = TimeZone.getAvailableIDs();
        for (int i = 0; i < availableIDs.length; i++) {
            final String id = availableIDs[i];
            String option;

            if (id.equals(preference.optString(Option.ID_C_TIME_ZONE_ID))) {
                option = "<option value=\"" + id + "\" selected=\"true\">" + id + "</option>";
            } else {
                option = "<option value=\"" + id + "\">" + id + "</option>";
            }

            timeZoneIdOptions.append(option);
        }

        dataModel.put("timeZoneIdOptions", timeZoneIdOptions.toString());

        fireFreeMarkerActionEvent(templateName, dataModel);
    }

    /**
     * Exports data as SQL zip file.
     *
     * @param request  the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @param context  the specified HTTP request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/export/sql", method = HTTPRequestMethod.GET)
    public void exportSQL(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        Thread.sleep(550); // 前端会发两次请求，文件名又是按秒生成，所以两次请求需要错开至少 1 秒避免文件名冲突

        final Latkes.RuntimeDatabase runtimeDatabase = Latkes.getRuntimeDatabase();
        if (Latkes.RuntimeDatabase.H2 != runtimeDatabase && Latkes.RuntimeDatabase.MYSQL != runtimeDatabase) {
            context.renderJSON().renderMsg("Just support MySQL/H2 export now");

            return;
        }

        final String dbUser = Latkes.getLocalProperty("jdbc.username");
        final String dbPwd = Latkes.getLocalProperty("jdbc.password");
        final String dbURL = Latkes.getLocalProperty("jdbc.URL");
        String sql; // exported SQL script

        if (Latkes.RuntimeDatabase.MYSQL == runtimeDatabase) {
            String db = StringUtils.substringAfterLast(dbURL, "/");
            db = StringUtils.substringBefore(db, "?");

            try {
                if (StringUtils.isNotBlank(dbPwd)) {
                    sql = Execs.exec("mysqldump -u" + dbUser + " -p" + dbPwd + " --databases " + db);
                } else {
                    sql = Execs.exec("mysqldump -u" + dbUser + " --databases " + db);
                }
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Export failed", e);
                context.renderJSON().renderMsg("Export failed, please check log");

                return;
            }
        } else if (Latkes.RuntimeDatabase.H2 == runtimeDatabase) {
            final Connection connection = Connections.getConnection();
            final Statement statement = connection.createStatement();

            try {
                final StringBuilder sqlBuilder = new StringBuilder();
                final ResultSet resultSet = statement.executeQuery("SCRIPT");
                while (resultSet.next()) {
                    final String stmt = resultSet.getString(1);
                    sqlBuilder.append(stmt).append(Strings.LINE_SEPARATOR);
                }
                resultSet.close();
                statement.close();

                sql = sqlBuilder.toString();
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Export failed", e);
                context.renderJSON().renderMsg("Export failed, please check log");

                return;
            } finally {
                if (null != connection) {
                    connection.close();
                }
            }
        } else {
            context.renderJSON().renderMsg("Just support MySQL/H2 export now");

            return;
        }

        if (StringUtils.isBlank(sql)) {
            context.renderJSON().renderMsg("Export failed, please check log");

            return;
        }

        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String localFilePath = tmpDir + File.separator + "solo-" + date + ".sql";
        LOGGER.trace(localFilePath);
        final File localFile = new File(localFilePath);

        try {
            final byte[] data = sql.getBytes("UTF-8");

            final OutputStream output = new FileOutputStream(localFile);
            IOUtils.write(data, output);
            IOUtils.closeQuietly(output);

            final File zipFile = ZipUtil.zip(localFile);

            final FileInputStream inputStream = new FileInputStream(zipFile);
            final byte[] zipData = IOUtils.toByteArray(inputStream);
            IOUtils.closeQuietly(inputStream);

            response.setContentType("application/zip");
            final String fileName = "solo-sql-" + date + ".zip";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            final ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(zipData);
            outputStream.flush();
            outputStream.close();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Export failed", e);
            context.renderJSON().renderMsg("Export failed, please check log");

            return;
        }
    }

    /**
     * Exports data as JSON zip file.
     *
     * @param request  the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @param context  the specified HTTP request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/export/json", method = HTTPRequestMethod.GET)
    public void exportJSON(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        Thread.sleep(550);

        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String localFilePath = tmpDir + File.separator + "solo-" + date + ".json";
        LOGGER.trace(localFilePath);
        final File localFile = new File(localFilePath);

        try {
            final JSONObject json = exportService.getJSONs();
            final byte[] data = json.toString(4).getBytes("UTF-8");

            final OutputStream output = new FileOutputStream(localFile);
            IOUtils.write(data, output);
            IOUtils.closeQuietly(output);

            final File zipFile = ZipUtil.zip(localFile);

            final FileInputStream inputStream = new FileInputStream(zipFile);
            final byte[] zipData = IOUtils.toByteArray(inputStream);
            IOUtils.closeQuietly(inputStream);

            response.setContentType("application/zip");
            final String fileName = "solo-json-" + date + ".zip";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            final ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(zipData);
            outputStream.flush();
            outputStream.close();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Export failed", e);
            context.renderJSON().renderMsg("Export failed, please check log");

            return;
        }
    }

    /**
     * Exports data as Hexo markdown zip file.
     *
     * @param request  the specified HTTP servlet request
     * @param response the specified HTTP servlet response
     * @param context  the specified HTTP request context
     * @throws Exception exception
     */
    @RequestProcessing(value = "/console/export/hexo", method = HTTPRequestMethod.GET)
    public void exportHexo(final HttpServletRequest request, final HttpServletResponse response, final HTTPRequestContext context)
            throws Exception {
        if (!userQueryService.isAdminLoggedIn(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        Thread.sleep(550);

        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String localFilePath = tmpDir + File.separator + "solo-hexo-" + date;
        LOGGER.trace(localFilePath);
        final File localFile = new File(localFilePath);

        final File postDir = new File(localFilePath + File.separator + "posts");
        final File passwordDir = new File(localFilePath + File.separator + "passwords");
        final File draftDir = new File(localFilePath + File.separator + "drafts");

        try {
            if (!postDir.mkdirs()) {
                throw new Exception("Create dir [" + postDir.getPath() + "] failed");
            }
            if (!passwordDir.mkdirs()) {
                throw new Exception("Create dir [" + passwordDir.getPath() + "] failed");
            }
            if (!draftDir.mkdirs()) {
                throw new Exception("Create dir [" + draftDir.getPath() + "] failed");
            }

            final JSONObject result = exportService.exportHexoMDs();
            final List<JSONObject> posts = (List<JSONObject>) result.opt("posts");
            exportHexoMd(posts, postDir.getPath());
            final List<JSONObject> passwords = (List<JSONObject>) result.opt("passwords");
            exportHexoMd(passwords, passwordDir.getPath());
            final List<JSONObject> drafts = (List<JSONObject>) result.opt("drafts");
            exportHexoMd(drafts, draftDir.getPath());

            final File zipFile = ZipUtil.zip(localFile);

            final FileInputStream inputStream = new FileInputStream(zipFile);
            final byte[] zipData = IOUtils.toByteArray(inputStream);
            IOUtils.closeQuietly(inputStream);

            response.setContentType("application/zip");
            final String fileName = "solo-hexo-" + date + ".zip";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            final ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(zipData);
            outputStream.flush();
            outputStream.close();
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Export failed", e);
            context.renderJSON().renderMsg("Export failed, please check log");

            return;
        }
    }

    /**
     * Fires FreeMarker action event with the host template name and data model.
     *
     * @param hostTemplateName the specified host template name
     * @param dataModel        the specified data model
     */
    private void fireFreeMarkerActionEvent(final String hostTemplateName, final Map<String, Object> dataModel) {
        try {
            final ViewLoadEventData data = new ViewLoadEventData();

            data.setViewName(hostTemplateName);
            data.setDataModel(dataModel);
            eventManager.fireEventSynchronously(new Event<ViewLoadEventData>(Keys.FREEMARKER_ACTION, data));
            if (Strings.isEmptyOrNull((String) dataModel.get(Plugin.PLUGINS))) {
                // There is no plugin for this template, fill ${plugins} with blank.
                dataModel.put(Plugin.PLUGINS, "");
            }
        } catch (final EventException e) {
            LOGGER.log(Level.WARN, "Event[FREEMARKER_ACTION] handle failed, ignores this exception for kernel health", e);
        }
    }

    private void exportHexoMd(final List<JSONObject> articles, final String dirPath) {
        articles.forEach(article -> {
            final String filename = sanitizeFilename(article.optString("title")) + ".md";
            final String text = article.optString("front") + "---" + Strings.LINE_SEPARATOR + article.optString("content");

            try {
                FileUtils.writeStringToFile(new File(dirPath + File.separator + filename), text, "UTF-8");
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Write markdown file failed", e);
            }
        });
    }
}
