<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#import "/spring.ftl" as spring/>
<#import "../settlement/macro.ftl" as settlements/>
<#import "../contact/macro.ftl" as contacts/>
<#import "../common/macro.ftl" as commons/>
<#macro employerForm
    formObject
    idPrefix
    errPathPrefix
    editable
    searchable
    regionOptions
    regionDistrictOptions
    settlementKindOptions
    streetKindOptions>
<#-- @ftlvariable name="formObject" type="ua.southpost.resource.backup.web.model.forms.entity.EmployerForm" -->
<#-- @ftlvariable name="idPrefix" type="java.lang.String" -->
<#-- @ftlvariable name="errPathPrefix type="java.lang.String" -->
<#-- @ftlvariable name="editable" type="boolean" -->
<#-- @ftlvariable name="searchable" type="boolean" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="regionDistrictOptions" type="ua.southpost.resource.backup.web.model.page.RegionDistrictOptions" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#if editable>
    <div id="data${errPathPrefix}.employer" class="form-error hidden"></div>
    <input id="${idPrefix}id" type="hidden" name="${idPrefix}id" value="${((formObject.id?string['0'])!'')?html}"/>
</#if>
<table id="${idPrefix}form">
    <caption><@spring.message "label.employer.form"/></caption>
    <#--
        employer name               |  employer settlement
        emp website                 |
        emp description             |
        ---------------------------------------------------
         contact 1
         ----------
         contact 2
         ----------
         ...
         ----------
         contact N
    -->
    <tr>
        <td class="form-field">
            <table>
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data${errPathPrefix}.employer.name" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td class="form-label"><label for="${idPrefix}name"><@spring.message "label.employer.name"/></label></td>
                    <td class="form-field">
                        <div>
                            <#if editable>
                                <input id="${idPrefix}name" type="text" name="${idPrefix}name" value="${((formObject.name)!'')?html}"/>
                                <#if searchable>
                                <span id="${idPrefix}lookup" class="-button" data-url="<@spring.url '/api/employer/lookup'/>">
                <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/></span>
                                <span id="${idPrefix}sweep" class="-button -tooltip-target" title="<@spring.message 'tooltip.sweep.employer'/>">
                                    <img src="${staticMediaUrl}/img/sweep.png" width="16" height="16" alt="sweep"/>
                                </span>
                                </#if>
                            <#else>
                                <span id="${idPrefix}name">${((formObject.name)!'')?html}</span>
                            </#if>
                        </div>
                    </td>
                </tr>
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data${errPathPrefix}.employer.website" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}website"><@spring.message "label.vacancy.employer.site"/></label>
                    </td>
                    <td class="form-field">
                        <#if editable>
                            <input id="${idPrefix}website" type="text" name="${idPrefix}website" value="${((formObject.website)!'')?html}"/>
                        <#else>
                            <span id="${idPrefix}website">${((formObject.website)!'')?html}</span>
                        </#if>
                    </td>
                </tr>
            </table>
        </td>
        <td class="form-field">
            <#if editable>
                <div id="data${errPathPrefix}.employer.settlement" class="form-error hidden"></div>
            </#if>
            <table>
                <caption><@spring.message "label.settlement"/></caption>
                <#assign _districtOptions=regionDistrictOptions.districtOptions(formObject.regionId())/>

                <@settlements.settlementFields
                    settlementForm=formObject.settlement
                    idPrefix="${idPrefix}settlement_"
                    errPathPrefix="${errPathPrefix}.settlement"
                    regionOptions=regionOptions
                    districtOptions=_districtOptions
                    settlementKindOptions=settlementKindOptions
                    editable=editable
                    searchable=true/>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <#-- Contact -->
            <#assign contactDistrictOptions = regionDistrictOptions.districtOptions(formObject.contact.regionId())/>
            <@contacts.contactForm
            formObject=formObject.contact
            idPrefix="${idPrefix}contact_"
            errPathPrefix="${errPathPrefix}.contact"
            editable=editable
            searchable=true
            regionOptions=regionOptions
            districtOptions=contactDistrictOptions
            settlementKindOptions=settlementKindOptions
            streetKindOptions=streetKindOptions/>
        </td>
    </tr>
</table>
</#macro>