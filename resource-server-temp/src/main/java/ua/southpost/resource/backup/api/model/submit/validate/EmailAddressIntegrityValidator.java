/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Verifies that there isn't any instance of the {@link EmailAddress} entity
 * with the same value of the {@link EmailAddress#getEmailAddress()} property.
 * Created by mchys on 15.09.2018.
 */
class EmailAddressIntegrityValidator implements ConstraintValidator<EmailAddressIntegrity, EmailPayload> {
	@Resource
	private EmailAddressRepository repository;

	public void initialize(EmailAddressIntegrity constraint) {
	}

	public boolean isValid(EmailPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherButSame(payload);
	}

	boolean noOtherButSame(EmailPayload payload) {
		return !repository.findByAddress(payload.getEmailAddress()).filter(ea -> !Objects.equals(ea.getId(), payload.getId())).isPresent();
	}
}
