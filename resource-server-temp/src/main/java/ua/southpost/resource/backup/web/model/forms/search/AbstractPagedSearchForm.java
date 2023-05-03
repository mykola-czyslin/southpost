/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The abstract paged search form.
 * Created by mchys on 26.05.2018.
 */
public class AbstractPagedSearchForm implements PagedSearchForm {
	@JsonProperty("page_num")
	private int pageNum = 1;
	@JsonProperty("lines_per_page")
	private int linesPerPage;
	@JsonProperty("sort_options")
	private List<SortOption> sortOptions;

	public AbstractPagedSearchForm() {
	}

	public AbstractPagedSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		this.pageNum = pageNum;
		this.linesPerPage = linesPerPage;
		this.sortOptions = sortOptions;
	}

	@Override
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public int getLinesPerPage() {
		return linesPerPage;
	}

	public void setLinesPerPage(int linesPerPage) {
		this.linesPerPage = linesPerPage;
	}

	@Override
	public List<SortOption> getSortOptions() {
		return sortOptions;
	}

	@Override
	@SuppressWarnings("unused")
	public void setSortOptions(List<SortOption> sortOptions) {
		this.sortOptions = sortOptions;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("pageNum", pageNum)
				.append("linesPerPage", linesPerPage)
				.append("sortOptions", sortOptions)
				.toString();
	}
}
