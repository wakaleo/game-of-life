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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.jdbc.JdbcRepository;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Strings;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Article;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * Import service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.1, Nov 11, 2017
 * @since 2.2.0
 */
@Service
public class ImportService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ImportService.class);

    /**
     * Default tag.
     */
    private static final String DEFAULT_TAG = "Note";

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Imports markdowns files as articles. See <a href="https://hacpai.com/article/1498490209748">Solo 支持 Hexo/Jekyll 数据导入</a> for
     * more details.
     */
    public void importMarkdowns() {
        new Thread(() -> {
            try {
                final ServletContext servletContext = SoloServletListener.getServletContext();
                final String markdownsPath = servletContext.getRealPath("markdowns");
                LOGGER.debug("Import directory [" + markdownsPath + "]");

                JSONObject admin;
                try {
                    admin = userQueryService.getAdmin();
                } catch (final Exception e) {
                    return;
                }

                if (null == admin) { // Not init yet
                    return;
                }

                final String adminEmail = admin.optString(User.USER_EMAIL);

                int succCnt = 0, failCnt = 0;
                final Set<String> failSet = new TreeSet<>();
                final Collection<File> mds = FileUtils.listFiles(new File(markdownsPath), new String[]{"md"}, true);
                if (null == mds || mds.isEmpty()) {
                    return;
                }

                for (final File md : mds) {
                    final String fileName = md.getName();
                    if (StringUtils.equalsIgnoreCase(fileName, "README.md")) {
                        continue;
                    }

                    try {
                        final String fileContent = FileUtils.readFileToString(md, "UTF-8");
                        final JSONObject article = parseArticle(fileName, fileContent);
                        article.put(Article.ARTICLE_AUTHOR_EMAIL, adminEmail);

                        final JSONObject request = new JSONObject();
                        request.put(Article.ARTICLE, article);

                        final String id = articleMgmtService.addArticle(request);
                        FileUtils.moveFile(md, new File(md.getPath() + "." + id));
                        LOGGER.info("Imported article [" + article.optString(Article.ARTICLE_TITLE) + "]");
                        succCnt++;
                    } catch (final Exception e) {
                        LOGGER.log(Level.ERROR, "Import file [" + fileName + "] failed", e);

                        failCnt++;
                        failSet.add(fileName);
                    }
                }

                if (0 == succCnt && 0 == failCnt) {
                    return;
                }

                final StringBuilder logBuilder = new StringBuilder();
                logBuilder.append("[").append(succCnt).append("] imported, [").append(failCnt).append("] failed");
                if (failCnt > 0) {
                    logBuilder.append(": ").append(Strings.LINE_SEPARATOR);

                    for (final String fail : failSet) {
                        logBuilder.append("    ").append(fail).append(Strings.LINE_SEPARATOR);
                    }
                } else {
                    logBuilder.append(" :p");
                }
                LOGGER.info(logBuilder.toString());
            } finally {
                JdbcRepository.dispose();
            }
        }).start();
    }

    private JSONObject parseArticle(final String fileName, String fileContent) {
        fileContent = StringUtils.trim(fileContent);
        String frontMatter = StringUtils.substringBefore(fileContent, "---");
        if (StringUtils.isBlank(frontMatter)) {
            fileContent = StringUtils.substringAfter(fileContent, "---");
            frontMatter = StringUtils.substringBefore(fileContent, "---");
        }

        final JSONObject ret = new JSONObject();
        final Yaml yaml = new Yaml();
        Map elems;

        try {
            elems = (Map) yaml.load(frontMatter);
        } catch (final Exception e) {
            // treat it as plain markdown
            ret.put(Article.ARTICLE_TITLE, StringUtils.substringBeforeLast(fileName, "."));
            ret.put(Article.ARTICLE_CONTENT, fileContent);
            ret.put(Article.ARTICLE_ABSTRACT, Article.getAbstract(fileContent));
            ret.put(Article.ARTICLE_TAGS_REF, DEFAULT_TAG);
            ret.put(Article.ARTICLE_IS_PUBLISHED, true);
            ret.put(Article.ARTICLE_COMMENTABLE, true);
            ret.put(Article.ARTICLE_VIEW_PWD, "");

            return ret;
        }

        String title = (String) elems.get("title");
        if (StringUtils.isBlank(title)) {
            title = StringUtils.substringBeforeLast(fileName, ".");
        }
        ret.put(Article.ARTICLE_TITLE, title);

        String content = StringUtils.substringAfter(fileContent, frontMatter);
        if (StringUtils.startsWith(content, "---")) {
            content = StringUtils.substringAfter(content, "---");
            content = StringUtils.trim(content);
        }
        ret.put(Article.ARTICLE_CONTENT, content);

        final String abs = parseAbstract(elems, content);
        ret.put(Article.ARTICLE_ABSTRACT, abs);

        final Date date = parseDate(elems);
        ret.put(Article.ARTICLE_CREATE_DATE, date);

        final String permalink = (String) elems.get("permalink");
        if (StringUtils.isNotBlank(permalink)) {
            ret.put(Article.ARTICLE_PERMALINK, permalink);
        }

        final List<String> tags = parseTags(elems);
        final StringBuilder tagBuilder = new StringBuilder();
        for (final String tag : tags) {
            tagBuilder.append(tag).append(",");
        }
        tagBuilder.deleteCharAt(tagBuilder.length() - 1);
        ret.put(Article.ARTICLE_TAGS_REF, tagBuilder.toString());

        ret.put(Article.ARTICLE_IS_PUBLISHED, true);
        ret.put(Article.ARTICLE_COMMENTABLE, true);
        ret.put(Article.ARTICLE_VIEW_PWD, "");

        return ret;
    }

    private String parseAbstract(final Map map, final String content) {
        String ret = (String) map.get("description");
        if (null == ret) {
            ret = (String) map.get("summary");
        }
        if (null == ret) {
            ret = (String) map.get("abstract");
        }
        if (StringUtils.isNotBlank(ret)) {
            return ret;
        }

        return Article.getAbstract(content);
    }

    private Date parseDate(final Map map) {
        Object date = map.get("date");
        if (null == date) {
            return new Date();
        }

        if (date instanceof String) {
            try {
                return DateUtils.parseDate((String) date, new String[]{
                        "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss",
                        "dd-MM-yyyy HH:mm:ss", "yyyyMMdd HH:mm:ss",
                        "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm", "dd/MM/yyyy HH:mm",
                        "dd-MM-yyyy HH:mm", "yyyyMMdd HH:mm"});
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Parse date [" + date + "] failed", e);

                throw new RuntimeException(e);
            }
        } else if (date instanceof Date) {
            return (Date) date;
        }

        return new Date();
    }

    private List<String> parseTags(final Map map) {
        final List<String> ret = new ArrayList<>();

        Object tags = map.get("tags");
        if (null == tags) {
            tags = map.get("category");
        }
        if (null == tags) {
            tags = map.get("categories");
        }
        if (null == tags) {
            tags = map.get("keyword");
        }
        if (null == tags) {
            tags = map.get("keywords");
        }
        if (null == tags) {
            ret.add(DEFAULT_TAG);

            return ret;
        }

        if (tags instanceof String) {
            final String[] tagArr = ((String) tags).split(" ");
            tags = Arrays.asList(tagArr);
        }
        final TreeSet tagSet = new TreeSet();
        for (final String tag : (List<String>) tags) {
            if (StringUtils.isBlank(tag)) {
                tagSet.add(DEFAULT_TAG);
            } else {
                tagSet.add(tag);
            }
        }
        ret.addAll(tagSet);

        return ret;
    }
}
