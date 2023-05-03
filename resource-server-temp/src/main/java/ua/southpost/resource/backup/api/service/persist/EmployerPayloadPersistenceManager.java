/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EmployerRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Resolves {@link EmployerPayload}
 * to {@link Employer}
 * Created by mchys on 17.03.2018.
 */
@Component
@NotFoundMessage(key = NotFoundException.ERR_EMPLOYER_NOT_FOUND_BY_ID)
class EmployerPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Employer, EmployerPayload, EmployerRepository> {
	@Resource
	private EmployerRepository repository;
	@Resource
	private SettlementPayloadPersistenceManager settlementPersistenceManager;
	@Resource
	private ContactPayloadPersistenceManager contactPersistenceManager;

	@Override
	protected EmployerRepository getRepository() {
		return repository;
	}

	@Override
	protected Employer createEntityInstance() {
		return new Employer();
	}

	@Override
	protected void populateEntity(EmployerPayload payload, Employer entity, boolean merge, User modifiedBy) {
		entity.setDisplayName(payload.getName());
		entity.setSearchValue(payload.getName());
		entity.setWebsite(isEmpty(payload.getWebsite()) ? null : payload.getWebsite());
		//settlement
		entity.setSettlement(settlementPersistenceManager.persist(payload.getSettlement(), merge, modifiedBy));
		//contact
		contactPersistenceManager.populateEntity(payload.getContact(), entity, merge, modifiedBy);
		entity.setModifiedBy(modifiedBy);
		entity.setModificationTime(new Date());
	}

	@Override
	protected Optional<Employer> findUnique(Employer entity) {
		return repository.findByName(defaultIfBlank(entity.getSearchValue(), entity.getDisplayName()));
	}

	@Nonnull
	@Override
	protected Employer merge(@Nonnull Employer from, @Nonnull Employer to) {
		to.setDisplayName(from.getDisplayName());
		to.setSearchValue(from.getSearchValue());
		to.setWebsite(from.getWebsite());
		to.setSettlement(settlementPersistenceManager.merge(from.getSettlement(), to.getSettlement()));
		contactPersistenceManager.merge(from, to);
		to.setModifiedBy(from.getModifiedBy());
		to.setModificationTime(from.getModificationTime());
		return to;
	}
}
