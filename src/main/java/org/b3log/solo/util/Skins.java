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
package org.b3log.solo.util;

import freemarker.template.TemplateExceptionHandler;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.LangPropsServiceImpl;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.latke.util.Strings;
import org.b3log.latke.util.freemarker.Templates;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Skin;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Skin utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.5.8, May 7, 2017
 * @since 0.3.1
 */
public final class Skins {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Skins.class);

    /**
     * Properties map.
     */
    private static final Map<String, Map<String, String>> LANG_MAP = new HashMap<>();

    /**
     * Private default constructor.
     */
    private Skins() {
    }

    /**
     * Fills the specified data model with the current skink's (WebRoot/skins/${skinName}/lang/lang_xx_XX.properties)
     * and core language (WebRoot/WEB-INF/classes/lang_xx_XX.properties) configurations.
     *
     * @param localeString       the specified locale string
     * @param currentSkinDirName the specified current skin directory name
     * @param dataModel          the specified data model
     * @throws ServiceException service exception
     */
    public static void fillLangs(final String localeString, final String currentSkinDirName, final Map<String, Object> dataModel)
            throws ServiceException {
        Stopwatchs.start("Fill Skin Langs");

        try {
            final String langName = currentSkinDirName + "." + localeString;
            Map<String, String> langs = LANG_MAP.get(langName);

            if (null == langs) {
                LANG_MAP.clear(); // Collect unused skin languages

                LOGGER.log(Level.DEBUG, "Loading skin [dirName={0}, locale={1}]", currentSkinDirName, localeString);
                langs = new HashMap<String, String>();

                final String language = Locales.getLanguage(localeString);
                final String country = Locales.getCountry(localeString);

                final ServletContext servletContext = SoloServletListener.getServletContext();
                final InputStream inputStream = servletContext.getResourceAsStream(
                        "/skins/" + currentSkinDirName + "/lang/lang_" + language + '_' + country + ".properties");

                final Properties props = new Properties();

                props.load(inputStream);
                final Set<Object> keys = props.keySet();

                for (final Object key : keys) {
                    langs.put((String) key, props.getProperty((String) key));
                }

                LANG_MAP.put(langName, langs);
                LOGGER.log(Level.DEBUG, "Loaded skin[dirName={0}, locale={1}, keyCount={2}]",
                        currentSkinDirName, localeString, langs.size());
            }

            dataModel.putAll(langs); // Fills the current skin's language configurations

            // Fills the core language configurations
            final LatkeBeanManager beanManager = Lifecycle.getBeanManager();
            final LangPropsService langPropsService = beanManager.getReference(LangPropsServiceImpl.class);

            dataModel.putAll(langPropsService.getAll(Latkes.getLocale()));
        } catch (final IOException e) {
            LOGGER.log(Level.ERROR, "Fills skin langs failed", e);
            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Sets the directory for template loading with the specified skin directory name, and sets the directory for mobile
     * request template loading.
     *
     * @param skinDirName the specified skin directory name
     */
    public static void setDirectoryForTemplateLoading(final String skinDirName) {
        final ServletContext servletContext = SoloServletListener.getServletContext();

        Templates.MAIN_CFG.setServletContextForTemplateLoading(servletContext, "/skins/" + skinDirName);
        Templates.MAIN_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Templates.MAIN_CFG.setLogTemplateExceptions(false);

        Templates.MOBILE_CFG.setServletContextForTemplateLoading(servletContext, "/skins/mobile");
        Templates.MOBILE_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Templates.MOBILE_CFG.setLogTemplateExceptions(false);
    }

    /**
     * Gets all skin directory names. Scans the /skins/ directory, using the subdirectory of it as the skin directory
     * name, for example,
     * <pre>
     * ${Web root}/skins/
     *     <b>default</b>/
     *     <b>mobile</b>/
     *     <b>classic</b>/
     * </pre>.
     *
     * @return a set of skin name, returns an empty set if not found
     */
    public static Set<String> getSkinDirNames() {
        final Set<String> ret = new HashSet<>();

        final ServletContext servletContext = SoloServletListener.getServletContext();
        final Set<String> resourcePaths = servletContext.getResourcePaths("/skins");

        for (final String path : resourcePaths) {
            final String dirName = path.substring("/skins".length() + 1, path.length() - 1);

            if (dirName.startsWith(".")) {
                continue;
            }

            ret.add(dirName);
        }

        return ret;
    }

    /**
     * Gets skin directory name from the specified request. Refers to https://github.com/b3log/solo/issues/12060 for
     * more details.
     *
     * @param request the specified request
     * @return directory name, or {@code "default"} if not found
     */
    public static String getSkinDirName(final HttpServletRequest request) {
        if (Requests.mobileRequest(request)) {
            return (String) request.getAttribute(Keys.TEMAPLTE_DIR_NAME); // resolved in listener
        }

        // 1. Get skin from query
        final String specifiedSkin = request.getParameter(Skin.SKIN);
        if ("default".equals(specifiedSkin)) {
            return "default";
        }

        if (!Strings.isEmptyOrNull(specifiedSkin)) {
            final Set<String> skinDirNames = Skins.getSkinDirNames();
            if (skinDirNames.contains(specifiedSkin)) {
                return specifiedSkin;
            } else {
                return null;
            }
        }

        // 2. Get skin from cookie
        final Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (final Cookie cookie : cookies) {
                if (Skin.SKIN.equals(cookie.getName())) {
                    final String skin = cookie.getValue();
                    final Set<String> skinDirNames = Skins.getSkinDirNames();

                    if (skinDirNames.contains(skin)) {
                        return skin;
                    }
                }
            }
        }

        return "default";
    }
}
