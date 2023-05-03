/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.VacancyPayload;
import ua.southpost.resource.backup.data.repo.VacancyRepository;
import ua.southpost.resource.backup.web.model.forms.entity.VacancyForm;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validates {@link VacancyForm}.
 * Created by mchys on 24.03.2018.
 */
class VacancyEntityValidator implements ConstraintValidator<VacancyEntity, VacancyPayload> {
	@Resource
	private VacancyRepository repository;

	@Override
	public void initialize(VacancyEntity constraintAnnotation) {

	}

	@Override
	public boolean isValid(VacancyPayload payload, ConstraintValidatorContext context) {
		return payload != null && (payload.getId() == null || repository.findById(payload.getId()).isPresent());
	}
}
