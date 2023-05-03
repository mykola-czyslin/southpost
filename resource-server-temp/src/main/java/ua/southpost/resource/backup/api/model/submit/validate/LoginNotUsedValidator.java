/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.data.repo.UserRepository;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies that login provided isn't already used.
 * Created by mchys on 06.03.2018.
 */
class LoginNotUsedValidator implements ConstraintValidator<LoginNotUsed, String> {
	@Resource
	private UserRepository repository;

	@Override
	public void initialize(LoginNotUsed constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(value) && !repository.findByLogin(value).isPresent();
	}
}
