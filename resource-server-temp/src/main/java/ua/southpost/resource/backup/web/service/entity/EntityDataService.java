package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.commons.model.dto.EntityInfo;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Optional;

public interface EntityDataService<T extends EntityInfo<I>, I> {

	Optional<T> byId(@Nonnull I id, Locale locale);
}
