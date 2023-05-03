package ua.southpost.resource.backup.api.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.web.utils.json.ClinicTypeConverter;
import ua.southpost.resource.backup.web.utils.json.MedicalServiceConverter;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClinicSearchRequestPayload extends LocationSearchRequestPayload {
	@JsonProperty("name_pattern")
	private String namePattern;
	@JsonProperty("description_pattern")
	private String descriptionPattern;
	@JsonProperty("clinic_type")
	@JsonDeserialize(converter = ClinicTypeConverter.class)
	private ClinicType clinicType;
	@JsonProperty("service")
	@JsonDeserialize(converter = MedicalServiceConverter.class)
	private MedicalService service;
	@JsonProperty("phone_pattern")
	private String phonePattern;
	@JsonProperty("email_pattern")
	private String emailPattern;
}
