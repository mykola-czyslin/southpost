<#-- @ftlvariable name="emailForm" type="ua.southpost.resource.backup.web.model.forms.entity.EmailAddressForm" -->
<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="staff" type="java.lang.Boolean" -->
<#import "/spring.ftl" as spring/>
<#assign editable = submitType.formEditable/>
<div id="manage-email-form"
     data-action="<@spring.url '/api/contact/email/save'/>"
     data-success-url="<@spring.url '/master/emails/list'/>"
     data-cancel-url="<@spring.url '/master/emails/list'/>">
    <div class="contact-detail-form-wrapper -layout-align-center">
        <div id="data" class="form-error hidden"></div>
        <table>
            <tbody>
                <tr>
                    <td class="-subform">
                        <#if editable>
                            <input type="hidden" id="id" name="id" value="${((emailForm.id?string['0'])!'')?html}"/>
                            <div id="data" class="form-error hidden"></div>
                        </#if>
                        <table>
                            <tbody>
                                <#if editable>
                                    <tr>
                                        <td colspan="2">
                                            <div id="data.emailAddress" class="form-error hidden"></div>
                                        </td>
                                    </tr>
                                </#if>
                                <tr>
                                    <td class="form-label">
                                        <label for="email_address"><@spring.message "label.email.address"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <input id="email_address" name="email_address" type="email" size="40" maxlength="80"
                                                   placeholder="name@domain.com"
                                                   value="${((emailForm.emailAddress)!'')?html}"/>
                                        <#else>
                                            <span id="email_address">${emailForm.emailAddress?html}</span>
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
                                        <label for="description"><@spring.message "label.email.description"/></label>
                                    </td>
                                    <td class="form-field">
                                        <#if editable>
                                            <textarea id="description" name="description" cols="40" rows="4">${(emailForm.description!'')?html}</textarea>
                                        <#else>
                                            <textarea id="description" name="description" cols="40" rows="4" readonly="readonly">${(emailForm.description!'')?html}</textarea>
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
                            <span class="-button -submit" data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                            <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                            <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                        <#else>
                            <#assign closeUrl><@spring.url "/master/emails/list"/></#assign>
                            <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                            <#if staff>
                                <#assign editActionUrl><@spring.url "/master/emails/${emailForm.id?string['0']}/edit"/></#assign>
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