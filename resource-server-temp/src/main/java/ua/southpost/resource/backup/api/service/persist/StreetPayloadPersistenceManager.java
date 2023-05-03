/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.model.forms.entity.StreetForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * Transforms {@link StreetForm} into {@link Street} instance.
 * Created by mchys on 17.03.2018.
 */
@Component
@NotFoundMessage(key = NotFoundException.ERR_STREET_NOT_FOUND_BY_ID)
class StreetPayloadPersistenceManager
		extends AbstractEntityPayloadPersistenceManager<Long, Street, StreetPayload, StreetRepository> {
	@Resource
	private StreetRepository repository;
	@Resource
	private SettlementPayloadPersistenceManager settlementPersistenceManager;

	@Override
	protected StreetRepository getRepository() {
		return repository;
	}

	@Override
	protected Street createEntityInstance() {
		return new Street();
	}

	@Override
	protected void populateEntity(StreetPayload payload, Street entity, boolean merge, User modifiedBy) {
		entity.setSettlement(settlementPersistenceManager.persist(payload.getSettlement(), merge, modifiedBy));
		entity.setKind(payload.getKind());
		entity.setDisplayName(payload.getName());
		entity.setSearchValue(payload.getName());
	}

	@Override
	protected Optional<Street> findUnique(Street entity) {
		return repository.findUnique(entity.getSearchValue(), entity.getKind(), entity.getSettlement().getId());
	}


	@Nonnull
	@Override
	protected Street merge(@Nonnull Street from, @Nonnull Street to) {
		to.setSettlement(settlementPersistenceManager.merge(from.getSettlement(), to.getSettlement()));
		to.setKind(from.getKind());
		to.setDisplayName(from.getDisplayName());
		to.setSearchValue(from.getSearchValue());
		return to;
	}
}
