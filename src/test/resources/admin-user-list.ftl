<div>
    <div id="userTable"></div>
    <div id="userPagination" class="margin12 right"></div>
</div>
<div class="clear"></div>
<table class="form" width="100%" cellpadding="0px" cellspacing="9px">
    <thead>
        <tr>
            <th style="text-align: left" colspan="2">
                ${addUserLabel}
            </th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th width="48px">
                <label for="userName">${commentName1Label}</label>
            </th>
            <td>
                <input id="userName" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="userEmail">${commentEmail1Label}</label>
            </th>
            <td>
                <input id="userEmail" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="userURL">${userURL1Label}</label>
            </th>
            <td>
                <input id="userURL" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="userPassword">${userPassword1Label}</label>
            </th>
            <td>
                <input id="userPassword" type="password"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="userAvatar">${userAvatar1Label}</label>
            </th>
            <td>
                <input id="userAvatar" type="text"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <button onclick="admin.userList.add();">${saveLabel}</button>
            </td>
        </tr>
    </tbody>
</table>
<div id="userUpdate" class="none">
    <table class="form" width="100%" cellpadding="0px" cellspacing="9px">
        <thead>
            <tr>
                <th style="text-align: left" colspan="2">
                    ${updateUserLabel}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th width="48px">
                    <label for="userNameUpdate">${commentName1Label}</label>
                </th>
                <td>
                    <input id="userNameUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="userEmailUpdate">${commentEmail1Label}</label>
                </th>
                <td>
                    <input id="userEmailUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="userURLUpdate">${userURL1Label}</label>
                </th>
                <td>
                    <input id="userURLUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="userPasswordUpdate">${userPassword1Label}</label>
                </th>
                <td>
                    <input id="userPasswordUpdate" type="password"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="userAvatarUpdate">${userAvatar1Label}</label>
                </th>
                <td>
                    <input id="userAvatarUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <button onclick="admin.userList.update();">${updateLabel}</button>
                </td>
            </tr>
        </tbody>
    </table>
</div>
${plugins}
