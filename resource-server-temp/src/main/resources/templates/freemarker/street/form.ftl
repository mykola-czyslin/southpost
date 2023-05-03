<#-- @ftlvariable name="streetForm" type="ua.southpost.resource.backup.web.model.forms.entity.StreetForm" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String,String>" -->
<#import "macro.ftl" as streets/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<div id="manage-street-form"
     data-action="<@spring.url '/api/streets/save'/>"
     data-success-url="<@spring.url '/master/streets/list'/>"
     data-cancel-url="<@spring.url '/master/streets/list'/>">
    <div>
        <div class="street-form-wrapper -layout-align-center">
            <div id="data" class="form-error hidden"></div>
            <div class="-form-frame">
                <table>
<#-- begin macro call -->
    <@streets.streetFormFields streetForm=streetForm
        regionOptions=regionOptions
        districtOptions=districtOptions
        settlementKindOptions=settlementKindOptions
        streetKindOptions=streetKindOptions
        errPathPrefix=""
        idPrefix="street_"
        editable=editable
        searchable=false/>

<#-- end of macro call -->
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="button-pane">
                            <#if editable>
                                <span class="-button -submit" data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                                <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                                <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                            <#else>
                                <#assign closeUrl><@spring.url "/master/streets/list"/></#assign>
                                <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                                <#if staff>
                                    <#assign editActionUrl><@spring.url "/master/streets/${streetForm.id?string['0']}/edit"/></#assign>
                                    <span class="-button -edit" data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                                </#if>
                            </#if>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<#if editable>
    <@commons.entityLookupPopup containerId="lookupPopupContainer" templateId="popupLookupTemplate"/>
    <@commons.optionsTemplate templateId="optionsTemplate"/>
</#if>