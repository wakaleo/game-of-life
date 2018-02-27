<div class="fn-clear article__wrap">
<#list articles as article>
    <article class="article__item
    <#if article_index == 0 || article_index == 10>article__item--big
    <#elseif article_index &gt; 0 && article_index &lt; 5>article__item--small
    <#elseif article_index &gt; 4 && article_index &lt; 8>article__item--big
    <#elseif article_index == 8 || article_index == 9>article__item--mid
    <#elseif article_index &gt; 10 && article_index &lt; 15>article__item--small
    <#elseif article_index &gt; 14 && article_index &lt; 18>article__item--big
    <#elseif article_index &gt; 17 && article_index &lt; 20>article__item--mid
    <#else>article__item--big
    </#if>">
        <header class="article__panel">
            <div class="article__main">
                <h2 class="article__title">
                    <a rel="bookmark" href="${servePath}${article.articlePermalink}">
                    ${article.articleTitle}
                    </a>
                    <#if article.articlePutTop>
                        <sup>
                        ${topArticleLabel}
                        </sup>
                    </#if>
                    <#if article.hasUpdated>
                        <sup>
                        ${updatedLabel}
                        </sup>
                    </#if>
                </h2>
                <div class="content-reset article__content">
                ${article.articleAbstract}
                </div>
            </div>

            <div class="article__meta ft-gray fn-flex">
                <time>
                ${article.articleCreateDate?string("yyyy-MM-dd")}
                </time> &nbsp;•&nbsp;
                <a href="${servePath}${article.articlePermalink}#comments" class="ft-gray">
                    ${article.articleCommentCount} ${commentLabel}
                </a> &nbsp;•&nbsp;
                <a href="${servePath}${article.articlePermalink}" class="ft-gray">
                ${article.articleViewCount} ${viewLabel}
                </a>
            </div>
        </header>
    </article>
</#list>
</div>

<#if 0 != paginationPageCount>
<nav class="pagination">
    <#if 1 != paginationPageNums?first>
        <a href="${servePath}${path}/${paginationPreviousPageNum}" class="pagination__item">&laquo;</a>
        <a class="pagination__item" href="${servePath}${path}/1">1</a>
        <span class="pagination__item pagination__omit">...</span>
    </#if>
    <#list paginationPageNums as paginationPageNum>
        <#if paginationPageNum == paginationCurrentPageNum>
            <span class="pagination__item pagination__item--active">${paginationPageNum}</span>
        <#else>
            <a class="pagination__item" href="${servePath}${path}/${paginationPageNum}">${paginationPageNum}</a>
        </#if>
    </#list>
    <#if paginationPageNums?last != paginationPageCount>
        <span class="pagination__item pagination__omit">...</span>
        <a href="${servePath}${path}/${paginationPageCount}" class="pagination__item">${paginationPageCount}</a>
        <a href="${servePath}${path}/${paginationNextPageNum}" class="pagination__item">&raquo;</a>
    </#if>
</nav>
</#if>