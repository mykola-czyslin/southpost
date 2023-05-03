/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
import ua.southpost.resource.backup.data.model.Settlement;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static java.util.Optional.ofNullable;

/**
 * Verifies that there isn't any instance of {@link Settlement} with the same
 * values of the "name", "kind" and "district" properties.
 * Created by mchys on 15.09.2018.
 */
class SettlementIntegrityValidator implements ConstraintValidator<SettlementIntegrity, SettlementPayload> {
	@Resource
	private SettlementRepository settlementDAO;

	public void initialize(SettlementIntegrity constraint) {
	}

	public boolean isValid(SettlementPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherButSame(payload);
	}

	private boolean noOtherButSame(SettlementPayload payload) {
		return !settlementDAO.findUnique(ofNullable(payload.getDistrictId()).orElse(Long.MIN_VALUE), payload.getKind(), payload.getName())
				.filter(e -> !Objects.equals(e.getId(), payload.getId())).isPresent();
	}
}
