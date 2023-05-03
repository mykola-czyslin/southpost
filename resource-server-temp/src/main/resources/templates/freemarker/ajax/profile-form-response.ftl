<#-- @ftlvariable name="userForm" type="ua.southpost.resource.backup.web.model.forms.entity.UpdateUserProfileForm" -->
<@spring.bind "userForm"/>
<#include "../includes/form-errors.ftl"/>
<@spring.bind "userForm.login"/>
<#include "../includes/field-errors.ftl"/>
<@spring.bind "userForm.firstName"/>
<#include "../includes/field-errors.ftl"/>
<@spring.bind "userForm.lastName"/>
<#include "../includes/field-errors.ftl"/>
<@spring.bind "userForm.email"/>
<#include "../includes/field-errors.ftl"/>
<#if success??>
<div class="success">
    <#assign arguments=[userForm.login!'']/>
    <@spring.messageArgs "profile.update.success" arguments/>
</div>
</#if>