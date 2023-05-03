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
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * The employer entity.
 * Created by mchys on 24.02.2018.
 */
@Entity
@Table(name = "employer")
public class Employer implements ModificationTrackingEntity, ContactEntity {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "DISPLAY_NAME", nullable = false, length = 80)
	private String displayName;
	@SortField("label.common.name")
	@Column(name = "SEARCH_VALUE", nullable = false, length = 80)
	private String searchValue;
	@SortField("label.employer.website")
	@Column(name = "WEBSITE", length = 200)
	private String website;
	@SortField(label = "label.settlement.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID", referencedColumnName = "ID")
	private Settlement settlement;
	@SortField(label = "label.location.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
	private Location locationAddress;
	@ManyToMany
	@JoinTable(
			name = "employer_phones",
			joinColumns = {
					@JoinColumn(name = "EMPLOYER_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	private List<Phone> phones;
	@ManyToMany
	@JoinTable(
			name = "employer_emails",
			joinColumns = {
					@JoinColumn(name = "EMPLOYER_ID", referencedColumnName = "ID")
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
	@JsonIgnore
	private Date modificationTime;
	@SortField(label = "label.modified.by", complex = true)
	@ManyToOne
	@JoinColumn(name = "MODIFIED_BY_USER_ID", referencedColumnName = "id")
	@JsonIgnore
	private User modifiedBy;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "display_name", required = true)
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@JsonProperty(value = "search_value", required = true)
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = upperCase(searchValue);
	}

	@JsonProperty("web_site")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = lowerCase(website);
	}

	@JsonProperty(value = "settlement", required = true)
	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Override
	public Location getLocationAddress() {
		return locationAddress;
	}

	@Override
	public void setLocationAddress(Location locationAddress) {
		this.locationAddress = locationAddress;
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

	public void setEmails(List<EmailAddress> emails) {
		this.emails = emails;
	}

	@Override
	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

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
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.append("website", website)
				.append("settlement", settlement)
				.append("locationAddress", locationAddress)
				.append("phones", phones)
				.append("emails", emails)
				.append("modificationTime", modificationTime)
				.append("modifiedBy", modifiedBy)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Employer)) return false;

		Employer employer = (Employer) o;

		return new EqualsBuilder()
				.append(id, employer.id)
				.append(displayName, employer.displayName)
				.append(searchValue, employer.searchValue)
				.append(website, employer.website)
				.append(settlement, employer.settlement)
				.append(locationAddress, employer.locationAddress)
				.append(phones, employer.phones)
				.append(emails, employer.emails)
				.append(modificationTime, employer.modificationTime)
				.append(modifiedBy, employer.modifiedBy)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(displayName)
				.append(searchValue)
				.append(website)
				.append(settlement)
				.append(locationAddress)
				.append(phones)
				.append(emails)
				.append(modificationTime)
				.append(modifiedBy)
				.toHashCode();
	}
}
