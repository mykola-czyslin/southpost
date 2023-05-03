/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.data.model.District;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that value validated is either {@code null} or points onto the existing
 * {@link District}.
 * Created by mchys on 22.03.2018.
 */
class DistrictIdValidator implements ConstraintValidator<DistrictId, Long> {
	@Resource
	private DistrictRepository repository;

	@Override
	public void initialize(DistrictId constraintAnnotation) {

	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		return value == null || value < 0L || repository.findById(value).isPresent();
	}
}
