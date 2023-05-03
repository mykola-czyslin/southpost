/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.service.persist;

import org.springframework.stereotype.Service;
import ua.southpost.resource.backup.api.model.submit.ContactPayload;
import ua.southpost.resource.commons.model.entity.ContactEntity;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.web.model.forms.entity.ContactForm;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Performs transformation from {@link ContactForm}
 * to {@link ContactEntity}.
 * Created by mchys on 17.03.2018.
 */
@Service
class ContactPayloadPersistenceManager {
	@Resource
	private LocationPayloadPersistenceManager locationPayloadConverter;
	@Resource
	private PhonePayloadPersistenceManager phonePayloadConverter;
	@Resource
	private EmailPayloadPersistenceManager emailPayloadConverter;

	protected void populateEntity(ContactPayload payload, ContactEntity entity, boolean merge, User modifiedBy) {
		entity.setAddress(locationPayloadConverter.persist(payload.getLocation(), merge, modifiedBy));
		entity.setPhones(
				ofNullable(payload.getPhones())
						.orElseGet(Collections::emptyList)
						.stream()
						.map((phonePayload) -> phonePayloadConverter.persist(phonePayload, merge, modifiedBy))
						.collect(Collectors.toList())
		);
		entity.setEmails(
				ofNullable(payload.getEmails())
						.orElseGet(Collections::emptyList)
						.stream()
						.map((emailPayload) -> emailPayloadConverter.persist(emailPayload, merge, modifiedBy))
						.collect(Collectors.toList())
		);
	}


	public ContactEntity merge(@Nonnull ContactEntity from, @Nonnull ContactEntity to) {
		if (to.getLocationAddress() == null || from.getLocationAddress() == null) {
			to.setLocationAddress(from.getLocationAddress());
		} else {
			to.setLocationAddress(locationPayloadConverter.merge(from.getLocationAddress(), to.getLocationAddress()));
		}
		final List<Phone> fromPhones = from.getPhones();
		if (isEmpty(to.getPhones()) || isEmpty(fromPhones)) {
			to.setPhones(fromPhones);
		} else {
			final List<Phone> toPhones = to.getPhones();
			to.setPhones(mergePhoneList(fromPhones, toPhones));
		}
		final List<EmailAddress> fromEmails = from.getEmails();
		if (isEmpty(to.getEmails()) || isEmpty(fromEmails)) {
			to.setEmails(fromEmails);
		} else {
			to.setEmails(mergeEmailList(fromEmails, to.getEmails()));
		}
		return to;
	}

	private List<Phone> mergePhoneList(List<Phone> fromPhones, List<Phone> toPhones) {
		return fromPhones
				.stream()
				.map(ph -> mergePhoneToList(ph, toPhones))
				.collect(Collectors.toList());
	}

	private Phone mergePhoneToList(Phone ph, List<Phone> toPhones) {
		return toPhones
				.stream()
				.filter(ph2 -> Objects.equals(ph2.getId(), ph.getId()))
				.findFirst()
				.map(ph2 -> phonePayloadConverter.merge(ph, ph2))
				.orElse(ph);
	}

	private List<EmailAddress> mergeEmailList(List<EmailAddress> fromEmails, List<EmailAddress> toEmails) {
		return fromEmails
				.stream()
				.map(e -> mergeEmailAddressToList(e, toEmails))
				.collect(Collectors.toList());
	}

	private EmailAddress mergeEmailAddressToList(EmailAddress fromEmail, List<EmailAddress> toEmails) {
		return toEmails.stream()
				.filter(e2 -> Objects.equals(e2.getId(), fromEmail.getId()))
				.findFirst()
				.map(e2 -> emailPayloadConverter.merge(fromEmail, e2))
				.orElse(fromEmail);
	}

}
