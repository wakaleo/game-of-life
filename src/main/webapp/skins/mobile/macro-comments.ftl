<#macro comments commentList article>
<!-- Let's rock the comments -->
<!-- You can start editing below here... but make a backup first!  -->
<div class="comment_wrapper" id="comments">
    <#if 0 lt commentList?size>
    <h3 onclick="bnc_showhide_coms_toggle();" id="com-head">
        ${commentList?size} ${responses}
    </h3>
    </#if>
    <ol class="commentlist" id="commentlist">
        <#list commentList as comment>
        <#include "common-comment.ftl"/>
        </#list>
    </ol>
    <#if article.commentable>
    <div id="textinputwrap">
        <div id="refresher" style="display:none;">
            <img src="${staticServePath}/skins/${skinDirName}/images/good.png" alt="checkmark" />
            <h3>${commentSuccess}</h3>
            <a href="javascript:this.location.reload();">${refresh2CComment}</a>
        </div>
        <div id="commentForm">
            <h3 id="respond">${postCommentsLabel}</h3>
            <#if !isLoggedIn>
            <p>
                <input type="text" id="commentName" size="22" tabindex="1"/>
                <label for="author">${commentNameLabel} *</label>
            </p>

            <p>
                <input type="text" id="commentEmail" size="22" tabindex="2" />
                <label for="email">${commentEmailLabel} *</label>
            </p>

            <p>
                <input type="text" id="commentURL" size="22" tabindex="3" />
                <label for="url">${commentURLLabel}</label>
            </p>
            </#if>
            <p>
                <span id="commentErrorTip" style="display:none;"></span>
            </p>
            <p><textarea id="comment" tabindex="4"></textarea></p>
            <#if !isLoggedIn>
            <p>
                <input type="text" id="commentValidate" tabindex="5" />
                <label for="url">${captchaLabel}</label>
                <img id="captcha" alt="validate" src="${servePath}/captcha.do" />
            </p>
            </#if>
            <p>
                <input class="reply-button" id="submitCommentButton" type="submit" onclick="page.submitComment();" value="${submmitCommentLabel}"  tabindex="6" />
            <div id="loading" style="display:none">
                <img src="${staticServePath}/skins/${skinDirName}/themes/core/core-images/comment-ajax-loader.gif" alt="" /> <p>${publishing}</p>
            </div>
            </p>
        </div>
    </div>
    </#if><!--textinputwrap div-->
</div>
</#macro>

<#macro comment_script oId>
<script type="text/javascript" src="${staticServePath}/js/page${miniPostfix}.js?${staticResourceVersion}" charset="utf-8"></script>
<script type="text/javascript">
        Page.prototype.submitComment = function(commentId, state) {
            if (!state) {
                state = '';
            }
            var tips = this.tips,
                    type = "article";
            if (tips.externalRelevantArticlesDisplayCount === undefined) {
                type = "page";
            }

            if (this.validateComment(state)) {
                $("#submitCommentButton" + state).attr("disabled", "disabled");
                $("#commentErrorTip" + state).show().html(this.tips.loadingLabel);
                var requestJSONObject = {
                    "oId": tips.oId,
                    "commentContent": $("#comment" + state).val().replace(/(^\s*)|(\s*$)/g, "")
                };

                if (!$("#admin").data("login")) {
                    requestJSONObject = {
                        "oId": tips.oId,
                        "commentContent": $("#comment" + state).val().replace(/(^\s*)|(\s*$)/g, ""),
                        "commentEmail": $("#commentEmail" + state).val(),
                        "commentURL": Util.proessURL($("#commentURL" + state).val().replace(/(^\s*)|(\s*$)/g, "")),
                        "commentName": $("#commentName" + state).val().replace(/(^\s*)|(\s*$)/g, ""),
                        "captcha": $("#commentValidate" + state).val()
                    };
                    Cookie.createCookie("commentName", requestJSONObject.commentName, 365);
                    Cookie.createCookie("commentEmail", requestJSONObject.commentEmail, 365);
                    Cookie.createCookie("commentURL", $("#commentURL" + state).val().replace(/(^\s*)|(\s*$)/g, ""), 365);
                }
                
                if (state === "Reply") {
                    requestJSONObject.commentOriginalCommentId = commentId;
                }
                $.ajax({
                    type: "POST",
                    url: latkeConfig.servePath + "/add-" + type + "-comment.do",
                    cache: false,
                    contentType: "application/json",
                    data: JSON.stringify(requestJSONObject),
                    success: function(result) {
                        $("#submitCommentButton" + state).removeAttr("disabled");

                        if (!result.sc) {
                            $("#commentValidate" + state).val("").focus();
                            $("#commentErrorTip" + state).html(result.msg);
                            $("#captcha" + state).attr("src", "/captcha.do?code=" + Math.random());
                            $wpt('#commentErrorTip' + state).show();
                            $wpt("#loading").fadeOut(400);
                            return;
                        }

                        $wpt("#commentForm").hide();
                        $wpt("#loading").fadeOut(400);
                        $wpt("#refresher").fadeIn(400);
                        $("#comment" + state).val("");
                        $("#commentValidate" + state).val("");
                        $("#replyForm").remove();

                    }, // end success 
                    error: function() {
                    } //end error
                });
            }

        };

        var replyTo = function(id) {
            var commentFormHTML = "<div id='replyForm'>";
            page.addReplyForm(id, commentFormHTML, "</div>");
        };

        var page = new Page({
            "nameTooLongLabel": "${nameTooLongLabel}",
            "mailCannotEmptyLabel": "${mailCannotEmptyLabel}",
            "mailInvalidLabel": "${mailInvalidLabel}",
            "commentContentCannotEmptyLabel": "${commentContentCannotEmptyLabel}",
            "captchaCannotEmptyLabel": "${captchaCannotEmptyLabel}",
            "loadingLabel": "${loadingLabel}",
            "oId": "${oId}",
            "skinDirName": "${skinDirName}",
            "blogHost": "${blogHost}",
            "randomArticles1Label": "${randomArticles1Label}",
            "externalRelevantArticles1Label": "${externalRelevantArticles1Label}"
        });

        (function() {
            page.load();
            // emotions
            page.replaceCommentsEm("#commentlist .combody");
            <#nested>
        })();
</script>
</#macro>