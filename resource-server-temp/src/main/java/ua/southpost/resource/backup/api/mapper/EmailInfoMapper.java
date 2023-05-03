/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Maps instance of the {@link EmailAddress} into instance of {@link EmailInfo}.
 * Created by mchys on 25.08.2018.
 */
@Component
public class EmailInfoMapper implements LocalizedEntityMapper<EmailAddress, EmailInfo, Long> {

	@Nonnull
	@Override
	public EmailInfo map(@Nonnull EmailAddress emailAddress, @Nonnull Locale locale) {
		EmailInfo emailInfo = new EmailInfo();
		emailInfo.setId(emailAddress.getId());
		emailInfo.setEmailAddress(emailAddress.getEmailAddress());
		emailInfo.setDescription(emailAddress.getDescription());
		emailInfo.setTextValue(emailAddress.getEmailAddress() + " - " + emailAddress.getDescription());
		return emailInfo;
	}
}
