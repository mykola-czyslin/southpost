<#if spring.status.error || spring.status.errors?? && spring.status.errors.allErrors?? && spring.status.errors.allErrors?size gt 0>
    <div class="form-error">
        <ul>
            <#list spring.status.errors.allErrors as error>
            <#assign messageKey = (error.message)!(error.defaultMessage!'Generic Error')/>
            <li><@spring.message messageKey/></li>
            </#list>
        </ul>
    </div>
</#if>
