/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.service.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.service.NotFoundException.ERR_ENTITY_NOT_FOUND_BY_ID;

/**
 * Performs transformation between descendants of {@link EntityPayload}
 * and respective entity class.
 * Created by mchys on 17.03.2018.
 */
abstract class AbstractEntityPayloadPersistenceManager<I, E extends Identity<I>, S extends EntityPayload<I>, D extends CrudRepository<E, I>> implements EntityPayloadPersistenceManager<I, E, S> {

	protected abstract D getRepository();

	protected abstract E createEntityInstance();

	protected abstract void populateEntity(S payload, E entity, boolean merge, User modifiedBy);

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public E persist(@Valid S payload, boolean merge, User modifiedBy) {
		final String messageKey = getNotFoundMessageKey();
		final D entityDAO = getRepository();
		return ofNullable(payload).map(f -> mapToEntity(payload, messageKey, entityDAO, merge, modifiedBy))
				.orElse(null);
	}

	private String getNotFoundMessageKey() {
		return ofNullable(getClass().getAnnotation(NotFoundMessage.class))
					.map(NotFoundMessage::key)
					.orElse(ERR_ENTITY_NOT_FOUND_BY_ID);
	}

	@Override
	public E remove(I id) {
		final String messageKey = getNotFoundMessageKey();
		final D entityDAO = getRepository();
		final E entity = entityDAO.findById(id)
				.orElseThrow(() -> new NotFoundException(messageKey, id));
		entityDAO.delete(entity);
		return entity;
	}

	private E mapToEntity(S payload, String messageKey, D repository, boolean merge, User modifiedBy) {
		return ofNullable(payload.getId()).map(repository::findById)
				.map(o -> o.orElseThrow(() -> new NotFoundException(messageKey, payload.getId())))
				.map(e -> merge ? persist(merge(prepareEntity(payload, true, modifiedBy), e)) : e)
				.orElseGet(() -> createEntity(payload, merge, modifiedBy));
	}


	private E createEntity(S payload, boolean merge, User modifiedBy) {
		final E entity = prepareEntity(payload, merge, modifiedBy);
		logger.debug("Create Entity. Prepared: {}", entity);
		return Optional.ofNullable(entity.getId())
				.map(id -> getRepository().findById(id))
				.filter(Optional::isPresent)
				.orElseGet(() -> findUnique(entity))
				.map(e -> conditionalUpdate(entity, e, merge))
				.orElseGet(() -> persist(entity));
	}

	protected abstract Optional<E> findUnique(E entity);

	private E prepareEntity(S payload, boolean merge, User modifiedBy) {
		final E entity = createEntityInstance();
		populateEntity(payload, entity, merge, modifiedBy);
		return entity;
	}

	private E conditionalUpdate(@Nonnull E translated, @Nonnull E persistent, boolean merge) {
		if (merge) {
			return persist(merge(translated, persistent));
		} else {
			return persistent;
		}
	}

	@Nonnull
	protected abstract E merge(@Nonnull E from, @Nonnull E to);

	private E persist(E entity) {
		logger.debug("Going to persist {} entity:\n{}", entity.getClass().getSimpleName(), entity);
		getRepository().save(entity);
		logger.debug("Entity {} saved:\n{}", entity.getClass().getSimpleName(), entity);
		return entity;
	}

}
