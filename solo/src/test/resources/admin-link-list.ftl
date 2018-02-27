<div>
    <div id="linkTable"></div>
    <div id="linkPagination" class="margin12 right"></div>
</div>
<div class="clear"></div>
<table class="form" width="100%" cellpadding="0px" cellspacing="9px">
    <thead>
        <tr>
            <th style="text-align: left" colspan="2">
                ${addLinkLabel}
            </th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th width="48px">
                ${linkTitle1Label}
            </th>
            <td>
                <input id="linkTitle" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                ${url1Label}
            </th>
            <td>
                <input id="linkAddress" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                ${linkDescription1Label}
            </th>
            <td>
                <input id="linkDescription" type="text"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <button onclick="admin.linkList.add();">${saveLabel}</button>
            </td>
        </tr>
    </tbody>
</table>
<div id="updateLink" class="none">
    <table class="form" width="100%" cellpadding="0px" cellspacing="9px">
        <thead>
            <tr>
                <th style="text-align: left" colspan="2">
                    ${updateLinkLabel}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th width="48px">
                    ${linkTitle1Label}
                </th>
                <td>
                    <input id="linkTitleUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    ${url1Label}
                </th>
                <td>
                    <input id="linkAddressUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    ${linkDescription1Label}
                </th>
                <td>
                    <input id="linkDescriptionUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <button onclick="admin.linkList.update();">${updateLabel}</button>
                </td>
            </tr>
        </tbody>
    </table>
</div>
${plugins}