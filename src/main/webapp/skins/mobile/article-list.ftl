<div class="content">
	<div class="result-text"><!--TODO wptouch_core_body_result_text()--></div>
    <#list articles as article>
    <div class="post" id="post-${article.oId}">
		<#if 0 lt article.articleCommentCount>
		<div class="comment-bubble">${article.articleCommentCount}</div>
		</#if>
		<script type="text/javascript">
			$wpt(document).ready(function(){
				$wpt("a#arrow-${article.oId}").bind( touchStartOrClick, function(e) {
					$wpt(this).toggleClass("post-arrow-down");
					$wpt('#entry-${article.oId}').wptouchFadeToggle(500);
				});	
			 });					
		</script>
		<a class="post-arrow" id="arrow-${article.oId}" href="javascript: return false;"></a>
		<div class="calendar">
			<div class="cal-month month-${article.articleUpdateDate?string("MM")}">${article.articleUpdateDate?string("MM")}</div>
			<div class="cal-date">${article.articleUpdateDate?string("dd")}</div>
		</div>	
		<a rel="bookmark" class="h2" href="${servePath}${article.articlePermalink}">${article.articleTitle}</a>
		<div class="post-author">
			<span class="lead">By</span> ${article.authorName}<br />
			<span class="lead">${tags1Label}</span> 
            <#list article.articleTags?split(",") as articleTag>
                <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                    ${articleTag}</a><#if articleTag_has_next>,</#if>
            </#list>		
		</div>	
		<div class="clearer"></div>	
        <div id="entry-${article.oId}" style="display:none" class="mainentry left-justified">
			${article.articleAbstract}
			<a class="read-more" href="${servePath}${article.articlePermalink}">${readThisPost}</a>
        </div>  
      </div>
    </div>
    </#list>
<!--TODO ajax load page
	<div id="call${paginationCurrentPageNum}" class="ajax-load-more">
		<div id="spinner${paginationCurrentPageNum}" class="spin"	 style="display:none"></div>
		<a class="ajax" href="javascript:void(0)" onclick="$wpt('#spinner${paginationCurrentPageNum}').fadeIn(200); $wpt('#ajaxentries${paginationCurrentPageNum}').load('${path}/${paginationPreviousPageNum}', {}, function(){ $wpt('#call${paginationCurrentPageNum}').fadeOut();});">
			Load more entries...
		</a>
	</div>
	<div id="ajaxentries${paginationCurrentPageNum}"></div>
-->
    <#if 0 != paginationPageCount>
    <div class="ajax-load-more">
        <#if 1 != paginationPageNums?first>
        <a href="${servePath}${path}/1">${firstPageLabel}</a>
        <a id="previousPage" href="${servePath}${path}/${paginationPreviousPageNum}">${previousPageLabel}</a>
        </#if>
        <#list paginationPageNums as paginationPageNum>
        <#if paginationPageNum == paginationCurrentPageNum>
        <a href="${servePath}${path}/${paginationPageNum}" class="selected">${paginationPageNum}</a>
        <#else>
        <a href="${servePath}${path}/${paginationPageNum}">${paginationPageNum}</a>
        </#if>
        </#list>
        <#if paginationPageNums?last != paginationPageCount>
        <a id="nextPage" href="${servePath}${path}/${paginationNextPageNum}">${nextPagePabel}</a>
        <a href="${servePath}${path}/${paginationPageCount}">${lastPageLabel}</a>
        </#if>
        &nbsp;&nbsp;${sumLabel} ${paginationPageCount} ${pageLabel}
    </div>
    </#if>
</div>