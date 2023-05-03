/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.data.model.District;
import ua.southpost.resource.backup.data.repo.DistrictRepository;
import ua.southpost.resource.backup.web.model.dto.address.SelectOptionInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mchys on 29.03.2018.
 */
@RestController
public class RegionLookupEndpoint {
	@Resource
	private DistrictRepository repository;

	@GetMapping(value = "/api/region/{regionId}/districts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SelectOptionInfo<Long>> regionDistricts(@PathVariable(name = "regionId") String regionId) {
		return repository.list(regionId)
				.stream()
				.sorted(Comparator.comparing(District::getSearchValue))
				.map(d -> SelectOptionInfo.create(d.getId(), d.getDisplayName()))
				.collect(Collectors.toList());
	}
}
