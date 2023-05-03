<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<div class="-popup -mail-form">
    <div class="-mail-popup">
        <p class="-recipient-display"></p>
        <form id="mail-form" method="post" action="<@spring.url "/mail/send?ajax=yes"/>">
        <@spring.bind "mailForm"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <@spring.bind "mailForm.recipient"/>
            <input id="recipient" type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
            <table width="100%">
                <tr>
                    <td class="form-field"><label for="subject"><@spring.message "mail.subject"/></label></td>
                </tr>
                <tr>
                    <td class="form-field">
                    <@spring.bind "mailForm.subject"/>
                        <input id="subject" type="text" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}" size="40" maxlength="256"/>
                    </td>
                </tr>
                <tr>
                    <td class="form-field">
                        <label for="content"><@spring.message "mail.content"/></label>
                    </td>
                </tr>
                <tr>
                    <td class="form-field">
                    <@spring.bind "mailForm.content"/>
                        <textarea id="content" name="${spring.status.expression}" rows="6" cols="40">${((spring.status.value)!'')?html}</textarea>
                    </td>
                </tr>
                <tr>
                    <td class="form-button-pane">
                        <span class="-button -submit"><@spring.message "button.caption.mail.send"/></span>
                        <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="-popup -mail-response">
    <div class="-response-box">

    </div>
</div>
