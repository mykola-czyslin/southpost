/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * Transforms from {@link EmailPayload}
 * Created by mchys on 17.03.2018.
 */
@Component
@NotFoundMessage(key = NotFoundException.ERR_EMAIL_NOT_FOUND_BY_ID)
class EmailPayloadPersistenceManager extends AbstractEntityPayloadPersistenceManager<Long, EmailAddress, EmailPayload, EmailAddressRepository> {

	@Resource
	private EmailAddressRepository repository;

	@Override
	protected EmailAddressRepository getRepository() {
		return repository;
	}

	@Override
	protected EmailAddress createEntityInstance() {
		return new EmailAddress();
	}

	@Override
	protected void populateEntity(EmailPayload payload, EmailAddress entity, boolean merge, User modifiedBy) {
		entity.setEmailAddress(lowerCase(payload.getEmailAddress()));
		entity.setDescription(payload.getDescription());
	}

	@Override
	protected Optional<EmailAddress> findUnique(EmailAddress entity) {
		return repository.findByAddress(entity.getEmailAddress());
	}

	@Nonnull
	@Override
	protected EmailAddress merge(@Nonnull EmailAddress from, @Nonnull EmailAddress to) {
		to.setEmailAddress(from.getEmailAddress());
		to.setDescription(from.getDescription());
		return to;
	}
}
