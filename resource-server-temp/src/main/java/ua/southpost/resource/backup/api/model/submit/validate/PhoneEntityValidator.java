/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.data.repo.PhoneRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies whether the specific entity form is valid:
 * <ul>
 *  <li>when {@code id} is specified then verifies this {@code id} points on existing entity</li>
 *  <li>otherwise verifies that specified phone number is in valid format and tries to find entity
 *  by phone number when the latter succeeds then form's {@code id} property is updated from entity found.</li>
 * </ul>
 * Created by mchys on 17.03.2018.
 */
class PhoneEntityValidator implements ConstraintValidator<PhoneEntity, PhonePayload> {
	@Resource
	private PhoneRepository repository;

	@Override
	public void initialize(PhoneEntity constraintAnnotation) {

	}

	@Override
	public boolean isValid(PhonePayload payload, ConstraintValidatorContext context) {
		return (payload.getId() == null || repository.findById(payload.getId()).isPresent());
	}
}
