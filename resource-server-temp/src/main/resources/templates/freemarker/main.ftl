<html lang="uk">
<#include "includes/head.ftl"/>
<body>
    <div class="body-content">
        <#if contentView??>
            <#include "${contentView}.ftl"/>
        </#if>
    </div>
<#include "includes/menu.ftl"/>
</body>
</html>