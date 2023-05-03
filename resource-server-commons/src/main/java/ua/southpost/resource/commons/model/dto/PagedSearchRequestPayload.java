package ua.southpost.resource.commons.model.dto;

public interface PagedSearchRequestPayload extends SortOptionsPayload {
	int getPageNum();

	int getLinesPerPage();
}
