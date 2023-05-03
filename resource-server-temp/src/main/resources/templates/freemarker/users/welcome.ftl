<#-- @ftlvariable name="user" type="ua.southpost.resource.backup.data.model.User" -->
<div class="success">
    <h1><@spring.message "heading.welcome"/></h1>
    <p>
    <#assign arguments = [user.firstName, user.lastName, user.login]/>
        <@spring.messageArgs "message.template.welcome" arguments/>

    </p>
</div>
