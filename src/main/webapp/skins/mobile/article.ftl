<#include "macro-head.ftl">
<#include "macro-comments.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${article.articleTitle} - ${blogTitle}">
        <meta name="keywords" content="${article.articleTags}" />
        <meta name="description" content="${article.articleAbstract?html}" />
        </@head>
        <#if previousArticlePermalink??>
            <link rel="prev" title="${previousArticleTitle}" href="${servePath}${previousArticlePermalink}">
        </#if>
        <#if nextArticlePermalink??>
            <link rel="next" title="${nextArticleTitle}" href="${servePath}${nextArticlePermalink}">
        </#if>
            <!-- Open Graph -->
            <meta property="og:locale" content="zh_CN"/>
            <meta property="og:type" content="article"/>
            <meta property="og:title" content="${article.articleTitle}"/>
            <meta property="og:description" content="${article.articleAbstract?html}"/>
            <meta property="og:image" content="${article.authorThumbnailURL}"/>
            <meta property="og:url" content="${servePath}${article.articlePermalink}"/>
            <meta property="og:site_name" content="Solo"/>
            <!-- Twitter Card -->
            <meta name="twitter:card" content="summary"/>
            <meta name="twitter:description" content="${article.articleAbstract?html}"/>
            <meta name="twitter:title" content="${article.articleTitle}"/>
            <meta name="twitter:image" content="${article.authorThumbnailURL}"/>
            <meta name="twitter:url" content="${servePath}${article.articlePermalink}"/>
            <meta name="twitter:site" content="@DL88250"/>
            <meta name="twitter:creator" content="@DL88250"/>
    </head>
    <body class="classic-wptouch-bg">
        <#include "header.ftl">
        <div class="content single">
            <div class="post">
                <a class="sh2" href="${servePath}${article.articlePermalink}" rel="bookmark">${article.articleTitle}</a>
                <div class="single-post-meta-top">
                    <#if article.hasUpdated>
                    ${article.articleUpdateDate?string("yyyy-MM-dd HH:mm:ss")}
                    <#else>
                    ${article.articleCreateDate?string("yyyy-MM-dd HH:mm:ss")}
                    </#if>
                    &rsaquo; ${article.authorName}<br />
                    <a rel="nofollow" href="#comments">${skipToComment}</a>
                </div>
            </div>
            <div class="clearer"></div>
            <div class="post article-body" id="post-${article.oId}">
                <div id="singlentry" class="left-justified">
                    ${article.articleContent}
                    <#if "" != article.articleSign.signHTML?trim>
                    <div class=""><!--TODO sign class-->
                        ${article.articleSign.signHTML}
                    </div>
                    </#if>
                </div>
                <!-- Categories and Tags post footer -->
                <div class="single-post-meta-bottom">
                    ${tags1Label}
                    <#list article.articleTags?split(",") as articleTag>
                    <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}" rel="tag">${articleTag}</a><#if articleTag_has_next>,</#if>
                    </#list>
                </div>   
                <ul id="post-options">
                    <#if nextArticlePermalink??>
                    <li><a href="${servePath}${nextArticlePermalink}" id="oprev"></a></li>
                    </#if>
                    <li><a href="mailto:?subject=${article.authorName} - ${article.articleTitle}&body=Check out this post: ${servePath}${article.articlePermalink}" id="omail"></a></li>
                    <li><a href="javascript:void(0)" onclick="window.open('http://service.weibo.com/share/share.php?url=${servePath}${article.articlePermalink}&title=B3LOG%20-%20${article.articleTitle}', '_blank');" id="otweet"></a></li>		
                    <li><a href="javascript:void(0)" id="obook"></a></li>
                    <#if previousArticlePermalink??>
                    <li><a href="${servePath}${previousArticlePermalink}" id="onext"></a></li>
                    </#if>
                </ul>
            </div>
            <div id="bookmark-box" style="display:none">
                <ul>
                    <li><a  href="http://del.icio.us/post?url=${servePath}/?p=12&title=${article.articleTitle}" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/delicious.jpg" alt="" /> Del.icio.us</a></li>
                    <li><a href="http://digg.com/submit?phase=2&url=${servePath}/?p=12&title=${article.articleTitle}" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/digg.jpg" alt="" /> Digg</a></li>
                    <li><a href="http://technorati.com/faves?add=${servePath}/?p=12" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/technorati.jpg" alt="" /> Technorati</a></li>
                    <li><a href="http://ma.gnolia.com/bookmarklet/add?url=${servePath}/?p=12&title=${article.articleTitle}" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/magnolia.jpg" alt="" /> Magnolia</a></li>
                    <li><a href="http://www.newsvine.com/_wine/save?popoff=0&u=${servePath}/?p=12&h=${article.articleTitle}" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/newsvine.jpg" target="_blank"> Newsvine</a></li>
                    <li class="noborder"><a href="http://reddit.com/submit?url=${servePath}/?p=12&title=${article.articleTitle}" target="_blank"><img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/bookmarks/reddit.jpg" alt="" /> Reddit</a></li>
                </ul>
            </div>
            <div id="externalRelevantArticles" class="post"></div>
            <@comments commentList=articleComments article=article></@comments>
        </div>
        <#include "footer.ftl">    
        <@comment_script oId=article.oId>
        page.tips.externalRelevantArticlesDisplayCount = "${externalRelevantArticlesDisplayCount}";
        <#if 0 != randomArticlesDisplayCount>
        page.loadRandomArticles();
        </#if>
        <#if 0 != relevantArticlesDisplayCount>
        page.loadRelevantArticles('${article.oId}', '<h4>${relevantArticles1Label}</h4>');
        </#if>
        <#if 0 != externalRelevantArticlesDisplayCount>
        page.loadExternalRelevantArticles("<#list article.articleTags?split(",") as articleTag>${articleTag}<#if articleTag_has_next>,</#if></#list>");
        </#if>
        </@comment_script>    
    </body>
</html>