<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo, java.lang.Long>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../../common/macro.ftl" as commons/>
<form id="searchForm" method="post" action="<@spring.url '/master/phones/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell">
                    <label for="phoneNumberPattern"><@spring.message "label.phone.number"/></label>
                </div>
                <div class="-cell">
                    <label for="descriptionPattern"><@spring.message "label.phone.description"/></label>
                </div>
            </div>
            <div class="-row">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <div class="-cell">
                    <#assign attributes>class="-tooltip-target" size="20" maxlength="20" title="${tooltip?html}"</#assign>
                    <@spring.formInput "searchForm.phoneNumberPattern", attributes, "text"/>
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
    <table class="grid phones-grid">
        <thead>
        <tr>
            <#if staff>
                <th><@commons.addEntityButton "/master/phones/add" "tooltip.add.phone"/></th>
            </#if>
            <th><@commons.headerCaptionBox textKey="label.phone.id"/></th>
            <th><@commons.headerCaptionBox textKey="label.phone.number"/></th>
            <th><@commons.headerCaptionBox textKey="label.phone.description"/></th>
            <#if staff>
                <th><@commons.addEntityButton "/master/phones/add" "tooltip.add.phone"/></th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as phone>
            <tr>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/contact/phone/${phone.id?string["0"]}/remove'/></#assign>
                    <td>
                        <@commons.deleteEntityButton
                        actionSelectorClass="-remove-phone"
                        actionUrl=removeUrl
                        confirmationMessageKey="message.remove.phone.confirmation"
                        tooltipMessageKey="tooltip.remove.phone"/>
                    </td>
                </#if>
                <td>
                    <#assign url>/master/phones/${phone.id?string['0']}/view</#assign>
                    <a href="<@spring.url url/>">${(phone.id?string['0'])?left_pad(10)}</a>
                </td>
                <td>${phone.phoneNumber?html}</td>
                <td>${(phone.description!'')?html}</td>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/contact/phone/${phone.id?string["0"]}/remove'/></#assign>
                    <td>
                        <@commons.deleteEntityButton
                            actionSelectorClass="-remove-phone"
                            actionUrl=removeUrl
                            confirmationMessageKey="message.remove.phone.confirmation"
                            tooltipMessageKey="tooltip.remove.phone"/>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

