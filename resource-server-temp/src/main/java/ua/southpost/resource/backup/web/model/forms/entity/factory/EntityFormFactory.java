package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EntityForm;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface EntityFormFactory<I, E extends EntityInfo<I>, S extends EntityForm<I>> {
	@Nonnull
	@Transactional
	S formByEntityId(I entityId, Locale locale);

	@Nonnull
	S createNewFormInstance();

	@Nonnull
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	S formFromEntity(@Nonnull E entity);
}
