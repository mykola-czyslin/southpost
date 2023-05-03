/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.data.repo.RegionRepository;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Checks whether the value being checked is {@code null} or points on existing region.
 * Created by mchys on 23.03.2018.
 */
class RegionIdValidator implements ConstraintValidator<RegionId, String> {
	@Resource
	private RegionRepository regionDAO;

	@Override
	public void initialize(RegionId constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return isBlank(value) || regionDAO.findById(value).isPresent();
	}
}
