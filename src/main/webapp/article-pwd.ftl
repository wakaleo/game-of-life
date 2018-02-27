<#include "macro-common-page.ftl">

<@commonPage "${articleViewPwdLabel}">
<h2>
${articleTitle}
</h2>
    <#if msg??>
    <div>${msg}</div>
    </#if>
<form class="form" method="POST" action="${servePath}/console/article-pwd">
    <label for="pwdTyped">访问密码：</label>
    <input type="password" id="pwdTyped" name="pwdTyped" />
    <input type="hidden" name="articleId" value="${articleId}" />
    <button id="confirm" type="submit">${confirmLabel}</button>
</form>
</@commonPage>