/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.data.model.StreetKind;

/**
 * Abstraction of the street lookup request.
 * Created by mchys on 19.05.2018.
 */
public class StreetLookupRequest {
	@JsonProperty("settlement")
	private SettlementLookupRequest settlementLookupRequest;
	@JsonProperty("kind")
	private StreetKind streetKind;
	@JsonProperty("name")
	private String streetName;

	public SettlementLookupRequest getSettlementLookupRequest() {
		return settlementLookupRequest;
	}

	public void setSettlementLookupRequest(SettlementLookupRequest settlementLookupRequest) {
		this.settlementLookupRequest = settlementLookupRequest;
	}

	public StreetKind getStreetKind() {
		return streetKind;
	}

	public void setStreetKind(StreetKind streetKind) {
		this.streetKind = streetKind;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
}
