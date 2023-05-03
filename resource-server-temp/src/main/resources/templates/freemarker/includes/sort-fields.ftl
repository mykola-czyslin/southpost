<#-- @ftlvariable name="staticMediaUrl" type="java.lang.String" -->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.AbstractPagedSearchForm" -->
<#-- @ftlvariable name="sortOptions" type="java.util.List<ua.southpost.resource.commons.model.dto.SortOption>" -->
<#-- @ftlvariable name="sortFields" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="sortDirections" type="java.util.Map<String, String>"-->
<#import "/spring.ftl" as spring/>
<#assign sortOptions = searchForm.sortOptions />
<div class="-table">
    <div class="-row">
        <div class="-cell">
            <div id="sortOptionsContainer" class="-table">
                <#if sortOptions??>
                    <#list sortOptions as sortOption>
                        <#assign __index = sortOption?index/>
                        <div class="-row -sort-option">
                            <div class="-cell">
                                <label for="sortOptions${__index}.fieldName"><@spring.message "label.sort.field"/></label>
                            </div>
                            <div class="-cell">
                                <#assign path = "searchForm.sortOptions[${__index}].fieldName"/>
                                <@spring.formSingleSelect path, sortFields, ""/>
                            </div>
                            <div class="-cell">
                                <label for="sortOptions${__index}.direction"><@spring.message "label.sort.direction"/></label>
                            </div>
                            <div class="-cell">
                                <#assign path = "searchForm.sortOptions[${__index}].direction"/>
                                <@spring.formSingleSelect path, sortDirections, ""/>
                            </div>
                            <div class="-cell">
                                <img class="-tooltip-target sort-remove"
                                     title="<@spring.message 'tooltip.remove.sort.field'/>"
                                     src="${staticMediaUrl}/img/delete.png" width="16" height="16" alt="delete"/>
                            </div>
                        </div>
                    </#list>
                </#if>
            </div>
        </div>
        <div class="-cell -button -small-button plus-sort -tooltip-target" title="<@spring.message 'tooltip.add.field.to.sort'/>">+</div>
    </div>
</div>
<script id="sortOptionTemplate" type="text/x-jQuery-tmpl">
          <div class="-row -sort-option">
              <div class="-cell">
                  <label for="sortOptions${r'${index}'}.fieldName"><@spring.message "label.sort.field"/></label>
              </div>
              <div class="-cell">
                  <select id="sortOptions${r'${index}'}.fieldName" name="sortOptions[${r'${index}'}].fieldName">
                        <#list sortFields as fieldValue, fieldText>
                            <option value="${fieldValue}">${fieldText}</option>
                        </#list>
                  </select>
              </div>
              <div class="-cell">
                  <label for="sortOptions${r'${index}'}.direction"><@spring.message "label.sort.direction"/></label>
              </div>
              <div class="-cell">
                  <select id="sortOptions${r'${index}'}.direction" name="sortOptions[${r'${index}'}].direction">
                        <#list sortDirections as directionValue, directionText>
                            <option value="${directionValue}">${directionText}</option>
                        </#list>
                  </select>
              </div>
              <div class="-cell">
                  <img class="-tooltip-target sort-remove" title="<@spring.message 'tooltip.remove.sort.field'/>" src="${staticMediaUrl}/img/delete.png" width="16" height="16" alt="delete"/>
              </div>
          </div>
</script>
