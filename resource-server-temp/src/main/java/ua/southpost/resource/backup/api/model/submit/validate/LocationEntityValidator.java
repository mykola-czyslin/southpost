/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.backup.data.repo.LocationRepository;
import ua.southpost.resource.backup.web.model.forms.entity.LocationForm;
import ua.southpost.resource.backup.data.model.Location;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies whether the {@link LocationForm#getId()} is properly defined or form contains enough information
 * to find {@link Location} entity or create it.
 * Created by mchys on 22.03.2018.
 */
class LocationEntityValidator implements ConstraintValidator<LocationEntity, LocationPayload> {
	@Resource
	private LocationRepository locationDAO;

	@Override
	public void initialize(LocationEntity constraintAnnotation) {

	}

	@Override
	public boolean isValid(LocationPayload payload, ConstraintValidatorContext context) {
		return (payload.getId() == null || locationDAO.findById(payload.getId()).isPresent());
	}

}
