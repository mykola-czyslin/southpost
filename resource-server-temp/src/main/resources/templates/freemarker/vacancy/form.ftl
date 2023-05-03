<#-- @ftlvariable name="moneyPattern" type="java.lang.String" -->
<#-- @ftlvariable name="statics" type="freemarker.template.TemplateHashModel" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="contactDistrictOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="regionDistrictOptions" type="ua.southpost.resource.backup.web.model.page.RegionDistrictOptions" -->
<#-- @ftlvariable name="vacancyForm" type="ua.southpost.resource.backup.web.model.forms.entity.VacancyForm" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#import "/spring.ftl" as spring/>
<#import "../contact/macro.ftl" as contacts/>
<#import "../employer/macro.ftl" as employers/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<#assign moneyTooltip><@spring.message "tooltip.monetary.value"/></#assign>
<div id="manage-vacancy-form" data-action="<@spring.url '/api/vacancy/save'/>"
     data-success-url="<@spring.url '/vacancy/list'/>"
     data-cancel-url="<@spring.url '/vacancy/list'/>">
    <input type="hidden" name="id" value="${(vacancyForm.id?string['0'])!''}" id="vacancy_id"/>
    <div id="data" class="form-error hidden"></div>
    <table class="wide-form">
        <tr>
            <td class="form-label -subform">
                <table>
                    <caption><@spring.message "label.vacancy"/></caption>
                <#if editable>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div id="data.summary" class="form-error hidden"></div>
                        </td>
                        <td class="form-field" colspan="2">
                            <div id="data.description" class="form-error hidden"></div>
                        </td>
                    </tr>
                </#if>
                    <tr>
                        <td class="form-label">
                            <label for="vacancy_summary"><@spring.message "label.vacancy.summary"/></label>
                        </td>
                        <td class="form-field">
                            <div>
                            <#if editable>
                                <input id="vacancy_summary" type="text"
                                       name="summary"
                                       value="${((vacancyForm.summary)!'')?html}" size="40" maxlength="80"/>
                            <#else>
                                <span id="vacancy_summary">${((vacancyForm.summary)!'')?html}</span>
                            </#if>
                            </div>
                        </td>
                        <td class="form-label" rowspan="2">
                            <label for="vacancy_description"><@spring.message "label.vacancy.description"/></label>
                        </td>
                        <td class="form-field" rowspan="3">
                            <div>
                                <textarea id="vacancy_description" name="description" cols="40" rows="6"<#if !editable>
                                          readonly="readonly"</#if>>${((vacancyForm.description)!'')?html}</textarea>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label">
                            <label for="salaryLowField"><@spring.message "label.vacancy.salary.low"/></label>
                        </td>
                        <td class="form-field">
                            <div>
                            <#if editable>
                                <input id="salaryLowField"
                                       type="text"
                                       name="salaryLow"
                                       value="${((vacancyForm.salaryLow?string['0.00'])!'')?html}"
                                       pattern="${moneyPattern}"
                                       size="14"/>
                            <#else>
                                <span id="salaryLowField">${((vacancyForm.salaryLow?string.currency)!'')?html}</span>
                            </#if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label">
                            <label for="salaryHighField"><@spring.message "label.vacancy.salary.high"/></label>
                        </td>
                        <td class="form-field">
                            <div>
                            <#if editable>
                                <input id="salaryHighField" type="text"
                                       name="salaryHigh"
                                       value="${((vacancyForm.salaryHigh?string['0.00'])!'')?html}"
                                       pattern="${moneyPattern}"
                                       size="14"/>
                            <#else>
                                <span id="salaryHighField">${((vacancyForm.salaryHigh?string.currency)!'')?html}</span>
                            </#if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="form-field">
                            <div>
                                <input id="hosting" type="checkbox"
                                       name="hosting" ${vacancyForm.hosting?string("checked", "")}<#if !editable>
                                       readonly="readonly"</#if>/>
                                <label for="hosting"><@spring.message "label.vacancy.hosting"/></label>
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="form-field -subform">
            <#assign employerForm = vacancyForm.employer/>
                <div class="employer-form-container">
                    <div class="-layout-align-center -toggleable">
                        <table>
                            <tr>
                                <td class="-subform">
                                <@employers.employerForm
                                    formObject=employerForm
                                idPrefix="vacancy_employer_"
                                errPathPrefix=".employer"
                                regionOptions=regionOptions
                                regionDistrictOptions=regionDistrictOptions
                                settlementKindOptions=settlementKindOptions
                                streetKindOptions=streetKindOptions
                                editable=editable
                                searchable=true/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td class="form-button-pane -subform">
                <div class="button-pane">
                <#if editable>
                    <span class="-button -submit -vacancy"
                          data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                    <span class="-button -reset -vacancy"><@spring.message "button.caption.reset"/></span>
                    <span class="-button -cancel -vacancy"><@spring.message "button.caption.cancel"/></span>
                <#else>
                    <#assign closeUrl><@spring.url "/vacancy/list"/></#assign>
                    <span class="-button -close"
                          data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                    <#if staff>
                        <#assign editActionUrl><@spring.url "/vacancy/manage/${vacancyForm.id?string['0']}/edit"/></#assign>
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
    <@commons.entityLookupPopup containerId="lookupPopupContainer" templateId="popupLookupTemplate"/>
    <@commons.optionsTemplate templateId="optionsTemplate"/>
    <@contacts.phoneFormTemplate templateId="phoneFormTemplate" idPrefix="vacancy_contact_" errPathPrefix=".vacancy.contact"/>
    <@contacts.mailFormTemplate templateId="mailFormTemplate" idPrefix="vacancy_contact_" errPathPrefix=".vacancy.contact"/>
</#if>
