package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.LocationEntity;
import ua.southpost.resource.backup.api.model.submit.validate.LocationIntegrity;
import ua.southpost.resource.commons.model.dto.EntityPayload;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
@LocationEntity
@LocationIntegrity
public class LocationPayload<AI> implements EntityPayload<AI> {
	@JsonProperty("id")
	private AI id;
	@JsonProperty("street")
	@Valid
	@NotNull(message = "err.location.street.required")
	private StreetPayload street;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("street_number")
	@NotEmpty(message = "err.street.number.required")
	private String streetNumber;
	@JsonProperty("block_number")
	private String blockNumber;
	@JsonProperty("room_number")
	private String roomNumber;
}
