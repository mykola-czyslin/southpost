package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.PhoneEntity;
import ua.southpost.resource.backup.api.model.submit.validate.PhoneNumber;
import ua.southpost.resource.backup.api.model.submit.validate.PhoneNumberIntegrity;
import ua.southpost.resource.commons.model.dto.EntityPayload;

@Data
@EqualsAndHashCode
@ToString
@PhoneEntity
@PhoneNumberIntegrity
public class PhonePayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("phone_number")
	@PhoneNumber
	private String phoneNumber;
	@JsonProperty("description")
	private String description;
}
