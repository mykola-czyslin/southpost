/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity.factory;

import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.forms.entity.LocationForm;
import ua.southpost.resource.backup.web.service.entity.EntityDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * Transforms instance of the {@link LocationForm}
 * into respective instance of the {@link LocationInfo} entity.
 * Created by mchys on 17.03.2018.
 */
@Component
class LocationFormFactory extends GenericEntityFormFactory<Long, LocationInfo, LocationForm> {

	@Resource(name = "locationEntityDataService")
	private EntityDataService<LocationInfo, Long> entityDataService;
	@Resource
	private StreetFormFactory streetFormFactory;

	@Override
	protected EntityDataService<LocationInfo, Long> getDataService() {
		return entityDataService;
	}



	@Nonnull
	@Override
	public LocationForm formFromEntity(@Nonnull LocationInfo entity) {
		final LocationForm locationForm = new LocationForm();
		locationForm.setId(entity.getId());
		locationForm.setRoomNumber(entity.getRoomNumber());
		locationForm.setBlockNumber(entity.getBlockNumber());
		locationForm.setStreetNumber(entity.getStreetNumber());
		locationForm.setPostalCode(entity.getPostalCode());
		locationForm.setStreet(streetFormFactory.formFromEntity(entity.getStreet()));
		return locationForm;
	}

	@Nonnull
	@Override
	public LocationForm createNewFormInstance() {
		return new LocationForm();
	}

}
