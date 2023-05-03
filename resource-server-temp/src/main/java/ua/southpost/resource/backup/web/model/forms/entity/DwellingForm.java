/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.DwellingKind;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The form to create/update Dwelling.
 * Created by mchys on 20.04.2018.
 */
public class DwellingForm implements RegionalEntityForm<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("settlement")
	@Valid
	@NotNull(message = "err.settlement.required")
	private SettlementForm settlement;
	@JsonProperty("settlement_area")
	private String settlementArea;
	@NotNull(message = "err.dwelling.kind.required")
	@JsonProperty("dwelling_kind")
	private DwellingKind dwellingKind;
	@JsonProperty("number_of_rooms")
	private int numberOfRooms;
	@JsonProperty("total_area")
	private BigDecimal totalArea;
	@JsonProperty("living_area")
	private BigDecimal livingArea;
	@JsonProperty("contact")
	@Valid
	@NotNull(message = "err.contact.required")
	private ContactForm contact;
	@JsonProperty("price")
	@NotNull(message = "err.price.required")
	private BigDecimal price;
	@JsonProperty("billing_period")
	@NotNull(message = "err.billing.period.required")
	private BillingPeriod billingPeriod;
	@JsonProperty("description")
	private String description;


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

	public String getSettlementArea() {
		return settlementArea;
	}

	public void setSettlementArea(String settlementArea) {
		this.settlementArea = settlementArea;
	}

	public DwellingKind getDwellingKind() {
		return dwellingKind;
	}

	public void setDwellingKind(DwellingKind dwellingKind) {
		this.dwellingKind = dwellingKind;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	public BigDecimal getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(BigDecimal livingArea) {
		this.livingArea = livingArea;
	}

	public ContactForm getContact() {
		return Optional.ofNullable(contact).orElseGet(() -> (contact = new ContactForm()));
	}

	public void setContact(ContactForm contact) {
		this.contact = contact;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BillingPeriod getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(BillingPeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(getSettlement()).map(SettlementForm::regionId).orElse(MOCK_REGION_ID);
	}

	public LocationForm createAddressForm() {
		return new LocationForm();
	}
}
