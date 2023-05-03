<#-- @ftlvariable name="phoneForm" type="ua.southpost.resource.backup.web.model.forms.entity.PhoneForm" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#import "/spring.ftl" as spring/>
<#assign editable = submitType.formEditable/>
<div id="manage-phone-form"
     data-action="<@spring.url '/api/contact/phone/save'/>"
     data-success-url="<@spring.url '/master/phones/list'/>"
     data-cancel-url="<@spring.url '/master/phones/list'/>">
    <div class="contact-detail-form-wrapper -layout-align-center">
        <div id="data" class="form-error hidden"></div>
        <table>
            <tbody>
                <tr>
                    <td class="-subform">
                        <#if editable>
                            <input type="hidden" id="id" name="id" value="${((phoneForm.id?string['0'])!'')?html}"/>
                            <div id="data" class="form-error hidden"></div>
                        </#if>
                        <table>
                            <tbody>
                                <#if editable>
                                    <tr>
                                        <td colspan="2">
                                            <div id="data.phoneNumber" class="form-error hidden"></div>
                                        </td>
                                    </tr>
                                </#if>
                                <tr>
                                    <td class="form-label">
                                        <label for="phone_number"><@spring.message "label.phone.number"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <input id="phone_number" name="phone_number" type="text" size="20" maxlength="20"
                                                   pattern="${statics['net.chyslin.ww.web.model.forms.entity.PhoneForm'].VALID_PHONE_NUMBER_PATTERN}"
                                                   placeholder="+380XXXXXXXXX"
                                                   value="${((phoneForm.phoneNumber)!'')?html}"/>
                                        <#else>
                                            <span id="phone_number">${phoneForm.phoneNumber?html}</span>
                                        </#if>
                                    </td>
                                </tr>
                                <#if editable>
                                <tr>
                                    <td colspan="2">
                                        <div id="data.description" class="form-error hidden"></div>
                                    </td>
                                </tr>
                                </#if>
                                <tr>
                                    <td class="form-label">
                                        <label for="description"><@spring.message "label.phone.description"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <textarea id="description" name="description" cols="40" rows="4">${(phoneForm.description!'')?html}</textarea>
                                        <#else>
                                            <textarea id="description" name="description" cols="40" rows="4" readonly="readonly">${(phoneForm.description!'')?html}</textarea>
                                        </#if>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="form-field">
                        <div class="button-pane">
                        <#if editable>
                            <span class="-button -submit" data-type="${(submitType!'CREATE')?html}"><@spring.message "button.caption.submit"/></span>
                            <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                            <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                        <#else>
                            <#assign closeUrl><@spring.url "/master/phones/list"/></#assign>
                            <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                            <#if staff>
                                <#assign editActionUrl><@spring.url "/master/phones/${phoneForm.id?string['0']}/edit"/></#assign>
                                <span class="-button -edit" data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                            </#if>
                        </#if>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>