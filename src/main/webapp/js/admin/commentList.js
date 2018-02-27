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
 * comment list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.5, Feb 23, 2013
 */

/* comment-list 相关操作 */
admin.commentList = {
    tablePagination:  new TablePaginate("comment"),
    pageInfo: {
        currentPage: 1
    },
    
    /* 
     * 初始化 table, pagination, comments dialog 
     */
    init: function (page) {
        this.tablePagination.buildTable([{
            text: Label.commentContentLabel,
            index: "content",
            minWidth: 300,
            style: "padding-left: 12px;"
        }, {
            text: Label.authorLabel,
            index: "title",
            width: 230,
            style: "padding-left: 12px;"
        }, {
            text: Label.createDateLabel,
            index: "date",
            width: 90,
            style: "padding-left: 12px;"
        }]);
        this.tablePagination.initPagination();
        this.getList(page);
    },

    /* 
     * 根据当前页码获取列表
     * @pagNum 当前页码
     */
    getList: function (pageNum) {
        var that = this;
        $("#loadMsg").text(Label.loadingLabel);
        
        $.ajax({
            url: latkeConfig.servePath + "/console/comments/" + pageNum + "/" + Label.PAGE_SIZE + "/" +  Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                
                that.pageInfo.currentPage = pageNum;
                var comments = result.comments,
                commentsData = [];
                for (var i = 0; i < comments.length; i++) {
                    var type = "Article"
                    if (comments[i].type === "pageComment") {
                        type = "Page"
                    }
                    
                    commentsData[i] = {};
                    
                    commentsData[i].content = '<div class="content-reset">' + Util.replaceEmString(comments[i].commentContent) +
                    "</div><span class='table-tag'> on &nbsp;&nbsp;</span><a href='" + latkeConfig.servePath + comments[i].commentSharpURL +
                    "' target='_blank'>" + comments[i].commentTitle +
                    "</a>";
                
                    commentsData[i].expendRow = "<a href='javascript:void(0)' onclick=\"admin.commentList.del('" +
                    comments[i].oId + "', '" + type + "')\">" + Label.removeLabel + "</a>";
                
                    commentsData[i].title = "<img class='small-head' src='" + 
                    comments[i].commentThumbnailURL + "'/>";
                    if ("http://" === comments[i].commentURL) {
                        commentsData[i].title += comments[i].commentName;
                    } else {
                        commentsData[i].title += "<a href='" + comments[i].commentURL +
                        "' target='_blank' class='no-underline'>" + comments[i].commentName + 
                        "</a>";
                    }                    
                    commentsData[i].title += "<br/><a href='mailto:" + comments[i].commentEmail +
                    "'>" + comments[i].commentEmail + "</a>";                
                    
                    commentsData[i].date = $.bowknot.getDate(comments[i].commentTime);
                }
                
                that.tablePagination.updateTablePagination(commentsData, pageNum, result.pagination);
                
                $("#loadMsg").text("");
            }
        });
    },
    
    /* 
     * 删除评论
     * @id 评论 id 
     * @type 评论类型：文章/自定义页面
     */
    del: function (id, type) {
        if (confirm(Label.confirmRemoveLabel + Label.commentLabel + "?")) {
            $("#loadMsg").text(Label.loadingLabel);
            
            $.ajax({
                url: latkeConfig.servePath + "/console/" + type.toLowerCase() + "/comment/" + id,
                type: "DELETE",
                cache: false,
                success: function(result, textStatus){
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    
                    admin.commentList.getList(admin.commentList.pageInfo.currentPage);
                    
                    $("#loadMsg").text("");
                }
            });
        }
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["comment-list"] =  {
    "obj": admin.commentList,
    "init": admin.commentList.init,
    "refresh": admin.commentList.getList
}