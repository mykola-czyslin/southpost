package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.StreetSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class StreetSearchFormFactory implements SearchFormFactory<StreetSearchForm> {
	@Nonnull
	@Override
	public StreetSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new StreetSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
