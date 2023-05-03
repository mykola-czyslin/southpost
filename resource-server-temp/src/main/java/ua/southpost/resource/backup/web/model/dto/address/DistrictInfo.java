/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

import java.util.List;

/**
 * Container for district data.
 * Created by mchys on 18.03.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DistrictInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("name")
	private String name;
	@JsonProperty("region")
	private RegionInfo region;
	@JsonProperty("settlements")
	private List<SelectOptionInfo<Long>> settlements;
	@JsonProperty("mock")
	private AbstractEntityInfo<NoYes> mock;

}
