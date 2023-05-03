/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.data.repo.EmployerRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates Employer form.
 * Created by mchys on 20.03.2018.
 */
class EmployerEntityValidator implements ConstraintValidator<EmployerEntity, EmployerPayload> {
	@Resource
	private EmployerRepository repository;

	@Override
	public void initialize(EmployerEntity constraintAnnotation) {
	}

	@Override
	public boolean isValid(EmployerPayload payload, ConstraintValidatorContext context) {
		return (payload.getId() == null || repository.findById(payload.getId()).isPresent());
	}
}
