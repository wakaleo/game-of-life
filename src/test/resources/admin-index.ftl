<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="robots" content="none" />
        <title>${blogTitle} - ${adminConsoleLabel}</title>
        <link type="text/css" rel="stylesheet" href="${staticServePath}/css/default-base${miniPostfix}.css?${staticResourceVersion}" />
        <link type="text/css" rel="stylesheet" href="${staticServePath}/css/default-admin${miniPostfix}.css?${staticResourceVersion}" />
        <link type="text/css" rel="stylesheet" href="${staticServePath}/js/lib/CodeMirrorEditor/codemirror.min.css?${staticResourceVersion}" />
        <link type="text/css" rel="stylesheet" href="${staticServePath}/js/lib/highlight/styles/github.css?${staticResourceVersion}" />
        <link rel="icon" type="image/png" href="${staticServePath}/favicon.png" />
    </head>
    <body onhashchange="admin.setCurByHash();">
        <div class="tip"><span id="loadMsg">${loadingLabel}</span></div>
        <div class="tip tip-msg"><span id="tipMsg"></span></div>
        <div id="allPanel">
            <div id="top">
                <a href="http://b3log.org" target="_blank" class="hover">
                    Solo
                </a>
               
                <span class="right"> 
                    <a href="${servePath}" title='${indexLabel}'>${indexLabel}</a><a href='javascript:admin.logout();' title='${logoutLabel}'>${logoutLabel}</a>
                </span>
            </div>
            <div id="tabs">
                <ul>
                    <li>
                        <div id="tabs_main">
                            <a href="#main">
                                <span class="icon-refresh"></span> ${adminIndexLabel}
                            </a>
                        </div>
                    </li>
                    <li>
                        <div id="tabArticleTitle" class="tab-current" onclick="admin.collapseNav(this)">
                            <span class="icon-article"></span>
                            ${articleLabel}
                            <span class="icon-chevron-up right"></span>
                        </div>
                        <ul id="tabArticleMgt">
                            <li>
                                <div id="tabs_article">
                                    <a href="#article/article" onclick="admin.article.prePost()">${postArticleLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_article-list">
                                    <a href="#article/article-list">${articleListLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_draft-list">
                                    <a href="#article/draft-list">${draftListLabel}</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div id="tabs_comment-list">
                            <a href="#comment-list">
                                <span class="icon-cmts"></span> ${commentListLabel}
                            </a>
                        </div>
                    </li>
                    <li>
                        <div id="tabToolsTitle" onclick="admin.collapseNav(this)">
                            <span class="icon-setting"></span>
                            ${ToolLabel}
                            <span class="icon-chevron-down right"></span>
                        </div>
                        <ul class="none" id="tabTools">
                            <li>
                                <div id="tabs_preference">
                                    <a href="#tools/preference">${preferenceLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_page-list">
                                    <a href="#tools/page-list">${navMgmtLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_link-list">
                                    <a href="#tools/link-list">${linkManagementLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_user-list">
                                    <a href="#tools/user-list">${userManageLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_plugin-list">
                                    <a href="#tools/plugin-list">${pluginMgmtLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_others">
                                    <a href="#tools/others">${othersLabel}</a>
                                </div>
                            </li>  
                        </ul>
                    </li>
                    <li>
                        <div id="tabs_about">
                            <a href="#about">
                                <span class="icon-info"></span> ${aboutLabel}
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="tabsPanel">
                <div id="tabsPanel_main" class="none"></div>
                <div id="tabsPanel_article" class="none"></div>
                <div id="tabsPanel_article-list" class="none"></div>
                <div id="tabsPanel_draft-list" class="none"></div>
                <div id="tabsPanel_link-list" class="none"></div>
                <div id="tabsPanel_preference" class="none"></div>
                <div id="tabsPanel_page-list" class="none"></div>
                <div id="tabsPanel_others" class="none"></div>
                <div id="tabsPanel_user-list" class="none"></div>
                <div id="tabsPanel_comment-list" class="none"></div>
                <div id="tabsPanel_plugin-list" class="none"></div>
                <div id="tabsPanel_about" class="none"></div>
            </div>
            <div class="clear"></div>
            <div class="footer">
                Powered by <a href="http://b3log.org" target="_blank">B3log 开源</a> • <a href="http://b3log.org/services/#solo" target="_blank">Solo</a> ${version}
            </div>
        </div>
        <script src="${staticServePath}/js/lib/compress/admin-lib.min.js"></script>
        <script src="${staticServePath}/js/lib/tiny_mce/tiny_mce.js"></script>
        <script src="${staticServePath}/js/common${miniPostfix}.js"></script>
        <#if "" == miniPostfix>
        <script src="${staticServePath}/js/admin/admin.js"></script>
        <script src="${staticServePath}/js/admin/editor.js"></script>
        <script src="${staticServePath}/js/admin/editorTinyMCE.js"></script>
        <script src="${staticServePath}/js/admin/editorKindEditor.js"></script>
        <script src="${staticServePath}/js/admin/editorCodeMirror.js"></script>
        <script src="${staticServePath}/js/admin/tablePaginate.js"></script>
        <script src="${staticServePath}/js/admin/article.js"></script>
        <script src="${staticServePath}/js/admin/comment.js"></script>
        <script src="${staticServePath}/js/admin/articleList.js"></script>
        <script src="${staticServePath}/js/admin/draftList.js"></script>
        <script src="${staticServePath}/js/admin/pageList.js"></script>
        <script src="${staticServePath}/js/admin/others.js"></script>
        <script src="${staticServePath}/js/admin/linkList.js"></script>
        <script src="${staticServePath}/js/admin/preference.js"></script>
        <script src="${staticServePath}/js/admin/pluginList.js"></script>
        <script src="${staticServePath}/js/admin/userList.js"></script>
        <script src="${staticServePath}/js/admin/commentList.js"></script>
        <script src="${staticServePath}/js/admin/plugin.js"></script>
        <script src="${staticServePath}/js/admin/main.js"></script>
        <script src="${staticServePath}/js/admin/about.js"></script>
        <#else>
        <script src="${staticServePath}/js/admin/latkeAdmin${miniPostfix}.js?${staticResourceVersion}"></script>
        </#if>
        <#include "admin-label.ftl">
        ${plugins}
        <script >
            admin.inited();
        </script>
    </body>
</html>