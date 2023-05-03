package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.EmployerEntity;
import ua.southpost.resource.backup.api.model.submit.validate.EmployerId;
import ua.southpost.resource.backup.api.model.submit.validate.EmployerNameIntegrity;
import ua.southpost.resource.backup.api.model.submit.validate.EmployerWebsiteIntegrity;
import ua.southpost.resource.commons.model.dto.EntityPayload;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
@EmployerEntity(idProperty = "id", nameProperty = "name")
@EmployerNameIntegrity
@EmployerWebsiteIntegrity
public class EmployerPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	@EmployerId
	private Long id;
	@JsonProperty("settlement")
	@NotNull(message = "err.employer.settlement.undefined")
	@Valid
	private SettlementPayload settlement;
	@JsonProperty("name")
	@NotEmpty(message = "err.employer.name.undefined")
	private String name;
	@JsonProperty("website")
	private String website;
	@JsonProperty("contact")
	@Valid
	private ContactPayload contact;
}
