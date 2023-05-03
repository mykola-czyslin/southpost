package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class PhoneSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<PhoneSearchForm, PhoneSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull PhoneSearchForm form, @Nonnull PhoneSearchRequestPayload payload) {
		payload.setPhoneNumberPattern(form.getPhoneNumberPattern());
		payload.setDescriptionPattern(form.getDescriptionPattern());
	}

	@Nonnull
	@Override
	protected PhoneSearchRequestPayload createPayload() {
		return new PhoneSearchRequestPayload();
	}
}
