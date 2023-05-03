<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "/spring.ftl" as spring/>
<div class="body-content">
    <@spring.bind "findForm"/>
    <#include "../includes/form-errors.ftl"/>
    <form method="POST" action="<@spring.url '/user/admin/find'/>">
        <table class="register-user-form">
            <tr>
                <td class="form-label"><@spring.message "label.user.e-mail"/></td>
                <@spring.bind "findForm.email"/>
                <td class="form-field">
                    <input type="email" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}">
                    <@spring.showErrors "<br/>"/>
                </td>
            </tr>
        </table>
        <div class="button-pane">
            <input type="submit" value="<@spring.message 'label.user.find'/>"/>
            <input type="reset"/>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
