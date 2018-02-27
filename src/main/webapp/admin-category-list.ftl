<div>
    <div id="categoryTable"></div>
    <div id="categoryPagination" class="margin12 right"></div>
</div>
<div class="clear"></div>
<table class="form" width="100%" cellpadding="0px" cellspacing="9px">
    <thead>
        <tr>
            <th style="text-align: left" colspan="2">
                ${addCategoryLabel}
            </th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th width="48px">
                <label for="categoryName">${linkTitle1Label}</label>
            </th>
            <td>
                <input id="categoryName" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="categoryURI">URI：</label>
            </th>
            <td>
                <input id="categoryURI" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="categoryDesc">${linkDescription1Label}</label>
            </th>
            <td>
                <input id="categoryDesc" type="text"/>
            </td>
        </tr>
        <tr>
            <th>
                <label for="categoryTags">${tags1Label}</label>
            </th>
            <td>
                <input id="categoryTags" type="text"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <button onclick="admin.categoryList.add();">${saveLabel}</button>
            </td>
        </tr>
    </tbody>
</table>
<div id="categoryUpdate" class="none">
    <table class="form" width="100%" cellpadding="0px" cellspacing="9px">
        <thead>
            <tr>
                <th style="text-align: left" colspan="2">
                    ${updateCategoryLabel}
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th width="48px">
                    <label for="categoryNameUpdate">${linkTitle1Label}</label>
                </th>
                <td>
                    <input id="categoryNameUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="categoryURIUpdate">URI：</label>
                </th>
                <td>
                    <input id="categoryURIUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="categoryDescUpdate">${linkDescription1Label}</label>
                </th>
                <td>
                    <input id="categoryDescUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <th>
                    <label for="categoryTagsUpdate">${tags1Label}</label>
                </th>
                <td>
                    <input id="categoryTagsUpdate" type="text"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <button onclick="admin.categoryList.update();">${updateLabel}</button>
                </td>
            </tr>
        </tbody>
    </table>
</div>
${plugins}
