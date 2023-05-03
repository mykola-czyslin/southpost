/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.SettlementKind;

import java.util.Optional;

/**
 * The settlement form.
 * Created by mchys on 16.03.2018.
 */
public class SettlementForm implements RegionalEntityForm<Long> {
	private Long id;
	private Long districtId;
	private String regionId;
	private String name;
	private SettlementKind kind;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SettlementKind getKind() {
		return kind;
	}

	public void setKind(SettlementKind kind) {
		this.kind = kind;
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(regionId).orElse(MOCK_REGION_ID);
	}
}
