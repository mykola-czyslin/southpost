<#-- @ftlvariable name="pagingModel" type="ua.southpost.resource.commons.model.dto.EntityGridPage.Model" -->
<#-- @ftlvariable name="linesPerPageOptions" type="int[]"-->
<#-- @ftlvariable name="searchForm" type="ua.southpost.resource.backup.web.model.forms.search.AbstractPagedSearchForm" -->
<#import "/spring.ftl" as spring/>
<#assign activePageClass = "active-page"/>
<#assign neighbourClass = "neighbour-page"/>
<div class="-page-control-pane">
    <div class="-page-control-pane-row">
        <div class="-page-labels-pane">
            <#list pagingModel.pages as pageNumber>
                <#if pagingModel.currentPage == pageNumber>
                    <div class="-page ${activePageClass}" data-page-number="${pageNumber?string['0']}">${pageNumber?string['0']}</div>
                <#else>
                    <div class="-page ${neighbourClass}" data-page-number="${pageNumber?string['0']}">${pageNumber?string['0']}</div>
                </#if>
            </#list>
        </div>
        <div class="-page-lines-per-page-caption-pane">
            <label for="linesPerPageSelect"><@spring.message 'label.lines.per.page'/></label>
        </div>
        <div class="-page-lines-per-page-pane">
            <select id="linesPerPageSelect" class="-lines-per-page-selector">
                <#list linesPerPageOptions as linesPerPageNumber>
                    <#if searchForm.linesPerPage == linesPerPageNumber>
                        <option value="${linesPerPageNumber}" selected>${linesPerPageNumber?string['0']}</option>
                    <#else >
                        <option value="${linesPerPageNumber}">${linesPerPageNumber?string['0']}</option>
                    </#if>
                </#list>
            </select>
        </div>
    </div>
</div>
