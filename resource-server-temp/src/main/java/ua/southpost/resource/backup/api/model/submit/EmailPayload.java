package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.EmailAddressIntegrity;
import ua.southpost.resource.backup.api.model.submit.validate.EmailEntity;
import ua.southpost.resource.commons.model.dto.EntityPayload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode
@ToString
@EmailEntity
@EmailAddressIntegrity
public class EmailPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("email_address")
	@NotEmpty(message = "err.empty.email")
	@Email(message = "err.invalid.email.format")
	private String emailAddress;
	@JsonProperty("description")
	private String description;
}
