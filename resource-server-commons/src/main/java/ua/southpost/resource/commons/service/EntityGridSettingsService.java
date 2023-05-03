package ua.southpost.resource.commons.service;

import javax.annotation.Nonnull;

//TODO: extend with principal (user) name or id
public interface EntityGridSettingsService {

	void applyGridSettingsFor(@Nonnull Class<?> entityType, @Nonnull EntityGridSettings entityGridSettings);

	@Nonnull
	EntityGridSettings obtainEntityGridSettingsFor(@Nonnull Class<?> entityType);
}
