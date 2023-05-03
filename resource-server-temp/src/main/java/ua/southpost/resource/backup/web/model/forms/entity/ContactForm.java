/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.api.model.submit.validate.OrderedEmailSet;
import ua.southpost.resource.backup.api.model.submit.validate.OrderedPhoneSet;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Contact form;
 * Created by mchys on 16.03.2018.
 */
public class ContactForm  {
	@JsonProperty("location")
	@Valid
	private LocationForm location;
	@JsonProperty("phones")
	@OrderedPhoneSet
	@Valid
	private List<PhoneForm> phones;
	@JsonProperty("emails")
	@OrderedEmailSet
	@Valid
	private List<EmailAddressForm> emails;

	public LocationForm getLocation() {
		return ofNullable(location).orElseGet(() -> (this.location = new LocationForm()));
	}

	public void setLocation(LocationForm location) {
		this.location = location;
	}

	public List<PhoneForm> getPhones() {
		return ofNullable(phones).orElseGet(() -> (phones = new ArrayList<>()));
	}

	public void setPhones(List<PhoneForm> phones) {
		this.phones = phones;
	}

	public List<EmailAddressForm> getEmails() {
		return ofNullable(emails).orElseGet(() -> (emails = new ArrayList<>()));
	}

	public void setEmails(List<EmailAddressForm> emails) {
		this.emails = emails;
	}

	public String regionId() {
		return ofNullable(getLocation()).map(LocationForm::regionId).orElse(RegionalEntityForm.MOCK_REGION_ID);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("location", location)
				.append("phones", phones)
				.append("emails", emails)
				.toString();
	}
}
