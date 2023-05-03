<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#import "../street/macro.ftl" as streets/>
<#import "../settlement/macro.ftl" as settlements/>
<#macro locationFormFields locationForm errPathPrefix idPrefix editable searchable
regionOptions districtOptions settlementKindOptions streetKindOptions>
    <input type="hidden" id="${idPrefix}id" name="${idPrefix}id" value="${(locationForm.id?string['0'])!''}"/>
    <#if editable && errPathPrefix != "">
    <tr>
        <td class="form-field" colspan="2">
            <div id="data${errPathPrefix}" class="form-error hidden"></div>
        </td>
    </tr>
    </#if>
    <tr>
        <td class="-subform">
            <table>
                <@streets.streetFormFields streetForm=locationForm.street
                regionOptions=regionOptions
                districtOptions=districtOptions
                settlementKindOptions=settlementKindOptions
                streetKindOptions=streetKindOptions
                errPathPrefix="${errPathPrefix}.street"
                idPrefix="${idPrefix}street_"
                editable=editable
                searchable=true/>
            </table>
        </td>
        <td class="-subform">
            <table>
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data${errPathPrefix}.postalCode" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}postal_code"><@spring.message "label.location.postal"/></label>
                    </td>
                    <td class="form-field">
                        <#if editable>
                            <#assign tooltip><@spring.message "tooltip.postal.code"/></#assign>
                            <input  id="${idPrefix}postal_code" name="${idPrefix}postal_code"
                                    type="text"
                                    value="${(locationForm.postalCode!'')?html}"
                                    title="${tooltip?html}" size="5" maxlength="5"
                                    pattern="[0-9]{5}"
                                    placeholder="00000"/>
                        <#else>
                            <span id="${idPrefix}postal_code">${(locationForm.postalCode!'')?html}</span>
                        </#if>
                    </td>
                </tr>
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data${errPathPrefix}.streetNumber" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}street_number"><@spring.message "label.street.number"/></label>
                    </td>
                    <td class="form-field">
                        <#if editable>
                            <#assign tooltip><@spring.message "tooltip.street.number"/></#assign>
                            <input id="${idPrefix}street_number" name="${idPrefix}street_number"
                                   type="text" size="10" maxlength="20" class="-tooltip-target"
                                   title="${tooltip?html}"
                                   value="${(locationForm.streetNumber!'')?html}"/>
                        <#else>
                            <span id="${idPrefix}street_number">${(locationForm.streetNumber!'')?html}</span>
                        </#if>
                    </td>
                </tr>
                <#if editable>
                <tr>
                    <td class="form-field" colspan="2">
                        <div id="data${errPathPrefix}.blockNumber" class="form-error hidden"></div>
                    </td>
                </tr>
                </#if>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}blockNumber"><@spring.message "label.block.number"/></label>
                    </td>
                    <td class="form-field">
                        <#if editable>
                            <#assign tooltip><@spring.message "tooltip.location.block"/></#assign>
                            <input id="${idPrefix}block_number" name="${idPrefix}block_number"
                                   type="text" size="10" maxlength="20" class="-tooltip-target"
                                   title="${tooltip?html}"
                                   value="${(locationForm.blockNumber!'')?html}"/>
                        <#else>
                            <span id="${idPrefix}block_number">${(locationForm.blockNumber!'')?html}</span>
                        </#if>
                    </td>
                </tr>
                <#-- roomNumber -->
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data${errPathPrefix}.roomNumber" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}room_number"><@spring.message "label.room.number"/></label>
                    </td>
                    <td class="form-field">
                        <#if editable>
                            <#assign tooltip><@spring.message "tooltip.location.room"/></#assign>
                            <input id="${idPrefix}room_number" name="${idPrefix}room_number"
                                   type="text" size="10" maxlength="20" class="-tooltip-target"
                                   title="${tooltip?html}"
                                   value="${(locationForm.roomNumber!'')?html}"/>
                            <#if searchable>
                                <span id="${idPrefix}lookup" class="-button -tooltip-target" data-url="<@spring.url '/api/locations/lookup'/>"
                                    title="<@spring.message 'tooltip.address.lookup'/>">
                <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/></span>
                                <span id="${idPrefix}sweep" class="-button -tooltip-target" title="<@spring.message 'tooltip.sweep.location'/>">
                                    <img src="${staticMediaUrl}/img/sweep.png" width="16" height="16" alt="sweep"/>
                                </span>
                            </#if>
                        <#else>
                            <span id="${idPrefix}room_number">${(locationForm.roomNumber!'')?html}</span>
                        </#if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</#macro>

<#macro locationInfo locationFormObject regionOptions districtOptions>
<div class="-location -id" data-value="${locationFormObject.id?string['0']}">
    <div class="-postal" data-value="${(locationFormObject.postalCode!'')?html}">${(locationFormObject.postalCode!'')?html}</div>
    <@streets.streetInfo
        streetFormObject=locationFormObject.street
        regionOptions=regionOptions
        districtOptions=districtOptions/>
    <div class="-street-number" data-value="${locationFormObject.streetNumber?html}">${locationFormObject.streetNumber?html}</div>
    <div class="-block-number" data-value="${(locationFormObject.blockNumber!'')?html}">${(locationFormObject.blockNumber!'')?html}</div>
    <div class="-room-number" data-value="${(locationFormObject.roomNumber!'')?html}">${(locationFormObject.roomNumber!'')?html}</div>
</div>

</#macro>

<#macro locationInfoTemplate parentRef>
<div class="-location -id" data-value="${r'${'}${parentRef}${r'id}'}">
    <div class="-postal" data-value="${r'${'}${parentRef}${r'postal_code}'}">${r'${'}${parentRef}${r'postal_code}'}</div>
    <@streets.streetInfoTemplate parentRef="${parentRef}street."/>
    <div class="-street-number" data-value="${r'${'}${parentRef}${r'street_number}'}">${r'${'}${parentRef}${r'street_number}'}</div>
    <div class="-block-number" data-value="${r'${'}${parentRef}${r'block_number}'}">${r'${'}${parentRef}${r'block_number}'}</div>
    <div class="-room-number" data-value="${r'${'}${parentRef}${r'room_number}'}">${r'${'}${parentRef}${r'room_number}'}</div>
</div>

</#macro>

<#macro flatEntityInfo location>
<#-- @ftlvariable name="location" type="ua.southpost.resource.backup.web.model.dto.address.LocationInfo" -->
<#assign settlementText><@settlements.flatEntityInfo settlement=location.street.settlement/></#assign>
<#assign briefStreetText><@streets.briefStreetText street=location.street/></#assign>
<#if (location.postalCode!'') != ''  && (location.blockNumber!'') != '' && (location.roomNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.postalCode, location.blockNumber, location.roomNumber, location.postalCode]/>
    <@spring.messageArgs "view.location.street-block-room-postal", args/>
<#elseif (location.postalCode!'') != '' && (location.blockNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.blockNumber, location.postalCode]/>
    <@spring.messageArgs "view.location.street-block-postal", args/>
<#elseif (location.postalCode!'') != '' && (location.roomNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.roomNumber, location.postalCode]/>
    <@spring.messageArgs "view.location.street-room-postal", args/>
<#elseif (location.postalCode!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.postalCode]/>
    <@spring.messageArgs "view.location.street-postal", args/>
<#elseif (location.blockNumber!'') != '' && (location.roomNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.blockNumber, location.roomNumber]/>
    <@spring.messageArgs "view.location.street-block-room", args/>
<#elseif (location.blockNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.blockNumber]/>
    <@spring.messageArgs "view.location.street-block", args/>
<#elseif (location.roomNumber!'') != ''>
    <#assign args = [settlementText, briefStreetText, location.streetNumber, location.roomNumber]/>
    <@spring.messageArgs "view.location.street-room", args/>
<#else>
    <#assign args = [settlementText, briefStreetText, location.streetNumber]/>
    <@spring.messageArgs "view.location.street-only", args/>
</#if>
</#macro>