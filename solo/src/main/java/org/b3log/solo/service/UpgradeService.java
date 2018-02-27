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
package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.mail.MailService;
import org.b3log.latke.mail.MailServiceFactory;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.repository.jdbc.util.Connections;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.util.Mails;
import org.b3log.solo.util.Thumbnails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Upgrade service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="mailto:dongxu.wang@acm.org">Dongxu Wang</a>
 * @version 1.2.0.22, Feb 1, 2018
 * @since 1.2.0
 */
@Service
public class UpgradeService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UpgradeService.class);

    /**
     * Step for article updating.
     */
    private static final int STEP = 50;

    /**
     * Mail Service.
     */
    private static final MailService MAIL_SVC = MailServiceFactory.getMailService();

    /**
     * Old version.
     */
    private static final String FROM_VER = "2.5.0";

    /**
     * New version.
     */
    private static final String TO_VER = SoloServletListener.VERSION;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Preference Query Service.
     */
    @Inject
    private PreferenceQueryService preferenceQueryService;

    /**
     * Statistic query service.
     */
    @Inject
    private StatisticQueryService statisticQueryService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Upgrades if need.
     */
    public void upgrade() {
        try {
            final JSONObject preference = preferenceQueryService.getPreference();
            if (null == preference) {
                return;
            }

            final String currentVer = preference.getString(Option.ID_C_VERSION);
            if (SoloServletListener.VERSION.equals(currentVer)) {
                return;
            }

            if (FROM_VER.equals(currentVer)) {
                perform();

                return;
            }

            LOGGER.log(Level.ERROR, "Attempt to skip more than one version to upgrade. Expected: {0}, Actually: {1}", FROM_VER, currentVer);
            notifyUserByEmail();

            System.exit(-1);
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            LOGGER.log(Level.ERROR,
                    "Upgrade failed [" + e.getMessage() + "], please contact the Solo developers or reports this "
                            + "issue directly (<a href='https://github.com/b3log/solo/issues/new'>"
                            + "https://github.com/b3log/solo/issues/new</a>) ");

            System.exit(-1);
        }
    }

    /**
     * Performs upgrade.
     *
     * @throws Exception upgrade fails
     */
    private void perform() throws Exception {
        LOGGER.log(Level.INFO, "Upgrading from version [{0}] to version [{1}]....", FROM_VER, TO_VER);

        final Transaction transaction = optionRepository.beginTransaction();
        try {
            final JSONObject versionOpt = optionRepository.get(Option.ID_C_VERSION);
            versionOpt.put(Option.OPTION_VALUE, TO_VER);
            optionRepository.update(Option.ID_C_VERSION, versionOpt);

            transaction.commit();
        } catch (final Exception e) {
            if (null != transaction && transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Upgrade failed!", e);
            throw new Exception("Upgrade failed from version [" + FROM_VER + "] to version [" + TO_VER + ']');
        }

        LOGGER.log(Level.INFO, "Upgraded from version [{0}] to version [{1}] successfully :-)", FROM_VER, TO_VER);
    }

    /**
     * Alters database tables.
     *
     * @throws Exception exception
     */
    private void alterTables() throws Exception {
        final Connection connection = Connections.getConnection();
        final Statement statement = connection.createStatement();

        final String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
        statement.executeUpdate("ALTER TABLE `" + tablePrefix + "page` ADD `pageIcon` VARCHAR(255) NOT NULL;");
        statement.close();
        connection.commit();
        connection.close();
    }

    /**
     * Drops database tables.
     *
     * @throws Exception exception
     */
    private void dropTables() throws Exception {
        final Connection connection = Connections.getConnection();
        final Statement statement = connection.createStatement();

        final String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
        statement.execute("DROP TABLE `" + tablePrefix + "statistic`;");
        statement.close();
        connection.commit();
        connection.close();
    }

    /**
     * Upgrade database tables.
     *
     * @throws Exception exception
     */
    private void upgradeTables() throws Exception {
        final Connection connection = Connections.getConnection();
        final Statement statement = connection.createStatement();

        final String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
        statement.execute("CREATE TABLE `" + tablePrefix + "category` (\n" +
                "  `oId` varchar(19) NOT NULL,\n" +
                "  `categoryTitle` varchar(64) NOT NULL,\n" +
                "  `categoryURI` varchar(32) NOT NULL,\n" +
                "  `categoryDescription` text NOT NULL,\n" +
                "  `categoryOrder` int(11) NOT NULL,\n" +
                "  `categoryTagCnt` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`oId`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        statement.execute("CREATE TABLE `" + tablePrefix + "category_tag` (\n" +
                "  `oId` varchar(19) NOT NULL,\n" +
                "  `category_oId` varchar(19) NOT NULL,\n" +
                "  `tag_oId` varchar(19) NOT NULL,\n" +
                "  PRIMARY KEY (`oId`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        statement.close();
        connection.commit();
        connection.close();
    }

    /**
     * Upgrades users.
     * <p>
     * Password hashing.
     * </p>
     *
     * @throws Exception exception
     */
    private void upgradeUsers() throws Exception {
        final JSONArray users = userRepository.get(new Query()).getJSONArray(Keys.RESULTS);

        for (int i = 0; i < users.length(); i++) {
            final JSONObject user = users.getJSONObject(i);
            final String email = user.optString(User.USER_EMAIL);
            user.put(UserExt.USER_AVATAR, Thumbnails.getGravatarURL(email, "128"));

            userRepository.update(user.optString(Keys.OBJECT_ID), user);
            LOGGER.log(Level.INFO, "Updated user[email={0}]", email);
        }
    }

    /**
     * Upgrades articles.
     *
     * @throws Exception exception
     */
    private void upgradeArticles() throws Exception {
        LOGGER.log(Level.INFO, "Adds a property [articleEditorType] to each of articles");

        final JSONArray articles = articleRepository.get(new Query()).getJSONArray(Keys.RESULTS);
        if (articles.length() <= 0) {
            LOGGER.log(Level.TRACE, "No articles");
            return;
        }

        Transaction transaction = null;
        try {
            for (int i = 0; i < articles.length(); i++) {
                if (0 == i % STEP || !transaction.isActive()) {
                    transaction = userRepository.beginTransaction();
                }

                final JSONObject article = articles.getJSONObject(i);
                final String articleId = article.optString(Keys.OBJECT_ID);
                LOGGER.log(Level.INFO, "Found an article[id={0}]", articleId);
                article.put(Article.ARTICLE_EDITOR_TYPE, "tinyMCE");

                articleRepository.update(article.getString(Keys.OBJECT_ID), article);

                if (0 == i % STEP) {
                    transaction.commit();
                    LOGGER.log(Level.TRACE, "Updated some articles");
                }
            }

            if (transaction.isActive()) {
                transaction.commit();
            }

            LOGGER.log(Level.TRACE, "Updated all articles");
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    /**
     * Send an email to the user who upgrades Solo with a discontinuous version.
     *
     * @throws ServiceException ServiceException
     * @throws JSONException    JSONException
     * @throws IOException      IOException
     */
    private void notifyUserByEmail() throws ServiceException, JSONException, IOException {
        if (!Mails.isConfigured()) {
            return;
        }

        final String adminEmail = preferenceQueryService.getPreference().getString(Option.ID_C_ADMIN_EMAIL);
        final MailService.Message message = new MailService.Message();
        message.setFrom(adminEmail);
        message.addRecipient(adminEmail);
        message.setSubject(langPropsService.get("skipVersionMailSubject"));
        message.setHtmlBody(langPropsService.get("skipVersionMailBody"));

        MAIL_SVC.send(message);

        LOGGER.info("Send an email to the user who upgrades Solo with a discontinuous version.");
    }
}
