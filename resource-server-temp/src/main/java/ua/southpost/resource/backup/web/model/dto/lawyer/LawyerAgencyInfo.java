package ua.southpost.resource.backup.web.model.dto.lawyer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.commons.model.dto.AbstractModificationTrackingEntityInfo;
import ua.southpost.resource.backup.web.model.dto.address.LocationInfo;
import ua.southpost.resource.backup.web.model.dto.address.SettlementInfo;
import ua.southpost.resource.backup.web.model.dto.contact.ContactInfo;
import ua.southpost.resource.backup.web.model.dto.contact.EmailInfo;
import ua.southpost.resource.backup.web.model.dto.contact.PhoneInfo;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LawyerAgencyInfo  extends AbstractModificationTrackingEntityInfo<Long> implements ContactInfo {
	@JsonProperty("settlement")
	private SettlementInfo settlement;
	@JsonProperty("agency_name")
	private String agencyName;
	@JsonProperty("web_site")
	private String webSite;
	@JsonProperty("cases")
	private Set<AbstractEntityInfo<LawCase>> cases;
	@JsonProperty("location")
	private LocationInfo location;
	@JsonProperty("phones")
	private List<PhoneInfo> phones;
	@JsonProperty("emails")
	private List<EmailInfo> emails;
}
