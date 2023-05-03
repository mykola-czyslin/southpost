/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import ua.southpost.resource.backup.data.model.NoYes;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Transfers vacancy search data from the client to the server.
 * Created by mchys on 04.04.2018.
 */
public class VacancySearchForm extends SettlementSearchForm {
	private String summaryPattern;
	private String descriptionPattern;
	private BigDecimal salaryLow;
	private NoYes hosting;
	private String employerNamePattern;
	private String phonePattern;
	private String mailPattern;

	@SuppressWarnings("unused")
	public VacancySearchForm() {
	}

	public VacancySearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getSummaryPattern() {
		return summaryPattern;
	}

	@SuppressWarnings("unused")
	public void setSummaryPattern(String summaryPattern) {
		this.summaryPattern = summaryPattern;
	}

	public String getDescriptionPattern() {
		return descriptionPattern;
	}

	public void setDescriptionPattern(String descriptionPattern) {
		this.descriptionPattern = descriptionPattern;
	}

	public BigDecimal getSalaryLow() {
		return salaryLow;
	}

	@SuppressWarnings("unused")
	public void setSalaryLow(BigDecimal salaryLow) {
		this.salaryLow = salaryLow;
	}

	public NoYes getHosting() {
		return hosting;
	}

	public void setHosting(NoYes hosting) {
		this.hosting = hosting;
	}

	public String getEmployerNamePattern() {
		return employerNamePattern;
	}

	@SuppressWarnings("unused")
	public void setEmployerNamePattern(String employerNamePattern) {
		this.employerNamePattern = employerNamePattern;
	}

	public String getPhonePattern() {
		return phonePattern;
	}

	public void setPhonePattern(String phonePattern) {
		this.phonePattern = phonePattern;
	}

	public String getMailPattern() {
		return mailPattern;
	}

	@SuppressWarnings("unused")
	public void setMailPattern(String mailPattern) {
		this.mailPattern = mailPattern;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("summaryPattern", summaryPattern)
				.append("descriptionPattern", descriptionPattern)
				.append("salaryLow", salaryLow)
				.append("hosting", hosting)
				.append("employerNamePattern", employerNamePattern)
				.append("phonePattern", phonePattern)
				.append("mailPattern", mailPattern)
				.toString();
	}
}
