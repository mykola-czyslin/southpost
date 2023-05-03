package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class StreetSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<StreetSearchForm, StreetSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull StreetSearchForm form, @Nonnull StreetSearchRequestPayload payload) {
		populatePayloadFromForm(form, payload);
	}

	@Nonnull
	@Override
	protected StreetSearchRequestPayload createPayload() {
		return new StreetSearchRequestPayload();
	}

	static void populatePayloadFromForm(@Nonnull StreetSearchForm form, @Nonnull StreetSearchRequestPayload payload) {
		SettlementSearchFormToPayloadConverter.populatePayloadFieldsFromForm(form, payload);
		payload.setStreetKind(form.getStreetKind());
		payload.setStreetNamePattern(form.getStreetNamePattern());
	}
}
