package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LocationSearchRequestPayload extends StreetSearchRequestPayload {
	@JsonProperty("postal")
	private String postalCodePattern;
	@JsonProperty("street_number")
	private String streetNumberPattern;
	@JsonProperty("block_number")
	private String blockNumberPattern;
	@JsonProperty("room_number")
	private String roomNumberPattern;
}
