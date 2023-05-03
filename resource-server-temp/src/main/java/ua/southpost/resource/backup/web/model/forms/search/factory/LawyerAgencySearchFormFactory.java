package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.LawyerAgencySearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class LawyerAgencySearchFormFactory implements SearchFormFactory<LawyerAgencySearchForm> {
	@Nonnull
	@Override
	public LawyerAgencySearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new LawyerAgencySearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
