/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.PhonePayload;
import ua.southpost.resource.backup.web.model.forms.entity.ContactForm;
import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Verifies that there aren't repetitions among items of the {@link ContactForm#getPhones()}
 * property value.
 * Created by mchys on 15.09.2018.
 */
class OrderedPhoneSetValidator implements ConstraintValidator<OrderedPhoneSet, List<PhonePayload>> {

	public void initialize(OrderedPhoneSet constraint) {
	}

	public boolean isValid(List<PhonePayload> payloadList, ConstraintValidatorContext context) {
		List<String> validPhoneNumbers = Optional.ofNullable(payloadList).orElse(Collections.emptyList())
				.stream()
				.map(PhonePayload::getPhoneNumber)
				.filter(PhoneForm::validFormat)
				.collect(Collectors.toList());
		return validPhoneNumbers.stream()
				.map(PhoneForm::searchFromValid)
				.collect(Collectors.toSet()).size() == validPhoneNumbers.size();
	}
}
