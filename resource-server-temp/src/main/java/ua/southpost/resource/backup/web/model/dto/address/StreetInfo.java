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
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

/**
 * Provides information about street.
 * Created by mchys on 19.05.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class StreetInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("settlement")
	private SettlementInfo settlement;
	@JsonProperty("kind")
	private AbstractEntityInfo<StreetKind> kind;
	@JsonProperty("name")
	private String name;
}
