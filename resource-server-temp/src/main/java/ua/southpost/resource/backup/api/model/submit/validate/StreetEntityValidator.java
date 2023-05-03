/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import ua.southpost.resource.backup.web.model.forms.entity.StreetForm;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates instance of the {@link StreetForm}: it should either have valid {@link StreetForm#getId()} set
 * or allow either find {@link Street} entity or create its new instance.
 * Created by mchys on 22.03.2018.
 */
class StreetEntityValidator implements ConstraintValidator<StreetEntity, StreetPayload> {
	@Resource
	private StreetRepository repository;

	@Override
	public void initialize(StreetEntity constraintAnnotation) {

	}

	@Override
	public boolean isValid(StreetPayload payload, ConstraintValidatorContext context) {
		return (payload.getId() == null || repository.findById(payload.getId()).isPresent());
	}
}
