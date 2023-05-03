/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.entity.ContactEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * The dwelling for rent entity class.
 * index dwelling_identity_idx UNIQUE(SETTLEMENT_ID, SETTLEMENT_AREA_SEARCH, LOCATION_ID, KIND)
 * Created by mchys on 15.04.2018.
 */
@Entity
@Table(name = "dwelling")
public class Dwelling implements ModificationTrackingEntity, ContactEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@SortField(label = "label.settlement.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID", referencedColumnName = "ID")
	private Settlement settlement;
	@Column(name = "SETTLEMENT_AREA")
	private String settlementArea;
	@SortField("label.settlement.area")
	@Column(name = "SETTLEMENT_AREA_SEARCH")
	private String settlementAreaSearch;
	@SortField("label.common.kind")
	@Column(name = "KIND")
	@Enumerated(EnumType.STRING)
	private DwellingKind kind;
	@SortField("label.number.of.rooms")
	@Column(name = "NUMBER_OF_ROOMS")
	private int numberOfRooms;
	@SortField("label.total.area")
	@Column(name = "TOTAL_AREA")
	private BigDecimal totalArea;
	@SortField("label.living.area")
	@Column(name = "LIVING_AREA")
	private BigDecimal livingArea;
	@SortField("label.common.price")
	@Column(name = "PRICE", nullable = false)
	private BigDecimal price;
	@SortField("label.billing.period")
	@Column(name = "BILLING_PERIOD", nullable = false)
	@Enumerated(EnumType.STRING)
	private BillingPeriod billingPeriod;
	@Column(name = "DESCRIPTION")
	private String description;
	@SortField(label = "label.location.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
	private Location locationAddress;
	@ManyToMany
	@JoinTable(
			name = "dwelling_phones",
			joinColumns = {
					@JoinColumn(name = "DWELLING_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	private List<Phone> phones;
	@ManyToMany
	@JoinTable(
			name = "dwelling_emails",
			joinColumns = {
					@JoinColumn(name = "DWELLING_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "EMAIL_ADDRESS_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	private List<EmailAddress> emails;
	@SortField("label.modification.time")
	@Column(name = "MODIFICATION_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTime;
	@SortField(label = "label.modified.by", complex = true)
	@ManyToOne
	@JoinColumn(name = "OPERATOR_ID", referencedColumnName = "ID")
	private User modifiedBy;


	@JsonProperty(value = "id", required = true)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "settlement", required = true)
	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@JsonProperty(value = "settlement_area", required = true)
	public String getSettlementArea() {
		return settlementArea;
	}

	public void setSettlementArea(String settlementArea) {
		this.settlementArea = settlementArea;
	}

	@JsonProperty(value = "settlement_area_search", required = true)
	public String getSettlementAreaSearch() {
		return settlementAreaSearch;
	}

	public void setSettlementAreaSearch(String settlementAreaSearch) {
		this.settlementAreaSearch = upperCase(settlementAreaSearch);
	}

	@JsonProperty(value = "kind", required = true)
	public DwellingKind getKind() {
		return kind;
	}

	public void setKind(DwellingKind kind) {
		this.kind = kind;
	}

	@JsonProperty("number_of_rooms")
	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	@JsonProperty("total_area")
	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	@JsonProperty("living_area")
	public BigDecimal getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(BigDecimal livingArea) {
		this.livingArea = livingArea;
	}

	@JsonProperty("price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@JsonProperty("billing_period")
	public BillingPeriod getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(BillingPeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String conditions) {
		this.description = conditions;
	}

	@JsonProperty("address")
	@Override
	public Location getLocationAddress() {
		return locationAddress;
	}

	@Override
	public void setLocationAddress(Location contactAddress) {
		this.locationAddress = contactAddress;
	}

	@Override
	public List<Phone> getPhones() {
		return phones;
	}

	@Override
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public List<EmailAddress> getEmails() {
		return emails;
	}

	@Override
	public void setEmails(List<EmailAddress> emails) {
		this.emails = emails;
	}

	@JsonIgnore
	@Override
	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	@JsonIgnore
	@Override
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("settlement", settlement)
				.append("settlementArea", settlementArea)
				.append("settlementAreaSearch", settlementAreaSearch)
				.append("kind", kind)
				.append("numberOfRooms", numberOfRooms)
				.append("totalArea", totalArea)
				.append("livingArea", livingArea)
				.append("price", price)
				.append("billingPeriod", billingPeriod)
				.append("locationAddress", locationAddress)
				.append("description", description)
				.append("modificationTime", modificationTime)
				.append("modifiedBy", modifiedBy)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Dwelling)) return false;

		Dwelling dwelling = (Dwelling) o;

		return new EqualsBuilder()
				.append(numberOfRooms, dwelling.numberOfRooms)
				.append(id, dwelling.id)
				.append(settlement, dwelling.settlement)
				.append(settlementArea, dwelling.settlementArea)
				.append(settlementAreaSearch, dwelling.settlementAreaSearch)
				.append(kind, dwelling.kind)
				.append(totalArea, dwelling.totalArea)
				.append(livingArea, dwelling.livingArea)
				.append(price, dwelling.price)
				.append(billingPeriod, dwelling.billingPeriod)
				.append(locationAddress, dwelling.locationAddress)
				.append(phones, dwelling.phones)
				.append(emails, dwelling.emails)
				.append(description, dwelling.description)
				.append(modificationTime, dwelling.modificationTime)
				.append(modifiedBy, dwelling.modifiedBy)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(settlement)
				.append(settlementArea)
				.append(settlementAreaSearch)
				.append(kind)
				.append(numberOfRooms)
				.append(totalArea)
				.append(livingArea)
				.append(price)
				.append(billingPeriod)
				.append(locationAddress)
				.append(phones)
				.append(emails)
				.append(description)
				.append(modificationTime)
				.append(modifiedBy)
				.toHashCode();
	}
}
