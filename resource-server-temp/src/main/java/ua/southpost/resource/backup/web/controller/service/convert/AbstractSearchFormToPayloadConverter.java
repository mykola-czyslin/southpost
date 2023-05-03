package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;
import ua.southpost.resource.backup.web.controller.service.SearchFormToPayloadConverter;
import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;

import javax.annotation.Nonnull;

abstract class AbstractSearchFormToPayloadConverter<F extends PagedSearchForm, P extends AbstractPagedSearchRequestPayload> implements SearchFormToPayloadConverter<F, P> {
	@Nonnull
	@Override
	public final P convert(@Nonnull F form) {
		final P payload = createPayload();
		payload.setPageNum(form.getPageNum());
		payload.setLinesPerPage(form.getLinesPerPage());
		payload.setSortOptions(form.getSortOptions());
		copySearchCriteriaToPayload(form, payload);
		return payload;
	}

	protected abstract void copySearchCriteriaToPayload(@Nonnull F form, @Nonnull P payload);

	@Nonnull
	protected abstract P createPayload();
}
