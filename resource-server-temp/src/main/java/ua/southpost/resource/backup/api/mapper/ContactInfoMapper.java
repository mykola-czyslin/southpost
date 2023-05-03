/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Maps instance of the {@link ContactEntity} into instance
 * of {@link ContactInfo}.
 * Created by mchys on 25.08.2018.
 */
@Component
class ContactInfoMapper {
	@Resource
	private PhoneInfoMapper phoneInfoMapper;
	@Resource
	private EmailInfoMapper emailInfoMapper;
	@Resource
	private LocationInfoMapper locationInfoMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public void mapContactData(@Nonnull ContactEntity contact, @Nonnull ContactInfo contactInfo, @Nonnull Locale locale) {
		logger.debug("Going to map:\n\tlocation: {};\n\tphones: {};\n\temails: {}", contact.getLocationAddress(), contact.getPhones(), contact.getEmails());
		ofNullable(contact.getLocationAddress()).ifPresent(address -> contactInfo.setLocation(locationInfoMapper.map(address, locale)));
		contactInfo.setPhones(ofNullable(contact.getPhones()).map(phones -> mapPhones(phones, locale)).orElseGet(Collections::emptyList));
		contactInfo.setEmails(ofNullable(contact.getEmails()).map(emails -> mapEmails(emails, locale)).orElseGet(Collections::emptyList));
		logger.debug(
				"Contact entity mapped to:\n\tlocation: {};\n\tphones: {},\n\t\t{};\n\temails: {},\n\t\t{}",
				contactInfo.getLocation(),
				contactInfo.getPhones(), contactInfo.getPhonesCSV(),
				contactInfo.getEmails(), contactInfo.getEmailsCSV()
		);
	}

	@Nonnull
	private List<PhoneInfo> mapPhones(@Nonnull List<Phone> phones, @Nonnull Locale locale) {
		return phones
				.stream()
				.map(p -> phoneInfoMapper.map(p, locale))
				.collect(Collectors.toList());
	}

	@Nonnull
	private List<EmailInfo> mapEmails(@Nonnull List<EmailAddress> emails, @Nonnull Locale locale) {
		return emails
				.stream()
				.map(e -> emailInfoMapper.map(e, locale))
				.collect(Collectors.toList());
	}
}
