<#-- @ftlvariable name="locationForm" type="ua.southpost.resource.backup.web.model.forms.entity.LocationForm" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String,String>" -->
<#import "/spring.ftl" as spring>
<#import "macro.ftl" as locations/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<div id="manage-location-form"
     data-action="<@spring.url '/api/locations/save'/>"
     data-success-url="<@spring.url '/master/locations/list'/>"
     data-cancel-url="<@spring.url '/master/locations/list'/>">
    <div class="location-form-wrapper -layout-align-center">
        <div id="data" class="form-error hidden"></div>
        <table>
            <@locations.locationFormFields
                errPathPrefix=""
                idPrefix="location_"
                locationForm=locationForm
                regionOptions=regionOptions
                districtOptions=districtOptions
                settlementKindOptions=settlementKindOptions
                streetKindOptions=streetKindOptions
                editable=editable
                searchable=false/>
            <tr>
                <td class="form-field" colspan="2">
                    <div class="button-pane">
                    <#if editable>
                        <span class="-button -submit" data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                        <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                        <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                    <#else>
                        <#assign closeUrl><@spring.url "/master/locations/list"/></#assign>
                        <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                        <#if staff>
                            <#assign editActionUrl><@spring.url "/master/locations/${locationForm.id?string['0']}/edit"/></#assign>
                            <span class="-button -edit" data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                        </#if>
                    </#if>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>

<#if editable>
<@commons.entityLookupPopup containerId="lookupPopupContainer" templateId="popupLookupTemplate"/>
<@commons.optionsTemplate templateId="optionsTemplate"/>
</#if>