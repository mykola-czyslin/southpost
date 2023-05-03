/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.validate;

import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.web.model.forms.EditMailAddressUserForm;
import ua.southpost.resource.backup.web.model.forms.entity.UpdateUserForm;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Verifies that provided email value isn't used by another account
 * Created by mykola on 25.10.16.
 */
class EmailNotUsedValidator implements ConstraintValidator<EmailNotUsed, EditMailAddressUserForm> {
	@Resource
	private UserRepository repository;

	@Override
	public void initialize(EmailNotUsed emailNotUsed) {

	}

	@Override
	public boolean isValid(EditMailAddressUserForm userForm, ConstraintValidatorContext constraintValidatorContext) {
		final String email = userForm.getEmail();
		final Long id;
		if (userForm instanceof UpdateUserForm) {
			id = ((UpdateUserForm) userForm).getUserId();
		} else {
			id = null;
		}
		return StringUtils.isNotBlank(email)
				&& repository.findByEmail(email)
				.map(u -> u.getId().equals(id)).orElse(true);
	}
}
