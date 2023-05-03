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

import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * Describes EMailAddress entity
 * Created by mchys on 02.03.2018.
 */
@Entity
@Table(name = "email_address")
@NamedQueries({
		@NamedQuery(
				name = "email.count",
				query = "select count(ea) from EmailAddress as ea \n" +
						"where (:addressPattern is null or ea.emailAddress like lower(:addressPattern) escape '\\\\')\n" +
						"  and (:descriptionPattern is null or upper(ea.description) like upper(:descriptionPattern) escape '\\\\')"
		),
		@NamedQuery(
				name = "email.list",
				query = "select ea from EmailAddress as ea \n" +
						"where (:addressPattern is null or ea.emailAddress like lower(:addressPattern) escape '\\\\')\n" +
						"  and (:descriptionPattern is null or upper(ea.description) like upper(:descriptionPattern) escape '\\\\')\n" +
						"order by ea.emailAddress"
		),
		@NamedQuery(
				name = "email.byAddress",
				query = "select ea from EmailAddress ea \n " +
						"where ea.emailAddress = lower(:emailAddress)"
		)
})
public class EmailAddress implements Identity<Long> {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField("label.mail.address")
	@Column(name = "EMAIL_ADDRESS", nullable = false, length = 80)
	private String emailAddress;
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

	@JsonProperty(value = "e_mail_address", required = true)
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = lowerCase(emailAddress);
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
				.append("emailAddress", emailAddress)
				.append("description", description)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof EmailAddress)) return false;

		EmailAddress that = (EmailAddress) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(emailAddress, that.emailAddress)
				.append(description, that.description)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(emailAddress)
				.append(description)
				.toHashCode();
	}
}
