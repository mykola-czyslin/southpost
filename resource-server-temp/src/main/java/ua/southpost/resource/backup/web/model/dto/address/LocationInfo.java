/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

/**
 * Transfers location data.
 * Created by mchys on 25.08.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LocationInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("street")
	private StreetInfo street;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("street_number")
	private String streetNumber;
	@JsonProperty("block_number")
	private String blockNumber;
	@JsonProperty("room_number")
	private String roomNumber;
}
