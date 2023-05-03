package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.dto.EntityInfo;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface EntitySortOptionsService {
	@Nonnull
	EntitySortOptions getSortOptions(@Nonnull Class<? extends EntityInfo<?>> entityType, @Nonnull Locale locale);
}
