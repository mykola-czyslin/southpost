<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="causes" type="java.util.List<java.lang.Throwable>" -->
<#-- @ftlvariable name="exception" type="java.lang.Exception" -->
        <div class="error">
            <p>An exception occurred: ${exception.class.name}</p>
            <p><span>message:</span> ${(message)!exception.class.name}</p>
                <#if exception.stackTrace??>
                    <p>Stack trace</p>
                    <div>
                        <#list exception.stackTrace as trace>${trace}<br/></#list>
                    </div>
                </#if>
            <#if causes?? && causes?size gt 0>
                <#list causes as cause>
                    <p>${cause.class.name}</p>
                    <p><span>message:</span> ${(cause.message)!cause.class.name}</p>
                    <#if cause.stackTrace??>
                        <p>Stack trace</p>
                        <div>
                            <#list cause.stackTrace as trace>${trace}<br/></#list>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
