<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="registerForm" type="ua.southpost.resource.backup.web.model.forms.RegisterUserForm" -->
<#-- @ftlvariable name="userKindOptions" type="java.util.Map<String,String>" -->
<#import "/spring.ftl" as spring/>
<div>
    <div>
        <div class="register-user-form-wrapper -layout-align-center">
            <div class="-form-frame">
                <@spring.bind "registerForm"/>
                <#include "../includes/form-errors.ftl"/>
                <form id="register-user" method="POST" action="<@spring.url '/user/authentication/register'/>">
                    <table id="input-layout-container" class="register-user-form">
                        <tr>
                            <td class="form-label"><label
                                        for="user.firstName"><@spring.message "label.user.firstName"/></label></td>
                            <td class="form-field">
                                <@spring.bind "registerForm.firstName"/>
                                <input id="user.firstName" type="text" name="${spring.status.expression}"
                                       value="${((spring.status.value)!'')?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="user.lastName"><@spring.message "label.user.lastName"/></label></td>
                            <td class="form-field">
                                <@spring.bind "registerForm.lastName"/>
                                <input id="user.lastName" type="text" name="${spring.status.expression}"
                                       value="${((spring.status.value)!'')?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label"><label for="user.login"><@spring.message "label.user.login"/></label>
                            </td>
                            <td class="form-field">
                                <@spring.bind "registerForm.login"/>
                                <div>
                                    <input id="user.login" type="text" name="${spring.status.expression}"
                                           value="${((spring.status.value)!'')?html}"/>
                                </div>
                                <#include "../includes/field-errors.ftl"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="user.email"><@spring.message "label.user.e-mail"/></label></td>
                            <td class="form-field">
                                <div>
                                    <@spring.bind "registerForm.email"/>
                                    <input id="user.email" type="email" name="${spring.status.expression}"
                                           value="${((spring.status.value)!'')?html}">
                                </div>
                                <#include "../includes/field-errors.ftl"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="user.password"><@spring.message "label.user.password"/></label></td>
                            <td class="form-field">
                                <div>
                                    <@spring.bind "registerForm.password"/>
                                    <input id="user.password" type="password" name="${spring.status.expression}"
                                           value="${((spring.status.value)!'')?html}">
                                </div>
                                <#include "../includes/field-errors.ftl"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="password.retype"><@spring.message "label.user.password.retype"/></label>
                            </td>
                            <@spring.bind "registerForm.passwordRetype"/>
                            <td class="form-field">
                                <div>
                                    <input id="password.retype" type="password" name="${spring.status.expression}"
                                           value="${((spring.status.value)!'')?html}">
                                </div>
                                <#include "../includes/field-errors.ftl"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-field" colspan="2">
                                <div class="box-header">
                                    <#assign kindsTooltip><@spring.message "tooltip.your.activity.kinds"/></#assign>
                                    <label for="userActivityKinds" class="-tooltip-target" title="${(kindsTooltip!'')?html}"><@spring.message "label.user.activities"/></label>
                                </div>
                                <div class="-flex-table -min-width-40em -max-width-40em">
                                    <div class="-flex-cell-2 -min-width-18em -min-width-18em">
                                        <#assign checkBoxesSeparator>
                                    </div>
                                    <div class="-flex-cell-2 -min-width-18em -min-width-18em">
                                        </#assign>
                                        <@spring.formCheckboxes
                                        path="registerForm.userActivityKinds"
                                        options=userKindOptions
                                        separator=checkBoxesSeparator/>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-button-pane" colspan="2">
                                <div class="button-pane">
                                    <span class="-button -register-user"><@spring.message "button.caption.register"/></span>
                                    <span class="-button -form-reset"><@spring.message "button.caption.reset"/></span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>
