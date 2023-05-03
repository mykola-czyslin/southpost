/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.web.model.dto.address.StreetInfo;
import ua.southpost.resource.backup.web.model.forms.entity.StreetForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Transforms {@link StreetForm} into {@link Street} instance.
 * Created by mchys on 17.03.2018.
 */
@Component
class StreetFormFactory extends GenericEntityFormFactory<Long, StreetInfo, StreetForm> {
	@Resource(name = "streetEntityDataServices")
	private EntityDataService<StreetInfo, Long> entityDataService;
	@Resource
	private SettlementFormFactory settlementFormFactory;

	@Override
	protected EntityDataService<StreetInfo, Long> getDataService() {
		return entityDataService;
	}

	@Nonnull
	@Override
	public StreetForm formFromEntity(@Nonnull StreetInfo entity) {
		final StreetForm streetForm = new StreetForm();
		streetForm.setId(entity.getId());
		streetForm.setName(entity.getName());
		streetForm.setKind(entity.getKind().getId());
		streetForm.setSettlement(settlementFormFactory.formFromEntity(entity.getSettlement()));
		return streetForm;
	}

	@Nonnull
	@Override
	public StreetForm createNewFormInstance() {
		return new StreetForm();
	}

}
