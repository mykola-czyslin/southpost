package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.VacancySearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class VacancySearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<VacancySearchForm, VacancySearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull VacancySearchForm form, @Nonnull VacancySearchRequestPayload payload) {
		SettlementSearchFormToPayloadConverter.populatePayloadFieldsFromForm(form, payload);
		payload.setSummaryPattern(form.getSummaryPattern());
		payload.setDescriptionPattern(form.getDescriptionPattern());
		payload.setEmployerNamePattern(form.getEmployerNamePattern());
		payload.setHosting(form.getHosting());
		payload.setDescriptionPattern(form.getDescriptionPattern());
		payload.setPhonePattern(form.getPhonePattern());
		payload.setMailPattern(form.getMailPattern());
		payload.setSalaryLow(form.getSalaryLow());
	}

	@Nonnull
	@Override
	protected VacancySearchRequestPayload createPayload() {
		return new VacancySearchRequestPayload();
	}
}
