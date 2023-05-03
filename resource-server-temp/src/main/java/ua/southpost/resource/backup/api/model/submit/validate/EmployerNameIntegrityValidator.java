/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmployerPayload;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.repo.EmployerRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Checks whether the uniqueness of the {@link Employer#getSearchValue()} will be maintained.
 * Created by mchys on 15.09.2018.
 */
class EmployerNameIntegrityValidator implements ConstraintValidator<EmployerNameIntegrity, EmployerPayload> {
	@Resource
	private EmployerRepository repository;

	public void initialize(EmployerNameIntegrity constraint) {
	}

	public boolean isValid(EmployerPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherButSame(payload);
	}

	boolean noOtherButSame(EmployerPayload payload) {
		return isNotBlank(payload.getName()) && !repository.findByName(payload.getName()).filter(e -> !Objects.equals(e.getId(), payload.getId())).isPresent();
	}
}
