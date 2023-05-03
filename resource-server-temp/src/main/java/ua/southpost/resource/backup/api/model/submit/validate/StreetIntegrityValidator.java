/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.api.model.submit.StreetPayload;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import ua.southpost.resource.backup.data.model.Street;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static java.util.Optional.ofNullable;

/**
 * Verifies is there any instance of the {@link Street} entity
 * with the same values of the "name", "kind" and "settlement" properties.
 * Created by mchys on 15.09.2018.
 */
class StreetIntegrityValidator implements ConstraintValidator<StreetIntegrity, StreetPayload> {
	@Resource
	private StreetRepository repository;

	public void initialize(StreetIntegrity constraint) {
	}

	public boolean isValid(StreetPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherButSame(payload);
	}

	private boolean noOtherButSame(StreetPayload payload) {
		return !repository.findUnique(payload.getName(), payload.getKind(), ofNullable(payload.getSettlement()).map(SettlementPayload::getId).orElse(Long.MIN_VALUE))
				.filter(e -> !Objects.equals(e.getId(), payload.getId())).isPresent();
	}
}
