/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Maps instance of {@link Phone} into instance of {@link PhoneInfo}.
 * Created by mchys on 25.08.2018.
 */
@Component
public class PhoneInfoMapper implements LocalizedEntityMapper<Phone, PhoneInfo, Long> {

	@Nonnull
	@Override
	public PhoneInfo map(@Nonnull Phone phone, @Nonnull Locale locale) {
		PhoneInfo phoneInfo = new PhoneInfo();
		phoneInfo.setId(phone.getId());
		phoneInfo.setPhoneNumber(phone.getDisplayNumber());//
		phoneInfo.setDescription(phone.getDescription());
		phoneInfo.setTextValue(phone.getDisplayNumber() + " - " + phone.getDescription());
		return phoneInfo;
	}
}
