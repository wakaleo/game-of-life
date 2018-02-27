<li id="${comment.oId}">
    <div class="comwrap">
        <div class="comtop"><!--TODO comment->comment_approved == '0') : comtop preview;-->
            <img alt='${comment.commentName}' src='${comment.commentThumbnailURL}' class='avatar avatar-64 photo' height='64' width='64' />
            <div class="com-author">
            <#if "http://" == comment.commentURL>
                <a>${comment.commentName}</a>
            <#else>
                <a href='${comment.commentURL}' rel='external nofollow' target="_blank" class='url'>${comment.commentName}</a>
            </#if>
            <#if comment.isReply>
                @
                <a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}">${comment.commentOriginalCommentName}</a>
            </#if>
            </div>
        <#if article.commentable>
            <div class="comdater">
                <!--<span>TODO wptouch_moderate_comment_link(get_comment_ID())</span>-->
            ${comment.commentDate2?string("yyyy-MM-dd HH:mm:ss")}
                <a rel="nofollow" href="javascript:replyTo('${comment.oId}');">${replyLabel}</a>
            </div>
        </#if>
        </div><!--end comtop-->
        <div class="combody">
            <p>${comment.commentContent}</p>
        </div>
    </div>
</li>