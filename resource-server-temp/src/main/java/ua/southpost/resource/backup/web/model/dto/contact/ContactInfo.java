/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.contact;

import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Data transfer object that transfers contact info.
 * Created by mchys on 25.08.2018.
 */
public interface ContactInfo {
	LocationInfo getLocation();

	void  setLocation(LocationInfo location);

	List<PhoneInfo> getPhones();

	void setPhones(List<PhoneInfo> phones);

	List<EmailInfo> getEmails();

	void setEmails(List<EmailInfo> emails);

	default String getPhonesCSV() {
		return csv(getPhones(), PhoneInfo::getPhoneNumber);
	}

	default String getEmailsCSV() {
		return csv(getEmails(), EmailInfo::getEmailAddress);
	}

	static <T> String csv(@Nullable List<T> data, @Nonnull Function<T, String> stringValueMapper) {
		return Optional.ofNullable(data)
				.orElseGet(Collections::emptyList)
				.stream()
				.map(stringValueMapper)
				.reduce("", (a,n) -> a + (isNotBlank(a) ? ", " : "") + n);
	}
}
