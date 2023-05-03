<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#import "../settlement/macro.ftl" as settlements/>
<#macro streetFormFields streetForm editable searchable errPathPrefix idPrefix regionOptions districtOptions settlementKindOptions streetKindOptions>

    <#assign settlementForm = streetForm.settlement/>
    <#if editable && errPathPrefix != "">
        <tr>
            <td class="form-field" colspan="2">
                <div id="data${errPathPrefix}" class="form-error hidden"></div>
            </td>
        </tr>
    </#if>

    <input type="hidden" name="${idPrefix}id" id="${idPrefix}id" value="${(streetForm.id?string['0'])!''}"/>
    <@settlements.settlementFields
    errPathPrefix="${errPathPrefix}.settlement"
    idPrefix="${idPrefix}settlement_"
    settlementForm=settlementForm
    regionOptions=regionOptions
    districtOptions=districtOptions
    settlementKindOptions=settlementKindOptions
    editable=editable
    searchable=true/>
    <#if editable>
        <tr>
            <td class="form-field" colspan="2">
                <div id="data${errPathPrefix}.kind" class="form-error hidden"></div>
            </td>
        </tr>
    </#if>
    <tr>
        <td class="form-label"><label for="${idPrefix}kind"><@spring.message "label.street.kind"/></label></td>
        <td class="form-field">
            <#if editable>
                <select id="${idPrefix}kind">
                    <#list streetKindOptions as kindValue, kindText>
                        <#if streetForm.kind?? && streetForm.kind.name() == kindValue>
                            <option value="${kindValue?html}" selected>${kindText?html}</option>
                        <#else>
                            <option value="${kindValue?html}">${kindText?html}</option>
                        </#if>
                    </#list>
                </select>
            <#else>
                <span id="${idPrefix}kind"><#if streetForm.kind??><@spring.message streetForm.kind.messageKey/></#if></span>
            </#if>
        </td>
    </tr>
    <#if editable>
        <tr>
            <td class="form-field" colspan="2">
                <div id="data${errPathPrefix}.name" class="form-error hidden"></div>
            </td>
        </tr>
    </#if>
    <tr>
        <td class="form-label">
            <label for="${idPrefix}name"><@spring.message "label.street.name"/></label>
        </td>
        <td class="form-field">
            <#if editable>
                <#assign tooltip><@spring.message "tooltip.street.name.input"/></#assign>
                <#if searchable>
                    <#assign patternTooltip><@spring.message "tooltip.pattern"/></#assign>
                    <#assign tooltip>${tooltip?html}&nbsp;&#10;&nbsp;${patternTooltip?html}</#assign>
                <#else>
                    <#assign tooltip = "${tooltip?html}"/>
                </#if>
                <input id="${idPrefix}name" name="${idPrefix}name" type="text" size="40" maxlength="80"
                       value="${(streetForm.name!'')?html}"
                       class="-tooltip-target"
                       title="${tooltip}"/>
                <#if searchable>
                    <#assign streetLookupTooltip><@spring.message "tooltip.street.lookup"/></#assign>
                    &nbsp;<span id="${idPrefix}name_lookup" class="-button -tooltip-target"
                                title="${streetLookupTooltip?html}"
                                data-url="<@spring.url '/api/street/lookup?ajax=true'/>" data-page="1">
                <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/></span>
                    <span id="${idPrefix}sweep" class="-button -tootip-target" title="<@spring.message 'tooltip.sweep.street'/>">
                        <img src="${staticMediaUrl}/img/sweep.png" width="16" height="16" alt="sweep"/>
                    </span>
                </#if>
            <#else>
                ${streetForm.name!'-'}
            </#if>
        </td>
    </tr>
</#macro>
<#macro streetInfo streetFormObject regionOptions districtOptions>
    <div class="-street -id" data-value="${streetFormObject.id?string['0']}">
        <@settlements.settlementInfo
        settlementFormObject=streetFormObject.settlement
        regionOptions=regionOptions
        districtOptions=districtOptions/>
        <div class="-kind" data-value="${streetFormObject.kind}">
            <span><@spring.message streetFormObject.kind.messageKey/></span>
        </div>
        <div class="-name" data-value="${streetFormObject.name?html}">${streetFormObject.name?html}</div>
    </div>
</#macro>

<#macro streetInfoTemplate parentRef>
    <div class="-street -id" data-value="${r'${'}${parentRef}${r'id}'}">
        <@settlements.settlementInfoTemplate parentRef="${parentRef}settlement."/>
        <div class="-kind" data-value="${r'${'}${parentRef}${r'kind.id}'}">
            <span>${r'${'}${parentRef}${r'kind_text}'}</span>
        </div>
        <div class="-name" data-value="${r'${'}${parentRef}${r'name}'}">${r'${'}${parentRef}${r'name}'}</div>
    </div>
</#macro>

<#macro flatEntityInfo street>
<#-- @ftlvariable name="street" type="ua.southpost.resource.backup.web.model.dto.address.StreetInfo" -->
    <#assign settlementText><@settlements.flatEntityInfo settlement=street.settlement/></#assign>
    <#assign kindText><@spring.message street.kind.messageKey/></#assign>
    <#assign arguments = [street.displayName, kindText, settlementText]/>
    <@spring.messageArgs "view.street.text", arguments/>
</#macro>

<#macro briefStreetText street>
<#-- @ftlvariable name="street" type="ua.southpost.resource.backup.web.model.dto.address.StreetInfo" -->
    <#assign streetArgs = [street.kind.textValue, street.name]/>
    <@spring.messageArgs "view.street.brief.text", streetArgs/>
</#macro>