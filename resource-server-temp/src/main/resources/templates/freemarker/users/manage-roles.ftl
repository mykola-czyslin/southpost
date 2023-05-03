<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="roleOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="userActivityKindOptions" type="java.util.Map<java.lang.String, java.lang.String>" -->
<#-- @ftlvariable name="editUserForm" type="ua.southpost.resource.backup.web.model.forms.entity.UserPermissionManagementForm" -->
<#import "/spring.ftl" as spring/>
<#assign formAction = "/user/admin/manage-roles" />
<div>
    <div class="user-form-wrapper -layout-align-center">
        <div class="-form-frame">
            <form id="manage-roles" action="<@spring.url formAction/>" method="POST">
                <@spring.bind "editUserForm"/>
                <#include "../includes/form-errors.ftl"/>
                <@spring.bind "editUserForm.userId"/>
                <input type="hidden" name="${spring.status.expression}" value="${((spring.status.value)!'')?html}"/>
                <table class="user-form">
                    <tr>
                        <td class="form-label"><@spring.message "label.user.login"/></td>
                        <td class="form-field">${(editUserForm.login!'')?html}</td>
                    </tr>
                    <tr>
                        <td class="form-label"><@spring.message "label.user.firstName"/></td>
                        <td class="form-field">${(editUserForm.firstName!'')?html}</td>
                    </tr>
                    <tr>
                        <td class="form-label"><@spring.message "label.user.lastName"/></td>
                        <td class="form-field">${(editUserForm.lastName!'')?html}</td>
                    </tr>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="box-header">
                                <#assign rolesTooltip><@spring.message "tooltip.user.roles"/></#assign>
                                <label for="roles" class="-tooltip-target"
                                       title="${(rolesTooltip!'')?html}"><@spring.message "label.user.roles"/></label>
                            </div>
                            <div class="-flex-table -min-width-40em -max-width-40em">
                                <div class="-flex-cell-3 -min-width-15em -max-width-15em">
                                    <#assign optionsSeparator>
                                </div>
                                <div class="-flex-cell-3 -min-width-15em -max-width-15em">
                                    </#assign>
                                    <@spring.formCheckboxes "editUserForm.roles" roleOptions optionsSeparator/>
                                </div>
                            </div>
                            <#include "../includes/field-errors.ftl"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="-table">
                                <div class="-row">
                                    <div class="-cell">
                                        <div class="box-header">
                                            <label for="confirmedUserActivities"><@spring.message "label.user.activities"/></label>
                                        </div>
                                    </div>
                                    <div class="-cell">
                                        <div class="box-header">
                                            <#assign declaredActivitiesTooltip><@spring.message "tooltip.user.declared.activities"/></#assign>
                                            <label for="declaredUserActivities" class="-tooltip-target"
                                                   title="${(declaredActivitiesTooltip!'')?html}"><@spring.message "label.user.declared.activities"/></label>

                                        </div>
                                        <div class="-flex-table -max-width-40em -max-width-40em">
                                            <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                                <#assign optionsSeparator>
                                            </div>
                                            <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                                </#assign>
                                                <@spring.formCheckboxes path="editUserForm.declaredUserActivities"
                                                    options=userActivityKindOptions
                                                    separator=optionsSeparator
                                                    attributes="readonly disabled"/>
                                            </div>
                                        </div>
                                        <div class="box-header">
                                            <#assign confirmedActivitiesTooltip><@spring.message "tooltip.user.confirmed.activities"/></#assign>
                                            <label for="confirmedUserActivities" class="-tooltip-target"
                                                   title="${(confirmedActivitiesTooltip!'')?html}"><@spring.message "label.user.confirmed.activities"/></label>
                                        </div>
                                        <div class="-flex-table -max-width-40em -max-width-40em">
                                            <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                                <#assign optionsSeparator>
                                            </div>
                                            <div class="-flex-cell-2 -min-width-18em -max-width-18em">
                                                </#assign>
                                                <@spring.formCheckboxes
                                                    path="editUserForm.confirmedUserActivities"
                                                    options=userActivityKindOptions
                                                    separator=optionsSeparator/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="form-button-pane" colspan="2">
                            <div class="button-pane">
                                <span class="-button -submit -manage-roles"><@spring.message "button.caption.submit"/></span>
                                <span class="-button -cancel -manage-roles"
                                      data-redirect-url="<@spring.url '/user/admin/list'/>"><@spring.message "button.caption.cancel"/></span>
                            </div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>

