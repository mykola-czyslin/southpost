package ua.southpost.resource.backup.api.model.submit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.api.model.submit.validate.ClinicEntity;
import ua.southpost.resource.commons.model.dto.EntityPayload;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.MedicalService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@ClinicEntity
public class ClinicPayload implements EntityPayload<Long> {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("clinic_name")
	@NotEmpty(message = "err.clinic.name.required")
	private String clinicName;
	@JsonProperty("clinic_type")
	@NotNull(message = "err.clinic.type.required")
	private ClinicType clinicType;
	@JsonProperty("description")
	private String description;
	@JsonProperty("contact")
	@Valid
	private ContactPayload contact;
	@JsonProperty("services")
	@NotEmpty(message = "err.clinic.at.least.one.service.required")
	private List<MedicalService> services;
}
