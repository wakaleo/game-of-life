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
 * draft list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.4, Feb 23, 2013
 */

/* draft-list 相关操作 */
admin.draftList = {
    tablePagination:  new TablePaginate("draft"),
    
    /* 
     * 初始化 table, pagination, comments dialog 
     */
    init: function (page) {
        this.tablePagination.buildTable([{
            text: Label.titleLabel,
            index: "title",
            minWidth: 110,
            style: "padding-left: 12px;font-size:14px;"
        }, {
            text: Label.authorLabel,
            index: "author",
            width: 150,
            style: "padding-left: 12px;"
        }, {
            text: Label.commentLabel,
            index: "comments",
            width: 80,
            style: "padding-left: 12px;"
        }, {
            text: Label.viewLabel,
            width: 60,
            index: "articleViewCount",
            style: "padding-left: 12px;"
        }, {
            text: Label.createDateLabel,
            index: "date",
            width: 90,
            style: "padding-left: 12px;"
        }]);
        this.tablePagination.initPagination();
        this.tablePagination.initCommentsDialog();
        this.getList(page);
    },

    /* 
     * 根据当前页码获取列表
     * @pagNum 当前页码
     */
    getList: function (pageNum) {
        $("#loadMsg").text(Label.loadingLabel);
        var that = this;
        
        $.ajax({
            url: latkeConfig.servePath + "/console/articles/status/unpublished/" + pageNum + "/" + Label.PAGE_SIZE + "/" +  Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                
                var articles = result.articles,
                articleData = [];
                for (var i = 0; i < articles.length; i++) {
                    articleData[i] = {};
                    articleData[i].tags = articles[i].articleTags;
                    articleData[i].date = $.bowknot.getDate(articles[i].articleCreateTime);
                    articleData[i].comments = articles[i].articleCommentCount;
                    articleData[i].articleViewCount = articles[i].articleViewCount;
                    articleData[i].author = articles[i].authorName;
                    articleData[i].title = "<a class='no-underline' href='" + latkeConfig.servePath +
                    articles[i].articlePermalink + "' target='_blank'>" + 
                    articles[i].articleTitle + "</a><span class='table-tag'>" + articles[i].articleTags + "</span>";
                    articleData[i].expendRow = "<a href='javascript:void(0)' onclick=\"admin.article.get('" + articles[i].oId + "', false);\">" + Label.updateLabel + "</a>  \
                                <a href='javascript:void(0)' onclick=\"admin.article.del('" + articles[i].oId + "', 'draft', '" + articles[i].articleTitle + "')\">" + Label.removeLabel + "</a>  \
                                <a href='javascript:void(0)' onclick=\"admin.comment.open('" + articles[i].oId + "', 'draft')\">" + Label.commentLabel + "</a>";
                }
                    
                that.tablePagination.updateTablePagination(articleData, pageNum, result.pagination);
                
                $("#loadMsg").text("");
            }
        });
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["draft-list"] =  {
    "obj": admin.draftList,
    "init": admin.draftList.init,
    "refresh": admin.draftList.getList
};