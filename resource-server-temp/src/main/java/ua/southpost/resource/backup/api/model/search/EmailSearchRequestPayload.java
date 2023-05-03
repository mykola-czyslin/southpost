package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.commons.model.dto.AbstractPagedSearchRequestPayload;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailSearchRequestPayload extends AbstractPagedSearchRequestPayload {
	@JsonProperty("email_address_pattern")
	private String emailAddressPattern;
	@JsonProperty("description_pattern")
	private String descriptionPattern;
}
