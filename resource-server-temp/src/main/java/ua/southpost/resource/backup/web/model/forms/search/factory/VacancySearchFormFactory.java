package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.VacancySearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class VacancySearchFormFactory implements SearchFormFactory<VacancySearchForm> {
	@Nonnull
	@Override
	public VacancySearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new VacancySearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
