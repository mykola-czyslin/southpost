/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.dwelling.DwellingInfo;
import ua.southpost.resource.backup.web.model.forms.entity.DwellingForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Performs transformations from {@link DwellingForm}
 * to {@link DwellingInfo} entity and vice versa.
 * Created by mchys on 21.04.2018.
 */
@Component
class DwellingFormFactory extends GenericEntityFormFactory<Long, DwellingInfo, DwellingForm> {
	@Resource(name = "dwellingEntityDataService")
	private EntityDataService<DwellingInfo, Long> entityDataService;
	@Resource
	private SettlementFormFactory settlementFormFactory;
	@Resource
	private ContactFormFactory contactFormFactory;

	@Override
	protected EntityDataService<DwellingInfo, Long> getDataService() {
		return entityDataService;
	}


	@Nonnull
	@Override
	public DwellingForm formFromEntity(@Nonnull DwellingInfo entity) {
		final DwellingForm dwellingForm = new DwellingForm();
		dwellingForm.setId(entity.getId());
		dwellingForm.setSettlement(settlementFormFactory.formFromEntity(entity.getSettlement()));
		dwellingForm.setSettlementArea(entity.getSettlementArea());
		dwellingForm.setDwellingKind(entity.getKind().getId());
		dwellingForm.setNumberOfRooms(entity.getNumberOfRooms());
		dwellingForm.setTotalArea(entity.getTotalArea());
		dwellingForm.setLivingArea(entity.getLivingArea());
		dwellingForm.setContact(contactFormFactory.formFromEntity(entity));
		dwellingForm.setPrice(entity.getPrice());
		dwellingForm.setBillingPeriod(entity.getBillingPeriod().getId());
		dwellingForm.setDescription(entity.getDescription());
		return dwellingForm;
	}

	@Nonnull
	@Override
	public DwellingForm createNewFormInstance() {
		return new DwellingForm();
	}

}
