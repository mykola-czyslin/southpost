package ua.southpost.resource.commons.service;

import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.commons.model.dto.EntityInfo;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface LocalizedEntityMapper<E extends Identity<I>, S extends EntityInfo<I>, I> {
	@Nonnull
	S map(@Nonnull E entity, @Nonnull Locale locale);
}
