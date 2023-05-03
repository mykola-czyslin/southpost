/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.data.repo.EmployerRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks that specified id points on existing employer entity.
 * Created by mchys on 16.03.2018.
 */
class EmployerIdValidator implements ConstraintValidator<EmployerId, Long> {
	@Resource
	private EmployerRepository repository;

	@Override
	public void initialize(EmployerId constraintAnnotation) {

	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		return value == null || repository.findById(value).isPresent();
	}
}
