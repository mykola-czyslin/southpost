<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.UserSearchFormImpl" -->
<#-- @ftlvariable name="pageListData" type="ua.southpost.resource.commons.model.dto.EntityGridPage<ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo, java.lang.Long>" -->
<#-- @ftlvariable name="roleOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="userActivityKindOptions" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#include "../includes/mail-dialog.ftl"/>
<#import "/spring.ftl" as spring/>
<#import "../common/macro.ftl" as commons/>
<form id="searchForm" method="post" action="<@spring.url '/user/admin/list'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <@spring.formHiddenInput "searchForm.pageNum", ""/>
    <@spring.formHiddenInput "searchForm.linesPerPage", ""/>
    <div class="-editable-fields" data-invalid-values-header="<@spring.message 'err.invalid.values.header'/>">
        <div class="-table">
            <div class="-row">
                <div class="-cell">
                    <div class="-table">
                        <div class="-row">
                            <div class="-cell">
                                <label for="loginPattern"><@spring.message "label.user.login"/></label>
                            </div>
                            <div class="-cell">
                                <label for="emailPattern"><@spring.message "label.user.e-mail"/></label>
                            </div>
                            <div class="-cell">
                                <label for="firstNamePattern"><@spring.message "label.user.firstName"/></label>
                            </div>
                            <div class="-cell">
                                <label for="lastNamePattern"><@spring.message "label.user.lastName"/></label>
                            </div>
                            <div class="-cell">
                                <label for="registeredAtFrom"><@spring.message "label.user.registered.at.from"/></label>
                            </div>
                            <div class="-cell">
                                <label for="registeredAtTo"><@spring.message "label.user.registered.at.to"/></label>
                            </div>
                        </div>
                        <div class="-row">
                            <#assign activitiesTooltip><@spring.message 'tooltip.pattern'/></#assign>
                            <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${activitiesTooltip?html}"</#assign>
                            <div class="-cell">
                                <@spring.formInput "searchForm.loginPattern", attributes, "text"/>
                            </div>
                            <div class="-cell">
                                <@spring.formInput "searchForm.emailPattern", attributes, "text"/>
                            </div>
                            <div class="-cell">
                                <@spring.formInput "searchForm.firstNamePattern", attributes, "text"/>
                            </div>
                            <div class="-cell">
                                <@spring.formInput "searchForm.lastNamePattern", attributes, "text"/>
                            </div>
                            <#assign datePlaceholder><@spring.message "placeholder.date"/></#assign>
                            <#assign activitiesTooltip><@spring.message "tooltip.date.format"/></#assign>
                            <#assign attributes>class="-tooltip-target" size="30" maxlength="80" title="${activitiesTooltip?html}" pattern="^20[1-9][0-9]-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$" placeholder="${datePlaceholder}"</#assign>
                            <div class="-cell">
                                <@spring.formInput "searchForm.registeredAtFrom", attributes, "text"/>
                                <#include "../includes/field-errors.ftl"/>
                            </div>
                            <div class="-cell">
                                <@spring.formInput "searchForm.registeredAtTo", attributes, "text"/>
                                <#include "../includes/field-errors.ftl"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="-row">
                <div class="-cell">
                    <div class="-table">
                        <div class="-row">
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign rolesTooltip><@spring.message 'tooltip.user.search.roles'/></#assign>
                                <label for="roles" class="-tooltip-target"
                                       title="${(rolesTooltip!'')?html}"><@spring.message "label.user.roles"/></label>
                            </div>
                        </div>
                        <div class="-row">
                            <div class="-cell -any-role -min-width-12em -max-width-18em">
                                <@spring.formCheckbox "searchForm.anyRole" ""/>
                                <label for="anyRole" class="-tooltip-target"
                                       title="<@spring.message 'tooltip.ignore.roles'/>"><@spring.message "label.ignore.roles"/></label>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign checkBoxSeparator>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                </#assign>
                                <@spring.formCheckboxes "searchForm.roles" roleOptions checkBoxSeparator/>
                            </div>
                        </div>
                        <div class="-row">
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign activitiesTooltip><@spring.message "tooltip.user.search.declared.activities"/></#assign>
                                <label for="declareActivities" class="-tooltip-target"
                                       title="${(activitiesTooltip!'')?html}">
                                    <@spring.message "label.user.declared.activities"/>
                                </label>
                            </div>
                        </div>
                        <div class="-row">
                            <div class="-cell -any-activity -declared -min-width-12em -max-width-18em">
                                <@spring.formCheckbox "searchForm.anyDeclaredActivity" ""/>
                                <label for="anyDeclaredActivity"><@spring.message "label.user.allow.any.activity"/></label>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign checkBoxSeparator>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                </#assign>
                                <@spring.formCheckboxes "searchForm.declaredActivities" userActivityKindOptions checkBoxSeparator/>
                            </div>
                        </div>
                        <div class="-row">
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign activitiesTooltip><@spring.message "tooltip.user.search.confirmed.activities"/></#assign>
                                <label for="confirmedActivities" class="-tooltip-target"
                                       title="${(activitiesTooltip!'')?html}">
                                    <@spring.message "label.user.confirmed.activities"/>
                                </label>
                            </div>
                        </div>
                        <div class="-row">
                            <div class="-cell -any-activity -confirmed -min-width-12em -max-width-18em">
                                <@spring.formCheckbox "searchForm.anyConfirmedActivity" ""/>
                                <label for="anyConfirmedActivity"><@spring.message "label.user.allow.any.activity"/></label>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                <#assign checkBoxSeparator>
                            </div>
                            <div class="-cell -min-width-12em -max-width-18em">
                                </#assign>
                                <@spring.formCheckboxes "searchForm.confirmedActivities" userActivityKindOptions checkBoxSeparator/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="-table">
        <div class="-row">
            <div class="-cell">
                <#include "../includes/sort-fields.ftl"/>
            </div>
            <div class="-cell">
                <div class="-table">
                    <div class="-row">
                        <div class="-cell -span-500px">

                        </div>
                        <div class="-cell">
                            <div class="button-pane">
                                <span class="-button -search-submit"><@spring.message "button.caption.search"/></span>
                                <span class="-button -search-reset"><@spring.message "button.caption.reset"/></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<#assign pagingModel = pageListData.model/>
<#include "../includes/paging-data.ftl"/>
<div class="responsive-container">
    <table class="grid user-grid">
        <thead>
        <tr>
            <th class="-grid-actions">&nbsp;</th>
            <th class="-user-login"><@commons.headerCaptionBox textKey="label.user.login"/></th>
            <th class="-user-email"><@commons.headerCaptionBox textKey="label.user.e-mail"/></th>
            <th class="-user-first-name"><@commons.headerCaptionBox textKey="label.user.firstName"/></th>
            <th class="-user-last-name"><@commons.headerCaptionBox textKey="label.user.lastName"/></th>
            <th class="-user-registered-at"><@commons.headerCaptionBox textKey="label.registered.at"/></th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <#list pageListData.entityList as user>
            <#assign userUrl = '/user/admin/${user.id?string["0"]}'/>
            <#assign removeUserUrl><@spring.url "/api/${userUrl}/delete"/></#assign>
            <#assign messageArguments = [user.login]/>
            <#assign confirmationMessage><@spring.messageArgs "message.remove.user.confirmation" messageArguments/></#assign>
            <tr>
                <td class="-grid-actions">
                    <#if !user.internal>&nbsp;
                        <@commons.deleteEntityButton
                        actionSelectorClass="-remove-user"
                        actionUrl=removeUserUrl
                        confirmationMessageKey="message.remove.user.confirmation"
                        tooltipMessageKey="tooltip.remove.user"
                        confirmationMessageArgs=[user.login]/>
                    </#if>
                </td>
                <#assign manageRolesUrl>${userUrl}/manage-roles</#assign>
                <td class="-user-login"><a href="<@spring.url manageRolesUrl/>">${user.login}</a></td>
                <td class="-user-email"><span class="-link-e-mail -clickable"
                                              data-address="${user.email}"
                                              data-dialog-id="mail-popup-container">${user.email}</span></td>
                <td class="-user-first-name">${user.firstName!"&nbsp;-&nbsp;"}</td>
                <td class="-user-last-name">${user.lastName!"&nbsp;-&nbsp;"}</td>
                <td class="-user-registered-at">${(user.registeredAt?date)?string["EEEE, d MMMM, yyyy"]}</td>
                <td>
                    <#if !user.internal>&nbsp;
                        <@commons.deleteEntityButton
                        actionSelectorClass="-remove-user"
                        actionUrl=removeUserUrl
                        confirmationMessageKey="message.remove.user.confirmation"
                        tooltipMessageKey="tooltip.remove.user"
                        confirmationMessageArgs=[user.login]/>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
