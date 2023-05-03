/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.web.utils.json.SettlementKindConverter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * defines the structure of employer lookup request.
 * Created by mchys on 18.03.2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployerLookupRequest extends AbstractPagedSearchRequestPayload {
	@JsonProperty("employer_name")
	private String employerName;
	@JsonProperty("web_site")
	private String webSite;
	@JsonProperty("settlement_kind")
	@JsonDeserialize(converter = SettlementKindConverter.class)
	private SettlementKind settlementKind;
	@JsonProperty("settlement_name")
	private String settlementName;
	@JsonProperty("district_id")
	private Long districtId;
	@JsonProperty("region_id")
	private String regionId;

	public EmployerSearchRequestPayload toSearchPayload() {
		EmployerSearchRequestPayload payload = new EmployerSearchRequestPayload();
		payload.setRegionId(this.regionId);
		payload.setDistrictId(this.districtId);
		payload.setSettlementNamePattern(this.settlementName);
		payload.setSettlementKind(this.settlementKind);
		payload.setWebSiteAddressPattern(this.webSite);
		payload.setEmployerNamePattern(this.employerName);
		payload.setPageNum(this.getPageNum());
		payload.setLinesPerPage(this.getLinesPerPage());
		payload.setSortOptions(this.getSortOptions());
		payload.setWebSiteSignificant(isNotBlank(this.getWebSite()));
		return payload;
	}
}
