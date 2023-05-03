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
 * Data transfer object that transfers e-mail address information.
 * Created by mchys on 25.08.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailInfo extends AbstractEntityInfo<Long> {
	@JsonProperty("email_address")
	private String emailAddress;
	@JsonProperty("description")
	private String description;

}
