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

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Verifies whether there is any other {@link Employer}
 * with the same non-blank value of the {@link Employer#getWebsite()} property.
 * Created by mchys on 15.09.2018.
 */
class EmployerWebsiteIntegrityValidator implements ConstraintValidator<EmployerWebsiteIntegrity, EmployerPayload> {
	@Resource
	private EmployerRepository repository;

	public void initialize(EmployerWebsiteIntegrity constraint) {
	}

	public boolean isValid(EmployerPayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || isBlank(payload.getWebsite()) || noOtherButSameWebsite(payload);
	}

	boolean noOtherButSameWebsite(EmployerPayload payload) {
		return !repository.findByWebSite(payload.getWebsite()).filter(e -> !Objects.equals(e.getId(), payload.getId())).isPresent();
	}
}
