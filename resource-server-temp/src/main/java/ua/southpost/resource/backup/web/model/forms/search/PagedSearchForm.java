package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.commons.model.dto.SortOption;

import java.util.List;

public interface PagedSearchForm {
	int getPageNum();

	int getLinesPerPage();

	List<SortOption> getSortOptions();

	@SuppressWarnings("unused")
	void setSortOptions(List<SortOption> sortOptions);
}
