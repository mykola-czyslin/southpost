<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.contact.EmailInfo, Long>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../../common/macro.ftl" as commons/>
<form id="searchForm" method="post" action="<@spring.url '/master/emails/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell">
                    <label for="emailAddressPattern"><@spring.message "label.email.address"/></label>
                </div>
                <div class="-cell">
                    <label for="descriptionPattern"><@spring.message "label.email.description"/></label>
                </div>
            </div>
            <div class="-row">
            <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <div class="-cell">
                <#assign attributes>class="-tooltip-target" size="40" maxlength="80" title="${tooltip?html}"</#assign>
                    <@spring.formInput "searchForm.emailAddressPattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <#assign attributes>class="-tooltip-target" size="60" maxlength="80" title="${tooltip?html}"</#assign>
                    <@spring.formInput "searchForm.descriptionPattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <div class="button-pane">
                        <span class="-button -search-submit"><@spring.message "button.caption.search"/></span>
                        <span class="-button -search-reset"><@spring.message "button.caption.reset"/></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../../includes/sort-fields.ftl"/>
</form>
<#assign pagingModel = pageListData.model/>
<#include "../../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid emails-grid">
        <thead>
            <tr>
                <#if staff>
                    <th class="-grid-action">
                        <@commons.addEntityButton "/master/emails/add" "tooltip.add.email"/>
                    </th>
                </#if>
                <th><@commons.headerCaptionBox textKey="label.email.id"/></th>
                <th><@commons.headerCaptionBox textKey="label.email.address"/></th>
                <th><@commons.headerCaptionBox textKey="label.email.description"/></th>
            <#if staff>
                <th class="-grid-action">
                    <@commons.addEntityButton "/master/emails/add" "tooltip.add.email"/>
                </th>
            </#if>
            </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as email>
            <tr>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/contact/email/${email.id?string["0"]}/remove'/></#assign>
                    <td class="-grid-actions">
                        <@commons.deleteEntityButton
                        actionSelectorClass="-remove-email"
                        actionUrl=removeUrl
                        confirmationMessageKey="message.remove.email.confirmation"
                        tooltipMessageKey="tooltip.remove.email"/>
                    </td>
                </#if>
                <td>
                    <#assign url>/master/emails/${email.id?string['0']}/view</#assign>
                    <a href="<@spring.url url/>">${(email.id?string['0'])?left_pad(10)}</a>
                </td>
                <td>${email.emailAddress?html}</td>
                <td>${(email.description!'')?html}</td>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/contact/email/${email.id?string["0"]}/remove'/></#assign>
                <td class="-grid-actions">
                    <@commons.deleteEntityButton
                        actionSelectorClass="-remove-email"
                        actionUrl=removeUrl
                        confirmationMessageKey="message.remove.email.confirmation"
                        tooltipMessageKey="tooltip.remove.email"/>
                </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

