package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.backup.web.controller.service.SearchFormToPayloadConverter;
import ua.southpost.resource.backup.web.model.forms.search.SettlementSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class SettlementSearchFormToPayloadConverter implements SearchFormToPayloadConverter<SettlementSearchForm, SettlementSearchRequestPayload> {
	@Nonnull
	@Override
	public SettlementSearchRequestPayload convert(@Nonnull SettlementSearchForm form) {
		final SettlementSearchRequestPayload payload = new SettlementSearchRequestPayload();
		populatePayloadFieldsFromForm(form, payload);
		return payload;
	}

	static void populatePayloadFieldsFromForm(@Nonnull SettlementSearchForm form, @Nonnull SettlementSearchRequestPayload payload) {
		payload.setDistrictId(form.getDistrictId());
		payload.setRegionId(form.getRegionId());
		payload.setSettlementKind(form.getSettlementKind());
		payload.setSettlementNamePattern(form.getSettlementNamePattern());
		payload.setPageNum(form.getPageNum());
		payload.setLinesPerPage(form.getLinesPerPage());
		payload.setSortOptions(form.getSortOptions());
	}
}
