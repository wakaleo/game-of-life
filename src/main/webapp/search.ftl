<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width">
    <meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)">
    <title>${keyword} - ${blogTitle}${searchLabel}</title>
    <link type="text/css" rel="stylesheet"
          href="${staticServePath}/css/default-init${miniPostfix}.css?${staticResourceVersion}" charset="utf-8"/>
    <link rel="icon" type="image/png" href="${staticServePath}/favicon.png"/>
    <link rel="apple-touch-icon" href="${staticServePath}/favicon.png">
</head>
<body>
<div class="search__header fn-clear">
    <a href="${servePath}"><img class="fn-left" width="44" border="0" alt="Solo" title="Solo" src="${staticServePath}/images/logo.png"/></a>
    <div class="search__input fn-left">
        <input value="${keyword}" id="keyword" onkeypress="if(event.keyCode===13){document.getElementById('searchBtn').click()}">
        <button id="searchBtn" onclick="window.location.href='${servePath}/search?keyword=' + document.getElementById('keyword').value">搜索</button>
    </div>
    <span class="fn-right">
    <#if isLoggedIn>
        <a href="${servePath}/admin-index.do#main">${adminLabel}</a> &nbsp;
        <a href="${logoutURL}">${logoutLabel}</a>
    <#else>
        <a href="${loginURL}">${loginLabel}</a>
        &nbsp;   <a href="${servePath}/register">${registerLabel}</a>
    </#if>
        </span>
</div>

<div class="search">
    <div class="search__articles">
    <#list articles as article>
        <article>
            <header>
                <h1>
                    <a rel="bookmark" href="${servePath}${article.articlePermalink}">
                    ${article.articleTitle}
                    </a>
                </h1>

                <div class="meta">
                    <time>
                    ${article.articleCreateDate?string("yyyy-MM-dd")}
                    </time>
                    &nbsp;
                ${article.articleCommentCount} ${commentLabel}
                    &nbsp;
                ${article.articleViewCount} ${viewLabel}
                </div>
            </header>
            <div class="content-reset">
            ${article.articleAbstract}
            </div>
            <footer>
                <#list article.articleTags?split(",") as articleTag>
                    <a class="tag" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>
                    <#if articleTag_has_next> · ‎</#if>
                </#list>
            </footer>
        </article>
    </#list>
    </div>


<#if 0 != articles?size>
    <nav class="search__pagination">
        <#if 1 != pagination.paginationPageNums?first>
            <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationCurrentPageNum - 1}">&laquo;</a>
            <a href="${servePath}/search?keyword=${keyword}&p=1">1</a> <span class="page-number">...</span>
        </#if>
        <#list pagination.paginationPageNums as paginationPageNum>
            <#if paginationPageNum == pagination.paginationCurrentPageNum>
                <span>${paginationPageNum}</span>
            <#else>
                <a href="${servePath}/search?keyword=${keyword}&p=${paginationPageNum}">${paginationPageNum}</a>
            </#if>
        </#list>
        <#if pagination.paginationPageNums?last != pagination.paginationPageCount>
            <span>...</span>
            <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationPageCount}">${pagination.paginationPageCount}</a>
            <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationCurrentPageNum + 1}">&raquo;</a>
        </#if>
    </nav>
<#else>
No Result, Return to <a href="${servePath}">Index</a> or <a href="https://hacpai.com">HacPai</a>.
</#if>
</div>

<div class="footerWrapper">
    <div class="footer">
        Powered by <a href="http://b3log.org" target="_blank">B3log 开源</a> • Solo ${version}
    </div>
</div>
</body>
</html>
