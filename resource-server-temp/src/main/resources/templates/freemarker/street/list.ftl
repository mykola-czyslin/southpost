<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.address.StreetInfo,java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<script id="optionsTemplate" type="text/x-jQuery-tmpl">
    <option value="${r'${id}'}">${r"${name}"}</option>


</script>
<form id="searchForm" method="post" action="<@spring.url '/master/streets/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell">
                    <label for="regionId"><@spring.message "label.region"/></label>
                </div>
                <div class="-cell">
                    <#-- district -->
                    <label for="districtId"><@spring.message "label.district"/></label>
                </div>
                <div class="-cell">
                    <#-- settlement kind -->
                    <label for="settlementKind"><@spring.message "label.settlement.kind"/></label>
                </div>
                <div class="-cell">
                    <#-- settlement name -->
                    <label for="settlementNamePattern"><@spring.message "label.settlement.name"/></label>
                </div>
                <div class="-cell"></div>
            </div>
            <div class="-row">
                <div class="-cell" data-on-change-url="<@spring.url '/api/region/{id}/districts'/>">
                    <#-- region -->
                    <@spring.formSingleSelect "searchForm.regionId", regionOptions, ""/>
                </div>
                <div class="-cell">
                    <@spring.formSingleSelect "searchForm.districtId", districtOptions, ""/>
                </div>
                <div class="-cell">
                    <@spring.formSingleSelect "searchForm.settlementKind", settlementKindOptions, ""/>
                </div>
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${tooltip?html}"</#assign>
                <div class="-cell">
                    <@spring.formInput "searchForm.settlementNamePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <div class="button-pane">
                        <span class="-button -search-submit"><@spring.message "button.caption.search"/></span>
                        <span class="-button -search-reset"><@spring.message "button.caption.reset"/></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="-table">
            <div class="-row">
                <div class="-cell"><label for="streetKind"><@spring.message "label.street.kind"/></label></div>
                <div class="-cell"><label for="streetNamePattern"><@spring.message "label.street.name"/></label></div>
            </div>
            <div class="-row">
                <div class="-cell">
                    <@spring.formSingleSelect "searchForm.streetKind", streetKindOptions, ""/>
                </div>
                <div class="-cell">
                    <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                    <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${tooltip?html}"</#assign>
                    <@spring.formInput "searchForm.streetNamePattern", attributes, "text"/>
                </div>
            </div>
        </div>
    </div>
    <#include "../includes/sort-fields.ftl"/>
</form>
<#assign pagingModel = pageListData.model/>
<#include "../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid settlement street">
        <thead>
        <tr>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/master/streets/add" tooltipMessageKey="tooltip.add.street"/>
                </th>
            </#if>
            <th class="-street-id"><@commons.headerCaptionBox textKey="label.street.id"/></th>
            <th class="-street-region"><@commons.headerCaptionBox textKey="label.region"/></th>
            <th class="-street-district"><@commons.headerCaptionBox textKey="label.district"/></th>
            <th class="-street-settlement-kind"><@commons.headerCaptionBox textKey="label.settlement.kind"/></th>
            <th class="-street-settlement-name"><@commons.headerCaptionBox textKey="label.settlement.name"/></th>
            <th class="-street-kind"><@commons.headerCaptionBox textKey="label.street.kind"/></th>
            <th class="-street-name"><@commons.headerCaptionBox textKey="label.street.name"/></th>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/master/streets/add" tooltipMessageKey="tooltip.add.street"/>
                </th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as street>
            <tr>
                <#if staff>
                    <td>
                        <#assign url>/api/streets/${street.id?string['0']}/remove</#assign>
                        <#assign removeUrl><@spring.url url/></#assign>
                        <@commons.deleteEntityButton
                        actionSelectorClass="-remove-street"
                        actionUrl=removeUrl
                        confirmationMessageKey="message.remove.street.confirmation"
                        tooltipMessageKey="tooltip.remove.street"/>
                    </td>
                </#if>
                <td>
                    <#assign url>/master/streets/${street.id?string['0']}/view</#assign>
                    <a href="<@spring.url url/>">${(street.id?string['0'])?left_pad(10)}</a>
                </td>
                <td>
                    ${(street.settlement.district.region.name!'-')?html}
                </td>
                <td>
                    <#if !street.settlement.district.mock.id.value>
                        ${street.settlement.district.name}
                    </#if>
                </td>
                <td>
                    ${(street.settlement.kind.textValue!'-')?html}
                </td>
                <td>
                    ${(street.settlement.name!'-')?html}
                </td>
                <td><#if street.kind??>${(street.kind.textValue!'-')?html}<#else>-</#if></td>
                <td>${(street.name!'-')?html}</td>
                <#if staff>
                    <td>
                        <#assign url>/api/streets/${street.id?string['0']}/remove</#assign>
                        <#assign removeUrl><@spring.url url/></#assign>
                        <@commons.deleteEntityButton
                                actionSelectorClass="-remove-street"
                                actionUrl=removeUrl
                                confirmationMessageKey="message.remove.street.confirmation"
                                tooltipMessageKey="tooltip.remove.street"/>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>