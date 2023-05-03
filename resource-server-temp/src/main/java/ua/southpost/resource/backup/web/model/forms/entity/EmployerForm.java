/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

/**
 * Employer form.
 * Created by mchys on 16.03.2018.
 */
public class EmployerForm implements RegionalEntityForm<Long> {
	private Long id;
	private SettlementForm settlement;
	private String name;
	private String website;
	private ContactForm contact;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public SettlementForm getSettlement() {
		return Optional.ofNullable(settlement).orElseGet(() -> (settlement = new SettlementForm()));
	}

	public void setSettlement(SettlementForm settlement) {
		this.settlement = settlement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public ContactForm getContact() {
		return Optional.ofNullable(contact).orElseGet(ContactForm::new);
	}

	public void setContact(ContactForm contact) {
		this.contact = contact;
	}

	public ContactForm createContactForm() {
		return new ContactForm();
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(getSettlement()).map(SettlementForm::regionId).orElse(MOCK_REGION_ID);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("settlement", settlement)
				.append("name", name)
				.append("website", website)
				.append("contact", contact)
				.toString();
	}
}
