/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.EntityInfo;

/**
 * Abstract lookup entity info.
 * Created by mchys on 26.05.2018.
 */
@Data
@ToString
@EqualsAndHashCode
public class AbstractEntityInfo<I> implements EntityInfo<I> {
	@JsonProperty("id")
	private I id;
	@JsonProperty("text_value")
	private String textValue;
}
