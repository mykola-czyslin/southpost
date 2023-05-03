<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.address.LocationInfo, java.lang.Long>" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#setting locale="uk_UA"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "../settlement/macro.ftl" as settlements/>
<#import "../street/macro.ftl" as streets/>
<@commons.optionsTemplate templateId="optionsTemplate"/>
<form id="searchForm" method="post" action="<@spring.url '/master/locations/list'/>">
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
                <div class="-cell"><label for="streetKind"><@spring.message "label.street.kind"/></label></div>
                <div class="-cell"><label for="streetNamePattern"><@spring.message "label.street.name"/></label></div>
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
                <@spring.formSingleSelect "searchForm.streetKind", streetKindOptions, ""/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.streetNamePattern", attributes, "text"/>
                </div>
            </div>
        </div>
        <div class="-table">
            <div class="-row">
                <div class="-cell"><label for="postalCodePattern"><@spring.message "label.location.postal"/></label></div>
                <div class="-cell"><label for="streetNumberPattern"><@spring.message "label.street.number"/></label></div>
                <div class="-cell"><label for="blockNumberPattern"><@spring.message "label.block.number"/></label></div>
                <div class="-cell"><label for="roomNumberPattern"><@spring.message "label.room.number"/></label></div>
                <div class="-cell"></div>
            </div>
            <div class="-row">
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="5" maxlength="5" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.postalCodePattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="10" maxlength="20" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.streetNumberPattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="10" maxlength="20" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.blockNumberPattern", attributes, "text"/>
                </div>
                <div class="-cell">
                <#assign tooltip><@spring.message 'tooltip.pattern'/></#assign>
                <#assign attributes>class="-tooltip-target" size="10" maxlength="20" title="${tooltip?html}"</#assign>
                <@spring.formInput "searchForm.roomNumberPattern", attributes, "text"/>
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
    <table class="grid settlement street location">
        <thead>
        <tr>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/master/locations/add" tooltipMessageKey="tooltip.add.address"/>
                </th>
            </#if>
            <th class="-location-id"><@commons.headerCaptionBox textKey="label.location.id"/></th>
            <th class="-location-settlement"><@commons.headerCaptionBox textKey="label.settlement"/></th>
            <th class="-location-street"><@commons.headerCaptionBox textKey="label.street"/></th>
            <th class="-location-postal"><@commons.headerCaptionBox textKey="label.location.postal"/></th>
            <th class="-location-street-number"><@commons.headerCaptionBox textKey="label.street.number"/></th>
            <th class="-location-block-number"><@commons.headerCaptionBox textKey="label.block.number"/></th>
            <th class="-location-room-number"><@commons.headerCaptionBox textKey="label.room.number"/></th>
            <#if staff>
                <th class="-grid-actions">
                    <@commons.addEntityButton actionUrl="/master/locations/add" tooltipMessageKey="tooltip.add.address"/>
                </th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as location>
        <tr>
            <#if staff>
                <td>
                    <#assign url>/api/locations/${location.id?string['0']}/remove</#assign>
                    <#assign removeUrl><@spring.url url/></#assign>
                    <@commons.deleteEntityButton actionSelectorClass="-remove-location" actionUrl=removeUrl confirmationMessageKey="message.remove.location.confirmation" tooltipMessageKey="tooltip.remove.location"/>
                </td>
            </#if>
            <td class="-location-id">
                <#assign url>/master/locations/${location.id?string['0']}/view</#assign>
                <a href="<@spring.url url/>">${(location.id?string['0'])?left_pad(10)}</a>
            </td>
            <td class="-location-settlement"><@settlements.flatEntityInfo settlement=location.street.settlement/></td>
            <td class="-location-street"><@streets.briefStreetText street=location.street/></td>
            <td class="-location-postal">${(location.postalCode!'')?html}</td>
            <td class="-location-street-number">${(location.streetNumber!'')?html}</td>
            <td class="-location-block-number">${(location.blockNumber!'')?html}</td>
            <td class="-location-room-number">${(location.roomNumber!'')?html}</td>
            <#if staff>
                <td>
                    <#assign url>/api/locations/${location.id?string['0']}/remove</#assign>
                    <#assign removeUrl><@spring.url url/></#assign>
                    <@commons.deleteEntityButton actionSelectorClass="-remove-location" actionUrl=removeUrl confirmationMessageKey="message.remove.location.confirmation" tooltipMessageKey="tooltip.remove.location"/>
                </td>
            </#if>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
