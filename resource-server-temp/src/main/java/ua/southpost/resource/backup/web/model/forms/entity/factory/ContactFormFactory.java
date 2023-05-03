/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.forms.entity.ContactForm;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Performs transformation from {@link ContactForm}
 * to {@link ContactInfo}.
 * Created by mchys on 17.03.2018.
 */
@Component
class ContactFormFactory {
	@Resource
	private LocationFormFactory locationFormFactory;
	@Resource
	private PhoneFormFactory phoneFormFactory;
	@Resource
	private EmailAddressFormFactory emailAddressFormFactory;

	@Nonnull
	public ContactForm formFromEntity(@Nonnull ContactInfo entity) {
		final ContactForm contactForm = new ContactForm();
		contactForm.setLocation(ofNullable(entity.getLocation()).map(locationFormFactory::formFromEntity).orElse(null));
		contactForm.setPhones(
				ofNullable(entity.getPhones())
						.orElseGet(Collections::emptyList)
						.stream()
						.map(phoneFormFactory::formFromEntity)
						.collect(Collectors.toList())
		);
		contactForm.setEmails(
				ofNullable(entity.getEmails())
						.orElseGet(Collections::emptyList)
						.stream()
						.map(emailAddressFormFactory::formFromEntity)
						.collect(Collectors.toList())
		);
		return contactForm;
	}

}
