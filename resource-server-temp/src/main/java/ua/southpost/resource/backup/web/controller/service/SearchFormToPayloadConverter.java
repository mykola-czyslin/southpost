package ua.southpost.resource.backup.web.controller.service;

import ua.southpost.resource.commons.model.dto.PagedSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;

import javax.annotation.Nonnull;

public interface SearchFormToPayloadConverter<F extends PagedSearchForm, P extends PagedSearchRequestPayload> {
	@Nonnull
	P convert(@Nonnull F form);
}
