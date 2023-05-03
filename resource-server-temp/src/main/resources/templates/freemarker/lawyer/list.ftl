<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="lawCaseOptions"  type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.lawyer.LawyerAgencyInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.LawyerAgencySearchForm" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../settlement/macro.ftl" as settlements/>
<#import "../location/macro.ftl" as locations/>
<#import "../contact/macro.ftl" as contacts/>
<form id="search-form" method="post" action="<@spring.url '/lawyer/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell"><label for="namePattern"><@spring.message "label.employer.name"/></label></div>
                <div class="-cell"><label for="webSitePattern"><@spring.message "label.employer.website"/></label></div>
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
                    <@spring.formInput "searchForm.namePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <#assign attributes>class="-tooltip-target" size="30" maxlength="200" title="${tooltip?html}"</#assign>
                    <@spring.formInput "searchForm.webSitePattern", attributes, "text"/>
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
        <div class="-table">
            <div class="-row">
                <div class="-cell -min-width-12em -max-width-18em">
                    <#assign rolesTooltip><@spring.message 'tooltip.lawyer.search.cases'/></#assign>
                    <label for="lawCaseSet" class="-tooltip-target"
                           title="${(rolesTooltip!'')?html}"><@spring.message "label.lawyer.agency.cases"/></label>
                </div>
            </div>
            <div class="-row">
                <div class="-cell -any-case -confirmed -min-width-12em -max-width-18em">
                    <@spring.formCheckbox "searchForm.anyCase" ""/>
                    <label for="anyCase"><@spring.message "label.any.law.case"/></label>
                </div>
                <div class="-cell -min-width-12em -max-width-18em">
                    <#assign checkBoxSeparator>
                </div>
                <div class="-cell -min-width-12em -max-width-18em">
                    </#assign>
                    <@spring.formCheckboxes "searchForm.lawCaseSet" lawCaseOptions checkBoxSeparator/>
                </div>
            </div>
        </div>
    </div>
    <#include "../includes/sort-fields.ftl"/>
</form>

<#assign pagingModel = pageListData.model/>
<#include "../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid lawyer">
        <thead>
            <tr>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/lawyer/manage/create" tooltipMessageKey="tooltip.add.lawyer.agency"/>
                    </th>
                </#if>
                <th class="-lawyer-agency-id"><@commons.headerCaptionBox textKey="label.lawyer.agency.id"/></th>
                <th class="-lawyer-agency-name"><@commons.headerCaptionBox textKey="label.lawyer.agency.name"/></th>
                <th class="-layer-agency-web-site"><@commons.headerCaptionBox textKey="label.lawyer.agency.web.site"/></th>
                <th class="-lawyer-agency-cases"><@commons.headerCaptionBox textKey="label.lawyer.agency.cases"/></th>
                <th class="-layer-agency-settlement"><@commons.headerCaptionBox textKey="label.settlement"/></th>
                <th class="-lawyer-agency-location"><@commons.headerCaptionBox textKey="label.contact.address"/></th>
                <th class="-lawyer-agency-phones"><@commons.headerCaptionBox textKey="label.contact.phones"/></th>
                <th class="-lawyer-agency-emails"><@commons.headerCaptionBox textKey="label.contact.emails"/></th>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/lawyer/manage/create" tooltipMessageKey="tooltip.add.lawyer.agency"/>
                    </th>
                </#if>
            </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as lawyer>
            <tr>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/lawyer/${lawyer.id?string["0"]}/delete'/></#assign>
                    <td class="-grid-actions">
                        <@commons.deleteEntityButton actionSelectorClass="-remove-lawyer" actionUrl=removeUrl confirmationMessageKey="message.remove.employer.confirmation" tooltipMessageKey="tooltip.remove.lawyer"/>
                    </td>
                </#if>
                <td class="-lawyer-agency-id">
                    <#assign url>/lawyer/${lawyer.id?string['0']}/view</#assign>
                    <a href="<@spring.url url/>">${(lawyer.id?string['0'])?left_pad(10)}</a>
                </td>
                <td class="-lawyer-agency-name">${lawyer.agencyName?html}</td>
                <td class="-lawyer-agency-web-site">
                    <#if (lawyer.webSite!'') != ''>
                        <a href="${lawyer.webSite}" target="_blank">${lawyer.webSite?html}</a>
                    </#if>
                </td>
                <td class="-lawyer-agency-cases">
                    <#if lawyer.cases??>
                        <ul>
                            <#list lawyer.cases as case>
                                <li><#if case??>${(case.textValue!'-')?html}</#if></li>
                            </#list>
                        </ul>
                    </#if>
                </td>
                <td class="-lawyer-agency-settlement"><@settlements.flatEntityInfo settlement=lawyer.settlement/></td>
                <td class="-lawyer-agency-location">
                    <#if lawyer.location??>
                        <@locations.flatEntityInfo lawyer.location/>
                    </#if>
                </td>
                <td class="-lawyer-agency-phones">
                    <@contacts.phoneCSV lawyer.phones/>
                </td>
                <td class="-lawyer-agency-emails">
                    <@contacts.emailCSV lawyer.emails/>
                </td>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/lawyer/${lawyer.id?string["0"]}/delete'/></#assign>
                    <td class="-grid-actions">
                        <@commons.deleteEntityButton actionSelectorClass="-remove-lawyer" actionUrl=removeUrl confirmationMessageKey="message.remove.employer.confirmation" tooltipMessageKey="tooltip.remove.lawyer"/>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>