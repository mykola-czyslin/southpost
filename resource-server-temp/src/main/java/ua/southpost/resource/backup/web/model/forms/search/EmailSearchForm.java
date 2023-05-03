/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Search form for the {@link EmailAddress} entity.
 * Created by mchys on 16.09.2018.
 */
public class EmailSearchForm extends AbstractPagedSearchForm {
	@JsonProperty("email_address_pattern")
	private String emailAddressPattern;
	@JsonProperty("description_pattern")
	private String descriptionPattern;

	public EmailSearchForm() {
	}

	public EmailSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getEmailAddressPattern() {
		return emailAddressPattern;
	}

	@SuppressWarnings("unused")
	public void setEmailAddressPattern(String emailAddressPattern) {
		this.emailAddressPattern = emailAddressPattern;
	}

	public String getDescriptionPattern() {
		return descriptionPattern;
	}

	@SuppressWarnings("unused")
	public void setDescriptionPattern(String descriptionPattern) {
		this.descriptionPattern = descriptionPattern;
	}
}
