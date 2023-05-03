/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;
import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Transforms {@link PhoneForm} into
 * {@link PhoneInfo} object.
 * Created by mchys on 17.03.2018.
 */
@Component
class PhoneFormFactory extends GenericEntityFormFactory<Long, PhoneInfo, PhoneForm> {
	@Resource(name = "phoneEntityDataService")
	private EntityDataService<PhoneInfo, Long> entityDataService;

	@Override
	protected EntityDataService<PhoneInfo, Long> getDataService() {
		return entityDataService;
	}


	@Nonnull
	@Override
	public PhoneForm formFromEntity(@Nonnull PhoneInfo entity) {
		final PhoneForm phoneForm = new PhoneForm();
		phoneForm.setId(entity.getId());
		phoneForm.setPhoneNumber(entity.getPhoneNumber());
		phoneForm.setDescription(entity.getDescription());
		return phoneForm;
	}

	@Nonnull
	@Override
	public PhoneForm createNewFormInstance() {
		return new PhoneForm();
	}

}
