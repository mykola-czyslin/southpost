<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="dwellingKindOptions"  type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="billingPeriodOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../settlement/macro.ftl" as settlements/>
<#import "../location/macro.ftl" as locations/>
<#import "../contact/macro.ftl" as contacts/>
<form id="search-form" method="post" action="<@spring.url '/dwelling/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
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
                    <label for="searchForm.settlementAreaPattern"><@spring.message "label.settlement.area"/></label>
                </div>
                <div class="-cell">
                    <label for="searchForm.dwellingKind"><@spring.message "label.dwelling.kind"/></label>
                </div>
                <div class="-cell">
                    <span class="label"><@spring.message "label.number.of.rooms"/></span>
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
                <@spring.formInput "searchForm.settlementAreaPattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <@spring.formSingleSelect "searchForm.dwellingKind", dwellingKindOptions, ""/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.optional.int'/></#assign>
                <#assign attributes>class="-tooltip-target" size="12" title="${tooltip?html}"</#assign>
                    <div class="-table">
                        <div class="-row">
                            <div class="-cell">
                                <label for="searchForm.numberOfRoomsFrom"><@spring.message "label.from"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.numberOfRoomsFrom", attributes, "number"/>
                            </div>
                            <div class="-cell">
                                <label for="searchForm.numberOfRoomsTo"><@spring.message "label.to"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.numberOfRoomsFrom", attributes, "number"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="-table">
            <div class="search-pane -row">
                <div class="-cell">
                    <span class="label"><@spring.message "label.total.area"/></span>
                </div>
                <div class="-cell">
                    <span class="label"><@spring.message "label.living.area"/></span>
                </div>
                <div class="-cell">
                    <label for=""><@spring.message "label.billing.period"/></label>
                </div>
                <div class="-cell">
                    <span class="label"><@spring.message "label.price"/></span>
                </div>
            </div>
            <div class="search-pane -row">
                <#assign tooltip><@spring.message 'tooltip.optional.decimal.4'/></#assign>
                <#assign attributes>class="-tooltip-target" size="12" title="${tooltip?html}"</#assign>
                <div class="search-form -cell">
                    <div class="-table">
                        <div class="-row">
                            <div class="-cell">
                                <label for="searchForm.totalAreaFrom"><@spring.message "label.from"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.totalAreaFrom", attributes, "number"/>
                            </div>
                            <div class="-cell">
                                <label for="searchForm.totalAreaTo"><@spring.message "label.to"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.totalAreaTo", attributes, "number"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="-cell">
                    <div class="-table">
                        <div class="-row">
                            <div class="search-form -cell">
                                <label for="searchForm.livingAreaFrom"><@spring.message "label.from"/></label>
                            </div>
                            <div class="search-form -cell">
                            <@spring.formInput "searchForm.livingAreaFrom", attributes, "number"/>
                            </div>
                            <div class="search-form -cell">
                                <label for="searchForm.livingAreaTo"><@spring.message "label.to"/></label>
                            </div>
                            <div class="search-form -cell">
                            <@spring.formInput "searchForm.livingAreaTo", attributes, "number"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="-cell">
                <@spring.formSingleSelect "searchForm.billingPeriod", billingPeriodOptions, ""/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.optional.decimal.2'/></#assign>
                <#assign attributes>class="-tooltip-target" size="12" title="${tooltip?html}"</#assign>
                    <div class="-table">
                        <div class="-row">
                            <div class="-cell">
                                <label for="searchForm.priceFrom"><@spring.message "label.from"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.priceFrom", attributes, "text"/>
                            </div>
                            <div class="-cell">
                                <label for="searchForm.priceTo"><@spring.message "label.to"/></label>
                            </div>
                            <div class="-cell">
                            <@spring.formInput "searchForm.priceTo", attributes, "text"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="-table">
            <div class="search-pane -row">
                <div class="-cell">
                    <label for="searchForm.contactPhonePattern"><@spring.message "label.phone.number"/></label>
                </div>
                <div class="search-form -cell">
                    <label for="searchForm.contactEmailPattern"><@spring.message "label.mail.address"/></label>
                </div>
                <div class="-cell">&nbsp;</div>
            </div>
            <div class="search-pane -row">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="40" maxlength="80" title="${tooltip?html}"</#assign>
                <div class="-cell">
                <@spring.formInput "searchForm.contactPhonePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <@spring.formInput "searchForm.contactEmailPattern", attributes, "text"/>
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
    <table class="grid dwelling">
        <thead>
            <tr>
                <#if staff>
                    <th class="-grid-actions">
                        <@commons.addEntityButton actionUrl="/dwelling/manage/create" tooltipMessageKey="tooltip.add.dwelling"/>
                    </th>
                </#if>
                <th class="-dwelling-id"><@commons.headerCaptionBox textKey="label.dwelling.id"/></th>
                <th class="-dwelling-settlement"><@commons.headerCaptionBox textKey="label.settlement"/></th>
                <th class="-dwelling-settlement-area"><@commons.headerCaptionBox textKey="label.settlement.area"/></th>
                <th class="-dwelling-kind"><@commons.headerCaptionBox textKey="label.dwelling.kind"/></th>
                <th class="-dwelling-number-of-rooms"><@commons.headerCaptionBox textKey="label.number.of.rooms"/></th>
                <th class="-dwelling-total-area"><@commons.headerCaptionBox textKey="label.total.area"/></th>
                <th class="-dwelling-living-area"><@commons.headerCaptionBox textKey="label.living.area"/></th>
                <th class="-dwelling-price"><@commons.headerCaptionBox textKey="label.price"/></th>
                <th class="-dwelling-billing-period"><@commons.headerCaptionBox textKey="label.billing.period"/></th>
                <th class="-dwelling-address"><@commons.headerCaptionBox textKey="label.contact.address"/></th>
                <th class="-dwelling-phones"><@commons.headerCaptionBox textKey="label.contact.phones"/></th>
                <th class="-dwelling-emails"><@commons.headerCaptionBox textKey="label.contact.emails"/></th>
                <th class="-dwelling-modified-date"><@commons.headerCaptionBox textKey="label.last.modified.date"/></th>
            <#if staff>
                <th class="-dwelling-modified-by"><@commons.headerCaptionBox textKey="label.last.modified.user"/></th>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/dwelling/manage/create" tooltipMessageKey="tooltip.add.dwelling"/>
                </th>
            </#if>
            </tr>
        </thead>
        <tbody>
            <#list pageListData.entityList as dwelling>
                <tr>
                    <#if staff>
                        <#assign removeUrl><@spring.url '/api/dwelling/${dwelling.id?string["0"]}/delete'/></#assign>
                        <td class="-grid-actions">
                            <@commons.deleteEntityButton
                            actionSelectorClass="-remove-dwelling"
                            actionUrl=removeUrl
                            confirmationMessageKey="message.remove.dwelling.confirmation"
                            tooltipMessageKey="tooltip.remove.dwelling"/>
                        </td>
                    </#if>
                    <#assign viewUrl>/dwelling/${dwelling.id?string['0']}/view</#assign>
                    <td class="-dwelling-id"><a href="<@spring.url viewUrl/>">${dwelling.id?string['0000000']}</a></td>
                    <td class="-dwelling-settlement"><@settlements.flatEntityInfo settlement=dwelling.settlement/></td>
                    <td class="-dwelling-settlement-area">${dwelling.settlementArea?html}</td>
                    <td class="-dwelling-kind">${(dwelling.kind.textValue!'')?html}</td>
                    <td class="-dwelling-number-of-rooms">${(dwelling.numberOfRooms!0)?string['0']}</td>
                    <td class="-dwelling-total-area">${(dwelling.totalArea!0)?string['0.####']}</td>
                    <td class="-dwelling-living-area">${(dwelling.livingArea!0)?string['0.####']}</td>
                    <td class="-dwelling-price">${(dwelling.price?string.currency)!'-'}</td>
                    <td class="-dwelling-billing-period"><#if dwelling.billingPeriod??>${(dwelling.billingPeriod.textValue)}</#if></td>
                    <td class="-dwelling-address">
                        <#if dwelling.address??>
                            <@locations.flatEntityInfo  dwelling.address/>
                        </#if>
                    </td>
                    <td class="-dwelling-phones"><@contacts.phoneCSV dwelling.phones/></td>
                    <td class="-dwelling-emails"><@contacts.emailCSV dwelling.emails/></td>
                    <td class="-dwelling-modification-time">${(dwelling.modificationTime)?iso_local_nz}</td>
                    <#if staff>
                        <#assign firstNameDefault><@spring.message "label.user.firstName"/></#assign>
                        <#assign lastNameDefault><@spring.message "label.user.lastName"/></#assign>
                        <td class="-dwelling-modified-by"><a class="-link-e-mail" href="mailto:${dwelling.modifiedBy.email}" data-address="${dwelling.modifiedBy.email}">${dwelling.modifiedBy.firstName!firstNameDefault}&nbsp;${dwelling.modifiedBy.lastName!lastNameDefault}</a></td>
                        <#assign removeUrl><@spring.url '/api/dwelling/${dwelling.id?string["0"]}/delete'/></#assign>
                        <td class="-grid-actions">
                            <@commons.deleteEntityButton
                                actionSelectorClass="-remove-dwelling"
                                actionUrl=removeUrl
                                confirmationMessageKey="message.remove.dwelling.confirmation"
                                tooltipMessageKey="tooltip.remove.dwelling"/>
                        </td>
                    </#if>
                </tr>
            </#list>
        </tbody>
    </table>
</div>