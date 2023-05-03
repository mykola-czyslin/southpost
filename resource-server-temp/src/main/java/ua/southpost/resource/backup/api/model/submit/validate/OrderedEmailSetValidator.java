/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Verifies that provided form list doesn't contain any email address repetition.
 * Created by mchys on 15.09.2018.
 */
class OrderedEmailSetValidator implements ConstraintValidator<OrderedEmailSet, List<EmailPayload>> {
	public void initialize(OrderedEmailSet constraint) {
	}

	public boolean isValid(List<EmailPayload> payloadList, ConstraintValidatorContext context) {
		return isEmpty(payloadList)
				|| payloadList.stream()
				.map(EmailPayload::getEmailAddress)
				.map(StringUtils::trim)
				.map(StringUtils::lowerCase)
				.collect(Collectors.toSet())
				.size() == payloadList.size();
	}
}
