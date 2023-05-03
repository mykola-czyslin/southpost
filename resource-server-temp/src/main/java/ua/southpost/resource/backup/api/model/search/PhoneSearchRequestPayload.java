package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhoneSearchRequestPayload extends AbstractPagedSearchRequestPayload {
	@JsonProperty("phone_number_pattern")
	private String phoneNumberPattern;
	@JsonProperty("description_pattern")
	private String descriptionPattern;
}
