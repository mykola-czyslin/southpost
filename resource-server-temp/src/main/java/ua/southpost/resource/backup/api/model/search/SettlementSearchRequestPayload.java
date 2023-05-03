package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.web.utils.json.SettlementKindConverter;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SettlementSearchRequestPayload extends AbstractPagedSearchRequestPayload {
	@JsonProperty("settlement_name")
	private String settlementNamePattern;
	@JsonProperty("settlement_kind")
	@JsonDeserialize(converter = SettlementKindConverter.class)
	private SettlementKind settlementKind;
	@JsonProperty("district_id")
	private Long districtId;
	@JsonProperty("region_id")
	private String regionId;
}
