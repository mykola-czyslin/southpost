<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>
<div>
    <div class="user-form-wrapper -layout-align-center">
        <div class="-form-frame">
        <#assign formAction = "/user/profile/change-password" />
            <form id="change-password" action="<@spring.url formAction/>" method="POST">
            <@spring.bind "editUserForm"/>
            <#include "../includes/form-errors.ftl"/>
            <@spring.bind "editUserForm.userId"/>
                <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                <table class="user-form">
                    <tr>
                        <td class="form-label"><@spring.message "label.user.login"/></td>
                    <@spring.bind "editUserForm.login"/>
                        <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        <td class="form-field">${(editUserForm.login)!''}</td>
                    </tr>
                    <tr>
                        <td class="form-label"><@spring.message "label.user.firstName"/></td>
                    <@spring.bind "editUserForm.firstName"/>
                        <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        <td class="form-field">${(editUserForm.firstName)!''}</td>
                    </tr>
                    <tr>
                        <td class="form-label"><@spring.message "label.user.lastName"/></td>
                    <@spring.bind "editUserForm.lastName"/>
                        <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        <td class="form-field">${(editUserForm.lastName)!''}</td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.password.old"><@spring.message "label.user.old.password"/></label></td>
                    <@spring.bind "editUserForm.oldPassword"/>
                        <td class="form-field">
                            <div>
                                <input id="user.password.old" type="password" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                            </div>
                        <#include "../includes/field-errors.ftl"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.password"><@spring.message "label.user.password"/></label></td>
                    <@spring.bind "editUserForm.password"/>
                        <td class="form-field">
                            <div>
                                <input id="user.password" type="password" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}">
                            </div>
                        <#include "../includes/field-errors.ftl"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.retype.password"><@spring.message "label.user.password.retype"/></label></td>
                    <@spring.bind "editUserForm.passwordRetype"/>
                        <td class="form-field">
                            <div>
                                <input id="user.retype.password" type="password" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}">
                            </div>
                        <#include "../includes/field-errors.ftl"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-button-pane" colspan="2">
                            <div class="button-pane">
                                <span class="-button -submit -change-password"><@spring.message "button.caption.submit"/></span>
                                <span class="-button -cancel -change-password" data-redirect-url="<@spring.url '/user/profile/welcome'/>"><@spring.message "button.caption.cancel"/></span>
                            </div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </div>
    </div>
</div>

