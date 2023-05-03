<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="userForm" type="ua.southpost.resource.backup.web.model.forms.entity.UpdateUserProfileForm" -->
<#-- @ftlvariable name="roles"  type="java.util.List<ua.southpost.resource.backup.data.model.UserRole>" -->
<#-- @ftlvariable name="userActivityKindOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#import "/spring.ftl" as spring/>
<div>
    <div class="user-form-wrapper -layout-align-center">
        <div class="-form-frame">
            <form id="form-user-profile" method="post" action="<@spring.url '/user/profile/update?ajax=yes'/>">
            <@spring.bind "userForm"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div id="form-message-pane" style="display:none"></div>
            <@spring.bind "userForm.userId"/>
                <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                <table class="user-form">
                    <tr><td class="form-label"><@spring.message "label.user.id"/></td><td class="form-field">${userForm.userId?string["0"]}</td></tr>
                    <tr>
                        <td class="form-label"><label for="user.firstName"><@spring.message "label.user.firstName"/></label></td>
                        <td class="form-field">
                        <@spring.bind "userForm.firstName"/>
                            <input id="user.firstName" type="text" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.lastName"><@spring.message "label.user.lastName"/></label></td>
                        <td class="form-field">
                        <@spring.bind "userForm.lastName"/>
                            <input id="user.lastName" type="text" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.login"><@spring.message "label.user.login"/></label></td>
                        <td class="form-field">
                        <@spring.bind "userForm.login"/>
                            <input id="user.login" type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                            <span>${userForm.login}</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-label"><label for="user.email"><@spring.message "label.user.e-mail"/></label></td>
                        <td class="form-field">
                        <@spring.bind "userForm.email"/>
                            <input id="user.email" type="email" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="box-header"><@spring.message "label.user.roles"/></div>
                            <#if roles??>
                                <div class="-flex-table -min-width-40em -max-width-40em">
                                    <#list roles as role>
                                        <div class="-flex-cell-2 -min-width-15em -max-width-15em"><@spring.message role.messageKey/></div>
                                    </#list>
                                </div>
                            <#else>
                                <p>no roles</p>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="box-header">
                                <#assign kindsTooltip><@spring.message "tooltip.your.activity.kinds"/></#assign>
                                <label for="userActivities" class="-tooltip-target" title="${(kindsTooltip!'')?html}"><@spring.message "label.user.activities"/></label>
                            </div>
                            <div class="-flex-table -min-width-40em -max-width-40em">
                                <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                    <#assign checkBoxesSeparator>
                                </div>
                                <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                    </#assign>
                                    <@spring.formCheckboxes
                                    path="userForm.userActivities"
                                    options=userActivityKindOptions
                                    separator=checkBoxesSeparator/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-button-pane" colspan="2">
                            <div class="button-pane">
                                <a class="-button" id="form-user-profile-submit" data-form-id="form-user-profile" data-message-pane="form-message-pane"><@spring.message "button.caption.update"/></a>
                                <a class="-button" href="<@spring.url '/'/>"><@spring.message "button.caption.cancel"/></a>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <p>
            <a href="<@spring.url '/user/profile/change-password'/>"><@spring.message "link.change-password"/></a>
        </p>
    </div>
</div>




