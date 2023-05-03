/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.web.model.dto.address.DistrictInfo;
import ua.southpost.resource.backup.web.model.dto.address.SelectOptionInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Maps {@link District} to {@link DistrictInfo}.
 * Created by mchys on 18.03.2018.
 */
@Component
public class DistrictInfoMapper implements LocalizedEntityMapper<District, DistrictInfo, Long> {
	@Resource
	private RegionInfoMapper regionInfoMapper;
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;

	@Nonnull
	public DistrictInfo map(@Nonnull District district, @Nonnull Locale locale) {
		DistrictInfo districtInfo = new DistrictInfo();
		districtInfo.setId(district.getId());
		districtInfo.setName(district.getDisplayName());
		districtInfo.setRegion(regionInfoMapper.map(district.getRegion(), locale));
		final List<SelectOptionInfo<Long>> settlements = Optional.ofNullable(district.getSettlements()).orElseGet(Collections::emptyList)
				.stream()
				.sorted(Comparator.comparing(Settlement::getSearchValue))
				.map(s -> SelectOptionInfo.create(s.getId(), s.getDisplayName()))
				.collect(Collectors.toList());
		districtInfo.setSettlements(settlements);
		districtInfo.setMock(keyHolderMapper.map(district.getMockDistrict(), locale));
		return districtInfo;
	}

}
