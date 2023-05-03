/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.util;

import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.model.Region;
import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.data.repo.RegionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.OPTION_TEXT_PAD_STR;
import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.OPTION_TEXT_TERMINAL_CHAR;

/**
 * Provides common functions for region/district lookup support
 * Created by mchys on 21.04.2018.
 */
@Service
public class RegionDistrictOptionsUtil {
	@Resource
	private RegionRepository regionRepository;
	@Resource
	private DistrictRepository districtRepository;

	public Map<String, String> regionOptions() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(NULL_OPTION_VALUE, StringUtils.leftPad(OPTION_TEXT_TERMINAL_CHAR, 17, OPTION_TEXT_PAD_STR));

		regionRepository.findAll()
				.stream()
				.sorted(Comparator.comparing(Region::getSearchValue))
				.forEach(r -> map.put(r.getId(), r.getDisplayName()));
		return map;
	}

	public Map<String, String> districtOptions(String regionId) {
		Map<String, String> result = new LinkedHashMap<>();
		result.put(OptionsUtilConstants.NULL_NUMERIC_OPTION_VALUE, StringUtils.leftPad(OPTION_TEXT_TERMINAL_CHAR, 21, OPTION_TEXT_PAD_STR));
		districtRepository.list(regionId)
				.stream()
				.sorted(Comparator.comparing(District::getSearchValue))
				.forEach(d -> result.put(String.format("%d", d.getId()), d.getDisplayName()));
		return result;
	}

}
