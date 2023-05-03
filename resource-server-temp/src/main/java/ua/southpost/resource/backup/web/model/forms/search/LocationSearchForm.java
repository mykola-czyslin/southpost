/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The location search form
 * Created by mchys on 22.05.2018.
 */
public class LocationSearchForm extends StreetSearchForm {
	@JsonProperty("postal")
	private String postalCodePattern;
	@JsonProperty("street_number")
	private String streetNumberPattern;
	@JsonProperty("block_number")
	private String blockNumberPattern;
	@JsonProperty("room_number")
	private String roomNumberPattern;

	public LocationSearchForm() {
	}

	public LocationSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getPostalCodePattern() {
		return postalCodePattern;
	}

	public void setPostalCodePattern(String postalCodePattern) {
		this.postalCodePattern = postalCodePattern;
	}

	public String getStreetNumberPattern() {
		return streetNumberPattern;
	}

	public void setStreetNumberPattern(String streetNumberPattern) {
		this.streetNumberPattern = streetNumberPattern;
	}

	public String getBlockNumberPattern() {
		return blockNumberPattern;
	}

	public void setBlockNumberPattern(String blockNumberPattern) {
		this.blockNumberPattern = blockNumberPattern;
	}

	public String getRoomNumberPattern() {
		return roomNumberPattern;
	}

	public void setRoomNumberPattern(String roomNumberPattern) {
		this.roomNumberPattern = roomNumberPattern;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.appendSuper(super.toString())
				.append("postalCodePattern", postalCodePattern)
				.append("streetNumberPattern", streetNumberPattern)
				.append("blockNumberPattern", blockNumberPattern)
				.append("roomNumberPattern", roomNumberPattern)
				.toString();
	}
}
