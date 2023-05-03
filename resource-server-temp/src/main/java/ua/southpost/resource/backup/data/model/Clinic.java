package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ua.southpost.resource.commons.model.annotations.SortField;
import ua.southpost.resource.commons.model.entity.ContactEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.upperCase;

@Entity
@Table(name = "clinic")
public class Clinic implements ModificationTrackingEntity, ContactEntity {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;
	@Column(name = "DISPLAY_NAME", nullable = false)
	@JsonProperty("display_name")
	private String displayName;
	@SortField(label = "label.clinic.name")
	@Column(name = "SEARCH_NAME", nullable = false)
	@JsonProperty("search_name")
	private String searchName;
	@SortField(label = "label.clinic.type")
	@Column(name = "CLINIC_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	@JsonProperty("clinic_type")
	private ClinicType clinicType;
	@Column(name = "DESCRIPTION")
	@JsonProperty("description")
	private String description;

	@SortField(label = "label.location.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
	private Location locationAddress;
	@ManyToMany
	@JoinTable(
			name = "clinic_phones",
			joinColumns = {
					@JoinColumn(name = "CLINIC_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	private List<Phone> phones;
	@ManyToMany
	@JoinTable(
			name = "clinic_emails",
			joinColumns = {
					@JoinColumn(name = "CLINIC_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "EMAIL_ADDRESS_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	private List<EmailAddress> emails;
	@ElementCollection
	@CollectionTable(name = "clinic_service", joinColumns = @JoinColumn(name = "CLINIC_ID"))
	@Column(name = "SERVICE")
	@Enumerated(EnumType.STRING)
	@JsonProperty("services")
	private Set<MedicalService> services;
	@SortField("label.modification.time")
	@Column(name = "MODIFICATION_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	private Date modificationTime;
	@SortField(label = "label.modified.by", complex = true)
	@ManyToOne
	@JoinColumn(name = "MODIFIED_BY_USER_ID", referencedColumnName = "id")
	@JsonIgnore
	private User modifiedBy;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = upperCase(searchName);
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

	@Override
	public Location getLocationAddress() {
		return locationAddress;
	}

	@Override
	public void setLocationAddress(Location contactAddress) {
		this.locationAddress = contactAddress;
	}

	@Override
	public List<Phone> getPhones() {
		return phones;
	}

	@Override
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public List<EmailAddress> getEmails() {
		return emails;
	}

	@Override
	public void setEmails(List<EmailAddress> emails) {
		this.emails = emails;
	}

	public Set<MedicalService> getServices() {
		return services;
	}

	public void setServices(Set<MedicalService> services) {
		this.services = services;
	}

	@Override
	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Override
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("displayName", displayName)
				.append("searchName", searchName)
				.append("clinicType", clinicType)
				.append("description", description)
				.append("contactAddress", locationAddress)
				.append("phones", phones)
				.append("emails", emails)
				.append("modificationTime", modificationTime)
				.append("modifiedBy", modifiedBy)
				.append("services", services)
				.toString();
	}
}
