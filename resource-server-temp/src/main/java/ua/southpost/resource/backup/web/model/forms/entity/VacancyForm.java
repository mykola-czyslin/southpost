/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * General vacancy form
 * Created by mchys on 16.03.2018.
 */
public class VacancyForm implements RegionalEntityForm<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("employer")
	@Valid
	private EmployerForm employer;
	@JsonProperty("summary")
	@NotEmpty(message = "err.vacancy.summary")
	private String summary;
	@JsonProperty("description")
	private String description;
	@JsonProperty("hosting")
	private boolean hosting;
	@JsonProperty("salary_low")
	private BigDecimal salaryLow;
	@JsonProperty("salary_high")
	private BigDecimal salaryHigh;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public EmployerForm getEmployer() {
		return Optional.ofNullable(employer).orElseGet(() -> (employer = new EmployerForm()));
	}

	public void setEmployer(EmployerForm employer) {
		this.employer = employer;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHosting() {
		return hosting;
	}

	public void setHosting(boolean hosting) {
		this.hosting = hosting;
	}

	public BigDecimal getSalaryLow() {
		return salaryLow;
	}

	public void setSalaryLow(BigDecimal salaryLow) {
		this.salaryLow = salaryLow;
	}

	public BigDecimal getSalaryHigh() {
		return salaryHigh;
	}

	public void setSalaryHigh(BigDecimal salaryHigh) {
		this.salaryHigh = salaryHigh;
	}

	@Override
	public String regionId() {
		return Optional.ofNullable(getEmployer()).map(EmployerForm::getContact).map(ContactForm::regionId).orElse(MOCK_REGION_ID);
	}
}
