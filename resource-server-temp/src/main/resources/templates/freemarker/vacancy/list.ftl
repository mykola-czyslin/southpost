<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="yesNoOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<SettlementKind, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<Long, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.vacancy.VacancyInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../settlement/macro.ftl" as settlements/>
<form id="search-form" method="post" action="<@spring.url '/vacancy/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="search-pane -row">
            <div class="search-form -cell">
            <#-- enterprise -->
                <label for="employerNamePattern"><@spring.message "label.vacancy.employer.name"/></label><br/>
                <@spring.formInput "searchForm.employerNamePattern", 'size="30" maxlength="80"', "text"/>
            </div>
            <div class="search-form -cell" data-on-change-url="<@spring.url '/api/region/{id}/districts'/>">
                <#-- region -->
                <label for="regionId"><@spring.message "label.region"/></label><br/>
                <@spring.formSingleSelect "searchForm.regionId", regionOptions, ""/>
            </div>
            <div class="search-form -cell">
            <#-- district -->
                <label for="districtId"><@spring.message "label.district"/></label><br/>
                <@spring.formSingleSelect "searchForm.districtId", districtOptions, ""/>
            </div>
            <div class="search-form -cell">
            <#-- settlement kind -->
                <label for="settlementKind"><@spring.message "label.settlement.kind"/></label><br/>
                <@spring.formSingleSelect "searchForm.settlementKind", settlementKindOptions, ""/>
            </div>
            <div class="search-form -cell">
            <#-- settlement name -->
                <label for="settlementNamePattern"><@spring.message "label.settlement.name"/></label><br/>
                <@spring.formInput "searchForm.settlementNamePattern", 'size="30" maxlength="80"', "text"/>
            </div>
        </div>
        <div class="search-pane -row">
            <div class="search-form -cell">
            <#-- contact phone -->
                <label for="phonePattern"><@spring.message "label.phone.number"/></label><br/>
                <@spring.formInput "searchForm.phonePattern", 'size="20" maxlength="20"', "text"/>
            </div>
            <div class="search-form -cell">
            <#-- contact email -->
                <label for="mailPattern"><@spring.message "label.mail.address"/></label><br/>
                <@spring.formInput "searchForm.mailPattern", 'size="30" maxlength="80"', "text"/>
            </div>
        </div>
        <div class="seats-pane -row">
            <div class="search-form -cell">
                <label for="summaryPattern"><@spring.message "label.vacancy.summary"/></label><br/>
                <@spring.formInput "searchForm.summaryPattern", 'size="40" maxlength="80"', "text"/>
            </div>
            <div class="search-form -cell">
                <label for="descriptionPattern"><@spring.message "label.vacancy.description"/></label><br/>
                <@spring.formInput "searchForm.descriptionPattern", 'size="40" maxlength="200"', "text"/>
            </div>
            <div class="search-form -cell">
                <label for="salaryLow"><@spring.message "label.vacancy.salary.low"/></label><br/>
                <@spring.formInput "searchForm.salaryLow", 'size="14"', "number"/>
            </div>
            <div class="search-form -cell">
                <label for="hosting"><@spring.message "label.vacancy.hosting"/></label><br/>
                <@spring.formSingleSelect "searchForm.hosting", yesNoOptions, ""/>
            </div>
            <div class="search-form -cell"><br/>
                <span class="-button -search-submit"><@spring.message "button.caption.search"/></span>
                <span class="-button -search-reset"><@spring.message "button.caption.reset"/></span>
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
    <table class="grid vacancy-grid">
        <thead>
        <tr>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/vacancy/manage/create" tooltipMessageKey="tooltip.add.vacancy"/>
                </th>
            </#if>
            <th class="-vacancy-id"><@commons.headerCaptionBox textKey="label.vacancy.id"/></th>
            <th class="-vacancy-settlement"><@commons.headerCaptionBox textKey="label.vacancy.settlement"/></th>
            <th class="-vacancy-employer-name"><@commons.headerCaptionBox textKey="label.vacancy.employer.name"/></th>
            <th class="-vacancy-employer-site"><@commons.headerCaptionBox textKey="label.vacancy.employer.site"/></th>
            <th class="-vacancy-summary"><@commons.headerCaptionBox textKey="label.vacancy.summary"/></th>
            <th class="-vacancy-description"><@commons.headerCaptionBox textKey="label.vacancy.description"/></th>
            <th class="-vacancy-salary-low"><@commons.headerCaptionBox textKey="label.vacancy.salary.low"/></th>
            <th class="-vacancy-salary-high"><@commons.headerCaptionBox textKey="label.vacancy.salary.high"/></th>
            <th class="-vacancy-hosting"><@commons.headerCaptionBox textKey="label.vacancy.hosting"/></th>
            <th class="-vacancy-phone"><@commons.headerCaptionBox textKey="label.vacancy.contacts.phone"/></th>
            <th class="-vacancy-email"><@commons.headerCaptionBox textKey="label.vacancy.contacts.email"/></th>
            <th class="-vacancy-modified-date"><@commons.headerCaptionBox textKey="label.last.modified.date"/></th>
        <#if staff>
            <th class="-vacancy-modified-by-user"><@commons.headerCaptionBox textKey="label.last.modified.user"/></th>
            <th class="-grid-actions">
                <@commons.addEntityButton actionUrl="/vacancy/manage/create" tooltipMessageKey="tooltip.add.vacancy"/>
            </th>
        </#if>
        </tr>
        </thead>
    <#setting locale="uk_UA"/>
        <tbody>
        <#list pageListData.entityList as vacancy>
        <tr>
            <#assign removeUrl><@spring.url '/api/vacancy/${vacancy.id?string["0"]}/delete'/></#assign>
            <#if staff>
                <td class="-vacancy-action -grid-actions">
                    <@commons.deleteEntityButton
                        actionSelectorClass="-remove-vacancy"
                        actionUrl=removeUrl
                        confirmationMessageKey="message.remove.vacancy.confirmation"
                        tooltipMessageKey="tooltip.remove.vacancy"/>
                </td>
            </#if>
            <#assign vacancyUrl = "/vacancy/${vacancy.id?string['0']}/view"/>
            <td class="-vacancy-id"><a class="action-details-link" href="<@spring.url vacancyUrl/>">${vacancy.id?string['000000000']}</a></td>
            <td class="-vacancy-settlement"><@settlements.flatEntityInfo settlement=vacancy.employer.settlement/></td>
            <#if vacancy.employer??>
                <td class="vacancy-employer-name">${vacancy.employer.name}</td>
                <td class="vacancy-employer-site"><#if vacancy.employer.website??><a target="_blank" href="${vacancy.employer.website}">${vacancy.employer.website}</a><#else>&nbsp;-&nbsp;</#if> </td>
            <#else>
                <td>&nbsp;-&nbsp;</td>
                <td>&nbsp;-&nbsp;</td>
            </#if>
            <td class="-vacancy-summary">${vacancy.summary!"&nbsp;-&nbsp;"}</td>
            <td class="-vacancy-description"><div>${vacancy.description!"&nbsp;-&nbsp;"}</div></td>
            <td class="-vacancy-salary-low"><#if vacancy.salaryLow??>${vacancy.salaryLow?string.currency}<#else>&nbsp;-&nbsp;</#if></td>
            <td class="-vacancy-salary-high"><#if vacancy.salaryHigh??>${vacancy.salaryHigh?string.currency}<#else>&nbsp;-&nbsp;</#if></td>
            <td class="-vacancy-hosting">${vacancy.hostingAvailable.textValue}</td>
            <#if vacancy.employer.phones??>
                <td class="-vacancy-phone">
                    <#if vacancy.employer.phones?? && vacancy.employer.phones?size gt 0>
                        <#assign phone = vacancy.employer.phones?first/>
                    ${phone.phoneNumber}
                    <#else>&nbsp;-&nbsp;</#if>
                </td>
                <td class="-vacancy-email">
                    <#if vacancy.employer.emails?? && vacancy.employer.emails?size gt 0>
                        <#assign emailAddress = vacancy.employer.emails?first/>
                        <a class="-link-e-mail" href="mailto:${emailAddress.emailAddress}" data-address="${emailAddress.emailAddress}">${emailAddress.emailAddress}</a><#else>&nbsp;-&nbsp;
                    </#if>
                </td>
            <#else>
                <td>&nbsp;-&nbsp;</td>
                <td>&nbsp;-&nbsp;</td>
            </#if>
            <td class="vacancy-modified-date">${vacancy.modificationTime}</td>
            <#if staff>
                <#assign firstNameDefault><@spring.message "label.user.firstName"/></#assign>
                <#assign lastNameDefault><@spring.message "label.user.lastName"/></#assign>
                <td class="-vacancy-modified-user"><a class="-link-e-mail" href="mailto:${vacancy.modifiedBy.email}" data-address="${vacancy.modifiedBy.email}">${vacancy.modifiedBy.firstName!firstNameDefault}&nbsp;${vacancy.modifiedBy.lastName!lastNameDefault}</a></td>
                <td class="-vacancy-action -grid-actions">
                    <@commons.deleteEntityButton
                            actionSelectorClass="-remove-vacancy"
                            actionUrl=removeUrl
                            confirmationMessageKey="message.remove.vacancy.confirmation"
                            tooltipMessageKey="tooltip.remove.vacancy"/>
                </td>
            </#if>
        </tr>
        </#list>
        </tbody>
    </table>
</div>

