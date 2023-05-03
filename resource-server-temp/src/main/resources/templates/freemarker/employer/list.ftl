<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.employer.EmployerInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.EmployerSearchForm" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../settlement/macro.ftl" as settlements/>
<#import "../location/macro.ftl" as locations/>
<#import "../contact/macro.ftl" as contacts/>
<@commons.optionsTemplate templateId="optionsTemplate"/>
<form id="searchForm" method="post" action="<@spring.url '/employers/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell"><label for="employerNamePattern"><@spring.message "label.employer.name"/></label></div>
                <div class="-cell"><label for="webSiteAddressPattern"><@spring.message "label.employer.website"/></label></div>
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
            </div>
            <div class="-row">
            <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
            <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${tooltip?html}"</#assign>
                <div class="-cell">
                <@spring.formInput "searchForm.employerNamePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <@spring.formCheckbox "searchForm.webSiteSignificant", attributes/>
                <#assign attributes>class="-tooltip-target" size="30" maxlength="200" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.webSiteAddressPattern", attributes, "text"/>
                </div>
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
                <div class="-cell">
                <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${tooltip?html}"</#assign>
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
    <table class="grid employer">
        <thead>
            <tr>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/employers/add" tooltipMessageKey="tooltip.add.employer"/>
                    </th>
                </#if>
                <th class="-employer-id"><@commons.headerCaptionBox textKey="label.employer.id"/></th>
                <th class="-employer-name"><@commons.headerCaptionBox textKey="label.employer.name"/></th>
                <th class="-employer-website"><@commons.headerCaptionBox textKey="label.employer.website"/></th>
                <th class="-employer-settlement"><@commons.headerCaptionBox textKey="label.settlement"/></th>
                <th class="-employer-location"><@commons.headerCaptionBox textKey="label.contact.address"/></th>
                <th class="-employer-phones"><@commons.headerCaptionBox textKey="label.contact.phones"/></th>
                <th class="-employer-emails"><@commons.headerCaptionBox textKey="label.contact.emails"/></th>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/employers/add" tooltipMessageKey="tooltip.add.employer"/>
                    </th>
                </#if>
            </tr>
        </thead>
        <tbody>
            <#list pageListData.entityList as employer>
                <tr>
                    <#if staff>
                        <#assign removeUrl><@spring.url '/api/employer/${employer.id?string["0"]}/remove'/></#assign>
                        <td class="-grid-actions">
                            <@commons.deleteEntityButton actionSelectorClass="-remove-employer" actionUrl=removeUrl confirmationMessageKey="message.remove.employer.confirmation" tooltipMessageKey="tooltip.remove.employer"/>
                        </td>
                    </#if>
                    <td class="-employer-id">
                        <#assign url>/employers/${employer.id?string['0']}/view</#assign>
                        <a href="<@spring.url url/>">${(employer.id?string['0'])?left_pad(10)}</a>
                    </td>
                    <td class="-employer-name">${employer.name?html}</td>
                    <td class="-employer-website">
                        <#if (employer.website!'') != ''>
                            <a href="${employer.website}" target="_blank">${employer.website?html}</a>
                        </#if>
                    </td>
                    <td class="-employer-settlement"><@settlements.flatEntityInfo settlement=employer.settlement/></td>
                    <td class="-employer-location">
                        <#if employer.location??>
                        <@locations.flatEntityInfo employer.location/>

                        </#if>
                    </td>
                    <td class="-employer-phones">
                        <@contacts.phoneCSV employer.phones/>
                    </td>
                    <td class="-employer-emails">
                        <@contacts.emailCSV employer.emails/>
                    </td>
                    <#if staff>
                        <#assign removeUrl><@spring.url '/api/employer/${employer.id?string["0"]}/remove'/></#assign>
                        <td class="-grid-actions">
                            <@commons.deleteEntityButton actionSelectorClass="-remove-employer" actionUrl=removeUrl confirmationMessageKey="message.remove.employer.confirmation" tooltipMessageKey="tooltip.remove.employer"/>
                        </td>
                    </#if>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
