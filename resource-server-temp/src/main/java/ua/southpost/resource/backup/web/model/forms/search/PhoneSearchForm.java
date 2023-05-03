/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Search form for the {@link Phone} entity.
 * Created by mchys on 15.09.2018.
 */
public class PhoneSearchForm extends AbstractPagedSearchForm {
	@JsonProperty("phone_number_pattern")
	private String phoneNumberPattern;
	@JsonProperty("description_pattern")
	private String descriptionPattern;

	public PhoneSearchForm() {
	}

	public PhoneSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getPhoneNumberPattern() {
		return phoneNumberPattern;
	}

	public void setPhoneNumberPattern(String phoneNumberPattern) {
		this.phoneNumberPattern = phoneNumberPattern;
	}

	public String getDescriptionPattern() {
		return descriptionPattern;
	}

	public void setDescriptionPattern(String descriptionPattern) {
		this.descriptionPattern = descriptionPattern;
	}
}
