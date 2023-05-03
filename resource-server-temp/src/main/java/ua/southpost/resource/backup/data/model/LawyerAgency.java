package ua.southpost.resource.backup.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
@Table(name = "lawyer_agency")
public class LawyerAgency implements ModificationTrackingEntity, ContactEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	@JsonProperty("id")
	private Long id;
	@Column(name = "DISPLAY_NAME")
	@JsonProperty("display_name")
	private String displayName;
	@SortField(label = "label.lawyer.agency.name")
	@Column(name = "SEARCH_VALUE")
	@JsonProperty("search_value")
	private String searchValue;
	@SortField(label = "label.lawyer.agency.web.site")
	@Column(name = "WEB_SITE")
	@JsonProperty("web_site")
	private String webSite;
	@SortField(label = "label.settlement.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "SETTLEMENT_ID", referencedColumnName = "ID")
	@JsonProperty("settlement")
	private Settlement settlement;
	@SortField(label = "label.location.fields", complex = true)
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
	@JsonProperty("location_address")
	private Location locationAddress;
	@ManyToMany
	@JoinTable(
			name = "lawyer_agency_phones",
			joinColumns = {
					@JoinColumn(name = "AGENCY_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	@JsonProperty("phones")
	private List<Phone> phones;
	@ManyToMany
	@JoinTable(
			name = "lawyer_agency_emails",
			joinColumns = {
					@JoinColumn(name = "AGENCY_ID", referencedColumnName = "ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "EMAIL_ADDRESS_ID", referencedColumnName = "ID")
			}
	)
	@OrderColumn(name = "ORDINAL_INDEX")
	@JsonProperty("emails")
	private List<EmailAddress> emails;
	@ElementCollection
	@CollectionTable(name = "lawyer_agency_cases", joinColumns = @JoinColumn(name = "AGENCY_ID"))
	@Column(name = "CASE_ID")
	@Enumerated(EnumType.STRING)
	@JsonProperty("services")
	private Set<LawCase> cases;

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
		return id;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = upperCase(searchValue);
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	public Set<LawCase> getCases() {
		return cases;
	}

	public void setCases(Set<LawCase> cases) {
		this.cases = cases;
	}

	@Override
	public Location getLocationAddress() {
		return this.locationAddress;
	}

	@Override
	public List<Phone> getPhones() {
		return this.phones;
	}

	@Override
	public List<EmailAddress> getEmails() {
		return this.emails;
	}

	@Override
	public void setLocationAddress(Location location) {
		this.locationAddress = location;
	}

	@Override
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public void setEmails(List<EmailAddress> emails) {
		this.emails = emails;
	}

	@Override
	public Date getModificationTime() {
		return modificationTime;
	}

	@Override
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("id", id)
				.append("displayName", displayName)
				.append("searchValue", searchValue)
				.append("webSite", webSite)
				.append("settlement", settlement)
				.append("locationAddress", locationAddress)
				.append("phones", phones)
				.append("emails", emails)
				.append("services", cases)
				.append("modificationTime", modificationTime)
				.append("modifiedBy", modifiedBy)
				.toString();
	}
}
