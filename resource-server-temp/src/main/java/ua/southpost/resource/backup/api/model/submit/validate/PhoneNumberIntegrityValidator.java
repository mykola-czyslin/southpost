/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.data.repo.PhoneRepository;
import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;
import ua.southpost.resource.backup.data.model.Phone;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Verifies that there isn't any other {@link Phone} instance
 * with the same value of the "phoneNumber" property.
 * Created by mchys on 15.09.2018.
 */
class PhoneNumberIntegrityValidator implements ConstraintValidator<PhoneNumberIntegrity, PhonePayload> {
	@Resource
	private PhoneRepository phoneDAO;

	public void initialize(PhoneNumberIntegrity constraint) {
	}

	public boolean isValid(PhonePayload payload, ConstraintValidatorContext context) {
		return payload.getId() == null || noOtherButSame(payload);
	}

	boolean noOtherButSame(PhonePayload payload) {
		return !(PhoneForm.validFormat(payload.getPhoneNumber())
				&& phoneDAO.findByNumber(PhoneForm.searchFromValid(payload.getPhoneNumber()))
				.filter(ph -> !Objects.equals(ph.getId(), payload.getId())).isPresent());
	}
}
