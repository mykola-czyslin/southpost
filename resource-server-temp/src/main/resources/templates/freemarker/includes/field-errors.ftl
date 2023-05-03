<#if spring.status.error || (spring.status.errorMessages?? && spring.status.errorMessages?size gt 0)>
    <div class="form-error">
        <ul>
            <#list spring.status.errorMessages as error>
            <#assign messageKey = (error!'Generic Error')/>
            <li><@spring.message messageKey/></li>
            </#list>
        </ul>
    </div>
</#if>
