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
 * link list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.1.4, Apr 9, 2017
 */

/* link-list 相关操作 */
admin.linkList = {
    tablePagination:  new TablePaginate("link"),
    pageInfo: {
        currentCount: 1,
        pageCount: 1,
        currentPage: 1
    },
    id: "",
    /*
     * 初始化 table, pagination
     */
    init: function (page) {
        this.tablePagination.buildTable([{
            text: "",
            index: "linkOrder",
            width: 60
        },{
            style: "padding-left: 12px;",
            text: Label.linkTitleLabel,
            index: "linkTitle",
            width: 230
        }, {
            style: "padding-left: 12px;",
            text: Label.urlLabel,
            index: "linkAddress",
            minWidth: 180
        }, {
            style: "padding-left: 12px;",
            text: Label.linkDescriptionLabel,
            index: "linkDescription",
            width: 360
        }]);
    
        this.tablePagination.initPagination();
        this.getList(page);
        
        $("#updateLink").dialog({
            width: 700,
            height: 210,
            "modal": true,
            "hideFooter": true
        });
    },

    /* 
     * 根据当前页码获取链接列表
     * 
     * @pagNum 当前页码
     */
    getList: function (pageNum) {
        $("#loadMsg").text(Label.loadingLabel);
        if (pageNum === 0) {
            pageNum = 1;
        }
        this.pageInfo.currentPage = pageNum;
        var that = this;
        
        $.ajax({
            url: latkeConfig.servePath + "/console/links/" + pageNum + "/" + Label.PAGE_SIZE + "/" +  Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                
                var links = result.links;
                var linkData = [];
                admin.linkList.pageInfo.currentCount = links.length;
                admin.linkList.pageInfo.pageCount = result.pagination.paginationPageCount === 0 ? 1 : result.pagination.paginationPageCount;

                for (var i = 0; i < links.length; i++) {
                    linkData[i] = {};
                    if (i === 0) {
                        if (links.length === 1) {
                            linkData[i].linkOrder = "";
                        } else {
                            linkData[i].linkOrder = '<div class="table-center" style="width:14px">\
                                <span onclick="admin.linkList.changeOrder(' + links[i].oId + ', ' + i + ', \'down\');" class="icon-move-down"></span>\
                            </div>';
                        }
                    } else if (i === links.length - 1) {
                        linkData[i].linkOrder = '<div class="table-center" style="width:14px">\
                                <span onclick="admin.linkList.changeOrder(' + links[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                            </div>';
                    } else {
                        linkData[i].linkOrder = '<div class="table-center" style="width:38px">\
                                <span onclick="admin.linkList.changeOrder(' + links[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                                <span onclick="admin.linkList.changeOrder(' + links[i].oId + ', ' + i + ', \'down\');" class="icon-move-down"></span>\
                            </div>';
                    }
                    
                    linkData[i].linkTitle = links[i].linkTitle;
                    linkData[i].linkAddress = "<a target='_blank' class='no-underline' href='" + links[i].linkAddress + "'>"
                    + links[i].linkAddress + "</a>";
                    linkData[i].linkDescription = links[i].linkDescription;
                    linkData[i].expendRow = "<span><a href='" + links[i].linkAddress + "' target='_blank'>" + Label.viewLabel + "</a>  \
                                <a href='javascript:void(0)' onclick=\"admin.linkList.get('" + links[i].oId + "')\">" + Label.updateLabel + "</a>\
                                <a href='javascript:void(0)' onclick=\"admin.linkList.del('" + links[i].oId + "', '" + links[i].linkTitle + "')\">" + Label.removeLabel + "</a></span>";
                }

                that.tablePagination.updateTablePagination(linkData, pageNum, result.pagination);
                
                $("#loadMsg").text("");
            }
        });
    },
    
    /*
     * 添加链接
     */
    add: function () {
        if (this.validate()) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var requestJSONObject = {
                "link": {
                    "linkTitle": $("#linkTitle").val(),
                    "linkAddress": $("#linkAddress").val(),
                    "linkDescription": $("#linkDescription").val()
                }
            };
            
            $.ajax({
                url: latkeConfig.servePath + "/console/link/",
                type: "POST",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function(result, textStatus){
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    
                    $("#linkTitle").val("");
                    $("#linkAddress").val("");
                    $("#linkDescription").val("");
                    if (admin.linkList.pageInfo.currentCount === Label.PAGE_SIZE &&
                        admin.linkList.pageInfo.currentPage === admin.linkList.pageInfo.pageCount) {
                        admin.linkList.pageInfo.pageCount++;
                    }
                    var hashList = window.location.hash.split("/");
                    if (admin.linkList.pageInfo.pageCount !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(admin.linkList.pageInfo.pageCount);
                    }
                    
                    admin.linkList.getList(admin.linkList.pageInfo.pageCount);
                    
                    $("#loadMsg").text("");
                }
            });
        }
    },
    
    /*
     * 获取链接
     * @id 链接 id
     */
    get: function (id) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#updateLink").dialog("open");
        
        $.ajax({
            url: latkeConfig.servePath + "/console/link/" + id,
            type: "GET",
            cache: false,
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                
                admin.linkList.id = id;
                
                $("#linkTitleUpdate").val(result.link.linkTitle);
                $("#linkAddressUpdate").val(result.link.linkAddress);
                $("#linkDescriptionUpdate").val(result.link.linkDescription);
                
                $("#loadMsg").text("");
            }
        });
    },
    
    /*
     * 更新链接
     */
    update: function () {
        if (this.validate("Update")) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var requestJSONObject = {
                "link": {
                    "linkTitle": $("#linkTitleUpdate").val(),
                    "oId": this.id,
                    "linkAddress": $("#linkAddressUpdate").val(),
                    "linkDescription": $("#linkDescriptionUpdate").val()
                }
            };
            
            $.ajax({
                url: latkeConfig.servePath + "/console/link/",
                type: "PUT",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function(result, textStatus){
                    $("#updateLink").dialog("close");
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    
                    admin.linkList.getList(admin.linkList.pageInfo.currentPage);
                    
                    $("#loadMsg").text("");
                }
            });
        }
    },
    
    /*
     * 删除链接
     * @id 链接 id
     * @title 链接标题
     */
    del: function (id, title) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.permalinkLabel + '"' + title + '"?');
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            
            $.ajax({
                url: latkeConfig.servePath + "/console/link/" + id,
                type: "DELETE",
                cache: false,
                success: function(result, textStatus){
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }
                    
                    var pageNum = admin.linkList.pageInfo.currentPage;
                    if (admin.linkList.pageInfo.currentCount === 1 && admin.linkList.pageInfo.pageCount !== 1 &&
                        admin.linkList.pageInfo.currentPage === admin.linkList.pageInfo.pageCount) {
                        admin.linkList.pageInfo.pageCount--;
                        pageNum = admin.linkList.pageInfo.pageCount;
                    }
                    
                    var hashList = window.location.hash.split("/");
                    if (pageNum !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(pageNum);
                    }
                    
                    admin.linkList.getList(pageNum);
                    
                    $("#loadMsg").text("");
                }
            });
        }
    },
    
    /*
     * 验证字段
     * @status 更新或者添加时进行验证
     */
    validate: function (status) {
        if (!status) {
            status = "";
        }
        if ($("#linkTitle" + status).val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.titleEmptyLabel);
            $("#linkTitle" + status).focus().val("");
        } else if ($("#linkAddress" + status).val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.addressEmptyLabel);
            $("#linkAddress" + status).focus().val("");
        } else if (!/^\w+:\/\//.test($("#linkAddress" + status).val())) {
            $("#tipMsg").text(Label.addressInvalidLabel);
            $("#linkAddress" + status).focus().val("");
        } else {
            return true;
        }
        return false;
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
            url: latkeConfig.servePath + "/console/link/order/",
            type: "PUT",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
                
                // Refershes the link list
                admin.linkList.getList(admin.linkList.pageInfo.currentPage);
                
                $("#loadMsg").text("");
            }
        });
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["link-list"] =  {
    "obj": admin.linkList,
    "init": admin.linkList.init,
    "refresh": admin.linkList.getList
}