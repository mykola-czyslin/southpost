/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import java.util.Optional;

/**
 * Location form.
 * Created by mchys on 17.03.2018.
 */
public class LocationForm implements RegionalEntityForm<Long> {
	private Long id;
	private StreetForm street;
	private String postalCode;
	private String streetNumber;
	private String blockNumber;
	private String roomNumber;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public StreetForm getStreet() {
		return Optional.ofNullable(street).orElseGet(StreetForm::new);
	}

	public void setStreet(StreetForm street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(getStreet()).map(StreetForm::regionId).orElse(MOCK_REGION_ID);
	}
}
