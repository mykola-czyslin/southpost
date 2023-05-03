package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.EmployerSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class EmployerSearchFormFactory implements SearchFormFactory<EmployerSearchForm> {
	@Nonnull
	@Override
	public EmployerSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new EmployerSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
