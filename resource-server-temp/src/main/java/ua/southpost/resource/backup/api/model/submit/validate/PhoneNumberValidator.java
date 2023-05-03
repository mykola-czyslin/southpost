/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Verifies that specified phone number is in valid format.
 * Created by mchys on 17.03.2018.
 */
class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
	@Override
	public void initialize(PhoneNumber constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return isNotBlank(value) && PhoneForm.validFormat(value);
	}
}
