<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<div class="body-content">
    <div>
        <div class="user-form-wrapper -layout-align-center">
            <div class="-form-frame">
                <#if error??>
                    <div class="form-error">
                        <p><@spring.message error/></p>
                    </div>
                </#if>
                <form id="login-form" method="POST" action="<@spring.url '/perform-login'/>">
                    <table class="register-user-form -login-form">
                        <tr>
                            <td class="form-label"><label
                                        for="user-name-input"><@spring.message "label.user.login"/></label></td>
                            <td class="form-field"><input id="user-name-input" type="text" name="login"/></td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="password-input"><@spring.message "label.user.password"/></label></td>
                            <td class="form-field"><input id="password-input" type="password" name="password"/></td>
                        </tr>
                        <tr>
                            <td class="form-label"><label
                                        for="remember-me-input"><@spring.message "label.remember.me"/></label></td>
                            <td class="form-field"><input id="remember-me-input" type="checkbox" name="remember-me"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-button-pane" colspan="2">
                                <div class="button-pane">
                                    <span class="-button -submit"><@spring.message "button.caption.submit"/></span>
                                    <span class="-button -cancel"
                                          data-redirect-url="<@spring.url '/'/>"><@spring.message "button.caption.cancel"/></span>
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
