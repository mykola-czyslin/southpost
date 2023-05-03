package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.commons.model.dto.PagedSearchResponsePayload;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public interface PageSearchService<R extends PagedSearchRequestPayload, E extends EntityInfo<I>, I> {
	String MA_SEARCH_FORM = "searchForm";

	PagedSearchResponsePayload<R, E, I> prepareListModelAndView(@Nullable R searchRequestPayload, @Nonnull Locale locale);
}
