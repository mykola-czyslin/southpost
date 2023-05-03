/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.forms.entity.EmailAddressForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Transforms from {@link EmailInfo}
 * Created by mchys on 17.03.2018.
 */
@Component
class EmailAddressFormFactory extends GenericEntityFormFactory<Long, EmailInfo, EmailAddressForm> {

	@Resource(name = "emailEntityDataService")
	private EntityDataService<EmailInfo,Long> entityDataService;

	@Override
	protected EntityDataService<EmailInfo,Long> getDataService() {
		return entityDataService;
	}


	@Nonnull
	@Override
	public EmailAddressForm formFromEntity(@Nonnull EmailInfo entity) {
		final EmailAddressForm emailAddressForm = new EmailAddressForm();
		emailAddressForm.setId(entity.getId());
		emailAddressForm.setEmailAddress(entity.getEmailAddress());
		emailAddressForm.setDescription(entity.getDescription());
		return emailAddressForm;
	}

	@Nonnull
	@Override
	public EmailAddressForm createNewFormInstance() {
		return new EmailAddressForm();
	}

}
