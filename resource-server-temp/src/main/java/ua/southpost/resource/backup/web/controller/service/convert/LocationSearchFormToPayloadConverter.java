package ua.southpost.resource.backup.web.controller.service.convert;

import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class LocationSearchFormToPayloadConverter extends AbstractSearchFormToPayloadConverter<LocationSearchForm, LocationSearchRequestPayload> {

	@Override
	protected void copySearchCriteriaToPayload(@Nonnull LocationSearchForm form, @Nonnull LocationSearchRequestPayload payload) {
		populatePayloadFromForm(form, payload);
	}

	@Nonnull
	@Override
	protected LocationSearchRequestPayload createPayload() {
		return new LocationSearchRequestPayload();
	}

	static void populatePayloadFromForm(LocationSearchForm form, LocationSearchRequestPayload payload) {
		StreetSearchFormToPayloadConverter.populatePayloadFromForm(form, payload);
		payload.setPostalCodePattern(form.getPostalCodePattern());
		payload.setStreetNumberPattern(form.getStreetNumberPattern());
		payload.setBlockNumberPattern(form.getBlockNumberPattern());
		payload.setRoomNumberPattern(form.getRoomNumberPattern());
	}
}
