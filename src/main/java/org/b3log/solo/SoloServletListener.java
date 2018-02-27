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
package org.b3log.solo;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.event.EventManager;
import org.b3log.latke.ioc.LatkeBeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.plugin.PluginManager;
import org.b3log.latke.plugin.ViewLoadEventHandler;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.repository.jdbc.JdbcRepository;
import org.b3log.latke.servlet.AbstractServletListener;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.latke.util.Strings;
import org.b3log.latke.util.freemarker.Templates;
import org.b3log.solo.event.comment.ArticleCommentReplyNotifier;
import org.b3log.solo.event.comment.PageCommentReplyNotifier;
import org.b3log.solo.event.plugin.PluginRefresher;
import org.b3log.solo.event.rhythm.ArticleSender;
import org.b3log.solo.event.rhythm.ArticleUpdater;
import org.b3log.solo.event.symphony.CommentSender;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.Skin;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.impl.OptionRepositoryImpl;
import org.b3log.solo.service.*;
import org.b3log.solo.util.Skins;
import org.json.JSONObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Solo Servlet listener.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.9.3.28, Feb 1, 2018
 * @since 0.3.1
 */
public final class SoloServletListener extends AbstractServletListener {

    /**
     * Solo version.
     */
    public static final String VERSION = "2.6.0";

    /**
     * B3log Rhythm address.
     */
    public static final String B3LOG_RHYTHM_SERVE_PATH;

    /**
     * B3log Symphony address.
     */
    public static final String B3LOG_SYMPHONY_SERVE_PATH;

    /**
     * Favicon API.
     */
    public static final String FAVICON_API;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SoloServletListener.class);

    static {
        final ResourceBundle b3log = ResourceBundle.getBundle("b3log");

        B3LOG_RHYTHM_SERVE_PATH = b3log.getString("rhythm.servePath");
        B3LOG_SYMPHONY_SERVE_PATH = b3log.getString("symphony.servePath");
        FAVICON_API = b3log.getString("faviconAPI");
    }

    /**
     * Bean manager.
     */
    private LatkeBeanManager beanManager;

    /**
     * Request lock.
     */
    private Lock requestLock = new ReentrantLock();

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        Latkes.setScanPath("org.b3log.solo"); // For Latke IoC        
        super.contextInitialized(servletContextEvent);
        Stopwatchs.start("Context Initialized");

        beanManager = Lifecycle.getBeanManager();

        // Upgrade check (https://github.com/b3log/solo/issues/12040)
        final UpgradeService upgradeService = beanManager.getReference(UpgradeService.class);
        upgradeService.upgrade();

        // Import check (https://github.com/b3log/solo/issues/12293)
        final ImportService importService = beanManager.getReference(ImportService.class);
        importService.importMarkdowns();

        JdbcRepository.dispose();

        // Set default skin, loads from preference later
        Skins.setDirectoryForTemplateLoading(Option.DefaultPreference.DEFAULT_SKIN_DIR_NAME);

        final OptionRepository optionRepository = beanManager.getReference(OptionRepositoryImpl.class);
        final Transaction transaction = optionRepository.beginTransaction();
        try {
            loadPreference();

            if (transaction.isActive()) {
                transaction.commit();
            }
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        registerEventProcessor();

        final PluginManager pluginManager = beanManager.getReference(PluginManager.class);
        pluginManager.load();

        LOGGER.info("Solo is running [" + Latkes.getServePath() + "]");

        Stopwatchs.end();
        LOGGER.log(Level.DEBUG, "Stopwatch: {0}{1}", Strings.LINE_SEPARATOR, Stopwatchs.getTimingStat());
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

        LOGGER.info("Destroyed the context");
    }

    @Override
    public void sessionCreated(final HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent httpSessionEvent) {
        super.sessionDestroyed(httpSessionEvent);
    }

    @Override
    public void requestInitialized(final ServletRequestEvent servletRequestEvent) {
        requestLock.lock();

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequestEvent.getServletRequest();
        Requests.log(httpServletRequest, Level.DEBUG, LOGGER);

        final String requestURI = httpServletRequest.getRequestURI();
        Stopwatchs.start("Request Initialized[requestURI=" + requestURI + "]");
        if (Requests.searchEngineBotRequest(httpServletRequest)) {
            LOGGER.log(Level.DEBUG, "Request made from a search engine[User-Agent={0}]", httpServletRequest.getHeader("User-Agent"));
            httpServletRequest.setAttribute(Keys.HttpRequest.IS_SEARCH_ENGINE_BOT, true);
        } else {
            // Gets the session of this request
            final HttpSession session = httpServletRequest.getSession();

            LOGGER.log(Level.DEBUG, "Gets a session[id={0}, remoteAddr={1}, User-Agent={2}, isNew={3}]", session.getId(),
                    httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent"), session.isNew());
            // Online visitor count
            final StatisticMgmtService statisticMgmtService = beanManager.getReference(StatisticMgmtService.class);

            statisticMgmtService.onlineVisitorCount(httpServletRequest);
        }

        resolveSkinDir(httpServletRequest);
    }

    @Override
    public void requestDestroyed(final ServletRequestEvent servletRequestEvent) {
        try {
            Stopwatchs.end();

            LOGGER.log(Level.DEBUG, "Stopwatch: {0}{1}", Strings.LINE_SEPARATOR, Stopwatchs.getTimingStat());
            Stopwatchs.release();

            super.requestDestroyed(servletRequestEvent);
        } finally {
            requestLock.unlock();
        }
    }

    /**
     * Loads preference.
     * <p>
     * Loads preference from repository, loads skins from skin directory then sets it into preference if the skins
     * changed.
     * </p>
     */
    private void loadPreference() {
        Stopwatchs.start("Load Preference");

        LOGGER.debug("Loading preference....");

        final PreferenceQueryService preferenceQueryService = beanManager.getReference(PreferenceQueryService.class);
        JSONObject preference;

        try {
            preference = preferenceQueryService.getPreference();
            if (null == preference) {
                LOGGER.info("Please open browser and visit [" + Latkes.getServePath() + "] to init your Solo, "
                        + "and then enjoy it :-p");

                return;
            }

            final PreferenceMgmtService preferenceMgmtService = beanManager.getReference(PreferenceMgmtService.class);

            preferenceMgmtService.loadSkins(preference);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);

            throw new IllegalStateException(e);
        }

        Stopwatchs.end();
    }

    /**
     * Register event processors.
     */
    private void registerEventProcessor() {
        Stopwatchs.start("Register Event Processors");

        LOGGER.debug("Registering event processors....");
        try {
            final EventManager eventManager = beanManager.getReference(EventManager.class);

            // Comment
            eventManager.registerListener(new ArticleCommentReplyNotifier());
            eventManager.registerListener(new PageCommentReplyNotifier());

            // Article
            // eventManager.registerListener(new AddArticleGoogleBlogSearchPinger());
            // eventManager.registerListener(new UpdateArticleGoogleBlogSearchPinger());
            // Plugin
            eventManager.registerListener(new PluginRefresher());
            eventManager.registerListener(new ViewLoadEventHandler());

            // Sync
            eventManager.registerListener(new ArticleSender());
            eventManager.registerListener(new ArticleUpdater());
            eventManager.registerListener(new CommentSender());
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Register event processors error", e);
            throw new IllegalStateException(e);
        }

        LOGGER.debug("Registering event processors....");

        Stopwatchs.end();
    }

    /**
     * Resolve skin (template) for the specified HTTP servlet request.
     *
     * @param httpServletRequest the specified HTTP servlet request
     */
    private void resolveSkinDir(final HttpServletRequest httpServletRequest) {
        // https://github.com/b3log/solo/issues/12060
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (null != cookies) {
            for (final Cookie cookie : cookies) {
                if (Skin.SKIN.equals(cookie.getName())) {
                    final String skin = cookie.getValue();
                    final Set<String> skinDirNames = Skins.getSkinDirNames();

                    if (skinDirNames.contains(skin)) {
                        Templates.MAIN_CFG.setServletContextForTemplateLoading(SoloServletListener.getServletContext(),
                                "/skins/" + skin);
                        httpServletRequest.setAttribute(Keys.TEMAPLTE_DIR_NAME, skin);

                        return;
                    }
                }
            }
        }

        try {
            final PreferenceQueryService preferenceQueryService = beanManager.getReference(PreferenceQueryService.class);
            final JSONObject preference = preferenceQueryService.getPreference();

            if (null == preference) { // Did not initialize yet
                return;
            }

            final String requestURI = httpServletRequest.getRequestURI();

            String desiredView = Requests.mobileSwitchToggle(httpServletRequest);
            if (desiredView == null && !Requests.mobileRequest(httpServletRequest) || desiredView != null && desiredView.equals("normal")) {
                desiredView = preference.getString(Skin.SKIN_DIR_NAME);
            } else {
                desiredView = "mobile";
                LOGGER.log(Level.DEBUG, "The request [URI={0}] comes frome mobile device", requestURI);
            }

            Templates.MAIN_CFG.setServletContextForTemplateLoading(SoloServletListener.getServletContext(), "/skins/" + desiredView);
            httpServletRequest.setAttribute(Keys.TEMAPLTE_DIR_NAME, desiredView);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Resolves skin failed", e);
        }
    }
}
