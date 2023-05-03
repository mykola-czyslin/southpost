/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.StreetKind;

import java.util.Optional;

/**
 * Street form.
 * Created by mchys on 17.03.2018.
 */
public class StreetForm implements RegionalEntityForm<Long> {
	private Long id;
	private SettlementForm settlement;
	private StreetKind kind;
	private String name;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public SettlementForm getSettlement() {
		return Optional.ofNullable(settlement).orElseGet(() -> (settlement = new SettlementForm()));
	}

	public void setSettlement(SettlementForm settlement) {
		this.settlement = settlement;
	}

	public StreetKind getKind() {
		return kind;
	}

	public void setKind(StreetKind kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(this.getSettlement()).map(SettlementForm::regionId).orElse(MOCK_REGION_ID);
	}
}
