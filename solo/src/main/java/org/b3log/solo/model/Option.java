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
package org.b3log.solo.model;

import org.b3log.latke.Keys;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class defines option model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.3.0.5, Jan 31, 2018
 * @since 0.6.0
 */
public final class Option {

    /**
     * Option.
     */
    public static final String OPTION = "option";

    /**
     * Options.
     */
    public static final String OPTIONS = "options";

    /**
     * Key of option value.
     */
    public static final String OPTION_VALUE = "optionValue";

    /**
     * Key of option category.
     */
    public static final String OPTION_CATEGORY = "optionCategory";

    // oId constants
    /**
     * Key of broadcast chance expiration time.
     */
    public static final String ID_C_BROADCAST_CHANCE_EXPIRATION_TIME = "broadcastChanceExpirationTime";

    /**
     * Key of Qiniu access key.
     */
    public static final String ID_C_QINIU_ACCESS_KEY = "qiniuAccessKey";

    /**
     * Key of Qiniu secret key.
     */
    public static final String ID_C_QINIU_SECRET_KEY = "qiniuSecretKey";

    /**
     * Key of Qiniu domain.
     */
    public static final String ID_C_QINIU_DOMAIN = "qiniuDomain";

    /**
     * Key of Qiniu bucket.
     */
    public static final String ID_C_QINIU_BUCKET = "qiniuBucket";

    /**
     * Key of blog title.
     */
    public static final String ID_C_BLOG_TITLE = "blogTitle";

    /**
     * Key of blog subtitle.
     */
    public static final String ID_C_BLOG_SUBTITLE = "blogSubtitle";

    /**
     * Key of relevant articles display count.
     */
    public static final String ID_C_RELEVANT_ARTICLES_DISPLAY_CNT = "relevantArticlesDisplayCount";

    /**
     * Key of random articles display count.
     */
    public static final String ID_C_RANDOM_ARTICLES_DISPLAY_CNT = "randomArticlesDisplayCount";

    /**
     * Key of external relevant articles display count.
     */
    public static final String ID_C_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_CNT = "externalRelevantArticlesDisplayCount";

    /**
     * Key of recent article display count.
     */
    public static final String ID_C_RECENT_ARTICLE_DISPLAY_CNT = "recentArticleDisplayCount";

    /**
     * Key of recent comment display count.
     */
    public static final String ID_C_RECENT_COMMENT_DISPLAY_CNT = "recentCommentDisplayCount";

    /**
     * Key of most used tag display count.
     */
    public static final String ID_C_MOST_USED_TAG_DISPLAY_CNT = "mostUsedTagDisplayCount";

    /**
     * Key of most comment article display count.
     */
    public static final String ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT = "mostCommentArticleDisplayCount";

    /**
     * Key of most view article display count.
     */
    public static final String ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT = "mostViewArticleDisplayCount";

    /**
     * Key of article list display count.
     */
    public static final String ID_C_ARTICLE_LIST_DISPLAY_COUNT = "articleListDisplayCount";

    /**
     * Key of article list pagination window size.
     */
    public static final String ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = "articleListPaginationWindowSize";

    /**
     * Key of administrator's email.
     */
    public static final String ID_C_ADMIN_EMAIL = "adminEmail";

    /**
     * Key of locale string.
     */
    public static final String ID_C_LOCALE_STRING = "localeString";

    /**
     * Key of time zone id.
     */
    public static final String ID_C_TIME_ZONE_ID = "timeZoneId";

    /**
     * Key of notice board.
     */
    public static final String ID_C_NOTICE_BOARD = "noticeBoard";

    /**
     * Key of HTML head.
     */
    public static final String ID_C_HTML_HEAD = "htmlHead";

    /**
     * Key of meta keywords.
     */
    public static final String ID_C_META_KEYWORDS = "metaKeywords";

    /**
     * Key of meta description.
     */
    public static final String ID_C_META_DESCRIPTION = "metaDescription";

    /**
     * Key of article update hint flag.
     */
    public static final String ID_C_ENABLE_ARTICLE_UPDATE_HINT = "enableArticleUpdateHint";

    /**
     * Key of signs.
     */
    public static final String ID_C_SIGNS = "signs";

    /**
     * Key of key of Solo.
     */
    public static final String ID_C_KEY_OF_SOLO = "keyOfSolo";

    /**
     * Key of allow visit draft via permalink.
     */
    public static final String ID_C_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "allowVisitDraftViaPermalink";

    /**
     * Key of allow register.
     */
    public static final String ID_C_ALLOW_REGISTER = "allowRegister";

    /**
     * Key of version.
     */
    public static final String ID_C_VERSION = "version";

    /**
     * Key of article list display style.
     * <p>
     * Optional values:
     * <ul>
     * <li>"titleOnly"</li>
     * <li>"titleAndContent"</li>
     * <li>"titleAndAbstract"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_ARTICLE_LIST_STYLE = "articleListStyle";

    /**
     * Key of article/page comment-able.
     */
    public static final String ID_C_COMMENTABLE = "commentable";

    /**
     * Key of feed (Atom/RSS) output mode.
     * <p>
     * Optional values:
     * <ul>
     * <li>"abstract"</li>
     * <li>"fullContent"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_FEED_OUTPUT_MODE = "feedOutputMode";

    /**
     * Key of feed (Atom/RSS) output entry count.
     */
    public static final String ID_C_FEED_OUTPUT_CNT = "feedOutputCnt";

    /**
     * Key of editor type.
     * <p>
     * Optional values:
     * <ul>
     * <li>"tinyMCE"</li>
     * <li>"CodeMirror-Markdown"</li>
     * <li>"KindEditor"</li>
     * </ul>
     * </p>
     */
    public static final String ID_C_EDITOR_TYPE = "editorType";

    /**
     * Key of skins.
     */
    public static final String ID_C_SKINS = "skins";

    /**
     * Key of skin dir name.
     */
    public static final String ID_C_SKIN_DIR_NAME = "skinDirName";

    /**
     * Key of skin name.
     */
    public static final String ID_C_SKIN_NAME = "skinName";

    /**
     * Key of reply notification template body.
     */
    public static final String ID_C_REPLY_NOTI_TPL_BODY = "replyNotiTplBody";

    /**
     * Key of reply notification template subject.
     */
    public static final String ID_C_REPLY_NOTI_TPL_SUBJECT = "replyNotiTplSubject";

    /**
     * Key of footer content.
     */
    public static final String ID_C_FOOTER_CONTENT = "footerContent";

    /**
     * Key of statistic blog view count.
     */
    public static final String ID_C_STATISTIC_BLOG_VIEW_COUNT = "statisticBlogViewCount";

    /**
     * Key of statistic blog comment count.
     */
    public static final String ID_C_STATISTIC_BLOG_COMMENT_COUNT = "statisticBlogCommentCount";

    /**
     * Key of statistic blog comment(published article) count.
     */
    public static final String ID_C_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT = "statisticPublishedBlogCommentCount";

    /**
     * Key of statistic blog article count.
     */
    public static final String ID_C_STATISTIC_BLOG_ARTICLE_COUNT = "statisticBlogArticleCount";

    /**
     * Key of statistic blog published article count.
     */
    public static final String ID_C_STATISTIC_PUBLISHED_ARTICLE_COUNT = "statisticPublishedBlogArticleCount";

    // Category constants
    /**
     * Broadcast.
     */
    public static final String CATEGORY_C_BROADCAST = "broadcast";

    /**
     * Qiniu.
     */
    public static final String CATEGORY_C_QINIU = "qiniu";

    /**
     * Preference.
     */
    public static final String CATEGORY_C_PREFERENCE = "preference";

    /**
     * Statistic.
     */
    public static final String CATEGORY_C_STATISTIC = "statistic";

    /**
     * Private constructor.
     */
    private Option() {
    }

    /**
     * Default preference.
     *
     * @author <a href="http://88250.b3log.org">Liang Ding</a>
     * @version 2.1.0.9, Nov 23, 2015
     * @since 0.3.1
     */
    public static final class DefaultPreference {

        /**
         * Default recent article display count.
         */
        public static final int DEFAULT_RECENT_ARTICLE_DISPLAY_COUNT = 10;

        /**
         * Default recent comment display count.
         */
        public static final int DEFAULT_RECENT_COMMENT_DISPLAY_COUNT = 10;

        /**
         * Default most used tag display count.
         */
        public static final int DEFAULT_MOST_USED_TAG_DISPLAY_COUNT = 20;

        /**
         * Default article list display count.
         */
        public static final int DEFAULT_ARTICLE_LIST_DISPLAY_COUNT = 20;

        /**
         * Default article list pagination window size.
         */
        public static final int DEFAULT_ARTICLE_LIST_PAGINATION_WINDOW_SIZE = 15;

        /**
         * Default most comment article display count.
         */
        public static final int DEFAULT_MOST_COMMENT_ARTICLE_DISPLAY_COUNT = 5;

        /**
         * Default blog title.
         */
        public static final String DEFAULT_BLOG_TITLE = "Solo 示例";

        /**
         * Default blog subtitle.
         */
        public static final String DEFAULT_BLOG_SUBTITLE = "Java 开源博客";

        /**
         * Default skin directory name.
         */
        public static final String DEFAULT_SKIN_DIR_NAME = "Medium";

        /**
         * Default language.
         */
        public static final String DEFAULT_LANGUAGE = "zh_CN";

        /**
         * Default time zone.
         *
         * @see java.util.TimeZone#getAvailableIDs()
         */
        public static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";

        /**
         * Default enable article update hint.
         */
        public static final String DEFAULT_ENABLE_ARTICLE_UPDATE_HINT = "true";

        /**
         * Default notice board.
         */
        public static final String DEFAULT_NOTICE_BOARD = "Open Source, Open Mind, <br/>Open Sight, Open Future!";

        /**
         * Default meta keywords..
         */
        public static final String DEFAULT_META_KEYWORDS = "Solo,Java 博客,开源";

        /**
         * Default meta description..
         */
        public static final String DEFAULT_META_DESCRIPTION = "An open source blog with Java. Java 开源博客";

        /**
         * Default HTML head to append.
         */
        public static final String DEFAULT_HTML_HEAD = "";

        /**
         * Default footer content.
         */
        public static final String DEFAULT_FOOTER_CONTENT = "";

        /**
         * Default relevant articles display count.
         */
        public static final int DEFAULT_RELEVANT_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default random articles display count.
         */
        public static final int DEFAULT_RANDOM_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default external relevant articles display count.
         */
        public static final int DEFAULT_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Most view articles display count.
         */
        public static final int DEFAULT_MOST_VIEW_ARTICLES_DISPLAY_COUNT = 5;

        /**
         * Default signs.
         */
        public static final String DEFAULT_SIGNS;

        /**
         * Default allow visit draft via permalink.
         */
        public static final String DEFAULT_ALLOW_VISIT_DRAFT_VIA_PERMALINK = "false";

        /**
         * Default allow register.
         */
        public static final String DEFAULT_ALLOW_REGISTER = "false";

        /**
         * Default allow comment article/page.
         */
        public static final String DEFAULT_COMMENTABLE = "true";

        /**
         * Default article list display style.
         */
        public static final String DEFAULT_ARTICLE_LIST_STYLE = "titleAndAbstract";

        /**
         * Default key of solo.
         */
        public static final String DEFAULT_KEY_OF_SOLO = "Your key";

        /**
         * Default reply notification template.
         */
        public static final String DEFAULT_REPLY_NOTIFICATION_TEMPLATE;

        /**
         * Default feed output mode.
         */
        public static final String DEFAULT_FEED_OUTPUT_MODE = "abstract";

        /**
         * Default feed output entry count.
         */
        public static final int DEFAULT_FEED_OUTPUT_CNT = 10;

        /**
         * Default editor type.
         */
        public static final String DEFAULT_EDITOR_TYPE = "CodeMirror-Markdown";

        /**
         * Logger.
         */
        private static final Logger LOGGER = Logger.getLogger(DefaultPreference.class);

        static {
            final JSONArray signs = new JSONArray();
            final int signLength = 4;

            try {
                for (int i = 0; i < signLength; i++) {
                    final JSONObject sign = new JSONObject();
                    sign.put(Keys.OBJECT_ID, i);
                    signs.put(sign);
                    sign.put(Sign.SIGN_HTML, "");
                }

                // Sign(id=0) is the 'empty' sign, used for article user needn't a sign
                DEFAULT_SIGNS = signs.toString();

                final JSONObject replyNotificationTemplate = new JSONObject();
                replyNotificationTemplate.put("subject", "${blogTitle}: New reply of your comment");
                replyNotificationTemplate.put("body",
                        "Your comment on post[<a href='${postLink}'>" + "${postTitle}</a>] received an reply: <p>${replier}"
                                + ": <span><a href='${replyURL}'>${replyContent}</a></span></p>");
                DEFAULT_REPLY_NOTIFICATION_TEMPLATE = replyNotificationTemplate.toString();
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Creates sign error!", e);

                throw new IllegalStateException(e);
            }
        }

        /**
         * Private default constructor.
         */
        private DefaultPreference() {
        }
    }
}
