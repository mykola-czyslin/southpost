/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.web.utils.json.SettlementKindConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ua.southpost.resource.commons.model.dto.SortOption;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Settlement search form.
 * Created by mchys on 10.05.2018.
 */
public class SettlementSearchForm extends AbstractPagedSearchForm {
	@JsonProperty("settlement_name")
	private String settlementNamePattern;
	@JsonProperty("settlement_kind")
	@JsonDeserialize(converter = SettlementKindConverter.class)
	private SettlementKind settlementKind;
	@JsonProperty("district_id")
	private Long districtId;
	@JsonProperty("region_id")
	private String regionId;

	public SettlementSearchForm() {
	}

	public SettlementSearchForm(int pageNum, int linesPerPage, @Nonnull List<SortOption> sortOptions) {
		super(pageNum, linesPerPage, sortOptions);
	}

	public String getSettlementNamePattern() {
		return settlementNamePattern;
	}

	public void setSettlementNamePattern(String settlementNamePattern) {
		this.settlementNamePattern = settlementNamePattern;
	}

	public SettlementKind getSettlementKind() {
		return settlementKind;
	}

	public void setSettlementKind(SettlementKind settlementKind) {
		this.settlementKind = settlementKind;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.appendSuper(super.toString())
				.append("settlementNamePattern", settlementNamePattern)
				.append("settlementKind", settlementKind)
				.append("districtId", districtId)
				.append("regionId", regionId)
				.toString();
	}
}
