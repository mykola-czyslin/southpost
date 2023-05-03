<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="clinicTypeOptions"  type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.clinic.ClinicInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.ClinicSearchForm" -->
<#-- @ftlvariable name="medicalServiceSelectOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../contact/macro.ftl" as contacts/>
<form id="search-form" method="post" action="<@spring.url '/clinic/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="search-pane -row">
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
                <div class="-cell">
                    <#-- clinicType -->
                    <label for="clinicType"><@spring.message "label.clinic.type"/></label>
                </div>
                <div class="-cell">
                    <#-- clinicName -->
                    <label for="namePattern"><@spring.message "label.clinic.name"/></label>
                </div>
                <div class="-cell">
                    <label for="serviceNamePattern"><@spring.message "label.clinic.services"/></label>
                </div>
            </div>
            <div class="search-pane -row">
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
                    <@spring.formSingleSelect "searchForm.clinicType", clinicTypeOptions, ""/>
                </div>
                <div class="-cell">
                    <@spring.formInput "searchForm.namePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <@spring.formSingleSelect "searchForm.service", medicalServiceSelectOptions, ""/>
                </div>
            </div>
        </div>
        <div class="-table">
            <div class="search-pane -row">
                <div class="-cell">
                    <label for="searchForm.phonePattern"><@spring.message "label.phone.number"/></label>
                </div>
                <div class="search-form -cell">
                    <label for="searchForm.emailPattern"><@spring.message "label.mail.address"/></label>
                </div>
                <div class="-cell">&nbsp;</div>
            </div>
            <div class="search-pane -row">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="40" maxlength="80" title="${tooltip?html}"</#assign>
                <div class="-cell">
                    <@spring.formInput "searchForm.phonePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                    <@spring.formInput "searchForm.emailPattern", attributes, "text"/>
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
<script id="optionsTemplate" type="text/x-jQuery-tmpl">
    <option value="${r'${id}'}">${r"${name}"}</option>
</script>

<#assign pagingModel = pageListData.model/>
<#include "../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid clinic">
        <thead>
        <tr>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/clinic/manage/create" tooltipMessageKey="tooltip.add.clinic"/>
                </th>
            </#if>
            <th class="-clinic-id"><@commons.headerCaptionBox textKey="label.clinic.id"/></th>
            <th class="-clinic-name"><@commons.headerCaptionBox textKey="label.clinic.name"/></th>
            <th class="-clinic-type"><@commons.headerCaptionBox textKey="label.clinic.type"/></th>
            <th class="-clinic-description"><@commons.headerCaptionBox textKey="label.clinic.description"/></th>
            <th class="-clinic-services"><@commons.headerCaptionBox textKey="label.clinic.services"/></th>
            <th class="-clinic-settlement"><@commons.headerCaptionBox textKey="label.settlement"/></th>
            <th class="-clinic-phones"><@commons.headerCaptionBox textKey="label.contact.phones"/></th>
            <th class="-clinic-emails"><@commons.headerCaptionBox textKey="label.contact.emails"/></th>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/clinic/manage/create" tooltipMessageKey="tooltip.add.clinic"/>
                </th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as clinic>
            <tr>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/clinic/${clinic.id?string["0"]}/delete'/></#assign>
                    <td class="-grid-actions">
                        <@commons.deleteEntityButton actionSelectorClass="-remove-clinic" actionUrl=removeUrl
                            confirmationMessageKey="message.remove.clinic.confirmation"
                            tooltipMessageKey="tooltip.delete.clinic"/>
                    </td>
                </#if>
                <#assign viewUrl>/clinic/${clinic.id?string['0']}/view</#assign>
                <td class="-clinic-id"><a href="<@spring.url viewUrl/>">${clinic.id?string['0000000']}</a></td>
                <td class="-clinic-name">
                    <div>${(clinic.clinicName!'----')?html}</div>
                </td>
                <td class="-clinic-type"><#if clinic.clinicType??>${(clinic.clinicType.textValue!'')?html}<#else>-</#if></td>
                <td class="-clinic-description">
                    <div>${(clinic.description!'----')?html}</div>
                </td>
                <td class="-clinic-services">
                    <#if clinic.services??>
                        <ul>
                            <#list clinic.services as service>
                                <li><#if service??>${(service.textValue!'-')?html}</#if></li>
                            </#list>
                        </ul>
                    </#if>
                </td>
                <td class="-clinic-settlement">
                    <div><#if clinic.location??>${clinic.location.street.settlement.textValue?html}<#else>----</#if></div>
                </td>
                <td class="-clinic-phones">
                    <div><@contacts.phoneCSV clinic.phones/></div>
                </td>
                <td class="-clinic-emails">
                    <div><@contacts.emailCSV clinic.emails/></div>
                </td>
                <#if staff>
                    <#assign removeUrl><@spring.url '/api/clinic/${clinic.id?string["0"]}/delete'/></#assign>
                    <td class="-grid-actions">
                        <@commons.deleteEntityButton actionSelectorClass="-remove-clinic" actionUrl=removeUrl
                            confirmationMessageKey="message.remove.clinic.confirmation"
                            tooltipMessageKey="tooltip.delete.clinic"/>

                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
