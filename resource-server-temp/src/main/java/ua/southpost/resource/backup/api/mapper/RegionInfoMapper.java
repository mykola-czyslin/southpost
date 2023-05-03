/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.web.model.dto.address.RegionInfo;
import ua.southpost.resource.backup.web.model.dto.address.SelectOptionInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps {@link Region} to {@link RegionInfo}.
 * Created by mchys on 18.03.2018.
 */
@Component
public class RegionInfoMapper implements LocalizedEntityMapper<Region, RegionInfo, String> {
	@Nonnull
	@Override
	public RegionInfo map(@Nonnull Region region, @Nonnull Locale locale) {
		RegionInfo regionInfo = new RegionInfo();
		regionInfo.setId(region.getId());
		regionInfo.setName(region.getDisplayName());
		regionInfo.setDistricts(
				Optional.ofNullable(region.getDistricts())
						.orElseGet(Collections::emptyList)
						.stream()
						.sorted(Comparator.comparing(District::getSearchValue))
						.map(d -> SelectOptionInfo.create(d.getId(), d.getDisplayName()))
						.collect(Collectors.toList())
		);
		return regionInfo;
	}
}
