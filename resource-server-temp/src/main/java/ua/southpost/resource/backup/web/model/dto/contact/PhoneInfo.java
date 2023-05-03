/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;

/**
 * Data transfer object that transfers phone info;
 * Created by mchys on 25.08.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhoneInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("phone_number")
	private String phoneNumber;
	@JsonProperty("description")
	private String description;

}
