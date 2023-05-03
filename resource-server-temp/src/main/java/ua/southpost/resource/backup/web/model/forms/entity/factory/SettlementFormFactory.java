/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.forms.entity.SettlementForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Performs settlement form transformation
 * Created by mchys on 17.03.2018.
 */
@Component
class SettlementFormFactory extends GenericEntityFormFactory<Long, SettlementInfo, SettlementForm> {
	@Resource(name = "settlementEntityDataService")
	private EntityDataService<SettlementInfo, Long> entityDataService;

	@Override
	protected EntityDataService<SettlementInfo, Long> getDataService() {
		return entityDataService;
	}


	@Nonnull
	@Override
	public SettlementForm formFromEntity(@Nonnull SettlementInfo entity) {
		final SettlementForm settlementForm = new SettlementForm();
		settlementForm.setId(entity.getId());
		settlementForm.setName(entity.getName());
		settlementForm.setKind(entity.getKind().getId());
		settlementForm.setDistrictId(entity.getDistrict().getId());
		settlementForm.setRegionId(entity.getDistrict().getRegion().getId());
		return settlementForm;
	}

	@Nonnull
	@Override
	public SettlementForm createNewFormInstance() {
		return new SettlementForm();
	}

}
