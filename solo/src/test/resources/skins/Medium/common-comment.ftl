<div id="${comment.oId}" class="comment__item">
    <img class="comment__avatar" src="${comment.commentThumbnailURL}"/>
    <main class="comment__body">
        <div class="fn-clear">
            <span class="comment__user">
                <#if "http://" == comment.commentURL>
                ${comment.commentName}
                <#else>
                    <a href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
                </#if>
            </span>
            <span class="ft-12">
                <#if comment.isReply>
                    <a class="ft-gray" href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"
                        onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 23);"
                        onmouseout="page.hideComment('${comment.commentOriginalCommentId}')">
                        <svg class="ft-gray"><use xlink:href="#icon-reply"></use></svg>
                        ${reply1Label} ${comment.commentOriginalCommentName}
                    </a>
                </#if>
                <time class="ft-fade"> • ${comment.commentDate2?string("yyyy-MM-dd")}</time>
            </span>


            <#if article.commentable>
                <a class="fn-right ft-green" href="javascript:replyTo('${comment.oId}')">
                    <svg><use xlink:href="#icon-reply"></use></svg> ${reply1Label}
                </a>
            </#if>
        </div>
        <div class="content-reset">
            ${comment.commentContent}
        </div>
    </main>
</div>