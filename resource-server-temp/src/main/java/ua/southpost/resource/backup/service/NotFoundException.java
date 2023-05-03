/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mchys on 08.03.2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ServiceException {
	public static final String ERR_ENTITY_NOT_FOUND_BY_ID = "err.entity.not.found.by.id";
	public static final String ERR_USER_NOT_FOUND_BY_EMAIL = "err.user.not.found.by.email";
	public static final String ERR_USER_NOT_FOUND_BY_LOGIN = "err.user.not.found.by.login";
	public static final String ERR_USER_NOT_FOUND_BY_ID = "err.user.not.found.by.id";
	public static final String ERR_EMPLOYER_NOT_FOUND_BY_ID = "err.employer.not.found.by.id";
	public static final String ERR_CONTACT_NOT_FOUND_BY_ID = "err.contact.not.found.by.id";
	public static final String ERR_LOCATION_NOT_FOUND_BY_ID = "err.location.not.found.by.id";
	public static final String ERR_STREET_NOT_FOUND_BY_ID = "err.street.not.found.by.id";
	public static final String ERR_SETTLEMENT_NOT_FOUND_BY_ID = "err.settlement.not.found.by.id";
	public static final String ERR_DISTRICT_NOT_FOUND_BY_ID = "err.district.not.found.by.id";
	public static final String ERR_REGION_NOT_FOUND_BY_ID = "err.region.not.found.by.id";
	public static final String ERR_PHONE_NOT_FOUND_BY_ID = "err.phone.not.found.by.id";
	public static final String ERR_EMAIL_NOT_FOUND_BY_ID = "err.email.not.found.by.id";
	public static final String ERR_VACANCY_NOT_FOUND_BY_ID = "err.vacancy.not.found.by.id";
	public static final String ERR_DWELLING_NOT_FOUND_BY_ID = "err.dwelling.not.found.by.id";
	public static final String ERR_MED_SERVICE_NOT_FOUND = "err.medical.service.not.found.by.id";
	public static final String ERR_CLINIC_NOT_FOUND = "err.clinic.not.found.by.id";
	public static final String ERR_LAWYER_AGENCY_NOT_FOUND_BY_ID = "err.lawyer.agency.not.found.by.id";

	private final String messageKey;
	private final Object[] arguments;

	public NotFoundException(String messageKey, Object... arguments) {
		super();
		this.messageKey = messageKey;
		this.arguments = arguments;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getArguments() {
		return arguments;
	}
}
