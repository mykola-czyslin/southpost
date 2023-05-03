/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Verifies whether valid email form was provided and populates id when possible.
 * Created by mchys on 17.03.2018.
 */
class EmailEntityValidator implements ConstraintValidator<EmailEntity, EmailPayload> {
	@Resource
	private EmailAddressRepository repository;

	public void initialize(EmailEntity constraint) {
	}

	public boolean isValid(EmailPayload payload, ConstraintValidatorContext context) {
		return (payload.getId() != null && repository.findById(payload.getId()).isPresent())
				|| isNotBlank(payload.getEmailAddress());
	}
}
