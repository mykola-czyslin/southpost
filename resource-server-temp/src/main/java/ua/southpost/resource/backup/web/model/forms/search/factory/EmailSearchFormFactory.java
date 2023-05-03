package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.EmailSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
class EmailSearchFormFactory implements SearchFormFactory<EmailSearchForm> {
	@Nonnull
	@Override
	public EmailSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new EmailSearchForm(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
