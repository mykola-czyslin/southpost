/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.VacancyPayload;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.data.repo.VacancyRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * Transforms instance of {@link VacancyPayload} into (persistent)
 * entity of the {@link Vacancy} type.
 * Created by mchys on 17.03.2018.
 */
@Component
class VacancyPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Vacancy, VacancyPayload, VacancyRepository> {
	@Resource
	private VacancyRepository repository;
	@Resource
	private EmployerPayloadPersistenceManager employerPersistenceManager;

	@Override
	protected VacancyRepository getRepository() {
		return repository;
	}

	@Override
	protected Vacancy createEntityInstance() {
		return new Vacancy();
	}

	@Override
	protected void populateEntity(VacancyPayload payload, Vacancy entity, boolean merge, User modifiedBy) {
		entity.setSummary(payload.getSummary());
		entity.setSearchValue(payload.getSummary());
		entity.setDescription(payload.getDescription());
		entity.setSalaryLow(payload.getSalaryLow());
		entity.setSalaryHigh(payload.getSalaryHigh());
		entity.setHostingAvailable(payload.isHosting() ? NoYes.YES : NoYes.NO);
		entity.setModificationTime(new Date());
		entity.setModifiedBy(modifiedBy);
		entity.setEmployer(employerPersistenceManager.persist(payload.getEmployer(), merge, modifiedBy));
	}

	@Override
	protected Optional<Vacancy> findUnique(Vacancy entity) {
		return repository.findUnique(entity.getEmployer().getId(), entity.getSearchValue());
	}


	@Nonnull
	@Override
	protected Vacancy merge(@Nonnull Vacancy from, @Nonnull Vacancy to) {
		//empty employer when updated is empty
		logger.debug("Going to merge from \n\t{}\n to\n\t{}", from, to);
		employerPersistenceManager.merge(from.getEmployer(), to.getEmployer());
		to.setSummary(from.getSummary());
		to.setSearchValue(from.getSearchValue());
		to.setDescription(from.getDescription());
		to.setSalaryLow(from.getSalaryLow());
		to.setSalaryHigh(from.getSalaryHigh());
		to.setHostingAvailable(from.getHostingAvailable());
		to.setModifiedBy(from.getModifiedBy());
		to.setModificationTime(from.getModificationTime());
		return to;
	}
}
