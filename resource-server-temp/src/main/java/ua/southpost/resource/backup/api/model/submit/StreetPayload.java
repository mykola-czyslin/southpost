package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.StreetEntity;
import ua.southpost.resource.backup.api.model.submit.validate.StreetIntegrity;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.backup.data.model.StreetKind;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
@StreetEntity
@StreetIntegrity
public class StreetPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("settlement")
	@Valid
	@NotNull(message = "err.settlement.required")
	private SettlementPayload settlement;
	@JsonProperty("kind")
	@NotNull(message = "err.street.kind.required")
	private StreetKind kind;
	@JsonProperty("name")
	@NotEmpty(message = "err.street.name.required")
	private String name;

}
