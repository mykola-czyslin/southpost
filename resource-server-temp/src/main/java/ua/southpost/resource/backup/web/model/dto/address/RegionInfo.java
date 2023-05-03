/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

import java.util.List;

/**
 * Region information container.
 * Created by mchys on 18.03.2018.
 */
public class RegionInfo extends AbstractEntityInfo<String> {
	@JsonProperty("name")
	private String name;
	@JsonProperty("districts")
	private List<SelectOptionInfo<Long>> districts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SelectOptionInfo<Long>> getDistricts() {
		return districts;
	}

	public void setDistricts(List<SelectOptionInfo<Long>> districts) {
		this.districts = districts;
	}
}
