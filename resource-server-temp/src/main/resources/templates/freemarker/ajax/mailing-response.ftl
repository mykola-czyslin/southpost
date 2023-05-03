<#-- @ftlvariable name="mailForm" type="ua.southpost.resource.backup.web.model.forms.MailForm" -->
<@spring.bind "mailForm"/>
<#include "../includes/form-errors.ftl"/>
<@spring.bind "mailForm.recipient"/>
<#include "../includes/field-errors.ftl"/>
<@spring.bind "mailForm.subject"/>
<#include "../includes/field-errors.ftl"/>
<@spring.bind "mailForm.content"/>
<#include "../includes/field-errors.ftl"/>
<#if success??>
    <div class="success">
        <#assign arguments = [mailForm.recipient!'unknown']/>
        <@spring.messageArgs "mail.send.success" arguments/>
    </div>
</#if>