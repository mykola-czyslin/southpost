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
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.annotations.SortField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * Vacancy entity.
 * Created by mchys on 25.02.2018.
 */
@Entity
@Table(name = "employer_vacancy")
public class Vacancy implements ModificationTrackingEntity {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@SortField(label = "label.employer.fields", complex = true)
	@ManyToOne(optional = false)
	@JoinColumn(name = "EMPLOYER_ID", referencedColumnName = "ID")
	private Employer employer;
	@Column(name = "SUMMARY", length = 80, nullable = false)
	private String summary;
	@SortField("label.vacancy.summary")
	@Column(name = "SEARCH_VALUE", length = 80, nullable = false)
	private String searchValue;
	@SortField("label.common.description")
	@Column(name = "DESCRIPTION")
	private String description;
	@SortField("label.vacancy.hosting")
	@Column(name = "HOSTING_AVAILABLE")
	@Enumerated(EnumType.STRING)
	private NoYes hostingAvailable;
	@SortField("label.vacancy.salary.low")
	@Column(name = "SALARY_LOW")
	private BigDecimal salaryLow;
	@SortField("label.vacancy.salary.high")
	@Column(name = "SALARY_HIGH")
	private BigDecimal salaryHigh;
	@SortField("label.modification.time")
	@Column(name = "MODIFICATION_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationTime;
	@SortField(label = "label.modified.by", complex = true)
	@ManyToOne
	@JoinColumn(name = "OPERATOR_ID", referencedColumnName = "id")
	private User modifiedBy;

	@JsonProperty(value = "id", required = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty(value = "employer", required = true)
	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	@JsonIgnore
	public Settlement getSettlement() {
		return getEmployer().getSettlement();
	}

	@JsonProperty("summary")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonProperty(value = "search_value", required = true)
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = upperCase(searchValue);
	}

	@JsonProperty(value = "description", required = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty(value = "hosting", required = true)
	public NoYes getHostingAvailable() {
		return hostingAvailable;
	}

	public void setHostingAvailable(NoYes hostingAvailable) {
		this.hostingAvailable = hostingAvailable;
	}

	@JsonProperty("salary_low")
	public BigDecimal getSalaryLow() {
		return salaryLow;
	}

	public void setSalaryLow(BigDecimal salaryLow) {
		this.salaryLow = salaryLow;
	}

	@JsonProperty("salary_high")
	public BigDecimal getSalaryHigh() {
		return salaryHigh;
	}

	public void setSalaryHigh(BigDecimal salaryHigh) {
		this.salaryHigh = salaryHigh;
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
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("employer", employer)
				.append("summary", summary)
				.append("searchValue", searchValue)
				.append("description", description)
				.append("hostingAvailable", hostingAvailable)
				.append("modificationTime", modificationTime)
				.append("salaryLow", salaryLow)
				.append("salaryHigh", salaryHigh)
				.append("modifiedBy", modifiedBy)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Vacancy)) return false;

		Vacancy vacancy = (Vacancy) o;

		return new EqualsBuilder()
				.append(id, vacancy.id)
				.append(employer, vacancy.employer)
				.append(searchValue, vacancy.searchValue)
				.append(description, vacancy.description)
				.append(hostingAvailable, vacancy.hostingAvailable)
				.append(salaryLow, vacancy.salaryLow)
				.append(salaryHigh, vacancy.salaryHigh)
				.append(modificationTime, vacancy.modificationTime)
				.append(modifiedBy, vacancy.modifiedBy)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(employer)
				.append(searchValue)
				.append(description)
				.append(hostingAvailable)
				.append(salaryLow)
				.append(salaryHigh)
				.append(modificationTime)
				.append(modifiedBy)
				.toHashCode();
	}
}
