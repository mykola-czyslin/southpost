/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.web.utils.json.StreetKindConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The street search form.
 * Created by mchys on 15.05.2018.
 */
public class StreetSearchForm extends SettlementSearchForm {
	@JsonProperty("street_kind")
	@JsonDeserialize(converter = StreetKindConverter.class)
	private StreetKind streetKind;
	@JsonProperty("street_name")
	private String streetNamePattern;

	public StreetSearchForm() {
	}

	public StreetSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public StreetKind getStreetKind() {
		return streetKind;
	}

	public void setStreetKind(StreetKind streetKind) {
		this.streetKind = streetKind;
	}

	public String getStreetNamePattern() {
		return streetNamePattern;
	}

	public void setStreetNamePattern(String streetNamePattern) {
		this.streetNamePattern = streetNamePattern;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.appendSuper(super.toString())
				.append("streetKind", streetKind)
				.append("streetNamePattern", streetNamePattern)
				.toString();
	}
}
