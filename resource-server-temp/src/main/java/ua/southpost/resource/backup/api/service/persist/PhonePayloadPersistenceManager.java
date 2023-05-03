/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.PhoneRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * Transforms {@link PhoneForm} into
 * {@link Phone} object.
 * Created by mchys on 17.03.2018.
 */
@Component
@NotFoundMessage(key = NotFoundException.ERR_PHONE_NOT_FOUND_BY_ID)
class PhonePayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, Phone, PhonePayload, PhoneRepository> {
	@Resource
	private PhoneRepository repository;

	@Override
	protected PhoneRepository getRepository() {
		return repository;
	}

	@Override
	protected Phone createEntityInstance() {
		return new Phone();
	}

	@Override
	protected void populateEntity(PhonePayload payload, Phone entity, boolean merge, User modifiedBy) {
		entity.setDisplayNumber(PhoneForm.displayFromValid(payload.getPhoneNumber()));
		entity.setSearchNumber(PhoneForm.searchFromValid(payload.getPhoneNumber()));
		entity.setDescription(payload.getDescription());
	}

	@Override
	protected Optional<Phone> findUnique(Phone entity) {
		return repository.findByNumber(entity.getSearchNumber());
	}

	@Nonnull
	@Override
	protected Phone merge(@Nonnull Phone from, @Nonnull Phone to) {
		to.setDisplayNumber(from.getDisplayNumber());
		to.setSearchNumber(from.getSearchNumber());
		to.setDescription(from.getDescription());
		return to;
	}

}
