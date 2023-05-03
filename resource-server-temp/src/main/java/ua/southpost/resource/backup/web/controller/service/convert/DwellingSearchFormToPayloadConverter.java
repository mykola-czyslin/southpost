package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.DwellingSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class DwellingSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<DwellingSearchForm, DwellingSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull DwellingSearchForm form, @Nonnull DwellingSearchRequestPayload payload) {
		SettlementSearchFormToPayloadConverter.populatePayloadFieldsFromForm(form, payload);
		payload.setSettlementAreaPattern(form.getSettlementAreaPattern());
		payload.setDwellingKind(form.getDwellingKind());
		payload.setNumberOfRoomsFrom(form.getNumberOfRoomsFrom());
		payload.setNumberOfRoomsTo(form.getNumberOfRoomsTo());
		payload.setTotalAreaFrom(form.getTotalAreaFrom());
		payload.setTotalAreaTo(form.getTotalAreaTo());
		payload.setLivingAreaFrom(form.getLivingAreaFrom());
		payload.setLivingAreaTo(form.getLivingAreaTo());
		payload.setPriceFrom(form.getPriceFrom());
		payload.setBillingPeriod(form.getBillingPeriod());
		payload.setPriceTo(form.getPriceTo());
		payload.setContactEmailPattern(form.getContactEmailPattern());
		payload.setContactPhonePattern(form.getContactPhonePattern());
	}

	@Nonnull
	@Override
	protected DwellingSearchRequestPayload createPayload() {
		return new DwellingSearchRequestPayload();
	}
}
