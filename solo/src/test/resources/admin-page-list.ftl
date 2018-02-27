<div>
    <div id="pageTable">
    </div>
    <div id="pagePagination" class="margin12 right">
    </div>
    <div class="clear"></div>
</div>
<div class="form">
    <div>
        <label>${title1Label}</label>
        <input id="pageTitle" type="text"/>
    </div>
    <div>
        <label>${permalink1Label}</label>
        <input id="pagePermalink" type="text"/>
    </div>
    <div>
        <label>${openMethod1Label}</label>
        <select id="pageTarget">
            <option value="_self">${targetSelfLabel}</option>
            <option value="_blank">${targetBlankLabel}</option>
            <option value="_parent">${targetParentLabel}</option>
            <option value="_top">${targetTopLabel}</option>
        </select>&nbsp;&nbsp;&nbsp;&nbsp;
        <label>${type1Label}</label>
        <button data-type="link" class="selected fn-type">${pageLinkLabel}</button>
        <button data-type="page" class="fn-type">${pageLabel}</button>
    </div>
    <div id="pagePagePanel" class="none">
        <textarea id="pageContent" style="height: 430px;width: 100%;" name="pageContent"></textarea>
        <label>${allowComment1Label}</label>
        <input type="checkbox" id="pageCommentable" checked="checked" />
    </div>
    <div class="right">
        <button onclick="admin.pageList.submit();">${saveLabel}</button>
    </div>
    <div class="clear"></div>
</div>
<div id="pageComments" class="none"></div>
<div class="clear"></div>
${plugins}
