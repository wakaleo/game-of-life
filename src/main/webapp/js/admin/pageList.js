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
 * page list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.3.6, Sep 21, 2017
 */

/* page-list 相关操作 */
admin.pageList = {
    currentEditorType: '',
    tablePagination: new TablePaginate("page"),
    pageInfo: {
        currentCount: 1,
        pageCount: 1,
        currentPage: 1
    },
    id: "",
    type: "link",
    /* 
     * 初始化 table, pagination, comments dialog
     */
    init: function (page) {
        this.tablePagination.buildTable([{
                text: "",
                index: "pageOrder",
                width: 60,
                style: "padding-left: 12px;font-size:14px;"
            }, {
                style: "padding-left: 12px;",
                text: Label.titleLabel,
                index: "pageTitle",
                width: 300
            }, {
                style: "padding-left: 12px;",
                text: Label.permalinkLabel,
                index: "pagePermalink",
                minWidth: 300
            }, {
                style: "padding-left: 12px;",
                text: Label.openMethodLabel,
                index: "pageTarget",
                width: 120
            }, {
                style: "padding-left: 12px;",
                text: Label.typeLabel,
                index: "pageType",
                width: 80
            }, {
                text: Label.commentLabel,
                index: "comments",
                width: 80,
                style: "padding-left: 12px;"
            }]);
        this.tablePagination.initPagination();
        this.tablePagination.initCommentsDialog();
        this.getList(page);

        var language = Label.localeString.substring(0, 2);
        if (language === "zh") {
            language = "zh-cn";
        }

        admin.pageList.currentEditorType = Label.editorType;
        admin.editors.pageEditor = new SoloEditor({
            language: language,
            kind: "all",
            id: "pageContent"
        });

        // select type
        $(".fn-type").click(function () {
            var $it = $(this);
            if ($it.hasClass("selected")) {
                return;
            }

            $(".fn-type").removeClass("selected");
            $it.addClass("selected");

            admin.pageList.type = $it.data("type");

            if (admin.pageList.type === "page") {
                $("#pagePagePanel").slideDown();

                // 使用 CodeMirror 编辑器时，当编辑器初始之前，元素为 display:none 时，行号显示不正常
                if (Label.editorType === "CodeMirror-Markdown"
                        && admin.editors.pageEditor.getContent() === "") {
                    admin.editors.pageEditor.setContent("");
                }
            } else {
                $("#pagePagePanel").slideUp();
            }
        });
    },
    /* 
     * 根据当前页码获取列表
     * @pagNum 当前页码
     */
    getList: function (pageNum) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");
        var that = this;

        $.ajax({
            url: latkeConfig.servePath + "/console/pages/" + pageNum + "/" + Label.PAGE_SIZE + "/" + Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var pages = result.pages;
                var pageData = [];
                admin.pageList.pageInfo.currentCount = pages.length;
                admin.pageList.pageInfo.pageCount = result.pagination.paginationPageCount === 0 ? 1 : result.pagination.paginationPageCount;
                for (var i = 0; i < pages.length; i++) {
                    pageData[i] = {};
                    if (i === 0) {
                        if (pages.length === 1) {
                            pageData[i].pageOrder = "";
                        } else {
                            pageData[i].pageOrder = '<div class="table-center" style="width:14px">\
                                        <span onclick="admin.pageList.changeOrder(' + pages[i].oId + ', ' + i + ', \'down\');" \
                                        class="icon-move-down"></span></div>';
                        }
                    } else if (i === pages.length - 1) {
                        pageData[i].pageOrder = '<div class="table-center" style="width:14px">\
                                    <span onclick="admin.pageList.changeOrder(' + pages[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                                    </div>';
                    } else {
                        pageData[i].pageOrder = '<div class="table-center" style="width:38px">\
                                    <span onclick="admin.pageList.changeOrder(' + pages[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                                    <span onclick="admin.pageList.changeOrder(' + pages[i].oId + ', ' + i + ', \'down\');" class="icon-move-down"></span>\
                                    </div>';
                    }

                    var pageIcon = '';
                    if (pages[i].pageIcon !== '') {
                      pageIcon = "<img class='navigation-icon' src='" + pages[i].pageIcon + "'/> ";
                    }
                    pageData[i].pageTitle = pageIcon + "<a class='no-underline' href='" + pages[i].pagePermalink + "' target='_blank'>" +
                            pages[i].pageTitle + "</a>";
                    pageData[i].pagePermalink = "<a class='no-underline' href='" + pages[i].pagePermalink + "' target='_blank'>"
                            + pages[i].pagePermalink + "</a>";
                    pageData[i].pageTarget = pages[i].pageOpenTarget;
                    pageData[i].pageType = pages[i].pageType;
                    pageData[i].comments = pages[i].pageCommentCount;
                    pageData[i].expendRow = "<span><a href='" + pages[i].pagePermalink + "' target='_blank'>" + Label.viewLabel + "</a>  \
                                <a href='javascript:void(0)' onclick=\"admin.pageList.get('" + pages[i].oId + "')\">" + Label.updateLabel + "</a>\
                                <a href='javascript:void(0)' onclick=\"admin.pageList.del('" + pages[i].oId + "', '" + pages[i].pageTitle + "')\">" + Label.removeLabel + "</a>\
                                <a href='javascript:void(0)' onclick=\"admin.comment.open('" + pages[i].oId + "', 'page')\">" + Label.commentLabel + "</a></span>";
                }

                that.tablePagination.updateTablePagination(pageData, pageNum, result.pagination);

                $("#loadMsg").text("");
            }
        });
    },
    /*
     * 获取自定义页面
     * @id 自定义页面 id
     */
    get: function (id) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");

        $.ajax({
            url: latkeConfig.servePath + "/console/page/" + id,
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                admin.pageList.id = id;

                $("#pageTitle").val(result.page.pageTitle);
                $("#pagePermalink").val(result.page.pagePermalink);
                $("#pageTarget").val(result.page.pageOpenTarget);
                $("#pageIcon").val(result.page.pageIcon);
                if (result.page.pageType === "page") {
                    $($(".fn-type").get(1)).click();
                } else {
                    $($(".fn-type").get(0)).click();
                }
                $("#pageCommentable").prop("checked", result.page.pageCommentable);

                if (admin.pageList.currentEditorType !== result.page.pageEditorType) {
                    admin.editors.pageEditor.remove();

                    admin.pageList.currentEditorType = result.page.pageEditorType;
                    admin.editors.pageEditor.init(result.page.pageEditorType);
                }

                admin.editors.pageEditor.setContent(result.page.pageContent);

                $("#loadMsg").text("");
            }
        });
    },
    /* 
     * 删除自定义页面
     * @id 自定义页面 id
     * @title 自定义页面标题
     */
    del: function (id, title) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.navLabel + '"' + title + '"?');
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            $.ajax({
                url: latkeConfig.servePath + "/console/page/" + id,
                type: "DELETE",
                cache: false,
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    var pageNum = admin.pageList.pageInfo.currentPage;
                    if (admin.pageList.pageInfo.currentCount === 1 && admin.pageList.pageInfo.pageCount !== 1 &&
                            admin.pageList.pageInfo.currentPage === admin.pageList.pageInfo.pageCount) {
                        admin.pageList.pageInfo.pageCount--;
                        pageNum = admin.pageList.pageInfo.pageCount;
                    }
                    var hashList = window.location.hash.split("/");
                    if (pageNum == hashList[hashList.length - 1]) {
                        admin.pageList.getList(pageNum);
                    } else {
                        admin.setHashByPage(pageNum);
                    }

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /*
     * 添加自定义页面
     */
    add: function () {
        if (this.validate()) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            var pageContent = admin.editors.pageEditor.getContent();

            var pagePermalink = $("#pagePermalink").val().replace(/(^\s*)|(\s*$)/g, "");
            if (admin.pageList.type === "link") {
                pagePermalink = Util.proessURL(pagePermalink);
            }

            var requestJSONObject = {
                "page": {
                    "pageTitle": $("#pageTitle").val(),
                    "pageContent": pageContent,
                    "pagePermalink": pagePermalink,
                    "pageCommentable": $("#pageCommentable").prop("checked"),
                    "pageType": admin.pageList.type,
                    "pageOpenTarget": $("#pageTarget").val(),
                    "pageIcon": $("#pageIcon").val()
                }
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/page/",
                type: "POST",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    admin.pageList.id = "";
                    $("#pagePermalink").val("");
                    $("#pageTitle").val("");
                    $("#pageIcon").val("");
                    $("#pageCommentable").prop("cheked", false);
                    $("#pageTarget").val("_self");
                    $($(".fn-type").get(0)).click();

                    admin.editors.pageEditor.setContent("");

                    if (admin.pageList.pageInfo.currentCount === Label.PAGE_SIZE &&
                            admin.pageList.pageInfo.currentPage === admin.pageList.pageInfo.pageCount) {
                        admin.pageList.pageInfo.pageCount++;
                    }
                    var hashList = window.location.hash.split("/");
                    if (admin.pageList.pageInfo.pageCount == hashList[hashList.length - 1]) {
                        admin.pageList.getList(admin.pageList.pageInfo.pageCount);
                    } else {
                        admin.setHashByPage(admin.pageList.pageInfo.pageCount);
                    }

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /*
     * 更新自定义页面
     */
    update: function () {
        if (this.validate()) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            var pageContent = admin.editors.pageEditor.getContent();

            var pagePermalink = $("#pagePermalink").val().replace(/(^\s*)|(\s*$)/g, "");
            if (admin.pageList.type === "link") {
                pagePermalink = Util.proessURL(pagePermalink);
            }


            var requestJSONObject = {
                "page": {
                    "pageTitle": $("#pageTitle").val(),
                    "oId": this.id,
                    "pageContent": pageContent,
                    "pagePermalink": pagePermalink,
                    "pageCommentable": $("#pageCommentable").prop("checked"),
                    "pageType": admin.pageList.type,
                    "pageOpenTarget": $("#pageTarget").val(),
                    "pageEditorType": admin.pageList.currentEditorType,
                    "pageIcon": $("#pageIcon").val()
                }
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/page/",
                type: "PUT",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);

                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    admin.pageList.id = "";

                    admin.pageList.getList(admin.pageList.pageInfo.currentPage);
                    $("#pageTitle").val("");
                    $("#pageIcon").val("");
                    $("#pagePermalink").val("");
                    $("#pageCommentable").prop("cheked", false);
                    $("#pageTarget").val("_self");
                    $($(".fn-type").get(0)).click();

                    admin.editors.pageEditor.setContent("");

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /*
     * 验证字段
     */
    validate: function () {
        if ($("#pageTitle").val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.titleEmptyLabel);
            $("#pageTitle").focus();
        } else if (admin.pageList.type === "link" &&
                $("#pagePermalink").val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.linkEmptyLabel);
        } else {
            return true;
        }
        return false;
    },
    /*
     * 提交自定义页面
     */
    submit: function () {
        if (this.id !== "") {
            this.update();
        } else {
            this.add();
        }

        if (admin.pageList.currentEditorType !== Label.editorType) {
            admin.editors.pageEditor.remove();

            admin.pageList.currentEditorType = Label.editorType;
            admin.editors.pageEditor.init(Label.editorType);
        }
    },
    /*
     * 调换顺序
     */
    changeOrder: function (id, order, status) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");

        var requestJSONObject = {
            "oId": id.toString(),
            "direction": status
        };

        $.ajax({
            url: latkeConfig.servePath + "/console/page/order/",
            type: "PUT",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);

                // Refershes the page list
                admin.pageList.getList(admin.pageList.pageInfo.currentPage);

                $("#loadMsg").text("");
            }
        });
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["page-list"] = {
    "obj": admin.pageList,
    "init": admin.pageList.init,
    "refresh": admin.pageList.getList
}
