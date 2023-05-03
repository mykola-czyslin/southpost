/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;

/**
 * Maps {@link Location} entity into {@link LocationInfo}
 * data transfer object.
 * Created by mchys on 25.08.2018.
 */
@Component
public class LocationInfoMapper implements LocalizedEntityMapper<Location, LocationInfo, Long> {
	@Resource
	private StreetInfoMapper streetInfoMapper;
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Nonnull
	@Override
	public LocationInfo map(@Nonnull Location location, @Nonnull Locale locale) {
		final LocationInfo locationInfo = new LocationInfo();
		locationInfo.setId(location.getId());
		locationInfo.setStreet(streetInfoMapper.map(location.getStreet(), locale));
		locationInfo.setPostalCode(location.getPostalCode());
		locationInfo.setStreetNumber(location.getStreetNumber());
		locationInfo.setBlockNumber(location.getBlockNumber());
		locationInfo.setRoomNumber(location.getRoomNumber());
		locationInfo.setTextValue(
				messageSource.getMessage(
						"lookup-info.text.location",
						new Object[]{
								locationInfo.getStreet().getTextValue(),
								location.getStreetNumber(),
								location.getBlockNumber(),
								location.getRoomNumber(),
								locationInfo.getPostalCode()
						},
						locale
				)
		);
		return locationInfo;
	}
}
