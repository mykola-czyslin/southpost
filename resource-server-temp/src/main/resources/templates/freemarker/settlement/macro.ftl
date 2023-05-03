<#import "/spring.ftl" as spring/>
<#macro settlementFields errPathPrefix idPrefix settlementForm regionOptions districtOptions settlementKindOptions editable searchable>
<#-- @ftlvariable name="settlementForm" type="ua.southpost.resource.backup.web.model.forms.entity.SettlementForm" -->
<input type="hidden" name="${idPrefix}id" id="${idPrefix}id"
       value="${(settlementForm.id?string['0'])!''}"/>
<#if editable && errPathPrefix != "">
    <tr>
        <td class="form-field" colspan="2">
            <div id="data${errPathPrefix}" class="form-error hidden"></div>
        </td>
    </tr>
</#if>
<#if editable>
<tr>
    <td class="form-field" colspan="2">
        <div id="data${errPathPrefix}.regionId" class="form-error hidden"></div>
    </td>
</tr>
</#if>
<tr>
    <td class="form-label"><label for="${idPrefix}region"><@spring.message "label.region"/></label></td>
    <td class="form-field">
        <#if editable>
            <div data-on-change-url="<@spring.url '/api/region/{id}/districts'/>">
                <select id="${idPrefix}region" name="${idPrefix}region">
                    <#list regionOptions as regionValue, regionText>
                        <#if settlementForm.regionId() == regionValue>
                            <option value="${regionValue?html}" selected>${regionText?html}</option>
                        <#else>
                            <option value="${regionValue?html}">${regionText?html}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        <#else>
            <span id="${idPrefix}region">${(regionOptions[settlementForm.regionId()]!'-----')?html}</span>
        </#if>
    </td>
</tr>
<#if editable>
<tr>
    <td class="form-field" colspan="2">
        <div id="data${errPathPrefix}.districtId" class="form-error hidden"></div>
    </td>
</tr>
</#if>
<tr>
    <td class="form-label">
        <label for="${idPrefix}district"><@spring.message "label.district"/></label>
    </td>
    <td class="form-field">
        <#if editable>
            <select id="${idPrefix}district">
                <#list districtOptions as districtValue, districtText>
                    <#if settlementForm.districtId?? && settlementForm.districtId?string['0'] == districtValue>
                        <option value="${districtValue}" selected>${districtText}</option>
                    <#else>
                        <option value="${districtValue}">${districtText}</option>
                    </#if>
                </#list>
            </select>
        <#else>

            <span id="${idPrefix}district">
                <#if settlementForm.districtId??>
                    <#assign _districtId = settlementForm.districtId?string['0']/>
                    ${(districtOptions[_districtId]!'-----')?html}
                <#else>.&nbsp;.&nbsp;.&nbsp;.&nbsp;.</#if>
            </span>
        </#if>
    </td>
</tr>
<#if editable>
    <tr>
        <td class="form-field" colspan="2">
            <div id="data${errPathPrefix}.kind" class="form-error hidden"></div>
        </td>
    </tr>
</#if>
<tr>
    <td class="form-label">
        <label for="${idPrefix}kind"><@spring.message "label.settlement.kind"/></label>
    </td>
    <td class="form-field">
        <#if editable>
            <select id="${idPrefix}kind" name="${idPrefix}kind">
                <#list settlementKindOptions as kindValue, kindText>
                    <#if settlementForm.kind?? && settlementForm.kind.name() == kindValue>
                        <option value="${kindValue?html}" selected>${kindText?html}</option>
                    <#else>
                        <option value="${kindValue?html}">${kindText?html}</option>
                    </#if>
                </#list>
            </select>
        <#else>
            <span id="${idPrefix}kind"><#if settlementForm.kind??><@spring.message settlementForm.kind.messageKey/></#if></span>
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
        <label for="${idPrefix}name"><@spring.message "label.settlement.name"/></label>
    </td>
    <td class="form-field">
        <#if editable>
            <#assign settlementNameTooltip><@spring.message "tooltip.settlement.name.input"/></#assign>
            <input type="text"
                   id="${idPrefix}name"
                   name="${idPrefix}name"
                   value="${(settlementForm.name!'')?html}"
                   title="${settlementNameTooltip?html}"
                   class="-tooltip-target"/>
            <#if searchable>
                <#assign settlementLookupTooltip><@spring.message "tooltip.settlement.lookup"/></#assign>
                &nbsp;<span id="${idPrefix}name_lookup" class="-button -tooltip-target"
                            title="${settlementLookupTooltip?html}"
                            data-url="<@spring.url '/api/settlement/lookup?ajax=true'/>">
                <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/></span>
                <span id="${idPrefix}sweep" class="-button -tooltip-target" title="<@spring.message 'tooltip.sweep.settlement'/>">
                    <img src="${staticMediaUrl}/img/sweep.png" width="16" height="16" alt="sweep"/>
                </span>
            </#if>
        <#else>
            <span id="${idPrefix}name">${(settlementForm.name!'')?html}</span>
        </#if>
    </td>
</tr>

</#macro>

<#macro settlementInfo settlementFormObject regionOptions districtOptions>
<#-- @ftlvariable name="settlementFormObject" type="ua.southpost.resource.backup.web.model.forms.entity.SettlementForm" -->
<div class="-settlement -id" data-value="${settlementFormObject.id?string['0']}">
    <div class="-region -id" data-value="${settlementFormObject.regionId?html}">
        <span>${(regionOptions[settlementFormObject.regionId()]!'*')?html}</span>
        <span class="-districts hidden">
            <#list districtOptions as dVal, dText>
                <#if dVal != '-1'>
                    <span class="-option" data-value="${dVal}" data-text="${dText}">${dText}</span>
                </#if>
            </#list>
        </span>
    </div>
    <#assign _districtId = settlementFormObject.districtId?string['0']/>
    <div class="-district -id" data-value="${_districtId}">
        <span>${(districtOptions[_districtId]!'------')?html}</span>
    </div>
    <div class="-kind" data-value="${settlementFormObject.kind}">
        <span><@spring.message settlementFormObject.kind.messageKey/></span>
    </div>
    <div class="-name" data-value="${settlementFormObject.name?html}">
        <span>${settlementFormObject.name?html}</span>
    </div>
</div>
</#macro>

<#macro settlementInfoTemplate parentRef>
<div class="-settlement -id" data-value="${r'${'}${parentRef}${r'id}'}">
    <div class="-region -id" data-value="${r'${'}${parentRef}${r'district.region.id}'}">
        <span>${r'${'}${parentRef}${r'district.region.name}'}</span>
        <span class="-districts hidden">
            {{each ${parentRef}district.region.districts}}
                <span class="-option" data-value="${r'${$value.id}'}" data-text="${r'${$value.name}'}">${r'${$value.name}'}</span>
            {{/each}}
        </span>
    </div>
    <div class="-district -id" data-value="${r'${'}${parentRef}${r'district.id}'}">
        <span>${r'${'}${parentRef}${r'district.name}'}</span>
    </div>
    <div class="-kind" data-value="${r'${'}${parentRef}${r'kind.id}'}">
        <span>${r'${'}${parentRef}${r'kind_text}'}</span>
    </div>
    <div class="-name" data-value="${r'${'}${parentRef}${r'name}'}">
        <span>${r'${'}${parentRef}${r'name}'}</span>
    </div>
</div>
</#macro>

<#macro flatEntityInfo settlement>
<#-- @ftlvariable name="settlement" type="ua.southpost.resource.backup.web.model.dto.address.SettlementInfo"-->
<#assign kindText = settlement.kind.textValue/>
<#if settlement.district.mock.id.value>
    <#assign arguments = [settlement.name, kindText, settlement.district.region.name]/>
    <@spring.messageArgs "view.settlement.mock.district", arguments/>
<#else>
    <#assign arguments = [settlement.name, kindText, settlement.district.name, settlement.district.region.name]/>
    <@spring.messageArgs "view.settlement.regular", arguments/>
</#if>
</#macro>