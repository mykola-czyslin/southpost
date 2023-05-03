<#-- @ftlvariable name="submitType" type="ua.southpost.resource.backup.web.model.SubmitType" -->
<#-- @ftlvariable name="settlementForm" type="ua.southpost.resource.backup.web.model.forms.entity.SettlementForm" -->
<#-- @ftlvariable name="settlementKindOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="districtOptions" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="regionOptions" type="java.util.Map<String,String>" -->
<#import "/spring.ftl" as spring/>
<#import "macro.ftl" as settlements/>
<#assign editable = submitType.formEditable/>
<div id="manage-settlement-form"
     data-action="<@spring.url '/api/settlement/save'/>"
     data-success-url="<@spring.url '/master/settlement/list'/>"
     data-cancel-url="<@spring.url '/master/settlement/list'/>">
    <div>
        <div class="settlement-form-wrapper -layout-align-center">
            <div id="data" class="form-error hidden"></div>
            <div class="-form-frame">
                <table>
                    <@settlements.settlementFields
                        errPathPrefix=""
                        idPrefix="settlement_"
                        settlementForm=settlementForm
                        regionOptions=regionOptions
                        districtOptions=districtOptions
                        settlementKindOptions=settlementKindOptions
                        editable=editable
                        searchable=false/>
                    <tr>
                        <td class="form-field" colspan="2">
                            <div class="button-pane">
                            <#if editable>
                                <span class="-button -submit" data-type="${(submitType!'create')?html}"><@spring.message "button.caption.submit"/></span>
                                <span class="-button -reset"><@spring.message "button.caption.reset"/></span>
                                <span class="-button -cancel"><@spring.message "button.caption.cancel"/></span>
                            <#else>
                                <#assign closeUrl><@spring.url "/master/settlement/list"/></#assign>
                                <span class="-button -close" data-url="${closeUrl}"><@spring.message "button.caption.close"/></span>
                                <#if staff>
                                    <#assign editActionUrl><@spring.url "/master/settlement/${settlementForm.id?string['0']}/edit"/></#assign>
                                    <span class="-button -edit" data-url="${editActionUrl}"><@spring.message "button.caption.edit"/></span>
                                </#if>
                            </#if>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<#if editable>
<script id="optionsTemplate" type="text/x-jQuery-tmpl">
    <option value="${r'${id}'}">${r"${name}"}</option>
</script>
</#if>