<#-- @ftlvariable name="statics" type="freemarker.template.TemplateHashModel" -->
<#-- @ftlvariable name="lawCaseOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="regionDistrictOptions" type="ua.southpost.resource.backup.web.model.page.RegionDistrictOptions" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="lawyerAgencyForm" type="ua.southpost.resource.backup.web.model.forms.entity.LawyerAgencyForm" -->
<#import "/spring.ftl" as spring/>
<#import "../contact/macro.ftl" as contacts/>
<#import "../settlement/macro.ftl" as setttlements/>
<#import "../location/macro.ftl" as locations/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<div id="manage-lawyer-form" data-action="<@spring.url '/api/lawyer/save'/>"
     data-success-url="<@spring.url '/lawyer/list'/>"
     data-cancel-url="<@spring.url '/lawyer/list'/>">
    <input type="hidden" name="id" id="lawyer_id" value="${(lawyerAgencyForm.id?string['0'])!''}"/>
    <div id="data" class="form-error hidden"></div>
    <table class="wide-form">
        <tr>
            <td class="form-field">
                <table>
                    <tr>
                        <td class="form-field -subform">
                            <#if editable>
                                <div id="data.lawyer.settlement" class="form-error hidden"></div>
                            </#if>
                            <table>
                                <caption><@spring.message "label.settlement"/></caption>
                                <@setttlements.settlementFields
                                errPathPrefix=".settlement"
                                idPrefix="lawyer_settlement_"
                                settlementForm=lawyerAgencyForm.settlementForm
                                regionOptions=regionOptions
                                districtOptions=districtOptions
                                settlementKindOptions=settlementKindOptions
                                editable=editable
                                searchable=true/>
                            </table>
                        </td>
                        <td class="form-field -subform">
                            <table>
                                <tr>
                                    <#-- agencyName -->
                                    <td class="form-label">
                                        <label for="agency_name"><@spring.message "label.lawyer.agency.name"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.agencyName" class="form-error hidden"></div>
                                            <input id="agency_name"
                                                   name="agency_name"
                                                   value="${(lawyerAgencyForm.agencyName!'')?html}" size="40" maxlength="80"/>
                                        <#else>
                                            <span id="agency_name">${(lawyerAgencyForm.agencyName!'')?html}</span>
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <#-- webSite -->
                                    <td class="form-label">
                                        <label for="web_site"><@spring.message "label.lawyer.agency.web.site"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.webSite" class="form-error hidden"></div>
                                            <input id="web_site"
                                                   name="web_site"
                                                   value="${(lawyerAgencyForm.webSite!'')?html}" size="40" maxlength="80"/>
                                        <#else>
                                            <span id="web_site">${(lawyerAgencyForm.webSite!'')?html}</span>
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="form-field"><label for="cases"><@spring.message "label.lawyer.agency.cases"/></label></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="form-field">
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
                                                <@spring.formCheckboxes "lawyerAgencyForm.cases" lawCaseOptions optionDelimiter checkBoxAttributes/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <!-- contact -->
            <td class="form-field -subform">
                <#-- Contact -->
                <#assign contactDistrictOptions = regionDistrictOptions.districtOptions(lawyerAgencyForm.contact.regionId())/>
                <@contacts.contactForm
                formObject=lawyerAgencyForm.contact
                idPrefix="lawyer_contact_"
                errPathPrefix=".contact"
                editable=editable
                searchable=true
                regionOptions=regionOptions
                districtOptions=contactDistrictOptions
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
                        <#assign closeUrl><@spring.url "/lawyer/list"/></#assign>
                        <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                        <#if staff>
                            <#assign editActionUrl><@spring.url "/lawyer/manage/${lawyerAgencyForm.id?string['0']}/edit"/></#assign>
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
    <@contacts.phoneFormTemplate templateId="phoneFormTemplate" idPrefix="lawyer_contact_" errPathPrefix=".clinic.contact"/>
<#-- mailFormTemplate -->
    <@contacts.mailFormTemplate templateId="mailFormTemplate" idPrefix="lawyer_contact_" errPathPrefix=".clinic.contact"/>
</#if>

