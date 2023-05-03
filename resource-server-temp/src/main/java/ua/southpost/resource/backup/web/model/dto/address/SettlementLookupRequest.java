/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.data.model.SettlementKind;

/**
 * The settlement lookup request body.
 * Created by mchys on 29.03.2018.
 */
public class SettlementLookupRequest {
	@JsonProperty("region_id")
	private String regionId;
	@JsonProperty("district_id")
	private Long districtId;
	@JsonProperty("kind")
	private SettlementKind kind;
	@JsonProperty("name")
	private String name;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public SettlementKind getKind() {
		return kind;
	}

	public void setKind(SettlementKind kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
