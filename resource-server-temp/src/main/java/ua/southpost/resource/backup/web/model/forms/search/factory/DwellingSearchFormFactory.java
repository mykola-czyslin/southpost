package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.DwellingSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class DwellingSearchFormFactory implements SearchFormFactory<DwellingSearchForm> {
	@Nonnull
	@Override
	public DwellingSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new DwellingSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
