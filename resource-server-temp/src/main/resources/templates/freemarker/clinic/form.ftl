<#-- @ftlvariable name="staff" type="Boolean" -->
<#-- @ftlvariable name="statics" type="freemarker.template.TemplateHashModel" -->
<#-- @ftlvariable name="clinicTypeOptions"  type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="clinicForm" type="ua.southpost.resource.backup.web.model.forms.entity.ClinicForm" -->
<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#-- @ftlvariable name="medicalServiceOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#import "/spring.ftl" as spring/>
<#import "../contact/macro.ftl" as contacts/>
<#import "../settlement/macro.ftl" as setttlements/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<div id="manage-clinic-form" data-action="<@spring.url '/api/clinic/save'/>"
     data-success-url="<@spring.url '/clinic/list'/>"
     data-cancel-url="<@spring.url '/clinic/list'/>">
    <input type="hidden" name="id" id="clinic_id" value="${(clinicForm.id?string['0'])!''}"/>
    <div id="data" class="form-error hidden"></div>
    <table class="wide-form">
        <tr>
            <#-- clinicName, clinicType, clinic Description, services -->
            <td colspan="2" class="-subform">
                <table>
                    <caption><@spring.message "label.clinic.form"/></caption>
                    <tr>
                        <td class="form-label"><label for="clinic_name"><@spring.message "label.clinic.name"/></label></td>
                        <td class="form-field">
                            <#if editable>
                                <div id="data.clinicName" class="form-error hidden"></div>
                                <input id="clinic_name"
                                       name="clinic_name"
                                       value="${(clinicForm.clinicName!'')?html}" size="40" maxlength="80"/>
                            <#else>
                                <span id="clinic_name">${(clinicForm.clinicName!'')?html}</span>
                            </#if>
                        </td>
                        <td class="form-label"><label for="clinic_type"><@spring.message "label.clinic.type"/></label></td>
                        <td class="form-field">
                            <#if editable>
                                <div id="data.clinicType" class="form-error hidden"></div>
                                <select id="clinic_type" name="clinic_type">
                                    <#list clinicTypeOptions as clinicTypeValue, clinicTypeText>
                                        <#if clinicForm.clinicType?? && clinicForm.clinicType.name() == clinicTypeValue>
                                            <option value="${clinicTypeValue?html}" selected>${clinicTypeText?html}</option>
                                        <#else>
                                            <option value="${clinicTypeValue?html}">${clinicTypeText?html}</option>
                                        </#if>
                                    </#list>
                                </select>
                            <#else>
                                <span id="clinic_type"><#if clinicForm.clinicType??><@spring.message clinicForm.clinicType.messageKey/><#else>-----</#if></span>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="clinic_description"><@spring.message "label.clinic.description"/></label></td>
                        <td class="form-field">
                            <#if editable>
                                <div id="data.description"></div>
                            </#if>
                            <textarea id="clinic_description" name="clinic_description" <#if !editable>readonly</#if> rows="5" cols="40">${(clinicForm.description!'')?html}</textarea>
                        </td>
                        <td class="form-label"><label for="services"><@spring.message "label.clinic.services"/></label></td>
                        <td class="form-field">
                            <#if editable>
                                <#assign checkBoxAttributes=""/>
                            <#else>
                                <#assign checkBoxAttributes="disabled readonly"/>
                            </#if>
                            <div class="-flex-table -max-width-50em">
                                <div class="-flex-cell-3 -min-width-15em -max-width-15em">
                                    <#assign optionDelimiter>
                                </div>
                                <div class="-flex-cell-3 -min-width-15em -max-width-15em">
                                    </#assign>
                                    <@spring.formCheckboxes "clinicForm.services" medicalServiceOptions optionDelimiter checkBoxAttributes/>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="form-field -subform">
                <#-- Contact -->
                <@contacts.contactForm
                formObject=clinicForm.contact
                idPrefix="clinic_contact_"
                errPathPrefix=".contact"
                editable=editable
                searchable=true
                regionOptions=regionOptions
                districtOptions=districtOptions
                settlementKindOptions=settlementKindOptions
                streetKindOptions=streetKindOptions/>
            </td>
        </tr>
        <tr>
            <td class="form-button-pane -subform" colspan="2">
                <div class="button-pane">
                    <#if editable>
                        <span class="-button -submit" data-type="${(submitType!'CREATE')?html}"><@spring.message "button.caption.submit"/></span>
                        <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                        <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                    <#else>
                        <#assign closeUrl><@spring.url "/clinic/list"/></#assign>
                        <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                        <#if staff>
                            <#assign editActionUrl><@spring.url "/clinic/manage/${clinicForm.id?string['0']}/edit"/></#assign>
                            <span class="-button -edit" data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                        </#if>
                    </#if>
                </div>
            </td>
        </tr>
    </table>
</div>
<#if editable>
    <@commons.entityLookupPopup containerId="lookupPopupContainer" templateId="popupLookupTemplate"/>
    <@commons.optionsTemplate templateId="optionsTemplate"/>
    <@contacts.phoneFormTemplate templateId="phoneFormTemplate" idPrefix="clinic_contact_" errPathPrefix=".clinic.contact"/>
<#-- mailFormTemplate -->
    <@contacts.mailFormTemplate templateId="mailFormTemplate" idPrefix="clinic_contact_" errPathPrefix=".clinic.contact"/>
</#if>
