package ua.southpost.resource.backup.web.model.forms.entity;

import ua.southpost.resource.backup.data.model.ClinicType;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static java.util.Optional.ofNullable;

public class ClinicForm implements RegionalEntityForm<Long> {
	private Long id;
	private String clinicName;
	private ClinicType clinicType;
	private String description;
	private ContactForm contact;
	private List<String> services;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public ClinicType getClinicType() {
		return clinicType;
	}

	public void setClinicType(ClinicType clinicType) {
		this.clinicType = clinicType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ContactForm getContact() {
		return ofNullable(contact).orElseGet(ContactForm::new);
	}

	public void setContact(ContactForm contact) {
		this.contact = contact;
	}


	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	@Override
	public String regionId() {
		return ofNullable(getContact()).map(ContactForm::getLocation).map(LocationForm::regionId).orElse(MOCK_REGION_ID);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("clinicName", clinicName)
				.append("clinicType", clinicType)
				.append("description", description)
				.append("contact", contact)
				.append("services", services)
				.toString();
	}
}
