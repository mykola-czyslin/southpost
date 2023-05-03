package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.UserSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.UserSearchFormImpl;
import ua.southpost.resource.commons.service.EntityGridSettings;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
class UserSearchFormFactory implements SearchFormFactory<UserSearchForm> {
	@Nonnull
	@Override
	public UserSearchForm create(@Nonnull EntityGridSettings entityGridSettings) {
		return new UserSearchFormImpl(1, entityGridSettings.getPageSize(), entityGridSettings.getSortOptions());
	}
}
