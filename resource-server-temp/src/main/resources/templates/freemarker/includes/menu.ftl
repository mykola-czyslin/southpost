<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="menu" type="ua.southpost.resource.backup.web.model.menu.CatalogMenu" -->
<#macro menuItem menuObject>
    <#if menuObject.enabled>
        <#assign level = menuObject.level - 1/>
        <div class="menu -level-${level}"  data-level="${(level + 1)*100}">
            <#if menuObject.submenuList??>
                <div class="menu-title" data-submenu="${menuObject.catalogId}"><span><@spring.message menuObject.title/></span></div>
            <#elseif menuObject.actionUri?? && menu.formId?? && menu.method??>
                <span class="form-link" data-form-Id="${menuObject.formId}"><@spring.message menuObject.title/></span>
                <form id="${menu.formId}" action="<@spring.url menuObject.actionUri/>" method="${menuObject.method}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            <#elseif menuObject.actionUri??>
                <a class="action-menu" href="<@spring.url menuObject.actionUri/>" ><@spring.message menuObject.title/></a>
            <#else>
                [Oops!] <@spring.message menuObject.title/>
            </#if>

        </div>
        <#if menuObject.submenuList??>
        </#if>
    </#if>
</#macro>

<div class="main-menu">
<#list menu.submenuList as submenu>
    <@menuItem submenu/>
</#list>
<#if menu.catalogsQueue??>
    <#list menu.catalogsQueue as catalog>
        <div id="${catalog.catalogId?html}" class="submenu -level-${catalog.level} hidden">
            <#list catalog.submenuList as catalogSubmenu>
                <@menuItem catalogSubmenu/>
            </#list>
        </div>
    </#list>
</#if>

</div>
