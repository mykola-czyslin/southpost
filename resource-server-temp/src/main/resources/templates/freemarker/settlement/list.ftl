<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.address.SettlementInfo,java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<script id="optionsTemplate" type="text/x-jQuery-tmpl">
    <option value="${r'${id}'}">${r"${name}"}</option>

</script>
<form id="searchForm" method="post" action="<@spring.url '/master/settlement/list'/>">
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
    </div>
    <#include "../includes/sort-fields.ftl"/>
</form>
<#assign pagingModel = pageListData.model/>
<#include "../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid settlement">
        <thead>
            <tr>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/master/settlement/add" tooltipMessageKey="tooltip.add.settlement"/>
                    </th>
                </#if>
                <th class="-settlement-id"><@commons.headerCaptionBox textKey="label.settlement.id"/></th>
                <th class="-settlement-region"><@commons.headerCaptionBox textKey="label.region"/></th>
                <th class="-settlement-district"><@commons.headerCaptionBox textKey="label.district"/></th>
                <th class="-settlement-kind"><@commons.headerCaptionBox textKey="label.settlement.kind"/></th>
                <th class="-settlement-name"><@commons.headerCaptionBox textKey="label.settlement.name"/></th>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/master/settlement/add" tooltipMessageKey="tooltip.add.settlement"/>
                    </th>
                </#if>
            </tr>
        </thead>
        <tbody>
            <#list pageListData.entityList as settlement>
                <tr>
                    <#if staff>
                        <td>
                            <#assign url>/api/settlement/${settlement.id?string['0']}/remove</#assign>
                            <#assign removeUrl><@spring.url url/></#assign>
                            <@commons.deleteEntityButton
                            actionSelectorClass="-remove-settlement"
                            actionUrl=removeUrl
                            confirmationMessageKey="message.remove.settlement.confirmation"
                            tooltipMessageKey="tooltip.remove.settlement"/>
                        </td>
                    </#if>
                    <td>
                        <#assign url>/master/settlement/${settlement.id?string['0']}/view</#assign>
                        <a href="<@spring.url url/>">${(settlement.id?string['0'])?left_pad(10)}</a>
                    </td>
                    <td>
                        ${settlement.district.region.name?html}
                    </td>
                    <td>
                        <#if !settlement.district.mock.id.value>
                            ${settlement.district.name?html}
                        </#if>
                    </td>
                    <td>
                        ${(settlement.kind.textValue!'')?html}
                    </td>
                    <td>
                        ${settlement.name?html}
                    </td>
                    <#if staff>
                        <td>
                            <#assign url>/api/settlement/${settlement.id?string['0']}/remove</#assign>
                            <#assign removeUrl><@spring.url url/></#assign>
                            <@commons.deleteEntityButton
                                    actionSelectorClass="-remove-settlement"
                                    actionUrl=removeUrl
                                    confirmationMessageKey="message.remove.settlement.confirmation"
                                    tooltipMessageKey="tooltip.remove.settlement"/>
                        </td>
                    </#if>
                </tr>
            </#list>
        </tbody>
    </table>
</div>