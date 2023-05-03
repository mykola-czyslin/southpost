package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.OrderedEmailSet;
import ua.southpost.resource.backup.api.model.submit.validate.OrderedPhoneSet;

import javax.validation.Valid;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class ContactPayload {
	@JsonProperty("location")
	@Valid
	private LocationPayload location;
	@JsonProperty("phones")
	@OrderedPhoneSet
	@Valid
	private List<PhonePayload> phones;
	@JsonProperty("emails")
	@OrderedEmailSet
	@Valid
	private List<EmailPayload> emails;
}
