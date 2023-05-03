<#-- @ftlvariable name="statics" type="freemarker.template.TemplateHashModel" -->
<#-- @ftlvariable name="dwellingKindOptions"  type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="billingPeriodOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="streetKindOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionDistrictOptions" type="ua.southpost.resource.backup.web.model.page.RegionDistrictOptions" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="dwellingForm" type="ua.southpost.resource.backup.web.model.forms.entity.DwellingForm" -->
<#import "/spring.ftl" as spring/>
<#import "../contact/macro.ftl" as contacts/>
<#import "../settlement/macro.ftl" as setttlements/>
<#import "../location/macro.ftl" as locations/>
<#import "../common/macro.ftl" as commons/>
<#assign editable = submitType.formEditable/>
<div id="manage-dwelling-form" data-action="<@spring.url '/api/dwelling/save'/>"
     data-success-url="<@spring.url '/dwelling/list'/>"
     data-cancel-url="<@spring.url '/dwelling/list'/>">
    <input type="hidden" name="id" id="dwelling_id" value="${(dwellingForm.id?string['0'])!''}"/>
    <div id="data" class="form-error hidden"></div>
    <table class="wide-form">
        <tr>
            <td class="-subform">
                <table>
                    <tr>
                        <td class="form-field -subform">
                            <#if editable>
                                <div id="data.dwelling.settlement" class="form-error hidden"></div>
                            </#if>
                            <table>
                                <caption><@spring.message "label.settlement"/></caption>
                                <@setttlements.settlementFields
                                errPathPrefix=".settlement"
                                idPrefix="dwelling_settlement_"
                                settlementForm=dwellingForm.settlement
                                regionOptions=regionOptions
                                districtOptions=districtOptions
                                settlementKindOptions=settlementKindOptions
                                editable=editable
                                searchable=true/>
                            </table>
                        </td>
                        <td class="-subform">
                            <#-- dwelling information -->
                            <table>
                                <caption><@spring.message "label.dwelling.form"/></caption>
                                <tr>
                                    <td class="form-label"><label
                                                for="settlement_area"><@spring.message "label.settlement.area"/></label></td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.settlementArea" class="form-error hidden"></div>
                                            <input id="settlement_area"
                                                   name="settlement_area"
                                                   value="${(dwellingForm.settlementArea!'')?html}"/>
                                        <#else>
                                            <span id="settlement_area">${(dwellingForm.settlementArea!'')?html}</span>
                                        </#if>
                                    </td>
                                    <td class="form-label"><label
                                                for="dwelling_kind"><@spring.message "label.dwelling.kind"/></label></td>
                                    <td class="form-field">
                                        <#if editable>
                                            <select id="dwelling_kind" name="dwelling_kind">
                                                <#list dwellingKindOptions as kindValue, kindText>
                                                    <#if dwellingForm.dwellingKind?? && dwellingForm.dwellingKind.name() == kindValue>
                                                        <option value="${kindValue?html}" selected>${kindText?html}</option>
                                                    <#else>
                                                        <option value="${kindValue?html}">${kindText?html}</option>
                                                    </#if>
                                                </#list>
                                            </select>
                                        <#else>
                                            <span id="dwelling_kind"><#if dwellingForm.dwellingKind??><@spring.message dwellingForm.dwellingKind.messageKey/><#else>-----</#if></span>
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="form-label">
                                        <label for="number_of_rooms"><@spring.message "label.number.of.rooms"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <#assign tooltipText><@spring.message "tooltip.number.of.rooms"/></#assign>
                                            <input id="number_of_rooms" name="number_of_rooms" type="number" min="0" max="10"
                                                   value="${(dwellingForm.numberOfRooms!0)?string['0']}"
                                                   class="-tooltip-target"
                                                   title="${tooltipText?html}"/>
                                        <#else>
                                            <span id="number_of_rooms">${(dwellingForm.numberOfRooms!0)?string['0']}</span>
                                        </#if>
                                    </td>
                                    <td class="form-label">
                                        <label for="total_area"><@spring.message "label.total.area"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.totalArea" class="form-error hidden"></div>
                                            <#assign tooltipText><@spring.message "tooltip.total.area"/></#assign>
                                            <input id="total_area" name="total_area" type="number"
                                                   value="${(dwellingForm.totalArea!0.00)?string['0.##']}"
                                                   title="${tooltipText?html}"
                                                   class="-tooltip-target"/>
                                        <#else>
                                            <span id="total_area">${(dwellingForm.totalArea!0.00)?string['0.##']}</span>
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="form-label">
                                        <label for="living_area"><@spring.message "label.living.area"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.livingArea" class="form-error hidden"></div>
                                            <#assign tooltipText><@spring.message "tooltip.living.area"/></#assign>
                                            <input id="living_area" name="living_area" type="number"
                                                   value="${(dwellingForm.livingArea!0.00)?string['0.##']}"
                                                   title="${tooltipText?html}"
                                                   class="-tooltip-target"/>
                                        <#else>
                                            <span id="living_area">${(dwellingForm.livingArea!0.0)?string['0.##']}</span>
                                        </#if>
                                    </td>
                                    <td class="form-label">
                                        <label for="price"><@spring.message "label.price"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <div id="data.price" class="form-error hidden"></div>
                                            <#assign tooltipText><@spring.message "tooltip.dwelling.price"/></#assign>
                                            <input id="price" name="price" value="${(dwellingForm.price!0.0)?string['0.00']}"
                                                   class="-tooltip-target"
                                                   title="${tooltipText?html}"/>
                                        <#else>
                                            <span id="price">${(dwellingForm.price!0.0)?string.currency}</span>
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="form-label"><label
                                                for="billing_period"><@spring.message "label.billing.period"/></label></td>
                                    <td class="form-field">
                                        <div id="data.billingPerion" class="form-error hidden"></div>
                                        <#if editable>
                                            <select id="billing_period" name="billing_period">
                                                <#list billingPeriodOptions as billingPeriodValue, billingPeriodText>
                                                    <#if dwellingForm.billingPeriod?? && dwellingForm.billingPeriod.name() == billingPeriodValue>
                                                        <option value="${billingPeriodValue?html}"
                                                                selected>${billingPeriodText?html}</option>
                                                    <#else>
                                                        <option value="${billingPeriodValue?html}">${billingPeriodText?html}</option>
                                                    </#if>
                                                </#list>
                                            </select>
                                        <#else>
                                            <span id="billing_period"><#if dwellingForm.billingPeriod??><@spring.message dwellingForm.billingPeriod.messageKey/><#else>-----</#if></span>
                                        </#if>
                                    </td>
                                    <td class="form-label"><label
                                                for="dwelling_description"><@spring.message "label.dwelling.description"/></label>
                                    </td>
                                    <td class="form-field">
                            <textarea id="dwelling_description" cols="60" rows="5"
                                      <#if !editable>readonly</#if>>${(dwellingForm.description!'')?html}</textarea>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="form-field -subform">
                <#-- Contact -->
                <#assign contactDistrictOptions = regionDistrictOptions.districtOptions(dwellingForm.contact.regionId())/>
                <@contacts.contactForm
                formObject=dwellingForm.contact
                idPrefix="dwelling_contact_"
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
            <td class="form-button-pane -subform">
                <div class="button-pane">
                    <#if editable>
                        <span class="-button -submit"
                              data-type="${(submitType!'CREATE')?html}"><@spring.message "button.caption.submit"/></span>
                        <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                        <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                    <#else>
                        <#assign closeUrl><@spring.url "/dwelling/list"/></#assign>
                        <span class="-button -close"
                              data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                        <#if staff>
                            <#assign editActionUrl><@spring.url "/dwelling/manage/${dwellingForm.id?string['0']}/edit"/></#assign>
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
    <@contacts.phoneFormTemplate templateId="phoneFormTemplate" idPrefix="dwelling_contact_" errPathPrefix=".dwelling.contact"/>
<#-- mailFormTemplate -->
    <@contacts.mailFormTemplate templateId="mailFormTemplate" idPrefix="dwelling_contact_" errPathPrefix=".dwelling.contact"/>
</#if>

