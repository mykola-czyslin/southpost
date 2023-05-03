/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.dto.employer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;

import java.util.List;

/**
 * Container for employer info transition.
 * Created by mchys on 18.03.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployerInfo extends AbstractModificationTrackingEntityInfo<Long> implements ContactInfo {
	@JsonProperty("name")
	private String name;
	@JsonProperty("website")
	private String website;
	@JsonProperty("settlement")
	private SettlementInfo settlement;
	@JsonProperty("location")
	private LocationInfo location;
	@JsonProperty("phones")
	private List<PhoneInfo> phones;
	@JsonProperty("emails")
	private List<EmailInfo> emails;
}
