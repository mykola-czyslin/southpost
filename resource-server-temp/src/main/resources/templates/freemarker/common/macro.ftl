<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#macro entityLookupPopup containerId templateId>
    <#if !(containerId?has_content)>
        <#assign containerId = 'lookupPopupContainer'>
    </#if>
    <#if !(templateId?has_content)>
        <#assign templateId = 'popupLookupTemplate'/>
    </#if>
    <div id="${containerId}" class="-popup -lookup">
        <div class="-button -scroll-button -prev-page"><div  class="w24 -layout-align-center"><img src="${staticMediaUrl}/img/prev.png" width="16" height="16" alt="prev"/></div></div>
        <ul class="select"></ul>
        <div class="-button -scroll-button -next-page"><div  class="w24 -layout-align-center"><img src="${staticMediaUrl}/img/next.png" width="16" height="16" alt="next"/></div></div>
    </div>
    <script id="${templateId}" type="text/x-jQuery-tmpl">
         <li><span class="-clickable" data-id="${r'${id}'}">${r"${text_value}"}</span></li>
    </script>
</#macro>

<#macro optionsTemplate templateId>
    <#if !(templateId?has_content)>
        <#assign templateId = 'optionsTemplate'/>
    </#if>
    <script id="${templateId}" type="text/x-jQuery-tmpl">
        <option value="${r'${id}'}">${r"${name}"}</option>
    </script>
</#macro>

<#macro button buttonUrl buttonCaptionKey tooltipTextKey staffMember>
    <#if staffMember>
        &nbsp;<a
            class="-tooltip-target -button -small-button"
            title="<@spring.message tooltipTextKey/>"
            href="<@spring.url buttonUrl/>"><@spring.message buttonCaptionKey/></a>&nbsp;
    </#if>
</#macro>
<#macro imageButton actionUrl imageSrc imageAlt imageWidth imageHeight tooltipTextKey staffMember>
    <#if staffMember>
        <a class="-tooltip-target" title="<@spring.message tooltipTextKey/>" href="<@spring.url actionUrl/>">
            <img src="${imageSrc}" alt="${imageAlt}" width="${imageWidth}" height="${imageHeight}"/>
        </a>
    </#if>
</#macro>
<#macro addEntityButton actionUrl tooltipMessageKey>
    <span class="-header-caption-box">
        <a class="-tooltip-target" title="<@spring.message tooltipMessageKey/>" href="<@spring.url actionUrl/>">
            <img src="${staticMediaUrl}/img/add.png" width="14" height="14" alt="add"/>
        </a>
    </span>
</#macro>
<#macro deleteEntityButton actionSelectorClass actionUrl confirmationMessageKey tooltipMessageKey confirmationMessageArgs=[]>
    <#assign confirmationText><@spring.messageArgs confirmationMessageKey confirmationMessageArgs/></#assign>
    <span class="${actionSelectorClass} -clickable -header-caption-box -tooltip-target" data-url="${actionUrl}"
          data-confirmation="${confirmationText}"
          title="<@spring.message tooltipMessageKey/>">
        <img src="${staticMediaUrl}/img/delete.png" width="14" height="14" alt="delete"/>
    </span>
</#macro>
<#macro headerCaptionBox textKey addOnClasses="" attributes="">
    <#assign cssSeparator><#if (addOnClasses!'')?has_content> <#else></#if></#assign>
    <span class="-header-caption-box${cssSeparator}${(addOnClasses!'')?html}" ${attributes}><@spring.message textKey/></span>
</#macro>