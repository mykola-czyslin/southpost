/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

/**
 * Provides information about settlement.
 * Created by mchys on 18.03.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class SettlementInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("name")
	private String name;
	@JsonProperty("kind")
	private AbstractEntityInfo<SettlementKind> kind;
	@JsonProperty("district")
	private DistrictInfo district;

}
