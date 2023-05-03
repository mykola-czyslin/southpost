/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.service.ServiceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Phone form.
 * Created by mchys on 17.03.2018.
 */
public class PhoneForm implements EntityForm<Long> {
	@SuppressWarnings("WeakerAccess")
	public static final String VALID_PHONE_NUMBER_PATTERN = "^\\+([0-9]{1,3})\\s*(\\(([0-9]{2,3})\\)|([0-9]{2,3}))\\s*([0-9]{3})\\s*-?\\s*([0-9]{2})\\s*-?\\s*([0-9]{2})$";
	private static final Pattern VALID_PHONE_NUMBER_COMPILED_PATTERN = Pattern.compile(PhoneForm.VALID_PHONE_NUMBER_PATTERN);
	private static final Pattern PHONE_NUMBER_COMPILED_PATTERN = Pattern.compile("^\\+([0-9]{1,2})\\(([0-9]{3})\\)([0-9]{3})-([0-9]{2})-([0-9]{2})$");
	private static final String SEARCH_FROM_DISPLAY_REPLACEMENT_STRING = "+$1$2$3$4$5";
	private static final String SEARCH_FROM_VALID_REPLACEMENT_STRING = "+$1$3$4$5$6$7";

	private Long id;
	private String phoneNumber;
	private String description;

	public static boolean validFormat(String phoneNumber) {
		return VALID_PHONE_NUMBER_COMPILED_PATTERN.matcher(phoneNumber).matches();
	}

	public static String searchFromDisplay(String phoneNumber) {
		Matcher matcher = PHONE_NUMBER_COMPILED_PATTERN.matcher(phoneNumber);
		if (matcher.matches()) {
			return matcher.replaceAll(SEARCH_FROM_DISPLAY_REPLACEMENT_STRING);
		} else {
			throw new ServiceException("err.phone.format.invalid");
		}
	}

	public static String searchFromValid(String phoneNumber) {
		Matcher matcher = VALID_PHONE_NUMBER_COMPILED_PATTERN.matcher(phoneNumber);
		if (matcher.matches()) {
			return matcher.replaceAll(SEARCH_FROM_VALID_REPLACEMENT_STRING);
		} else {
			throw new ServiceException("err.phone.format.invalid");
		}
	}

	public static String displayFromValid(String phoneNumber) {
		String searchNumber = searchFromValid(phoneNumber);
		return searchNumber.substring(0, 3) + "(" + searchNumber.substring(3, 6) + ")"
				+ searchNumber.substring(6, 9) + "-" + searchNumber.substring(9, 11) + "-"
				+ searchNumber.substring(11);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
