package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.ClinicSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class ClinicSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<ClinicSearchForm, ClinicSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull ClinicSearchForm form, @Nonnull ClinicSearchRequestPayload payload) {
		payload.setClinicType(form.getClinicType());
		payload.setDescriptionPattern(form.getDescriptionPattern());
		payload.setNamePattern(form.getNamePattern());
		payload.setService(form.getService());
		LocationSearchFormToPayloadConverter.populatePayloadFromForm(form, payload);
		payload.setPhonePattern(form.getPhonePattern());
		payload.setEmailPattern(form.getEmailPattern());
	}

	@Nonnull
	@Override
	protected ClinicSearchRequestPayload createPayload() {
		return new ClinicSearchRequestPayload();
	}
}
