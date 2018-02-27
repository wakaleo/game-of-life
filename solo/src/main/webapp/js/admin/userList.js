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
 * user list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.1.7, Oct 17, 2015
 */

/* user-list 相关操作 */
admin.userList = {
    tablePagination: new TablePaginate("user"),
    pageInfo: {
        currentCount: 1,
        pageCount: 1,
        currentPage: 1
    },
    userInfo: {
        'oId': "",
        "userRole": ""
    },
    /* 
     * 初始化 table, pagination
     */
    init: function(page) {
        this.tablePagination.buildTable([{
                style: "padding-left: 12px;",
                text: Label.commentNameLabel,
                index: "userName",
                width: 230
            }, {
                style: "padding-left: 12px;",
                text: Label.commentEmailLabel,
                index: "userEmail",
                minWidth: 180
            }, {
                style: "padding-left: 12px;",
                text: Label.roleLabel,
                index: "isAdmin",
                width: 120
            }]);

        this.tablePagination.initPagination();
        this.getList(page);

        $("#userUpdate").dialog({
            width: 700,
            height: 300,
            "modal": true,
            "hideFooter": true
        });
    },
    /* 
     * 根据当前页码获取列表
     * @pagNum 当前页码
     */
    getList: function(pageNum) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");
        this.pageInfo.currentPage = pageNum;
        var that = this;

        $.ajax({
            url: latkeConfig.servePath + "/console/users/" + pageNum + "/" + Label.PAGE_SIZE + "/" + Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function(result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var users = result.users;
                var userData = [];
                admin.userList.pageInfo.currentCount = users.length;
                admin.userList.pageInfo.pageCount = result.pagination.paginationPageCount;
                if (users.length < 1) {
                    $("#tipMsg").text("No user  " + Label.reportIssueLabel);
                    $("#loadMsg").text("");
                    return;
                }

                for (var i = 0; i < users.length; i++) {
                    userData[i] = {};
                    userData[i].userName = users[i].userName;
                    userData[i].userEmail = users[i].userEmail;

                    if ("adminRole" === users[i].userRole) {
                        userData[i].isAdmin = "&nbsp;" + Label.administratorLabel;
                        userData[i].expendRow = "<a href='javascript:void(0)' onclick=\"admin.userList.get('" +
                                users[i].oId + "', '" + users[i].userRole + "')\">" + Label.updateLabel + "</a>";
                    } else {
                        userData[i].expendRow = "<a href='javascript:void(0)' onclick=\"admin.userList.get('" +
                                users[i].oId + "', '" + users[i].userRole + "')\">" + Label.updateLabel + "</a>\
                                <a href='javascript:void(0)' onclick=\"admin.userList.del('" + users[i].oId + "', '" + users[i].userName + "')\">" + Label.removeLabel + "</a> " +
                                "<a href='javascript:void(0)' onclick=\"admin.userList.changeRole('" + users[i].oId + "')\">" + Label.changeRoleLabel + "</a>";
                        if ("defaultRole" === users[i].userRole) {
                            userData[i].isAdmin = Label.commonUserLabel;
                        }
                        else {
                            userData[i].isAdmin = Label.visitorUserLabel;
                        }
                    }

                    that.tablePagination.updateTablePagination(userData, pageNum, result.pagination);

                    $("#loadMsg").text("");
                }
            }
        });
    },
    /*
     * 添加用户
     */
    add: function() {
        if (this.validate()) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            var requestJSONObject = {
                "userName": $("#userName").val(),
                "userEmail": $("#userEmail").val(),
                "userURL": $("#userURL").val(),
                "userPassword": $("#userPassword").val(),
                "userAvatar": $("#userAvatar").val()
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/user/",
                type: "POST",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function(result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    $("#userName").val("");
                    $("#userEmail").val("");
                    $("#userURL").val("");
                    $("#userPassword").val("");
                    $("#userAvatar").val("");
                    if (admin.userList.pageInfo.currentCount === Label.PAGE_SIZE &&
                            admin.userList.pageInfo.currentPage === admin.userList.pageInfo.pageCount) {
                        admin.userList.pageInfo.pageCount++;
                    }
                    var hashList = window.location.hash.split("/");
                    if (admin.userList.pageInfo.pageCount !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(admin.userList.pageInfo.pageCount);
                    }

                    admin.userList.getList(admin.userList.pageInfo.pageCount);

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /*
     * 获取用户
     * @id 用户 id
     */
    get: function(id, userRole) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");
        $("#userUpdate").dialog("open");

        $.ajax({
            url: latkeConfig.servePath + "/console/user/" + id,
            type: "GET",
            cache: false,
            success: function(result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var $userEmailUpdate = $("#userEmailUpdate");
                $("#userNameUpdate").val(result.user.userName).data("userInfo", {
                    'oId': id,
                    "userRole": userRole
                });
                $userEmailUpdate.val(result.user.userEmail);
                if ("adminRole" === userRole) {
                    $userEmailUpdate.attr("disabled", "disabled");
                } else {
                    $userEmailUpdate.removeAttr("disabled");
                }
                
                $("#userURLUpdate").val(result.user.userURL);
                $("#userPasswordUpdate").val(result.user.userPassword);
                $("#userAvatarUpdate").val(result.user.userAvatar);

                $("#loadMsg").text("");
            }
        });
    },
    /*
     * 更新用户
     */
    update: function() {
        if (this.validate("Update")) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            var userInfo = $("#userNameUpdate").data("userInfo");
            var requestJSONObject = {
                "userName": $("#userNameUpdate").val(),
                "oId": userInfo.oId,
                "userEmail": $("#userEmailUpdate").val(),
                "userURL": $("#userURLUpdate").val(),
                "userRole": userInfo.userRole,
                "userPassword": $("#userPasswordUpdate").val(),
                "userAvatar": $("#userAvatarUpdate").val()
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/user/",
                type: "PUT",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function(result, textStatus) {
                    $("#userUpdate").dialog("close");
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    admin.userList.getList(admin.userList.pageInfo.currentPage);

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /*
     * 删除用户
     * @id 用户 id
     * @userName 用户名称
     */
    del: function(id, userName) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.userLabel + '"' + userName + '"?');
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            $.ajax({
                url: latkeConfig.servePath + "/console/user/" + id,
                type: "DELETE",
                cache: false,
                success: function(result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    var pageNum = admin.userList.pageInfo.currentPage;
                    if (admin.userList.pageInfo.currentCount === 1 && admin.userList.pageInfo.pageCount !== 1 &&
                            admin.userList.pageInfo.currentPage === admin.userList.pageInfo.pageCount) {
                        admin.userList.pageInfo.pageCount--;
                        pageNum = admin.userList.pageInfo.pageCount;
                    }
                    var hashList = window.location.hash.split("/");
                    if (pageNum !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(pageNum);
                    }
                    admin.userList.getList(pageNum);

                    $("#loadMsg").text("");
                }
            });
        }
    },
    /**
     * 修改角色
     * @param id
     */
    changeRole: function(id) {
        $("#tipMsg").text("");
        $.ajax({
            url: latkeConfig.servePath + "/console/changeRole/" + id,
            type: "GET",
            cache: false,
            success: function(result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var pageNum = admin.userList.pageInfo.currentPage;
                if (admin.userList.pageInfo.currentCount === 1 && admin.userList.pageInfo.pageCount !== 1 &&
                        admin.userList.pageInfo.currentPage === admin.userList.pageInfo.pageCount) {
                    admin.userList.pageInfo.pageCount--;
                    pageNum = admin.userList.pageInfo.pageCount;
                }
                var hashList = window.location.hash.split("/");
                if (pageNum !== parseInt(hashList[hashList.length - 1])) {
                    admin.setHashByPage(pageNum);
                }
                admin.userList.getList(pageNum);

                $("#loadMsg").text("");
            }
        });
    },
    /*
     * 验证字段
     * @status 更新或者添加时进行验证
     */
    validate: function(status) {
        if (!status) {
            status = "";
        }
        var userName = $("#userName" + status).val().replace(/(^\s*)|(\s*$)/g, "");
        if (2 > userName.length || userName.length > 20) {
            $("#tipMsg").text(Label.nameTooLongLabel);
            $("#userName" + status).focus();
        } else if ($("#userEmail" + status).val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.mailCannotEmptyLabel);
            $("#userEmail" + status).focus();
        } else if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test($("#userEmail" + status).val())) {
            $("#tipMsg").text(Label.mailInvalidLabel);
            $("#userEmail" + status).focus();
        } else if ($("#userPassword" + status).val() === "") {
            $("#tipMsg").text(Label.passwordEmptyLabel);
            $("#userPassword" + status).focus();
        } else {
            return true;
        }
        return false;
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["user-list"] = {
    "obj": admin.userList,
    "init": admin.userList.init,
    "refresh": admin.userList.getList
}