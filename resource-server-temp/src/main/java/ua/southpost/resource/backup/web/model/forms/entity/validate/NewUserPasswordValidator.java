/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.validate;


import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks that password value and re-typed value match
 * Created by mykola on 25.10.16.
 */
class NewUserPasswordValidator implements ConstraintValidator<NewUserPasswordRule, Object> {

	private BeanToPropertyValueTransformer passwordProperty;
	private BeanToPropertyValueTransformer confirmPasswordProperty;

	@Override
	public void initialize(NewUserPasswordRule newUserPasswordRule) {
		passwordProperty = new BeanToPropertyValueTransformer(newUserPasswordRule.passwordProperty());
		confirmPasswordProperty = new BeanToPropertyValueTransformer(newUserPasswordRule.confirmPasswordProperty());
	}

	@Override
	public boolean isValid(Object bean, ConstraintValidatorContext constraintValidatorContext) {
		String password = (String) passwordProperty.transform(bean);
		String confirmPassword = (String) confirmPasswordProperty.transform(bean);
		return StringUtils.isNotBlank(password) && password.equals(confirmPassword);
	}
}
