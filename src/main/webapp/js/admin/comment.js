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
/**
 *  common comment for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.9, May 28, 2013
 */

admin.comment = { 
    /*
     * 打开评论窗口
     * @id 该评论对应的 id
     * @fromId 该评论来自文章/草稿/自定义页面
     */
    open: function (id, fromId) {
        this.getList(id, fromId);
        $("#" + fromId + "Comments").dialog("open");
    },
    
    /*
     * 获取评论列表
     * 
     * @onId 该评论对应的实体 id，可能是文章，也可能是自定义页面
     * @fromId 该评论来自文章/草稿/自定义页面
     */
    getList: function (onId, fromId) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");
        $("#" + fromId + "Comments").html("");
        
        var from = "article";
        if (fromId === "page") {
            from = "page";
        }
        
        $.ajax({
            url: latkeConfig.servePath + "/console/comments/" + from + "/" + onId ,
            type: "GET",
            cache: false,
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                
                var comments = result.comments,
                commentsHTML = '';
                for (var i = 0; i < comments.length; i++) {
                    var hrefHTML = "<a target='_blank' href='" + comments[i].commentURL + "'>",
                    content = comments[i].commentContent,
                    contentHTML = Util.replaceEmString(content);
                        
                    if (comments[i].commentURL === "http://") {
                        hrefHTML = "<a target='_blank'>";
                    }

                    commentsHTML += "<div class='comment-title'><span class='left'>"
                    + hrefHTML + comments[i].commentName + "</a>";

                    if (comments[i].commentOriginalCommentName) {
                        commentsHTML += "@" + comments[i].commentOriginalCommentName;
                    }
                    commentsHTML += "</span><span title='" + Label.removeLabel + "' class='right deleteIcon' onclick=\"admin.comment.del('"
                    + comments[i].oId + "', '" + fromId + "', '" + onId + "')\"></span><span class='right'><a href='mailto:"
                    + comments[i].commentEmail + "'>" + comments[i].commentEmail + "</a>&nbsp;&nbsp;"
                    + $.bowknot.getDate(comments[i].commentTime)
                    + "&nbsp;</span><div class='clear'></div></div><div class='margin12'>"
                    + contentHTML + "</div>";
                }
                if ("" === commentsHTML) {
                    commentsHTML = Label.noCommentLabel;
                }
                
                $("#" + fromId + "Comments").html(commentsHTML);
                
                $("#loadMsg").text("");
            }
        });
    },
    
    /*
     * 删除评论
     * @id 评论 id
     * @fromId 该评论来自文章/草稿/自定义页面
     * @articleId 该评论对应的实体 id，可能是文章，也可能是自定义页面
     */
    del: function (id, fromId, articleId) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.commentLabel + "?");
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            var from = "article";
            if (fromId === "page") {
                from = "page";
            }
            
            $.ajax({
                url: latkeConfig.servePath + "/console/" + from + "/comment/" + id,
                type: "DELETE",
                cache: false,
                success: function(result, textStatus){
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    
                    admin.comment.getList(articleId, fromId);
                    
                    $("#loadMsg").text("");
                }
            });
        }
    }
};
