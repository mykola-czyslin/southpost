package ua.southpost.resource.backup.web.model.forms.search.factory;

import ua.southpost.resource.backup.web.model.forms.search.PagedSearchForm;
import ua.southpost.resource.commons.service.EntityGridSettings;

import javax.annotation.Nonnull;

public interface SearchFormFactory<F extends PagedSearchForm> {
	@Nonnull
	F create(@Nonnull EntityGridSettings entityGridSettings);
}
