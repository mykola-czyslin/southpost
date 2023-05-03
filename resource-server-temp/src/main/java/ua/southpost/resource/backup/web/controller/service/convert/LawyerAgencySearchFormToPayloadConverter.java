package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.LawyerAgencySearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.LawyerAgencySearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class LawyerAgencySearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<LawyerAgencySearchForm, LawyerAgencySearchRequestPayload> {
	@Override
	protected void copySearchCriteriaToPayload(@Nonnull LawyerAgencySearchForm form, @Nonnull LawyerAgencySearchRequestPayload payload) {
		SettlementSearchFormToPayloadConverter.populatePayloadFieldsFromForm(form, payload);
		payload.setAgencyNamePattern(form.getNamePattern());
		payload.setWebSitePattern(form.getWebSitePattern());
		payload.setCases(form.getLawCaseSet());
	}

	@Nonnull
	@Override
	protected LawyerAgencySearchRequestPayload createPayload() {
		return new LawyerAgencySearchRequestPayload();
	}
}
