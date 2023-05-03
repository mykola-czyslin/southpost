package ua.southpost.resource.backup.web.model.dto.clinic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClinicInfo extends AbstractModificationTrackingEntityInfo<Long> implements ContactInfo {
	@JsonProperty("clinic_name")
	private String clinicName;
	@JsonProperty("description")
	private String description;
	@JsonProperty("clinic_type")
	private AbstractEntityInfo<ClinicType> clinicType;
	@JsonProperty("services")
	private Set<AbstractEntityInfo<MedicalService>> services;
	@JsonProperty("location")
	private LocationInfo location;
	@JsonProperty("phones")
	private List<PhoneInfo> phones;
	@JsonProperty("emails")
	private List<EmailInfo> emails;

}
