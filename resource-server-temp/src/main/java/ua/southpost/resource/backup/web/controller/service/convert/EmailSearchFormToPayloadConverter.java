package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.EmailSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class EmailSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<EmailSearchForm, EmailSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull EmailSearchForm form, @Nonnull EmailSearchRequestPayload payload) {
		payload.setEmailAddressPattern(form.getEmailAddressPattern());
		payload.setDescriptionPattern(form.getDescriptionPattern());
	}

	@Nonnull
	@Override
	protected EmailSearchRequestPayload createPayload() {
		return new EmailSearchRequestPayload();
	}
}
