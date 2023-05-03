/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.entity.Identity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The phone number entity.
 * Created by mchys on 02.03.2018.
 */
@Entity
@Table(name = "phone")
@NamedQueries({
		@NamedQuery(
				name = "phone.findByName",
				query = "select ph from Phone as ph\n" +
						"where ph.searchNumber = :searchNumber"
		),
		@NamedQuery(
				name = "phone.count",
				query = "select count(ph) from Phone ph\n" +
						"where (:numberPattern is null or ph.searchNumber like upper(:numberPattern)  escape '\\\\')\n " +
						"  and (:descriptionPattern is null or upper(ph.description) like upper(:descriptionPattern)  escape '\\\\')"
		),
		@NamedQuery(
				name = "phone.list",
				query = "select ph from Phone ph\n" +
						"where (:numberPattern is null or ph.searchNumber like upper(:numberPattern)  escape '\\\\')\n " +
						"  and (:descriptionPattern is null or upper(ph.description) like upper(:descriptionPattern)  escape '\\\\')\n" +
						" order by ph.searchNumber asc"
		)
})
public class Phone implements Identity<Long> {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "DISPLAY_PHONE_NUMBER", nullable = false, length = 40)
	private String displayNumber;
	@SortField("label.phone.number")
	@Column(name = "SEARCH_PHONE_NUMBER", nullable = false, length = 40)
	private String searchNumber;
	@SortField("label.common.description")
	@Column(name = "DESCRIPTION", length = 250)
	private String description;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "display_number", required = true)
	public String getDisplayNumber() {
		return displayNumber;
	}

	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}

	@JsonProperty(value = "search_number", required = true)
	public String getSearchNumber() {
		return searchNumber;
	}

	public void setSearchNumber(String searchNumber) {
		this.searchNumber = searchNumber;
	}

	@JsonProperty(value = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("displayNumber", displayNumber)
				.append("searchNumber", searchNumber)
				.append("description", description)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Phone)) return false;

		Phone phone = (Phone) o;

		return new EqualsBuilder()
				.append(id, phone.id)
				.append(displayNumber, phone.displayNumber)
				.append(searchNumber, phone.searchNumber)
				.append(description, phone.description)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(displayNumber)
				.append(searchNumber)
				.append(description)
				.toHashCode();
	}
}
