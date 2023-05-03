package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.LocationSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class LocationSearchFormFactory implements SearchFormFactory<LocationSearchForm> {
	@Nonnull
	@Override
	public LocationSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new LocationSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
