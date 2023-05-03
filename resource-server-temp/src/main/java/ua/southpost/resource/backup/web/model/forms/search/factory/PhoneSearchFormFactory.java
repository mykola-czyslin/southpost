package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.PhoneSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class PhoneSearchFormFactory implements SearchFormFactory<PhoneSearchForm> {
	@Nonnull
	@Override
	public PhoneSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new PhoneSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
