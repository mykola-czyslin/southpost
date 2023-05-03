package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.DistrictId;
import ua.southpost.resource.backup.api.model.submit.validate.RegionId;
import ua.southpost.resource.backup.api.model.submit.validate.SettlementEntity;
import ua.southpost.resource.backup.api.model.submit.validate.SettlementIntegrity;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.backup.data.model.SettlementKind;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
@SettlementEntity
@SettlementIntegrity
public class SettlementPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("district_id")
	@DistrictId
	@NotNull(message = "err.settlement.district.null")
	private Long districtId;
	@JsonProperty("region_id")
	@RegionId
	@NotEmpty(message = "err.settlement.region.empty")
	private String regionId;
	@JsonProperty("name")
	@NotEmpty(message = "err.settlement.name.empty")
	private String name;
	@JsonProperty("kind")
	@NotNull(message = "err.settlement.kind.null")
	private SettlementKind kind;

}
