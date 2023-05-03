/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.validate;

import ua.southpost.resource.backup.web.security.UserInfo;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates login password.
 * Created by mykola on 26.10.16.
 */
class LoginUserRuleValidator implements ConstraintValidator<LoginUserRule, Object> {
	@Resource(name = "authProvider")
	private AuthenticationProvider authenticationProvider;
	private BeanToPropertyValueTransformer loginValueExtractor;
	private BeanToPropertyValueTransformer passwordValueExtractor;

	@Override
	public void initialize(LoginUserRule loginUserRule) {
		loginValueExtractor = new BeanToPropertyValueTransformer(loginUserRule.loginProperty());
		passwordValueExtractor = new BeanToPropertyValueTransformer(loginUserRule.passwordProperty());
	}

	@Override
	public boolean isValid(Object form, ConstraintValidatorContext constraintValidatorContext) {
		String login = (String) loginValueExtractor.transform(form);
		String password = (String) passwordValueExtractor.transform(form);
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(login);
		userInfo.setPassword(password);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, password);
		Authentication authenticated;
		try {
			authenticated = authenticationProvider.authenticate(authentication);
			return authenticated.isAuthenticated();
		} catch (AuthenticationException e) {
			return false;
		}
	}
}
