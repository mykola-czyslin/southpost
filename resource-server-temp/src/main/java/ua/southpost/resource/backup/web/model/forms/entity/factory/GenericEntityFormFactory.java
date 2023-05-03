package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.commons.model.dto.EntityInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EntityForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Locale;

abstract class GenericEntityFormFactory<I, E extends EntityInfo<I>, S extends EntityForm<I>> implements EntityFormFactory<I, E, S> {

	protected abstract EntityDataService<E, I> getDataService();


	@Override
	@Nonnull
	@Transactional
	public S formByEntityId(I entityId, Locale locale) {
		return getDataService().byId(entityId, locale).map(this::formFromEntity).orElseGet(this::createNewFormInstance);
	}

}