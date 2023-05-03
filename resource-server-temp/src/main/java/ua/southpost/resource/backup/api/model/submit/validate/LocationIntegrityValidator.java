/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.LocationPayload;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.repo.LocationRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static java.util.Optional.ofNullable;

/**
 * Verifies that there is no another instance of the {@link LocationEntity} with the same values
 * of the "street", "streetNumber", "blockNumber" and "roomNumber" properties.
 * Created by mchys on 15.09.2018.
 */
class LocationIntegrityValidator implements ConstraintValidator<LocationIntegrity, LocationPayload> {
	@Resource
	private LocationRepository repository;

	public void initialize(LocationIntegrity constraint) {
	}

	public boolean isValid(LocationPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherbutSame(payload);
	}

	boolean noOtherbutSame(LocationPayload payload) {
		return !repository.findByIdentity(
				ofNullable(payload.getStreet()).map(StreetPayload::getId).orElse(Long.MIN_VALUE),
				payload.getStreetNumber(),
				payload.getBlockNumber(),
				payload.getRoomNumber()
		)
		.filter(e -> !Objects.equals(e.getId(), payload.getId())).isPresent();
	}
}
