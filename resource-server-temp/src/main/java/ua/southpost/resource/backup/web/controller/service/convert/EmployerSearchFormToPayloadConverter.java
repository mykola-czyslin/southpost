package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.EmployerSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class EmployerSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<EmployerSearchForm, EmployerSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull EmployerSearchForm form, @Nonnull EmployerSearchRequestPayload payload) {
		SettlementSearchFormToPayloadConverter.populatePayloadFieldsFromForm(form, payload);
		payload.setEmployerNamePattern(form.getEmployerNamePattern());
		payload.setWebSiteAddressPattern(form.getWebSiteAddressPattern());
		payload.setWebSiteSignificant(form.isWebSiteSignificant());
	}

	@Nonnull
	@Override
	protected EmployerSearchRequestPayload createPayload() {
		return new EmployerSearchRequestPayload();
	}
}
