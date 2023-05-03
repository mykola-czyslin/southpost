<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="employerForm" type="ua.southpost.resource.backup.web.model.forms.entity.EmployerForm" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="regionDistrictOptions" type="ua.southpost.resource.backup.web.model.page.RegionDistrictOptions" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<#import "macro.ftl" as employers/>
<#import "../contact/macro.ftl" as contacts/>
<#assign editable = submitType.formEditable/>
<div>
    <div class="employer-form-wrapper -layout-align-center -toggleable"
         data-cancel-url="<@spring.url '/employers/list'/>"
         data-success-url="<@spring.url '/employers/list'/>"
         data-action="<@spring.url '/api/employer/save'/>">
        <div id="data" class="form-error hidden"></div>
        <table>
            <tr>
                <td class="-subform">
                    <@employers.employerForm
                    formObject=employerForm
                    idPrefix="employer_"
                    errPathPrefix=""
                    regionOptions=regionOptions
                    regionDistrictOptions=regionDistrictOptions
                    settlementKindOptions=settlementKindOptions
                    streetKindOptions=streetKindOptions
                    editable=editable
                    searchable=false/>
                </td>
            </tr>
            <tr>
                <td class="form-field">
                    <div class="button-pane">
                        <#if editable>
                            <span class="-button -submit -employer"
                                  data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                            <span class="-button -reset -employer"><@spring.message "button.caption.reset"/></span>
                            <span class="-button -cancel -employer"><@spring.message "button.caption.cancel"/></span>
                        <#else>
                            <#assign closeUrl><@spring.url "/employers/list"/></#assign>
                            <span class="-button -close"
                                  data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                            <#if staff>
                                <#assign editActionUrl><@spring.url "/employers/${employerForm.id?string['0']}/edit"/></#assign>
                                <span class="-button -edit"
                                      data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                            </#if>
                        </#if>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <#if editable>
        <@contacts.phoneFormTemplate templateId="phoneFormTemplate" idPrefix="employer_contact_" errPathPrefix=".contact"/>
        <@contacts.mailFormTemplate templateId="mailFormTemplate"  idPrefix="employer_contact_" errPathPrefix=".contact"/>
        <@commons.entityLookupPopup containerId="lookupPopupContainer" templateId="popupLookupTemplate"/>
        <@commons.optionsTemplate templateId="optionsTemplate"/>
    </#if>
</div>