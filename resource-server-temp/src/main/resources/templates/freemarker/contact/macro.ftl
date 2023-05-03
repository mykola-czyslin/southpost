<#-- @ftlvariable name="statics" type="freemarker.template.TemplateHashModel" -->
<#-- @ftlvariable name="formObject" type="ua.southpost.resource.backup.web.model.forms.entity.ContactForm" -->
<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#import "../location/macro.ftl" as locations/>
<#macro contactForm formObject idPrefix errPathPrefix editable searchable regionOptions districtOptions settlementKindOptions streetKindOptions>
<#-- @ftlvariable name="formObject" type="ua.southpost.resource.backup.web.model.forms.entity.ContactForm" -->
<table id="${idPrefix}form">
    <caption><@spring.message "label.contact"/></caption>
    <#if editable>
        <#if editable && errPathPrefix != "">
            <tr>
                <td class="form-field" colspan="2">
                    <div id="data${errPathPrefix}" class="form-error hidden"></div>
                </td>
            </tr>
        </#if>
        <tr>
            <td class="form-field" colspan="2">
                <div id="data${errPathPrefix}.name" class="form-error hidden"></div>
            </td>
        </tr>
    </#if>
    <#assign addLocationContainer>-add-location-container</#assign>
    <#assign locationForm = formObject.location/>
    <tr>
        <td class="form-field -subform" colspan="2">
            <table>
                <tbody>
                    <@locations.locationFormFields
                        locationForm=locationForm
                        errPathPrefix="${errPathPrefix}.location"
                        idPrefix="${idPrefix}location_"
                        editable=editable
                        searchable=true
                        regionOptions=regionOptions
                        districtOptions=districtOptions
                        settlementKindOptions=settlementKindOptions
                        streetKindOptions=streetKindOptions/>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td class="form-field -subform" colspan="2">
            <div id="data${errPathPrefix}.phones" class="form-error hidden"></div>
            <table class="-contact-phones">
                <caption><@spring.message "label.contact.phones"/></caption>
                <tr>
                    <td>
                        <table>
                            <tbody class="phone-list">
                            <#if formObject.phones?? && formObject.phones?size gt 0>
                                <#list  formObject.phones as phoneForm>
                                    <#assign phoneIndex = phoneForm?index?string['0']/>
                                    <tr class="phone-form-container -subform-item">
                                        <td class="phone-form-container">
                                            <#if editable>
                                                <div id="data.phones[${phoneIndex}]" class="form-error hidden"></div>
                                                <input type="hidden" id="${idPrefix}phones_${phoneIndex}_id" name="${idPrefix}phones[${phoneIndex}].id"
                                                       value="${(phoneForm.id?string['0'])!''}" class="phone-id"/>
                                            </#if>
                                            <table>
                                                <#if editable>
                                                    <tr>
                                                        <td class="form-field" colspan="2">
                                                            <div id="data${errPathPrefix}.phones[${phoneIndex}].phoneNumber" class="form-error hidden"></div>
                                                        </td>
                                                        <td class="form-label" colspan="4">&nbsp;</td>
                                                    </tr>
                                                </#if>
                                                <tr>
                                                    <td class="form-label">
                                                        <label for="${idPrefix}phones_${phoneIndex}_phoneNumber"><@spring.message "label.phone.number"/></label>
                                                    </td>
                                                    <td class="form-field">
                                                        <#assign phoneNumField>${idPrefix}phones[${phoneIndex}].phoneNumber</#assign>
                                                        <div>
                                                            <#if editable>
                                                                <input id="${idPrefix}phones_${phoneIndex}_phoneNumber" type="tel" placeholder="+380XXXXXXXXX"
                                                                       name="${phoneNumField}" value="${((phoneForm.phoneNumber)!'')?html}"
                                                                       pattern="${statics['net.chyslin.ww.web.model.forms.entity.PhoneForm'].VALID_PHONE_NUMBER_PATTERN}"
                                                                       class="phone-num" />
                                                            <#else>
                                                                <span id="${idPrefix}phones_${phoneIndex}_phoneNumber">${((phoneForm.phoneNumber)!'')?html}</span>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                    <td class="form-label">
                                                        <label for="${idPrefix}phones_${phoneIndex}_description"><@spring.message "label.phone.description"/></label>
                                                    </td>
                                                    <td class="form-field">
                                                        <div>
                                                            <#assign phoneDescField>${idPrefix}phones[${phoneIndex}].description</#assign>
                                                            <#if editable>
                                                                <input id="${idPrefix}phones_${phoneIndex}_description" type="text"
                                                                       name="${phoneDescField}"
                                                                       value="${((phoneForm.description)!'')?html}"
                                                                       size="30"
                                                                       class="phone-desc" />
                                                                <span class="-button -phone-lookup -tooltip-target" data-url="<@spring.url '/api/contact/phone/lookup'/>" title="<@spring.message 'tooltip.phone.lookup'/>">
                                                                    <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/>
                                                                </span>
                                                            <#else>
                                                                <span id="${idPrefix}phones_${phoneIndex}_description">${((phoneForm.description)!'')?html}</span>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                    <#if editable>
                                                        <td>
                                                <span class="-clickable -action-remove-phone"
                                                      data-index="${phoneIndex}"><img src="${staticMediaUrl}/img/delete.png" width="12" height="12" alt="delete"/></span>
                                                        </td>
                                                        <td>
                                                            <div class="-row -clickable -move-up">
                                                                <img src="${staticMediaUrl}/img/prev.png" width="12" height="6" alt="prev"/>
                                                            </div>
                                                            <div class="-row -clickable -move-down">
                                                                <img src="${staticMediaUrl}/img/next.png" width="12" height="6" alt="next"/>
                                                            </div>
                                                        </td>
                                                    </#if>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </td>
                    <td class="-add-list-entry-cell">
                        <#if editable>
                            <span class="-button -action-add-phone"><img src="${staticMediaUrl}/img/add.png" width="12" height="12" alt="add"/></span>
                        </#if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="form-field -subform" colspan="2">
            <div id="data${errPathPrefix}.emails" class="form-error hidden"></div>
            <table class="-contact-emails">
                <caption><@spring.message "label.contact.emails"/></caption>
                <tr>
                    <td>
                        <table>
                            <tbody class="mail-list">
                            <#if formObject.emails?? && formObject.emails?size gt 0>
                                <#list formObject.emails as emailForm>
                                    <#assign mailIndex = emailForm?index?string['0']/>
                                    <tr class="email-form-container -subform-item">
                                        <td class="email-form-container form-field">
                                            <#if editable>
                                                <div id="data${errPathPrefix}.emails[${mailIndex}]" class="form-error hidden"></div>
                                                <input id="${idPrefix}emails[${mailIndex}]_id" type="hidden" name="${idPrefix}emails[${mailIndex}].id" value="${(emailForm.id?string['0'])!''}"
                                                       class="email-id"/>
                                            </#if>
                                            <#assign formId>${idPrefix}emails[${mailIndex}]</#assign>
                                            <table>
                                                <#if editable>
                                                    <tr>
                                                        <td class="form-field" colspan="2">
                                                            <div id="data${errPathPrefix}.emails[${mailIndex}].emailAddress" class="form-error hidden"></div>
                                                        </td>
                                                        <td class="form-field" colspan="4">&nbsp;</td>
                                                    </tr>
                                                </#if>
                                                <tr>
                                                    <#assign fieldId>${formId}_emailAddress</#assign>
                                                    <td class="form-label"><label for="${fieldId}"><@spring.message "label.mail.address"/></label></td>
                                                    <td class="form-field">
                                                        <div>
                                                            <#assign emailAddressField>${idPrefix}emails[${mailIndex}].emailAddress</#assign>
                                                            <#if editable>
                                                                <input id="${fieldId}" type="email" name="${emailAddressField}"
                                                                       value="${((emailForm.emailAddress)!'')?html}" class="email-address"/>
                                                            <#else>
                                                                <span id="${fieldId}">${((emailForm.emailAddress)!'')?html}</span>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                    <#assign fieldId>${formId}_description</#assign>
                                                    <td class="form-label"><label for="${fieldId}"><@spring.message "label.mail.description"/></label></td>
                                                    <td class="form-field">
                                                        <div>
                                                            <#assign emailDescriptionField>${idPrefix}emails[${mailIndex}].description</#assign>
                                                            <#if editable>
                                                                <input id="${fieldId}" type="text" name="${emailDescriptionField}"
                                                                       value="${((emailForm.description)!'')?html}" class="email-desc"/>
                                                                <span class="-button -email-lookup -tooltip-target" data-url="<@spring.url '/api/contact/email/lookup'/>" title="<@spring.message 'tooltip.email.lookup'/>">
                                                                    <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/>
                                                                </span>
                                                            <#else>
                                                                <span id="${fieldId}">${((emailForm.description)!'')?html}</span>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                    <#if editable>
                                                        <td>
                                                <span class="-clickable -action-remove-email"
                                                      data-index="${mailIndex}"><img src="${staticMediaUrl}/img/delete.png" width="12" height="12" alt="delete"/></span>
                                                        </td>
                                                        <td>
                                                            <div class="-row -clickable -move-up">
                                                                <img src="${staticMediaUrl}/img/prev.png" width="12" height="6" alt="prev"/>
                                                            </div>
                                                            <div class="-row -clickable -move-down">
                                                                <img src="${staticMediaUrl}/img/next.png" width="12" height="6" alt="next"/>
                                                            </div>
                                                        </td>
                                                    </#if>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </td>
                    <td  class="-add-list-entry-cell">
                       <#if editable>
                           <span class="-button -action-add-email"><img src="${staticMediaUrl}/img/add.png" width="12" height="12" alt="add"/></span>
                       </#if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</#macro>
<#macro phoneFormTemplate templateId idPrefix errPathPrefix>
<script id="${templateId}" type="text/x-jQuery-tmpl">
    <tr class="phone-form-container -subform-item">
        <td class="phone-form-container">
            <div id="data${errPathPrefix}.phones[${r'${index}'}]" class="form-error hidden"></div>
            <input type="hidden" id="${idPrefix}phones_${r'${index}'}_id" name="${idPrefix}phones[${r'${index}'}].id"
                   value="${r'${id}'}" class="phone-id"/>
            <table>
                <tr>
                    <td class="form-field" colspan="2">
                        <div id="data${errPathPrefix}.phones[${r'${index}'}].phoneNumber" class="form-error hidden"></div>
                    </td>
                    <td class="form-field" colspan="4">&nbsp;</td>
                </tr>
                <tr>
                    <td class="form-label">
                        <label for="${idPrefix}phones_${r'${index}'}_phoneNumber"><@spring.message "label.phone.number"/></label>
                    </td>
                    <td class="form-field">
                        <div>
                            <input id="${idPrefix}phones_${r'${index}'}_phoneNumber" type="tel" placeholder="+380XXXXXXXXX"
                                   name="${idPrefix}phones[${r'${index}'}].phoneNumber"
                                   value="${r'${phone_number}'}"
                                   pattern="${statics['net.chyslin.ww.web.model.forms.entity.PhoneForm'].VALID_PHONE_NUMBER_PATTERN}"
                                   class="phone-num"/>
                        </div>
                    </td>
                    <td class="form-label">
                        <label for="${idPrefix}phones_${r'${index}'}_description"><@spring.message "label.phone.description"/></label>
                    </td>
                    <td class="form-field">
                        <div>
                            <input id="${idPrefix}phones_${r'${index}'}_description" type="text"
                                   name="${idPrefix}phones[${r'${index}'}].description"
                                   value="${r'${description}'}"
                                   size="30"
                                   class="phone-desc"/>
                                   <span class="-button -phone-lookup -tooltip-target" data-url="<@spring.url '/api/contact/phone/lookup'/>" title="<@spring.message 'tooltip.phone.lookup'/>">
                                        <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/>
                                   </span>
                        </div>
                    </td>
                    <td>
                    <span class="-clickable -action-remove-phone"
                          data-index="${r'${index}'}"><img src="${staticMediaUrl}/img/delete.png" width="12" height="12"/></span>
                    </td>
                    <td>
                        <div class="-row -clickable -move-up">
                            <img src="${staticMediaUrl}/img/prev.png" width="12" height="6"/>
                        </div>
                        <div class="-row -clickable -move-down">
                            <img src="${staticMediaUrl}/img/next.png" width="12" height="6"/>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</script>
</#macro>
<#macro mailFormTemplate templateId idPrefix errPathPrefix>
<script id="${templateId}" type="text/x-jQuery-tmpl">
    <tr class="email-form-container -subform-item">
        <td class="email-form-container form-field">
            <input id="${idPrefix}emails[${r'${index}'}]_id" type="hidden" name="${idPrefix}emails[${r'${index}'}].id"
            value="${r'${id}'}" class="email-id"/>
            <div id="data${errPathPrefix}.emails[${r'${index}'}]" class="form-error hidden"></div>
            <table>
                <tr>
                    <td class="form-field" colspan="2">
                        <div id="data${errPathPrefix}.emails[${r'${index}'}].emailAddress" class="form-error hidden"></div>
                    </td>
                    <td class="form-field" colspan="4">&nbsp;</td>
                </tr>
                <tr>
                    <td class="form-label"><label for="${idPrefix}emails[${r'${index}'}]_emailAddress"><@spring.message "label.mail.address"/></label></td>
                    <td class="form-field">
                        <div>
                            <input id="${idPrefix}emails[${r'${index}'}]_emailAddress" type="email" name="contact.emails[${r'${index}'}].emailAddress"
                                   value="${r'${email_address}'}" class="email-address"/>
                        </div>
                    </td>
                    <td class="form-label"><label for="${idPrefix}emails[${r'${index}'}]_description"><@spring.message "label.mail.description"/></label></td>
                    <td class="form-field">
                        <div>
                            <input id="${idPrefix}emails[${r'${index}'}]_description" type="text" name="contact.emails[${r'${index}'}].description"
                                   value="${r'${description}'}" class="email-desc"/>
                            <span class="-button -email-lookup -tooltip-target" data-url="<@spring.url '/api/contact/email/lookup'/>" title="<@spring.message 'tooltip.email.lookup'/>">
                                  <img src="${staticMediaUrl}/img/looking-glass.png" width="16" height="16" alt="lookup"/>
                            </span>
                        </div>
                    </td>
                    <td>
                        <span class="-clickable -action-remove-email"
                              data-index="${r'${index}'}"><img src="${staticMediaUrl}/img/delete.png" width="12" height="12"/></span>
                    </td>
                    <td>
                        <div class="-row -clickable -move-up">
                            <img src="${staticMediaUrl}/img/prev.png" width="12" height="6"/>
                        </div>
                        <div class="-row -clickable -move-down">
                            <img src="${staticMediaUrl}/img/next.png" width="12" height="6"/>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</script>
</#macro>

<#macro contactInfo contactFormObject regionOptions districtOptions>
<div class="-contact -id">
    <#if contactFormObject.location??>
        <@locations.locationInfo
            locationFormObject=contactFormObject.location
            regionOptions=regionOptions
            districtOptions=districtOptions/>
    </#if>
    <#if contactFormObject.phones??>
        <div class="-phone-list">
            <#list contactFormObject.phones as phone>
                <div class="-phone -id" data-value="${phone.id?string['0']}">
                    <div class="-phone-number" data-value="${phone.phoneNumber?html}">${phone.phoneNumber?html}</div>
                    <div class="-description" data-value="${phone.description?html}">${phone.description?html}</div>
                </div>
            </#list>
        </div>
    </#if>
    <#if contactFormObject.emails??>
        <div class="-email-list">
            <#list contactFormObject.emails as email>
                <div class="-email -id" data-value="${email.id?string['0']}">
                    <div class="-email-address" data-value="${email.emailAddress}">${email.emailAddress}</div>
                    <div class="-description" data-value="${email.description}">${email.description}</div>
                </div>
            </#list>
        </div>
    </#if>
</div>

</#macro>

<#macro contactInfoTemplate parentRef>
<div class="-contact -id" data-value="${r'${id}'}">
    <div class="-name" data-value="${r'${'}${parentRef}${r'name}'}">${r'${'}${parentRef}${r'name}'}</div>
    <div class="-description" data-value="${r'${'}${parentRef}${r'description}'}">${r'${'}${parentRef}${r'description}'}</div>
    {{if ${parentRef}location}}
        <@locations.locationInfoTemplate parentRef="${parentRef}location."/>
    {{/if}}
    {{if ${parentRef}phones}}
        <div class="-phone-list">
            {{each ${parentRef}phones}}
                <div class="-phone -id" data-value="${r'${$value.id}'}">
                    <div class="-phone-number" data-value="${r'${$value.phone_number}'}">${r'${$value.phone_number}'}</div>
                    <div class="-description" data-value="${r'${$value.description}'}">${r'${$value.description}'}</div>
                </div>
            {{/each}}
        </div>
    {{/if}}
    {{if ${parentRef}emails}}
        <div class="-email-list">
            {{each ${parentRef}emails}}
                <div class="-email -id" data-value="${r'${$value.id}'}">
                    <div class="-email-address" data-value="${r'${$value.email_address}'}">${r'${$value.email_address}'}</div>
                    <div class="-description" data-value="${r'${$value.description}'}">${r'${$value.description}'}</div>
                </div>
            {{/each}}
        </div>
    {{/if}}
</div>
</#macro>

<#macro flatEntityInfo contact>
<#-- @ftlvariable name="contact" type="ua.southpost.resource.backup.web.model.dto.contact.ContactInfo" -->
<#if contact.contactAddress??>, <@locations.flatEntityInfo location=contact.location/></#if>
<#if contact.phones??>, ${contact.getPhonesCSV()?html}</#if><#if contact.emails??>, ${contact.getEmailsCSV()?html}</#if>
</#macro>

<#macro phoneCSV phones>
<#-- @ftlvariable name="phones" type="java.util.List<ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo>" -->
    <#assign delim=''/>
    <#if phones??>
        <#assign csv=''/>
        <#list phones as phone>
            <#assign csv>${csv}${delim}${(phone.phoneNumber!'-')}</#assign>
            <#assign delim=", "/>
        </#list>
        ${csv?html}
    </#if>
</#macro>
<#macro emailCSV emails>
<#-- @ftlvariable name="emails" type="java.util.List<ua.southpost.resource.backup.web.model.dto.contact.EmailInfo>" -->
    <#assign delim=''/>
    <#if emails??>
        <#assign csv=''/>
        <#list emails as email>
            <#assign csv>${csv}${delim}${(email.emailAddress!'-')}</#assign>
            <#assign delim=", "/>
        </#list>
        ${csv?html}
    </#if>
</#macro>