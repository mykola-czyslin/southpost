package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.StreetKind;
import ua.southpost.resource.backup.web.utils.json.StreetKindConverter;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StreetSearchRequestPayload extends SettlementSearchRequestPayload {
	@JsonProperty("street_kind")
	@JsonDeserialize(converter = StreetKindConverter.class)
	private StreetKind streetKind;
	@JsonProperty("street_name")
	private String streetNamePattern;
}
