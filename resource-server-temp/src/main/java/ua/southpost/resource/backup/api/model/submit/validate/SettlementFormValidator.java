/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.SettlementPayload;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
import ua.southpost.resource.backup.web.model.forms.entity.SettlementForm;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates instance of {@link SettlementForm}.
 * Created by mchys on 20.03.2018.
 */
class SettlementFormValidator implements ConstraintValidator<SettlementEntity, SettlementPayload> {
	@Resource
	private SettlementRepository repository;

	@Override
	public void initialize(SettlementEntity constraintAnnotation) {

	}

	@Override
	public boolean isValid(SettlementPayload payload, ConstraintValidatorContext context) {
		return (payload.getId() == null || repository.findById(payload.getId()).isPresent());
	}
}
